package com.example.eventmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

public class EventDTO {
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotBlank(message = "Description is required")
    private String description;
    
    @NotNull(message = "Date is required")
    private LocalDateTime date;

    // Constructors
    public EventDTO() {
    }

    public EventDTO(String name, String description, LocalDateTime date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    @Override
    public String toString() {
        return "EventDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventDTO eventDTO = (EventDTO) o;
        return Objects.equals(name, eventDTO.name) &&
                Objects.equals(description, eventDTO.description) &&
                Objects.equals(date, eventDTO.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, date);
    }
} 