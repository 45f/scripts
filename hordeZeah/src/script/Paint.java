package script;

import org.osbot.rs07.api.ui.Skill;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * <p>
 * </p>
 *
 * @author Capitals <onlytainted@gmail.com>
 */
public class Paint {

    protected HordeZeah s;
    private Task t;

    public Paint(HordeZeah s) {
        this.s = s;
    }

    public final String formatTime(final long ms) {
        long s = ms / 1000, m = s / 60, h = m / 60;
        s %= 60;
        m %= 60;
        h %= 24;
        return String.format("%02d:%02d:%02d", h, m, s);
    }

    public Image getImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
            return null;
        }
    }
    public final long startTime = System.currentTimeMillis();

    public static String status = "";

    public void repaint(Graphics paint) {
        Graphics2D gg = (Graphics2D) paint;
        long upTime = System.currentTimeMillis() - startTime;

        gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Font FONT = new Font("Arial", Font.PLAIN, 11);
        Color BLACK = new Color(0, 0, 0, 125); // 180% alpha

        paint.setFont(FONT);
        paint.setColor(BLACK);
        paint.fillRect(1, 263, 275, 75);

        paint.setColor(Color.WHITE);
        paint.drawString("Horde Zeah v: " + s.getVersion() + " - " + formatTime(upTime), 5, 275);
        paint.drawString("Status: " + status, 5, 285);

       if (s.getExperienceTracker().getGainedXP(Skill.CRAFTING) > 0) {
           paint.drawString("Experience gained: " + (s.getExperienceTracker().getGainedXP(Skill.CRAFTING)), 5, 295);
       }
    }
}
