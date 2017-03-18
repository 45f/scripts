package messhall.src.tasks;

import dependencies.handlers.InventoryHandler;
import dependencies.tasks.Task;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep;

/**
 * Created by RAIJIN on 3/13/2017.
 */
public class CombineTask extends Task {

    public CombineTask(MethodProvider ctx) {
        super(ctx);
    }

    private InventoryHandler handler = new InventoryHandler(ctx);

    @Override
    public boolean canExecute() {
        return true;
    }

    @Override
    public void execute() throws InterruptedException {
        if (canMakeStew()) makeStew1();
        if (canMakeFullStew()) makeStewFull();
    }

    private void makeDough() {
        handler.itemOnItem("Bowl of water", "Servery flour");
        ctx.logger.info("Mixing items");
        new ConditionalSleep(1200, 600) {
            @Override
            public boolean condition() throws InterruptedException {
                final RS2Widget dough = ctx.getWidgets().get(219, 0, 1);
                return dough != null && dough.isVisible() && dough.hover() && ctx.getMouse().click(false);
            }
        }.sleep();

        new ConditionalSleep(30000) {
            @Override
            public boolean condition() throws InterruptedException {
                return ctx.getInventory().getAmount("Servery pastry dough") == 14;
            }
        }.sleep();
        ctx.log("making dough");
    }

    private void makeShell() {
        handler.itemOnItem("Servery pastry dough", "Servery dish");
        ctx.logger.info("Mixing dough and dish");
        new ConditionalSleep(30000) {
            @Override
            public boolean condition() throws InterruptedException {
                return !ctx.getInventory().contains("Servery pastry dough");
            }
        }.sleep();
        ctx.logger.info("Making pie shells");
    }

    private void makePie() {
        new ConditionalSleep(60000) {
            @Override
            public boolean condition() throws InterruptedException {
                return !ctx.getInventory().contains("Servery raw meat");
            }
        }.sleep();
        handler.itemOnItem("Servery pie shell", "Servery cooked meat");
        new ConditionalSleep(30000) {
            @Override
            public boolean condition() throws InterruptedException {
                return ctx.getInventory().getAmount("Servery meat pie") == 14;
            }
        }.sleep();
        ctx.logger.info("Making pies");
    }

    private void makeStew1() {
        new ConditionalSleep(60000) {
            @Override
            public boolean condition() throws InterruptedException {
                return !ctx.getInventory().contains("Servery raw meat");
            }
        }.sleep();
        handler.itemOnItem("Bowl of water", "Servery cooked meat");
        new ConditionalSleep(30000) {
            @Override
            public boolean condition() throws InterruptedException {
                return ctx.getInventory().getAmount("Servery incomplete stew") == 14;
            }
        }.sleep();
        ctx.logger.info("Making stew 1");
    }

    private void makeStewFull() {
        new ConditionalSleep(60000) {
            @Override
            public boolean condition() throws InterruptedException {
                return !ctx.getInventory().contains("Servery raw meat");
            }
        }.sleep();
        handler.itemOnItem("Servery incomplete stew", "Servery potato");
        new ConditionalSleep(30000) {
            @Override
            public boolean condition() throws InterruptedException {
                return !ctx.getInventory().contains("Servery incomplete stew") && !ctx.getInventory().contains("Servery potato");
            }
        }.sleep();
        ctx.logger.info("Making pies");
    }

    private boolean canMakeDough() {
        return ctx.getInventory().contains("Bowl of water") && ctx.getInventory().contains("Servery flour");
    }

    private boolean canMakeShell() {
        return ctx.getInventory().contains("Servery pastry dough") && ctx.getInventory().contains("Servery dish");
    }

    private boolean canMakePie() {
        return ctx.getInventory().contains("Servery pie shell") && ctx.getInventory().contains("Servery cooked meat");
    }

    private boolean canMakeStew() {
        return ctx.getInventory().contains("Bowl of water") && ctx.getInventory().contains("Servery cooked meat");
    }

    private boolean canMakeFullStew() {
        return ctx.getInventory().contains("Servery incomplete stew") && ctx.getInventory().contains("Servery potato");
    }
}
