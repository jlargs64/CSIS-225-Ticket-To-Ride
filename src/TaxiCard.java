import java.awt.Color;

/**
 * The TaxiCard class for ticket to ride - New York.
 *
 * @authors Michael Lamb, Justin Largo, Leon Griffiths, Jennifer LeClair, Yousuf
 *      Borna
 * @version 1.0
 */
public class TaxiCard
{
    //taxi type color
    protected Color type;
    // Player with the taxi
    protected Player player;
    // start point of the journey
    protected String startDistrict;
    // end point of the journey
    protected String endDistrict;
    //The destination object for collecting information about the trip
    protected Destination destination;
    
    //this is a getter for type
    public Color getType(){
    
       return this.type;
    }
    
    //this is a getter for player
    public Player getPlayer(){
    
       return this.player;
    }
    
    // this is a getter for startDistrict 
    public String getStartDistrict(){
    
      return this.startDistrict;
    }
    // this is a getter for endDistrict 
    public String getEndDistrict(){
    
      return this.endDistrict;
    }
    
    //this is a getter for Destination object
    public String getDestination(){
    
      return destination;
    }
    
    
    
    
    
    /**
     * Constructor for the TaxiCard class that initialize the taxi
     *  type, player,Destination.
     *  @param type the type of the taxi
     *  @param player the player of the taxi
     *  @param destination the taxi of the player 
     */
    public TaxiCard(Color type,Player player,Destination destination){
    
       // assigning the type of Taxi 
       this.type = type;
       // assigning the player for the Taxi
       this.player=player;
       // assigning the destination for the Taxi
       this.destination = destination;
       // assigning the startDistrict for the Taxi
       this.startDistrict = destination.trip[1];
       // assigning the endDistrict for the Taxi
       this.endDistrict = destination.trip[2];
       
    
    }
    
}
