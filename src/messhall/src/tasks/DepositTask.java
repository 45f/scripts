package messhall.src.tasks;

import dependencies.handlers.InventoryHandler;
import dependencies.tasks.Task;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep;

/**
 * Created by RAIJIN on 3/14/2017.
 */
public class DepositTask extends Task {

    public DepositTask(MethodProvider ctx) {
        super(ctx);
    }

    private InventoryHandler handler = new InventoryHandler(ctx);

    @Override
    public boolean canExecute() {
        return ctx.getInventory().contains("Servery pastry dough")
                && ctx.getInventory().contains("Bowl");
    }

    @Override
    public void execute() throws InterruptedException {
        ctx.log("depositing bowls");
        if (canExecute()) {
            handler.itemOnEntity("Bowl", "Utensil cupboard");
            new ConditionalSleep(5000) {
                @Override
                public boolean condition() throws InterruptedException {
                    return ctx.getInventory().onlyContains("Servery pastry dough");
                }
            }.sleep();
        }
    }
}
