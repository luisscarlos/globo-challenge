package com.microservice.consumer.entity;

import com.microservice.consumer.model.NotificationTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "event_history")
@NoArgsConstructor
public class EventHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    private String type;

    @ManyToOne
    @JoinColumn(name = "subscription_id", nullable = false)
    private Subscription subscription;

    public EventHistory(NotificationTypeEnum notificationType, Subscription subscription) {
        this.type = notificationType.toString();
        this.subscription = subscription;
    }
}
