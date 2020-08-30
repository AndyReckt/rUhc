package com.thesevenq.uhc.scenario.type;

import com.thesevenq.uhc.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemStack;

public class LuckyLeavesScenario extends Scenario implements Listener {

	public LuckyLeavesScenario() {
		super("Lucky Leaves", Material.LEAVES, "There is a small chance of a golden apple", "to drop from trees.");
	}

	public static void handleDecay(Block block) {
        if(Math.random() * 100 <= 0.75) {
            block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.GOLDEN_APPLE));
        }
    }

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent event) {
    	Block block = event.getBlock();
    	
        if(Math.random() * 100 <= 0.75) {
            block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.GOLDEN_APPLE));
        }
    }
}
