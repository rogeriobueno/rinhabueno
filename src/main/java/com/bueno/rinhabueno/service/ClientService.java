package com.bueno.rinhabueno.service;

import com.bueno.rinhabueno.dto.*;
import com.bueno.rinhabueno.entity.Client;
import com.bueno.rinhabueno.entity.Transaction;
import com.bueno.rinhabueno.entity.TypeTransaction;
import com.bueno.rinhabueno.exceptions.ResourceNotFoundException;
import com.bueno.rinhabueno.exceptions.ResourceUnprocessableException;
import com.bueno.rinhabueno.repository.ClientRepository;
import com.bueno.rinhabueno.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ClientService {

    private final TransactionRepository transactionRepository;
    private final ClientRepository clientRepository;

    public ClientService(TransactionRepository transactionRepository, ClientRepository clientRepository) {
        this.transactionRepository = transactionRepository;
        this.clientRepository = clientRepository;
    }

    private static boolean isPermitedOperation(TransactionRequest request, Client client) {
        return TypeTransaction.D.isEqualIgnoreCase(request.tipo()) &&
                (client.getLimite() + client.getSaldo() < request.valor());
    }

    @Transactional
    public TransactionResponse addNewTransaction(Long clientId, TransactionRequest request) {

        Client savedClient = saveAndUpdateClient(clientId, request);

        Transaction newTransaction = new Transaction(request.valor(),
                TypeTransaction.valueOf(request.tipo().toUpperCase()), request.descricao(), savedClient);
        transactionRepository.save(newTransaction);

        return new TransactionResponse(savedClient.getSaldo(), savedClient.getLimite());
    }

    @Transactional
    public Client saveAndUpdateClient(Long clientId, TransactionRequest request) {
        Client client = fetchClientLocked(clientId);

        if (isPermitedOperation(request, client)) {
            throw new ResourceUnprocessableException("Operacap nao pode ser processada");
        }
        if (TypeTransaction.C.isEqualIgnoreCase(request.tipo())) {
            client.setSaldo(client.getSaldo() + request.valor());
        } else {
            client.setSaldo(client.getSaldo() - request.valor());
        }
        return clientRepository.save(client);
    }

    @Transactional(readOnly = true)
    public ExtratoResponse showLastTenTransactions(Long clientId) {

        Client client = fetchClient(clientId);

        SaldoResponse saldo = new SaldoResponse(client.getSaldo(), ZonedDateTime.now(), client.getLimite());

        List<Transaction> lastTransactions = transactionRepository.findLastTenTransactions(client);

        List<TransactionItemResponse> transactionItens = lastTransactions.parallelStream().map(transaction ->
                        new TransactionItemResponse(transaction.getValue(), transaction.getType().name(),
                                transaction.getDescription(), transaction.getCreateAt()))
                .toList();

        return new ExtratoResponse(saldo, transactionItens);
    }


    private Client fetchClientLocked(Long clientId) {
        return clientRepository.findById(clientId).orElseThrow(() -> {
            return new ResourceNotFoundException(String.format("Cliente não encontrado com id %d", clientId));
        });
    }

    private Client fetchClient(Long clientId) {
        return clientRepository.findClientByIdNoLock(clientId).orElseThrow(() -> {
            return new ResourceNotFoundException(String.format("Cliente não encontrado com id %d", clientId));
        });
    }

}
