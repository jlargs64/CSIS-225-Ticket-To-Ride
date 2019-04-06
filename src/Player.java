import java.util.ArrayList;
import java.awt.Color;
/**
 * The player class for ticket to ride - New York.
 *
 * @authors Michael Lamb, Justin Largo, Leon Griffiths, Jennifer LeClair, Yousef
 *      Borna
 * @version 1.0
 */
public class Player
{
    //The player name
    private String name; 
    //The player's color
    private Color color;
    //The age of the player to determine who goes
    //first(youngest goes first)
    private int age;
    //The number of taxis
    private int taxis;
    //The player's score
    private int points = 0; 
    //The cards that player has in hand
    private ArrayList<Card> playerHand = new ArrayList<Card>();
    //The list of the destination cards
    private ArrayList<DestCard> destCards = new ArrayList<DestCard>();
    //The list of Transportation cards
    private ArrayList<TransportCard> transportCards = new ArrayList<TransportCard>();
    
    /**
     * Constructor for the player class that initialize the player
     *  name, color.
     *  @param name the name of the player
     *  @param color the color of the player's taxis
     */
    public Player(String name, Color color, int age){
        //Initialize player name
        this.name = name;
        //Initialize the player color
        //(blue, purple, white, yellow)   
        this.color = color;
        //Youngest player goes first
        this.age = age;
        //Set the player's starting num of taxis
        taxis = 15;
        //initial dealing to player hand
        for(int i = 0; i < 2; i++){
            //add the cards to player hand
            DestCard destCard = new DestCard();
            TransportCard transportCard = new TransPortCard();
            playerHand.add(destCard);
            playerHand.add(transportCard);
        }
    }
    
    //does nothing at the moment
    public void drawCards(){
        //if the player chooses to draw cards
        //they can pick one of the five cards 
        //that are facing up. Or they can 
        //draw from either the transportation deck
        //or the destination deck
    }
    
    //does nothing at the moment
    public void claimRoute(){
        //if player has enough cards and chooses to
        //claim a route that isn't already claimed
    }
    
    //does nothing at the moment
    public void playerTurn(){
        //The player can only do one of three things
        //each turn.
        
        //Draw transportation cards, Claim a route,
        //or draw destination tickets
    }
    
    //does nothing at the moment
    public void pointsEarned(){
        //points earned from the length of each route
        //claimed by the player
        
        //if player failed complete destination tickets
        //deduct the ticket value from final score
        
        //if player completed destination tickets
        //add their value to the final score
        
    }
}
