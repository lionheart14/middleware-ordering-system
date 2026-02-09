# Middleware Ordering System (MOS)

Dieses Repository enthält die Implementierung einer **Elektronischen Handelsplattform**. Das Projekt entstand im Rahmen der **5. Übung** zum Thema Middleware[cite: 8]. Es demonstriert die Integration verschiedener Programmiersprachen und Plattformen in einem verteilten System.

## 🏗 Architektur (4-Stufen-Modell)
Die Anwendung ist nach dem im Kurs behandelten Schichtenmodell aufgebaut:

1.  **Stufe 1 & 2 (Präsentation):** Ein **Python-Client**, der Bestellvorgänge über das Netzwerk anstößt.
2.  **Stufe 3 (Anwendungslogik):** Ein **Java-gRPC-Server**, der die Vorverarbeitung der Bestellungen und die Geschäftslogik übernimmt.
3.  **Stufe 4 (Datenhaltung):** Ein **PostgreSQL-Container** zur persistenten Speicherung der Kundendaten und Transaktionen.



## ✅ Implementierte Anforderungen
Das System erfüllt zentrale Anforderungen aus der Aufgabenstellung:

* **Plattformneutralität (Aufgabe 10):** Kommunikation zwischen Java (Server) und Python (Client) mittels gRPC über verschiedene Container hinweg[cite: 75, 76].
* **Kundenrabatte (Anforderung b):** Automatische Berechnung von Rabatten (z. B. für VIP-Kunden) während der Auftragsbearbeitung.
* **Firmenkunden-Validierung (Anforderung c):** Zugriffskontrolle, die bestimmte Bestellvorgänge exklusiv für Firmenkunden reserviert.
* **Persistenz-Vorbereitung (Anforderung f):** Strukturierte Datenhaltung zur konsistenten Verwaltung von Kundendatensätzen.

## 🛠 Technologien
* **Middleware:** gRPC (Remote Procedure Call).
* **Sprachen:** Java 17 (Server), Python 3.9 (Client).
* **Infrastruktur:** Docker & Docker Compose zur Gewährleistung der Plattformneutralität.
* **Datenbank:** PostgreSQL 15.

## 🚀 Schnellstart
Um das System zu bauen und zu starten, sind folgende Befehle im Hauptverzeichnis nötig:

1. **System bauen:**
   ```powershell
   docker compose build
2. **System starten:**
   ```powershell
   docker compose up

Der Client führt nach dem Start automatisch Test-Szenarien aus und zeigt die Ergebnisse (Erfolg, Rabatte, Zugriffskontrolle) direkt in der Konsole an.

Projekt von: Leon und Kilian

Lehrveranstaltung: Middleware & mobile Cloud Computing (M.Sc.) 

Institution: Universität der Bundeswehr München
   