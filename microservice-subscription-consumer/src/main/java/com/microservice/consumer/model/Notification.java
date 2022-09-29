package com.microservice.consumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Notification {

    @JsonProperty("notification_type")
    private NotificationTypeEnum notificationType;

    @JsonProperty("subscription")
    private String subscriptionId;
}
