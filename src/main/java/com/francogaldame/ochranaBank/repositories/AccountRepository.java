package com.francogaldame.ochranaBank.repositories;


import com.francogaldame.ochranaBank.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account,Long> {
    Boolean existsByNumber(String number);
    Account findByNumber(String number);
}
