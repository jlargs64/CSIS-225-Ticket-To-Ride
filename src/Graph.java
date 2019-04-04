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

    private Vertex[] vertices;

    /**
     * Constructor
     */
    public Graph(File data) {

        int numVerts = 0;
        try {

            //Scanner we read from file with
            Scanner s = new Scanner(data);
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
     * @param src the src destination in the vertex array
     * @param dest edge destination to add
     * @param color color of the taxi required
     * @param cost amount of taxi's needed
     */
    public void addEdge(int src, int dest, Color color, int cost) {

        vertices[src].firstEdge = new Edge(dest, vertices[src].firstEdge,
                color, cost);
    }

    /**
     * @param src source vertex to remove from
     * @param dest edge being removed
     */
    public void removeEdge(int src, int dest) {

        // empty list, none to remove
        if (vertices[src].firstEdge == null) return;

        // remove first?  To do so, just bypass it in the list
        if (vertices[src].firstEdge.dest == dest) {
            vertices[src].firstEdge = vertices[src].firstEdge.next;
            return;
        }

        // remove later, note we are always looking ahead one edge so we can
        // easily "bypass" that node in the list by manipulating next references
        Edge e = vertices[src].firstEdge;
        while (e.next != null) {
            if (e.next.dest == dest) {
                e.next = e.next.next;
                return;
            }
            e = e.next;
        }
    }

    private class Vertex {

        private Edge firstEdge;
        private boolean isAttraction;
        private String name;

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

    // A private class to represent edges to other districts
    private class Edge {
        private int dest;
        private Edge next;
        private Color color;
        private int cost;

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
        }
    }
}
