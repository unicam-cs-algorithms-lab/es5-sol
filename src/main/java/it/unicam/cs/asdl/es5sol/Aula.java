package it.unicam.cs.asdl.es5sol;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Rappresenta un’aula identificata univocamente dal nome, con dotazioni e prenotazioni.
 * <p>Le dotazioni sono conservate in un {@link java.util.Set}: non ammettono
 * duplicati secondo {@code equals}/{@code hashCode}. Le prenotazioni sono
 * conservate in un {@link java.util.SortedSet}, ordinato secondo
 * {@link Prenotazione#compareTo(Prenotazione)}.
 * <p>Questa classe riprende il modello dell’esercitazione 4, sostituendo gli
 * array con le Collections studiate nelle slide.
 * <p><strong>Nota sull’incapsulamento:</strong> i getter delle collezioni
 * restituiscono riferimenti agli insiemi interni (non copie difensive);
 * modificandoli si può aggirare la validazione dei metodi dell’aula.
 * 
 * @author Template: Luca Tesei, Implementazione: Collettiva
 *
 */
public class Aula implements Comparable<Aula> {
    // Identificativo unico di un'aula
    private final String nome;

    // Location dell'aula
    private final String location;

    // Insieme delle facilities di quest'aula
    private final Set<Facility> facilities;

    // Insieme delle prenotazioni per quest'aula, segue l'ordinamento naturale
    // delle prenotazioni
    private final SortedSet<Prenotazione> prenotazioni;

    /**
     * Costruisce una certa aula con nome e location. Il set delle facilities è
     * vuoto. L'aula non ha inizialmente nessuna prenotazione.
     * 
     * @param nome
     *                     il nome dell'aula
     * @param location
     *                     la location dell'aula
     * 
     * @throws NullPointerException
     *                                  se una qualsiasi delle informazioni
     *                                  richieste è nulla
     */
    public Aula(String nome, String location) {
        if (nome == null)
            throw new NullPointerException(
                    "Tentativo di costruire un'aula senza nome");
        if (location == null)
            throw new NullPointerException(
                    "Tentativo di costruire un'aula senza location");
        this.location = location;
        this.nome = nome;
        this.prenotazioni = new TreeSet<Prenotazione>();
        this.facilities = new HashSet<Facility>();
    }

    /**
     * Costruisce una certa aula con nome, location e insieme delle facilities.
     * L'aula non ha inizialmente nessuna prenotazione.
     * 
     * @param nome
     *                       il nome dell'aula
     * @param location
     *                       la location dell'aula
     * @param facilities
     *                       l'insieme delle facilities dell'aula
     * @throws NullPointerException
     *                                  se una qualsiasi delle informazioni
     *                                  richieste è nulla
     */
    public Aula(String nome, String location, Set<Facility> facilities) {
        if (nome == null)
            throw new NullPointerException(
                    "Tentativo di costruire un'aula senza nome");
        if (location == null)
            throw new NullPointerException(
                    "Tentativo di costruire un'aula senza location");
        if (facilities == null)
            throw new NullPointerException(
                    "Tentativo di costruire un'aula senza facilities");
        this.nome = nome;
        this.location = location;
        // Si conserva il riferimento al Set ricevuto: scelta dell’API originale.
        // Chi lo modifica dall’esterno modifica anche le dotazioni dell’aula.
        this.facilities = facilities;
        this.prenotazioni = new TreeSet<Prenotazione>();
    }

    /**
     * Calcola l’hash dall’identificativo immutabile dell’aula.
     * <p>Il risultato deve essere coerente con {@link #equals(Object)}
     * affinché l’aula funzioni correttamente in {@link java.util.HashSet}.
     * @return codice hash del nome
     */
    @Override
    public int hashCode() {
        return this.nome.hashCode();
    }

    /**
     * Confronta l’identità logica delle aule: conta soltanto il nome,
     * non la posizione, le dotazioni o le prenotazioni.
     * @param obj oggetto da confrontare
     * @return {@code true} se {@code obj} è un’aula con lo stesso nome
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof Aula) {
            Aula other = (Aula) obj;
            return this.nome.equals(other.nome);
        }
        return false;
    }

    /**
     * Definisce l’ordinamento naturale lessicografico per nome,
     * compatibile con {@link #equals(Object)}.
     * @param o aula da confrontare
     * @return valore negativo, zero o positivo secondo l’ordine dei nomi
     * @throws NullPointerException se {@code o} è {@code null}
     */
    @Override
    public int compareTo(Aula o) {
        return this.nome.compareTo(o.nome);
    }

    /**
     * Restituisce le dotazioni dell’aula.
     * @return l’insieme delle dotazioni dell’aula (riferimento interno modificabile)
     */
    public Set<Facility> getFacilities() {
        return facilities;
    }

    /**
     * Restituisce il nome identificativo dell’aula.
     * @return il nome identificativo dell’aula
     */
    public String getNome() {
        return nome;
    }

    /**
     * Restituisce la posizione dell’aula.
     * @return la posizione dell’aula
     */
    public String getLocation() {
        return location;
    }

    /**
     * Restituisce le prenotazioni ordinate dell’aula.
     * @return l’insieme ordinato delle prenotazioni (riferimento interno modificabile)
     */
    public SortedSet<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    /**
     * Aggiunge una dotazione all’insieme {@code Set<Facility>} dell’aula.
     * <p>Il risultato di {@code Set.add} indica se l’insieme è cambiato.
     * 
     * @param f
     *              la facility da aggiungere
     * @return true se la facility non era già presente e quindi è stata
     *         aggiunta, false altrimenti
     * @throws NullPointerException
     *                                  se la facility passata è nulla
     */
    public boolean addFacility(Facility f) {
        if (f == null)
            throw new NullPointerException(
                    "Tentativo di aggiungere una facility nulla");
        return this.facilities.add(f);
    }

    /**
     * Determina se l’aula è libera nell’intervallo richiesto.
     * <p>Le prenotazioni sono ordinate per intervallo: è possibile interrompere
     * la scansione quando l’inizio della prenotazione corrente è successivo
     * all’intervallo richiesto. L’eventuale sovrapposizione è determinata
     * da {@link TimeSlot#overlapsWith(TimeSlot)}.
     * 
     * 
     * @param ts
     *               il time slot da controllare
     * 
     * @return true se l'aula risulta libera per tutto il periodo del time slot
     *         specificato
     * @throws NullPointerException
     *                                  se il time slot passato è nullo
     */
    public boolean isFree(TimeSlot ts) {
        // NOTA: sfruttare l'ordinamento tra le prenotazioni per
        // rispondere in maniera efficiente
        if (ts == null)
            throw new NullPointerException(
                    "Tentativo di testare se un'aula è libera in un time slot nullo");
        // TreeSet itera secondo compareTo di Prenotazione: posso fermarmi
        // non appena il prossimo intervallo comincia dopo quello richiesto.
        // Scorro l'insieme ordinato delle prenotazioni fino a quando non ne
        // trovo una che ha un time slot maggiore stretto di quello passato
        for (Prenotazione p : this.prenotazioni)
            if (p.getTimeSlot().overlapsWith(ts))
                // l'aula già contiene una prenotazione nel time slot
                // specificato o in una sua parte per un periodo sopra la soglia
                // di tolleranza
                return false;
            else if (p.getTimeSlot().compareTo(ts) > 0)
                // poiché le prenotazioni sono in ordine crescente di time slot
                // se arrivo a una prenotazione che segue il time slot
                // specificato posso concludere che l'aula è libera nel time
                // slot desiderato e posso interrompere il ciclo
                return true;
        // ho terminato le prenotazioni e nessuna si è sovrapposta
        return true;
    }

    /**
     * Determina se ogni dotazione richiesta è soddisfatta da almeno una dotazione
     * presente nell’aula.
     * <p>Non basta {@code containsAll}: il metodo
     * {@link Facility#satisfies(Facility)} esprime una relazione di
     * soddisfacimento che può essere diversa da {@code equals}.
     * 
     * @param requestedFacilities
     *                                l'insieme di facilities richieste da
     *                                soddisfare
     * @return true se e solo se tutte le facilities di
     *         {@code requestedFacilities} sono soddisfatte da questa aula.
     * @throws NullPointerException
     *                                  se il set di facility richieste è nullo
     */
    public boolean satisfiesFacilities(Set<Facility> requestedFacilities) {
        if (requestedFacilities == null)
            throw new NullPointerException(
                    "Richiesta di controllare se un insieme di facility nullo è soddisfatto");
        // satisfies non coincide necessariamente con equals: containsAll
        // non sarebbe sufficiente per le dotazioni quantitative.
        // scorro le facilities richieste
        boolean satisfied;
        for (Facility rf : requestedFacilities) {
            satisfied = false;
            // scorro le facilities di questa aula e controllo se ce n'è una che
            // soddisfa la facility richiesta corrente
            for (Facility f : this.facilities)
                if (f.satisfies(rf)) {
                    // la facility richiesta corrente è stata soddisfata
                    satisfied = true;
                    // esco dal for interno
                    break;
                }
            if (!satisfied)
                // la facility richiesta non è soddisfatta, quindi rispondo
                // false
                return false;
        }
        // tutte le facility richieste sono state soddisfatte, quindi rispondo
        // true
        return true;
    }

    /**
     * Inserisce una prenotazione nell’insieme ordinato solo se l’intervallo è libero.
     * <p>L’ordinamento naturale di {@link Prenotazione} è utilizzato da
     * {@link java.util.TreeSet} per mantenere le prenotazioni ordinate.
     * 
     * @param ts intervallo richiesto
     * @param docente docente responsabile
     * @param motivo motivo della prenotazione
     * @throws IllegalArgumentException
     *                                      se la prenotazione comporta una
     *                                      sovrapposizione con un'altra
     *                                      prenotazione nella stessa aula.
     * @throws NullPointerException
     *                                      se una qualsiasi delle informazioni
     *                                      richieste è nulla.
     */
    public void addPrenotazione(TimeSlot ts, String docente, String motivo) {
        if (ts == null || docente == null || motivo == null)
            throw new NullPointerException(
                    "Tentativo di prenotare un aula con time slot, docente o motivo nullo");
        // Controllo sovrapposizione
        if (!this.isFree(ts))
            throw new IllegalArgumentException(
                    "Tentativo di prenotare un aula in un time slot già occupato");
        // Creo la prenotazione e la aggiungo al set
        this.prenotazioni.add(new Prenotazione(this, ts, docente, motivo));
    }

    /**
     * Rimuove una prenotazione dal {@code SortedSet}.
     * <p>In {@code TreeSet} la ricerca avviene secondo l’ordinamento naturale,
     * che deve essere compatibile con l’uguaglianza logica.
     * 
     * @param p
     *              la prenotazione da cancellare
     * @return true se la prenotazione è stata cancellata, false se non era
     *         presente.
     * @throws NullPointerException
     *                                  se la prenotazione passata è null
     */
    public boolean removePrenotazione(Prenotazione p) {
        if (p == null)
            throw new NullPointerException(
                    "Tentativo di cancellare una prenotazione nulla");
        return this.prenotazioni.remove(p);
    }

    /**
     * Rimuove tutte le prenotazioni il cui istante iniziale è minore o uguale
     * a {@code timePoint}, senza considerare l’istante finale.
     * <p>L’ordinamento consente di interrompere la scansione alla prima
     * prenotazione successiva; per eliminare durante la scansione si usa
     * {@link java.util.Iterator#remove()}, non {@code Set.remove}.
     * 
     * @param timePoint
     *                      un certo punto nel tempo
     * @return true se almeno una prenotazione è stata cancellata, false
     *         altrimenti.
     * @throws NullPointerException
     *                                  se il punto nel tempo passato è nullo.
     */
    public boolean removePrenotazioniBefore(GregorianCalendar timePoint) {
        // NOTA: sfruttare l'ordinamento tra le prenotazioni per
        // eseguire in maniera efficiente
        if (timePoint == null)
            throw new NullPointerException(
                    "Tentativo di rimuovere le prenotazioni che precedono un punto nel tempo nullo");
        // uso un flag per determinare se sono state fatte cancellazioni
        boolean ret = false;
        // scorro le prenotazioni nell'ordine canonico che è in base al tempo di
        // inizio
        // iterator.remove() aggiorna coerentemente lo stato dell’iteratore;
        // chiamare prenotazioni.remove(p) durante la scansione non è equivalente.
        // uso un iteratore perché devo cancellare alcune prenotazioni durante
        // lo scorrimento
        Iterator<Prenotazione> i = this.prenotazioni.iterator();
        while (i.hasNext()) {
            Prenotazione p = i.next();
            int cmp = timePoint.compareTo(p.getTimeSlot().getStart());
            if (cmp >= 0) {
                // la prenotazione è da cancellare
                i.remove();
                ret = true;
            } else // ho raggiunto una prenotazione con tempo di inizio maggiore
                   // del tempo indicato e quindi posso smettere la procedura.
                return ret;
        }
        // Anche quando tutte le prenotazioni sono state rimosse, il risultato
        // deve segnalare che l’insieme è cambiato.
        return ret;
    }
}
