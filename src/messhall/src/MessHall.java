package messhall.src;

import messhall.src.tasks.CreateMeatPie;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import resources.tasks.AbstractTask;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by mvpni on 3/3/2017.
 */
@ScriptManifest(logo = "", name = "Mess Hall", info = "Does the Mess hall minigame.",
        author = "Raijin", version = 0.1)
public class MessHall extends Script {

    ArrayList<AbstractTask> tasks = new ArrayList<AbstractTask>();

    @Override
    public void onStart() {

    }

    @Override
    public int onLoop() throws InterruptedException {
        tasks.add(new CreateMeatPie(this));
        tasks.forEach(tasks -> {
            try {
                tasks.execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return 2500;
    }

    @Override
    public void onPaint(Graphics2D paint) {

    }
}
