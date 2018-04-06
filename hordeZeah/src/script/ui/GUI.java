package script.ui;

import org.osbot.rs07.script.Script;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * <p>
 * </p>
 *
 * @author Capitals <onlytainted@gmail.com>
 */
public class GUI extends JFrame {

    private JButton confirm;
    private JComboBox option;


    public GUI(String title, Script s) {
        super(title);
        setLayout(new BorderLayout());
        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                s.stop();
            }
        });

        option = new JComboBox();
        option.addItem("Repair cranes");
        option.addItem("Hunt for worms");
        option.addItem("Mix fertiliser");
        option.addActionListener(e -> option.getSelectedItem());

        confirm = new JButton("Start Script");
        confirm.addActionListener(e -> dispose());

        c.add(option, BorderLayout.NORTH);
        c.add(confirm, BorderLayout.SOUTH);
        c.setPreferredSize(new Dimension(300, 100));
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        pack();
    }

    public String getTask() {
        return String.valueOf(option.getSelectedItem());
    }

}
