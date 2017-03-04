package resources.paint;

import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.MethodProvider;
import resources.util.Timer;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created  on 8/14/2016.
 */
public class ProgressBar {

    private final static int ALPHA = 160, X = 7, Y = 344, WIDTH = 489, HEIGHT = 15;
    private static final String[] suffix = new String[]{"", "k", "m", "b", "t"};
    private static boolean hasUpdated, startedThread;

    public static void repaint(Graphics g2, MethodProvider ctx) {
        if (hasUpdated) {
            Graphics2D g = (Graphics2D) g2;
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            List<PaintSkill> paintSkillList = Arrays.stream(PaintSkill.values())
                    .filter(v -> ctx.getExperienceTracker().getGainedXP(v.getSkill()) > 0)
                    .collect(Collectors.toList());

            if (!paintSkillList.isEmpty()) {
                int size = paintSkillList.size() > 9 ? 9 : paintSkillList.size();
                for (int i = 0; i < size; i++) {
                    PaintSkill skill = paintSkillList.get(i);
                    Color blackTrans = new Color(0, 0, 0, 127);


                    g.setFont(new Font("Arial", Font.PLAIN, 12));

                    g.setColor(blackTrans);
                    g.fillRect(X, (Y + (i * HEIGHT) + 1), WIDTH, 113);
                    g.setColor(skill.getBackground());
                    g.fillRoundRect(X, (Y + (i * HEIGHT) + 1), WIDTH, HEIGHT, 5, 5);

                    g.setColor(skill.getForeground());
                    g.fillRoundRect(X, (Y + (i * HEIGHT) + 1), getBarFillPercent(WIDTH, ctx, skill.getSkill()), HEIGHT, 5, 5);

                    String text = skill.getSkill().name() + ": ";
                    text += ctx.getSkills().getStatic(skill.getSkill()) + " (+" + ctx.getExperienceTracker().getGainedLevels(skill.getSkill()) + ")";
                    text += "  |  XP Gained: " + format(ctx.getExperienceTracker().getGainedXP(skill.getSkill()));
                    text += "  |  XP/HR: " + format(ctx.getExperienceTracker().getGainedXPPerHour(skill.getSkill()));
                    //text += "|  XPTL: " + format(ctx.getExperienceTracker().getTimeToLevel(skill.getSkill()));
                    text += "  |  TTL: " + Timer.formatTime(ctx.getExperienceTracker().getTimeToLevel(skill.getSkill()));

                    g.setColor((skill.equals(PaintSkill.PRAYER) || skill.equals(PaintSkill.RUNECRAFTING)) ? Color.black : Color.white);
                    g.drawString(text, X + 6, (13 + Y + (i * HEIGHT)));
                }
            }

            g.dispose();
        } else if (!startedThread) {
            new Thread(() -> startSkillTrackers(ctx)).start();
            startedThread = true;
        }
    }

    private static int getBarFillPercent(int barWidth, MethodProvider ctx, Skill skill) {
        double xpTL = ctx.getSkills().getExperienceForLevel(ctx.getSkills().getDynamic(skill));
        double xpTL1 = ctx.getSkills().getExperienceForLevel(ctx.getSkills().getDynamic(skill) + 1);
        double percentTNL = ((ctx.getSkills().getExperience(skill) - xpTL) / (xpTL1 - xpTL) * 100);
        return (int) ((barWidth / 100D) * percentTNL);
    }

    private static String format(double number) {
        String r = new DecimalFormat("##0E0").format(number);
        r = r.replaceAll("E[0-9]", suffix[Character.getNumericValue(r.charAt(r.length() - 1)) / 3]);
        while (r.length() > 4 || r.matches("[0-9]+\\.[a-z]")) {
            r = r.substring(0, r.length() - 2) + r.substring(r.length() - 1);
        }
        return r;
    }

    private static void startSkillTrackers(MethodProvider ctx) {
        while (!ctx.getClient().isLoggedIn() && ctx.getClient().getBot().getScriptExecutor().isRunning())
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        for (Skill s : Skill.values()) //Start all of the skill trackers
            ctx.getExperienceTracker().start(s);

        hasUpdated = true;
        //log("Started skill trackers!"); //Notify
    }

    private enum PaintSkill {
        ATTACK(new Color(80, 17, 8, ALPHA)),
        DEFENCE(new Color(111, 131, 196, ALPHA)),
        STRENGTH(new Color(13, 112, 65, ALPHA)),
        HITPOINTS(new Color(206, 58, 71, ALPHA)),
        RANGED(new Color(85, 111, 13, ALPHA)),
        PRAYER(new Color(224, 220, 220, ALPHA)),
        MAGIC(new Color(162, 150, 129, ALPHA)),
        COOKING(new Color(61, 12, 77, ALPHA)),
        WOODCUTTING(new Color(145, 115, 53, ALPHA)),
        FLETCHING(new Color(0, 36, 37, ALPHA)),
        FISHING(new Color(96, 128, 161, ALPHA)),
        FIREMAKING(new Color(202, 86, 12, ALPHA)),
        CRAFTING(new Color(125, 90, 56, ALPHA)),
        SMITHING(new Color(52, 52, 31, ALPHA)),
        MINING(new Color(56, 97, 100, ALPHA)),
        HERBLORE(new Color(3, 69, 5, ALPHA)),
        AGILITY(new Color(23, 25, 96, ALPHA)),
        THIEVING(new Color(82, 29, 62, ALPHA)),
        SLAYER(new Color(22, 20, 20, ALPHA)),
        FARMING(new Color(3, 56, 3, ALPHA)),
        RUNECRAFTING(new Color(181, 181, 169, ALPHA)),
        HUNTER(new Color(99, 91, 56, ALPHA)),
        CONSTRUCTION(new Color(155, 143, 123, ALPHA));

        private final Color foreground;
        private final Color background;

        PaintSkill(Color background) {
            this.background = background;
            this.foreground = new Color(background.getRed(), background.getGreen(), background.getBlue()).brighter();
        }

        public Color getForeground() {
            return foreground;
        }

        public Color getBackground() {
            return background;
        }

        public Skill getSkill() {
            return Skill.values()[ordinal()];
        }
    }
}
