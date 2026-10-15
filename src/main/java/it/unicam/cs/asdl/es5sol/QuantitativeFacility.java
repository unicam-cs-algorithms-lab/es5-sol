package it.unicam.cs.asdl.es5sol;

/**
 * Facility che rappresenta un servizio disponibile in una certa quantità.
 * <p>Estende {@link Facility} aggiungendo il campo {@code quantity}.
 * La disponibilità soddisfa una richiesta quantitativa con lo stesso codice
 * quando la quantità disponibile è almeno quella richiesta.
 * L'identità logica resta basata sul solo codice, anche con quantità diverse.
 */
public class QuantitativeFacility extends Facility {

    // La quantità riguarda satisfies, non equals.
    private final int quantity;

    /**
     * Costruisce una facility quantitativa, inizializzando prima la superclasse.
     * <p>Il contratto originale non impone un limite inferiore a quantity.
     * @param codice identificativo
     * @param descrizione descrizione
     * @param quantity quantità disponibile
     * @throws NullPointerException se codice o descrizione è null
     */
    public QuantitativeFacility(String codice, String descrizione,
                                int quantity) {
        super(codice, descrizione);
        this.quantity = quantity;
    }

    /**
     * Restituisce la quantità associata alla facility.
     * @return quantità disponibile
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Confronta codice, tipo concreto e quantità disponibile.
     * @param o facility richiesta
     * @return true se o è quantitativa, ha lo stesso codice e richiede
     * una quantità non superiore a questa
     * @throws NullPointerException se o è null
     */
    @Override
    public boolean satisfies(Facility o) {
        if (o == null)
            throw new NullPointerException(
                    "Tentativo di controllare la soddisfacibilità di una facility nulla");
        // instanceof controlla il tipo concreto prima del cast.
        if (o instanceof QuantitativeFacility) {
            // Controllo se la facility ha lo stesso codice e la quantità
            // soddisfa la richiesta
            QuantitativeFacility oo = (QuantitativeFacility) o;
            return this.getCodice().equals(oo.getCodice())
                    && this.quantity >= oo.quantity;
        } else
            // la facility non è una presence facility quindi non può essere
            // soddisfatta
            return false;
    }

}
