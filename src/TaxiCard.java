import java.awt.*;

/**
 * The TaxiCard class for ticket to ride - New York.
 *
 * @author Michael Lamb, Justin Largo, Leon Griffiths, Jennifer LeClair, Yousuf
 * Borna
 * @version 1.0
 */
public class TaxiCard {

    //taxi type color
    protected String type;
    protected Image cardImage;

    /**
     * Constructor for the TaxiCard class that initialize the taxi
     * type, player,Destination.
     *
     * @param type the type of the taxi
     */
    public TaxiCard(String type) {

        // assigning the type of Taxi
        this.type = type;
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        //Assign the string type of taxi card and assign it the respective
        //image that goes to it.
        //Type can be black, blue, red, green, orange, pink, or rainbow.
        if (type.equalsIgnoreCase("BLACK")) {

            cardImage = toolkit.getImage("assets\\pieces\\black-card.png");
        } else if (type.equalsIgnoreCase("BLUE")) {

            cardImage = toolkit.getImage("assets\\pieces\\blue-card.png");
        } else if (type.equalsIgnoreCase("RED")) {

            cardImage = toolkit.getImage("assets\\pieces\\red-card.png");
        } else if (type.equalsIgnoreCase("GREEN")) {

            cardImage = toolkit.getImage("assets\\pieces\\green-card.png");
        } else if (type.equalsIgnoreCase("ORANGE")) {

            cardImage = toolkit.getImage("assets\\pieces\\orange-card.png");
        } else if (type.equalsIgnoreCase("PINK")) {

            cardImage = toolkit.getImage("assets\\pieces\\pink-card.png");
        } else {

            //It's a locomotive
            cardImage = toolkit.getImage("assets\\pieces\\rainbow.png");
        }
    }

    public void rotateCard(String direction) {

        Toolkit toolkit = Toolkit.getDefaultToolkit();

        if (direction.equalsIgnoreCase("VERTICAL")) {

            if (type.equalsIgnoreCase("BLACK")) {

                cardImage = toolkit.getImage("assets\\pieces\\black-card2.png");
            } else if (type.equalsIgnoreCase("BLUE")) {

                cardImage = toolkit.getImage("assets\\pieces\\blue-card2.png");
            } else if (type.equalsIgnoreCase("RED")) {

                cardImage = toolkit.getImage("assets\\pieces\\red-card2.png");
            } else if (type.equalsIgnoreCase("GREEN")) {

                cardImage = toolkit.getImage("assets\\pieces\\green-card2.png");
            } else if (type.equalsIgnoreCase("ORANGE")) {

                cardImage = toolkit.getImage(
                        "assets\\pieces\\orange-card2.png");
            } else if (type.equalsIgnoreCase("PINK")) {

                cardImage = toolkit.getImage(
                        "assets\\pieces\\pink-card2.png");
            } else {

                //It's a locomotive
                cardImage = toolkit.getImage(
                        "assets\\pieces\\rainbow2.png");
            }
        } else {
            //Rotate horizontally
            if (type.equalsIgnoreCase("BLACK")) {

                cardImage = toolkit.getImage(
                        "assets\\pieces\\black-card.png");
            } else if (type.equalsIgnoreCase("BLUE")) {

                cardImage = toolkit.getImage(
                        "assets\\pieces\\blue-card.png");
            } else if (type.equalsIgnoreCase("RED")) {

                cardImage = toolkit.getImage(
                        "assets\\pieces\\red-card.png");
            } else if (type.equalsIgnoreCase("GREEN")) {

                cardImage = toolkit.getImage(
                        "assets\\pieces\\green-card.png");
            } else if (type.equalsIgnoreCase("ORANGE")) {

                cardImage = toolkit.getImage(
                        "assets\\pieces\\orange-card.png");
            } else if (type.equalsIgnoreCase("PINK")) {

                cardImage = toolkit.getImage(
                        "assets\\pieces\\pink-card.png");
            } else {

                //It's a locomotive
                cardImage = toolkit.getImage(
                        "assets\\pieces\\rainbow.png");
            }
        }
    }

}
