/**
 * The Destination class for ticket to ride - New York.
 *
 * @author Michael Lamb, Justin Largo, Leon Griffiths, Jennifer LeClair, Yousuf
 * Borna
 * @version 1.0
 */
public class DestCard {

    //Instance variables
    protected int worth;
    protected int startDistrict;
    protected int endDistrict;

    /**
     * Constructor for the Destination class that initialize the Destination
     * trip array.
     *
     * @param worth         the points gained from completion
     * @param startDistrict the start point of the trip
     * @param endDistrict   the  end point of the trip
     */
    public DestCard(int worth, int startDistrict, int endDistrict) {

        this.worth = worth;
        this.startDistrict = startDistrict;
        this.endDistrict = endDistrict;
    }
}
