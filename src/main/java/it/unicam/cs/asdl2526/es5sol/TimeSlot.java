package it.unicam.cs.asdl2526.es5sol;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Rappresenta un intervallo di tempo continuo delimitato da un istante di
 * inizio e da un istante di fine.
 * <p>
 * Gli oggetti di questa classe sono immutabili: dopo la costruzione gli istanti
 * di inizio e fine non possono cambiare. Poiché {@link GregorianCalendar} è
 * invece una classe mutabile, l'implementazione usa copie difensive per evitare
 * che una modifica effettuata dall'esterno possa alterare lo stato del time
 * slot.
 * <p>
 * L'esercizio usa questa classe per applicare in modo coordinato i concetti di
 * uguaglianza logica, codice hash e ordinamento naturale. Due time slot sono
 * uguali quando hanno esattamente gli stessi istanti di inizio e fine, cioè gli
 * stessi valori temporali espressi in millisecondi; il codice hash usa gli
 * stessi dati e {@link #compareTo(TimeSlot)} restituisce zero se e solo se i
 * due oggetti sono uguali.
 * <p>
 * Non sono ammessi time slot di durata nulla o negativa: l'istante di inizio
 * deve precedere strettamente quello di fine.
 *
 * @author Luca Tesei
 */
public class TimeSlot implements Comparable<TimeSlot> {

    /**
     * Soglia di tolleranza, espressa in minuti, usata da
     * {@link #overlapsWith(TimeSlot)}. Due time slot sono considerati
     * sovrapposti solo se la loro sovrapposizione è strettamente maggiore di
     * questa soglia.
     */
    public static final int MINUTES_OF_TOLERANCE_FOR_OVERLAPPING = 5;

    private final GregorianCalendar start;

    private final GregorianCalendar stop;

    /**
     * Costruisce un time slot compreso tra gli istanti {@code start} e
     * {@code stop}.
     * <p>
     * Per garantire l'immutabilità vengono memorizzate copie degli oggetti
     * {@link GregorianCalendar} ricevuti e non i riferimenti originali.
     *
     * @param start l'istante iniziale del time slot
     * @param stop l'istante finale del time slot
     * @throws NullPointerException se {@code start} o {@code stop} è
     *         {@code null}
     * @throws IllegalArgumentException se {@code start} è uguale o successivo
     *         a {@code stop}
     */
    public TimeSlot(GregorianCalendar start, GregorianCalendar stop) {
        if (start == null)
            throw new NullPointerException(
                    "Tentativo di creare un time slot con inizio nullo");
        if (stop == null)
            throw new NullPointerException(
                    "Tentativo di creare un time slot con fine nulla");
        if (start.compareTo(stop) >= 0)
            throw new IllegalArgumentException(
                    "Tentativo di creare un time slot con inizio maggiore o uguale di fine");

        /*
         * Copie difensive: GregorianCalendar è mutabile. Se conservassimo i
         * riferimenti ricevuti, il chiamante potrebbe modificare dall'esterno
         * lo stato di un TimeSlot già costruito.
         */
        this.start = (GregorianCalendar) start.clone();
        this.stop = (GregorianCalendar) stop.clone();
    }

    /**
     * Restituisce l'istante iniziale del time slot.
     * <p>
     * Viene restituita una copia difensiva: modificare il calendario ottenuto
     * non modifica lo stato di questo oggetto.
     *
     * @return una copia dell'istante iniziale
     */
    public GregorianCalendar getStart() {
        return (GregorianCalendar) start.clone();
    }

    /**
     * Restituisce l'istante finale del time slot.
     * <p>
     * Viene restituita una copia difensiva: modificare il calendario ottenuto
     * non modifica lo stato di questo oggetto.
     *
     * @return una copia dell'istante finale
     */
    public GregorianCalendar getStop() {
        return (GregorianCalendar) stop.clone();
    }

    /**
     * Verifica l'uguaglianza logica tra questo time slot e un altro oggetto.
     * Due time slot sono uguali se e solo se rappresentano esattamente lo stesso
     * intervallo, cioè gli istanti di inizio e fine rappresentano
     * rispettivamente gli stessi valori temporali in millisecondi.
     *
     * @param obj l'oggetto con cui confrontare questo time slot
     * @return {@code true} se {@code obj} è un time slot con gli stessi istanti
     *         di inizio e fine, {@code false} altrimenti
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof TimeSlot))
            return false;
        TimeSlot other = (TimeSlot) obj;

        /*
         * L'identità logica dipende dai valori temporali, non dall'identità
         * degli oggetti GregorianCalendar né da altre loro proprietà interne.
         */
        return this.start.getTimeInMillis() == other.start.getTimeInMillis()
                && this.stop.getTimeInMillis() == other.stop.getTimeInMillis();
    }

    /**
     * Restituisce un codice hash coerente con {@link #equals(Object)}.
     * <p>
     * Poiché l'uguaglianza dipende dai valori temporali degli istanti di inizio
     * e fine, anche il codice hash viene calcolato a partire dagli stessi due
     * valori. Oggetti uguali secondo {@code equals} producono quindi sempre lo
     * stesso codice hash.
     *
     * @return il codice hash di questo time slot
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;

        /*
         * getTimeInMillis() restituisce un long: lo ripieghiamo in un int
         * combinando con XOR i 32 bit alti e i 32 bit bassi.
         */
        temp = start.getTimeInMillis();
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = stop.getTimeInMillis();
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     * Confronta questo time slot con un altro secondo l'ordinamento naturale
     * della classe. Un time slot precede un altro se inizia prima; a parità di
     * istante iniziale, precede quello che termina prima.
     * <p>
     * L'ordinamento è compatibile con {@link #equals(Object)}: il metodo
     * restituisce zero se e solo se i due time slot hanno lo stesso inizio e la
     * stessa fine.
     *
     * @param o il time slot con cui effettuare il confronto
     * @return un valore negativo se questo time slot precede {@code o}, zero se
     *         i due time slot sono uguali, un valore positivo se questo time
     *         slot segue {@code o}
     * @throws NullPointerException se {@code o} è {@code null}
     */
    @Override
    public int compareTo(TimeSlot o) {
        if (o == null)
            throw new NullPointerException(
                    "Tentativo di comparare un time slot nullo");

        /*
         * Ordinamento lessicografico: prima confrontiamo l'inizio; soltanto a
         * parità di inizio passiamo alla fine. Usiamo gli stessi valori
         * temporali scelti da equals, così compareTo restituisce 0 se e solo se
         * equals restituisce true.
         */
        int cmp = Long.compare(this.start.getTimeInMillis(),
                o.start.getTimeInMillis());
        if (cmp != 0)
            return cmp;
        return Long.compare(this.stop.getTimeInMillis(),
                o.stop.getTimeInMillis());
    }

    /**
     * Determina la durata in minuti dell'intersezione tra questo time slot e
     * quello passato.
     * <p>
     * Se i due intervalli non hanno istanti in comune, oppure si toccano
     * soltanto in un estremo, il metodo restituisce {@code -1}. Una
     * sovrapposizione positiva inferiore a un minuto produce invece
     * {@code 0}. Se la durata dell'intersezione non è un numero esatto di
     * minuti, vengono ignorati secondi e millisecondi residui: il risultato è
     * quindi arrotondato per difetto.
     * <p>
     * Il risultato è simmetrico: per ogni coppia di time slot {@code a} e
     * {@code b}, {@code a.getMinutesOfOverlappingWith(b)} coincide con
     * {@code b.getMinutesOfOverlappingWith(a)}.
     *
     * @param o il time slot da confrontare con questo
     * @return i minuti interi di sovrapposizione, oppure {@code -1} se non
     *         esiste una sovrapposizione di durata positiva
     * @throws NullPointerException se {@code o} è {@code null}
     * @throws IllegalArgumentException se il numero di minuti di
     *         sovrapposizione supera {@link Integer#MAX_VALUE}
     */
    public int getMinutesOfOverlappingWith(TimeSlot o) {
        if (o == null)
            throw new NullPointerException(
                    "Tentativo di calcolare i minuti di sovrapposizione con un time slot nullo");

        /*
         * L'intersezione di due intervalli [a,b] e [c,d] inizia nel maggiore
         * dei due estremi iniziali e termina nel minore dei due estremi finali.
         * Questo evita di distinguere manualmente tutti i possibili casi di
         * sovrapposizione.
         */
        long overlapStart = Math.max(this.start.getTimeInMillis(),
                o.start.getTimeInMillis());
        long overlapStop = Math.min(this.stop.getTimeInMillis(),
                o.stop.getTimeInMillis());

        if (overlapStop <= overlapStart)
            // Intervalli separati oppure coincidenti soltanto in un estremo.
            return -1;

        long overlappingMilliseconds = overlapStop - overlapStart;
        long truncatedOverlappingMinutes = overlappingMilliseconds / 60000;

        /*
         * La divisione intera tronca secondi e millisecondi residui. Una
         * sovrapposizione positiva inferiore a un minuto produce quindi 0.
         */
        if (truncatedOverlappingMinutes > Integer.MAX_VALUE)
            throw new IllegalArgumentException(
                    "Numero di minuti di sovrapposizione troppo grande per un int");

        return (int) truncatedOverlappingMinutes;
    }

    /**
     * Determina se questo time slot è sovrapposto a un altro oltre la soglia di
     * tolleranza definita da {@link #MINUTES_OF_TOLERANCE_FOR_OVERLAPPING}.
     * <p>
     * La soglia è stretta: una sovrapposizione di esattamente 5 minuti non è
     * sufficiente, mentre una sovrapposizione di 6 minuti è considerata tale.
     * Il metodo riusa il servizio offerto da
     * {@link #getMinutesOfOverlappingWith(TimeSlot)}.
     *
     * @param o il time slot con cui verificare la sovrapposizione
     * @return {@code true} se la sovrapposizione è strettamente maggiore della
     *         soglia di tolleranza, {@code false} altrimenti
     * @throws NullPointerException se {@code o} è {@code null}
     */
    public boolean overlapsWith(TimeSlot o) {
        /*
         * Riutilizziamo un metodo già definito nell'API invece di duplicare la
         * logica di calcolo della sovrapposizione.
         */
        int minutes = this.getMinutesOfOverlappingWith(o);
        return minutes > MINUTES_OF_TOLERANCE_FOR_OVERLAPPING;
    }

    /**
     * Restituisce una rappresentazione testuale del time slot nel formato
     * {@code [giorno/mese/anno ora.minuti - giorno/mese/anno ora.minuti]}.
     * Secondi e millisecondi non vengono rappresentati.
     * <p>
     * Esempi:
     * <ul>
     * <li>{@code [4/11/2019 11.0 - 4/11/2019 13.0]}</li>
     * <li>{@code [10/11/2019 11.15 - 10/11/2019 23.45]}</li>
     * </ul>
     * Il metodo restituisce una stringa e non effettua alcuna stampa sullo
     * standard output.
     *
     * @return la rappresentazione testuale di questo time slot
     */
    @Override
    public String toString() {
        return "[" + start.get(Calendar.DAY_OF_MONTH) + "/"
                + (start.get(Calendar.MONTH) + 1) + "/"
                + start.get(Calendar.YEAR) + " "
                + start.get(Calendar.HOUR_OF_DAY) + "."
                + start.get(Calendar.MINUTE) + " - "
                + stop.get(Calendar.DAY_OF_MONTH) + "/"
                + (stop.get(Calendar.MONTH) + 1) + "/" + stop.get(Calendar.YEAR)
                + " " + stop.get(Calendar.HOUR_OF_DAY) + "."
                + stop.get(Calendar.MINUTE) + "]";
    }
}
