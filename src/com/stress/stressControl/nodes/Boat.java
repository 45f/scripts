/*
 *
 * Created Dec 3, 2015, 2:15:58 PM. 
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
package com.stress.stressControl.nodes;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.ui.Message;

import omniapi.OmniScript;
import omniapi.api.nodes.AbstractNode;
import omniapi.data.Entity;

public class Boat extends AbstractNode {

	/**
	 * Variables
	 */
	Message message = new Message(0, null, null);
	public static String LANDER;
	//Entity cauldron = getEntityFinder().findClosest("Cauldron");
	Entity plank = getEntityFinder().findClosest("Gangplank");

	Area area = new Area(2644, 2645, 2644, 2643);
	boolean areWeInBoat = false;

	public Boat(OmniScript script) {
		super(script);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int execute() throws InterruptedException {
		if (area.contains(myPlayer())) {
			if (plank != null) {
				if (plank.isVisible()) {
					plank.interact("Cross");
					sleep(845);

				}
			}
		}
		return 500;
	}

	@Override
	public boolean canExecute() {
		Area area = new Area(2644, 2645, 2644, 2643);
		return area.contains(myPlayer());
	}

}
