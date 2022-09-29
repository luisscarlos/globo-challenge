package com.microservice.producer.service;

import com.microservice.producer.model.Notification;
import com.microservice.producer.model.NotificationTypeEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class NotificationService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.purchased-routing-key}")
    private String purchasedRoutingKey;

    @Value("${spring.rabbitmq.canceled-routing-key}")
    private String canceledRoutingKey;

    @Value("${spring.rabbitmq.restarted-routing-key}")
    private String restartedRoutingKey;

    @Value("${spring.rabbitmq.dead-letter-routing-key}")
    private String deadLetterRoutingKey;

    public void sendMessage(Notification notification) {
        try {
            rabbitTemplate.convertAndSend(exchange, getRoutingKey(notification.getNotificationType()), notification);
        } catch (Exception e) {
            log.error("Error to send message. " + e);
        }
    }

    private String getRoutingKey(NotificationTypeEnum notificationType) {
        switch(notificationType) {
            case SUBSCRIPTION_PURCHASED:
                return purchasedRoutingKey;
            case SUBSCRIPTION_CANCELED:
                return canceledRoutingKey;
            case SUBSCRIPTION_RESTARTED:
                return restartedRoutingKey;
            default:
                return deadLetterRoutingKey;
        }

    }
}
