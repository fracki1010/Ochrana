package com.francogaldame.ochranaBank.repositories;

import com.francogaldame.ochranaBank.models.Pending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PendingRepository extends JpaRepository<Pending, Long> {
}
