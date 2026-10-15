package it.unicam.cs.asdl2526.es5sol;

/**
 * Facility che rappresenta la presenza di un servizio, senza quantità.
 * <p>La sottoclasse riutilizza lo stato e l'identità logica definiti da
 * {@link Facility}, ma implementa la propria regola di soddisfacimento:
 * un'altra {@code PresenceFacility} con lo stesso codice.
 */
public class PresenceFacility extends Facility {

    /**
     * Costruisce una facility di presenza delegando l'inizializzazione a super.
     * @param codice codice identificativo
     * @param descrizione descrizione
     * @throws NullPointerException se un argomento è null
     */
    public PresenceFacility(String codice, String descrizione) {
        super(codice, descrizione);
    }

    /**
     * Verifica una richiesta di presenza con lo stesso codice.
     * @param o facility richiesta
     * @return true solo se o è una PresenceFacility con lo stesso codice
     * @throws NullPointerException se o è null
     */
    @Override
    public boolean satisfies(Facility o) {
        if (o == null)
            throw new NullPointerException(
                    "Tentativo di controllare la soddisfacibilità di una facility nulla");
        // instanceof controlla il tipo concreto prima del cast.
        if (o instanceof PresenceFacility) {
            // Controllo se la facility ha lo stesso codice
            PresenceFacility oo = (PresenceFacility) o;
            return this.getCodice().equals(oo.getCodice());
        } else
            // la facility non è una presence facility quindi non può essere
            // soddisfatta
            return false;
    }

}
