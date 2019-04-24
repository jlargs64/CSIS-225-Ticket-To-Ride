import javax.swing.*;

/**
 * The player selection frame for getting the form data of the players.
 *
 * @author Justin Largo, Leon Griffiths, Jennifer LeClair, Michael Lamb, Yousef
 * Borna
 * @version 1.0
 */
public class DestCardSelectionFrame extends JFrame {

    protected JPanel content;

    public DestCardSelectionFrame(Object[] destcards, Player currentPlayer) {

        setTitle("Destination Card Selection");
        //setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        content = new DestCardSelectionPanel(this, destcards, currentPlayer);
        getContentPane().add(content);

        // Making sure the game scales well
        setResizable(false);
        // Display the window.
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
