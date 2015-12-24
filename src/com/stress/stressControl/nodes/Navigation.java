/*
*
* Created Dec 3, 2015, 12:22:25 PM. 
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
import org.osbot.rs07.api.map.Position;

import omniapi.OmniScript;
import omniapi.api.Constants;
import omniapi.api.nodes.AbstractNode;

public class Navigation extends AbstractNode {

	Position boatSpot = new Position(9775, 3600, 0);

	
	public Navigation(OmniScript script) {
		super(script);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int execute() throws InterruptedException {
		getWebWalker().walkPath(boatSpot);
		return Constants.TICK;
	}

	@Override
	public boolean canExecute() {
		Area landingArea = new Area(8035, 1649, 8032, 1654);
		return landingArea.contains(myPlayer());
	}

}
