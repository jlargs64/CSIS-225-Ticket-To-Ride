import javax.swing.*;

/**
 * A java implementation of ticket to ride - New York.
 *
 * @author Justin Largo, Leon Griffiths, Jennifer LeClair, Michael Lamb, Yousef
 * Borna
 * @version 1.0
 */
public class GameDriver {

    /**
     * The main method to execute the program.
     *
     * @param args command line arguements
     */
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {


                // Create and set up the window.
                JFrame frame = new JFrame("Ticket to Ride: New York");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                GamePanel panel = new GamePanel();
                frame.getContentPane().add(panel);

                // Making sure the game scales well
                frame.setResizable(false);
                // Display the window.
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}