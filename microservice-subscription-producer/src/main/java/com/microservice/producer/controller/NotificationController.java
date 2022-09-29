package com.microservice.producer.controller;

import com.microservice.producer.model.Notification;
import com.microservice.producer.service.NotificationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/api/v1/")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Value("${app.message}")
    private String response;

    @PostMapping("/notify")
    public ResponseEntity<String> sendMessage(@RequestBody Notification notification) {
        notificationService.sendMessage(notification);
        log.info("Subscription notification sent: {}", notification);
        return ResponseEntity.ok(response);
    }
}
