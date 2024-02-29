package com.bueno.rinhabueno.service;

import com.bueno.rinhabueno.dto.*;
import com.bueno.rinhabueno.entity.Client;
import com.bueno.rinhabueno.entity.Transaction;
import com.bueno.rinhabueno.entity.TypeTransaction;
import com.bueno.rinhabueno.exceptions.ResourceNotFoundException;
import com.bueno.rinhabueno.exceptions.ResourceUnprocessableException;
import com.bueno.rinhabueno.repository.ClientRepository;
import com.bueno.rinhabueno.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private boolean isPermitedOperation(final TransactionRequest request, final Client client) {
        long saldo = client.getSaldo();
        long limite = client.getLimite();
        long valor = request.valor();

        if (request.tipo().equals("d")) {
            return (limite + saldo) > valor;
        }
        return true;
    }

    @Transactional
    public TransactionResponse addNewTransaction(Long clientId, TransactionRequest request) {

        Client savedClient = saveAndUpdateClient(clientId, request);

        Transaction newTransaction = new Transaction(request.valor(),
                TypeTransaction.valueOf(request.tipo().toLowerCase()), request.descricao(), savedClient);
        transactionRepository.save(newTransaction);

        return new TransactionResponse(savedClient.getSaldo(), savedClient.getLimite());
    }

    @Transactional
    public synchronized Client saveAndUpdateClient(long clientId, TransactionRequest request) {

        Client client = fetchClientLocked(clientId);

        if (!isPermitedOperation(request, client)) {
            throw new ResourceUnprocessableException("Not permited");
        }

        TypeTransaction transactionType = TypeTransaction.valueOf(request.tipo());
        long transactionAmount = request.valor();
        long currentBalance = client.getSaldo();
        long newBalance;
        if (transactionType == TypeTransaction.c) {
            newBalance = currentBalance + transactionAmount;
        } else {
            newBalance = currentBalance - transactionAmount;
        }
        client.setSaldo(newBalance);

        return clientRepository.save(client);
    }

    public ExtratoResponse showLastTenTransactions(Long clientId) {

        Client client = fetchClient(clientId);

//        List<Transaction> lastTransactions = transactionRepository.findLastTenTransactions(client);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Transaction> lastTransactions = transactionRepository.findByClientOrderByCreateAtDesc(client, pageable);

        SaldoResponse saldo = new SaldoResponse(client.getSaldo(), ZonedDateTime.now(), client.getLimite());

        List<TransactionItemResponse> transactionItens = lastTransactions.stream().map(transaction ->
                        new TransactionItemResponse(transaction.getValue(), transaction.getType().name(),
                                transaction.getDescription(), transaction.getCreateAt()))
                .toList();

        return new ExtratoResponse(saldo, transactionItens);
    }


    private Client fetchClientLocked(Long clientId) {
        return clientRepository.findById(clientId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Cliente não encontrado com id %d", clientId)));
    }

    private Client fetchClient(Long clientId) {
        return clientRepository.findClientByIdNoLock(clientId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Cliente não encontrado com id %d", clientId)));
    }

}
