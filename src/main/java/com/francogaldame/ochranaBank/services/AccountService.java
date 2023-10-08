package com.francogaldame.ochranaBank.services;

import com.francogaldame.ochranaBank.dtos.AccountDTO;
import com.francogaldame.ochranaBank.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AccountService {
    List<AccountDTO> getAccounts();

    AccountDTO getAccountById(Long id);

    List<AccountDTO> getAccountsCurrent(Authentication authentication);

    ResponseEntity<Object> createdAccount(Authentication authentication);

    Client getCurrentClient(Authentication authentication);
}
