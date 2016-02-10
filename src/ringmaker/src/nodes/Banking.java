package ringmaker.src.nodes;

import omniapi.OmniScript;
import omniapi.api.nodes.AbstractNode;

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
public class Banking extends AbstractNode {

    public static String gemToUse;
    public static String ring;
    public static String staffToUse;

    private String RING_MOULD = "Ring mould";
    private String GOLD_BAR = "Gold bar";
    private String COSMIC_RUNE = "Cosmic rune";
    // Position p = new Position(3096, 3494, 0);

    public Banking(OmniScript script) {
        super(script);
    }

    @Override
    public boolean canExecute() {
        return !canCraft() && !canEnchant();
    }

    @Override
    public int execute() throws InterruptedException {
        if (!getBank().isOpen()) {
            getBank().open();
        } else if (containsCraftItems()) {
            getBank().depositAllExcept(RING_MOULD);
            withdrawCraftItems();
            debug("withdrew craft items");
        } else if (containsEnchantmentItems()) {
            getBank().depositAllExcept(COSMIC_RUNE);
            withdrawEnchantmentItems();
            debug("withdrew enchantment items");
        }
        return 2000;
    }

    /* Method to check if the bank contains the needed items for enchantment */
    private boolean containsEnchantmentItems() {
        return (getInventory().contains(COSMIC_RUNE) || getBank().contains(COSMIC_RUNE))
                && (getInventory().contains(ring) || getBank().contains(ring));
    }

    /* Withdraw needed items for enchantment */
    private void withdrawEnchantmentItems() {
        if (!getInventory().contains(COSMIC_RUNE) && getBank().contains(COSMIC_RUNE)
                && !getInventory().contains(ring) && getBank().contains(ring)) {
            getBank().withdrawAll(COSMIC_RUNE);
            debug("withdrew cosmic runes");
        }
        if (!script.getInventory().contains(ring) && script.getBank().contains(ring)) {
            script.getBank().withdraw(ring, 25);
            debug("withdrew " + ring + "s");
        }

    }

    /* Method to check if the bank contains needed to craft rings */
    private boolean containsCraftItems() {
        return (getInventory().contains(GOLD_BAR) || getBank().contains(GOLD_BAR))
                && (getInventory().contains(RING_MOULD) || getBank().contains(RING_MOULD))
                && (getInventory().contains(gemToUse) || getBank().contains(gemToUse));
    }

    /* Withdraw the items needed for crafting */
    private void withdrawCraftItems() {
        if (!script.getInventory().contains(RING_MOULD) && script.getBank().contains(RING_MOULD)) {
            script.getBank().withdraw(RING_MOULD, 1);
        }
        if (!script.getInventory().contains(GOLD_BAR) && script.getBank().contains(GOLD_BAR)) {
            script.getBank().withdraw(GOLD_BAR, 13);
        }
        if (!script.getInventory().contains(gemToUse) && script.getBank().contains(gemToUse)) {
            script.getBank().withdraw(gemToUse, 13);
        }
    }


    private boolean canEnchant() {
        return script.getInventory().contains(ring) && script.getInventory().contains(COSMIC_RUNE) &&
                script.getEquipment().contains(staffToUse);

    }

    private boolean canCraft() {
        return script.getInventory().contains(RING_MOULD) && script.getInventory().contains(GOLD_BAR) &&
                script.getInventory().contains(gemToUse);
    }

}
