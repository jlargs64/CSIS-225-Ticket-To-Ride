import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Deque;

/**
 * The player selection panel for getting the data from the player selection
 * form.
 *
 * @author Justin Largo, Leon Griffiths, Jennifer LeClair, Michael Lamb, Yousef
 * Borna
 * @version 1.0
 */
public class PlayerSelectionPanel extends JPanel implements ActionListener {

    //Instance variables
    private int numPlayers = 2;

    //Player data from form
    private ArrayList<Player> playersList = new ArrayList<>();
    private Deque<Player> players;
    private ArrayList<String> playerNames = new ArrayList<>();
    private ArrayList<String> playerAges = new ArrayList<>();
    private ArrayList<String> playerColors = new ArrayList<>();

    //Keep track of form buttons
    private ArrayList<JTextField> names = new ArrayList<>();
    private ArrayList<JTextField> ages = new ArrayList<>();
    private ArrayList<JTextField> colors = new ArrayList<>();

    private JButton submitNum, submitVal;
    private JPanel numPlayerPanel, playerForm, helpPanel;
    private JFrame parentFrame;

    PlayerSelectionPanel(PlayerSelectionFrame parentFrame,
                         Deque<Player> players) {

        //Set our panels size
        setPreferredSize(new Dimension(600, 400));

        //Set our parent frame
        this.parentFrame = parentFrame;

        //Set our player deque
        this.players = players;

        //Initialize our radio buttons for player selection
        JRadioButton p2 = new JRadioButton("2 Players");
        p2.setActionCommand("2");

        JRadioButton p3 = new JRadioButton("3 Players");
        p3.setActionCommand("3");

        JRadioButton p4 = new JRadioButton("4 Players");
        p4.setActionCommand("4");

        //Button group is used for keeping track of which button is clicked.
        ButtonGroup buttons = new ButtonGroup();
        buttons.add(p2);
        buttons.add(p3);
        buttons.add(p4);

        //Set two players as default
        p2.setSelected(true);

        //Form submission buttons
        submitNum = new JButton("Submit");
        submitVal = new JButton("Submit");

        //ActionListeners are for seeing if a button was clicked
        p2.addActionListener(this);
        p3.addActionListener(this);
        p4.addActionListener(this);
        submitNum.addActionListener(this);
        submitVal.addActionListener(this);

        //Adding the radio buttons and submit to our num player panel.
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

        //See which radio button was clicked and set it to the number of players
        //we want.
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

            //Get data from the form
            for (int i = 0; i < numPlayers; i++) {

                playerNames.set(i, names.get(i).getText());
                playerAges.set(i, ages.get(i).getText());
                playerColors.set(i, colors.get(i).getText());
            }
            //Validate the player form
            if (validateInput()) {

                for (int i = 0; i < numPlayers; i++) {

                    //Construct our players
                    String name = playerNames.get(i);
                    int age = Integer.parseInt(playerAges.get(i));
                    String color = playerColors.get(i);
                    playersList.add(new Player(name, color, age));
                }

                //We now sort out deque of players to go from youngest to
                //oldest
                while (players.size() < numPlayers) {

                    //Init our youngest player temp variable
                    Player youngest = playersList.get(0);

                    //Find a new youngest player
                    for (int i = 0; i < playersList.size(); i++) {

                        int ageToCompare = playersList.get(i).age;
                        if (youngest.age > ageToCompare) {

                            youngest = playersList.get(i);
                            i = 0;
                        }
                    }
                    players.add(youngest);
                    playersList.remove(youngest);
                }
                //Kill the frame
                parentFrame.dispose();
            } else {

                remove(helpPanel);
                remove(playerForm);
                //If form is not valid ask to reinput.
                createPlayerForm();
            }
        }
    }

    /**
     * A method used for generated the forms for player selection
     */
    private void createPlayerForm() {

        //Clear our form buttons again
        names.clear();
        ages.clear();
        colors.clear();

        //Remove the num player form because its no longer in use.
        remove(numPlayerPanel);

        //Init the values for everything we can add.
        for (int i = 0; i < numPlayers; i++) {
            playerNames.add("");
            playerAges.add("");
            playerColors.add("");
        }

        //Create the player form panel.
        playerForm = new JPanel(new GridLayout(0, 6));

        //Helpful labels for proper form input
        helpPanel = new JPanel(new GridLayout(4, 0));
        helpPanel.add(new JLabel("Player Selection Rules:"));
        helpPanel.add(new JLabel("Names should be unique."));
        helpPanel.add(new JLabel("Ages should be whole numbers."));
        helpPanel.add(new JLabel("Colors are unique and only " +
                "PURPLE, WHITE, BLUE, or YELLOW."));
        add(helpPanel, BorderLayout.NORTH);

        //Add a form for each player.
        for (int i = 0; i < numPlayers; i++) {

            //A form for the player name.
            playerForm.add(new JLabel("Player " + (i + 1) +
                    " Name:"));

            //Process the player name form.
            String name = playerNames.get(i);
            JTextField nameInput = new JTextField(name, 5);
            names.add(nameInput);
            playerForm.add(nameInput);

            //A form for the player age.
            playerForm.add(new JLabel("Player " + (i + 1) +
                    " Age:"));

            //Process the player age form.
            String age = playerAges.get(i);
            JTextField ageInput = new JTextField(age, 5);
            ages.add(ageInput);
            playerForm.add(ageInput);

            //A form for the player color.
            playerForm.add(new JLabel("Player " + (i + 1) +
                    " Color:"));

            //Process the player color form.
            String color = playerColors.get(i);
            JTextField colorInput = new JTextField(color, 5);
            colors.add(colorInput);
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

        //ArrayList of temp names to keep track if they are not unique
        ArrayList<String> tempNames = new ArrayList<>();

        for (int i = 0; i < numPlayers; i++) {

            if (playerNames.get(i).equals("")) {

                return false;
            } else {
                if (tempNames.contains(playerNames.get(i))) {
                    return false;
                } else {
                    tempNames.add(playerNames.get(i));
                }
            }

            //If the input cannot be parsed into an int we break out
            try {
                int test = Integer.parseInt(playerAges.get(i));
            } catch (Exception e) {
                return false;
            }

            //If the color is not in the colors array it is either
            //Already used or not a valid choice
            if (!colors.contains(playerColors.get(i).toUpperCase().trim())) {
                return false;
            } else {
                //Remove it so it can't be picked again
                colors.remove(playerColors.get(i).toUpperCase().trim());
            }

        }
        return true;
    }
}
