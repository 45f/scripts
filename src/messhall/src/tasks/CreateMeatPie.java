package messhall.src.tasks;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;
import resources.tasks.AbstractTask;
import resources.util.CSleep;

public class CreateMeatPie extends AbstractTask {

    public CreateMeatPie(Script ctx) {
        super(ctx);
    }

    @Override
    public boolean canExecute() {
        return true;
    }

    @Override
    public void execute() throws InterruptedException {
        if (canGetBowl()) {
            grabBowl();
        } else if (canFill()) {
            fillBowls();
        } else if (canGetFlour()) {
            grabFlour();
        } else if (canMix()) {
            mixItems();
        }
    }

    private void grabBowl() {
        if (isCupboardScreenVisible()) {
            if (isEAVisible()) {
                ctx.getKeyboard().typeString("14");
            } else if (takeXBowl()) {
                CSleep.sleepUntil(this::isEAVisible, 5000);
            }
        } else if (searchUtils()) {
            CSleep.sleepUntil(this::isCupboardScreenVisible, 5000);
        }
    }

    private void fillBowls() {
        final long amt = ctx.getInventory().getAmount("Bowl");
        RS2Object sink = ctx.getObjects().closest("Sink");
        if (ctx.getInventory().contains("Bowl")) {
            ctx.getInventory().interact("Use", "Bowl");
            sink.interact("Use");
            new ConditionalSleep(12000) {
                @Override
                public boolean condition() throws InterruptedException {
                    return ctx.getInventory().getAmount("Bowl of water") > amt;
                }
            }.sleep();
        }
    }

    private void grabFlour() {
        if (isCupboardScreenVisible()) {
            if (isEAVisible()) {
                ctx.getKeyboard().typeString("14");
            } else if (takeXFlour()) {
                CSleep.sleepUntil(this::isEAVisible, 5000);
            }
        } else if (searchFood()) {
            CSleep.sleepUntil(this::isCupboardScreenVisible, 5000);
        }
    }

    private void mixItems() {
        final long amt = ctx.getInventory().getAmount("Bowl of water");
        if (ctx.getInventory().contains("Bowl of water") && ctx.getInventory().contains("Servery flour")) {
            ctx.getInventory().interact("Use", "Bowl of water");
            ctx.getInventory().interact("Use", "Servery flour");
            ctx.logger.info("Mixing items");
        }
        if (new ConditionalSleep(1200, 500) {
            @Override
            public boolean condition() throws InterruptedException {
                final RS2Widget dough = ctx.getWidgets().get(219, 0, 1);
                return dough != null && dough.isVisible() && dough.hover() && ctx.getMouse().click(false);
            }
        }.sleep()) ;
        new ConditionalSleep(30_000) {
            @Override
            public boolean condition() throws InterruptedException {
                return ctx.getInventory().getAmount("Servery pastry dough") > amt;
            }
        }.sleep();
        ctx.logger.info("Sleeping");
    }


    private boolean canGetBowl() {
        return ctx.getInventory().isEmpty();
    }

    private boolean searchUtils() {
        return ctx.getObjects().closest("Utensil cupboard").interact("Search");
    }

    private boolean searchFood() {
        return ctx.getObjects().closest("Food cupboard").interact("Search");
    }

    private boolean isCupboardScreenVisible() {
        return ctx.getWidgets().get(242, 1, 0) != null;
    }

    private boolean isEAVisible() {
        RS2Widget amountWidget = ctx.getWidgets().singleFilter(ctx.getWidgets().getAll(), widget -> widget.getMessage().contains("Enter amount"));
        return amountWidget != null && amountWidget.isVisible();
    }

    private boolean takeXBowl() {
        return ctx.getWidgets().get(242, 3, 1).interact("Take-X");
    }

    private boolean takeXFlour() {
        return ctx.getWidgets().get(242, 3, 0).interact("Take-X");
    }

    private boolean canFill() {
        return ctx.getInventory().contains("Bowl");
    }

    private boolean canGetFlour() {
        return ctx.getInventory().contains("Bowl of water");
    }

    private boolean canMix() {
        return ctx.getInventory().contains("Bowl of water") && ctx.getInventory().contains("Servery flour");
    }

}
