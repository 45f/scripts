package com.stress.stressSplash.gui;

import javax.swing.*;

/**
 * Copyright (c) 2015  Stress
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * Created by Stress on 12/25/2015.
 * This file is part of com.stress.stressSplash.gui.
 * Created with IntelliJ IDEA.
 *
 * @info
 */
public class AlcherGUI extends JFrame {

    private JFrame frame;
    private JPanel panel;
    private JLabel lbl1;
    private JComboBox spellBox;
    private JLabel lbl2;
    private JCheckBox yesCheckBox;
    private JCheckBox noCheckBox;
    private JFormattedTextField itemToAlch;
    private JButton startButton;

    public AlcherGUI() {
        frame.setVisible(true);
        frame.setTitle("Alcher Setup");
        spellBox.addActionListener(e -> System.out.println(spellBox.getSelectedItem().toString()));
        startButton.addActionListener(e -> { System.out.println("start pressed");

        });
    }
}
