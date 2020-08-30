package com.thesevenq.uhc.scenario.type;

import com.thesevenq.uhc.managers.ScenarioManager;
import com.thesevenq.uhc.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.thesevenq.uhc.scenario.Scenario;
import com.thesevenq.uhc.managers.ScenarioManager;

/**
 * Created by Marko on 05.04.2018.
 */
public class InfiniteEnchanterScenario extends Scenario {

    public InfiniteEnchanterScenario() {
        super("Infinite Enchanter", Material.ENCHANTMENT_TABLE, "You can infinitely enchant, no limitations.");
    }

    public static void start(Player player) {
        if(ScenarioManager.getByName("Infinite Enchanter").isEnabled()) {
            player.setExp(0);
            player.setLevel(30000);
            player.getInventory().addItem(new ItemStack(Material.ENCHANTMENT_TABLE, 64));
            player.getInventory().addItem(new ItemStack(Material.ANVIL, 64));
            player.getInventory().addItem(new ItemStack(Material.BOOKSHELF, 64));
            player.getInventory().addItem(new ItemStack(Material.BOOKSHELF, 64));
        }
    }
}
