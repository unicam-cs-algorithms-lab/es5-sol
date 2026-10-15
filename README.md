# Esercitazione 5 — Collections: gestione delle aule e delle prenotazioni

Questa è l’ultima esercitazione del modulo introduttivo di Java del Laboratorio di Algoritmi e Strutture Dati. Riprende il sistema di gestione delle aule dell’**esercitazione 4** e lo evolve usando le **Collections del Java Collections Framework**, come nelle slide *Collections e Generics in Java*.

## Il problema e la progressione didattica

Nell’esercitazione 4 le dotazioni e le prenotazioni erano conservate in array: era necessario distinguere capacità e numero di elementi, ridimensionare gli array, cercare e compattare manualmente. In questa esercitazione la rappresentazione cambia:

- `Set<Facility>` (implementato con `HashSet<Facility>`) mantiene le dotazioni senza duplicati secondo l’uguaglianza logica.
- `SortedSet<Prenotazione>` (implementato con `TreeSet<Prenotazione>`) mantiene le prenotazioni ordinate naturalmente per intervallo temporale e, a parità di intervallo, per aula.
- `GestoreAule` usa un `Set<Aula>` per gestire più aule e selezionare quelle disponibili che soddisfano le dotazioni richieste.

I tipi delle variabili sono **interfacce** (`Set`, `SortedSet`), mentre `HashSet` e `TreeSet` sono **implementazioni concrete**. Le parentesi angolari indicano gli **argomenti di tipo** dei generics, controllati dal compilatore.

## Obiettivi di apprendimento

L’esercitazione permette di applicare i concetti delle slide:

1. scegliere una Collection coerente con i requisiti dell’API, distinguendo `Set` da `SortedSet`;
2. usare `add`, `remove`, `isEmpty` e l’iterazione senza gestire manualmente array e capacità;
3. capire il ruolo di `equals` e `hashCode` nell’unicità degli elementi di un `HashSet`;
4. capire come `TreeSet` usa `compareTo` per ordinare gli elementi e determinare l’equivalenza;
5. sfruttare l’ordinamento per **interrompere anticipatamente** una ricerca o una cancellazione;
6. usare `Iterator.remove()` per cancellare elementi durante la scansione senza invalidare l’iteratore;
7. distinguere la relazione `Facility.satisfies(...)` dall’uguaglianza logica: `Set.containsAll(...)` non la sostituisce;
8. ragionare su riferimenti, aliasing e incapsulamento quando un metodo restituisce una Collection modificabile.

## Classi del progetto

`Facility`, `PresenceFacility`, `QuantitativeFacility`, `TimeSlot` e `Prenotazione` sono già implementate e riprendono i contratti dell’esercitazione precedente. **Non è richiesto modificarle.** Le classi su cui lavorare sono:

- **`Aula`**: adattare costruttori e metodi alla rappresentazione mediante `Set` e `SortedSet`; implementare i TODO, compresi `isFree`, `removePrenotazione` e `removePrenotazioniBefore`.
- **`GestoreAule`**: completare i TODO per aggiungere aule e cercare quelle libere con le dotazioni richieste.

Le firme e le Javadoc descrivono il **contratto** di ogni metodo: parametri ammessi, valori restituiti, eccezioni e comportamento osservabile. Le scelte implementative devono rispettarlo anche nei casi limite.

## Suggerimenti per affrontare i TODO

Leggere prima `Aula`, `Prenotazione.compareTo` e `TimeSlot.overlapsWith`. In `Aula.isFree`, l’ordine di `TreeSet` permette di fermarsi quando le prenotazioni successive non possono più sovrapporsi alla richiesta. In `removePrenotazioniBefore`, rimuovere tramite l’iteratore soltanto le prenotazioni che iniziano entro il punto indicato e restituire `true` se **almeno una** è stata cancellata. Per `satisfiesFacilities`, cercare per ogni dotazione richiesta una dotazione che la soddisfi: non è un semplice controllo di appartenenza al Set.

Le Collection restituite da alcuni getter sono riferimenti alle strutture interne, secondo l’API di questo esercizio. È un limite di incapsulamento da riconoscere: non modificarle direttamente nei test, salvo quando il test riguarda esplicitamente tale comportamento.

## Compilazione e autovalutazione

Il progetto è Maven e richiede **Java 8**. Da IntelliJ IDEA eseguire i test JUnit 5 oppure, dalla radice del progetto:

```bash
mvn test
```

I test pubblici sono parte del materiale didattico: leggere i commenti, capire perché ogni asserzione verifica il contratto e aggiungere prove personali per insiemi vuoti, duplicati, intervalli sovrapposti e rimozioni al confine temporale. Nella versione con i TODO è normale che alcuni test non passino finché i metodi non vengono completati.

## Consegna

La consegna avviene **nel compito corrispondente sul Moodle del corso**, entro la scadenza indicata lì. Caricare esclusivamente i **due file Java `Aula.java` e `GestoreAule.java`** con tutti i TODO implementati. Non è necessario consegnare il progetto Maven, i test o le classi già fornite. L’esercitazione è uno strumento di pratica e autovalutazione e non comporta bonus o malus sul voto dell’esame di laboratorio.
