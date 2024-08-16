package com.example.eventmanagement.Model;


// Registrierungsklasse, speichert Email und Event
public class Registration {
    private Long id;  // Registration ID
    private String email;
    private Long eventId;
    private boolean confirmed;

    public Registration() {}

    public Registration(Long id, String email, Long eventId) {
        this.id = id;
        this.email = email;
        this.eventId = eventId;
        this.confirmed = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}
