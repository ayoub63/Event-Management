package com.example.eventmanagement.Controller;

import com.example.eventmanagement.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notify")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<String> sendNotification(@RequestParam String email, @RequestParam String eventName) {
        try {
            notificationService.sendEmail(email, eventName);
            return ResponseEntity.ok("Nachricht erfolgreich gesendet");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Senden der Nachricht fehlgeschlagen");
        }
    }
}
