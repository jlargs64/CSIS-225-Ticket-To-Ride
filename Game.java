import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

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

    //The constructor for Text Twist
    private Game() {

        //Default window settings
        setPreferredSize(new Dimension(800, 600));
        setOpaque(true);
        width = getPreferredSize().width;
        height = getPreferredSize().height;
        setBackground(new Color(65, 218, 249));
        setFocusable(true);
        setLayout(null);

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
            JFrame frame = new JFrame("Text Twist");
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