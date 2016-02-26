package ringmaker.src.nodes;

import omniapi.OmniScript;
import omniapi.api.nodes.AbstractNode;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.utility.ConditionalSleep;


/**
 * Copyright (c) 2015  Stress
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * Created by Stress on 1/30/2016.
 * This file is part of ringmaker.src.nodes.
 * Created with IntelliJ IDEA.
 *
 * @info
 */
public class CraftRing extends AbstractNode {

    RS2Widget smeltSelectWidget;
    public static boolean craftRecoils = false;
    public static boolean craftDuelings = false;
    public static String gemToUse;

    public CraftRing(OmniScript script) {
        super(script);
    }

    @Override
    public boolean canExecute() {
        return canCraft();
    }

    private boolean canCraft() {
        return script.getInventory().contains("Ring mould") && script.getInventory().contains("Gold bar") &&
                script.getInventory().contains(gemToUse);
    }

    @Override
    public int execute() throws InterruptedException {
        Position spot = new Position(3109, 3499, 0);
        script.getWalking().walk(spot);
        craft();
        getMouse().moveRandomly();
        return 3000;
    }

    private void craft() throws InterruptedException {
        final RS2Widget amountWidget = getEnterAmountWidget();
        if (craftRecoils) {
            smeltSelectWidget = getSapphireWidget();
        } else smeltSelectWidget = getEmeraldWidget();

        if (amountWidget != null && amountWidget.isVisible()) {
            enterRingAmount();
        } else if (smeltSelectWidget != null && smeltSelectWidget.isVisible()) {
            selectMakeXOption();
        } else if (this.isGoldBarSelected()) {
            useFurnace();
        } else {
            useGoldBar();
        }
    }

    private RS2Widget getSapphireWidget() {
        return getWidgets().get(446, 8);
    }

    private RS2Widget getEmeraldWidget() {
        return getWidgets().get(446, 9);
    }

    private RS2Widget getEnterAmountWidget() {
        return getWidgets().get(162, 33);
    }

    public RS2Widget getSmeltSelectWidget() {
        return smeltSelectWidget;
    }

    private void enterRingAmount() {
        if (script.getKeyboard().typeString("13")) {
            new ConditionalSleep(2000) {
                public boolean condition() throws InterruptedException {
                    return !CraftRing.this.script.getInventory().contains("Gold bar");
                }
            }.sleep();
        }
    }

    private void selectMakeXOption() {
        if (getSmeltSelectWidget().interact("Make-X")) {
            new ConditionalSleep(2000) {
                public boolean condition() throws InterruptedException {
                    return script.getDialogues().inDialogue();
                }
            }.sleep();
        }
    }


    private boolean isGoldBarSelected() {
        final String selectedItemName = getInventory().getSelectedItemName();
        return selectedItemName != null && selectedItemName.equals("Gold bar");
    }


    private void useFurnace() {
        RS2Object FURNACE = getObjects().closest("Furnace");
        if (FURNACE != null && FURNACE.interact("Use")) {
            new ConditionalSleep(2500) {
                public boolean condition() throws InterruptedException {
                    return CraftRing.this.getSmeltSelectWidget() != null;
                }
            }.sleep();
        }
    }


    private void useGoldBar() {
        if (script.getInventory().interact("Use", "Gold bar")) {
            new ConditionalSleep(2500) {
                public boolean condition() throws InterruptedException {
                    return CraftRing.this.isGoldBarSelected();
                }
            }.sleep();
        }
    }

}