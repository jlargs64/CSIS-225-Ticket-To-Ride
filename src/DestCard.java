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
    //Used for finding the correct image to display
    protected String cardNum;

    /**
     * Constructor for the Destination class that initialize the Destination
     * trip array.
     *
     * @param cardNum       used for finding which card image to display
     * @param worth         the points gained from completion
     * @param startDistrict the start point of the trip
     * @param endDistrict   the  end point of the trip
     */
    public DestCard(int worth,  int startDistrict, int endDistrict, String cardNum) {

        //Initialize our instance variables
        this.worth = worth;
        this.startDistrict = startDistrict;
        this.endDistrict = endDistrict;
        this.cardNum = cardNum;
    }
}
