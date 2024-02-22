package com.bueno.rinhabueno.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "transactions")
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "value",  nullable = false)
    private Long value;

    @Column(name = "type_transaction",  nullable = false, length = 1)
    @Enumerated(EnumType.STRING)
    private TypeTransaction type;

    @Column(nullable = false, length = 50)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "create_at")
    private ZonedDateTime createAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

}
