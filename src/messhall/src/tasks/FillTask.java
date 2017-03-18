package messhall.src.tasks;

import dependencies.handlers.InventoryHandler;
import dependencies.tasks.Task;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep;

/**
 * Created by RAIJIN on 3/13/2017.
 */
public class FillTask extends Task {

    public FillTask(MethodProvider ctx) {
        super(ctx);

    }

    private InventoryHandler handler = new InventoryHandler(ctx);

    @Override
    public boolean canExecute() {
        return ctx.getInventory().onlyContains("Bowl");
    }

    @Override
    public void execute() throws InterruptedException {
        ctx.log("filling bowls");
        final long amt = ctx.getInventory().getAmount("Bowl");
        if (ctx.getInventory().contains("Bowl")) {
            handler.itemOnEntity("Bowl", "Sink");
            new ConditionalSleep(8000) {
                @Override
                public boolean condition() throws InterruptedException {
                    return ctx.getInventory().getAmount("Bowl of water") >= amt;
                }
            }.sleep();
        }
    }

}
