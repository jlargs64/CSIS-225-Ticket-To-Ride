import javax.swing.*;

/**
 * The game panel for displaying the various game states of ticket to ride.
 *
 * @author Justin Largo, Leon Griffiths, Jennifer LeClair, Michael Lamb, Yousef
 * Borna
 * @version 1.0
 */
public class PlayerSelectionFrame extends JFrame {

    protected JPanel content;

    public PlayerSelectionFrame() {

        setTitle("Player Selection");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        content = new PlayerSelectionPanel(this);
        getContentPane().add(content);
        hasFocus();

        // Making sure the game scales well
        setResizable(false);
        // Display the window.
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
