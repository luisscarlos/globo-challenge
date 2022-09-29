package com.microservice.consumer.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.exchange}")
    private String notificationsExchange;

    @Value("${spring.rabbitmq.dead-letter-exchange}")
    private String deadLetterExchange;

    @Value("${spring.rabbitmq.purchased-queue}")
    private String purchasedQueue;

    @Value("${spring.rabbitmq.canceled-queue}")
    private String canceledQueue;

    @Value("${spring.rabbitmq.restarted-queue}")
    private String restartedQueue;

    @Value("${spring.rabbitmq.dead-letter-queue}")
    private String deadLetterQueue;

    @Value("${spring.rabbitmq.purchased-routing-key}")
    private String purchasedRoutingKey;

    @Value("${spring.rabbitmq.canceled-routing-key}")
    private String canceledRoutingKey;

    @Value("${spring.rabbitmq.restarted-routing-key}")
    private String restartedRoutingKey;

    @Value("${spring.rabbitmq.dead-letter-routing-key}")
    private String deadLetterRoutingKey;

    @Bean
    public Exchange notificationsExchange() {
        return ExchangeBuilder.directExchange(notificationsExchange).durable(true).build();
    }

    @Bean
    public Exchange deadLetterExchange() {
        return ExchangeBuilder.directExchange(deadLetterExchange).durable(true).build();
    }

    private Map<String, Object> deadLetterArgs() {
        Map<String, Object> deadLetterArg = new HashMap<String, Object>();
        deadLetterArg.put("x-dead-letter-exchange", deadLetterExchange);
        deadLetterArg.put("x-dead-letter-routing-key", deadLetterRoutingKey);

        return deadLetterArg;
    }

    @Bean
    public Queue purchasedQueue() {
        return QueueBuilder.durable(purchasedQueue).withArguments(deadLetterArgs()).build();
    }

    @Bean
    public Queue canceledQueue() {
        return QueueBuilder.durable(canceledQueue).withArguments(deadLetterArgs()).build();
    }

    @Bean
    public Queue restartedQueue() {
        return QueueBuilder.durable(restartedQueue).withArguments(deadLetterArgs()).build();
    }

    @Bean
    public Queue deadLetterQueue() { return new Queue(deadLetterQueue, true); }

    @Bean
    public Binding purchasedBinding() {
        return BindingBuilder
                .bind(purchasedQueue())
                .to(notificationsExchange())
                .with(purchasedRoutingKey)
                .noargs();
    }

    @Bean
    public Binding canceledBinding() {
        return BindingBuilder
                .bind(canceledQueue())
                .to(notificationsExchange())
                .with(canceledRoutingKey)
                .noargs();
    }

    @Bean
    public Binding restartedBinding() {
        return BindingBuilder
                .bind(restartedQueue())
                .to(notificationsExchange())
                .with(restartedRoutingKey)
                .noargs();
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder
                .bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(deadLetterRoutingKey)
                .noargs();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        return cachingConnectionFactory;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
