package com.bueno.rinhabueno.repository;

import com.bueno.rinhabueno.dto.TransactionResponse;
import com.bueno.rinhabueno.entity.Client;
import com.bueno.rinhabueno.entity.TypeTransaction;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Query(value = "SELECT Ret_return_status, Ret_limite, Ret_saldo from create_transaction(:clientId, :valor, :descricao, :tipo)",
            nativeQuery = true)
    List<TransactioReturn> executeNewTransaction(Long clientId, Long valor, String descricao, String tipo);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Client> findClientById(Long id);

    @Query("SELECT c FROM Client c WHERE c.id = :clientId" )
    Optional<Client> findClientByIdNoLock(Long clientId);

}

