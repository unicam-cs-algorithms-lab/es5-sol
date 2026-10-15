package it.unicam.cs.asdl.es5sol;

/**
 * Descrive un servizio offerto da un'aula attraverso una superclasse astratta.
 * <p>Il codice identifica logicamente la facility: {@code equals} e
 * {@code hashCode} non dipendono né dalla descrizione né dalla sottoclasse.
 * Il metodo astratto {@link #satisfies(Facility)} delega alle sottoclassi
 * la regola di soddisfacimento della richiesta. Uguaglianza e soddisfacimento
 * sono quindi due relazioni distinte.
 */
public abstract class Facility {

    private final String codice;

    private final String descrizione;

    /**
     * Inizializza i dati comuni a tutte le sottoclassi.
     * @param codice identificativo della facility
     * @param descrizione descrizione testuale
     * @throws NullPointerException se uno dei parametri è null
     */
    public Facility(String codice, String descrizione) {
        if (codice == null || descrizione == null)
            throw new NullPointerException(
                    "Tentativo di costruire una facility con almeno una informazione nulla");
        this.codice = codice;
        this.descrizione = descrizione;
    }

    /**
     * Restituisce il codice che determina l'identità logica.
     * @return codice della facility
     */
    public String getCodice() {
        return codice;
    }

    /**
     * Restituisce la descrizione, non rilevante per l'identità logica.
     * @return descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Calcola un hash coerente con equals usando il solo codice.
     * @return hash del codice identificativo
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codice == null) ? 0 : codice.hashCode());
        return result;
    }

    /**
     * Definisce l'uguaglianza per tutte le sottoclassi in base al codice.
     * <p>Due facility con stesso codice sono uguali anche se di sottoclassi
     * diverse o con quantità diverse; non confondere con {@code satisfies}.
     * @param obj oggetto da confrontare
     * @return true se è una Facility con lo stesso codice
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Facility))
            return false;
        Facility other = (Facility) obj;
        if (codice == null) {
            if (other.codice != null)
                return false;
        } else if (!codice.equals(other.codice))
            return false;
        return true;
    }

    /**
     * Produce una rappresentazione testuale dei dati comuni.
     * @return stringa descrittiva della facility
     */
    @Override
    public String toString() {
        return "Facility [codice=" + codice + ", descrizione=" + descrizione
                + "]";
    }

    /**
     * Stabilisce se la facility disponibile soddisfa una richiesta.
     * <p>È un metodo astratto: la regola concreta dipende dalla sottoclasse
     * effettiva che riceve la chiamata (selezione posticipata).
     * @param o facility richiesta
     * @return true se la richiesta è soddisfatta
     * @throws NullPointerException se o è null
     */
    public abstract boolean satisfies(Facility o);

}
