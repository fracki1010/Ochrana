package com.francogaldame.ochranaBank.dtos;


import com.francogaldame.ochranaBank.models.Client;
import com.francogaldame.ochranaBank.models.RolType;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private RolType rol;
    private String dni;
    private String birthdate;
    private String cuil;
    private int age;
    private Set<AccountDTO> accounts = new HashSet<>();
    private Set<ClientLoanDTO> loans = new HashSet<>();
    private Set<CardDTO> cards = new HashSet<>();


    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.rol = client.getRol();
        this.dni = client.getDni();
        this.birthdate = client.getBirthdate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.cuil = client.getCuil();
        this.age = Period.between(client.getBirthdate(), LocalDate.now()).getYears();
        this.accounts = client
                            .getAccounts()
                            .stream()
                            .map(account -> new AccountDTO(account))
                            .collect(Collectors.toSet());
        this.loans = client
                        .getClientLoans()
                        .stream()
                        .map(loan -> new ClientLoanDTO(loan))
                        .collect(Collectors.toSet());
        this.cards = client
                        .getCards()
                        .stream()
                        .map(card -> new CardDTO(card))
                        .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public RolType getRol() {
        return rol;
    }

    public String getDni() {
        return dni;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getCuil() {
        return cuil;
    }

    public int getAge() {
        return age;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }

    public Set<CardDTO> getCards() {
        return cards;
    }
}