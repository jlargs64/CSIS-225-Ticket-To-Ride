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
    private final int CARD_W = 100;
    private final int CARD_H = 50;

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
    private Rectangle taxiDeckRect, destDeckRect;
    private Shape[] roads;
    //Buttons
    private JButton playButton, helpButton, quitButton, backButton;
    private JButton switchButton;

    //Card objects
    private Deque<TaxiCard> taxiCards;
    private Deque<DestCard> destCards;
    private ArrayList<TaxiCard> activeTaxiCards;
    private ArrayList<TaxiCard> discaredTaxis;

    //Player related variables
    private Deque<Player> players = new LinkedList<>();
    private Player currentPlayer;

    //Used for turns
    private int pickUpCount = 0;

    //The constructor for Game Panel
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

        roads = new Shape[30];
        //Lincoln to Midtown West
        roads[0] = new Rectangle(81, 54, 15, 76);
        //Lincoln to Central Park
        roads[1] = new Rectangle(113, 23, 80, 15);
        //Lincoln to Times Square
        roads[2] = new Polygon(new int[]{99, 122, 158, 132},
                new int[]{53, 41, 107, 121}, 4);
        //Central to Times Square
        roads[3] = new Polygon(new int[]{159, 186, 213, 185},
                new int[]{109, 41, 50, 121}, 4);
        //Midtown to Times Square
        roads[4] = new Polygon(new int[]{100, 140, 142, 102},
                new int[]{135, 127, 140, 147}, 4);
        //Times Square to United Nations
        roads[5] = new Rectangle(193, 115, 80, 15);
        //Central Park to United Nations
        roads[6] = new Polygon(
                new int[]{225, 261, 267, 296, 300, 314, 300, 287, 259, 221},
                new int[]{24, 35, 39, 66, 71, 104, 109, 77, 50, 37},
                10);
        //Times Square to Empire St.
        roads[7] = new Rectangle(159, 148, 10, 10);
        //Midtown to Empire St.
        roads[8] = new Rectangle(101, 155, 10, 10);
        //United to Empire St.
        roads[9] = new Rectangle(214, 145, 10, 10);
        //Midtown to Chelsea
        roads[10] = new Rectangle(81, 169, 15, 76);
        //Chelsea to Empire St.
        roads[11] = new Rectangle(120, 205, 10, 10);
        //Empire St. to Gramercy Park
        roads[12] = new Rectangle(208, 217, 10, 10);
        //Chelsea to Gramercy Park
        roads[13] = new Rectangle(148, 250, 80, 20);
        //United Nations to Gramercy Park
        roads[14] = new Rectangle(294, 139, 10, 10);
        //Gramercy Park to Greenwich Village
        roads[15] = new Rectangle(234, 263, 10, 10);
        //Gramercy Park to East Village
        roads[16] = new Rectangle(270, 271, 10, 10);
        //Chelsea to Soho
        roads[17] = new Rectangle(96, 287, 10, 10);
        //Chelsea to Greenwich Village
        roads[18] = new Rectangle(114, 280, 10, 10);
        //Soho to Wall St.
        roads[19] = new Rectangle(164, 463, 10, 10);
        //Greenwich Village to Soho
        roads[20] = new Rectangle(158, 371, 30, 30);
        //Greenwich Village to Chinatown
        roads[21] = new Rectangle(223, 371, 10, 10);
        //East Village to Lower East Side
        roads[22] = new Rectangle(330, 361, 13, 40);
        //Greenwich to Lower East Side
        roads[23] = new Rectangle(250, 365, 10, 10);
        //Greenwich to East Village
        roads[24] = new Rectangle(247, 341, 80, 15);
        //Chinatown to Lower East Side
        roads[25] = new Rectangle(271, 417, 30, 20);
        //Wall St. to Chinatown
        roads[26] = new Rectangle(230, 470, 30, 40);
        //Chinatown to Brooklyn
        roads[27] = new Rectangle(271, 461, 10, 10);
        //Wall St. to Brooklyn
        roads[28] = new Rectangle(239, 531, 10, 10);
        //Lower East Side to Brooklyn
        roads[29] = new Rectangle(330, 430, 10, 10);
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

                //Make the font for text
                Font titleFont = new Font("Monospace", Font.BOLD, 16);
                g.setFont(titleFont);
                g.setColor(Color.WHITE);

                //Draw the background
                int bgWidth = woodenImage.getWidth(this);
                int bgHeight = woodenImage.getHeight(this);
                g.drawImage(woodenImage, 0, 0, bgWidth, bgHeight, this);

                //Layer the game map on top of background
                g.drawImage(gameMap, 20, 10, 400, 580, this);

                //Keep items in line
                int textXAxis = 460;
                int currentHandY = 100;

                //An array of card counts per type
                int[] amountOfCard = currentPlayer.getCardTypes();
                boolean[] hasBeenDrawn = new boolean[7];
                //Now draw the players hand
                for (int i = 0; i < currentPlayer.playerTaxis.size(); i++) {

                    TaxiCard card = currentPlayer.playerTaxis.get(i);
                    card.border.x = textXAxis;
                    card.border.y = currentHandY;
                    int w = card.border.width;
                    int h = card.border.height;

                    if (card.type.equalsIgnoreCase("BLUE")
                            && !hasBeenDrawn[0]) {

                        hasBeenDrawn[0] = true;
                        g.drawImage(card.cardImage, textXAxis, currentHandY, w,
                                h, this);
                        g.drawString("x" + amountOfCard[0],
                                textXAxis + 115, currentHandY + 30);
                        currentHandY += 60;
                    } else if (card.type.equalsIgnoreCase("GREEN")
                            && !hasBeenDrawn[1]) {

                        hasBeenDrawn[1] = true;
                        g.drawImage(card.cardImage, textXAxis, currentHandY, w,
                                h, this);
                        g.drawString("x" + amountOfCard[1],
                                textXAxis + 115, currentHandY + 30);
                        currentHandY += 60;
                    } else if (card.type.equalsIgnoreCase("BLACK")
                            && !hasBeenDrawn[2]) {

                        hasBeenDrawn[2] = true;
                        g.drawImage(card.cardImage, textXAxis, currentHandY, w,
                                h, this);
                        g.drawString("x" + amountOfCard[2],
                                textXAxis + 115, currentHandY + 30);
                        currentHandY += 60;
                    } else if (card.type.equalsIgnoreCase("PINK")
                            && !hasBeenDrawn[3]) {

                        hasBeenDrawn[3] = true;
                        g.drawImage(card.cardImage, textXAxis, currentHandY, w,
                                h, this);
                        g.drawString("x" + amountOfCard[3],
                                textXAxis + 115, currentHandY + 30);
                        currentHandY += 60;
                    } else if (card.type.equalsIgnoreCase("ORANGE")
                            && !hasBeenDrawn[4]) {

                        hasBeenDrawn[4] = true;
                        g.drawImage(card.cardImage, textXAxis, currentHandY, w,
                                h, this);
                        g.drawString("x" + amountOfCard[4],
                                textXAxis + 115, currentHandY + 30);
                        currentHandY += 60;
                    } else if (card.type.equalsIgnoreCase("RED")
                            && !hasBeenDrawn[5]) {

                        hasBeenDrawn[5] = true;
                        g.drawImage(card.cardImage, textXAxis, currentHandY, w,
                                h, this);
                        g.drawString("x" + amountOfCard[5],
                                textXAxis + 115, currentHandY + 30);
                        currentHandY += 60;
                    } else if (card.type.equalsIgnoreCase("RAINBOW")
                            && !hasBeenDrawn[6]) {

                        hasBeenDrawn[6] = true;
                        g.drawImage(card.cardImage, textXAxis, currentHandY, w,
                                h, this);
                        g.drawString("x" + amountOfCard[6],
                                textXAxis + 115, currentHandY + 30);
                        currentHandY += 60;
                    }
                }

                //Draw the current player stats:
                //Capitalize the first letter of the players name by default.
                String name = currentPlayer.name;
                String newName = name.substring(0, 1).toUpperCase()
                        + name.substring(1);

                String msg = newName + "\'s Turn";
                g.drawString(msg, textXAxis, 20);

                //Draw the turn number
                g.drawString("Turn Number: " + turnNum, textXAxis, 40);

                //Draw the amount of trains
                g.drawString("Taxis Left: " + currentPlayer.taxis,
                        textXAxis, 60);

                //Draw the current hand of the player
                g.drawString("Current Hand", textXAxis, 80);

                //Draw the cards available
                int activeCardX = textXAxis + 150;
                g.drawString("Available Cards", activeCardX, 80);

                int handY = 100;
                for (TaxiCard card : activeTaxiCards) {

                    card.border.x = activeCardX;
                    card.border.y = handY;
                    int w = card.border.width;
                    int h = card.border.height;
                    g.drawImage(card.cardImage, activeCardX, handY, w,
                            h, this);
                    handY += 60;
                }

                //Get the y axis for desired place to draw the decks for
                //taxis and destination cards.

                int cardY = height / 2 + 100;
                //Draw the dest card deck
                g.drawImage(destBackImg, activeCardX, cardY, CARD_W,
                        CARD_H, this);
                destDeckRect = new Rectangle(activeCardX, cardY, CARD_W,
                        CARD_H);
                cardY += 60;
                //Draw the card decks on the right hand side
                //To specify, these are the card decks that we can draw from
                g.drawImage(taxiBackImg, activeCardX, cardY, CARD_W,
                        CARD_H, this);
                taxiDeckRect = new Rectangle(activeCardX, cardY, CARD_W,
                        CARD_H);

                Graphics2D g2d = (Graphics2D) g;
                //Collision boxes for the graph
                for (int i = 0; i < roads.length; i++) {

                    g2d.draw(roads[i]);
                    //g.drawRect(roads[i].x, roads[i].y, roads[i].width,
                    //        roads[i].height);
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
            //PlayerSelectionFrame playerSelectForm =
            //       new PlayerSelectionFrame(players);
            //Prompt the users with the form
            //JDialog playerSelect = new JDialog(playerSelectForm);

            //We add a listener to see if the dialog window how been closed.
            //If it was closed it means it was successful or program exited.
            //playerSelectForm.addWindowListener(new WindowAdapter() {
            ///**
            //* Invoked when a window has been closed.
            //*
            //* @param e a window event object
            //*/
            //    @Override
            //    public void windowClosed(WindowEvent e) {
            //        super.windowClosed(e);

            //The code below belongs in here but for debugging purposes
            //I am leaving it out so the form doesn't have to be filled
            //out every time.
            //}
            //});

            //Set our current state to player selection
            currentState = GameState.values()[2];

            //Game Start Initialization
            //Initialize our array deques of cards
            taxiCards = new LinkedList<>();
            activeTaxiCards = new ArrayList<>();
            destCards = new LinkedList<>();
            discaredTaxis = new ArrayList<>();

            //Init turn number
            turnNum = 0;

            //Adding taxi cards to our deck
            //6 each color and 8 rainbow
            //Use a temporary ArrayList so that we can shuffle later.
            ArrayList<TaxiCard> tempCards = new ArrayList<>();
            for (int i = 0; i < 6; i++) {

                tempCards.add(new TaxiCard("blue", new
                        Rectangle(0, 0, CARD_W, CARD_H)));
                tempCards.add(new TaxiCard("green", new
                        Rectangle(0, 0, CARD_W, CARD_H)));
                tempCards.add(new TaxiCard("orange", new
                        Rectangle(0, 0, CARD_W, CARD_H)));
                tempCards.add(new TaxiCard("black", new
                        Rectangle(0, 0, CARD_W, CARD_H)));
                tempCards.add(new TaxiCard("pink", new
                        Rectangle(0, 0, CARD_W, CARD_H)));
                tempCards.add(new TaxiCard("red", new
                        Rectangle(0, 0, CARD_W, CARD_H)));
            }
            //Add the 8 locomotives
            for (int i = 0; i < 8; i++) {
                tempCards.add(new TaxiCard("rainbow", new
                        Rectangle(0, 0, CARD_W, CARD_H)));
            }
            //Shuffle the taxi card deck
            Collections.shuffle(tempCards);

            //Place our temp hand into the deque it handles like a card deck.
            //Add it to our deck
            taxiCards.addAll(tempCards);
            //We are done with this structure now
            tempCards.clear();

            //FOR DEBUGGING PURPOSES HERE IS A TEMP PLAYER DEQUE
            players.addLast(new Player("name", "blue", 19));
            players.addLast(new Player("name2", "white", 20));

            //Deal 2 taxi cards to all players
            for (Player p : players) {

                p.playerTaxis.add(taxiCards.removeFirst());
                p.playerTaxis.add(taxiCards.removeFirst());
            }

            //Deal 5 cards face up from top of deck
            for (int i = 0; i < 5; i++) {

                activeTaxiCards.add(taxiCards.removeFirst());
            }
            //Make sure there aren't more than 3 rainbow taxi's
            //otherwise discard
            int taxiCount = 0;
            for (TaxiCard card : activeTaxiCards) {

                //Count the amount of rainbow cards in the active deck.
                if (card.type.equalsIgnoreCase("rainbow")) {
                    taxiCount++;
                }
                //If three taxis exist discard all 5
                if (taxiCount == 3) {

                    //Remove all the cards from active to discard pile.
                    discaredTaxis.addAll(activeTaxiCards);
                    activeTaxiCards.clear();

                    //Redraw 5 new cards
                    for (int k = 0; k < 5; k++) {

                        activeTaxiCards.add(taxiCards.removeFirst());
                    }
                    taxiCount = 0;
                    break;
                }
            }

            //Adding dest cards to our deck
            //TO DO ISSUE #6


            //Set our current player to the youngest
            currentPlayer = players.removeFirst();

            //Repaint and end the method
            repaint();

            JOptionPane.showMessageDialog(this,
                    "It is now " + currentPlayer.name + "\'s turn");
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

        //This was for debugging where to put collision boxes over the routes
        int x = e.getX();
        int y = e.getY();
        System.out.println("X:" + x + " Y:" + y);

        Point pointClicked = e.getPoint();

        //This is for checking if we are picking up an active card
        for (TaxiCard c : activeTaxiCards) {

            //Only pick up a rainbow card if no other card has been picked up.
            if (c.border.contains(pointClicked)
                    && c.type.equalsIgnoreCase("rainbow")
                    && pickUpCount == 0) {

                //Add the card to the players hand
                currentPlayer.playerTaxis.add(c);
                //Remove the card from the active pile
                activeTaxiCards.remove(c);
                //Draw a new card for the active pile but only if there are
                //cards to be drawn from the deck
                if (taxiCards.size() > 0) {

                    activeTaxiCards.add(taxiCards.removeFirst());
                } else if (taxiCards.size() == 0 && discaredTaxis.size() > 0) {

                    Collections.shuffle(discaredTaxis);
                    for (TaxiCard card : discaredTaxis) {

                        taxiCards.addLast(card);
                    }
                    discaredTaxis.clear();
                }

                //Change players
                players.addLast(currentPlayer);
                currentPlayer = players.removeFirst();
                pickUpCount = 0;
                turnNum++;

                repaint();

                JOptionPane.showMessageDialog(this,
                        "It is now " + currentPlayer.name + "\'s turn");
                break;

            } else if (c.border.contains(pointClicked)
                    && !c.type.equalsIgnoreCase("rainbow")
                    && pickUpCount < 2) {

                //Add the card to the players hand
                currentPlayer.playerTaxis.add(c);

                //Remove the card from the active pile
                activeTaxiCards.remove(c);

                //Draw a new card for the active pile but only if there are
                //cards to be drawn from the deck
                if (taxiCards.size() > 0) {

                    activeTaxiCards.add(taxiCards.removeFirst());
                    repaint();
                } else if (taxiCards.size() == 0 && discaredTaxis.size() > 0) {

                    Collections.shuffle(discaredTaxis);
                    for (TaxiCard card : discaredTaxis) {

                        taxiCards.addLast(card);
                    }
                    discaredTaxis.clear();
                }
                //Add one card to our pick up since we can only take 2
                //So 2 colored cards or 1 rainbow taxi card
                pickUpCount++;

                if (pickUpCount == 2) {

                    //Change players
                    players.addLast(currentPlayer);
                    currentPlayer = players.removeFirst();
                    pickUpCount = 0;
                    turnNum++;
                    repaint();
                    JOptionPane.showMessageDialog(this,
                            "It is now " +
                                    currentPlayer.name + "\'s turn");
                }

                break;
            }
        }
        //If we are picking up a card from the top of the taxi deck
        if (taxiDeckRect.contains(pointClicked)) {

            //Check to make sure there are enough cards to draw
            if (taxiCards.size() > 0 && pickUpCount < 2) {

                //Get our new card.
                TaxiCard newCard = taxiCards.removeFirst();
                if (newCard.type.equalsIgnoreCase("rainbow")
                        && pickUpCount == 0) {

                    //Change players
                    players.addLast(currentPlayer);
                    currentPlayer = players.removeFirst();
                    pickUpCount = 0;
                    turnNum++;
                    repaint();
                    JOptionPane.showMessageDialog(this,
                            "It is now " + currentPlayer.name + "\'s turn");
                }
                //Add the new card to the player deck.
                currentPlayer.playerTaxis.add(newCard);
                pickUpCount++;
                repaint();
            }
            //Check to make sure after taking the last card our taxi deck is
            //not empty, if it is, move the discarded pile into the taxi deck.
            if (taxiCards.size() == 0 && discaredTaxis.size() > 0) {

                Collections.shuffle(discaredTaxis);
                for (TaxiCard card : discaredTaxis) {

                    taxiCards.addLast(card);
                }
                discaredTaxis.clear();
            }

            //If we hit the max amount of cards to pick up.
            if (pickUpCount == 2) {

                //Change players
                players.addLast(currentPlayer);
                currentPlayer = players.removeFirst();
                pickUpCount = 0;
                turnNum++;
                repaint();
                JOptionPane.showMessageDialog(this,
                        "It is now " + currentPlayer.name + "\'s turn");
            }
        }
        //Draw 2 destination cards, the player can keep 1 or both.
        else if (destDeckRect.contains(pointClicked)) {

        }
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
