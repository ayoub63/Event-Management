package com.example.eventmanagement.Service;

import com.example.eventmanagement.Model.Event;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EventService {
    private final Map<Long, Event> events = new HashMap<>();
    private Long eventIdCounter = 1L;

    // Initialize with sample events
    public EventService() {
        // Musik-Events
        addEvent(new Event(eventIdCounter++, 
            "Sommerfestival 2024",
            "Erleben Sie ein unvergessliches Wochenende mit Live-Musik-Auftritten von Top-Künstlern.",
            "2024-07-15",
            "14:00",
            "Stadtpark Frankfurt",
            "MUSIC",
            5000,
            0,
            "/images/music-festival.jpg",
            49.99,
            "Frankfurt Events GmbH",
            Arrays.asList("live", "open-air", "festival"),
            Event.EventStatus.UPCOMING
        ));

        addEvent(new Event(eventIdCounter++,
            "Klassische Orchesternacht",
            "Ein Abend mit klassischen Meisterwerken, aufgeführt von der Frankfurter Philharmonie.",
            "2024-05-20",
            "19:30",
            "Alte Oper Frankfurt",
            "MUSIC",
            800,
            0,
            "/images/classical.jpg",
            79.99,
            "Frankfurter Philharmonie",
            Arrays.asList("klassik", "orchester", "indoor"),
            Event.EventStatus.UPCOMING
        ));

        // Technologie-Events
        addEvent(new Event(eventIdCounter++,
            "Entwicklerkonferenz 2024",
            "Treffen Sie führende Tech-Experten für Vorträge über KI, Webentwicklung und Cloud Computing.",
            "2024-06-10",
            "09:00",
            "Messe Frankfurt",
            "TECH",
            2000,
            0,
            "/images/dev-conf.jpg",
            299.99,
            "TechEvents Frankfurt",
            Arrays.asList("technologie", "konferenz", "networking"),
            Event.EventStatus.UPCOMING
        ));

        // Kostenloses Tech-Event
        addEvent(new Event(eventIdCounter++,
            "Open Source Hackathon",
            "Arbeiten Sie gemeinsam mit anderen Entwicklern an Open-Source-Projekten. Kostenlose Teilnahme!",
            "2024-05-25",
            "10:00",
            "TechHub Frankfurt",
            "TECH",
            100,
            0,
            "/images/hackathon.jpg",
            0.0,
            "OpenTech Community",
            Arrays.asList("hackathon", "opensource", "coding"),
            Event.EventStatus.UPCOMING
        ));

        // Kunst-Events
        addEvent(new Event(eventIdCounter++,
            "Moderne Kunstausstellung",
            "Zeitgenössische Kunstausstellung mit lokalen und internationalen Künstlern.",
            "2024-06-01",
            "11:00",
            "Kunsthalle Frankfurt",
            "ARTS",
            300,
            0,
            "/images/art-exhibition.jpg",
            15.00,
            "Frankfurter Kunstverein",
            Arrays.asList("kunst", "ausstellung", "kultur"),
            Event.EventStatus.UPCOMING
        ));

        // Sport-Events
        addEvent(new Event(eventIdCounter++,
            "Frankfurt Marathon 2024",
            "Jährlicher Stadtmarathon durch das Herz von Frankfurt.",
            "2024-10-27",
            "08:00",
            "Frankfurt Innenstadt",
            "SPORTS",
            10000,
            0,
            "/images/marathon.jpg",
            45.00,
            "Frankfurter Sportverein",
            Arrays.asList("marathon", "laufen", "outdoor"),
            Event.EventStatus.UPCOMING
        ));
    }

    private void addEvent(Event event) {
        events.put(event.getId(), event);
    }

    public List<Event> getAllEvents() {
        return new ArrayList<>(events.values());
    }

    public List<Event> getFilteredEvents(String category, String dateFilter, String priceFilter) {
        return events.values().stream()
            .filter(event -> category.isEmpty() || event.getCategory().equals(category))
            .filter(event -> filterByDate(event, dateFilter))
            .filter(event -> filterByPrice(event, priceFilter))
            .collect(Collectors.toList());
    }

    private boolean filterByDate(Event event, String dateFilter) {
        if (dateFilter.isEmpty()) return true;
        
        LocalDate eventDate = LocalDate.parse(event.getDate());
        LocalDate today = LocalDate.now();
        
        return switch (dateFilter) {
            case "TODAY" -> eventDate.equals(today);
            case "THIS_WEEK" -> eventDate.isAfter(today.minusDays(1)) && 
                               eventDate.isBefore(today.plusDays(7));
            case "THIS_MONTH" -> eventDate.getMonth() == today.getMonth() && 
                                eventDate.getYear() == today.getYear();
            default -> true;
        };
    }

    private boolean filterByPrice(Event event, String priceFilter) {
        return switch (priceFilter) {
            case "FREE" -> event.getPrice() == 0;
            case "PAID" -> event.getPrice() > 0;
            default -> true;
        };
    }

    public Event createEvent(String name, String description, LocalDateTime date) {
        Event event = new Event(eventIdCounter++, name, description, date.toString());
        events.put(event.getId(), event);
        return event;
    }

    public Optional<Event> getEventById(Long id) {
        return Optional.ofNullable(events.get(id));
    }
}
