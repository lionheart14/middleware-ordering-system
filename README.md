# Middleware Ordering System (MOS)

[cite_start]Dieses Repository enthält die Implementierung einer **Elektronischen Handelsplattform**. Das Projekt entstand im Rahmen der **5. [cite_start]Übung** zum Thema Middleware[cite: 8]. [cite_start]Es demonstriert die Integration verschiedener Programmiersprachen und Plattformen in einem verteilten System[cite: 75, 76].

## 🏗 Architektur (4-Stufen-Modell)
[cite_start]Die Anwendung ist nach dem im Kurs behandelten Schichtenmodell aufgebaut [cite: 103-106]:

1.  [cite_start]**Stufe 1 & 2 (Präsentation):** Ein **Python-Client**, der Bestellvorgänge über das Netzwerk anstößt[cite: 103, 104].
2.  [cite_start]**Stufe 3 (Anwendungslogik):** Ein **Java-gRPC-Server**, der die Vorverarbeitung der Bestellungen und die Geschäftslogik übernimmt[cite: 105, 118].
3.  [cite_start]**Stufe 4 (Datenhaltung):** Ein **PostgreSQL-Container** zur persistenten Speicherung der Kundendaten und Transaktionen[cite: 106, 123].



## ✅ Implementierte Anforderungen
[cite_start]Das System erfüllt zentrale Anforderungen aus der Aufgabenstellung[cite: 114]:

* **Plattformneutralität (Aufgabe 10):** Kommunikation zwischen Java (Server) und Python (Client) mittels gRPC über verschiedene Container hinweg[cite: 75, 76].
* [cite_start]**Kundenrabatte (Anforderung b):** Automatische Berechnung von Rabatten (z. B. für VIP-Kunden) während der Auftragsbearbeitung[cite: 118, 119].
* [cite_start]**Firmenkunden-Validierung (Anforderung c):** Zugriffskontrolle, die bestimmte Bestellvorgänge exklusiv für Firmenkunden reserviert[cite: 120].
* **Persistenz-Vorbereitung (Anforderung f):** Strukturierte Datenhaltung zur konsistenten Verwaltung von Kundendatensätzen[cite: 123, 124].

## 🛠 Technologien
* [cite_start]**Middleware:** gRPC (Remote Procedure Call)[cite: 75].
* [cite_start]**Sprachen:** Java 17 (Server), Python 3.9 (Client)[cite: 76].
* **Infrastruktur:** Docker & Docker Compose zur Gewährleistung der Plattformneutralität[cite: 77].
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
   