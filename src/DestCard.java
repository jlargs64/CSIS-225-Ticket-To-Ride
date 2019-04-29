import java.awt.*;

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

    protected String start;
    protected String end;

    //The image we need to represent the dest card
    protected Image cardImage;


    /**
     * Constructor for the Destination class that initialize the Destination
     * trip array.
     *
     * @param cardNum       used for finding which card image to display
     * @param worth         the points gained from completion
     * @param startDistrict the start point of the trip
     * @param endDistrict   the  end point of the trip
     */
    public DestCard(int worth, int startDistrict, int endDistrict,
                    String cardNum) {

        //Initialize our instance variables
        this.worth = worth;
        this.startDistrict = startDistrict;
        this.endDistrict = endDistrict;
        this.cardNum = cardNum;

        //Toolkit for getting image
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        //Assigning the said image
        this.cardImage = toolkit.getImage(
                "assets\\dest-cards\\dest-card" + cardNum + ".jpg");

        if (startDistrict == 0) {
            start = "Lincoln Center";
        } else if (startDistrict == 1) {
            start = "Central Park";
        } else if (startDistrict == 2) {
            start = "Midtown West";
        } else if (startDistrict == 3) {
            start = "Times Square";
        } else if (startDistrict == 4) {
            start = "United Nations";
        } else if (startDistrict == 5) {
            start = "Chelsea";
        } else if (startDistrict == 6) {
            start = "Empire State Building";
        } else if (startDistrict == 7) {
            start = "Gramercy Park";
        } else if (startDistrict == 8) {
            start = "Greenwich village";
        } else if (startDistrict == 9) {
            start = "Soho";
        } else if (startDistrict == 10) {
            start = "East Village";
        } else if (startDistrict == 11) {
            start = "China Town";
        } else if (startDistrict == 12) {
            start = "Lower East Side";
        } else if (startDistrict == 13) {
            start = "Wall St.";
        } else if (startDistrict == 14) {
            start = "Brooklyn";
        }

        if (endDistrict == 0) {
            end = "Lincoln Center";
        } else if (endDistrict == 1) {
            end = "Central Park";
        } else if (endDistrict == 2) {
            end = "Midtown West";
        } else if (endDistrict == 3) {
            end = "Times Square";
        } else if (endDistrict == 4) {
            end = "United Nations";
        } else if (endDistrict == 5) {
            end = "Chelsea";
        } else if (endDistrict == 6) {
            end = "Empire State Building";
        } else if (endDistrict == 7) {
            end = "Gramercy Park";
        } else if (endDistrict == 8) {
            end = "Greenwich village";
        } else if (endDistrict == 9) {
            end = "Soho";
        } else if (endDistrict == 10) {
            end = "East Village";
        } else if (endDistrict == 11) {
            end = "China Town";
        } else if (endDistrict == 12) {
            end = "Lower East Side";
        } else if (endDistrict == 13) {
            end = "Wall St.";
        } else if (endDistrict == 14) {
            end = "Brooklyn";
        }
    }
}
