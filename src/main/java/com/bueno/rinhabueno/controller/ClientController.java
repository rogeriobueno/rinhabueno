package com.bueno.rinhabueno.controller;

import com.bueno.rinhabueno.dto.ExtratoResponse;
import com.bueno.rinhabueno.dto.TransactionRequest;
import com.bueno.rinhabueno.dto.TransactionResponse;
import com.bueno.rinhabueno.entity.Client;
import com.bueno.rinhabueno.exceptions.ConflictRequestException;
import com.bueno.rinhabueno.service.ClientService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/clientes")
@AllArgsConstructor
@Slf4j
@Validated
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/{id}/transacoes")
    public ResponseEntity<?> newTransaction(@PathVariable("id") @NotNull(message = "ID deve ter um valor") Long clientId,
                                            @Valid @RequestBody TransactionRequest request) {
        TransactionResponse transactionResponse;
            transactionResponse = clientService.addNewTransaction(clientId, request);
        return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}/extrato")
    public ResponseEntity<?> fetchExtrato(@PathVariable("id") @NotNull(message = "ID deve ter um valor") Long clientId) {
        ExtratoResponse extratoResponse = clientService.showLastTenTransactions(clientId);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(100, TimeUnit.MILLISECONDS))
                .body(extratoResponse);
    }

}
