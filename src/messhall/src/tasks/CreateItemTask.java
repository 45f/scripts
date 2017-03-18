package messhall.src.tasks;

import dependencies.tasks.Task;
import org.osbot.rs07.script.MethodProvider;

/**
 * Created by RAIJIN on 3/13/2017.
 */
public class CreateItemTask extends Task {

    public CreateItemTask(MethodProvider ctx) {
        super(ctx);
    }

    @Override
    public boolean canExecute() {
        return false;
    }

    @Override
    public void execute() throws InterruptedException {

    }
}
