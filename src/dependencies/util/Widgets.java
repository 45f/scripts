package dependencies.util;


import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

public abstract class Widgets {

    private static Script s;

    public static boolean containsText(int parent, int child, String text) {
        RS2Widget widget = s.getWidgets().get(parent, child);
        if (exists(widget) && widget.isVisible()) {
            return widget.getMessage().contains(text);
        }
        return false;
    }

    public static boolean interactTil(String action, int i, int j, int k, ConditionalSleep con) {
        RS2Widget widget = s.getWidgets().get(i, j, k);
        if (exists(widget)) {
            widget.interact(action);
            con.sleep();
            return true;
        }
        return false;
    }

    public static String getText(int i, int j) {
        RS2Widget widget = s.getWidgets().get(i, j);
        if (exists(widget) && widget.isVisible()) {
            return widget.getMessage();
        }
        return "";
    }

    private static boolean exists(RS2Widget widget) {
        return widget != null;
    }

    public static boolean interactTil(String action, int parent, int child, ConditionalSleep con) {
        RS2Widget widget = s.getWidgets().get(parent, child);
        if (exists(widget)) {
            widget.interact(action);
            con.sleep();
            return true;
        }
        return false;
    }

    public static boolean interactTilNull(String action, int parent, int child) {
        RS2Widget widget = s.getWidgets().get(parent, child);
        if (exists(widget)) {
            widget.interact(action);
            new ConditionalSleep(2500, 3000) {
                @Override
                public boolean condition() {
                    return !exists(widget);
                }
            }.sleep();
            return true;
        }
        return false;
    }

    public static boolean interactTilNull(String action, int parent, int child, int child2) {
        RS2Widget widget = s.getWidgets().get(parent, child, child2);
        if (exists(widget)) {
            widget.interact(action);
            new ConditionalSleep(2500, 3000) {
                @Override
                public boolean condition() {
                    return !exists(widget);
                }
            }.sleep();
            return true;
        }
        return false;
    }

}
