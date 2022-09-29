package com.microservice.consumer.entity;

import com.microservice.consumer.model.NotificationTypeEnum;
import com.microservice.consumer.model.StatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "status")
@NoArgsConstructor
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    public Status(NotificationTypeEnum notificationType) {
        switch (notificationType) {
            case SUBSCRIPTION_PURCHASED:
            case SUBSCRIPTION_RESTARTED:
                this.name = StatusEnum.ACTIVE.toString();
                break;
            case SUBSCRIPTION_CANCELED:
                this.name = StatusEnum.CANCELED.toString();
                break;
        }
    }
}
