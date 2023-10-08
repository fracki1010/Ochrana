package com.francogaldame.ochranaBank.services.implement;

import com.francogaldame.ochranaBank.dtos.AccountDTO;
import com.francogaldame.ochranaBank.models.Account;
import com.francogaldame.ochranaBank.models.Client;
import com.francogaldame.ochranaBank.repositories.AccountRepository;
import com.francogaldame.ochranaBank.repositories.ClientRepository;
import com.francogaldame.ochranaBank.services.AccountService;
import com.francogaldame.ochranaBank.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplement implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<AccountDTO> getAccounts(){
        List<Account> allAccounts = accountRepository.findAll();

        return allAccounts
                .stream()
                .map(currentAccount -> new AccountDTO(currentAccount))
                .collect(Collectors.toList());
    }

    @Override
    public AccountDTO getAccountById(@PathVariable Long id){
        Optional<Account> accountOptional = accountRepository.findById(id);
        return new AccountDTO (accountOptional.get());
    }

    @Override
    public List<AccountDTO> getAccountsCurrent(Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        return client
                .getAccounts()
                .stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<Object> createdAccount(Authentication authentication){

        if (clientRepository.findByEmail(authentication.getName()).getAccounts().size() >= 3){
            return new ResponseEntity<>("Usted llego al limite de cuentas posibles", HttpStatus.FORBIDDEN);
        }

        //Creador de número random y comprobar que no exista en la base de datos
        String numberAccount;
        do {

            numberAccount = "VIN-" + String.valueOf(CardUtils.randomNumber(0, 99999999));

        }while (accountRepository.existsByNumber(numberAccount));


        //creacion de la cuenta unica
        Account accountCurrent = new Account(numberAccount, LocalDate.now(),0.0);

        //Identificaion del clinte y asignacion de cuenta a cliente
        clientRepository.findByEmail(authentication.getName()).addAccount(accountCurrent);

        //Guardado de cuenta
        accountRepository.save(accountCurrent);

        return new ResponseEntity<>("Cuenta creada con exito",HttpStatus.CREATED);
    }


    //Identificacion del cliente
    @Override
    public Client getCurrentClient(Authentication authentication){
        Client clientCurrent = clientRepository.findByEmail(authentication.getName());
        return clientCurrent;
    }
}
