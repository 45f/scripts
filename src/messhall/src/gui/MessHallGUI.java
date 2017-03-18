package messhall.src.gui;

import messhall.src.MessHall;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class MessHallGUI {

    private JFrame frmSetup;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MessHallGUI window = new MessHallGUI();
                window.frmSetup.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public MessHallGUI() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmSetup = new JFrame();
        frmSetup.setTitle("Setup");
        frmSetup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmSetup.setBounds(100, 100, 450, 300);
        frmSetup.getContentPane().setLayout(new MigLayout("", "[][][grow]", "[][][]"));

        JLabel lblCookItem = new JLabel("Choose Item:");
        frmSetup.getContentPane().add(lblCookItem, "cell 0 0");

        JComboBox<String> comboBox = new JComboBox<String>();
        comboBox.addActionListener(e -> {
            if (comboBox.getSelectedItem().toString().equalsIgnoreCase("servery pie")) {

            }

        });
        comboBox.setModel(new DefaultComboBoxModel<String>(new String[]{"Servery pie", "Servery stew", "Servery pizza"}));
        frmSetup.getContentPane().add(comboBox, "cell 1 0,alignx right");

        JButton btnStart = new JButton("Start");
        btnStart.addActionListener(arg0 -> MessHall.started = true);
        frmSetup.getContentPane().add(btnStart, "cell 1 2");
    }

}
