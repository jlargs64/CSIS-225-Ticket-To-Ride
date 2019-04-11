import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerSelectionPanel extends JPanel implements ActionListener {


    PlayerSelectionPanel() {

        setPreferredSize(new Dimension(100, 500));

        JRadioButton p2 = new JRadioButton("2 Players");
        p2.setActionCommand("2");

        JRadioButton p3 = new JRadioButton("3 Players");
        p3.setActionCommand("3");

        JRadioButton p4 = new JRadioButton("4 Players");
        p4.setActionCommand("4");

        ButtonGroup buttons = new ButtonGroup();
        buttons.add(p2);
        buttons.add(p3);
        buttons.add(p4);

        p2.addActionListener(this);
        p3.addActionListener(this);
        p4.addActionListener(this);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.add(p2);
        buttonPanel.add(p3);
        buttonPanel.add(p4);
        add(buttonPanel, BorderLayout.NORTH);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("2")) {

        }
        if (e.getActionCommand().equals("3")) {

        }
        if (e.getActionCommand().equals("4")) {

        }
    }

    private void createPlayerForm() {


    }
}
