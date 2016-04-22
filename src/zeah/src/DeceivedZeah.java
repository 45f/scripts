/*
*
* Created Apr 20, 2016, 9:12:49 PM. 
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
package zeah.src;

import omniapi.OmniScript;
import org.osbot.rs07.script.ScriptManifest;
import zeah.core.piscarilius.DeliverFishTask;
import zeah.core.piscarilius.RepairCraneTask;

@ScriptManifest(author = "Deceiver", info = "Does Zeah Tasks", logo = "", name = "Deceived Zeah", version = 0)
public class DeceivedZeah extends OmniScript {
	
	@Override
	public void onStart() {
	getExperienceTracker().startAll();
	}

	@Override
	public int onLoop() throws InterruptedException {
        getNodeHandler().addNode(new DeliverFishTask(this));
		getNodeHandler().addNode(new RepairCraneTask(this));
		return getNodeHandler().execute();
	}

}
