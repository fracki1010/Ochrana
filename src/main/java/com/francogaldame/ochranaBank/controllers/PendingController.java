package com.francogaldame.ochranaBank.controllers;


import com.francogaldame.ochranaBank.dtos.PendingDTO;
import com.francogaldame.ochranaBank.models.Pending;
import com.francogaldame.ochranaBank.repositories.PendingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PendingController {

    @Autowired
    PendingRepository pendingRepository;

    @GetMapping("/cards/pending")
        public List<PendingDTO> getPending(){
        List<Pending> pendingCard = pendingRepository.findAll();
        return pendingCard
                .stream()
                .map(pending -> new PendingDTO(pending))
                .collect(Collectors.toList());
        }


}
