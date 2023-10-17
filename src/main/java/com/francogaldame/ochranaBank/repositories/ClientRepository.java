package com.francogaldame.ochranaBank.repositories;

import com.francogaldame.ochranaBank.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByEmail(String email);
    Boolean existsByDni(String dni);

    Boolean existsByCuil(String cuil);
}
