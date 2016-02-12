package ringmaker;

import omniapi.OmniScript;
import omniapi.debug.LogLevel;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.ScriptManifest;
import ringmaker.src.gui.RingGui;
import ringmaker.src.nodes.Banking;
import ringmaker.src.nodes.CraftRing;
import ringmaker.src.nodes.EnchantRing;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Copyright (c) 2016  Stress
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * Created by Stress on 1/30/2016.
 * This file is part of ringmaker.
 * Created with IntelliJ IDEA.
 *
 * @info
 */
@ScriptManifest(name = "Ring Maker", version = 1.3, author = "Stress", info = "Makes rings for profit.", logo = "http://i.imgur.com/IEmAY4I.png")
public class RingMaker extends OmniScript {

    /* Variables */
    private long startTime;

    private int startXP;
    private double profit;
    private int startCraftXP;
    public static int buttDick; // Ring ID for loot
    public static boolean started = false;
    public static String dickButt; // Ring name

    private Loot RING;

    RingGui frame = new RingGui();

    @Override
    public void onStart() {
        frame.setVisible(true);
        while (frame.isVisible()) {
            try {
                sleep(400);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        setLogLevel(LogLevel.DEBUG);
        startTime = System.currentTimeMillis();
        startXP = getSkills().getExperience(Skill.CRAFTING);
        getExperienceTracker().start(Skill.CRAFTING);
        getExperienceTracker().start(Skill.MAGIC);
        RING = new Loot(buttDick);
    }

    @Override
    public void onExit() {

    }

    @Override
    public int onLoop() throws InterruptedException {
        long ringCount = getInventory().getAmount(buttDick);
        if (ringCount > RING.prevInvCount) {
            RING.count += ringCount - RING.prevInvCount;
            RING.prevInvCount = ringCount;
            return 0;
        }
        getNodeHandler().addNode(new CraftRing(this));
        getNodeHandler().addNode(new EnchantRing(this));
        getNodeHandler().addNode(new Banking(this));
        return getNodeHandler().execute();
    }

    @Override
    public void onPaint(Graphics2D gg) {
        long runTime = System.currentTimeMillis() - startTime;
        long ringProfit = RING.getProfit();
        float hours = ((float) runTime / 3600000f);
        gg.setColor(Color.red);
        gg.setFont(new Font("Arial", Font.PLAIN, 13));
        Point mP = getMouse().getPosition();

        /* X cursor for mouse pos */
        gg.drawLine(mP.x - 5, mP.y + 5, mP.x + 5, mP.y - 5);
        gg.drawLine(mP.x + 5, mP.y + 5, mP.x - 5, mP.y - 5);

        gg.setColor(Color.white);
        gg.drawString("Ring Maker", 20, 180);
        gg.drawString("Run Time: " + (formatTime(runTime)), 20, 200);
        gg.drawString("Gained magic xp: " + getExperienceTracker().getGainedXP(Skill.MAGIC) + " (" + getExperienceTracker().getGainedXPPerHour(Skill.MAGIC) + ")", 20, 220);
        gg.drawString("Gained magic levels: " + getExperienceTracker().getGainedLevels(Skill.MAGIC), 20, 240);
        gg.drawString("Gained craft xp: " + getExperienceTracker().getGainedXP(Skill.CRAFTING) + " (" + getExperienceTracker().getGainedXPPerHour(Skill.CRAFTING) + ")", 20, 260);
        gg.drawString("Gained craft levels: " + getExperienceTracker().getGainedLevels(Skill.CRAFTING), 20, 280);
        gg.drawString("Rings Made: " + getRingsMade(startXP, getSkills().getExperience(Skill.CRAFTING)), 20, 300);
        gg.drawString("Profit Made: " + ringProfit + " (" + (ringProfit / hours) + ")", 20, 320);

    }

    public static int getRingsMade(final int startXP, final int currentXP) {
        return (currentXP - startXP) / 40;
    }

    public String formatTime(long ms) {
        long s = ms / 1000, m = s / 60, h = m / 60;
        s %= 60;
        m %= 60;
        h %= 24;
        return String.format("%02d:%02d:%02d", h, m, s);
    }

    private void drawProfitH(Graphics2D gg, double profit, float hours) {
        gg.setColor(Color.PINK);
        gg.drawString("Profit/h: " + (profit / hours), 20, 360);
    }

    private class Loot {

        int id; // Id used for getting the price of the item
        int price; // Stores the price of the item
        long count; // Stores the total count of loot obtained
        long prevInvCount; // Stores the previous seen inventory count

        public Loot(int id) { // Constructor with Id of the item passed as an argument
            this.id = id;
            this.prevInvCount = getInventory().getAmount(id);
            log(price = getPrice(id));
        }

        public long getProfit() {

            return count * price; // Profit = quantity * price
        }

        private int getPrice(int id) {

            try {
                URL url = new URL("http://api.rsbuddy.com/grandExchange?a=guidePrice&i=" + id);
                URLConnection con = url.openConnection();
                con.setUseCaches(true);
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String[] data = br.readLine().replace("{", "").replace("}", "").split(",");
                return Integer.parseInt(data[0].split(":")[1]);
            } catch (Exception e) {
                log(e);
            }
            return -1;
        }
    }
}


