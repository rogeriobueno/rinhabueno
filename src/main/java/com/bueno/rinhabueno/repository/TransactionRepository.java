package com.bueno.rinhabueno.repository;

import com.bueno.rinhabueno.entity.Client;
import com.bueno.rinhabueno.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.client=:clientP ORDER BY t.createAt desc LIMIT 10")
    List<Transaction> findLastTenTransactions(@Param("clientP") Client client);

    Page<Transaction> findByClientOrderByCreateAtDesc(Client client, Pageable pageable);
}
