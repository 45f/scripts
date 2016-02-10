package curserbot.src;

import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Spells;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.ConditionalSleep;

import javax.swing.*;
import javax.swing.plaf.metal.MetalIconFactory;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Copyright (c) 2015  Stress
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
 * Created by Stress on 1/14/2016.
 * This file is part of curserbot.src.
 * Created with IntelliJ IDEA.
 *
 * @info
 */
@ScriptManifest(info = "curses stuff", name = "Curse Bot", logo = "", author = "stress", version = 1.0)
public class CurseBot extends Script {

    /* Variables */
    long startTime;
    int startXP;
    int gainedXP;

    private TrayIcon alerter;

    @Override
    public void onStart() {
        Icon defaultIcon = MetalIconFactory.getTreeHardDriveIcon();
        Image image = new BufferedImage(defaultIcon.getIconWidth(), defaultIcon.getIconHeight(), 6);
        defaultIcon.paintIcon(new Panel(), image.getGraphics(), 0, 0);
        this.alerter = new TrayIcon(image, "which do", new PopupMenu("how do"));

        startTime = System.currentTimeMillis();
        getExperienceTracker().start(Skill.MAGIC);

        try {
            SystemTray.getSystemTray().add(this.alerter);
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }

    public void onExit() {
        try {
            SystemTray.getSystemTray().add(this.alerter);
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int onLoop() throws InterruptedException {
        sendAlert();
        org.osbot.rs07.api.model.NPC target = getNpcs().closest("Monk of Zamorak");
        if (target != null) {
            if (target.isVisible()) {
                if (this.npcs.closest("Monk of Zamorak") != null) {
                    this.getMagic().castSpellOnEntity(Spells.NormalSpells.CURSE,
                            this.npcs.closest("Monk of Zamorak"));
                    new ConditionalSleep(2000) {
                        @Override
                        public boolean condition() throws InterruptedException {
                            return myPlayer().isAnimating();
                        }
                    }.sleep();
                }
            }
        }

        if (!getInventory().contains("Body rune")) {
            logoutTab.logOut();
        }
        return 1000;
    }

    @Override
    public void onPaint(Graphics2D gg) {
        long runTime = System.currentTimeMillis() - startTime;
        gainedXP = getExperienceTracker().getGainedXP(Skill.MAGIC);

        gg.setColor(Color.white);
        gg.setFont(new Font("Arial", Font.PLAIN, 15));

        gg.drawString("run time: " + (formatTime(runTime)), 325, 250);
        gg.drawString("gained xp: " + (formatValue(gainedXP)), 325, 270);
        gg.drawString("gained xp/hr: " + (formatValue(getExperienceTracker().getGainedXPPerHour(Skill.MAGIC))), 325, 290);
        gg.drawString("ttl: " + (formatTime(getExperienceTracker().getTimeToLevel(Skill.MAGIC))), 325, 310);

        Point mP = getMouse().getPosition();

        gg.drawLine(mP.x - 5, mP.y + 5, mP.x + 5, mP.y - 5);
        gg.drawLine(mP.x + 5, mP.y + 5, mP.x - 5, mP.y - 5);
    }

    public String formatTime(long ms) {

        long s = ms / 1000, m = s / 60, h = m / 60, d = h / 24;
        s %= 60;
        m %= 60;
        h %= 24;

        return d > 0 ? String.format("%02d:%02d:%02d:%02d", d, h, m, s) :
                h > 0 ? String.format("%02d:%02d:%02d", h, m, s) :
                        String.format("%02d:%02d", m, s);
    }

    public String formatValue(int v) {

        if (v > 1_000_000) return String.format("%.2fm", (double) (v / 1_000_000));
        else if (v > 1000) return String.format("%.1fk", (double) (v / 1000));
        return v + "";
    }

    public void sendAlert() {
        Toolkit.getDefaultToolkit().beep();
        log("FIRING ALERT");
        this.alerter.displayMessage("Level up!", "hello world", TrayIcon.MessageType.INFO);

    }
}

