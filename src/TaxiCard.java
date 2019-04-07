import java.awt.Color;

/**
 * The TaxiCard class for ticket to ride - New York.
 *
 * @version 1.0
 * @author Michael Lamb, Justin Largo, Leon Griffiths, Jennifer LeClair, Yousuf
 * Borna
 */
public class TaxiCard {

    //taxi type color
    protected Color type;

    /**
     * Constructor for the TaxiCard class that initialize the taxi
     * type, player,Destination.
     *
     * @param type the type of the taxi
     */
    public TaxiCard(Color type) {

        // assigning the type of Taxi
        this.type = type;
    }

}
