Voraussetzungen

Um die Applikation korrekt auszuführen, werden folgende Voraussetzungen benötigt:

- Eine PostgreSQL-Datenbank, die mit den Daten aus einem der folgenden Datenbank-Dumps eingerichtet ist:
  - "Bachelor/dump-bundeshaushaltdb-202502061041"
  - "Bachelor/src/test/resources/DB_backup"

Konfiguration

1. Datenbank-Konfiguration:
   Die für die Anwendung benötigten Datenbank- und Benutzerinformationen sind in der Datei application.properties festgelegt:
   - Pfad: "Bachelor/src/main/resources/application.properties"

2. Test-Konfiguration:
   Für Testzwecke wird eine separate Konfigurationsdatei verwendet:
   - Pfad: "Bachelor/src/test/resources/application-test.properties"

3. Access Control Matrix:
   Die Access Control Matrix, die für die Tests verwendet wird, befindet sich unter:
   - Pfad: "Bachelor/src/test/resources/TestSuite.csv"

Anwendung starten

Mit Maven Wrapper (empfohlen)

- Auf Linux oder Mac:
  Wechsle im Terminal in das Projektverzeichnis und führe folgenden Befehl aus:

  ./mvnw spring-boot:run

- Auf Windows:
  Nutze den folgenden Befehl im Terminal:

  mvnw spring-boot:run

Ohne Maven Wrapper

Falls der Maven Wrapper (mvnw) nicht verwendet wird und Maven global installiert ist, kann die Anwendung mit folgendem Befehl gestartet werden:

mvn spring-boot:run                                         
