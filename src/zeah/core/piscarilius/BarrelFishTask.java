package zeah.core.piscarilius;

import omniapi.OmniScript;
import omniapi.api.nodes.AbstractNode;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.event.WalkingEvent;

/**
 * Created  on 4/22/2016.
 * This file is part of zeah.core.piscarilius.
 */
public class BarrelFishTask extends AbstractNode {

    public BarrelFishTask(OmniScript script) {
        super(script);
    }

    @Override
    public boolean canExecute() {
        RS2Object barrel = script.getObjects().closest(o -> o.getId() == 27552);
        return barrel.isVisible();
    }

    @Override
    public int execute() throws InterruptedException {
        Position barrelSpot = new Position(1808, 3715, 0);
        Position chestSpot = new Position(1832, 3718, 0);
        RS2Object barrel = script.getObjects().closest(o -> o.getId() == 27552);
        RS2Object chest = script.getObjects().closest(o -> o.getId() == 27554);
        WalkingEvent toChest = new WalkingEvent(chestSpot);
        WalkingEvent toBarrel = new WalkingEvent(barrelSpot);

        if (!myPosition().equals(barrelSpot)) {
            script.execute(toBarrel);
        }
        if (barrel != null) {
            if (barrel.isVisible()) {
                barrel.interact("Collect");
                sleep(50);
                /*new ConditionalSleep(100) {
                    @Override
                    public boolean condition() throws InterruptedException {
                        return barrel.getId() != 27552;
                    }
                }.sleep()*/;
            }
        } else if (script.getInventory().getAmount("Fresh fish") == 24) {
                script.execute(toChest);
            chest.interact("Deposit");
        }
        return rand(100, 2000);
    }
}
