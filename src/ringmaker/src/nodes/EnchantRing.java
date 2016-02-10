package ringmaker.src.nodes;

import omniapi.OmniScript;
import omniapi.api.nodes.AbstractNode;
import org.osbot.rs07.api.ui.Spells;
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
public class EnchantRing extends AbstractNode {

    public static String staffToUse;
    public static String gemToUse;
    public static String ring;

    public EnchantRing(OmniScript script) {
        super(script);
    }

    @Override
    public boolean canExecute() {
        return canEnchant();
    }

    private boolean canEnchant() {
        return script.getInventory().contains(ring) && script.getInventory().contains("Cosmic rune")
                && script.getEquipment().contains(staffToUse);

    }

    @Override
    public int execute() throws InterruptedException {
        if (script.getBank().isOpen()) {
            script.getBank().close();
        } else {
            if (CraftRing.craftDuelings) {
                final long ringAmnt = script.getInventory().getAmount("Ring of dueling");
                script.getMagic().castSpell(Spells.NormalSpells.LVL_2_ENCHANT);
                script.getInventory().getItem("Emerald ring").interact();
                //log("enchanting rings to dueling rings");
                new ConditionalSleep(1000) {
                    public boolean condition() throws InterruptedException {
                        return EnchantRing.this.script.getInventory().getAmount("Ring of dueling") > ringAmnt;
                    }
                }.sleep();
            } else if (CraftRing.craftRecoils) {
                final long ringAmnt = script.getInventory().getAmount("Ring of recoil");
                script.getMagic().castSpell(Spells.NormalSpells.LVL_1_ENCHANT);
                script.getInventory().getItem("Sapphire ring").interact();
                //log("enchanting rings to recoil rings");
                new ConditionalSleep(1000) {
                    public boolean condition() throws InterruptedException {
                        return EnchantRing.this.script.getInventory().getAmount("Ring of recoil") > ringAmnt;
                    }
                }.sleep();
            }
        }
        return 2500;
    }
}
