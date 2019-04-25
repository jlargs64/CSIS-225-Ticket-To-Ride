import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The destination card display panel for showing players destination cards
 *
 * @author Justin Largo, Leon Griffiths, Jennifer LeClair, Michael Lamb, Yousef
 * Borna
 * @version 1.0
 */
public class ShowDestCardsPanel extends JPanel {

    private ArrayList<DestCard> cards;

    public ShowDestCardsPanel(ArrayList<DestCard> cards) {

        this.cards = cards;
        setLayout(new GridLayout(cards.size() / 2, cards.size()));

        for (DestCard dc : cards) {


            JLabel label = new JLabel();
            Image imgIcon = dc.cardImage.getScaledInstance(200,
                    200, Image.SCALE_SMOOTH);

            ImageIcon icon = new ImageIcon((imgIcon));

            label.setIcon(icon);
            add(label);
        }
    }
}
