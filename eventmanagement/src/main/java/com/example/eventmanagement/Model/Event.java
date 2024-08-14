package com.example.eventmanagement.Model;

import java.util.List;

public class Event {
    private Long id;              // Eindeutige ID des Events
    private String name;          // Name des Events
    private String description;   // Beschreibung des Events
    private String date;          // Datum des Events
    private String time;          // Uhrzeit des Events
    private String location;      // Veranstaltungsort
    private String category;      // Kategorie des Events (z.B. MUSIC, TECH, ARTS, SPORTS)
    private int capacity;         // Maximale Teilnehmerzahl
    private int registeredAttendees; // Aktuelle Anzahl der registrierten Teilnehmer
    private String imageUrl;      // URL zum Event-Bild
    private double price;         // Preis des Events in Euro
    private String organizer;     // Name des Veranstalters
    private List<String> tags;    // Liste von Schlagworten für das Event
    private EventStatus status;   // Status des Events (UPCOMING, ONGOING, COMPLETED, CANCELLED)
    
    public enum EventStatus {
        UPCOMING, ONGOING, COMPLETED, CANCELLED
    }

    // Konstruktor, der das Event mit den angegebenen Werten erstellt
    public Event(Long id, String name, String description, String date, 
                String time, String location, String category, int capacity, 
                int registeredAttendees, String imageUrl, double price, 
                String organizer, List<String> tags, EventStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
        this.location = location;
        this.category = category;
        this.capacity = capacity;
        this.registeredAttendees = registeredAttendees;
        this.imageUrl = imageUrl;
        this.price = price;
        this.organizer = organizer;
        this.tags = tags;
        this.status = status;
    }

    // Konstruktor, der das Event mit den angegebenen Werten erstellt
    public Event(Long id, String name, String description, String date) {
        this.id = id; // ID generieren
        this.name = name;
        this.description = description;
        this.date = date;
    }

    // Getter und Setter für jede Variable
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRegisteredAttendees() {
        return registeredAttendees;
    }

    public void setRegisteredAttendees(int registeredAttendees) {
        this.registeredAttendees = registeredAttendees;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public boolean hasAvailableSpots() {
        return registeredAttendees < capacity;
    }

    public void incrementRegisteredAttendees() {
        if (hasAvailableSpots()) {
            this.registeredAttendees++;
        } else {
            throw new IllegalStateException("Keine verfügbaren Plätze mehr");
        }
    }
}
