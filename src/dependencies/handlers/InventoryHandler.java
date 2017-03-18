package dependencies.handlers;

import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.script.MethodProvider;

/**
 * Created by RAIJIN on 3/17/2017.
 */
public class InventoryHandler {

    private MethodProvider ctx;
    public String oldItem1;
    public String oldItem2;

    public InventoryHandler(MethodProvider ctx) {
        this.ctx = ctx;
    }

    public boolean swapItems(int slot1, final int slot2) throws InterruptedException {
        if (ctx.getInventory().isItemSelected()) {
            ctx.getInventory().deselectItem();
        }
        ctx.getMouse().continualClick(ctx.getInventory().getMouseDestination(slot1), new org.osbot.rs07.utility.Condition() {
            public boolean evaluate() {
                ctx.getMouse().move(ctx.getInventory().getMouseDestination(slot2), true);
                return ctx.getInventory().getMouseDestination(slot2).getBoundingBox()
                        .contains(ctx.getMouse().getPosition());
            }
        });
        return false;
    }

    public boolean itemOnItem(int slot1, int slot2) {
        if ((ctx.getInventory().interact(slot1, "Use")) &&
                (ctx.getInventory().isItemSelected())) {
            if (ctx.getInventory().interact(slot2, "Use")) {
                return true;
            }
        }

        return false;
    }

    public boolean itemOnItem(String item1, String item2) {
        if (ctx.getInventory().interact(item1, "Use") &&
                ctx.getInventory().isItemSelected()) {
            if (ctx.getInventory().interact(item2, "Use")) {
                return true;
            }
        }

        return false;
    }

    public boolean itemOnEntity(String item, Entity object) {
        if (ctx.getInventory().interact("Use", item) &&
                ctx.getInventory().isItemSelected()) {
            ctx.getCamera().toEntity(object);
            return object.interact("Use");
        }

        return false;
    }

    public boolean itemOnEntity(String item, String object) {
        Entity interact = ctx.getObjects().closest(object);
        if (ctx.getInventory().interact("Use", item) &&
                ctx.getInventory().isItemSelected()) {
            ctx.getCamera().toEntity(interact);
            return interact.interact("Use");
        }

        return false;
    }

}
