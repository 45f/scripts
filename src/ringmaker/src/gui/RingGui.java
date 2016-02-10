/*
 * Created by JFormDesigner on Sat Jan 30 11:52:13 CST 2016
 */

package ringmaker.src.gui;

import ringmaker.RingMaker;
import ringmaker.src.nodes.Banking;
import ringmaker.src.nodes.CraftRing;
import ringmaker.src.nodes.EnchantRing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author Stress wet
 */
public class RingGui extends JFrame {
    public RingGui() {
        initComponents();
    }


    private void ringBoxActionPerformed(ActionEvent e) {
        if (ringBox.getSelectedItem().equals("Ring of dueling")) {
            RingMaker.dickButt = "Ring of dueling(8)";
            RingMaker.buttDick = 2552;
            CraftRing.craftDuelings = true;
            CraftRing.gemToUse = "Emerald";
            Banking.gemToUse = "Emerald";
            Banking.ring = "Emerald ring";
            Banking.staffToUse = "Staff of air";
            EnchantRing.staffToUse = "Staff of air";
            EnchantRing.ring = "Emerald ring";
        } else {
            RingMaker.dickButt = "Ring of recoil";
            RingMaker.buttDick = 2550;
            CraftRing.craftRecoils = true;
            CraftRing.gemToUse = "Sapphire";
            Banking.gemToUse = "Sapphire";
            Banking.ring = "Sapphire ring";
            Banking.staffToUse = "Staff of water";
            EnchantRing.staffToUse = "Staff of water";
            EnchantRing.ring = "Sapphire ring";
        }
    }

    private void startBtnActionPerformed(ActionEvent e) {
        RingMaker.started = true;
        dispose();
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Stress wet
        label1 = new JLabel();
        ringBox = new JComboBox<>();
        startBtn = new JButton();

        //======== this ========
        setTitle("Setup");
        setMinimumSize(new Dimension(250, 150));
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- label1 ----
        label1.setText("Select Ring:");
        contentPane.add(label1);
        label1.setBounds(new Rectangle(new Point(30, 20), label1.getPreferredSize()));

        //---- ringBox ----
        ringBox.setModel(new DefaultComboBoxModel<>(new String[]{
                "Ring of recoil",
                "Ring of dueling"
        }));
        ringBox.addActionListener(e -> {
            ringBoxActionPerformed(e);
        });
        contentPane.add(ringBox);
        ringBox.setBounds(new Rectangle(new Point(100, 15), ringBox.getPreferredSize()));

        //---- startBtn ----
        startBtn.setText("Start");
        startBtn.addActionListener(e -> startBtnActionPerformed(e));
        contentPane.add(startBtn);
        startBtn.setBounds(new Rectangle(new Point(80, 65), startBtn.getPreferredSize()));

        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for (int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Stress wet
    private JLabel label1;
    private JComboBox<String> ringBox;
    private JButton startBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
