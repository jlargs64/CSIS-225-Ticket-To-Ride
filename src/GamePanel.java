import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A java implementation of ticket to ride - New York.
 *
 * @author Justin Largo, Leon Griffiths, Jennifer LeClair, Michael Lamb, Yousef
 * Borna
 * @version 1.0
 */
public class GamePanel extends JPanel implements MouseListener, ActionListener {

    //instance variables
    private final int width;
    private final int height;
    private final Graph map;
    private Toolkit toolkit;

    //Image related variables
    private Image mainMenuBackground, woodenBackgroundImage, gameMap;
    private Image[] helpImages = new Image[2];
    private int currentHelpImage;

    //Game state related variables
    private GameState currentState;

    //Buttons
    private JButton playButton, helpButton, quitButton, backButton;
    private JButton switchButton;
    private ButtonGroup selectionGroup;
    private JRadioButton p2, p3, p4;

    //Card objects
    private ArrayDeque<TaxiCard> taxiCards;
    private ArrayDeque<TaxiCard> activeTaxiCards;
    private ArrayDeque<DestCard> destCards;

    //Player related variables
    private ArrayList<Player> players;
    private Player currentPlayer;

    //The constructor for Text Twist
    public GamePanel() {

        //Default window settings
        setPreferredSize(new Dimension(800, 600));
        toolkit = Toolkit.getDefaultToolkit();
        setOpaque(true);
        width = getPreferredSize().width;
        height = getPreferredSize().height;
        setBackground(Color.WHITE);
        setFocusable(true);
        setLayout(null);

        //Construct our graph
        File data = new File("Districts.txt");
        map = new Graph(data);
        map.fillNYData();

        //Set our current state
        currentState = GameState.values()[0];

        // Add universal interface listeners
        addMouseListener(this);

        // Adding board buttons
        playButton = new JButton("Play");
        playButton.setVerticalTextPosition(AbstractButton.CENTER);
        playButton.setHorizontalTextPosition(AbstractButton.CENTER);
        playButton.setBounds(50, height - 100, 200, 100);

        helpButton = new JButton("Help");
        helpButton.setVerticalTextPosition(AbstractButton.CENTER);
        helpButton.setHorizontalTextPosition(AbstractButton.CENTER);
        helpButton.setBounds(300, height - 100, 200, 100);

        quitButton = new JButton("Quit");
        quitButton.setVerticalTextPosition(AbstractButton.CENTER);
        quitButton.setHorizontalTextPosition(AbstractButton.CENTER);
        quitButton.setBounds(width - 250, height - 100, 200, 100);

        backButton = new JButton("Back");
        backButton.setVerticalTextPosition(AbstractButton.CENTER);
        backButton.setHorizontalTextPosition(AbstractButton.CENTER);
        backButton.setBounds(50, height - 100, 200, 100);

        switchButton = new JButton("Next");
        switchButton.setVerticalTextPosition(AbstractButton.CENTER);
        switchButton.setHorizontalTextPosition(AbstractButton.CENTER);
        switchButton.setBounds(width - 250, height - 100, 200, 100);

        // Radio Buttons for selecting number of players
        p2 = new JRadioButton("2");
        p3 = new JRadioButton("3");
        p4 = new JRadioButton("4");

        selectionGroup = new ButtonGroup();
        selectionGroup.add(p2);
        selectionGroup.add(p3);
        selectionGroup.add(p4);

        // Add action listeners for if a button is clicked
        helpButton.addActionListener(this);
        playButton.addActionListener(this);
        quitButton.addActionListener(this);
    }

    /**
     * PaintComponent method for JPanel.
     *
     * @param g the Graphics object for this applet
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //The background image
        String imgFileLocation = "assets\\wood-background.jpg";
        woodenBackgroundImage = toolkit.getImage(imgFileLocation);

        //The main menu image
        imgFileLocation = "assets\\game-cover.jpg";
        mainMenuBackground = toolkit.getImage(imgFileLocation);

        //The game map
        imgFileLocation = "assets\\ny-board.jpg";
        gameMap = toolkit.getImage(imgFileLocation);

        //Switch case for displaying different states
        switch (currentState) {

            case MAIN_MENU:

                // display images
                imgFileLocation = "assets\\game-cover.jpg";
                mainMenuBackground = toolkit.getImage(imgFileLocation);
                int bgWidth = mainMenuBackground.getWidth(this);
                int bgHeight = mainMenuBackground.getHeight(this);
                g.drawImage(mainMenuBackground, 0, 0, bgWidth, bgHeight, this);

                // Draw the Title Text
                Font titleFont = new Font("Monospace", Font.BOLD, 100);
                g.setFont(titleFont);
                g.setColor(Color.BLACK);

                //Draw the play button which leads to the select players screen.
                add(playButton);

                //Draw the help button which displays the help photos
                add(helpButton);

                //Draw the exit button
                add(quitButton);

                //Disable buttons not in use
                remove(switchButton);
                remove(backButton);
                break;
            case HELP_MENU:

                //Swap between the 2 help images and also a back to main button.
                add(switchButton);
                add(backButton);

                // Disable Buttons not in use
                remove(playButton);
                remove(helpButton);
                remove(quitButton);
                break;
            case PLAYER_SELECTION:

                //Paint the background image
                bgWidth = woodenBackgroundImage.getWidth(this);
                bgHeight = woodenBackgroundImage.getHeight(this);
                g.drawImage(woodenBackgroundImage, 0, 0, bgWidth, bgHeight, this);
                //We should have some radio buttons for 2/3/4 players
                add(p2);
                add(p3);
                add(p4);

                //Then generate programmatically each player and their selected
                //colors (CYAN/MAGENTA/YELLOW/WHITE
                //Color selection should be a dropdown menu

                // Disable Buttons not in use
                remove(playButton);
                remove(helpButton);
                remove(quitButton);
                break;
            case GAME_MENU:

                //Initialize our array deques of cards
                taxiCards = new ArrayDeque<>();
                activeTaxiCards = new ArrayDeque<>();
                destCards = new ArrayDeque<>();

                //Draw the background
                bgWidth = woodenBackgroundImage.getWidth(this);
                bgHeight = woodenBackgroundImage.getHeight(this);
                g.drawImage(woodenBackgroundImage, 0, 0, bgWidth, bgHeight, this);

                //Layer the game map on top of background
                bgWidth = gameMap.getWidth(this);
                bgHeight = gameMap.getHeight(this);
                g.drawImage(gameMap, 0, 0, bgWidth, bgHeight, this);

                //This menu is where the bulk of the code will exist
                //Disable buttons not in use
                remove(p2);
                remove(p3);
                remove(p4);
                break;
            case SCORE_MENU:

                //This is where we show who won the game with the scorecard
                //There will be some animations that we can add later.

                break;
        }
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        //Enter the player selection screen
        if (e.getSource().equals(playButton)) {

            //Set our current state to player selection
            currentState = GameState.values()[2];

            //Repaint and end the method
            repaint();
            return;
        }

        //Enter the help screen
        else if (e.getSource().equals(helpButton)) {

            //Set our current state to help menu
            currentState = GameState.values()[1];

            //Repaint and end the method
            repaint();
            return;
        }

        //Kill the program
        else if (e.getSource().equals(quitButton)) {

            //Repaint and end the method
            repaint();
            return;
        }

        //Switch the help messages
        else if (e.getSource().equals(switchButton)) {


            //Repaint and end the method
            repaint();
            return;
        }

        //Go back from help to the main menu
        else if (e.getSource().equals(backButton)) {

            //Set our current state to player selection
            currentState = GameState.values()[2];

            //Repaint and end the method
            repaint();
            return;
        }
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }

    // For managing the game state
    public enum GameState {
        MAIN_MENU, HELP_MENU, PLAYER_SELECTION, GAME_MENU, SCORE_MENU
    }
}
