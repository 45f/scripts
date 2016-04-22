/*
*
* Created Mar 7, 2016, 7:59:30 PM. 
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
package zeah.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.TextArea;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class ZeahGUI extends JFrame {
	
	private		JTabbedPane tabbedPane;
	private		JPanel		panel1;
	private		JPanel		panel2;
	private		JPanel		panel3;
	private		JPanel		panel4;
	private		JPanel		panel5;


	/**
	 * 
	 */
	private static final long serialVersionUID = 3191317646871627301L;
	private JPanel contentPane;
	private JLabel lblChooseTask_1;
	private JComboBox<String> comboBox_1;
	private JLabel label;
	private JComboBox<String> comboBox_2;
	private JLabel label_1;
	private JComboBox<String> comboBox_3;
	private JLabel label_2;
	private JComboBox<String> comboBox_4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ZeahGUI frame = new ZeahGUI();
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
	public ZeahGUI() {
		setTitle("Zeah Completionist");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout( new BorderLayout() );
		getContentPane().add( topPanel );

		// Create the tab pages
		createPage1();
		createPage2();
		createPage3();
		createPage4();
		createPage5();

		// Create a tabbed pane
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab( "Arceuus", panel1 );
		
		JLabel lblChooseTask = new JLabel("Choose Task:");
		lblChooseTask.setBounds(10, 11, 75, 14);
		panel1.add(lblChooseTask);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Assist Library Customers", "Seek Dark Knowledge", "Mine Dark Essence", "Craft Blood Runes", "Craft Soul Runes"}));
		comboBox.setBounds(95, 8, 110, 20);
		panel1.add(comboBox);
		tabbedPane.addTab( "Hosidius", panel2 );
		panel2.setLayout(null);
		{
			lblChooseTask_1 = new JLabel("Choose Task:");
			lblChooseTask_1.setBounds(10, 11, 75, 14);
			panel2.add(lblChooseTask_1);
		}
		{
			comboBox_1 = new JComboBox<String>();
			comboBox_1.setModel(new DefaultComboBoxModel<String>(new String[] {"Plough Fields", "Make Fertiliser", "Mess Hall Cooking"}));
			comboBox_1.setBounds(95, 8, 110, 20);
			panel2.add(comboBox_1);
		}
		tabbedPane.addTab( "Lovakengj", panel3 );
		panel3.setLayout(null);
		{
			label = new JLabel("Choose Task:");
			label.setBounds(10, 11, 75, 14);
			panel3.add(label);
		}
		{
			comboBox_2 = new JComboBox<String>();
			comboBox_2.setModel(new DefaultComboBoxModel<String>(new String[] {"Make Dynamite", "Make Shayzien Armour", "Blast Mine"}));
			comboBox_2.setBounds(95, 8, 110, 20);
			panel3.add(comboBox_2);
		}
		tabbedPane.addTab( "Piscarilius", panel4 );
		panel4.setLayout(null);
		{
			label_1 = new JLabel("Choose Task:");
			label_1.setBounds(10, 14, 75, 14);
			panel4.add(label_1);
		}
		{
			comboBox_3 = new JComboBox<String>();
			comboBox_3.setModel(new DefaultComboBoxModel<String>(new String[] {"Maintain Docks", "Take Fish", "Steal Booty", "Catch Anglerfish"}));
			comboBox_3.setBounds(95, 11, 110, 20);
			panel4.add(comboBox_3);
		}
		tabbedPane.addTab( "Shayzien", panel5 );
		panel5.setLayout(null);
		{
			label_2 = new JLabel("Choose Task:");
			label_2.setBounds(10, 14, 75, 14);
			panel5.add(label_2);
		}
		{
			comboBox_4 = new JComboBox<String>();
			comboBox_4.setModel(new DefaultComboBoxModel<String>(new String[] {"Heal Soldiers", "Kill Lizardmen", "Tackle Organized Crime", "Win Armour"}));
			comboBox_4.setBounds(95, 11, 110, 20);
			panel5.add(comboBox_4);
		}
		topPanel.add( tabbedPane, BorderLayout.CENTER );
	}
	
	public void createPage1()
	{
		panel1 = new JPanel();
		panel1.setLayout( null );

	}

	public void createPage2()
	{
		panel2 = new JPanel();
	}

	public void createPage3()
	{
		panel3 = new JPanel();
	}

	public void createPage4()
	{
		panel4 = new JPanel();
	}
	public void createPage5()
	{
		panel5 = new JPanel();
	}
}
