package com.example.eventmanagement.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.eventmanagement.Service.RegistrationService;
import com.example.eventmanagement.Model.Registration;

@RestController
@RequestMapping("/api/register")

public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        try {
            // Input überprüfen
            if (!body.containsKey("email") || !body.containsKey("eventId")) {
                return ResponseEntity.badRequest()
                    .body("Eamil und EventId werden bemötigt");
            }

            String email = body.get("email");
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Email cannot be empty");
            }

            Long eventId;
            try {
                eventId = Long.parseLong(body.get("eventId"));
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body("Invalid eventId format");
            }

            // Registrierung versuchen
            Registration registration = registrationService.register(email, eventId);
            return ResponseEntity.ok(registration);

        } catch (Exception e) {
            e.printStackTrace(); // Fehler loggen
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Registration failed: " + e.getMessage());
        }
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<?> getRegistrations(@PathVariable Long eventId) {
        try {
            List<Registration> registrations = registrationService.getRegistrationsByEventId(eventId);
            return ResponseEntity.ok(registrations);
        } catch (Exception e) {
            e.printStackTrace(); // Log the error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to fetch registrations: " + e.getMessage());
        }
    }
}
