/*
*
* Created Apr 20, 2016, 9:02:55 PM. 
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
package zeah.core.piscarilius;

import omniapi.OmniScript;
import omniapi.api.nodes.AbstractNode;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.event.WalkingEvent;
import org.osbot.rs07.utility.Condition;
import org.osbot.rs07.utility.ConditionalSleep;

public class RepairCraneTask extends AbstractNode {

	public RepairCraneTask(OmniScript script) {
		super(script);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int execute() throws InterruptedException {
        Position craneSpot = new Position(1831, 3756, 0);
		Player player = script.myPlayer();
		RS2Object damagedCrane = script.getObjects().closest(o -> o.getId() == 27555 && o.getPosition().getX() == 1828 && o.getPosition().getY() == 3754);
        RS2Object damagedCrane2 = script.getObjects().closest(o -> o.getId() == 27555 && o.getPosition().getX() == 1828 && o.getPosition().getY() == 3758);
        RS2Object repairedCrane = script. getObjects().closest(o -> o.getId() == 27556);
        WalkingEvent toCrane = new WalkingEvent(craneSpot);
        bank();
        toCrane.setBreakCondition(new Condition() {
            @Override
            public boolean evaluate() {
                return player.getPosition().equals(craneSpot);
            }
        });
        script.execute(toCrane);
        if (damagedCrane != null) {
            if (damagedCrane.isVisible()) {
                damagedCrane.interact("Repair");
                new ConditionalSleep(2500) {
                    @Override
                    public boolean condition() throws InterruptedException {
                        return damagedCrane.getId() != 27555;
                    }
                }.sleep();
            }
        } else if (damagedCrane2 != null) {
            if (damagedCrane2.isVisible()) {
                damagedCrane2.interact("Repair");
                new ConditionalSleep(2500) {
                    @Override
                    public boolean condition() throws InterruptedException {
                        return damagedCrane2.getId() != 27555;
                    }
                }.sleep();
            }
        }
		return rand(800, 2200);
	}

	@Override
	public boolean canExecute() {
		// TODO Auto-generated method stub
		return script.getInventory().contains("Iron nails") || script.getInventory().contains("Mithril nails") 
				&& script.getInventory().contains("Plank") && script.getInventory().contains("Hammer") ;
	}

    public void bank() throws InterruptedException {
        Position bankSpot = new Position(1804, 3790, 0);
        WalkingEvent toBank = new WalkingEvent(bankSpot);
        if (script.getInventory().getAmount("Plank") == 2) {
            toBank.setBreakCondition(new Condition() {
                @Override
                public boolean evaluate() {
                    return myPlayer().getPosition().equals(bankSpot);
                }
            });
            script.execute(toBank);
            script.getBank().open();
            script.getBank().withdrawAll("Plank");
            script.getBank().close();
        }
    }

}
