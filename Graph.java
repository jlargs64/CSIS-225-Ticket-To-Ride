import java.awt.Color;
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
    public Graph(int numVerts) {


        vertices = new Vertex[numVerts];
        for (int src = 0; src < numVerts; src++) {
            vertices[src] = new Vertex();
        }

    }

    public void addEdge(int src, int dest) {

        vertices[src].firstEdge = new Edge(dest, vertices[src].firstEdge);
    }

    public void removeEdge(int src, int dest) {

        // empty list, none to remove
        if (vertices[src].firstEdge == null) return;

        // remove first?  To do so, just bypass it in the list
        if (vertices[src].firstEdge.dest == dest) {
            vertices[src].firstEdge = vertices[src].firstEdge.next;
            return;
        }

        // remove later, note we are always looking ahead one edge so we can easily
        // "bypass" that node in the list by manipulating next references
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
    }

    private class Edge {
        private int dest;
        private Edge next;
        private Color color;
        private int cost;
        private boolean isAttraction;
        /**
         *
         * @param dest the vertex we are from
         * @param next the vertex we are to
         */
        private Edge(int dest, Edge next) {
            this.dest = dest;
            this.next = next;
        }
    }
}
