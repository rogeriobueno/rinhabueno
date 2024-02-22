package com.bueno.rinhabueno.entity;

import com.bueno.rinhabueno.dto.TransactionResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
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
//    @Version
//    private Integer version;
}
