# Basis

Als Beispiel soll eine Anwendung für die Vergütung von Büchern erstellt werden. Darin will der Sachbearbeiter Bücher erfassen um deren Vergütungswert zu ermitteln.

Es soll eine einfache Anlegemaske für die Bücher und eine einfache Anzeige aller erfassten Bücher geben.

 

**Relevante Attribute Buch**:

* Titel
* ISBN (https://de.wikipedia.org/wiki/Internationale_Standardbuchnummer)
* Erscheinungsjahr
* Anzahl Seiten
* Sprache
* Sachgruppe

 

**Abnahmekriterien**:

* Der Benutzer der Anwendung (genannt Sachbearbeiter) kann Bücher erfassen, indem mindestens Titel und ISBN angegeben wird.
* Es dürfen nur gültige ISBNs erfasst werden und jede ISBN darf nur einmal vorkommen
* Angelegte Bücher werden dem Sachbearbeiter inkl. dem jeweiligen berechneten Vergütungswert angezeigt
 

 

**Regeln Vergütungsberechnung**:

* Der Basisbetrag für die Vergütung pro Buch beträgt 100€.
* Anhand der Seitenanzahl wird ein Faktor ermittelt:
  * < 50 Seiten: 0,7
  * 50 – 99 Seiten: 1
  * 100 – 199: 1,1
  * 200 – 299: 1,2
  * 300 – 499: 1,3
  * Ab 500: 1,5
* Bücher die vor 1990 erschienen sind, erhalten einen Zuschlag von 15€.
* Deutsche Bücher bekommen einen Zuschlag von 10%.
    Vergütungsbetrag = Basisbetrag * Faktor + Zuschläge

 

Die Lösung sollte in unserem Technologiestack erfolgen. Neuere Versionen oder vergleichbare Technologien sind aber auch in Ordnung.

**Technologiestack**:

* Java 17
* Spring Boot 3 / Spring Data
* JUnit 5
* MySQL 8
* Angular 15
* Jest / Cypress
 

Zur besseren Einschätzung soll eine von den möglichen Vertiefungen gewählt werden:

## Vertiefungsoption 1:

Mit Hilfe einer öffentlichen API sollen zusätzliche Informationen zu Büchern abgeholt werden. Das soll regelmäßig im Hintergrund erfolgen und die Daten der angelegten Bücher anreichern.

Dazu kann folgende API verwendet werden: https://openlibrary.org/developers/api. Für das Anreichern interessante Daten sind unter anderem der Verlag, die Seitenanzahl und die Sprache.

 

## Vertiefungsoption 2:

Visualisierung der Aufteilung der Vergütungswerte über alle Bücher (nach Jahren, Sprachen, Sachgruppen, …). Verbesserung der Darstellung für den Benutzer (Filter, Summen, …)
 