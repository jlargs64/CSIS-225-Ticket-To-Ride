import javax.swing.*;

/**
 * The game panel for displaying the various game states of ticket to ride.
 *
 * @author Justin Largo, Leon Griffiths, Jennifer LeClair, Michael Lamb, Yousef
 * Borna
 * @version 1.0
 */
public class PlayerSelectionDialog extends JDialog {
    private JPanel myPanel = null;
    private JButton yesButton = null;
    private JButton noButton = null;

    public PlayerSelectionDialog(JFrame frame, boolean modal,
                                 String myMessage) {

        super(frame, modal);
        myPanel = new JPanel();
        getContentPane().add(myPanel);
        myPanel.add(new JLabel(myMessage));
        yesButton = new JButton("Yes");
        myPanel.add(yesButton);
        noButton = new JButton("No");
        myPanel.add(noButton);
        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }
}
