package com.francogaldame.ochranaBank.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    //Propiedad nueva en mis cuentas, uno a muchos
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();


    //Propiendad de Client Loan, uno a muchos
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();


    //Propiedad de <<Card>>, uno a muchos
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();


    public Client() {
    }

    public Client(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public Long getId() {
        return id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastname(String lastName) {
        this.lastName = lastName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public Set<Card> getCards() {
        return cards;
    }


    //Metodo para agregar una cuenta
    public void addAccount(Account account) {
        //Decirle a la mascota que el dueño soy yo

        account.setClient(this);
        //agregar la mascota que me pasaron como parametro a mi coleccion de mascotas
        accounts.add(account);
    }


    // Metodo de agregar <<Prestamos>> a cliente

    public void addClientLoan(ClientLoan clientLoan){
        clientLoan.setClient(this);
        clientLoans.add(clientLoan);
    }


    //Obtener una lista de pagos

    public List<Loan> getLoans() {
        return clientLoans.stream().map(loan -> loan.getLoan()).collect(toList());
    }


    //Metodo agregar <<Tarjeta>> a un cliente

    public void addCard(Card card){
        card.setClient(this);
        cards.add(card);
    }
}

