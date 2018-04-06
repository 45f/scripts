package script.core.piscarilius;

import org.osbot.rs07.api.Chatbox;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.event.InteractionEvent;
import org.osbot.rs07.event.WalkingEvent;
import org.osbot.rs07.script.MethodProvider;
import script.HordeZeah;
import script.Paint;
import script.Task;
import script.util.Sleep;

/**
 * <p>
 * </p>
 *
 * @author Capitals <onlytainted@gmail.com>
 */
public class RepairCranes extends Task {

    private final Area CRANE_AREA = new Area(1834, 3761, 1830, 3748);

    private String[] MATERIALS = {"Mithril nails", "Plank", "Hammer"};

    public RepairCranes(MethodProvider ctx) {
        super(ctx);
        setDebug(-1);
    }

    @Override
    public boolean verify() {
        return canRepair();
    }

    @Override
    public void process() throws InterruptedException {
        if (hasMaterials()) {
            if (!CRANE_AREA.contains(ctx.myPlayer())) {
                walkToCranes();
            } else if (CRANE_AREA.contains(ctx.myPlayer())) {
                repair();
            }
        }
        if (ctx.getInventory().getAmount("Plank") <= 2) {
            walkToBank();
            bank();
        } else if (ctx.getInventory().isEmpty()) {
            bank();
        }
    }

    @Override
    public String status() {
        return "REPAIR";
    }

    @Override
    public int getDebug() {
        return debug;
    }

    @Override
    public void setDebug(int debug) {
        this.debug = debug;
    }

    private boolean hasMaterials() {
        return ctx.getInventory().contains(MATERIALS);
    }

    private boolean canRepair() {
        return hasMaterials();
    }

    private void bank() throws InterruptedException {
        if (Banks.PISCARILIUS_HOUSE.contains(ctx.myPosition())) {
            if (!ctx.getBank().isOpen()) {
                ctx.getBank().open();
                Sleep.sleepUntil(() -> ctx.getBank().isOpen(), 5000);
            }
            if (ctx.getInventory().getAmount("Plank") <= 2 && ctx.getInventory().contains("Mithril nails", "Hammer")) {
                ctx.getBank().withdrawAll("Plank");
                Paint.status = "Withdrawing planks";
                ctx.log("withdrew more planks");
                ctx.getBank().close();
                Paint.status = "Closing bank";
                ctx.log("closed bank");
            } else {
                Paint.status = "Withdrawing materials";
                ctx.getBank().withdrawAll("Mithril nails");
                ctx.getBank().withdraw("Hammer", 1);
                ctx.getBank().withdrawAll("Plank");
                ctx.log("withdrew all needed materials");
                ctx.getBank().close();
            }
            if (ctx.getInventory().contains("Mithril nails", "Hammer") && !ctx.getInventory().contains("Plank") && !ctx.getBank().contains("Plank")) {
                ctx.getBot().getScriptExecutor().stop();
                ctx.log("out of planks - stopping script");
            }
        }
    }

    private void walkToBank() {
        final Position BANK_SPOT = new Position(1804, 3790, 0);
        WalkingEvent TO_BANK = new WalkingEvent(BANK_SPOT);

        if (ctx.getInventory().getAmount("Plank") <= 2) {
            HordeZeah.status = "Retrieving more materials";
            Paint.status = "Walking to bank";
            ctx.execute(TO_BANK);
            setDebug(1);
            ctx.log("need more planks - walking to the bank");
            TO_BANK.setMinDistanceThreshold(5);
        }
    }

    private void walkToCranes() {
        final Position CRANE_SPOT = new Position(1832, 3756, 0);
        // final RS2Object CRANE = ctx.getObjects().closest(rs2Object -> rs2Object != null && rs2Object.hasAction("Repair"));

        if (ctx.getInventory().contains(MATERIALS)) {
            HordeZeah.status = "Walking to crane location";
            ctx.getWalking().webWalk(CRANE_SPOT);

        }
    }

    private void repair() {
        final Area CRANE_AREA = new Area(1830, 3760, 1834, 3752);
        final RS2Object CRANE = ctx.getObjects().closest(rs2Object -> rs2Object != null && rs2Object.hasAction("Repair"));
        final boolean MESSAGE = ctx.getChatbox().getMessages(Chatbox.MessageType.GAME).contains("You successfully repair");

        if (CRANE_AREA.contains(ctx.myPlayer())) {
           /* if (CRANE.interact("Repair")) {
                Paint.status = "Interacting with crane";
                ctx.log("interacting with crane 2");
                Sleep.sleepUntil(() -> MESSAGE, 5000);
            }*/
            InteractionEvent e = new InteractionEvent(CRANE, "Repair");
            e.setAsync();
            e.setOperateCamera(true);
            Paint.status = "Interacting with crane";
            setDebug(3);
            ctx.execute(e);
            ctx.log("interacting with crane");
            Sleep.sleepUntil(() -> ctx.myPlayer().getInteracting() == null, 8000);
            Sleep.sleepUntil(() -> MESSAGE, 8000);
        }
    }
}
