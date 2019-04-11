import javax.swing.*;
import java.util.Deque;

/**
 * The player selection frame for getting the form data of the players.
 *
 * @author Justin Largo, Leon Griffiths, Jennifer LeClair, Michael Lamb, Yousef
 * Borna
 * @version 1.0
 */
public class PlayerSelectionFrame extends JFrame {

    protected JPanel content;

    public PlayerSelectionFrame(Deque<Player> players) {

        setTitle("Player Selection");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        content = new PlayerSelectionPanel(this, players);
        getContentPane().add(content);

        // Making sure the game scales well
        setResizable(false);
        // Display the window.
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
