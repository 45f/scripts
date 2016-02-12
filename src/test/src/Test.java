package test.src;

import omniapi.OmniScript;
import omniapi.debug.LogLevel;
import org.osbot.rs07.script.ScriptManifest;

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
 * Created by Stress on 1/25/2016.
 * This file is part of test.src.
 * Created with IntelliJ IDEA.
 *
 * @info
 */
@ScriptManifest(info = "Test bot 2", name = "Cons Bot", logo = "", author = "Stress", version = 1.0)
public class Test extends OmniScript {

    private TrayIcon alerter;


    //public static boolean started = false;

    @Override
    public void onStart() {
        setLogLevel(LogLevel.DEBUG);

        Icon defaultIcon = MetalIconFactory.getTreeHardDriveIcon();
        Image image = new BufferedImage(defaultIcon.getIconWidth(), defaultIcon.getIconHeight(), 6);
        defaultIcon.paintIcon(new Panel(), image.getGraphics(), 0, 0);

    }

    @Override
    public void onExit() {
        SystemTray.getSystemTray().remove(alerter);
    }

    @Override
    public int onLoop() throws InterruptedException {

      //sendAlert();

        return 3000;

    }

    public void sendAlert() {
        // Toolkit.getDefaultToolkit().beep(); //optional beep to notify user
        this.alerter.displayMessage("Level up!", "bottom alert", TrayIcon.MessageType.INFO);
    }


}
