package com.bueno.rinhabueno.repository;

import com.bueno.rinhabueno.entity.Client;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Client> findClientById(Long id);

    @Query("SELECT c FROM Client c WHERE c.id = :clientId")
    Optional<Client> findClientByIdNoLock(Long clientId);

}

