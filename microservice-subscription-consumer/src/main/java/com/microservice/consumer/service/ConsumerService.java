package com.microservice.consumer.service;

import com.microservice.consumer.entity.EventHistory;
import com.microservice.consumer.entity.Status;
import com.microservice.consumer.entity.Subscription;
import com.microservice.consumer.model.Notification;
import com.microservice.consumer.repository.EventHistoryRepository;
import com.microservice.consumer.repository.StatusRepository;
import com.microservice.consumer.repository.SubscriptionRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Log4j2
public class ConsumerService {

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private EventHistoryRepository eventHistoryRepository;

    @RabbitListener(queues = "${spring.rabbitmq.purchased-queue}")
    public void subscriptionPurchasedListener(Notification notification) {
        log.info("Queue: subscription-purchased - Message received. {}", notification);
        handleListenerData(notification);
        log.info("Queue: subscription-purchased - Data saved to DB.");
    }

    @RabbitListener(queues = "${spring.rabbitmq.canceled-queue}")
    public void subscriptionCanceledListener(Notification notification) {
        log.info("Queue: subscription-canceled - Message received. {}", notification);
        handleListenerData(notification);
        log.info("Queue: subscription-canceled - Data saved to DB.");
    }

    @RabbitListener(queues = "${spring.rabbitmq.restarted-queue}")
    public void subscriptionRestartedListener(Notification notification) {
        log.info("Queue: subscription-restarted - Message received. {}", notification);
        handleListenerData(notification);
        log.info("Queue: subscription-restarted - Data saved to DB.");
    }

    @Transactional
    private void handleListenerData(Notification notification) {

        Status status = new Status(notification.getNotificationType());
        Subscription subscription = handleSubscription(notification.getSubscriptionId(), status);
        EventHistory eventHistory = new EventHistory(notification.getNotificationType(), subscription);

        statusRepository.save(status);
        subscriptionRepository.save(subscription);
        eventHistoryRepository.save(eventHistory);
    }

    private Subscription handleSubscription(String subscriptionId, Status status) {
        Optional<Subscription> subscriptionById = subscriptionRepository.findById(subscriptionId);
        if (subscriptionById.isPresent()) {
            subscriptionById.get().setUpdatedAt(LocalDateTime.now());
            subscriptionById.get().setStatus(status);

            return subscriptionById.get();
        }

        return new Subscription(subscriptionId, status);
    }
}
