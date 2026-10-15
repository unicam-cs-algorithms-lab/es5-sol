package it.unicam.cs.asdl.es5sol;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;

/**
 * Test JUnit per {@link TimeSlot}.
 * <p>
 * Oltre alle funzionalità sui periodi temporali, i test verificano i concetti
 * centrali dell'esercitazione: immutabilità, uguaglianza logica, coerenza di
 * {@code hashCode} e compatibilità tra {@code compareTo} ed {@code equals}.
 *
 * @author Luca Tesei
 */
class TimeSlotTest {

    private static GregorianCalendar date(int year, int month, int day,
                                          int hour, int minute) {
        return new GregorianCalendar(year, month, day, hour, minute);
    }

    @Test
    void constructorRejectsNullAndInvalidIntervals() {
        GregorianCalendar now = date(2019, 10, 4, 12, 0);

        assertThrows(NullPointerException.class, () -> new TimeSlot(null, now));
        assertThrows(NullPointerException.class, () -> new TimeSlot(now, null));
        assertThrows(NullPointerException.class, () -> new TimeSlot(null, null));

        // L'inizio deve precedere strettamente la fine.
        assertThrows(IllegalArgumentException.class,
                () -> new TimeSlot(date(2019, 10, 4, 12, 1),
                        date(2019, 10, 4, 12, 0)));
        assertThrows(IllegalArgumentException.class,
                () -> new TimeSlot(date(2019, 10, 4, 12, 0),
                        date(2019, 10, 4, 12, 0)));
    }

    @Test
    void constructorUsesDefensiveCopies() {
        GregorianCalendar start = date(2019, 10, 4, 11, 0);
        GregorianCalendar stop = date(2019, 10, 4, 13, 0);
        TimeSlot ts = new TimeSlot(start, stop);

        // GregorianCalendar è mutabile: modificare gli oggetti passati al costruttore
        // non deve modificare lo stato interno di un TimeSlot immutabile.
        start.add(Calendar.HOUR_OF_DAY, 1);
        stop.add(Calendar.HOUR_OF_DAY, 1);

        assertEquals(date(2019, 10, 4, 11, 0), ts.getStart());
        assertEquals(date(2019, 10, 4, 13, 0), ts.getStop());
    }

    @Test
    void gettersReturnDefensiveCopies() {
        TimeSlot ts = new TimeSlot(date(2019, 10, 4, 11, 0),
                date(2019, 10, 4, 13, 0));

        GregorianCalendar returnedStart = ts.getStart();
        GregorianCalendar returnedStop = ts.getStop();
        returnedStart.add(Calendar.DAY_OF_MONTH, 1);
        returnedStop.add(Calendar.DAY_OF_MONTH, 1);

        // Anche modificando ciò che restituiscono i getter, il time slot non cambia.
        assertEquals(date(2019, 10, 4, 11, 0), ts.getStart());
        assertEquals(date(2019, 10, 4, 13, 0), ts.getStop());
    }

    /**
     * Verifica che TimeSlot utilizzi correttamente le copie difensive.
     *
     * <p>
     * GregorianCalendar è una classe mutabile: il suo stato può
     * essere modificato dopo la costruzione.
     *
     * <p>
     * TimeSlot deve invece essere immutabile. Per questo motivo
     * deve creare copie indipendenti dei calendari ricevuti
     * nel costruttore e restituire copie nei getter.
     *
     * <p>
     * Il test verifica l'assenza di aliasing sia in ingresso
     * sia in uscita dall'oggetto.
     */
    @Test
    final void testDefensiveCopies() {

        GregorianCalendar start =
                new GregorianCalendar(2019, 10, 4, 10, 0);

        GregorianCalendar stop =
                new GregorianCalendar(2019, 10, 4, 12, 0);

        TimeSlot ts = new TimeSlot(start, stop);

        /*
         * Conserviamo gli istanti iniziali.
         *
         * getTimeInMillis restituisce un valore primitivo long,
         * non un riferimento a un oggetto mutabile.
         */
        long originalStart = ts.getStart().getTimeInMillis();
        long originalStop = ts.getStop().getTimeInMillis();

        /*
         * PRIMA VERIFICA: copia difensiva nel costruttore.
         *
         * Modifichiamo gli oggetti GregorianCalendar originali.
         * Se TimeSlot avesse conservato direttamente i riferimenti,
         * il suo stato cambierebbe.
         */
        start.add(Calendar.HOUR_OF_DAY, 1);
        stop.add(Calendar.HOUR_OF_DAY, 1);

        // Gli estremi del TimeSlot devono essere rimasti invariati.
        assertEquals(originalStart,
                ts.getStart().getTimeInMillis());

        assertEquals(originalStop,
                ts.getStop().getTimeInMillis());

        /*
         * SECONDA VERIFICA: copia difensiva nei getter.
         *
         * Otteniamo un calendario dal TimeSlot e lo modifichiamo.
         * Anche questa modifica non deve alterare lo stato interno.
         */
        GregorianCalendar exposedStart = ts.getStart();
        GregorianCalendar exposedStop = ts.getStop();

        exposedStart.add(Calendar.HOUR_OF_DAY, 2);
        exposedStop.add(Calendar.HOUR_OF_DAY, 2);

        assertEquals(originalStart,
                ts.getStart().getTimeInMillis());

        assertEquals(originalStop,
                ts.getStop().getTimeInMillis());

        /*
         * TERZA VERIFICA: i getter restituiscono oggetti distinti.
         *
         * Non basta che il contenuto sia uguale: vogliamo verificare
         * che non venga restituito il riferimento interno.
         */
        assertNotSame(start, ts.getStart());
        assertNotSame(stop, ts.getStop());

        /*
         * Due chiamate successive allo stesso getter devono
         * restituire due oggetti distinti, perché ciascuna
         * produce una nuova copia difensiva.
         */
        assertNotSame(ts.getStart(), ts.getStart());
        assertNotSame(ts.getStop(), ts.getStop());
    }

    @Test
    void equalsRecognizesSameLogicalInterval() {
        TimeSlot ts1 = new TimeSlot(date(2019, 10, 4, 11, 0),
                date(2019, 10, 4, 13, 0));
        TimeSlot ts2 = new TimeSlot(date(2019, 10, 4, 11, 0),
                date(2019, 10, 4, 13, 0));

        // Due oggetti distinti nello heap possono rappresentare la stessa entità logica.
        assertNotSame(ts1, ts2);
        assertEquals(ts1, ts2);
        assertEquals(ts2, ts1); // simmetria
        assertEquals(ts1, ts1); // riflessività
    }

    @Test
    void equalsDistinguishesDifferentEndpoints() {
        TimeSlot reference = new TimeSlot(date(2019, 10, 4, 11, 0),
                date(2019, 10, 4, 13, 0));
        TimeSlot differentStart = new TimeSlot(date(2019, 10, 4, 10, 0),
                date(2019, 10, 4, 13, 0));
        TimeSlot differentStop = new TimeSlot(date(2019, 10, 4, 11, 0),
                date(2019, 10, 4, 14, 0));

        assertNotEquals(reference, differentStart);
        assertNotEquals(reference, differentStop);
        assertNotEquals(reference, null);
        assertNotEquals(reference, "un time slot");
    }

    @Test
    void hashCodeIsConsistentWithEquals() {
        TimeSlot ts1 = new TimeSlot(date(2019, 10, 4, 11, 0),
                date(2019, 10, 4, 13, 0));
        TimeSlot ts2 = new TimeSlot(date(2019, 10, 4, 11, 0),
                date(2019, 10, 4, 13, 0));

        assertEquals(ts1, ts2);
        // equals implica uguaglianza degli hash code; il viceversa non è richiesto.
        assertEquals(ts1.hashCode(), ts2.hashCode());
    }

    @Test
    void compareToOrdersByStartFirst() {
        TimeSlot earlier = new TimeSlot(date(2019, 10, 4, 10, 0),
                date(2019, 10, 4, 14, 0));
        TimeSlot later = new TimeSlot(date(2019, 10, 4, 11, 0),
                date(2019, 10, 4, 12, 0));

        // Conta prima l'inizio, anche se il primo intervallo termina più tardi.
        assertTrue(earlier.compareTo(later) < 0);
        assertTrue(later.compareTo(earlier) > 0);
    }

    @Test
    void compareToUsesStopWhenStartsAreEqual() {
        TimeSlot shorter = new TimeSlot(date(2019, 10, 4, 11, 0),
                date(2019, 10, 4, 12, 0));
        TimeSlot longer = new TimeSlot(date(2019, 10, 4, 11, 0),
                date(2019, 10, 4, 13, 0));

        // A parità di inizio, precede il time slot che termina prima.
        assertTrue(shorter.compareTo(longer) < 0);
        assertTrue(longer.compareTo(shorter) > 0);
    }

    @Test
    void compareToIsCompatibleWithEquals() {
        TimeSlot ts1 = new TimeSlot(date(2019, 10, 4, 11, 0),
                date(2019, 10, 4, 13, 0));
        TimeSlot ts2 = new TimeSlot(date(2019, 10, 4, 11, 0),
                date(2019, 10, 4, 13, 0));

        assertEquals(ts1, ts2);
        assertEquals(0, ts1.compareTo(ts2));
        assertEquals(0, ts2.compareTo(ts1));
    }

    @Test
    void compareToRejectsNull() {
        TimeSlot ts = new TimeSlot(date(2019, 10, 4, 11, 0),
                date(2019, 10, 4, 13, 0));
        assertThrows(NullPointerException.class, () -> ts.compareTo(null));
    }

    @Test
    void overlappingReturnsMinusOneForDisjointOrTouchingIntervals() {
        TimeSlot base = new TimeSlot(date(2019, 10, 4, 11, 0),
                date(2019, 10, 4, 13, 0));
        TimeSlot disjoint = new TimeSlot(date(2019, 10, 4, 14, 0),
                date(2019, 10, 4, 16, 0));
        TimeSlot touching = new TimeSlot(date(2019, 10, 4, 13, 0),
                date(2019, 10, 4, 15, 0));

        assertEquals(-1, base.getMinutesOfOverlappingWith(disjoint));
        assertEquals(-1, disjoint.getMinutesOfOverlappingWith(base));
        assertEquals(-1, base.getMinutesOfOverlappingWith(touching));
        assertEquals(-1, touching.getMinutesOfOverlappingWith(base));
    }

    @Test
    void overlappingHandlesPartialAndContainedIntervalsSymmetrically() {
        TimeSlot base = new TimeSlot(date(2019, 10, 4, 11, 0),
                date(2019, 10, 4, 13, 0));
        TimeSlot partialRight = new TimeSlot(date(2019, 10, 4, 12, 30),
                date(2019, 10, 4, 14, 0));
        TimeSlot contained = new TimeSlot(date(2019, 10, 4, 11, 20),
                date(2019, 10, 4, 12, 5));
        TimeSlot containing = new TimeSlot(date(2019, 10, 4, 10, 0),
                date(2019, 10, 4, 14, 0));

        assertEquals(30, base.getMinutesOfOverlappingWith(partialRight));
        assertEquals(30, partialRight.getMinutesOfOverlappingWith(base));
        assertEquals(45, base.getMinutesOfOverlappingWith(contained));
        assertEquals(45, contained.getMinutesOfOverlappingWith(base));
        assertEquals(120, base.getMinutesOfOverlappingWith(containing));
        assertEquals(120, containing.getMinutesOfOverlappingWith(base));
    }

    @Test
    void overlappingRoundsPositiveDurationDownToWholeMinutes() {
        GregorianCalendar start = new GregorianCalendar(2019, 10, 4, 12, 55,
                53);
        GregorianCalendar stop = date(2019, 10, 4, 14, 0);
        TimeSlot base = new TimeSlot(date(2019, 10, 4, 11, 0),
                date(2019, 10, 4, 13, 0));
        TimeSlot other = new TimeSlot(start, stop);

        // La sovrapposizione è 4 minuti e 7 secondi: si restituiscono 4 minuti.
        assertEquals(4, base.getMinutesOfOverlappingWith(other));
        assertEquals(4, other.getMinutesOfOverlappingWith(base));
    }

    @Test
    void overlappingCanBePositiveButLessThanOneMinute() {
        GregorianCalendar aStart = new GregorianCalendar(2019, 10, 4, 11, 0,
                0);
        GregorianCalendar aStop = new GregorianCalendar(2019, 10, 4, 11, 1,
                0);
        GregorianCalendar bStart = new GregorianCalendar(2019, 10, 4, 11, 0,
                30);
        GregorianCalendar bStop = new GregorianCalendar(2019, 10, 4, 11, 2,
                0);
        TimeSlot a = new TimeSlot(aStart, aStop);
        TimeSlot b = new TimeSlot(bStart, bStop);

        // Esistono 30 secondi di intersezione: non è "nessuna sovrapposizione".
        // Dopo l'arrotondamento per difetto il numero di minuti è quindi 0.
        assertEquals(0, a.getMinutesOfOverlappingWith(b));
        assertEquals(0, b.getMinutesOfOverlappingWith(a));
    }

    @Test
    void overlappingRejectsNull() {
        TimeSlot ts = new TimeSlot(date(2019, 10, 4, 11, 0),
                date(2019, 10, 4, 13, 0));
        assertThrows(NullPointerException.class,
                () -> ts.getMinutesOfOverlappingWith(null));
        assertThrows(NullPointerException.class, () -> ts.overlapsWith(null));
    }

    @Test
    void overlapsWithUsesStrictToleranceThreshold() {
        TimeSlot base = new TimeSlot(date(2019, 10, 4, 11, 0),
                date(2019, 10, 4, 13, 0));
        TimeSlot overlap4 = new TimeSlot(date(2019, 10, 4, 12, 56),
                date(2019, 10, 4, 14, 0));
        TimeSlot overlap5 = new TimeSlot(date(2019, 10, 4, 12, 55),
                date(2019, 10, 4, 14, 0));
        TimeSlot overlap6 = new TimeSlot(date(2019, 10, 4, 12, 54),
                date(2019, 10, 4, 14, 0));

        // La soglia è strettamente maggiore di 5: 4 e 5 non bastano, 6 sì.
        assertFalse(base.overlapsWith(overlap4));
        assertFalse(base.overlapsWith(overlap5));
        assertTrue(base.overlapsWith(overlap6));

        // La relazione di sovrapposizione deve essere simmetrica.
        assertFalse(overlap4.overlapsWith(base));
        assertFalse(overlap5.overlapsWith(base));
        assertTrue(overlap6.overlapsWith(base));
    }

    @Test
    void toStringUsesRequiredFormatAndOmitsSeconds() {
        TimeSlot ts1 = new TimeSlot(date(2019, 10, 4, 11, 0),
                date(2019, 10, 4, 13, 0));
        TimeSlot ts2 = new TimeSlot(date(2019, 10, 10, 11, 15),
                date(2019, 10, 10, 23, 45));
        TimeSlot withSeconds = new TimeSlot(
                new GregorianCalendar(2019, 10, 4, 11, 15, 27),
                new GregorianCalendar(2019, 10, 4, 13, 45, 59));

        assertEquals("[4/11/2019 11.0 - 4/11/2019 13.0]", ts1.toString());
        assertEquals("[10/11/2019 11.15 - 10/11/2019 23.45]",
                ts2.toString());
        assertEquals("[4/11/2019 11.15 - 4/11/2019 13.45]",
                withSeconds.toString());
    }
}
