package script.core.hosidius;

import org.osbot.rs07.script.MethodProvider;
import script.Paint;
import script.Task;
import script.util.Sleep;

/**
 * <p>
 * </p>
 *
 * @author Capitals <onlytainted@gmail.com>
 */
public class MakeFertiliser extends Task {

    private String COMPOST = "Compost";
    private String SALT = "Saltpetre";
    private String FERTILISER = "Sulphurous fertiliser";


    public MakeFertiliser(MethodProvider ctx) {
        super(ctx);
        setDebug(-1);
    }

    @Override
    public boolean verify() {
        return ctx.getInventory().isEmpty() || ctx.getInventory().contains("Sulphurous fertiliser");
    }

    @Override
    public void process() throws InterruptedException {
        if (ctx.getInventory().isEmpty() || ctx.getInventory().contains("Sulphurous fertiliser")) {
            bank();
        } else if (ctx.getInventory().contains(COMPOST) && ctx.getInventory().contains(SALT)) {
            combine();
        } else {
            stop();
            ctx.logger.debug("out of items, stopping script");
        }
    }

    @Override
    public String status() {
        return "MIX";
    }

    @Override
    public int getDebug() {
        return debug;
    }

    @Override
    public void setDebug(int debug) {
        this.debug = debug;
    }

    private void bank() throws InterruptedException {
        if (!ctx.getBank().isOpen()) {
            Paint.status = "Opening bank";
            ctx.getBank().open();
            ctx.logger.debug("opened bank");
            Sleep.sleepUntil(() -> ctx.getBank().isOpen(), 5000);
        } else if (ctx.getInventory().contains("Sulphurous fertiliser")) {
            Paint.status = "Depositing inventory";
            ctx.getBank().depositAll();
        } else if (!ctx.getInventory().contains(FERTILISER) || !ctx.getInventory().contains(COMPOST)) {
            if (ctx.getBank().contains(FERTILISER) || ctx.getBank().contains(COMPOST)) {
                Paint.status = "Withdrawing items";
                ctx.getBank().withdraw(COMPOST, 14);
                ctx.getBank().withdraw(SALT, 14);
                ctx.logger.debug("withdrew items");
                ctx.getBank().close();
            }
        }
    }

    private void combine() {
        if (!ctx.getInventory().isItemSelected()) {
            if (COMPOST != null) {
                Paint.status = "Selecting compost";
                ctx.getInventory().interact("Use", COMPOST);
                ctx.logger.debug("selected compost");
                Paint.status = "Using it on salt";
                ctx.getInventory().interact("Use", SALT);
                ctx.logger.debug("combining items");
                Paint.status = "Mixing items";
                handleDialogue();
                Sleep.sleepUntil(() -> ctx.getDialogues().isPendingContinuation(), 5000);
                Sleep.sleepUntil(() -> !ctx.getInventory().contains(COMPOST), 30000);

            }
        }
    }

    private void handleDialogue() {
        if (ctx.getDialogues().isPendingContinuation()) {
            ctx.getDialogues().clickContinue();
            Paint.status = "Handling dialogue";
            ctx.log("handling dialogue");
            Sleep.sleepUntil(() -> !ctx.getDialogues().isPendingContinuation(), 5000);
        }
    }

    private void stop() throws InterruptedException {
        if (!ctx.getBank().contains(COMPOST) && !ctx.getBank().contains(FERTILISER) && !ctx.getInventory().contains(COMPOST) && !ctx.getInventory().contains(FERTILISER)) {
            ctx.getBot().getScriptExecutor().stop();
        }
    }
}
