package script;

import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import script.core.hosidius.MakeFertiliser;
import script.core.piscarilius.HuntWorms;
import script.core.piscarilius.RepairCranes;
import script.ui.GUI;
import script.util.Sleep;

import java.awt.*;
import java.util.ArrayList;

/**
 * <p>
 * </p>
 *
 * @author Capitals <onlytainted@gmail.com>
 */
@ScriptManifest(info = "Gains favour in the Zeah houses.", name = "Horde Zeah", author = "Deceiver", version = 0.3, logo = "https://i.imgur.com/LiNJWJF.png")
public class HordeZeah extends Script {

    private GUI gui;
    private final Paint paint = new Paint(this);

    private ArrayList<Task> tasks = new ArrayList<>();
    public static String status;

    @Override
    public void onStart() throws InterruptedException {
        gui = new GUI("Horde Zeah", this);
        while (gui.isVisible())
            sleep(100);

        log("Running version: " + getVersion());

        if (gui.getTask().contains("Repair")) {
            tasks.add(new RepairCranes(this));
        } else if (gui.getTask().contains("Hunt")) {
            tasks.add(new HuntWorms(this));
        } else tasks.add(new MakeFertiliser(this));

        getExperienceTracker().start(Skill.CRAFTING);
        getExperienceTracker().start(Skill.HUNTER);
        getExperienceTracker().start(Skill.FARMING);
    }

    @Override
    public int onLoop() {
        handleDialogue();
        tasks.forEach(tasks -> {
            try {
                tasks.process();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return 600;
    }

    @Override
    public void onPaint(Graphics2D graphics) {
        paint.repaint(graphics);
    }

    private void handleDialogue() {
        if (getDialogues().isPendingContinuation()) {
            getDialogues().clickContinue();
            Sleep.sleepUntil(() -> !getDialogues().isPendingContinuation(), 5000);
        }
    }
}
