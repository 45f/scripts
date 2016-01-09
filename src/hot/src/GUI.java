/*
*
* Created Jan 6, 2016, 1:48:32 PM. 
* @author Stress <steelgseries@hotmail.com>
* Copyright (C) 2016  Stress

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
package hot.src;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;

public class GUI extends JFrame {

	private JPanel contentPane;

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
		setTitle("Master Farmer Setup");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 262, 129);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox foodBox = new JComboBox();
		foodBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Hot.foodToEat = foodBox.getSelectedItem().toString();
			}
		});
		foodBox.setModel(new DefaultComboBoxModel(new String[] {"Shrimp", "Anchoives", "Salmon", "Trout", "Tuna", "Lobster", "Tuna"}));
		foodBox.setBounds(98, 11, 97, 20);
		contentPane.add(foodBox);
		
		JButton startBtn = new JButton("Start");
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Hot.started = true;
				setVisible(false);
			}
		});
		startBtn.setBounds(74, 56, 89, 23);
		contentPane.add(startBtn);
		
		JLabel lblFood = new JLabel("Food:");
		lblFood.setBounds(55, 14, 33, 14);
		contentPane.add(lblFood);
	}
}
