package com.example.eventmanagement.Service;

import com.example.eventmanagement.Model.Registration; // Registrierungs-Klasse importieren
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.eventmanagement.Model.Event;

@Service
public class RegistrationService {
    private final Map<Long, Registration> registrations = new HashMap<>(); // Hash-Map ID als Key, Registration als Value
    private Long registrationIdCounter = 1L; // Long

    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private EventService eventService; // eventService importieren

    public Registration register(String email, Long eventId) {
        // Check if event exists
        Event event = eventService.getEventById(eventId)
            .orElseThrow(() -> new IllegalArgumentException("Event mit ID " + eventId + " nicht gefunden"));
        
        // Check if there are available spots
        if (event.getRegisteredAttendees() >= event.getCapacity()) {
            throw new IllegalStateException("Event ist ausgebucht");
        }

        // Check if email is already registered for this event
        boolean alreadyRegistered = registrations.values().stream()
            .anyMatch(reg -> reg.getEmail().equals(email) && reg.getEventId().equals(eventId));
        
        if (alreadyRegistered) {
            throw new IllegalStateException("Diese E-Mail-Adresse ist bereits für dieses Event registriert");
        }

        // Create registration
        Registration registration = new Registration(registrationIdCounter++, email, eventId);
        registrations.put(registration.getId(), registration);
        
        // Update event capacity
        event.setRegisteredAttendees(event.getRegisteredAttendees() + 1);
        
        // Send confirmation email
        notificationService.sendEmail(email, event.getName());
        
        return registration;
    }

    public List<Registration> getRegistrationsByEventId(Long eventId) {
        return registrations.values().stream()
            .filter(reg -> reg.getEventId().equals(eventId)) // filtert den Stream nach Registrierungs ID's
            .collect(Collectors.toList()); // Gefilterter Stream wird zu Liste konvertiert
    }

    public boolean isEmailRegisteredForEvent(String email, Long eventId) {
        return registrations.values().stream()
            .anyMatch(reg -> reg.getEmail().equals(email) && reg.getEventId().equals(eventId));
    }

    public int getRegistrationCountForEvent(Long eventId) {
        return (int) registrations.values().stream()
            .filter(reg -> reg.getEventId().equals(eventId))
            .count();
    }
}
