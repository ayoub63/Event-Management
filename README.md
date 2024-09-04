# Event Management System

## Overview
A web-based event management system that allows users to browse, filter, and register for various events. The system is built with a Spring Boot backend and JavaScript frontend, providing a responsive and user-friendly interface for event management.

## Features
- 🎫 Event browsing and registration
- 🔍 Advanced filtering capabilities:
  - Search by name and description
  - Filter by category
  - Filter by date
  - Filter by price (free/paid)
- 📱 Responsive event cards displaying:
  - Event name and description
  - Date and time
  - Location
  - Category
  - Price (formatted in EUR)
  - Organizer information
  - Available capacity
  - Tags
  - Event status
- ✉️ Email confirmation system for registrations
- 🌐 Multi-language support (German interface)

## Technical Stack
### Backend
- Java 17
- Spring Boot
- Spring Data JPA
- Jakarta Validation
- RESTful API

### Frontend
- HTML5
- CSS3
- JavaScript (Vanilla)

## API Endpoints
- `GET /api/events` - Retrieve all events
- `POST /api/register` - Register for an event

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven
- MySQL/PostgreSQL (or your preferred database)

### Installation
1. Clone the repository