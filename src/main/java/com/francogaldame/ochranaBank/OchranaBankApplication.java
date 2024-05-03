package com.francogaldame.ochranaBank;

import com.francogaldame.ochranaBank.models.*;
import com.francogaldame.ochranaBank.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class OchranaBankApplication <commandLineRunner> {

	public static void main(String[] args) {
		SpringApplication.run(OchranaBankApplication.class, args);
	}

/*	@Autowired
	PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository,
									  AccountRepository accountRepository, TransactionRepository transactionRepository,
									  LoanRepository loanRepository, ClientLoanRepository clientLoanRepository,
									  CardRepository cardRepository, PendingRepository pendingRepository) {
		return (arg) -> {
			//Creacion de clientes
			Client client1 = new Client("Melba", "Morel", "melba@gmail.com", passwordEncoder.encode("1234"), RolType.CLIENT, "42.793.845", LocalDate.of(2001,12,20),"20-42793845-0");
			Client client2 = new Client("Franco", "Galdame", "franco23@admin.com", passwordEncoder.encode("123"), RolType.ADMIN, "44.453.234", LocalDate.of(2000,7,5),"20-25362967-1");

			clientRepository.save(client1);
			clientRepository.save(client2);


			//Creacion de cuentas
			Account account1 = new Account("VIN001", LocalDate.now(), 5000.0, false);
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500.0, true);

			Account account3 = new Account("VIN003", LocalDate.now().plusDays(2), 2000.0, true);
			Account account4 = new Account("VIN004", LocalDate.now().plusDays(1), 10500.0, true);


			client1.addAccount(account1);
			client1.addAccount(account2);
			client2.addAccount(account3);
			client2.addAccount(account4);

			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);


			//Creacion de transacciones

			Transaction transaction1 = new Transaction(TransactionType.CREDIT, 400.0, "Progresar", LocalDate.now());
			Transaction transaction2 = new Transaction(TransactionType.DEBIT, -300.0, "Pago x", LocalDate.now().plusDays(4));
			Transaction transaction3 = new Transaction(TransactionType.DEBIT, -1000.0, "Pago x", LocalDate.now().plusDays(3));
			Transaction transaction4 = new Transaction(TransactionType.CREDIT, 1500.0, "Sueldo", LocalDate.now().plusDays(1));


			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);
			account2.addTransaction(transaction3);
			account4.addTransaction(transaction4);


			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);


			//Creacion de prestamos

			Loan loan1 = new Loan("Hipotecario", 5000000, List.of(12, 24, 36, 48, 60));
			Loan loan2 = new Loan("Personal", 1000000, List.of(6, 12, 24));
			Loan loan3 = new Loan("Automotriz", 7000000, List.of(12, 24, 36));
			Loan loan4 = new Loan("Empresario", 9000000, List.of(6, 12));

			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);


			//Agregación de prestamos a clientes

			ClientLoan clientLoan1 = new ClientLoan(60, 400000.0, "VIN001", client1, loan1, false);
			ClientLoan clientLoan2 = new ClientLoan(12, 12000.0, "VIN001", client1, loan2, true);


			ClientLoan clientLoan3 = new ClientLoan(24, 100000.0, "VIN003", client2, loan2, true);
			ClientLoan clientLoan4 = new ClientLoan(36, 200000.0, "VIN003", client2, loan3, true);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);

			//Creacion de prestamo

			Card card1 = new Card(client1.getFirstName() + " " + client1.getLastName(), CardType.DEBIT, CardColor.GOLD, "5090-6554-4233-2112", "998", LocalDate.now(), LocalDate.now().plusYears(5), true);
			Card card2 = new Card(client1.getFirstName() + " " + client1.getLastName(), CardType.CREDIT, CardColor.TITANIUM, "5085-5433-2345-5342", "889", LocalDate.now(), LocalDate.now().plusYears(5), false);
			Card card4 = new Card(client1.getFirstName() + " " + client1.getLastName(), CardType.CREDIT, CardColor.GOLD, "5555-3467-6453-5533", "839", LocalDate.now(), LocalDate.now().plusYears(5), true);

			Card card3 = new Card(client2.getFirstName() + " " + client2.getLastName(), CardType.CREDIT, CardColor.SILVER, "5040-2345-6577-9871", "337", LocalDate.now(), LocalDate.now().plusYears(6), true);

			client1.addCard(card1);
			client1.addCard(card2);
			client2.addCard(card3);
			client1.addCard(card4);

			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);
			cardRepository.save(card4);

			Pending pending1 = new Pending("card", client1.getEmail(), CardType.CREDIT.toString(), CardColor.SILVER.toString(), loan1.getId(), 0.0, 0 );

			pendingRepository.save(pending1);
		};
	}*/
}
