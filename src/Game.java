import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import static javax.swing.SwingUtilities.invokeLater;

/**
 * A java implementation of Text Twist
 *
 * @author Justin Largo, Leon Griffiths, Jennifer LeClair, Michael Lamb, Yousef
 * Borna
 * @version 1.0
 */
class Game extends JPanel implements MouseListener, ActionListener {

    //instance variables
    private final int width;
    private final int height;
    private final Graph map;
    private Toolkit toolkit;
    private Image mainMenuBackground;
    private GameState currentState;
    private JButton playButton, helpButton, quitButton;

    //The constructor for Text Twist
    private Game() {

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
        helpButton = new JButton("Help");
        helpButton.setVerticalTextPosition(AbstractButton.CENTER);
        helpButton.setHorizontalTextPosition(AbstractButton.CENTER);
        helpButton.setBounds(100, 50, 300, 100);

        playButton = new JButton("Play");
        playButton.setVerticalTextPosition(AbstractButton.CENTER);
        playButton.setHorizontalTextPosition(AbstractButton.CENTER);
        playButton.setBounds(100, 160, 300, 100);

        quitButton = new JButton("Quit");
        quitButton.setVerticalTextPosition(AbstractButton.CENTER);
        quitButton.setHorizontalTextPosition(AbstractButton.CENTER);
        quitButton.setBounds(100, 270, 300, 100);

        // Radio Buttons for selecting number of players
        JRadioButton p2 = new JRadioButton("2");
        JRadioButton p3 = new JRadioButton("3");
        JRadioButton p4 = new JRadioButton("4");
        ButtonGroup group = new ButtonGroup();
        group.add(p2);
        group.add(p3);
        group.add(p4);

        // Add action listeners for if a button is clicked
        helpButton.addActionListener(this);
        playButton.addActionListener(this);
        quitButton.addActionListener(this);
    }

    /**
     * The main method to execute the program.
     *
     * @param args command line arguements
     */
    public static void main(String[] args) {

        invokeLater(() -> {

                // Create and set up the window.
                JFrame frame = new JFrame("Ticket to Ride: New York");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                Game panel = new Game();
                frame.getContentPane().add(panel);

                // Making sure the game scales well
                frame.setResizable(false);
                // Display the window.
                frame.pack();
                frame.setVisible(true);
            });
    }

    /**
     * PaintComponent method for JPanel.
     *
     * @param g the Graphics object for this applet
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Switch case for displaying different states
        switch (currentState) {

            case MAIN_MENU:

            // display images
            mainMenuBackground = toolkit.getImage("assets\\game-cover.jpg");
            int bgWidth = mainMenuBackground.getWidth(this);
            int bgHeight = mainMenuBackground.getHeight(this);
            g.drawImage(mainMenuBackground, 0, 0, bgWidth, bgHeight, this);

            // Draw the Title Text
            Font titleFont = new Font("Monospace", Font.BOLD, 100);
            g.setFont(titleFont);
            g.setColor(Color.BLACK);
            g.drawString("Ticket to Ride", width/4, 100);

            //Draw the play button which leads to the select players screen.
            add(playButton);

            //Draw the help button which displays the help photos
            add(helpButton);

            //Draw the exit button
            add(quitButton);
            
            //Disable Buttons
            // remove(group);
            
            
            break;
            case HELP_MENU:

            //Swap between the 2 help images and also a back to main button.

            break;
            case PLAYER_SELECTION:

            //We should have some radio buttons for 2/3/4 players

            //Then generate programmatically each player and their selected
            //colors (BLUE(CYAN)/PURPLE/YELLOW/WHITE
            //Color selection should be a dropdown menu

            break;
            case GAME_MENU:
            break;
            case SCORE_MENU:

            //This is where we show who won the game with the scorecard
            //There will be some animations that we can add later.

            break;
        }
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) { }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) { }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) { }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) { }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) { }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) { }

    // For managing the game state
    public enum GameState {
        MAIN_MENU, HELP_MENU, PLAYER_SELECTION, GAME_MENU, SCORE_MENU
    }
}