package com.francogaldame.ochranaBank.services.implement;

import com.francogaldame.ochranaBank.models.Account;
import com.francogaldame.ochranaBank.models.Transaction;
import com.francogaldame.ochranaBank.models.TransactionType;
import com.francogaldame.ochranaBank.repositories.AccountRepository;
import com.francogaldame.ochranaBank.repositories.ClientRepository;
import com.francogaldame.ochranaBank.repositories.TransactionRepository;
import com.francogaldame.ochranaBank.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
@Transactional
@Service
public class TransactionServiceImplement implements TransactionService {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public ResponseEntity<Object> createdTransaction(String fromAccountNumber, String toAccountNumber,
            Double amount, String description, Authentication authentication){

        //Verificar que no haya espacios en blanco y todo este completo
        if (fromAccountNumber.isBlank() || toAccountNumber.isBlank() || amount == 0 || description.isBlank()){
            return new ResponseEntity<>("Porfavor complete todos los casilleros", HttpStatus.FORBIDDEN);
        }

        //Verificar que las cuentas no sean identicas
        if (fromAccountNumber.equals(toAccountNumber)){
            return new ResponseEntity<>("Los numeros de cuenta son identicos",HttpStatus.FORBIDDEN);
        }

        //Verifica que la cuenta de origen exista
        if (!accountRepository.existsByNumber(fromAccountNumber)){
            return new ResponseEntity<>("No existe esta cuenta",HttpStatus.FORBIDDEN);
        }


        //Lista de cuentas que tiene el cliente autenticado
        Set<String> currentAccount = clientRepository
                .findByEmail(authentication.getName())
                .getAccounts()
                .stream()
                .map(account -> account.getNumber())
                .collect(Collectors.toSet());

        if (!currentAccount.contains(fromAccountNumber)){
            return new ResponseEntity<>("Esta cuenta no es del cliente actual", HttpStatus.FORBIDDEN);
        }

        //Verificar que la cuenta de destino exista
        if (!accountRepository.existsByNumber(toAccountNumber)){
            return new ResponseEntity<>("No existe la cuenta de destino", HttpStatus.FORBIDDEN);
        }

        //Busqueda de ambas cuentas en la base de datos y asignacion a una variable
        Account originAccount = accountRepository.findByNumber(fromAccountNumber);
        Account destinationAccount = accountRepository.findByNumber(toAccountNumber);

        //Verifica que las cuentas elegidas esten aprobadas
        if (!originAccount.getApproved() || !destinationAccount.getApproved()){
            return new ResponseEntity<>("Cuenta no aprobada", HttpStatus.FORBIDDEN);
        }

        //Condicion el cliente tiene el balance suficiente para la transaccion
        if(originAccount.getBalance() < amount){
            return new ResponseEntity<>("No el tiene dinero suficiente en su cuenta", HttpStatus.FORBIDDEN);
        }

        //Transaccion 1
        Transaction transaction1 = new Transaction(TransactionType.DEBIT, -amount,
                "DEBIT " + destinationAccount.getNumber()
                        + " " + description, LocalDate.now());
        Double balanceOriginAccount = originAccount.getBalance();
        originAccount.setBalance(balanceOriginAccount - amount);


        //Transaccion 2
        Transaction transaction2 = new Transaction(TransactionType.CREDIT, amount,
                "CREDIT " + originAccount.getNumber()
                        +" "+ description, LocalDate.now());
        Double balanceDestinationAccount = destinationAccount.getBalance();
        destinationAccount.setBalance(balanceDestinationAccount + amount);

        //Asignacion de las transacciones a cada cuenta
        originAccount.addTransaction(transaction1);
        destinationAccount.addTransaction(transaction2);

        //Guardado en el repositorio de transacciones
        transactionRepository.save(transaction1);
        transactionRepository.save(transaction2);

        //Guardado en el repositorio de cuentas
        accountRepository.save(originAccount);
        accountRepository.save(destinationAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
