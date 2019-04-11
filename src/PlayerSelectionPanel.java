import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/**
 * The game panel for displaying the various game states of ticket to ride.
 *
 * @author Justin Largo, Leon Griffiths, Jennifer LeClair, Michael Lamb, Yousef
 * Borna
 * @version 1.0
 */
public class PlayerSelectionPanel extends JPanel implements ActionListener {

    protected int numPlayers = 2;
    protected ArrayList<Player> players = new ArrayList<>();
    private ArrayList<String> playerNames = new ArrayList<>();
    private ArrayList<String> playerAges = new ArrayList<>();
    private ArrayList<String> playerColors = new ArrayList<>();

    private JButton submitNum, submitVal;
    private JPanel numPlayerPanel, playerForm;

    PlayerSelectionPanel() {

        setPreferredSize(new Dimension(600, 400));

        JRadioButton p2 = new JRadioButton("2 Players");
        p2.setActionCommand("2");

        JRadioButton p3 = new JRadioButton("3 Players");
        p3.setActionCommand("3");

        JRadioButton p4 = new JRadioButton("4 Players");
        p4.setActionCommand("4");

        ButtonGroup buttons = new ButtonGroup();
        buttons.add(p2);
        buttons.add(p3);
        buttons.add(p4);

        p2.setSelected(true);

        submitNum = new JButton("Submit");
        submitVal = new JButton("Submit");

        p2.addActionListener(this);
        p3.addActionListener(this);
        p4.addActionListener(this);
        submitNum.addActionListener(this);
        submitVal.addActionListener(this);

        numPlayerPanel = new JPanel(new GridLayout(1, 4));
        numPlayerPanel.add(p2);
        numPlayerPanel.add(p3);
        numPlayerPanel.add(p4);
        numPlayerPanel.add(submitNum);
        add(numPlayerPanel, BorderLayout.CENTER);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("2")) {

            numPlayers = 2;
        }
        if (e.getActionCommand().equals("3")) {

            numPlayers = 3;
        }
        if (e.getActionCommand().equals("4")) {

            numPlayers = 4;
        }

        if (e.getSource().equals(submitNum)) {

            createPlayerForm();
        }

        if (e.getSource().equals(submitVal)) {

            //Validate the player form
            if (validateInput()) {

                for (int i = 0; i < numPlayers; i++) {

                    String name = playerNames.get(i);
                    int age = Integer.parseInt(playerAges.get(i));
                    String color = playerColors.get(i);
                    players.add(new Player(name, color, age));
                }
            } else {

                createPlayerForm();
            }
        }
    }

    /**
     * A method used for generated the forms for player selection
     */
    private void createPlayerForm() {

        remove(numPlayerPanel);

        //Init the values for everything we can add
        for (int i = 0; i < numPlayers; i++) {
            playerNames.add("");
            playerAges.add("");
            playerColors.add("");
        }

        playerForm = new JPanel(new GridLayout(0, 6));

        for (int i = 0; i < numPlayers; i++) {

            playerForm.add(new JLabel("Player " + (i + 1) +
                    " Name:"));

            String name = playerNames.get(i);
            JTextField nameInput = new JTextField(name, 5);
            playerForm.add(nameInput);

            playerForm.add(new JLabel("Player " + (i + 1) +
                    " Age:"));

            String age = playerAges.get(i);
            JTextField ageInput = new JTextField(age, 5);
            playerForm.add(ageInput);

            playerForm.add(new JLabel("Player " + (i + 1) +
                    " Color:"));

            String color = playerColors.get(i);
            JTextField colorInput = new JTextField(color, 5);
            playerForm.add(colorInput);
        }

        playerForm.add(submitVal);

        add(playerForm, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    /**
     * Tests to see if the player selection form is valid
     *
     * @return a boolean if the form is valid
     */
    private boolean validateInput() {

        //Colors that the player can select
        ArrayList<String> colors = new ArrayList<>();
        colors.add("BLUE");
        colors.add("PURPLE");
        colors.add("WHITE");
        colors.add("YELLOW");

        for (int i = 0; i < numPlayers; i++) {

            //If the input cannot be parsed into an int we break out
            try {
                int test = Integer.parseInt(playerAges.get(i));
            } catch (Exception e) {
                return false;
            }

            //If the color is not in the colors array it is either
            //Already used or not a valid choice
            if (!colors.contains(playerColors.get(i).toUpperCase())) {
                return false;
            } else {
                //Remove it so it can't be picked again
                colors.remove(playerColors.get(i));
            }

        }
        return true;
    }
}
