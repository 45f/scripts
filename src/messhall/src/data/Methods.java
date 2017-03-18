package messhall.src.data;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep;

/**
 * Created by RAIJIN on 3/12/2017.
 */
public class Methods {

    static MethodProvider ctx;

    public static boolean canGetBowl() {
        return ctx.getInventory().isEmpty();
    }

    public static boolean canGetFlour() {
        return ctx.getInventory().contains("Bowl of water") && !ctx.getInventory().contains("Servery flour");
    }

    public static boolean searchUtils() {
        return ctx.getObjects().closest("Utensil cupboard").interact("Search");
    }

    public static boolean searchFood() {
        return ctx.getObjects().closest("Food cupboard").interact("Search");
    }

    public static boolean canMix() {
        return ctx.getInventory().contains("Bowl of water") && ctx.getInventory().contains("Servery flour")
                || ctx.getInventory().contains("Servery pastry dough") && ctx.getInventory().contains("Servery dish")
                || ctx.getInventory().contains("Servery pie shell") && ctx.getInventory().contains("Servery cooked meat");
    }

    public static boolean canGetPie() {
        return ctx.getInventory().contains("Servery pastry dough") && !ctx.getInventory().contains("Servery dish");
    }

    public static boolean canMakeShell() {
        return ctx.getInventory().contains("Servery pastry dough") && ctx.getInventory().contains("Servery dish");
    }

    public static boolean canGrabMeat() {
        return ctx.getInventory().contains("Servery pie shell") && !ctx.getInventory().contains("Servery raw meat");
    }

    public static boolean canCookMeat() {
        return ctx.getInventory().contains("Servery pie shell") && ctx.getInventory().contains("Servery raw meat");
    }

    public static boolean canCookPie() {
        return ctx.getInventory().contains("Servery uncooked pie") && !ctx.getInventory().contains("Servery meat pie")
                && !ctx.getInventory().contains("Servery raw meat");
    }

    public static boolean canCompleteTask() {
        return ctx.getInventory().contains("Servery meat pie") && !ctx.getInventory().contains("Servery uncooked pie");
    }

    public static boolean takeItem1() {
        return ctx.getWidgets().get(242, 3, 1).interact("Take-X");
    }

    public static boolean takeItem2() {
        return ctx.getWidgets().get(242, 3, 0).interact("Take-X");
    }


    private void fillBowls() {
        final long amt = ctx.getInventory().getAmount("Bowl");
        RS2Object sink = ctx.getObjects().closest("Sink");
        if (ctx.getInventory().contains("Bowl")) {
            ctx.getInventory().interact("Use", "Bowl");
            sink.interact("Use");
            new ConditionalSleep(10000) {
                @Override
                public boolean condition() throws InterruptedException {
                    return ctx.getInventory().getAmount("Bowl of water") >= amt;
                }
            }.sleep();
        }
    }
}
