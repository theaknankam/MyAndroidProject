# Carsharing-App

Eine Carsharing-App für Stidenten auf Android, die im Rahmen des Moduls **Mobile Computing (MOCO)** an der TH Köln (SoSe 2026) ertsellt wurde. Mit der App können Nutzer an angebotene Fahrten teilnehmen gegen eine fairen preis, die Route wird auf einer Karte angezeigt.

## Funktionen

- **Authentifizierung** — Registrierung und Anmeldung über Firebase Authentifizierung
- **Fahrzeuge durchsuchen** — Liste der verfügbaren Fahrzeuge mit Details (Modell, Preis, Verfügbarkeit, Ladys only, Raucher , etc) anzeigen
- **Kartenansicht** — Interaktiven Karte mit Routen anzeigen
- **Buchung** — eine angebotene Fahrt für ein Zeitfenster reservieren
- **SOS / Notfall** — Schnellzugriff auf die Notruf-Schaltfläche

## Tech-Stack

| Ebene | Technologie |
|---|---|
| Sprache | Kotlin |
| UI | Jetpack Compose (Material 3) |
| Navigation | Jetpack Navigation Compose |
| Backend / Authentifizierung | Firebase (Firestore, Authentifizierung) |
| Lokaler Speicher | Room |
| Karten & Routenplanung |##placeholder## |
| IDE | Android Studio |

## Architektur

Die App folgt einer schichtbasierten Architektur:

```
ui_elemente/
├── screens/        # Composable-Bildschirme (Startseite, Karte, Buchung, SOS usw.)
├── navigation/      # NavHost, Topbar
├── viewmodel/       # ViewModels zur Verwaltung des UI-Zustands und der Geschäftslogik
├── data/            # Repositories, Room-DAOs/Entitäten, Firestore-Zugriff
└── model/           # Datenklassen (Auto, Buchung, Benutzer usw.)
```

## Screenshots

> _Screenshots folgen in Kürze_

| Startseite / Durchsuchen | Kartenansicht | Buchung | SOS |
|---|---|---|---|
| _Platzhalter_ | _Platzhalter_ | _Platzhalter_ | _Platzhalter_ |


### Voraussetzungen

- Android Studio (neueste stabile Version) oder IntelliJ IDEA mit Android-Plugin
- JDK 17+
- Ein Firebase-Projekt (Firestore + Authentifizierung aktiviert)

### 

## Team

| Name | Rolle |
|---|---|
| Diana Sukiiazova | _TODO_ |
| Liya Aklil | _TODO_ |
| Thea E. Kamdoum-Nankam | _TODO_ |

## Kurskontext

Dieses Projekt wurde für das Modul **Mobile Computing** (MI/WPF)(MOCO) an der TH Köln entwickelt.
