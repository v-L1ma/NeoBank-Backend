package com.neobank.neobankapi.entities;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name= "tb_transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="transaction_id")
    private Long transactionId;


    @ManyToOne
    @JoinColumn(name= "sender_id", referencedColumnName="client_id")
    private Client sender;

    @ManyToOne
    @JoinColumn(name= "receiver_id", referencedColumnName="client_id")
    private Client receiver;

    private Double value;
    
    @CreationTimestamp
    private Instant creationTimeStamp;

    public Long gettransactionId() {
        return transactionId;
    }

    public void settransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Client getSender() {
        return sender;
    }

    public void setSender(Client sender) {
        this.sender = sender;
    }

    public Client getReceiver() {
        return receiver;
    }

    public void setReceiver(Client receiver) {
        this.receiver = receiver;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Instant getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(Instant creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }

}
