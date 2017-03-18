package messhall.src.tasks;

import dependencies.handlers.InventoryHandler;
import dependencies.tasks.Task;
import dependencies.util.CSleep;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep;

/**
 * Created by RAIJIN on 3/13/2017.
 */
public class CookingTask extends Task {

    public CookingTask(MethodProvider ctx) {
        super(ctx);
    }

    private InventoryHandler handler = new InventoryHandler(ctx);

    @Override
    public boolean canExecute() {
        return ctx.getInventory().contains("Servery raw meat")
                || ctx.getInventory().contains("Servery uncooked pie");
    }

    @Override
    public void execute() throws InterruptedException {
        if (canCookMeat()) {
            cookMeat();
            cookAll();
        }
    }

    private boolean isCAVisible() {
        RS2Widget amountWidget = ctx.getWidgets().singleFilter(ctx.getWidgets().getAll(), widget -> widget.getMessage().contains("How many"));
        return amountWidget != null && amountWidget.isVisible();
    }

    private boolean cookAll() {
        return ctx.getWidgets().get(307, 2).interact("Cook All");
    }

    private boolean canCookMeat() {
        return ctx.getInventory().contains("Servery pie shell") && ctx.getInventory().contains("Servery raw meat");
    }

    private boolean canCookPie() {
        return ctx.getInventory().contains("Servery uncooked pie") && !ctx.getInventory().contains("Servery meat pie")
                && !ctx.getInventory().contains("Servery raw meat");
    }

    private void cookMeat() {
        ctx.log("cooking meat");
        if (isCAVisible()) {
            new ConditionalSleep(30000) {
                @Override
                public boolean condition() throws InterruptedException {
                    return !ctx.myPlayer().isAnimating();
                }
            }.sleep();
        } else if (!isCAVisible()) {
            handler.itemOnEntity("Servery raw meat", "Clay oven");
            CSleep.sleepUntil(this::isCAVisible, 5000);
        }
    }

    private void cookPie() throws InterruptedException {
        ctx.log("cooking pies");
        if (isCAVisible()) {
            new ConditionalSleep(30000) {
                @Override
                public boolean condition() throws InterruptedException {
                    return !ctx.myPlayer().isAnimating();
                }
            }.sleep();
        } else if (!isCAVisible()) {
            handler.itemOnEntity("Servery uncooked pie", "Clay oven");
            CSleep.sleepUntil(this::isCAVisible, 5000);
        }
    }

    private void cookStew() {
        ctx.log("cooking stews");
        if (isCAVisible()) {
            new ConditionalSleep(30000) {
                @Override
                public boolean condition() throws InterruptedException {
                    return !ctx.myPlayer().isAnimating();
                }
            }.sleep();
        } else if (!isCAVisible()) {
            handler.itemOnEntity("Servery uncooked stew", "Clay oven");
            CSleep.sleepUntil(this::isCAVisible, 5000);
        }
    }
}
