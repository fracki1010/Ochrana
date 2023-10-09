package com.francogaldame.ochranaBank.services;

import com.francogaldame.ochranaBank.dtos.ClientDTO;
import com.francogaldame.ochranaBank.models.RolType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;

public interface ClientService {
    List<ClientDTO> getClients();

    ClientDTO getClientById(Long id);

    ResponseEntity<Object> register(String firstName, String lastName, String email,
                                    String password, RolType rolType,
                                    String DNI, String birthdate,
                                    String cuil, Authentication authentication);

    ClientDTO getCurrentClient(Authentication authentication);

    int randomNumber(int min, int max);
}
