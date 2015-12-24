/*
*
* Created Dec 3, 2015, 11:53:38 AM. 
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

import java.util.Arrays;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.utility.ConditionalSleep;

import com.stress.stressControl.StressControl;

import omniapi.OmniScript;
import omniapi.api.Constants;
import omniapi.api.nodes.AbstractNode;

public class Attack extends AbstractNode {

	/**
	 * Variables
	 */

	public Attack(OmniScript script) {
		super(script);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int execute() throws InterruptedException {

		String[] MONSTERS = { "Spinner", "Defiler", "Shifter", "Brawler", "Torcher" };
		String[] MONSTERS2 = { "Spinner", "Defiler", "Shifter", "Torcher" };
		omniapi.data.NPC monsters = getNPCFinder().find((e) -> (Arrays.asList(MONSTERS).contains(e.getName())));
		omniapi.data.NPC monsters2 = getNPCFinder().find((e) -> (Arrays.asList(MONSTERS2).contains(e.getName())));
		omniapi.data.NPC portal = getNPCFinder().findClosest("Portal");

		if (portal != null && portal.isAttackable()) {
			portal.interact("Attack");
			// log("Attacking portal");
			StressControl.STATUS = "Attacking portal";
			sleep(rand(800, 1000));
		} else {

			if (monsters.isVisible()) {
				monsters.attack();
				// log("Attacking pests");
				StressControl.STATUS = "Attacking pests";
				sleep(rand(250, 450));
			}
		}
		return 250;
	}

	@Override
	public boolean canExecute() {
		Area area = new Area(7072, 886, 7075, 881);
		if (area.contains(myPlayer()) && !myPlayer().isUnderAttack()) {
			return true;
		}
		return true;
	}

}
