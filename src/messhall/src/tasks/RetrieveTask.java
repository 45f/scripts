package messhall.src.tasks;

import dependencies.tasks.Task;
import dependencies.util.CSleep;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;

/**
 * Created by RAIJIN on 3/13/2017.
 */
public class RetrieveTask extends Task {


    public RetrieveTask(MethodProvider ctx) {
        super(ctx);
    }


    @Override
    public boolean canExecute() {
        return ctx.getInventory().isEmpty();
    }

    @Override
    public void execute() throws InterruptedException {
        if (canGetBowl()) grabBowl();
        if (canGrabPotato()) grabPotato();
        if (canGrabMeat()) grabMeat();
    }

    private void grabBowl() {
        ctx.log("grabbing bowls");
        if (isCupboardScreenVisible()) {
            if (isEAVisible()) {
                ctx.getKeyboard().typeString("14");
                ctx.getWidgets().closeOpenInterface();
                CSleep.sleepUntil(() -> ctx.getInventory().contains("Bowl"), 5000);
            } else if (takeItem1()) {
                CSleep.sleepUntil(this::isEAVisible, 5000);
            }
        } else if (searchUtils()) {
            CSleep.sleepUntil(this::isCupboardScreenVisible, 5000);
        }
    }

    private void grabFlour() throws InterruptedException {
        ctx.log("grabbing flour");
        if (isCupboardScreenVisible()) {
            if (isEAVisible()) {
                ctx.getKeyboard().typeString("14");
                ctx.getWidgets().closeOpenInterface();
                CSleep.sleepUntil(() -> ctx.getInventory().contains("Servery flour"), 5000);
            } else if (takeX()) {
                CSleep.sleepUntil(this::isEAVisible, 5000);
            }
        } else if (searchFood()) {
            CSleep.sleepUntil(this::isCupboardScreenVisible, 5000);
        }
    }

    private void grabPie() throws InterruptedException {
        ctx.log("grabbing dishes");
        if (isCupboardScreenVisible()) {
            if (isEAVisible()) {
                ctx.getKeyboard().typeString("14");
                ctx.getWidgets().closeOpenInterface();
                CSleep.sleepUntil(() -> ctx.getInventory().contains("Servery dish"), 5000);
            } else if (takeX()) {
                CSleep.sleepUntil(this::isEAVisible, 5000);
            }
        } else if (searchUtils()) {
            CSleep.sleepUntil(this::isCupboardScreenVisible, 5000);
        }
    }

    private void grabMeat() throws InterruptedException {
        ctx.log("grabbing meat");
        if (canGrabMeat()) {
            if (isEAVisible()) {
                ctx.getKeyboard().typeString("14");
                ctx.getWidgets().closeOpenInterface();
                CSleep.sleepUntil(() -> ctx.getInventory().contains("Servery raw meat"), 5000);
            } else if (searchMeat()) {
                CSleep.sleepUntil(this::isEAVisible, 5000);
            }
        }
    }

    private void grabPotato() throws InterruptedException {
        ctx.log("grabbing potato");
        if (isCupboardScreenVisible()) {
            if (isEAVisible()) {
                ctx.getKeyboard().typeString("14");
                ctx.getWidgets().closeOpenInterface();
                CSleep.sleepUntil(() -> ctx.getInventory().contains("Servery potato"), 5000);
            } else if (takeItem2()) {
                CSleep.sleepUntil(this::isEAVisible, 5000);
            }
        } else if (searchFood()) {
            CSleep.sleepUntil(this::isCupboardScreenVisible, 5000);
        }
    }

    private boolean isEAVisible() {
        RS2Widget amountWidget = ctx.getWidgets().singleFilter(ctx.getWidgets().getAll(), widget -> widget.getMessage().contains("Enter amount"));
        return amountWidget != null && amountWidget.isVisible();
    }

    private boolean canGetBowl() {
        return ctx.getInventory().isEmpty();
    }

    private boolean canGetFlour() {
        return ctx.getInventory().contains("Bowl of water") && !ctx.getInventory().contains("Servery flour");
    }

    private boolean canGetPie() {
        return ctx.getInventory().contains("Servery pastry dough") && !ctx.getInventory().contains("Servery dish");
    }

    private boolean canGrabMeat() {
        return ctx.getInventory().contains("Servery pie shell") && !ctx.getInventory().contains("Servery raw meat");
    }

    private boolean canGrabPotato() {
        return ctx.getInventory().contains("Bowl of water") && !ctx.getInventory().contains("Servery potato");
    }

    private boolean searchUtils() {
        return ctx.getObjects().closest("Utensil cupboard").interact("Search");
    }

    private boolean searchFood() {
        return ctx.getObjects().closest("Food cupboard").interact("Search");
    }

    private boolean searchMeat() {
        return ctx.getObjects().closest("Meat Table").interact("Take-X");
    }

    private boolean isCupboardScreenVisible() {
        return ctx.getWidgets().get(242, 1, 0) != null;
    }

    private boolean takeItem1() {
        return ctx.getWidgets().get(242, 3, 1).interact("Take-X");
    }

    private boolean takeX() {
        return ctx.getWidgets().get(242, 3, 0).interact("Take-X");
    }

    private boolean takeItem2() {
        return ctx.getWidgets().get(242, 3, 2).interact("Take-X");
    }
}
