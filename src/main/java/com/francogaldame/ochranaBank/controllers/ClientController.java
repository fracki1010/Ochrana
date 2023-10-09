package com.francogaldame.ochranaBank.controllers;

import com.francogaldame.ochranaBank.dtos.ClientDTO;
import com.francogaldame.ochranaBank.models.RolType;
import com.francogaldame.ochranaBank.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientService clientService;


    @GetMapping("/clients")
    public List<ClientDTO> getClients(){
        return clientService.getClients();
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClientById(@PathVariable Long id){
        return clientService.getClientById(id);
    }


    //registrar un cliente nuevo
    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password,
            @RequestParam RolType rolType, @RequestParam String dni,
            @RequestParam String birthdate, @RequestParam String cuil, Authentication authentication){
        return clientService.register(firstName, lastName, email, password, rolType, dni, birthdate, cuil, authentication);
    }


    // Proceso de busqueda de cliente espeficico que ya inicio session
    @GetMapping("/clients/current")
    public ClientDTO getCurrentClient(Authentication authentication){
        return clientService.getCurrentClient(authentication);
    }
}
