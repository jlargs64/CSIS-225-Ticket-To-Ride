import java.awt.Color;
import java.io.File;
import java.util.Scanner;

/**
 * An adjacency list graph to represent our game map.
 *
 * @author Justin Largo, Leon Griffiths, Jennifer LeClair, Michael Lamb, Yousef
 * Borna
 * @version 1.0
 */
public class Graph {

    protected Vertex[] vertices;
    private Scanner s;

    /**
     * Constructor
     */
    public Graph(File data) {

        int numVerts;
        try {

            //Scanner we read from file with
            s = new Scanner(data);
            numVerts = s.nextInt();

            //Initialize our vertices from top of the file
            vertices = new Vertex[numVerts];
            //Go to the next line b/c nothing is there.
            s.nextLine();
            for (int src = 0; src < numVerts; src++) {

                //Parse the district name and if it is a tourist attraction.
                //If district is an attraction it is an extra point if captured.
                String input = s.nextLine();
                String[] vertexData = input.split(",");

                //Set a vertex as an attraction if it is 1
                boolean isAttraction = false;
                if (vertexData[1].trim().equals("1")) {
                    isAttraction = true;
                }
                vertices[src] = new Vertex(vertexData[0], isAttraction);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * @param src   the src destination in the vertex array
     * @param dest  edge destination to add
     * @param color color of the taxi required
     * @param cost  amount of taxi's needed
     */
    public void addEdge(int src, int dest, Color color, int cost) {

        vertices[src].firstEdge = new Edge(dest, vertices[src].firstEdge,
                color, cost);
    }

    /**
     * @param src  source vertex to remove from
     * @param dest edge being removed
     */
    public void removeEdge(int src, int dest, Color color) {

        // empty list, none to remove
        if (vertices[src].firstEdge == null) return;

        // remove first?  To do so, just bypass it in the list
        if (vertices[src].firstEdge.dest == dest &&
                vertices[src].firstEdge.color == color) {
            vertices[src].firstEdge = vertices[src].firstEdge.next;
            return;
        }

        // remove later, note we are always looking ahead one edge so we can
        // easily "bypass" that node in the list by manipulating next references
        Edge e = vertices[src].firstEdge;
        while (e.next != null) {
            if (e.next.dest == dest && e.next.color == color) {
                e.next = e.next.next;
                return;
            }
            e = e.next;
        }
    }

    /**
     * Fill the graph with the NY Data from file
     */
    public void fillNYData() {

        //Skip the next line b/c it's empty
        s.nextLine();
        while (s.hasNextLine()) {
            int src = s.nextInt();
            int dest = s.nextInt();
            int cost = s.nextInt();
            String strColor = s.next().trim();
            Color color;
            switch (strColor) {
                case "Red":
                    color = Color.RED;
                    break;
                case "Blue":

                    color = Color.BLUE;
                    break;
                case "Orange":

                    color = Color.ORANGE;
                    break;
                case "Black":

                    color = Color.BLACK;
                    break;
                case "Pink":

                    color = Color.PINK;
                    break;
                case "Clear":

                    color = Color.WHITE;
                    break;
                default:
                    color = Color.GREEN;
                    break;
            }
            addEdge(src, dest, color, cost);
        }
    }

    /***
     * Used for if a edge exists within the given graph
     * @param src the source vertex
     * @param dest the destination edge
     * @return true if it exits, false if not
     */
    public boolean contains(int src, int dest, Color color) {

        //The finger to traverse the graph
        Edge finger = vertices[src].firstEdge;

        //Loop to check all the edges
        while (finger != null) {

            //If the finger was found return true
            if (finger.dest == dest && finger.color == color) {
                return true;
            }
            //Otherwise keep looking
            finger = finger.next;
        }
        return false;
    }

    /***
     * A class to hold each district as a vertex on the graph.
     */
    protected class Vertex {

        protected Edge firstEdge;
        protected boolean isAttraction;
        protected String name;

        /**
         * Constructor for Vertex
         *
         * @param name         name of the district
         * @param isAttraction 1 pt added if true
         */
        public Vertex(String name, boolean isAttraction) {

            firstEdge = null;
            this.name = name;
            this.isAttraction = isAttraction;
        }
    }

    /***
     * A class to hold each route as a edge on the graph.
     */
    protected class Edge {
        protected int dest;
        protected Edge next;
        protected Color color;
        protected String strColor;
        protected int cost;

        /**
         * Constructor for Edge
         *
         * @param dest the vertex we are from
         * @param next the vertex we are to
         */
        private Edge(int dest, Edge next, Color color, int cost) {

            this.dest = dest;
            this.next = next;
            this.color = color;
            this.cost = cost;

            if (color.equals(Color.RED)) {
                strColor = "RED";
            } else if (color.equals(Color.BLUE)) {
                strColor = "BLUE";
            } else if (color.equals(Color.ORANGE)) {
                strColor = "ORANGE";
            } else if (color.equals(Color.PINK)) {
                strColor = "PINK";
            } else if (color.equals(Color.GREEN)) {
                strColor = "GREEN";
            } else if (color.equals(Color.WHITE)) {
                strColor = "CLEAR";
            } else if (color.equals(Color.BLACK)) {
                strColor = "BLACK";
            }
        }
    }
}
