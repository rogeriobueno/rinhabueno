package com.bueno.rinhabueno.controller;

import com.bueno.rinhabueno.dto.TransactionRequest;
import com.bueno.rinhabueno.service.ClientService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> newTransaction(@PathVariable("id") Long clientId,
                                            @RequestBody TransactionRequest request) {
        if (!isValidRequest(request)) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        return ResponseEntity.ok(clientService.addNewTransaction(clientId, request));
    }

    private boolean isValidRequest(TransactionRequest request) {
        if (request == null ||
                request.valor() == null || request.valor() <= 0 ||
                request.descricao() == null || request.descricao().isEmpty() || request.descricao().length() > 10) {
            return false;
        }
        String tipo = request.tipo();
        return "c".equals(tipo) || "d".equals(tipo);
    }

    @GetMapping("/{id}/extrato")
    public ResponseEntity<?> fetchExtrato(@PathVariable("id") Long clientId) {
        if (clientId == null)
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        else
            return ResponseEntity.ok(clientService.showLastTenTransactions(clientId));

    }

}
