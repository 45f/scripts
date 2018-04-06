package script.core.piscarilius;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.event.WalkingEvent;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.Condition;
import script.Paint;
import script.Task;
import script.util.Sleep;

/**
 * <p>
 * </p>
 *
 * @author Capitals <onlytainted@gmail.com>
 */
public class HuntWorms extends Task {

    public HuntWorms(MethodProvider ctx) {
        super(ctx);
        setDebug(-1);
    }

    @Override
    public boolean verify() {
        return canDig();
    }

    @Override
    public void process() throws InterruptedException {
        if (ctx.getInventory().isEmpty()) {
            bank();
            Paint.status = "Banking";
            ctx.log("banking");
        } else if (ctx.getInventory().onlyContains("Spade")) {
            walkToBank();
            Paint.status = "Walking to bank";
            ctx.log("walking to bank");
            bank();
            Paint.status = "Banking";
            ctx.log("banking");
        } else if (hasMaterials()) {
            walkToWorms();
            dig();
            emptyBucket();
        } else if (canTurnIn()) {
            walkToStore();
            Paint.status = "Walking to store";
            ctx.log("walking to store");
            turnIn();
            Paint.status = "Turning in worms";
            ctx.log("turned in worms");
        }
    }

    @Override
    public String status() { return "HUNTING"; }

    @Override
    public int getDebug() {
        return debug;
    }

    @Override
    public void setDebug(int debug) {
        this.debug = debug;
    }

    private boolean hasMaterials() {
        return ctx.getInventory().contains("Spade") && ctx.getInventory().contains("Bucket");
    }

    private boolean canDig() {
        final Area WORM_AREA = new Area(1834, 3791, 1848, 3812);
        return WORM_AREA.contains(ctx.myPlayer()) && ctx.getInventory().contains("Spade") && ctx.getInventory().contains("Bucket");
    }

    private boolean canTurnIn() {
        return ctx.getInventory().onlyContains("Spade", "Bucket of sandworms");
    }

    private void walkToBank() {
        final Position BANK_SPOT = new Position(1804, 3790, 0);
        WalkingEvent TO_BANK = new WalkingEvent(BANK_SPOT);

        if (!ctx.getInventory().contains("Bucket")) {
            ctx.log("need more buckets - walking to the bank");
            TO_BANK.setMinDistanceThreshold(5);
            TO_BANK.setBreakCondition(new Condition() {
                @Override
                public boolean evaluate() {
                    return Banks.PISCARILIUS_HOUSE.contains(ctx.myPlayer());
                }
            });
            TO_BANK.setAsync();
            ctx.execute(TO_BANK);
        }
    }

    private void walkToWorms() {
        final Area WORM_AREA = new Area(1834, 3809, 1844, 3795);
        WalkingEvent TO_WORMS = new WalkingEvent(WORM_AREA);

        if (hasMaterials()) {
            TO_WORMS.setMinDistanceThreshold(5);
            TO_WORMS.setBreakCondition(new Condition() {
                @Override
                public boolean evaluate() {
                    return WORM_AREA.contains(ctx.myPlayer());
                }
            });
            ctx.execute(TO_WORMS);
        }
    }

    private void walkToStore() {
        final Area SHOP_AREA = new Area(1845, 3789, 1836, 3782);

        if (ctx.getInventory().onlyContains("Spade", "Bucket of sandworms")) {
            if (!SHOP_AREA.contains(ctx.myPosition())) {
                ctx.getWalking().walk(SHOP_AREA);
                Paint.status = "Walking to store";
            }
        }
    }

    private void bank() throws InterruptedException {
        if (Banks.PISCARILIUS_HOUSE.contains(ctx.myPosition())) {
            if (!ctx.getBank().isOpen()) {
                ctx.getBank().open();
                Sleep.sleepUntil(() -> ctx.getBank().isOpen(), 5000);
            }
        }
        if (ctx.getBank().isOpen()) {
            if (!ctx.getInventory().contains("Bucket") && ctx.getInventory().contains("Spade")) {
                ctx.getBank().withdrawAll("Bucket");
                Paint.status = "Withdrawing bucket";
                ctx.log("withdrew more buckets");
                ctx.getBank().close();
                Paint.status = "Closing bank";
                ctx.log("closed bank");
            } else if (!ctx.getInventory().contains("Bucket", "Spade")) {
                ctx.getBank().withdraw("Spade", 1);
                Paint.status = "Withdrawing spade";
                ctx.log("withdrew spade");
                ctx.getBank().withdrawAll("Bucket");
                Paint.status = "Withdrawing buckets";
                ctx.log("withdrew more buckets");
                ctx.getBank().close();
                ctx.log("closed bank");
            }
            if (!ctx.getBank().contains("Bucket") && !ctx.getInventory().contains("Bucket")) {
                ctx.log("no more buckets, stopping script");
                ctx.getBot().getScriptExecutor().stop();
            }
        }
    }

    private void dig() {
        final Area WORM_AREA = new Area(1834, 3791, 1848, 3812);
        final RS2Object WORM = ctx.getObjects().closest("Sandworm castings");

        if (WORM_AREA.contains(ctx.myPlayer())) {
            if (WORM != null) {
                WORM.interact("Dig");
                Paint.status = "Digging";
                Sleep.sleepUntil(() -> ctx.myPlayer().isAnimating(), 5000);
            }
        }
    }

    private void emptyBucket() {
        if (ctx.getInventory().contains("Bucket of sand")) {
            ctx.getInventory().interact("Empty", "Bucket of sand");
            ctx.log("emptied bucket");
        }
    }

    private void turnIn() {
        final NPC TYNAN = ctx.getNpcs().closest("Tynan");
        if (TYNAN != null) {
            ctx.getInventory().interact("Use", "Bucket of sandworms");
            if (ctx.getInventory().isItemSelected()) {
                if (TYNAN.isVisible()) {
                    TYNAN.interact("Use");
                } else ctx.getCamera().toEntity(TYNAN);
            }
            Sleep.sleepUntil(() -> ctx.getInventory().onlyContains("Spade"), 5000);
        }
    }
}

