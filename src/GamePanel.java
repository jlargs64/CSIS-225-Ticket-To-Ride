import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import java.util.List;
import javax.sound.sampled.*;

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
    String theme = "assets/TicketToRide-TitleTheme.wav";
    //Game state related variables
    private GameState currentState;
    private Graph map;
    private Toolkit toolkit;
    private int turnNum;
    //Image related variables
    private Image mainMenuImg, woodenImage, gameMap, scoreCard;
    private Image taxiBackImg, destBackImg;
    private Image[] helpImages = new Image[2];
    private int currentHelpImage = 0;
    private Rectangle taxiDeckRect, destDeckRect;
    private ArrayList<Route> routes;
    private Rectangle[] districts;
    private int districtClicked;
    //Buttons
    private JButton playButton, helpButton, quitButton, backButton;
    private JButton switchButton;
    //Card objects
    private Deque<TaxiCard> taxiCards;
    private Deque<DestCard> destCards;
    private ArrayList<TaxiCard> activeTaxiCards;
    private ArrayList<TaxiCard> discardedTaxis;
    //Player related variables
    private Deque<Player> players = new LinkedList<>();
    private Player currentPlayer;
    //Used for turns
    private int pickUpCount = 0;
    private boolean lessThanTwoTaxis;
    private Player lastPlayer;

    //Used for claimed edges
    private int E;

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

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream
                    (new File(theme).getCanonicalFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception ex) {
            System.err.println("Error playing theme");
            ex.printStackTrace();
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

        //Init our routes array
        routes = new ArrayList<>();

        //Lincoln to Central Park
        routes.add(new Route(
                0,
                1,
                new Rectangle(113, 23, 80, 15)));
        //Lincoln to Midtown West
        routes.add(new Route(
                0,
                2,
                new Rectangle(81, 54, 15, 76)));
        //Lincoln to Times Square Green
        routes.add(new Route(
                Color.GREEN,
                0,
                3,
                new Polygon(new int[]{99, 110, 145, 133},
                        new int[]{55, 47, 115, 119}, 4)));
        //Lincoln to Times Square Blue
        routes.add(new Route(
                Color.BLUE,
                0,
                3,
                new Polygon(new int[]{111, 124, 158, 147},
                        new int[]{46, 40, 106, 114}, 4)));
        //Central to Times Square Black
        routes.add(new Route(
                Color.BLACK,
                1,
                3,
                new Polygon(new int[]{187, 199, 172, 160},
                        new int[]{40, 44, 114, 109}, 4)));
        //Central to Times Square Red
        routes.add(new Route(
                Color.RED,
                1,
                3,
                new Polygon(new int[]{200, 214, 188, 174},
                        new int[]{45, 49, 121, 115}, 4)));
        //Midtown to Times Square
        routes.add(new Route(
                2,
                3,
                new Polygon(new int[]{100, 140, 142, 102},
                        new int[]{135, 127, 140, 147}, 4)));
        //Times Square to United Nations
        routes.add(new Route(
                3,
                4,
                new Rectangle(193, 115, 80, 15)));
        //Central Park to United Nations
        routes.add(new Route(
                1,
                4,
                new Polygon(
                        new int[]{225, 261, 267, 296, 300, 314, 300, 287,
                                259, 221},
                        new int[]{24, 35, 39, 66, 71, 104, 109, 77, 50, 37},
                        10)));
        //Times Square to Empire St. Orange
        routes.add(new Route(
                Color.ORANGE,
                3,
                6,
                new Polygon(new int[]{157, 170, 193, 180},
                        new int[]{153, 145, 176, 183}, 4)));
        //Times Square to Empire St. Pink
        routes.add(new Route(
                Color.PINK,
                3,
                6,
                new Polygon(new int[]{171, 183, 206, 194},
                        new int[]{147, 140, 169, 175}, 4)));
        //Midtown to Empire St.
        routes.add(new Route(
                2,
                6,
                new Polygon(new int[]{98, 169, 176, 103},
                        new int[]{163, 196, 183, 151}, 4)));
        //United to Empire St.
        routes.add(new Route(
                4,
                6,
                new Polygon(new int[]{212, 278, 288, 219},
                        new int[]{173, 133, 144, 184}, 4)));
        //Midtown to Chelsea
        routes.add(new Route(
                2,
                5,
                new Polygon(new int[]{82, 95, 116, 101},
                        new int[]{169, 165, 237, 242}, 4)));
        //Chelsea to Empire St. Clear
        routes.add(new Route(
                Color.WHITE,
                5,
                6,
                new Polygon(new int[]{118, 182, 191, 126},
                        new int[]{235, 191, 202, 247}, 4)));
        //Chelsea to Empire St. Clear
        routes.add(new Route(
                Color.WHITE,
                5,
                6,
                new Polygon(new int[]{128, 191, 201, 135},
                        new int[]{249, 204, 214, 259}, 4)));
        //Empire St. to Gramercy Park Red
        routes.add(new Route(
                Color.RED,
                6,
                7,
                new Polygon(new int[]{205, 217, 239, 229},
                        new int[]{219, 212, 241, 246}, 4)));
        //Empire St. to Gramercy Park Blue
        routes.add(new Route(
                Color.BLUE,
                6,
                7,
                new Polygon(new int[]{218, 229, 251, 240},
                        new int[]{210, 203, 232, 238}, 4)));
        //Chelsea to Gramercy Park
        routes.add(new Route(
                5,
                7,
                new Polygon(new int[]{148, 228, 229, 147},
                        new int[]{255, 250, 263, 269}, 4)));
        //United Nations to Gramercy Park
        routes.add(new Route(
                4,
                7,
                new Polygon(
                        new int[]{298, 315, 308, 291, 274, 260, 279, 292},
                        new int[]{136, 138, 176, 216, 248, 239, 210, 172},
                        8)));
        //Gramercy Park to Greenwich Village Black
        routes.add(new Route(
                Color.BLACK,
                7,
                8,
                new Polygon(new int[]{236, 250, 232, 219},
                        new int[]{260, 263, 335, 331}, 4)));
        //Gramercy Park to Greenwich Village Pink
        routes.add(new Route(
                Color.PINK,
                7,
                8,
                new Polygon(new int[]{250, 264, 246, 234},
                        new int[]{263, 266, 338, 335}, 4)));
        //Gramercy Park to East Village
        routes.add(new Route(
                7,
                10,
                new Polygon(new int[]{270, 282, 333, 320},
                        new int[]{271, 262, 321, 331}, 4)));
        //Chelsea to Soho
        routes.add(new Route(
                5,
                9,
                new Polygon(new int[]{94, 108, 162, 146},
                        new int[]{285, 281, 425, 429}, 4)));
        //Chelsea to Greenwich Village Green
        routes.add(new Route(
                Color.GREEN,
                5,
                8,
                new Polygon(new int[]{112, 122, 206, 198},
                        new int[]{281, 271, 347, 355}, 4)));
        //Chelsea to Greenwich Village Red
        routes.add(new Route(
                Color.RED,
                5,
                8,
                new Polygon(new int[]{123, 132, 218, 208},
                        new int[]{270, 261, 336, 346}, 4)));
        //Soho to Wall St.
        routes.add(new Route(
                9,
                13,
                new Polygon(new int[]{163, 176, 216, 202},
                        new int[]{462, 454, 521, 528}, 4)));
        //Greenwich Village to Soho
        routes.add(new Route(
                8,
                9,
                new Polygon(
                        new int[]{204, 219, 212, 208, 181, 171, 196},
                        new int[]{369, 371, 409, 415, 444, 432, 404},
                        7)));
        //Greenwich Village to Chinatown Clear
        routes.add(new Route(
                Color.WHITE,
                8,
                11,
                new Polygon(new int[]{221, 233, 254, 240},
                        new int[]{371, 367, 440, 444}, 4)));
        //Greenwich Village to Chinatown Clear
        routes.add(new Route(
                Color.WHITE,
                8,
                11,
                new Polygon(new int[]{234, 248, 268, 256},
                        new int[]{369, 365, 435, 440}, 4)));
        //East Village to Lower East Side
        routes.add(new Route(
                10,
                12,
                new Polygon(new int[]{329, 344, 340, 325},
                        new int[]{361, 363, 400, 399}, 4)));
        //Greenwich to Lower East Side
        routes.add(new Route(
                8,
                12,
                new Polygon(new int[]{250, 258, 321, 312},
                        new int[]{367, 358, 400, 411}, 4)));
        //Greenwich to East Village
        routes.add(new Route(
                8,
                10,
                new Rectangle(247, 341, 80, 15)));
        //Chinatown to Lower East Side
        routes.add(new Route(
                11,
                12,
                new Polygon(new int[]{271, 303, 314, 280},
                        new int[]{439, 416, 428, 448}, 4)));
        //Wall St. to Chinatown Green
        routes.add(new Route(
                Color.GREEN,
                13,
                11,
                new Polygon(new int[]{238, 252, 238, 226},
                        new int[]{472, 477, 511, 507}, 4)));
        //Wall St. to Chinatown Pink
        routes.add(new Route(
                Color.PINK,
                13,
                11,
                new Polygon(new int[]{253, 267, 253, 241},
                        new int[]{477, 481, 515, 511}, 4)));
        //Chinatown to Brooklyn Red
        routes.add(new Route(
                Color.RED,
                11,
                14,
                new Polygon(new int[]{264, 272, 366, 359},
                        new int[]{473, 464, 534, 541}, 4)));
        //Chinatown to Brooklyn Orange
        routes.add(new Route(
                Color.ORANGE,
                11,
                14,
                new Polygon(new int[]{275, 282, 376, 367},
                        new int[]{462, 452, 521, 532}, 4)));
        //Wall St. to Brooklyn Black
        routes.add(new Route(
                Color.BLACK,
                13,
                14,
                new Polygon(new int[]{236, 237, 356, 355},
                        new int[]{556, 543, 557, 570}, 4)));
        //Wall St. to Brooklyn Blue
        routes.add(new Route(
                Color.BLUE,
                13,
                14,
                new Polygon(new int[]{238, 240, 357, 356},
                        new int[]{542, 530, 543, 556}, 4)));
        //Lower East Side to Brooklyn
        routes.add(new Route(
                12,
                14,
                new Polygon(
                        new int[]{331, 341, 363, 382, 396, 381, 369, 353},
                        new int[]{433, 424, 451, 487, 525, 529, 494, 462},
                        8)));

        //Making the districts clickable
        districts = new Rectangle[15];
        int boxSize = 20;

        //0- Lincoln Center
        districts[0] = new Rectangle(87, 28, boxSize, boxSize);
        //1- Central Park
        districts[1] = new Rectangle(200, 19, boxSize, boxSize);
        //2- Midtown West
        districts[2] = new Rectangle(73, 137, boxSize, boxSize);
        //3- Times Square
        districts[3] = new Rectangle(150, 120, boxSize, boxSize);
        //4- United Nations
        districts[4] = new Rectangle(295, 115, boxSize, boxSize);
        //5- Chelsea
        districts[5] = new Rectangle(99, 247, boxSize, boxSize);
        //6- Empire State Building
        districts[6] = new Rectangle(193, 186, boxSize, boxSize);
        //7- Gramercy Park
        districts[7] = new Rectangle(241, 239, boxSize, boxSize);
        //8- Greenwich Village
        districts[8] = new Rectangle(217, 341, boxSize, boxSize);
        //9- Soho
        districts[9] = new Rectangle(151, 434, boxSize, boxSize);
        //10- East Village
        districts[10] = new Rectangle(331, 332, boxSize, boxSize);
        //11- China Town
        districts[11] = new Rectangle(244, 451, boxSize, boxSize);
        //12- Lower East Side
        districts[12] = new Rectangle(318, 405, boxSize, boxSize);
        //13- Wall St.
        districts[13] = new Rectangle(214, 526, boxSize, boxSize);
        //14- Brooklyn
        districts[14] = new Rectangle(368, 540, boxSize, boxSize);
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
        // helpImage 1
        imgFileLocation = "assets\\instructions-1.jpg";
        helpImages[0] = toolkit.getImage(imgFileLocation);
        // helpImage 2
        imgFileLocation = "assets\\instructions-2.jpg";
        helpImages[1] = toolkit.getImage(imgFileLocation);

        //Switch case for dis1laying different states
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
                //display images
                // helpImage 1
                g.drawImage(helpImages[currentHelpImage], 0, 0,
                        800, 600, this);
                // helpImage 2
                /*
                imgFileLocation = "assets\\instructions-2.jpg";
                helpImages[1] = toolkit.getImage(imgFileLocation);
                g.drawImage(helpImages[1], 0, 0, 800, 600, this);
                */

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
                int totalCards = taxiCards.size() + activeTaxiCards.size();
                g.drawString("Available Cards-" + totalCards,
                        activeCardX, 80);

                int handY = 100;
                for (int i = 0; i < activeTaxiCards.size(); i++) {

                    TaxiCard card = activeTaxiCards.get(i);
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

                //Draw all the players claimed routes
                for (Player p : players) {

                    g.setColor(p.COLOR);
                    ArrayList<Route> playerRoutes = p.routes;
                    for (int i = 0; i < playerRoutes.size(); i++) {
                        g2d.fill(playerRoutes.get(i).img);
                    }
                }
                //Draw the current players claimed routes
                ArrayList<Route> playerRoutes = currentPlayer.routes;
                g.setColor(currentPlayer.COLOR);
                for (int i = 0; i < playerRoutes.size(); i++) {
                    g2d.fill(playerRoutes.get(i).img);
                }

                //Show what district is currently selected
                if (districtClicked != -1) {

                    g2d.draw(districts[districtClicked]);
                }
                //FOR DEBUGGING PURPOSES
                /*for(Route r : routes){

                    g2d.fill(r.img);
                }*/
                //UNCOMMENT ONLY FOR DEBUGGING PURPOSES
                //Draw the colliders around the districts
                /*for (Rectangle r : districts) {

                    g2d.draw(r);
                }*/

                break;
            case SCORE_MENU:

                //This is where we show who won the game with the scorecard
                //There will be some animations that we can add later.
                //REFER TO ISSUE #8
                Player winner = players.getFirst();
                for (Player p : players) {

                    //Add points for attractions
                    p.points += p.claimedRoutes.numAttractions();

                    //Add points for completed destination cards, subtract for incomplete destination cards
                    for (DestCard c : p.playerDestCards){
                        if (p.claimedRoutes.findPath(c.startDistrict, c.endDistrict)
                                || p.claimedRoutes.findPath(c.endDistrict, c.startDistrict)){
                            p.points+= c.worth;
                        }
                        else {
                            p.points = p.points-c.worth;
                        }
                    }

                    //Determine the winner
                    if (p.points>winner.points){
                        winner = p;
                    }

                }

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
            discardedTaxis = new ArrayList<>();

            //Init turn number
            turnNum = 0;

            //Init our district clicked
            districtClicked = -1;
            lessThanTwoTaxis = false;

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
            players.addLast(new Player("Person1", "blue", 19));
            players.addLast(new Player("Person2", "white", 20));
            players.addLast(new Player("Person3", "yellow", 21));
            players.addLast(new Player("Person4", "purple", 22));

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
                    discardedTaxis.addAll(activeTaxiCards);
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
            List<DestCard> myDestCards = new ArrayList<DestCard>();
            try {
                //Read in the destination card data
                Scanner destCardsScan = new Scanner(
                        new File("destCards.txt"));

                while (destCardsScan.hasNextLine()) {

                    //String tokenizer to parse the string
                    StringTokenizer destCardInfo = new
                            StringTokenizer(destCardsScan.nextLine());

                    //How much the destination card is worth
                    int worth = Integer.valueOf(destCardInfo.nextToken());

                    //Int representing the start city
                    int startDistrict = Integer.valueOf(
                            destCardInfo.nextToken());

                    //Int representing the end city
                    int endDistrict = Integer.valueOf(destCardInfo.nextToken());

                    //String representing the path to the image we need
                    String cardNum = destCardInfo.nextToken();
                    myDestCards.add(new DestCard(worth, startDistrict,
                            endDistrict, cardNum));
                }
            } catch (Exception err) {
                err.printStackTrace();
            }

            //Shuffle the destination card deck and place it into the deck
            Collections.shuffle(myDestCards);
            for (int i = 0; i < myDestCards.size(); i++) {
                destCards.addLast(myDestCards.get(i));
            }

            //DEAL THE DESTINATION CARDS TO EACH PLAYER
            //REFER TO ISSUE #10 Fix to make option to take 1 or both
            for (Player p : players) {

                p.playerDestCards.add(destCards.removeFirst());
                p.playerDestCards.add(destCards.removeFirst());
            }

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
            if (currentHelpImage == 0) {

                currentHelpImage = 1;
            } else {

                currentHelpImage = 0;
            }


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
        //int x = e.getX();
        //int y = e.getY();
        //System.out.println("X:" + x + " Y:" + y);

        Point pointClicked = e.getPoint();

        //This is for checking if we are picking up an active card
        for (int i = 0; i < activeTaxiCards.size(); i++) {

            TaxiCard c = activeTaxiCards.get(i);
            //Only pick up a rainbow card if no other card has been picked up.
            if (c.border.contains(pointClicked)
                    && c.type.equalsIgnoreCase("RAINBOW")
                    && pickUpCount == 0) {

                //Add the card to the players hand
                currentPlayer.playerTaxis.add(c);
                //Remove the card from the active pile
                activeTaxiCards.remove(c);
                //Draw a new card for the active pile but only if there are
                //cards to be drawn from the deck
                if (taxiCards.size() > 0) {

                    activeTaxiCards.add(taxiCards.removeFirst());

                    repaint();
                }

                //If the last card was picked up and there are no cards left,
                //continue the game because it will stop it otherwise.
                if (pickUpCount == 1 && (activeTaxiCards.size() == 0
                        && taxiCards.size() == 0)) {

                    //Change players
                    pickUpCount = 0;
                    changeTurns();
                }

                //Change players
                pickUpCount = 0;
                changeTurns();
                return;

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
                }

                //If the last card was picked up and there are no cards left,
                //continue the game because it will stop it otherwise.
                if (pickUpCount == 1 && (activeTaxiCards.size() == 0
                        && taxiCards.size() == 0)) {

                    //Change players
                    pickUpCount = 0;
                    changeTurns();
                }

                //Add one card to our pick up since we can only take 2
                //So 2 colored cards or 1 rainbow taxi card
                pickUpCount++;

                if (pickUpCount == 2) {

                    //Change players
                    pickUpCount = 0;
                    changeTurns();
                    return;
                }

                break;
            }
        }
        //If we are picking up a card from the top of the taxi deck
        if (taxiDeckRect.contains(pointClicked)) {

            //Do nothing
            if (taxiCards.size() == 0) {
                return;
            }
            //Check to make sure there are enough cards to draw
            if (taxiCards.size() > 0) {

                //Get our new card.
                TaxiCard newCard = taxiCards.removeFirst();
                if (newCard.type.equalsIgnoreCase("RAINBOW")
                        && pickUpCount == 0) {

                    //Change players
                    pickUpCount = 0;
                    changeTurns();
                } else if (newCard.type.equalsIgnoreCase("RAINBOW")
                        && pickUpCount == 1) {
                    //We can't pick up rainbow so discard and prompt user to
                    //pick another card
                    discardedTaxis.add(newCard);

                    JOptionPane.showMessageDialog(this,
                            "You can't pick up a rainbow since " +
                                    "you already picked up a card! Draw a new" +
                                    "card!");
                    return;
                } else {

                    //Add the new card to the player deck.
                    currentPlayer.playerTaxis.add(newCard);
                    pickUpCount++;
                }

                repaint();
            }

            //If we hit the max amount of cards to pick up.
            if (pickUpCount == 2) {

                //Change players
                pickUpCount = 0;
                changeTurns();
            }
        }
        //Draw 2 destination cards, the player can keep 1 or both.
        else if (destDeckRect.contains(pointClicked)) {
            //Do something
            //ISSUE #10
        }

        //After everything happens check for 2 things
        //1) That the taxi card deck is not empty
        if (taxiCards.size() == 0 && discardedTaxis.size() > 0) {

            JOptionPane.showMessageDialog(this, "Deck " +
                    "has been redistributed due to the transport cards being " +
                    "empty!");
            //Shuffle the discards
            Collections.shuffle(discardedTaxis);
            //Add the discards into the transport card deck
            for (int i = 0; i < discardedTaxis.size(); i++) {

                taxiCards.addLast(discardedTaxis.get(i));
            }

            //If the amount dealt to our active card deck is less than 5
            //then just add all of the taxi cards to active
            if (taxiCards.size() < 5) {

                for (int j = 0; j < taxiCards.size(); j++) {
                    if (activeTaxiCards.size() == 5) {
                        repaint();
                        break;
                    }
                    activeTaxiCards.add(taxiCards.removeFirst());
                }
            } else {
                //Since our new transport deck is greater than 5
                //add only 5 to the active deck
                for (int j = 0; j < 5; j++) {
                    if (activeTaxiCards.size() == 5) {
                        repaint();
                        break;
                    }
                    activeTaxiCards.add(taxiCards.removeFirst());
                }
            }

            //Empty out discarded
            discardedTaxis.clear();
            repaint();
            return;
        }
        //2) If there are 3 or more taxi cards in the deck
        //Make sure there aren't more than 3 rainbow taxi's
        //otherwise discard
        int taxiCount = 0;
        for (int i = 0; i < activeTaxiCards.size(); i++) {

            //Get the current card
            TaxiCard card = activeTaxiCards.get(i);

            //Count the amount of rainbow cards in the active deck.
            if (card.type.equalsIgnoreCase("rainbow")) {
                taxiCount++;
            }
            //If three taxis exist discard all 5
            if (taxiCount == 3) {

                //Remove all the cards from active to discard pile.
                discardedTaxis.addAll(activeTaxiCards);
                activeTaxiCards.clear();

                if (taxiCards.size() == 0) {

                    repaint();
                    return;
                } else if (taxiCards.size() < 5) {
                    //Redraw 5 new cards
                    for (int k = 0; k < taxiCards.size(); k++) {

                        activeTaxiCards.add(taxiCards.removeFirst());
                    }

                    repaint();
                    return;
                } else {

                    //Redraw 5 new cards
                    for (int k = 0; k < 5; k++) {

                        activeTaxiCards.add(taxiCards.removeFirst());
                    }
                    repaint();
                    return;
                }
            }
        }

        //Check if a route is being claimed
        findRoute(pointClicked);
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

    /***
     * This method searches for which district was clicked on and if a pair
     * occurs it will execute claimRoute
     * @param pointClicked the point where the mouse clicked
     */
    public void findRoute(Point pointClicked) {

        if (pickUpCount == 0) {
            for (int i = 0; i < districts.length; i++) {
                if (districts[i].contains(pointClicked)) {

                    if (districtClicked != -1) {

                        //Claim the route
                        claimRoute(i);

                        //Reset district clicked back to -1
                        districtClicked = -1;
                        repaint();
                    } else {
                        districtClicked = i;
                        repaint();
                    }
                    break;
                }
            }
        }
    }

    /***
     * This method is executed when a pair has been found between two vertices
     * and will try to see if a connection can be made given the taxis and
     * cards.
     * @param index the end district in our graph
     */
    public void claimRoute(int index) {

        int endIndex = index;

        //Used to help organize the code of removing cards
        boolean canAfford = false;
        boolean connectionFound = false;
        String selectedColor = "";
        //We find out if we can claim this route
        Graph.Edge finger = map.vertices[districtClicked].firstEdge;

        //We use was found to display a message to user if it was a valid
        //move or not.
        while (finger != null) {

            //We found the route
            if (finger.dest == endIndex) {

                connectionFound = true;
                //The player can afford the route
                if (currentPlayer.taxis - finger.cost >= 0) {

                    canAfford = true;
                }
                break;
            }
            //Check the next edge
            finger = finger.next;
        }

        //If there is no connection in the graph then prompt user
        if (!connectionFound) {

            JOptionPane.showMessageDialog(this,
                    "Invalid districts clicked!");
            return;
        }

        //Check if the route has already been claimed by current player
        ArrayList<Route> playerRoutes = currentPlayer.routes;
        for (int i = 0; i < playerRoutes.size(); i++) {

            Route currentRoute = playerRoutes.get(i);
            //We found matching start
            boolean currentCoord = currentRoute.start == districtClicked
                    && currentRoute.end == endIndex;
            boolean flippedCoord = currentRoute.end == districtClicked
                    && currentRoute.start == endIndex;
            if (currentCoord || flippedCoord) {

                if (currentRoute.color != finger.color) {

                    //Already claimed so exit
                    JOptionPane.showMessageDialog(this,
                            "Route already claimed!");
                    return;
                }
            }
        }


        if (canAfford) {

            int[] cardTypeNum = currentPlayer.getCardTypes();

            //If the player doesn't have enough cards in deck to even
            //Cover the cost
            if (currentPlayer.playerTaxis.size() < finger.cost) {

                JOptionPane.showMessageDialog(this,
                        "Not enough cards to claim!");
                return;
            }

            //If the route color is specified
            if (finger.color != Color.WHITE) {

                //Check to see if it is a double
                Graph.Edge findDoubleFinger =
                        map.vertices[endIndex].firstEdge;
                ArrayList<Color> doubleRouteColors = new ArrayList<>();

                boolean isDouble = false;
                while (findDoubleFinger != null) {

                    //Stop searching if we found our colors
                    if (doubleRouteColors.size() == 2) {

                        //Since both spots are full it is a double route
                        isDouble = true;
                        break;
                    }

                    //If we have a match in the graph
                    if (findDoubleFinger.dest == districtClicked
                            || findDoubleFinger.dest == endIndex) {

                        //Add selected color to ArrayList
                        doubleRouteColors.add(findDoubleFinger.color);
                    }
                    //Traverse the graph for a double route
                    findDoubleFinger = findDoubleFinger.next;
                }

                //Check to see if the player is trying to claim a route using
                //colors they don't have
                boolean noCardType = true;
                for (int i = 0; i < doubleRouteColors.size(); i++) {

                    String routeColor = colorToString(
                            doubleRouteColors.get(i));
                    for (int j = 0; j < currentPlayer.playerTaxis.size(); j++) {

                        //If the player has a card to claim a route selected
                        // then continue to claim it
                        TaxiCard currentCard = currentPlayer.playerTaxis.get(j);

                        //If the card equals a rainbow or one of the colors to
                        //select than we continue
                        if (currentCard.type.equalsIgnoreCase(routeColor)
                                || currentCard.type.equalsIgnoreCase(
                                "RAINBOW")) {

                            noCardType = false;
                            break;
                        }
                    }
                }

                //Prompt the user that the card types in deck don't match
                //the ones that they want to claim.
                if (noCardType) {

                    JOptionPane.showMessageDialog(this,
                            "Incorrect card type!");
                    return;
                }

                //Here we check to see of the colors we can choose one,
                //which color we can actually draw from.
                for (int i = 0; i < doubleRouteColors.size(); i++) {

                    int colorNum = colorToNumber(doubleRouteColors.get(i));
                    int sumOfType = cardTypeNum[colorNum] + cardTypeNum[6];
                    //If the color cannot claim the route by itself or combined
                    //with a rainbow card then don't consider it as a choice
                    if (cardTypeNum[colorNum] < finger.cost
                            && sumOfType < finger.cost) {
                        doubleRouteColors.remove(i);
                    }
                }

                if (doubleRouteColors.size() == 0) {

                    JOptionPane.showMessageDialog(this,
                            "Incorrect amount of cards!");
                    return;
                }

                ArrayList<String> strColors = new ArrayList<>();
                for (int i = 0; i < doubleRouteColors.size(); i++) {

                    strColors.add(colorToString(doubleRouteColors.get(i)));
                }

                if (isDouble) {

                    Object[] colors = strColors.toArray();
                    //Select the correct color
                    Object selectedCardType =
                            JOptionPane.showInputDialog(
                                    null,
                                    "Choose preferred card type",
                                    "Card Selection",
                                    JOptionPane.INFORMATION_MESSAGE,
                                    null,
                                    colors, colors[0]);
                    selectedColor = selectedCardType.toString();
                } else {

                    selectedColor = strColors.get(0);
                }

                //Get the routes color in number form
                int routeColor = -1;

                //Find the route color
                Graph.Edge routeFinger =
                        map.vertices[districtClicked].firstEdge;

                //Search the graph for the specific route we need
                while (routeFinger != null) {

                    //If the route is equal to the dest and color then select it
                    if (routeFinger.dest == endIndex &&
                            routeFinger.strColor.equals(selectedColor)) {

                        routeColor = colorToNumber(routeFinger.color);
                        break;
                    }
                    routeFinger = routeFinger.next;
                }

                //For the type + rainbow condition
                int sumOfType = cardTypeNum[routeColor] + cardTypeNum[6];
                //Cost of the finger
                int costLeft = finger.cost;

                //There is not enough of the singular cards type to
                //claim by itself and not enough if combined
                if (cardTypeNum[routeColor] < costLeft
                        && sumOfType < costLeft) {

                    JOptionPane.showMessageDialog(this,
                            "Not enough cards to claim!");
                    return;
                }

                //If the route can be claimed with just the specified card
                if (cardTypeNum[routeColor] >= costLeft) {

                    for (int i = 0; i <
                            currentPlayer.playerTaxis.size(); i++) {

                        //To prevent discarding more cards than needed
                        if (costLeft == 0) {
                            break;
                        }

                        TaxiCard t = currentPlayer.playerTaxis.get(i);

                        if (t.type.equalsIgnoreCase(
                                numberToColor(routeColor))) {

                            discardedTaxis.add(t);
                            currentPlayer.playerTaxis.remove(t);
                            currentPlayer.taxis--;
                            costLeft--;
                            i = -1;
                        }
                    }
                }
                //If the route can be claimed with only rainbow
                else if (cardTypeNum[6] >= costLeft) {

                    for (int i = 0; i <
                            currentPlayer.playerTaxis.size(); i++) {

                        //To prevent discarding more cards than needed
                        if (costLeft == 0) {
                            break;
                        }

                        TaxiCard t = currentPlayer.playerTaxis.get(i);

                        if (t.type.equalsIgnoreCase("RAINBOW")) {

                            discardedTaxis.add(t);
                            currentPlayer.playerTaxis.remove(t);
                            currentPlayer.taxis--;
                            i = -1;
                        }
                    }
                } else {

                    //Start removing using our selected color
                    for (int i = 0; i <
                            currentPlayer.playerTaxis.size(); i++) {

                        //To prevent discarding more cards than needed
                        if (costLeft == 0) {
                            break;
                        }

                        TaxiCard t = currentPlayer.playerTaxis.get(i);

                        if (t.type.equalsIgnoreCase(
                                numberToColor(routeColor))) {

                            discardedTaxis.add(t);
                            currentPlayer.playerTaxis.remove(t);
                            currentPlayer.taxis--;
                            costLeft--;
                            i = -1;
                        }
                    }
                    //If there is still a cost left it means we are using
                    //rainbow to make up the left over cost
                    if (costLeft > 0) {
                        for (int i = 0; i <
                                currentPlayer.playerTaxis.size(); i++) {

                            //To prevent discarding more cards than needed
                            if (costLeft == 0) {
                                break;
                            }
                            TaxiCard t = currentPlayer.playerTaxis.get(i);

                            if (t.type.equalsIgnoreCase("RAINBOW")) {

                                discardedTaxis.add(t);
                                currentPlayer.playerTaxis.remove(t);
                                currentPlayer.taxis--;
                                costLeft--;
                                i = -1;
                            }
                        }
                    }
                }
            } else {

                //The route is clear
                //The colors we can choose from
                ArrayList<String> colors = new ArrayList<>();

                //Use the contains method here to detect if it is a double

                //Find which cards can remove something
                for (int i = 0; i < cardTypeNum.length; i++) {

                    //For card type + rainbow condition
                    int sumOfType = cardTypeNum[i] + cardTypeNum[6];

                    //If the card type by itself is sufficient
                    if (cardTypeNum[i] >= finger.cost) {

                        colors.add(numberToColor(i));
                    }
                    //If the sumOfType is sufficient and not considering if
                    //there are none of that type and the type isn't already a
                    //rainbow.
                    else if (i != 6 && cardTypeNum[i] != 0
                            && sumOfType >= finger.cost) {

                        colors.add(numberToColor(i));
                    }
                }
                //If we don't have enough cards to fulfill them individually
                if (colors.size() == 0) {

                    JOptionPane.showMessageDialog(this,
                            "Not enough cards to claim!");
                    return;
                }

                Object[] colorsArray = colors.toArray();
                //Select the correct color
                Object selectedCardType =
                        JOptionPane.showInputDialog(
                                null,
                                "Choose preferred card type",
                                "Card Selection",
                                JOptionPane.INFORMATION_MESSAGE,
                                null,
                                colorsArray, colorsArray[0]);

                //If there is a null value assume the player didn't
                //want to place it
                if (selectedCardType == null) {
                    return;
                }
                selectedColor = selectedCardType.toString();
                int costLeft = finger.cost;
                //Start removing using our selected color
                for (int i = 0; i < currentPlayer.playerTaxis.size(); i++) {

                    //Stop taking cards if cost has been met
                    if (costLeft == 0) {
                        break;
                    }

                    //Select our card
                    TaxiCard t = currentPlayer.playerTaxis.get(i);

                    //Remove it from deck if we find the match with route color
                    if (t.type.equalsIgnoreCase(selectedColor)) {

                        discardedTaxis.add(t);
                        currentPlayer.playerTaxis.remove(t);
                        currentPlayer.taxis--;
                        costLeft--;
                        i = -1;
                    }
                }
                //If there is still a cost left it means we are using rainbow
                //to make up the left over cost
                if (costLeft > 0) {
                    for (int i = 0; i < currentPlayer.playerTaxis.size(); i++) {

                        //Stop removing cards if we have met the cost
                        if (costLeft == 0) {
                            break;
                        }

                        //Select our card
                        TaxiCard t = currentPlayer.playerTaxis.get(i);

                        //Discard card if found specific type
                        if (t.type.equalsIgnoreCase("RAINBOW")) {

                            discardedTaxis.add(t);
                            currentPlayer.playerTaxis.remove(t);
                            currentPlayer.taxis--;
                            costLeft--;
                            i = -1;
                        }
                    }
                }

            }

            //Add to the players graph
            currentPlayer.claimedRoutes.addEdge(
                    districtClicked,
                    endIndex,
                    finger.color,
                    finger.cost);
            E++;

            //Add points for route claimed
            if (finger.cost==1){
                currentPlayer.points+=1;
            }
            if (finger.cost==2){
                currentPlayer.points+=2;
            }
            if (finger.cost==3){
                currentPlayer.points+=4;
            }
            if (finger.cost==4){
                currentPlayer.points+=7;
            }


            //Add the graphical version of the route to player
            Color colorSelected = null;
            if (selectedColor.equalsIgnoreCase("BLUE")) {

                colorSelected = Color.BLUE;
            } else if (selectedColor.equalsIgnoreCase("GREEN")) {

                colorSelected = Color.GREEN;
            } else if (selectedColor.equalsIgnoreCase("RED")) {

                colorSelected = Color.RED;
            } else if (selectedColor.equalsIgnoreCase("BLACK")) {

                colorSelected = Color.BLACK;
            } else if (selectedColor.equalsIgnoreCase("PINK")) {

                colorSelected = Color.PINK;
            } else if (selectedColor.equalsIgnoreCase("ORANGE")) {

                colorSelected = Color.ORANGE;
            }
            Route routeToClaim = null;
            for (Route r : routes) {

                //For claiming a double route
                if (r.start == districtClicked
                        && r.end == endIndex
                        && (r.color == colorSelected
                        || r.color == Color.WHITE)) {

                    routeToClaim = r;
                    break;
                } else if (r.start == endIndex
                        && r.end == districtClicked
                        && (r.color == colorSelected
                        || r.color == Color.WHITE)) {

                    routeToClaim = r;
                    break;
                }

                //For claiming a single route
                else if (r.start == districtClicked
                        && r.end == endIndex
                        && r.color == null) {

                    routeToClaim = r;
                    break;
                } else if (r.start == endIndex
                        && r.end == districtClicked
                        && r.color == null) {

                    routeToClaim = r;
                    break;
                }
            }

            //Remove from the master graph (single routes)
            map.removeEdge(districtClicked, endIndex,
                    stringToColor(selectedColor));
            map.removeEdge(endIndex, districtClicked,
                    stringToColor(selectedColor));

            //We need to remove the other references to the
            //edges.
            if (players.size() == 1) {

                //For double routes
                //We need to check if another vertex has that same
                //reference.
                Graph.Edge removalFinger =
                        map.vertices[endIndex].firstEdge;
                while (removalFinger != null) {

                    if (removalFinger.dest == districtClicked) {

                        //Remove the other edges
                        map.removeEdge(endIndex, removalFinger.dest,
                                finger.color);

                        map.removeEdge(removalFinger.dest, endIndex,
                                finger.color);
                        break;
                    }
                    removalFinger = removalFinger.next;
                }
            }

            if (currentPlayer.taxis <= 2) {
                lessThanTwoTaxis = true;
                lastPlayer = currentPlayer;
                JOptionPane.showMessageDialog(this,
                        lastPlayer.name + " has less than two taxis," +
                                "everyone has one more turn!",
                        "End Game",
                        0);
            }
            //Add it to the players route to claim
            currentPlayer.routes.add(routeToClaim);
            //Remove it from the master routes list
            routes.remove(routeToClaim);

            //Change the player turn
            changeTurns();
        }
    }

    /***
     *
     * This is a method to reuse when changing players turns
     */
    public void changeTurns() {

        //Change players
        players.addLast(currentPlayer);
        currentPlayer = players.removeFirst();

        if (lessThanTwoTaxis && lastPlayer == currentPlayer) {

            //Go score the players
            currentState = GameState.values()[3];
        }
        turnNum++;
        repaint();
        JOptionPane.showMessageDialog(this,
                "It is now " + currentPlayer.name
                        + "\'s turn");
    }

    /***
     * A useful method to see a string representation of a color
     * @param color a color object to be turned into the string of its color.
     * @return the string version of the color
     */
    public String colorToString(Color color) {

        String result = "";
        if (color.equals(Color.BLUE)) {
            result = "BLUE";
        } else if (color.equals(Color.BLACK)) {
            result = "BLACK";
        } else if (color.equals(Color.ORANGE)) {
            result = "ORANGE";
        } else if (color.equals(Color.GREEN)) {
            result = "GREEN";
        } else if (color.equals(Color.PINK)) {
            result = "PINK";
        } else if (color.equals(Color.RED)) {
            result = "RED";
        }

        return result;
    }

    /***
     * This method is for seeing which amount we have of a card type.
     * From 0 to 6 respectively we have blue, green, black, pink, orange, red,
     * and rainbow(taxi).
     * @param color the desired color we would like to see as a number.
     * @return the number form of the color
     */
    public int colorToNumber(Color color) {

        int result = 0;

        if (color.equals(Color.BLUE)) {
            result = 0;
        } else if (color.equals(Color.GREEN)) {
            result = 1;
        } else if (color.equals(Color.BLACK)) {
            result = 2;
        } else if (color.equals(Color.PINK)) {
            result = 3;
        } else if (color.equals(Color.ORANGE)) {
            result = 4;
        } else if (color.equals(Color.RED)) {
            result = 5;
        } else {
            result = 6;
        }

        return result;
    }

    /***
     * A helpful method to convert a number representation of a color to a
     * string.
     * @param number the number we would like to become a color string
     * @return the string version of that color from the number
     */
    public String numberToColor(int number) {

        String result = "";

        if (number == 0) {
            result = "BLUE";
        } else if (number == 1) {
            result = "GREEN";
        } else if (number == 2) {
            result = "BLACK";
        } else if (number == 3) {
            result = "PINK";
        } else if (number == 4) {
            result = "ORANGE";
        } else if (number == 5) {
            result = "RED";
        } else {
            result = "RAINBOW";
        }

        return result;
    }

    /***
     * A useful method to see a color representation of a string
     * USE CASE WARNING: Works with most colors except rainbow and clear
     * @param color a color object to be turned into the string of its color.
     * @return the string version of the color
     */
    public Color stringToColor(String color) {

        Color result = null;
        if (color.equals("BLUE")) {
            result = Color.BLUE;
        } else if (color.equals("BLACK")) {
            result = Color.BLACK;
        } else if (color.equals("ORANGE")) {
            result = Color.ORANGE;
        } else if (color.equals("GREEN")) {
            result = Color.GREEN;
        } else if (color.equals("PINK")) {
            result = Color.PINK;
        } else if (color.equals("RED")) {
            result = Color.RED;
        }

        return result;
    }
}
