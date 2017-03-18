package messhall.src.tasks;

import dependencies.tasks.Task;
import dependencies.util.CSleep;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.MethodProvider;

/**
 * Created by RAIJIN on 3/15/2017.
 */
public class TurnInTask extends Task {

    public TurnInTask(MethodProvider ctx) {
        super(ctx);
    }


    @Override
    public boolean canExecute() {
        return ctx.getInventory().onlyContains("Servery meat pie") && !ctx.getInventory().contains("Servery uncooked pie");
    }

    @Override
    public void execute() throws InterruptedException {
        if (canExecute()) completePie();

    }

    private void completePie() {
        ctx.log("completing the task");
        final RS2Object bT = ctx.getObjects().closest("Buffet table");
        if (ctx.getInventory().contains("Servery meat pie")) {
            bT.interact("Serve");
            CSleep.sleepUntil(() -> !ctx.getInventory().contains("Servery meat pie"), 5000);
            ctx.getWorlds().hopToP2PWorld();
        }
    }
}
