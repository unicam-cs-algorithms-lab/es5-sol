package it.unicam.cs.asdl.es5sol;

import static org.junit.jupiter.api.Assertions.*;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * Test aggiuntivi dei contratti delle Collections usate nell’esercitazione.
 * Ogni caso collega un comportamento osservabile a un concetto delle slide.
 */
class ContrattiCollectionsTest {

    private static TimeSlot slot(int hour) {
        return new TimeSlot(new GregorianCalendar(2026, 9, 15, hour, 0),
                new GregorianCalendar(2026, 9, 15, hour + 1, 0));
    }

    @Test
    void removePrenotazioniBeforeRestituisceTrueAncheSeSvuotaIlSet() {
        // Caso limite: la scansione arriva alla fine dopo aver rimosso tutto.
        // Il risultato deve indicare che la collezione è stata modificata.
        Aula a = new Aula("A1", "Polo");
        a.addPrenotazione(slot(9), "Docente", "Lezione");
        a.addPrenotazione(slot(11), "Docente", "Lezione");
        assertTrue(a.removePrenotazioniBefore(
                new GregorianCalendar(2026, 9, 15, 11, 0)));
        assertTrue(a.getPrenotazioni().isEmpty());
        // Un secondo tentativo non rimuove nulla.
        assertFalse(a.removePrenotazioniBefore(
                new GregorianCalendar(2026, 9, 15, 11, 0)));
    }

    @Test
    void removePrenotazioniBeforeIncludeLaSogliaMaNonIlGiornoSuccessivo() {
        // Il confronto riguarda l'inizio, non la fine, del TimeSlot.
        Aula a = new Aula("A1", "Polo");
        a.addPrenotazione(slot(9), "D", "M");
        a.addPrenotazione(slot(11), "D", "M");
        a.addPrenotazione(slot(13), "D", "M");
        assertTrue(a.removePrenotazioniBefore(
                new GregorianCalendar(2026, 9, 15, 11, 0)));
        assertEquals(1, a.getPrenotazioni().size());
        assertEquals(13, a.getPrenotazioni().first().getTimeSlot()
                .getStart().get(GregorianCalendar.HOUR_OF_DAY));
    }

    @Test
    void prenotazioniSonoOrdinateAncheSeInseriteFuoriOrdine() {
        // TreeSet itera secondo compareTo, non secondo l'ordine di inserimento.
        Aula a = new Aula("A1", "Polo");
        a.addPrenotazione(slot(14), "D", "M");
        a.addPrenotazione(slot(8), "D", "M");
        a.addPrenotazione(slot(11), "D", "M");
        assertEquals(8, a.getPrenotazioni().first().getTimeSlot()
                .getStart().get(GregorianCalendar.HOUR_OF_DAY));
        assertEquals(14, a.getPrenotazioni().last().getTimeSlot()
                .getStart().get(GregorianCalendar.HOUR_OF_DAY));
    }

    @Test
    void setAuleRifiutaDuplicatiLogici() {
        // Due oggetti distinti con lo stesso nome sono la stessa aula per equals.
        GestoreAule g = new GestoreAule();
        assertTrue(g.addAula(new Aula("A1", "Piano terra")));
        assertFalse(g.addAula(new Aula("A1", "Primo piano")));
        assertEquals(1, g.getAule().size());
    }

    @Test
    void ricercaAuleNonEsponeIlSetInterno() {
        // Il risultato della ricerca è una nuova collezione, non un alias.
        GestoreAule g = new GestoreAule();
        Aula a = new Aula("A1", "Polo");
        g.addAula(a);
        Set<Aula> result = g.cercaAuleLibere(new HashSet<Facility>(), slot(10));
        assertEquals(1, result.size());
        result.clear();
        assertEquals(1, g.getAule().size());
    }

    @Test
    void gestoreControllaGliArgomentiNull() {
        // Il contratto distingue insieme vuoto (valido) da null (non valido).
        GestoreAule g = new GestoreAule();
        assertThrows(NullPointerException.class, () -> g.addAula(null));
        assertThrows(NullPointerException.class,
                () -> g.cercaAuleLibere(null, slot(10)));
        assertThrows(NullPointerException.class,
                () -> g.cercaAuleLibere(new HashSet<Facility>(), null));
    }
}
