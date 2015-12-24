package com.stress.stressControl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.osbot.rs07.api.EntityAPI;
import org.osbot.rs07.api.NPCS;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.canvas.paint.Painter;
import org.osbot.rs07.script.ScriptManifest;

import com.stress.stressControl.gui.GUI;
import com.stress.stressControl.nodes.Attack;
import com.stress.stressControl.nodes.Boat;
import com.stress.stressControl.nodes.Defend;
import com.stress.stressControl.nodes.Navigation;
import com.sun.glass.ui.Timer;

import omniapi.OmniScript;
import omniapi.api.Constants;
import omniapi.data.NPCs;
import omniapi.debug.LogLevel;
import omniapi.paint.Button;


@ScriptManifest(author = "Stress", info = "pest control", logo = "http://vgy.me/VFSt5V.png", name = "Stress Control", version = 0.4)
public class StressControl extends OmniScript implements Painter {

	/**
	 * Variables
	 */
	GUI frame = new GUI();
	BufferedImage img = null;

	public static String LANDER;
	public static String FOOD;
	public static String STATUS;

	private long startTime;
	private long runTime;
	private int PROFIT;

	public static boolean guiStarted = false;
	
	public static boolean attackMode;
	public static boolean defendMode;
	public static boolean prioritizeSpinners;
	public static boolean attackBrawlers;
	public static boolean rangerHelm;
	public static boolean mageHelm;
	public static boolean meleeHelm;
	public static boolean knightTop;
	public static boolean knightGloves;
	public static boolean knightBottoms;

	@Override
	public void onStart() {
		frame.setVisible(true);
		setLogLevel(LogLevel.DEBUG);
		getBot().addMessageListener(this);
		log("StressControl starting!");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		try {
			img = ImageIO.read(new URL("http://vgy.me/I9m6oM.png"));
		} catch (IOException e) {
			log(e);
		}
		startTime = System.currentTimeMillis();
		
	}

	@Override
	public int onLoop() throws InterruptedException {
		getNodeHandler().addNode(new Boat(this));
		getNodeHandler().addNode(new Attack(this));
		//getNodeHandler().addNode(new Defend(this));
		//getNodeHandler().addNode(new Navigation(this));
		return getNodeHandler().execute();
	}

	@Override
	public void onExit() {
		frame.dispose();
	}

	public void onPaint(Graphics2D g) {
		// Mouse paint
		g.setColor(Color.red);
		Point mousePoint = getMouse().getPosition();
		g.drawOval(mousePoint.x - 10, mousePoint.y - 10, 20, 20);
		g.setColor(Color.white);
		g.drawLine(mousePoint.x, 0, mousePoint.x, 500);
		g.drawLine(0, mousePoint.y, 800, mousePoint.y);
		// Image drawing
		g.drawImage(img, 0, 270, null);

		g.setFont(new Font("Open Sans", Font.PLAIN, 12));

		// Variables for image
		runTime = System.currentTimeMillis() - startTime;
		g.drawString("" + ft(runTime), 165, 389);
		g.drawString("" + PROFIT, 209, 445);
		g.drawString("Food selected: " + FOOD, 265, 380);
		g.drawString("Status: " + STATUS, 265, 400);

	}

	/**
	 * Formats time.
	 * 
	 * @param duration
	 * @return formatted time
	 */
	private String ft(long duration) {
		String res = "";
		long days = TimeUnit.MILLISECONDS.toDays(duration);
		long hours = TimeUnit.MILLISECONDS.toHours(duration)
				- TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
		long minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
				- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration));
		long seconds = TimeUnit.MILLISECONDS.toSeconds(duration)
				- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));
		if (days == 0) {
			res = (hours + ":" + minutes + ":" + seconds);
		} else {
			res = (days + ":" + hours + ":" + minutes + ":" + seconds);
		}
		return res;
	}
	
}