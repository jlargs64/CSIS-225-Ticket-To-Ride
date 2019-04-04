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

    //The constructor for Text Twist
    private Game() {

        //Default window settings
        setPreferredSize(new Dimension(800, 600));
        toolkit = Toolkit.getDefaultToolkit();
        setOpaque(true);
        width = getPreferredSize().width;
        height = getPreferredSize().height;
        setFocusable(true);
        setLayout(null);

        //Construct our graph
        File data = new File("Districts.txt");
        map = new Graph(data);
        // Add universal interface listeners
        addMouseListener(this);
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

        // display images
        mainMenuBackground = toolkit.getImage("assets\\game-cover.jpg");
        int bgWidth = mainMenuBackground.getWidth(this);
        int bgHeight = mainMenuBackground.getHeight(this);
        g.drawImage(mainMenuBackground, 0, 0, bgWidth, bgHeight, this);
        // display icon
        //icon1.paintIcon(this, g, width- icon1.getIconWidth(), 0);
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

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}