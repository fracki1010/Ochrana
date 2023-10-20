package com.francogaldame.ochranaBank.services.implement;


import com.francogaldame.ochranaBank.dtos.*;
import com.francogaldame.ochranaBank.models.*;
import com.francogaldame.ochranaBank.repositories.*;
import com.francogaldame.ochranaBank.services.LoanService;
import com.francogaldame.ochranaBank.utils.PaymentsUtils;
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
            return new ResponseEntity<>("Complete all fields", HttpStatus.FORBIDDEN);
        }

        if (!loanRepository.existsById(loanApplicationDTO.getLoanId())){
            return new ResponseEntity<>("The requested loan does not exist", HttpStatus.FORBIDDEN);
        }

        //Loan pedido por el cliente
        Loan loan = loanRepository.findById(loanApplicationDTO.getLoanId()).orElse(null);
        //Verifica que el monto no exceda el prestamo maximo
        if (loan.getMaxAmount() <= loanApplicationDTO.getAmount()){
            return new ResponseEntity<>("The amount requested exceeds the maximum possible", HttpStatus.FORBIDDEN);
        }

        //Verificar que la cantidad de cuotas exista en el prestamo
        if (!loan.getPayments().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("I select a non-existent amount of installments", HttpStatus.FORBIDDEN);
        }

        if (!accountRepository.existsByNumber(loanApplicationDTO.getToAccountNumber())){
            return new ResponseEntity<>("The destination account does not exist", HttpStatus.FORBIDDEN);
        }

        //Verifica que la cuenta de destino pertenezca al cliente autenticado
        if (!client.getAccounts().contains(accountClient)){
            return new ResponseEntity<>("The destination account does not belong to the authenticated client", HttpStatus.FORBIDDEN);
        }

        //Verifica que la cuenta esta previamente aprobada por un administrador
        if(!accountClient.getApproved()){
            return new ResponseEntity<>("The destination account is not approved", HttpStatus.FORBIDDEN);
        }

        //Traemos el loan solicitado por el cliente para poder espesificar el nombre
        Loan loanRequest = loanRepository.findById(loanApplicationDTO.getLoanId()).orElse(null);

        //Creacion del Prestamo al cliente, tambien se le agrega el interes con un utilis
        ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getPayments(),
                loanApplicationDTO.getAmount() * PaymentsUtils.bankInterest(loanApplicationDTO.getPayments(), loanRequest.getName()),
                loanApplicationDTO.getToAccountNumber(), client, loan, false);


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

        // Sustraccion del interes, porque se coloca el interes cuando hacemos la transaccion

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

        return new ResponseEntity("Payment approval is made",HttpStatus.OK);
    }


    //elimina el prestamo
    @Override
    public ResponseEntity deleteLoan(Long loanDeleteDTO){
        clientLoanRepository.deleteById(loanDeleteDTO);
        return new ResponseEntity("delete loan",HttpStatus.OK);
    }

}
