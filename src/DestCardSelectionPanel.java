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
    int width, height;
    private JPanel selectionPanel, helpPanel;
    private JButton both;

    public DestCardSelectionPanel(JFrame frame, Object[] destCards
            , Player currentPlayer) {

        //Set our panels size
        setPreferredSize(new Dimension(600, 400));
        width = 600;
        height = 400;

        parentFrame = frame;
        this.destCards = destCards;

        both = new JButton("Both");
        both.setVerticalTextPosition(AbstractButton.CENTER);
        both.setHorizontalTextPosition(AbstractButton.CENTER);
        both.setBounds(width / 2, height - 100, 200, 100);
        //ActionListeners are for seeing if a button was clicked
        both.addActionListener(this);

        selectionPanel = new JPanel();
        selectionPanel.setLayout(null);
        selectionPanel.add(both);
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
