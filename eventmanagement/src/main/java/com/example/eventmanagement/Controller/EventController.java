package com.example.eventmanagement.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.example.eventmanagement.Service.EventService;
import com.example.eventmanagement.Model.Event;
import com.example.eventmanagement.dto.EventDTO;
import com.example.eventmanagement.exception.ResourceNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @PostMapping("/create")
    public ResponseEntity<Event> createEvent(@Valid @RequestBody EventDTO eventDTO) {
        Event createdEvent = eventService.createEvent(
            eventDTO.getName(),
            eventDTO.getDescription(),
            eventDTO.getDate()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return eventService.getEventById(id)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
    }
}