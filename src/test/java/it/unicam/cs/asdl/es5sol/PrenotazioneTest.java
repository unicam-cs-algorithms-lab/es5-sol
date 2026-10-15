/**
 *
 */
package it.unicam.cs.asdl.es5sol;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Luca Tesei
 *
 */
class PrenotazioneTest {

    private Aula a;

    /**
     * @throws Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        a = new Aula("LA1", "Polo Lodovici");
    }

    /**
     * Test method for
     * {@link Prenotazione#hashCode()}.
     */
    // Test del contratto pubblico e dei suoi casi limite.
    @Test
    final void testHashCode() {
        GregorianCalendar g1 = new GregorianCalendar(2019, 10, 4, 11, 00);
        GregorianCalendar g2 = new GregorianCalendar(2019, 10, 4, 13, 00);
        TimeSlot ts1 = new TimeSlot(g1, g2);
        Prenotazione p1 = new Prenotazione(a, ts1, "Luca Tesei",
                "Lezione ASDL");
        GregorianCalendar g3 = new GregorianCalendar(2019, 10, 4, 11, 00);
        GregorianCalendar g4 = new GregorianCalendar(2019, 10, 4, 13, 00);
        TimeSlot ts2 = new TimeSlot(g3, g4);
        Prenotazione p2 = new Prenotazione(new Aula("LA1", "Polo Lodovici"),
                ts2, "", "");
        assertTrue(p1.equals(p2));
        assertTrue(p2.equals(p1));
        assertTrue(p1.hashCode() == p2.hashCode());
    }

    /**
     * Test method for
     * {@link Prenotazione#Prenotazione(Aula, TimeSlot, String, String)}.
     */
    // Test del contratto pubblico e dei suoi casi limite.
    @Test
    final void testPrenotazione() {
        GregorianCalendar g1 = new GregorianCalendar(2019, 10, 4, 11, 00);
        GregorianCalendar g2 = new GregorianCalendar(2019, 10, 4, 13, 00);
        TimeSlot ts1 = new TimeSlot(g1, g2);
        // Testa se il controllo di null è corretto
        assertThrows(NullPointerException.class,
                () -> new Prenotazione(a, ts1, "Luca Tesei", null));
        assertThrows(NullPointerException.class,
                () -> new Prenotazione(a, ts1, null, "Lezione ASDL"));
        assertThrows(NullPointerException.class,
                () -> new Prenotazione(a, null, "Luca Tesei", "Lezione ASDL"));
        assertThrows(NullPointerException.class, () -> new Prenotazione(null,
                ts1, "Luca Tesei", "Lezione ASDL"));
        assertThrows(NullPointerException.class, () -> new Prenotazione(null,
                null, "Luca Tesei", "Lezione ASDL"));
        assertThrows(NullPointerException.class,
                () -> new Prenotazione(null, null, null, "Lezione ASDL"));
        assertThrows(NullPointerException.class,
                () -> new Prenotazione(null, null, null, null));
    }

    /**
     * Test method for {@link Prenotazione#getAula()}.
     */
    // Test del contratto pubblico e dei suoi casi limite.
    @Test
    final void testGetAula() {
        GregorianCalendar g1 = new GregorianCalendar(2019, 10, 4, 11, 00);
        GregorianCalendar g2 = new GregorianCalendar(2019, 10, 4, 13, 00);
        TimeSlot ts1 = new TimeSlot(g1, g2);
        Prenotazione p1 = new Prenotazione(a, ts1, "Luca Tesei",
                "Lezione ASDL");
        assertEquals(new Aula("LA1","pippo"),p1.getAula());
    }

    /**
     * Test method for
     * {@link Prenotazione#getTimeSlot()}.
     */
    // Test del contratto pubblico e dei suoi casi limite.
    @Test
    final void testGetTimeSlot() {
        GregorianCalendar g1 = new GregorianCalendar(2019, 10, 4, 11, 00);
        GregorianCalendar g2 = new GregorianCalendar(2019, 10, 4, 13, 00);
        TimeSlot ts1 = new TimeSlot(g1, g2);
        Prenotazione p1 = new Prenotazione(a, ts1, "Luca Tesei",
                "Lezione ASDL");
        GregorianCalendar g3 = new GregorianCalendar(2019, 10, 4, 11, 00);
        GregorianCalendar g4 = new GregorianCalendar(2019, 10, 4, 13, 00);
        TimeSlot ts2 = new TimeSlot(g3, g4);
        assertEquals(ts2, p1.getTimeSlot());
    }

    /**
     * Test method for
     * {@link Prenotazione#getDocente()}.
     */
    // Test del contratto pubblico e dei suoi casi limite.
    @Test
    final void testGetDocente() {
        GregorianCalendar g1 = new GregorianCalendar(2019, 10, 4, 11, 00);
        GregorianCalendar g2 = new GregorianCalendar(2019, 10, 4, 13, 00);
        TimeSlot ts1 = new TimeSlot(g1, g2);
        Prenotazione p1 = new Prenotazione(a, ts1, "Luca Tesei",
                "Lezione ASDL");
        assertEquals("Luca Tesei", p1.getDocente());
    }

    /**
     * Test method for
     * {@link Prenotazione#getMotivo()}.
     */
    // Test del contratto pubblico e dei suoi casi limite.
    @Test
    final void testGetMotivo() {
        GregorianCalendar g1 = new GregorianCalendar(2019, 10, 4, 11, 00);
        GregorianCalendar g2 = new GregorianCalendar(2019, 10, 4, 13, 00);
        TimeSlot ts1 = new TimeSlot(g1, g2);
        Prenotazione p1 = new Prenotazione(a, ts1, "Luca Tesei",
                "Lezione ASDL");
        assertEquals("Lezione ASDL", p1.getMotivo());
    }

    /**
     * Test method for
     * {@link Prenotazione#equals(Object)}.
     */
    @SuppressWarnings("unlikely-arg-type")
    // Test del contratto pubblico e dei suoi casi limite.
    @Test
    final void testEqualsObject() {
        GregorianCalendar g1 = new GregorianCalendar(2019, 10, 4, 11, 00);
        GregorianCalendar g2 = new GregorianCalendar(2019, 10, 4, 13, 00);
        TimeSlot ts1 = new TimeSlot(g1, g2);
        Prenotazione p1 = new Prenotazione(a, ts1, "Luca Tesei",
                "Lezione ASDL");
        GregorianCalendar g3 = new GregorianCalendar(2019, 10, 4, 11, 00);
        GregorianCalendar g4 = new GregorianCalendar(2019, 10, 4, 13, 00);
        TimeSlot ts2 = new TimeSlot(g3, g4);
        Prenotazione p2 = new Prenotazione(new Aula("LA1", "Polo Lodovici"),
                ts2, "", "");
        assertTrue(p1.equals(p2));
        assertTrue(p2.equals(p1));
        GregorianCalendar g5 = new GregorianCalendar(2019, 10, 4, 14, 00);
        GregorianCalendar g6 = new GregorianCalendar(2019, 10, 4, 16, 00);
        TimeSlot ts3 = new TimeSlot(g5, g6);
        Prenotazione p3 = new Prenotazione(a, ts3, " ", "");
        assertFalse(p1.equals(p3));
        assertFalse(p2.equals(p3));
        assertFalse(p3.equals(p1));
        assertFalse(p3.equals(p2));
        Prenotazione p4 = new Prenotazione(new Aula("AA1", "Polo Lodovici"),
                ts1, " ", "");
        assertFalse(p1.equals(p4));
        assertFalse(p4.equals(p1));
        // Classe diversa
        assertFalse(p1.equals("Pippo"));
        // Null
        assertFalse(p1.equals(null));
    }

    /**
     * Verifica l'ordinamento naturale delle prenotazioni.
     *
     * <p>
     * Il contratto di compareTo prevede un ordinamento lessicografico:
     * prima si confrontano i TimeSlot e, a parità di intervallo,
     * si confrontano i nomi delle aule.
     *
     * <p>
     * Il test verifica anche la compatibilità tra compareTo ed equals:
     * due prenotazioni logicamente uguali devono avere compareTo == 0.
     *
     * <p>
     * I TimeSlot sono immutabili: per rappresentare un intervallo diverso
     * occorre costruire un nuovo oggetto, anziché modificare i calendari
     * utilizzati nella costruzione di un TimeSlot esistente.
     */
    @Test
    final void testCompareTo() {

        // Due aule ordinate lessicograficamente per nome.
        Aula aulaA = new Aula("A1", "Aula A");
        Aula aulaM = new Aula("M1", "Aula M");

        // Primo intervallo: 4 novembre 2019, dalle 11:00 alle 13:00.
        GregorianCalendar g1 =
                new GregorianCalendar(2019, 10, 4, 11, 0);
        GregorianCalendar g2 =
                new GregorianCalendar(2019, 10, 4, 13, 0);

        TimeSlot ts1 = new TimeSlot(g1, g2);

        // Secondo intervallo: dalle 10:00 alle 13:00.
        GregorianCalendar g3 =
                new GregorianCalendar(2019, 10, 4, 10, 0);
        GregorianCalendar g4 =
                new GregorianCalendar(2019, 10, 4, 13, 0);

        TimeSlot ts2 = new TimeSlot(g3, g4);

        Prenotazione p1 =
                new Prenotazione(aulaA, ts1, "AA", "BB");

        Prenotazione p2 =
                new Prenotazione(aulaM, ts2, "CC", "DD");

        /*
         * CASO 1: intervalli con istanti iniziali diversi.
         *
         * ts2 inizia alle 10:00, mentre ts1 inizia alle 11:00.
         * Il confronto delle aule non viene considerato perché
         * il TimeSlot è il primo criterio di ordinamento.
         */
        assertTrue(p1.compareTo(p2) > 0);
        assertTrue(p2.compareTo(p1) < 0);

        /*
         * CASO 2: intervalli uguali, aule diverse.
         *
         * Creiamo un nuovo TimeSlot con gli stessi estremi di ts1.
         * Non modifichiamo ts2, che deve rimanere immutabile.
         */
        TimeSlot ts3 = new TimeSlot(
                new GregorianCalendar(2019, 10, 4, 11, 0),
                new GregorianCalendar(2019, 10, 4, 13, 0));

        Prenotazione p3 =
                new Prenotazione(aulaM, ts3, "CC", "DD");

        // A parità di TimeSlot, "A1" precede "M1".
        assertTrue(p1.compareTo(p3) < 0);
        assertTrue(p3.compareTo(p1) > 0);

        /*
         * CASO 3: prenotazioni logicamente uguali.
         *
         * Aula e TimeSlot coincidono, mentre docente e motivo
         * sono diversi.
         *
         * Questi ultimi due campi non partecipano all'identità
         * logica della prenotazione.
         */
        Prenotazione p4 =
                new Prenotazione(aulaA, ts3, "XX", "YY");

        assertEquals(p1, p4);
        assertEquals(0, p1.compareTo(p4));
        assertEquals(0, p4.compareTo(p1));

        /*
         * CASO 4: la seconda prenotazione inizia dopo la prima.
         *
         * Costruiamo un nuovo intervallo dalle 12:00 alle 13:00.
         */
        TimeSlot ts4 = new TimeSlot(
                new GregorianCalendar(2019, 10, 4, 12, 0),
                new GregorianCalendar(2019, 10, 4, 13, 0));

        Prenotazione p5 =
                new Prenotazione(aulaM, ts4, "CC", "DD");

        assertTrue(p1.compareTo(p5) < 0);
        assertTrue(p5.compareTo(p1) > 0);

        /*
         * CASO 5: intervalli con stesso inizio ma fine diversa.
         *
         * ts5 inizia alle 11:00 come ts1, ma termina alle 14:00.
         * L'ordinamento dei TimeSlot considera prima l'inizio
         * e poi la fine.
         */
        TimeSlot ts5 = new TimeSlot(
                new GregorianCalendar(2019, 10, 4, 11, 0),
                new GregorianCalendar(2019, 10, 4, 14, 0));

        Prenotazione p6 =
                new Prenotazione(aulaM, ts5, "CC", "DD");

        assertTrue(p1.compareTo(p6) < 0);
        assertTrue(p6.compareTo(p1) > 0);

        /*
         * CASO 6: riflessività del confronto.
         *
         * Un oggetto deve essere uguale a sé stesso
         * anche secondo l'ordinamento naturale.
         */
        assertEquals(0, p1.compareTo(p1));
    }

    /**
     * Verifica che modificare i calendari originali non alteri
     * l'ordinamento delle prenotazioni.
     *
     * <p>
     * Questo test collega due concetti:
     * - copie difensive e immutabilità;
     * - stabilità dell'ordinamento naturale.
     */
    @Test
    final void testCompareToNotAffectedByCalendarMutation() {

        Aula aula = new Aula("A1", "Aula A");

        GregorianCalendar start1 =
                new GregorianCalendar(2019, 10, 4, 10, 0);
        GregorianCalendar stop1 =
                new GregorianCalendar(2019, 10, 4, 12, 0);

        GregorianCalendar start2 =
                new GregorianCalendar(2019, 10, 4, 11, 0);
        GregorianCalendar stop2 =
                new GregorianCalendar(2019, 10, 4, 13, 0);

        Prenotazione p1 = new Prenotazione(
                aula,
                new TimeSlot(start1, stop1),
                "AA",
                "BB");

        Prenotazione p2 = new Prenotazione(
                aula,
                new TimeSlot(start2, stop2),
                "CC",
                "DD");

        // p1 precede p2 perché inizia prima.
        assertTrue(p1.compareTo(p2) < 0);

        /*
         * Spostiamo il calendario originale di p1
         * a un orario successivo a quello di p2.
         */
        start1.add(Calendar.HOUR_OF_DAY, 5);
        stop1.add(Calendar.HOUR_OF_DAY, 5);

        /*
         * L'ordinamento deve rimanere invariato.
         *
         * Se TimeSlot non avesse creato copie difensive,
         * p1 inizierebbe ora dopo p2 e il test fallirebbe.
         */
        assertTrue(p1.compareTo(p2) < 0);
    }

}
