import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * The player class for ticket to ride - New York.
 *
 * @version 1.0
 * @author Michael Lamb, Justin Largo, Leon Griffiths, Jennifer LeClair, Yousef
 * Borna
 */
public class Player {
    //The player name
    protected String name;
    //The player's color
    protected String color;
    //The age of the player to determine who goes
    //first(youngest goes first)
    protected int age;
    //The number of taxis
    protected int taxis;
    //The player's score
    protected int routePoints;
    protected int destPoints;
    protected int totalPoints;
    //The cards that player has in hand
    protected ArrayList<TaxiCard> playerTaxis = new ArrayList<>();
    //The cards that player has in hand
    protected ArrayList<DestCard> playerDestCards = new ArrayList<>();
    //The player graph
    protected Graph claimedRoutes;
    //The players images of claimed routes
    protected ArrayList<Route> routes;
    //The player Color object
    protected Color COLOR;

    /**
     * Constructor for the player class that initialize the player
     * name, color.
     *
     * @param name  the name of the player
     * @param color the color of the player's taxis
     * @param age the numerical age of the player
     */
    public Player(String name, String color, int age) {
        //Initialize player name
        this.name = name;
        //Initialize the player color
        //(blue, purple, white, yellow)
        this.color = color;
        //Youngest player goes first
        this.age = age;
        //Set the player's starting num of taxis
        taxis = 15;
        //Setting player scores to 0
        routePoints = 0;
        destPoints = 0;
        totalPoints = 0;
        if (color.equalsIgnoreCase("BLUE")) {
            COLOR = Color.BLUE;
        } else if (color.equalsIgnoreCase("WHITE")) {
            COLOR = Color.WHITE;
        } else if (color.equalsIgnoreCase("PURPLE")) {
            COLOR = Color.MAGENTA;
        } else {
            COLOR = Color.YELLOW;
        }

        //Initialize the routes the player has claimed
        try {
            claimedRoutes = new Graph(new File("Districts.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Initialize the routes
        routes = new ArrayList<>();
    }

    /**
     * This method returns an int[] that holds how many of each card type a
     * player has.
     * From 0 to 6 respectively we have blue, green, black, pink, orange, red,
     * and rainbow(taxi).
     *
     * @return amountOfCard the int amount of each card type
     */
    public int[] getCardTypes() {

        //An array of card counts per type
        int[] amountOfCard = new int[7];
        for (TaxiCard card : playerTaxis) {

            if (card.type.equalsIgnoreCase("BLUE")) {
                amountOfCard[0]++;
            } else if (card.type.equalsIgnoreCase("GREEN")) {
                amountOfCard[1]++;
            } else if (card.type.equalsIgnoreCase("BLACK")) {
                amountOfCard[2]++;
            } else if (card.type.equalsIgnoreCase("PINK")) {
                amountOfCard[3]++;
            } else if (card.type.equalsIgnoreCase("ORANGE")) {
                amountOfCard[4]++;
            } else if (card.type.equalsIgnoreCase("RED")) {
                amountOfCard[5]++;
            } else if (card.type.equalsIgnoreCase("RAINBOW")) {
                amountOfCard[6]++;
            }
        }

        return amountOfCard;
    }
}
