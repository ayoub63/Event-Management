```markdown
# Registration Service

## Funktionsbeschreibung
Der Registration Service ist verantwortlich für die Verwaltung von Event-Registrierungen. Er bietet folgende Kernfunktionalitäten:

- Registrierung von Teilnehmern für Events
- Überprüfung der Event-Verfügbarkeit 
- Validierung von Registrierungsdaten
- Aktualisierung der Event-Kapazitäten
- Auslösen von Bestätigungsmails

## Schnittstellendefinition

### REST Endpoints

| Methode | Endpoint | Beschreibung | Request Body | Response |
|---------|----------|--------------|--------------|-----------|
| POST | /api/register | Neue Registrierung erstellen | `{"email": "string", "eventId": "number"}` | Registration-Objekt |
| GET | /api/register/{eventId} | Registrierungen für Event abrufen | - | Liste von Registrierungen |

### Service-Methoden

```java:src/main/java/com/example/eventmanagement/service/RegistrationService.java
startLine: 24
endLine: 53
```

## Datenmodell

```java:src/main/java/com/example/eventmanagement/model/Registration.java
startLine: 5
endLine: 50
```

## User Stories

```
Als Teilnehmer
möchte ich mich für ein Event registrieren können
damit ich einen Platz bei der Veranstaltung reservieren kann

Als Teilnehmer
möchte ich eine Bestätigungsmail erhalten
damit ich weiß, dass meine Registrierung erfolgreich war

Als System
möchte ich die Verfügbarkeit prüfen
damit Events nicht überbucht werden
```

## Fehlerbehandlung

Der Service behandelt folgende Fehlerfälle:

- Event nicht gefunden
- Event ausgebucht 
- E-Mail bereits registriert
- Ungültige Event-ID

## Controller-Implementation

```java:src/main/java/com/example/eventmanagement/controller/RegistrationController.java
startLine: 22
endLine: 52
```

## Integration

Der Registration Service ist mit folgenden anderen Services integriert:

- Event Service: Prüfung der Event-Verfügbarkeit
- Notification Service: Versand von Bestätigungsmails

Die Kommunikation erfolgt über Dependency Injection und Service-Aufrufe.
```
