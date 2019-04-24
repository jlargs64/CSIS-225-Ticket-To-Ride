import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The destination card selection panel for getting the destination cards
 * selected
 *
 * @author Justin Largo, Leon Griffiths, Jennifer LeClair, Michael Lamb, Yousef
 * Borna
 * @version 1.0
 */
public class DestCardSelectionPanel extends JPanel implements ActionListener {

    private JFrame parentFrame;
    private Object[] destCards;
    private JButton submit;
    private JPanel selectionPanel, helpPanel;

    public DestCardSelectionPanel(JFrame frame, Object[] destCards
            , Player currentPlayer) {

        //Set our panels size
        setPreferredSize(new Dimension(600, 400));
        parentFrame = frame;
        this.destCards = destCards;

        //Initialize our radio buttons for destination card selection
        JRadioButton leftCard = new JRadioButton("Left Card");
        leftCard.setActionCommand("left");

        JRadioButton rightCard = new JRadioButton("Right Card");
        rightCard.setActionCommand("right");

        JRadioButton bothCards = new JRadioButton("Both Cards");
        bothCards.setActionCommand("both");

        //Button group is used for keeping track of which button is clicked.
        ButtonGroup buttons = new ButtonGroup();
        buttons.add(leftCard);
        buttons.add(rightCard);
        buttons.add(bothCards);

        //Set two players as default
        bothCards.setSelected(true);

        submit = new JButton("Submit");

        //ActionListeners are for seeing if a button was clicked
        leftCard.addActionListener(this);
        rightCard.addActionListener(this);
        bothCards.addActionListener(this);
        submit.addActionListener(this);

        selectionPanel = new JPanel(new GridLayout(1, 3));
        selectionPanel.add(leftCard);
        selectionPanel.add(rightCard);
        selectionPanel.add(bothCards);
        selectionPanel.add(submit);
        add(selectionPanel, BorderLayout.CENTER);

        helpPanel = new JPanel(new GridLayout(0, 6));
        helpPanel.add(new JLabel("Player: " + currentPlayer.name));
        add(helpPanel, BorderLayout.NORTH);
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
