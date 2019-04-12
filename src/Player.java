import java.util.ArrayList;
import java.awt.Color;

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
    protected int points = 0;
    //The cards that player has in hand
    protected ArrayList<TaxiCard> playerTaxis = new ArrayList<>();
    //The cards that player has in hand
    protected ArrayList<DestCard> playerDestCards = new ArrayList<>();

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
    }

    //does nothing at the moment
    public void drawCards() {
        //if the player chooses to draw cards
        //they can pick one of the five cards 
        //that are facing up. Or they can 
        //draw from either the transportation deck
        //or the destination deck
    }

    //does nothing at the moment
    public void claimRoute() {
        //if player has enough cards and chooses to
        //claim a route that isn't already claimed
    }

    //does nothing at the moment
    public void playerTurn() {
        //The player can only do one of three things
        //each turn.

        //Draw transportation cards, Claim a route,
        //or draw destination tickets
    }

    //does nothing at the moment
    public void pointsEarned() {
        //points earned from the length of each route
        //claimed by the player

        //if player failed complete destination tickets
        //deduct the ticket value from final score

        //if player completed destination tickets
        //add their value to the final score

    }

    /**
     * This method returns an int[] that holds how many of each card type a
     * player has.
     * From 0 to 7 respectively we have blue, green, black, pink, orange, red,
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
