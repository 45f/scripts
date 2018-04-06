package script;

import org.osbot.rs07.script.MethodProvider;

/**
 * <p>
 * </p>
 *
 * @author Capitals <onlytainted@gmail.com>
 */
public abstract class Task {

    protected MethodProvider ctx;

    protected int debug;

    public Task(MethodProvider ctx) {
        this.ctx = ctx;
    }

    /**
     * @return if this Task should execute.
     */
    public abstract boolean verify();

    /**
     * Executes this Task.
     *
     * @return sleep time after this script.tasks ends.
     */
    public abstract void process() throws InterruptedException;

    /**
     * @return a description of the current Task.
     */
    public abstract String status();

    public abstract int getDebug();

    public abstract void setDebug(int debug);
}
