/**
 * The Destination class for ticket to ride - New York.
 *
 * @authors Michael Lamb, Justin Largo, Leon Griffiths, Jennifer LeClair, Yousuf
 *      Borna
 * @version 1.0
 */
public class Destination
{
    // array which contains the points, startDistrict and endDistrict
    protected String trip[];
    
    
    /**
     * Constructor for the Destination class that initialize the Destination
     *  trip array.
     *  @param points the points of the trip
     *  @param startDistrict the start point of the trip
     *  @param startDistrict the  end point of the trip
     */
    public Destination(String points, String startDistrict, String endDistrict){
    
    //initializing the trip array
    trip = new String[3];
    // assigning the points of the trip array
    trip[0] = points;
    // assigning the startDistrict of the trip array
    trip[1] = startDistrict;
    // assigning the endDistrict of the trip array
    trip[2] = endDistrict;
    
    
    }
}
