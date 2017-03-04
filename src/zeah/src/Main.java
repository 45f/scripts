package zeah.src;

import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.ConditionalSleep;
import resources.paint.ProgressBar;
import resources.tasks.AbstractTask;
import zeah.src.core.hosidius.MakeFertiliserTask;
import zeah.src.core.hosidius.PloughFieldsTask;
import zeah.src.core.piscarilius.HuntForWorms;
import zeah.src.core.piscarilius.RepairCraneTask;
import zeah.src.gui.ZeahGUI;

import java.awt.*;
import java.util.ArrayList;

@ScriptManifest(author = "Deceiver", info = "Gains Zeah favor!", logo = "http://i.imgur.com/b2aIHdy.png",
        name = "Zeah Completionist", version = 0.2)
public class Main extends Script {

    private long startTime, runTime;
    public static String status, mode;
    public static boolean started = false;

    private ZeahGUI zeahGUI;
    ArrayList<AbstractTask> tasks = new ArrayList<AbstractTask>();


    @Override
    public void onStart() {
        this.zeahGUI = new ZeahGUI();
        log("[VERSION]: " + getVersion());
        getUi().setVisible(true);
        while (zeahGUI.isVisible()) {
            try {
                sleep(400);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        startTime = System.currentTimeMillis();
        getExperienceTracker().startAll();

        // Hosidius Options
        if (getUi().getComboBox_1().getSelectedItem().equals("Plough")) {
            tasks.add(new PloughFieldsTask(this));
        }
        if (getUi().getComboBox_1().getSelectedItem().equals("Make Fertiliser")) {
            tasks.add(new MakeFertiliserTask(this));
        }
        // Piscarilius Options
        if (getUi().getComboBox_3().getSelectedItem().equals("Repair Cranes")) {
            tasks.add(new RepairCraneTask(this));
        }
        if (getUi().getComboBox_3().getSelectedItem().equals("Hunt For Worms")) {
            tasks.add(new HuntForWorms(this));
        }
    }

    @Override
    public int onLoop() throws InterruptedException {
        handleDialogue();
        tasks.forEach(tasks -> {
            try {
                tasks.execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        return 850;
    }

    public void onPaint(Graphics2D paint) {
        Point mP = getMouse().getPosition();

        runTime = System.currentTimeMillis() - startTime;
        Color blackTrans = new Color(0, 0, 0, 127);

        paint.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        paint.setFont(new Font("Arial", Font.PLAIN, 11));

        paint.setColor(Color.CYAN);
        paint.drawLine(mP.x - 5, mP.y + 5, mP.x + 5, mP.y - 5);
        paint.drawLine(mP.x + 5, mP.y + 5, mP.x - 5, mP.y - 5);

        paint.setColor(blackTrans);
        paint.fillRect(4, 280, 295, 58);
        paint.setColor(Color.WHITE);
        paint.drawString("Zeah Completionist v: " + getVersion(), 10, 298);
        paint.drawString("Run Time: " + formatTime(runTime), 10, 315);
        paint.drawString("Status: " + status, 140, 298);
        ProgressBar.repaint(paint, this);
    }

    public final String formatTime(final long ms) {
        long s = ms / 1000, m = s / 60, h = m / 60, d = h / 24;
        s %= 60;
        m %= 60;
        h %= 24;

        return d > 0 ? String.format("%02d:%02d:%02d:%02d", d, h, m, s) :
                h > 0 ? String.format("%02d:%02d:%02d", h, m, s) :
                        String.format("%02d:%02d", m, s);
    }

    private void handleDialogue() {
        if (getDialogues().isPendingContinuation()) {
            getDialogues().clickContinue();
            new ConditionalSleep(450, 600) {
                @Override
                public boolean condition() throws InterruptedException {
                    return !getDialogues().isPendingContinuation();
                }
            }.sleep();
        }
    }

    public ZeahGUI getUi() {
        return zeahGUI;
    }
}
