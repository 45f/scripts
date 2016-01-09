/*
*
* Created Jan 4, 2016, 12:53:23 PM. 
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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.api.ui.Message.MessageType;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.ScriptManifest;

import omniapi.OmniScript;
import omniapi.debug.LogLevel;

@ScriptManifest(author = "Stress", info = "Thieves Master Farmer at Draynor", logo = "http://i.imgur.com/LfGL4QD.png", name = "Stress Thiever", version = 1)
public class Hot extends OmniScript {
	
	GUI window = new GUI();
	
	public static boolean started = false;
	public static String foodToEat;
	private long startTime;
	int successfulThieves = 0;
	int failedThieves = 0;
	int startXP;
	int gainedXP;
	int profit;
	
	@Override
	public void onStart() {
		window.setVisible(true);
		while (window.isVisible()) { try {
			sleep(400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} }
		startTime = System.currentTimeMillis();
		setLogLevel(LogLevel.DEBUG);
		getExperienceTracker().start(Skill.THIEVING);
		
	}

	@Override
	public int onLoop() throws InterruptedException {
		 	started = true;
		if (skills.getDynamic(Skill.HITPOINTS) < skills.getDynamic(Skill.HITPOINTS) / 2) {
			inventory.interact("Eat", foodToEat);
		}	else if (!bank.contains(foodToEat)) {
			logoutTab.logOut();
		}
		else if (!inventory.contains(foodToEat)) {
			bank.open();
			bank.depositAll();
			bank.withdraw(foodToEat, 10);
			bank.close();
		} else getNPCFinder().findClosest("Master Farmer").pickpocket();
	
		return 3000;
	}

	@Override
	public void onPaint(Graphics2D gg) {
		long runTime = System.currentTimeMillis() - startTime;
		gainedXP = getExperienceTracker().getGainedXP(Skill.THIEVING);
		gg.setFont(new Font("Monsterrat", Font.PLAIN, 13));
		gg.setColor(Color.RED);
		gg.drawString("Run time: " + (formatTime(runTime)), 325, 240);
		gg.drawString("Successful thieves: " + successfulThieves, 325, 260);
		gg.drawString("Failed thieves: " + failedThieves, 325, 280);
		gg.drawString("Gained xp: " + (formatValue(gainedXP)), 325, 300);
		
		Point mP = getMouse().getPosition();

		gg.drawLine(mP.x - 5, mP.y + 5, mP.x + 5, mP.y - 5); 
		gg.drawLine(mP.x + 5, mP.y + 5, mP.x - 5, mP.y - 5);
	}
	
	public void onMessage(Message c) throws InterruptedException {
	
		if (c.getType() == MessageType.GAME) {
		String m = c.getMessage();
		try {
		 if (m.contains("You pick the Master ")) {
		successfulThieves++;
		}   else if (m.contains("You fail to pick the Master ")) {
			failedThieves++;
			sleep(3000);
		}
}
		finally {
			// empty
	}
		}
	}
	
	public String formatTime(long ms){

	    long s = ms / 1000, m = s / 60, h = m / 60, d = h / 24;
	    s %= 60; m %= 60; h %= 24;

	    return d > 0 ? String.format("%02d:%02d:%02d:%02d", d, h, m, s) :
	           h > 0 ? String.format("%02d:%02d:%02d", h, m, s) :
	           String.format("%02d:%02d", m, s);
	}
	
	public String formatValue(int v){

	    if(v > 1_000_000) return String.format("%.2fm", (double) (v / 1_000_000));
	    else if(v > 1000) return String.format("%.1fk", (double) (v / 1000));
	    return v + "";
	}
}
