package it.unicam.cs.asdl2526.es5sol;

/**
 * Rappresenta la prenotazione di un {@link Aula} per un {@link TimeSlot}.
 * <p>L'identità logica dipende solo da aula e intervallo; docente e motivo
 * possono cambiare senza modificare {@code equals} e {@code hashCode}.
 * L'ordinamento naturale confronta prima gli intervalli, poi le aule.
 */
public class Prenotazione implements Comparable<Prenotazione> {

    private final Aula aula;

    private final TimeSlot timeSlot;

    // Campo modificabile escluso dall'identità logica.
    private String docente;

    private String motivo;

    /**
     * Costruisce una prenotazione con identità fissata da aula e time slot.
     * @param aula aula prenotata
     * @param timeSlot intervallo prenotato
     * @param docente docente responsabile
     * @param motivo motivo della prenotazione
     * @throws NullPointerException se un argomento è null
     */
    public Prenotazione(Aula aula, TimeSlot timeSlot, String docente,
                        String motivo) {
        if (aula == null)
            throw new NullPointerException(
                    "Tentativo di costruire una prenotazione senza aula");
        if (timeSlot == null)
            throw new NullPointerException(
                    "Tentativo di costruire una prenotazione senza time slot");
        if (docente == null)
            throw new NullPointerException(
                    "Tentativo di costruire una prenotazione senza docente");
        if (motivo == null)
            throw new NullPointerException(
                    "Tentativo di costruire una prenotazione senza motivo");
        this.aula = aula;
        this.timeSlot = timeSlot;
        this.docente = docente;
        this.motivo = motivo;
    }

    /**
     * Restituisce l'aula che partecipa all'identità logica.
     * @return aula della prenotazione
     */
    public Aula getAula() {
        return aula;
    }

    /**
     * Restituisce l'intervallo della prenotazione.
     * @return time slot
     */
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    /**
     * Restituisce il docente, attributo modificabile non identitario.
     * @return docente
     */
    public String getDocente() {
        return docente;
    }

    /**
     * Restituisce il motivo, attributo modificabile non identitario.
     * @return motivo
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * Modifica il docente senza cambiare l'identità logica.
     * @param docente nuovo docente; il contratto originale del setter ammette null
     */
    public void setDocente(String docente) {
        this.docente = docente;
    }

    /**
     * Modifica il motivo senza cambiare l'identità logica.
     * @param motivo nuovo motivo; il contratto originale del setter ammette null
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    /**
     * Calcola l'hash usando solo aula e time slot, come equals.
     * @return codice hash della prenotazione
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((aula == null) ? 0 : aula.hashCode());
        result = prime * result
                + ((timeSlot == null) ? 0 : timeSlot.hashCode());
        return result;
    }

    /**
     * Confronta soltanto aula e time slot, non docente e motivo.
     * @param obj oggetto da confrontare
     * @return true se la prenotazione ha la stessa aula e lo stesso intervallo
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Prenotazione))
            return false;
        Prenotazione other = (Prenotazione) obj;
        if (aula == null) {
            if (other.aula != null)
                return false;
        } else if (!aula.equals(other.aula))
            return false;
        if (timeSlot == null) {
            if (other.timeSlot != null)
                return false;
        } else if (!timeSlot.equals(other.timeSlot))
            return false;
        return true;
    }

    /**
     * Ordina prima per time slot, poi per aula; coerente con equals.
     * @param o altra prenotazione
     * @return risultato del confronto lessicografico
     * @throws NullPointerException se o è null
     */
    @Override
    public int compareTo(Prenotazione o) {
        int cmp = this.timeSlot.compareTo(o.timeSlot);
        if (cmp != 0)
            return cmp;
        // se hanno lo stesso time slot vale l'ordine tra aule
        return this.aula.compareTo(o.aula);
    }

    /**
     * Restituisce una rappresentazione testuale della prenotazione.
     * @return descrizione testuale, senza stampare sullo standard output
     */
    @Override
    public String toString() {
        return "Prenotazione [aula = " + aula + ", time slot =" + timeSlot
                + ", docente=" + docente + ", motivo=" + motivo + "]";
    }

}
