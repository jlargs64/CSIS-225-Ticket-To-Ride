import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;

/**
 * The game panel for displaying the various game states of ticket to ride.
 *
 * @author Justin Largo, Leon Griffiths, Jennifer LeClair, Michael Lamb, Yousef
 * Borna
 * @version 1.0
 */
public class GamePanel extends JPanel implements MouseListener, ActionListener {

    //instance variables
    private final int width;
    private final int height;
    //Game state related variables
    private GameState currentState;
    private Graph map;
    private Toolkit toolkit;
    private int turnNum;

    //Image related variables
    private Image mainMenuImg, woodenImage, gameMap, scoreCard;
    private Image taxiBackImg, destBackImg;
    private Image[] helpImages = new Image[2];
    private int currentHelpImage;

    //Buttons
    private JButton playButton, helpButton, quitButton, backButton;
    private JButton switchButton;

    //Card objects
    private Deque<TaxiCard> taxiCards;
    private Deque<TaxiCard> activeTaxiCards;
    private Deque<TaxiCard> discaredTaxis;
    private Deque<DestCard> destCards;

    //Player related variables
    private Deque<Player> players = new LinkedList<>();
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
        try {
            File data = new File("Districts.txt");
            map = new Graph(data);
            map.fillNYData();
        } catch (Exception e) {

            e.printStackTrace();
        }

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

        // Add action listeners for if a button is clicked
        helpButton.addActionListener(this);
        playButton.addActionListener(this);
        quitButton.addActionListener(this);
        backButton.addActionListener(this);
        switchButton.addActionListener(this);
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
        woodenImage = toolkit.getImage(imgFileLocation);

        //The main menu image
        imgFileLocation = "assets\\game-cover.jpg";
        mainMenuImg = toolkit.getImage(imgFileLocation);

        //The game map
        imgFileLocation = "assets\\ny-board.jpg";
        gameMap = toolkit.getImage(imgFileLocation);

        //Score card
        imgFileLocation = "assets\\score-card.jpg";
        scoreCard = toolkit.getImage(imgFileLocation);

        //The back of a taxi card
        imgFileLocation = "assets\\taxi-card-cover.png";
        taxiBackImg = toolkit.getImage(imgFileLocation);

        //The back of a destination card
        imgFileLocation = "assets\\dest-card-cover.png";
        destBackImg = toolkit.getImage(imgFileLocation);

        //Switch case for displaying different states
        switch (currentState) {

            case MAIN_MENU:

                // display images
                imgFileLocation = "assets\\game-cover.jpg";
                mainMenuImg = toolkit.getImage(imgFileLocation);
                g.drawImage(mainMenuImg, 0, 0, 800, 600, this);

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
            case GAME_MENU:

                // Disable Buttons not in use
                remove(playButton);
                remove(helpButton);
                remove(quitButton);

                //Draw the background
                int bgWidth = woodenImage.getWidth(this);
                int bgHeight = woodenImage.getHeight(this);
                g.drawImage(woodenImage, 0, 0, bgWidth, bgHeight, this);

                //Layer the game map on top of background
                g.drawImage(gameMap, 20, 10, 400, 580, this);

                //Keep items in line
                int textXaxis = 460;
                int activeCardY = height / 2 + 100;
                int cardW = 100;
                int cardH = 50;
                //Draw the card decks on the right hand side
                //To specify, these are the card decks that we can draw from
                g.drawImage(taxiBackImg, textXaxis, activeCardY, cardW,
                        cardH, this);

                //Now draw the remaining active cards
                for (TaxiCard card : activeTaxiCards) {

                    activeCardY -= 60;
                    g.drawImage(card.cardImage, textXaxis, activeCardY, cardW,
                            cardH, this);
                }

                //Make the font for text
                Font titleFont = new Font("Monospace", Font.BOLD, 16);
                g.setFont(titleFont);
                g.setColor(Color.WHITE);

                //Draw the current player stats:
                //Capitalize the first letter of the players name by default.
                String name = currentPlayer.name;
                String newName = name.substring(0, 1).toUpperCase()
                        + name.substring(1);

                String msg = newName + "\'s Turn";
                g.drawString(msg, textXaxis, 20);

                //Draw the turn number
                g.drawString("Turn Number: " + turnNum, textXaxis, 40);

                //Draw the amount of trains
                g.drawString("Taxis Left: " + currentPlayer.taxis,
                        textXaxis, 60);

                //Draw the cards available
                g.drawString("Available Cards", textXaxis, 80);

                //Draw the current hand of the player
                int currentHandX = textXaxis + 150;
                g.drawString("Current Hand", currentHandX, 80);

                //Reset card y axis
                activeCardY = height / 2 + 170;

                //Draw the dest card deck
                g.drawImage(destBackImg, textXaxis, activeCardY, cardW,
                        cardH, this);

                //Draw our hand

                //Just an array of string ref to our cards
                String[] cardFileNames = {

                        "assets\\pieces\\blue-card.png",
                        "assets\\pieces\\green-card.png",
                        "assets\\pieces\\black-card.png",
                        "assets\\pieces\\pink-card.png",
                        "assets\\pieces\\orange-card.png",
                        "assets\\pieces\\red-card.png",
                        "assets\\pieces\\rainbow.png"
                };
                //An array of card counts per type
                int[] amountOfCard = new int[7];
                for (TaxiCard card : currentPlayer.playerTaxis) {

                    if (card.type.equalsIgnoreCase("BLUE")) {
                        amountOfCard[0]++;
                    } else if (card.type.equalsIgnoreCase("GREEN")) {
                        amountOfCard[1]++;
                    } else if (card.type.equalsIgnoreCase("BLACK")) {
                        amountOfCard[2]++;
                    } else if (card.type.equalsIgnoreCase("PINK")) {
                        amountOfCard[3]++;
                    } else if (card.type.equalsIgnoreCase("ORANGE")) {
                        amountOfCard[4]++;
                    } else if (card.type.equalsIgnoreCase("RED")) {
                        amountOfCard[5]++;
                    } else if (card.type.equalsIgnoreCase("RAINBOW")) {
                        amountOfCard[6]++;
                    }
                }
                for (int i = 0; i < cardFileNames.length; i++) {

                    //Create and draw the image of the card
                    Image card = toolkit.getImage(cardFileNames[i]);
                    g.drawImage(card, currentHandX, activeCardY, cardW,
                            cardH, this);
                    g.drawString("x" + amountOfCard[i],
                            currentHandX + 115, activeCardY + 30);
                    activeCardY -= 60;
                }
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

            //Send the player set up form
            //We pass in the player deque to get our data
            PlayerSelectionFrame playerSelectForm =
                    new PlayerSelectionFrame(players);
            //Prompt the users with the form
            JDialog playerSelect = new JDialog(playerSelectForm);

            //We add a listener to see if the dialog window how been closed.
            //If it was closed it means it was successful or program exited.
            playerSelectForm.addWindowListener(new WindowAdapter() {
                /**
                 * Invoked when a window has been closed.
                 *
                 * @param e a window event object
                 */
                @Override
                public void windowClosed(WindowEvent e) {
                    super.windowClosed(e);

                    //Set our current state to player selection
                    currentState = GameState.values()[2];

                    //Game Start Initialization
                    //Initialize our array deques of cards
                    taxiCards = new LinkedList<>();
                    activeTaxiCards = new LinkedList<>();
                    destCards = new LinkedList<>();
                    discaredTaxis = new LinkedList<>();

                    //Init turn number
                    turnNum = 0;

                    //Adding taxi cards to our deck
                    //6 each color and 8 rainbow
                    //Use a temporary ArrayList so that we can shuffle later.
                    ArrayList<TaxiCard> tempCards = new ArrayList<>();
                    for (int i = 0; i < 6; i++) {
                        tempCards.add(new TaxiCard("blue"));
                        tempCards.add(new TaxiCard("green"));
                        tempCards.add(new TaxiCard("orange"));
                        tempCards.add(new TaxiCard("black"));
                        tempCards.add(new TaxiCard("pink"));
                        tempCards.add(new TaxiCard("red"));
                    }
                    //Add the 8 locomotives
                    for (int i = 0; i < 8; i++) {
                        tempCards.add(new TaxiCard("rainbow"));
                    }
                    //Shuffle the taxi card deck
                    Collections.shuffle(tempCards);

                    //Place our temp hand into the deque due to it being a data structure
                    //that handles more like a card deck.
                    for (int i = 0; i < tempCards.size(); i++) {

                        //Add it to our deck
                        taxiCards.add(tempCards.get(i));
                        //Remove it because we don't need it anymore
                        tempCards.remove(i);
                    }

                    //Deal 2 taxi cards to all players
                    for (Player p : players) {

                        p.playerTaxis.add(taxiCards.removeFirst());
                        p.playerTaxis.add(taxiCards.removeFirst());
                    }

                    //Deal 5 cards face up from top of deck
                    for (int i = 0; i < 5; i++) {

                        activeTaxiCards.addFirst(taxiCards.removeFirst());
                    }
                    //Make sure there aren't more than 3 rainbow taxi's
                    //otherwise discard
                    int taxiCount = 0;
                    for (int i = 0; i < activeTaxiCards.size(); i++) {

                        //Count the amount of rainbow cards in the active deck.
                        TaxiCard t = activeTaxiCards.removeFirst();
                        if (t.type.equalsIgnoreCase("rainbow")) {
                            taxiCount++;
                        }
                        //Add the card back to the bottom of the deck.
                        activeTaxiCards.addLast(t);
                        //If three taxis exist discard all 5
                        if (taxiCount == 3) {

                            //Remove all the cards from active to discard pile.
                            while (activeTaxiCards.size() > 0) {

                                discaredTaxis.addFirst(
                                        activeTaxiCards.removeFirst());
                            }

                            //Redraw 5 new cards
                            for (int k = 0; k < 5; k++) {

                                activeTaxiCards.addFirst(taxiCards.removeFirst());
                            }
                            taxiCount = 0;
                        }
                    }

                    //Adding dest cards to our deck
                    //TO DO ISSUE #6


                    //Set our current player to the youngest
                    currentPlayer = players.removeFirst();

                    //Repaint and end the method
                    repaint();
                }
            });
        }

        //Enter the help screen
        else if (e.getSource().equals(helpButton)) {

            //Set our current state to help menu
            currentState = GameState.values()[1];

            //Repaint and end the method
            repaint();
        }

        //Kill the program
        else if (e.getSource().equals(quitButton)) {

            //Repaint and end the method
            repaint();
        }

        //Switch the help messages
        else if (e.getSource().equals(switchButton)) {


            //Repaint and end the method
            repaint();
        }

        //Go back from help to the main menu
        else if (e.getSource().equals(backButton)) {

            //Set our current state to player selection
            currentState = GameState.values()[0];

            //Repaint and end the method
            repaint();
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
        MAIN_MENU, HELP_MENU, GAME_MENU, SCORE_MENU
    }
}
