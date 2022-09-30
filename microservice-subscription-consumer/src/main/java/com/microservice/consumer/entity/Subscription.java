package com.microservice.consumer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "subscription")
@NoArgsConstructor
public class Subscription {

    @Id
    private String id;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    public Subscription(String subscriptionId, Status status) {
        this.id = subscriptionId;
        this.status = status;
    }

}
