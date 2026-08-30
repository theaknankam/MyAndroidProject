# Carsharing-App

Eine Carsharing-App für Studierende auf Android, die im Rahmen des **Moduls Mobile Computing (MOCO)** an der TH Köln im Sommersemester 2026 entwickelt wurde. Mit der App können Studierende gegenseitig Fahrten anbieten, nach passenden Mitfahrangebote zu einem fairen Preis suchen und diese buchen. Nach der Buchung können sie sich über die Karte über die Route informieren und direkt mit dem Fahrer bzw. der Fahrerin chatten.

## Funktionen

- **Authentifizierung** — Registrierung und Anmeldung über Firebase Authentifizierung
- **Fahrzeuge durchsuchen** — Liste der verfügbaren Fahrzeuge mit Details (Modell, Preis, Verfügbarkeit, Ladys only, Raucher , etc) anzeigen
- **Fahrten anbieten** – Neue Fahrten mit Start- und Zielort, Datum, verfügbaren Sitzplätzen, Preis und zusätzlichen Präferenzen erstellen
- **Fahrten suchen** – Nach Fahrten suchen, die den angegebenen Suchparametern entsprechen
- **Kartenansicht** — Interaktiven Karte mit Routen anzeigen
- **Buchung** — Eine angebotene Fahrt für ein Zeitfenster reservieren
- **Wallet** - Jedes Konto verfügt über ein In-App-Wallet, das online aufgeladen werden kann
- **Chat** - Echtzeit-Chat zwischen Fahrer und Mitfahrer über Firestore
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

<img width="400" height="800" alt="Screenshot_20260830_212400" src="https://github.com/user-attachments/assets/1c3effa2-bd81-4965-a5ef-e8ddcf523e85" />
<img width="400" height="800" alt="Screenshot_20260830_212531" src="https://github.com/user-attachments/assets/f0b4475a-ff74-4ab4-be22-5e96e84c8563" />
<img width="400" height="800" alt="Screenshot_20260830_212455" src="https://github.com/user-attachments/assets/0cb28920-b1de-41f7-941e-6b8fff11fbc0" />
<img width="400" height="800" alt="Screenshot_20260830_221725" src="https://github.com/user-attachments/assets/1e4787d2-1ea7-4257-92ef-fa759a57b01f" />




### Voraussetzungen

- Android Studio (neueste stabile Version) oder IntelliJ IDEA mit Android-Plugin
- JDK 17+
- Ein Firebase-Projekt (Firestore + Authentifizierung aktiviert)

### 

## Team

| Name |
|---|
| Diana Sukiiazova |
| Liya Aklil |
| Thea E. Kamdoum-Nankam |

## Kurskontext

Dieses Projekt wurde für das Modul **Mobile Computing** (MI/WPF)(MOCO) an der TH Köln entwickelt, im So26. 
