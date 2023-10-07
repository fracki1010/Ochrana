package com.francogaldame.ochranaBank.services.implement;

import com.francogaldame.ochranaBank.dtos.ClientDTO;
import com.francogaldame.ochranaBank.models.Account;
import com.francogaldame.ochranaBank.models.Client;
import com.francogaldame.ochranaBank.repositories.AccountRepository;
import com.francogaldame.ochranaBank.repositories.ClientRepository;
import com.francogaldame.ochranaBank.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;


@Service
public class ClientServiceImplement implements ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<ClientDTO> getClients(){
        List<Client> allClients = clientRepository.findAll();

        //funcion map
        List<ClientDTO> convertedList = allClients
                .stream()
                .map(currentClient -> new ClientDTO(currentClient))
                .collect(Collectors.toList());

        return convertedList;
    }


    @Override
    public ClientDTO getClientById(@PathVariable Long id){
        Optional<Client> clientOptional = clientRepository.findById(id);
        return new ClientDTO(clientOptional.get());
    }


    //registrar un cliente nuevo
    @Override
    public ResponseEntity<Object> register(String firstName,  String lastName,
             String email,  String password, Authentication authentication){

        if (firstName.isEmpty() || lastName.isEmpty()||email.isEmpty()||password.isEmpty()){
            return new ResponseEntity<>("Porfavor completa todo los datos", HttpStatus.FORBIDDEN);
        }

        if(clientRepository.findByEmail(email) !=null){
            return new ResponseEntity<>("Este nombre de usuario ya fue utilizado", HttpStatus.FORBIDDEN);
        }



        //Creador de número random y comprobar que no exista en la base de datos
        String numberAccount;
        do {

            numberAccount = "VIN-" + String.valueOf(randomNumber(0, 99999999));

        }while (accountRepository.existsByNumber(numberAccount));

        //creacion de la cuenta unica
        Account accountCurrent = new Account(numberAccount, LocalDate.now(),0.0);

        //Identificaion del clinte y asignacion de cuenta a cliente
        Client client = new Client(firstName,lastName,email,passwordEncoder.encode(password));
        client.addAccount(accountCurrent);

        //Guardado de cliente
        clientRepository.save(client);


        //Guardado de cuenta
        accountRepository.save(accountCurrent);

        //----------------------------------

        return new ResponseEntity<>("Cliente registrado con existo",HttpStatus.CREATED);
    }



    // Proceso de busqueda de cliente espeficico que ya inicio session
    @Override
    public ClientDTO getCurrentClient(Authentication authentication){
        Client clientCurrent = clientRepository.findByEmail(authentication.getName());
        return new ClientDTO(clientCurrent);
    }
    //Funcion del número aleatorio

    @Override
    public int randomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
