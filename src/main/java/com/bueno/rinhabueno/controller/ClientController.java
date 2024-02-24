package com.bueno.rinhabueno.controller;

import com.bueno.rinhabueno.dto.ExtratoResponse;
import com.bueno.rinhabueno.dto.TransactionRequest;
import com.bueno.rinhabueno.dto.TransactionResponse;
import com.bueno.rinhabueno.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/{id}/transacoes")
    public ResponseEntity<?> newTransaction(@PathVariable("id") Long clientId, @RequestBody TransactionRequest request) {
        TransactionResponse transactionResponse;
        transactionResponse = clientService.addNewTransaction(clientId, request);
        return ResponseEntity.ok(transactionResponse);
    }

    @GetMapping("/{id}/extrato")
    public ResponseEntity<?> fetchExtrato(@PathVariable("id") Long clientId) {
        ExtratoResponse extratoResponse = clientService.showLastTenTransactions(clientId);
        return ResponseEntity.ok(extratoResponse);
    }

}
