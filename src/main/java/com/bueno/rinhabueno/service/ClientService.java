package com.bueno.rinhabueno.service;

import com.bueno.rinhabueno.dto.*;
import com.bueno.rinhabueno.entity.Client;
import com.bueno.rinhabueno.entity.Transaction;
import com.bueno.rinhabueno.entity.TypeTransaction;
import com.bueno.rinhabueno.exceptions.ResourceNotFoundException;
import com.bueno.rinhabueno.exceptions.ResourceUnprocessableException;
import com.bueno.rinhabueno.repository.ClientRepository;
import com.bueno.rinhabueno.repository.TransactioReturn;
import com.bueno.rinhabueno.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class ClientService {

    private final TransactionRepository transactionRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public TransactionResponse addNewTransactionProc(Long clientId, TransactionRequest request) {
        TransactioReturn response = clientRepository.executeNewTransaction(clientId,
                request.valor(), request.descricao(), request.tipo().toUpperCase()).get(0);

        if (response.getRet_return_status().equals(422))
            throw new ResourceUnprocessableException("Not permit");
        if (response.getRet_return_status().equals(404))
            throw new ResourceNotFoundException("Not found");

        return TransactionResponse.builder()
                .returnStatus(response.getRet_return_status())
                .saldo(response.getRet_saldo())
                .limite(response.getRet_limite())
                .build();
    }

    @Transactional
    public TransactionResponse addNewTransaction(Long clientId, TransactionRequest request) {

        Client savedClient = saveAndUpdateClient(clientId, request);

        Transaction newTransaction = Transaction.builder()
                .client(savedClient)
                .value(request.valor())
                .type(TypeTransaction.valueOf(request.tipo().toUpperCase()))
                .description(request.descricao())
                .createAt(ZonedDateTime.now())
                .build();

        transactionRepository.save(newTransaction);

        return TransactionResponse.builder()
                .saldo(savedClient.getSaldo())
                .limite(savedClient.getLimite())
                .build();
    }

    @Transactional
    public Client saveAndUpdateClient(Long clientId, TransactionRequest request) {
        Client client = fetchClientLocked(clientId);

        if (isPermitedOperation(request, client)) {
            throw new ResourceUnprocessableException("Operacap nao pode ser processada");
        }
        if (request.tipo().equalsIgnoreCase(TypeTransaction.C.name())) {
            client.setSaldo(client.getSaldo() + request.valor());
        } else {
            client.setSaldo(client.getSaldo() - request.valor());
        }
        return clientRepository.save(client);
    }


    private static boolean isPermitedOperation(TransactionRequest request, Client client) {
        return TypeTransaction.D.isEqualOf(request.tipo()) &&
                (client.getLimite() + client.getSaldo() < request.valor());
    }

    @Transactional(readOnly = true)
    public ExtratoResponse showLastTenTransactions(Long clientId) {

        Client client = fetchClient(clientId);

        SaldoResponse saldo = SaldoResponse.builder()
                .dataExtrato(ZonedDateTime.now())
                .total(client.getSaldo())
                .limite(client.getLimite())
                .build();

        List<Transaction> lastTransactions = transactionRepository.findLastTenTransactions(client);

        List<TransactionItemResponse> transactionItens = lastTransactions.parallelStream().map(transaction ->
                        TransactionItemResponse.builder()
                                .valor(transaction.getValue())
                                .tipo(transaction.getType().name())
                                .descricao(transaction.getDescription())
                                .createAt(transaction.getCreateAt())
                                .build())
                .toList();

        return ExtratoResponse.builder()
                .saldo(saldo)
                .ultimasTransacoes(transactionItens)
                .build();
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
