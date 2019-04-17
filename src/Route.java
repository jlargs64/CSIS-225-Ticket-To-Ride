import java.awt.*;

/**
 * The route class for ticket to ride - New York.
 * Used for graphically drawing each route.
 *
 * @author Michael Lamb, Justin Largo, Leon Griffiths, Jennifer LeClair, Yousef
 * Borna
 * @version 1.0
 */
public class Route {

    protected Color color;
    protected int start;
    protected int end;
    protected Shape img;

    /***
     * This constructor is for single routes, no color needed.
     *
     * @param start start district index
     * @param end end district index
     * @param img the polygon to draw
     */
    Route(int start, int end, Shape img) {

        this.start = start;
        this.end = end;
        this.img = img;
        color = null;
    }

    /***
     * This constructor is for double routes, color is needed
     * to specify which one.
     *
     * @param start start district index
     * @param end end district index
     * @param img the polygon to draw
     * @param color the color of the route
     */
    Route(Color color, int start, int end, Shape img) {

        this.color = color;
        this.start = start;
        this.end = end;
        this.img = img;
    }
}