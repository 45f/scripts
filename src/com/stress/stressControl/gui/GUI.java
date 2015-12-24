/*
*
* Created Nov 20, 2015, 10:34:41 PM. 
* @author Stress <steelgseries@hotmail.com>
* Copyright (C) 2015  Stress

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
*
*/
package com.stress.stressControl.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.stress.stressControl.StressControl;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5992797482539422010L;

	private JPanel contentPane;
	public final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		setTitle("Stress Control Setup");
		JTabbedPane jtp = new JTabbedPane();
		getContentPane().add(jtp);
		JPanel main = new JPanel();
		main.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Main Information",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		jtp.addTab("Main", main);
		jtp.setToolTipTextAt(0, "Main information tab.");

		// Main Info pane
		main.setLayout(null);

		JLabel lblSelectUrPoison = new JLabel("Select Option:");
		lblSelectUrPoison.setBounds(10, 32, 76, 14);
		main.add(lblSelectUrPoison);

		JRadioButton rdbtnAttackStuff = new JRadioButton("Attack portals and pests");
		rdbtnAttackStuff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnAttackStuff.isSelected()) {
					StressControl.attackMode = true;
				} else
					StressControl.attackMode = false;
			}
		});

		buttonGroup.add(rdbtnAttackStuff);
		rdbtnAttackStuff.setBounds(91, 28, 170, 23);
		main.add(rdbtnAttackStuff);

		JRadioButton rdbtnDefendTheNight = new JRadioButton("Defend the knight");
		rdbtnDefendTheNight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnDefendTheNight.isSelected()) {
					StressControl.defendMode = true;
				} else
					StressControl.defendMode = false;
			}
		});

		buttonGroup.add(rdbtnDefendTheNight);
		rdbtnDefendTheNight.setBounds(273, 29, 126, 23);
		main.add(rdbtnDefendTheNight);

		JLabel lblGameStuff = new JLabel("Game Stuff:");
		lblGameStuff.setBounds(79, 89, 66, 14);
		main.add(lblGameStuff);

		JLabel lblSpinner = new JLabel("");
		lblSpinner.setIcon(new ImageIcon(GUI.class.getResource("/com/stress/stressControl/gui/res/spinner.png")));
		lblSpinner.setBounds(14, 58, 56, 54);
		main.add(lblSpinner);

		JCheckBox chckbxAttackBrawlers = new JCheckBox("Attack brawlers");
		chckbxAttackBrawlers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxAttackBrawlers.isSelected()) {
					StressControl.attackBrawlers = true;
				} else
					StressControl.attackBrawlers = false;
			}
		});
		chckbxAttackBrawlers.setBounds(10, 124, 135, 23);
		main.add(chckbxAttackBrawlers);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StressControl.guiStarted = true;
				setVisible(false);
			}
		});
		btnStart.setBounds(252, 152, 89, 23);
		main.add(btnStart);
		JPanel points = new JPanel();
		points.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Point Spending Options",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		// Points pane
		jtp.addTab("Points", points);
		jtp.setEnabledAt(1, true);
		jtp.setToolTipTextAt(1, "Points spending options.");
		points.setLayout(null);

		JLabel lblWatSpend = new JLabel("Void Equipment:");
		lblWatSpend.setBounds(53, 31, 97, 14);
		points.add(lblWatSpend);

		JCheckBox chckbxVoidRangeHelm = new JCheckBox("Ranger helm");
		chckbxVoidRangeHelm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxVoidRangeHelm.isSelected()) {
					System.out.println("Ranger helm selected");
				} else
					System.out.println("Ranger helm deselected");
			}
		});
		chckbxVoidRangeHelm.setBounds(6, 74, 117, 23);
		points.add(chckbxVoidRangeHelm);

		JCheckBox chckbxVoidMageHelm = new JCheckBox("Mage helm");
		chckbxVoidMageHelm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxVoidMageHelm.isSelected()) {
					System.out.println("Mage helm selected");
				} else
					System.out.println("Mage helm deselected");
			}
		});
		chckbxVoidMageHelm.setBounds(6, 100, 97, 23);
		points.add(chckbxVoidMageHelm);

		JCheckBox chckbxVoidMeleeHelm = new JCheckBox("Melee helm");
		chckbxVoidMeleeHelm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxVoidMeleeHelm.isSelected()) {
					System.out.println("Melee helm selected");
				} else
					System.out.println("Melee helm deselected");
			}
		});
		chckbxVoidMeleeHelm.setBounds(6, 126, 97, 23);
		points.add(chckbxVoidMeleeHelm);

		JCheckBox chckbxVoidTorso = new JCheckBox("Knight top");
		chckbxVoidTorso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxVoidTorso.isSelected()) {
					System.out.println("Knight top selected");
				} else
					System.out.println("Knight top deselected");
			}
		});
		chckbxVoidTorso.setBounds(6, 152, 97, 23);
		points.add(chckbxVoidTorso);

		JCheckBox chckbxVoidBottoms = new JCheckBox("Knight bottoms");
		chckbxVoidBottoms.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxVoidBottoms.isSelected()) {
					System.out.println("Knight bottoms selected");
				} else
					System.out.println("Knight bottoms deselected");
			}
		});
		chckbxVoidBottoms.setBounds(129, 74, 117, 23);
		points.add(chckbxVoidBottoms);

		JCheckBox chckbxVoidGloves = new JCheckBox("Knight gloves");
		chckbxVoidGloves.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxVoidGloves.isSelected()) {
					System.out.println("Knight gloves selected");
				} else
					System.out.println("Knight gloves deselected");
			}
		});
		chckbxVoidGloves.setBounds(129, 100, 117, 23);
		points.add(chckbxVoidGloves);

		JCheckBox chckbxVoidBoots = new JCheckBox("Knight boots");
		chckbxVoidBoots.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxVoidBoots.isSelected()) {
					System.out.println("Knight boots selected");
				} else
					System.out.println("Knight boots deselected");
			}
		});
		chckbxVoidBoots.setBounds(129, 126, 106, 23);
		points.add(chckbxVoidBoots);

		JLabel lblRngHelm = new JLabel("");
		lblRngHelm.setIcon(new ImageIcon(GUI.class.getResource("/com/stress/stressControl/gui/res/rngrHelm.png")));
		lblRngHelm.setBounds(10, 20, 34, 40);
		points.add(lblRngHelm);

		// Food and Potions pane
		JPanel food = new JPanel();
		food.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Food & Potion Options",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jtp.addTab("Food & Potions", food);
		jtp.setToolTipTextAt(2, "Potions and food tab.");
		food.setLayout(null);

		JLabel lblLobster = new JLabel("");
		lblLobster.setBounds(10, 20, 30, 29);
		lblLobster.setIcon(new ImageIcon(GUI.class.getResource("/com/stress/stressControl/gui/res/lobster.png")));
		food.add(lblLobster);

		JLabel lblSelectUrFood = new JLabel("Select food:");
		lblSelectUrFood.setBounds(48, 26, 65, 14);
		food.add(lblSelectUrFood);

		JComboBox<String> foodBox = new JComboBox<String>();
		foodBox.setModel(new DefaultComboBoxModel<String>(new String[] {"None", "Tuna", "Swordfish", "Lobster", "Shark", "Manta ray"}));
		foodBox.addActionListener(e -> StressControl.FOOD = (String) foodBox.getSelectedItem());
		foodBox.setBounds(130, 20, 134, 26);
		food.add(foodBox);

		JLabel lblNewLabel = new JLabel("Potions:");
		lblNewLabel.setBounds(48, 70, 49, 14);
		food.add(lblNewLabel);

		JCheckBox chckbxAtkPot = new JCheckBox("Attack potion");
		chckbxAtkPot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxAtkPot.isSelected()) {
					System.out.println("Attack potion selected");
				} else
					System.out.println("Attack potion deselected");
			}
		});
		chckbxAtkPot.setBounds(48, 90, 116, 23);
		food.add(chckbxAtkPot);

		JCheckBox chckbxStrPot = new JCheckBox("Strength potion");
		chckbxStrPot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxStrPot.isSelected()) {
					System.out.println("Strength potion selected");
				} else
					System.out.println("Strength potion deselected");
			}
		});
		chckbxStrPot.setBounds(201, 115, 125, 23);
		food.add(chckbxStrPot);

		JCheckBox chckbxDefencePotz = new JCheckBox("Defence potion");
		chckbxDefencePotz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxDefencePotz.isSelected()) {
					System.out.println("Defence potion selected");
				} else
					System.out.println("Defence potion deselected");
			}

		});
		chckbxDefencePotz.setBounds(48, 116, 116, 23);
		food.add(chckbxDefencePotz);

		JCheckBox chckbxRangePotz = new JCheckBox("Range potion");
		chckbxRangePotz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxRangePotz.isSelected()) {
					System.out.println("Range potion selected");
				} else
					System.out.println("Range potion deselected");
			}
		});
		chckbxRangePotz.setBounds(201, 90, 125, 23);
		food.add(chckbxRangePotz);

		JCheckBox chckbxMagePotz = new JCheckBox("Magic potion");
		chckbxMagePotz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxMagePotz.isSelected()) {
					System.out.println("Magic potion selected");
				} else
					System.out.println("Magic potion deselected");
			}
		});
		chckbxMagePotz.setBounds(48, 142, 116, 23);
		food.add(chckbxMagePotz);

		JLabel lblAtkPot = new JLabel("");
		lblAtkPot.setIcon(new ImageIcon(GUI.class.getResource("/com/stress/stressControl/gui/res/atkPot.png")));
		lblAtkPot.setBounds(10, 60, 20, 35);
		food.add(lblAtkPot);


	}
}
