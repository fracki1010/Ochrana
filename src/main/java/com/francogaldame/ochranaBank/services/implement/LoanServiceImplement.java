package com.francogaldame.ochranaBank.services.implement;


import com.francogaldame.ochranaBank.dtos.*;
import com.francogaldame.ochranaBank.models.*;
import com.francogaldame.ochranaBank.repositories.*;
import com.francogaldame.ochranaBank.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LoanServiceImplement implements LoanService {

    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ClientLoanRepository clientLoanRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Set<LoanDTO> getLoans(){
        return loanRepository
                .findAll()
                .stream()
                .map(loan -> new LoanDTO(loan))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ClientLoanDTO> getClientLoans(){
        return clientLoanRepository
                .findAll()
                .stream()
                .map(loan -> new ClientLoanDTO(loan))
                .collect(Collectors.toSet());
    }
    @Override
    public ResponseEntity applyForLoan(LoanApplicationDTO loanApplicationDTO, Authentication authentication){

        //Cliente autenticado
        Client client = clientRepository.findByEmail(authentication.getName());

        //cuenta del cliente
        Account accountClient = client
                .getAccounts()
                .stream()
                .filter(account -> account.getNumber().equals(loanApplicationDTO.getToAccountNumber()))
                .findFirst()
                .orElse(null);

        //Verifica que los campos no este vacios
        if (loanApplicationDTO.getToAccountNumber().isBlank() || loanApplicationDTO.getLoanId() == 0
                || loanApplicationDTO.getAmount() == 0 || loanApplicationDTO.getPayments() == 0){
            return new ResponseEntity<>("Completa todos los campos", HttpStatus.FORBIDDEN);
        }

        if (!loanRepository.existsById(loanApplicationDTO.getLoanId())){
            return new ResponseEntity<>("El prestamo no existe", HttpStatus.FORBIDDEN);
        }

        //Loan pedido por el cliente
        Loan loan = loanRepository.findById(loanApplicationDTO.getLoanId()).orElse(null);
        //Verifica que el monto no exceda el prestamo maximo
        if (loan.getMaxAmount() <= loanApplicationDTO.getAmount()){
            return new ResponseEntity<>("El monto que pide excede a maximo posible", HttpStatus.FORBIDDEN);
        }

        //Verificar que la cantidad de cuotas exista en el prestamo
        if (!loan.getPayments().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("Selecciono una cantidad de cuotas inexistente", HttpStatus.FORBIDDEN);
        }

        if (!accountRepository.existsByNumber(loanApplicationDTO.getToAccountNumber())){
            return new ResponseEntity<>("La cuenta de destino no existe", HttpStatus.FORBIDDEN);
        }

        //Verifica que la cuenta de destino pertenezca al cliente autenticado
        if (!client.getAccounts().contains(accountClient)){
            return new ResponseEntity<>("La cuenta de destino no pertenece al cliente autenticado", HttpStatus.FORBIDDEN);
        }

        //Verifica que la cuenta esta previamente aprobada por un administrador
        if(!accountClient.getApproved()){
            return new ResponseEntity<>("La cuenta de destino no esta aprobada", HttpStatus.FORBIDDEN);
        }

        //Creacion del Prestamo al cliente
        ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getPayments(),
                loanApplicationDTO.getAmount() * 1.2, loanApplicationDTO.getToAccountNumber(), client, loan, false);


        //Guardado de cuenta y cliente
        accountRepository.save(accountClient);
        clientLoanRepository.save(clientLoan);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity approvedLoan(LoanApprovedDTO loanApprovedDTO){
        Loan loan = loanRepository.findByName(loanApprovedDTO.getLoanName());
        Account accountClient = accountRepository.findByNumber(loanApprovedDTO.getToAccountNumber());

        //Aprobacion del prestamo
        ClientLoan clientLoan = clientLoanRepository.findById(loanApprovedDTO.getClientLoanId()).orElse(null);
        clientLoan.setApproved(true);


        //Creacion de la transaccion
        Transaction transaction = new Transaction(TransactionType.CREDIT, loanApprovedDTO.getAmount(),
                loan.getName()+" loan approved", LocalDate.now());

        //Asignacion del dinero a la cuenta
        accountClient.setBalance(accountClient.getBalance()+(loanApprovedDTO.getAmount()));

        //Asignacion de transaccion a la cuenta
        accountClient.addTransaction(transaction);

        //Guardado de transaccion
        clientLoanRepository.save(clientLoan);
        accountRepository.save(accountClient);
        transactionRepository.save(transaction);

        return new ResponseEntity("Se realizo la aprobacion del pago",HttpStatus.OK);
    }


    //elimina el prestamo
    @Override
    public ResponseEntity deleteLoan(Long loanDeleteDTO){
        clientLoanRepository.deleteById(loanDeleteDTO);
        return new ResponseEntity("Se elimino el prestamo",HttpStatus.OK);
    }
}
