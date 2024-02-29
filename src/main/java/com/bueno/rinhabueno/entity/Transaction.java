package com.bueno.rinhabueno.entity;

import jakarta.persistence.*;

import java.time.ZonedDateTime;


@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "value", nullable = false)
    private Long value;
    @Column(name = "type_transaction", nullable = false, length = 1)
    @Enumerated(value = EnumType.STRING)
    private TypeTransaction type;

    @Column(length = 20)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_at")
    private ZonedDateTime createAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    public Transaction() {
        createAt = ZonedDateTime.now();
    }

    public Transaction(Long value, TypeTransaction type, String description, Client client) {
        this();
        this.value = value;
        this.type = type;
        this.description = description;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public TypeTransaction getType() {
        return type;
    }

    public void setType(TypeTransaction type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(ZonedDateTime createAt) {
        this.createAt = createAt;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
