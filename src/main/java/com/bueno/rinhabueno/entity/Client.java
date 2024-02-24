package com.bueno.rinhabueno.entity;

import com.bueno.rinhabueno.dto.TransactionResponse;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "nome", length = 50)
    private String nome;
    @Column(name = "limite")
    private Long limite;
    @Column(name = "saldo")
    private Long saldo;

    public Client() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getLimite() {
        return limite;
    }

    public void setLimite(Long limite) {
        this.limite = limite;
    }

    public Long getSaldo() {
        return saldo;
    }

    public void setSaldo(Long saldo) {
        this.saldo = saldo;
    }
}
