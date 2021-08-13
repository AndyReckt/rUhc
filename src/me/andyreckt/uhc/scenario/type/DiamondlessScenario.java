package me.andyreckt.uhc.scenario.type;

import me.andyreckt.uhc.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DiamondlessScenario extends Scenario implements Listener {

	public DiamondlessScenario() {
		super("Diamondless", Material.DIAMOND, "You can't mine diamonds.", "Players drop 1 diamond on death.");
	}

	public static void handleBreak(Block block, BlockBreakEvent event) {
		if(block.getType() == Material.DIAMOND_ORE) {
			event.setCancelled(true);

			block.setType(Material.AIR);
			block.getState().update();
			block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(5);
		}
	}

	public static void handleDeath(List<ItemStack> drops) {
		drops.add(new ItemStack(Material.DIAMOND, 1));
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();

		if(block.getType() == Material.DIAMOND_ORE) {
			event.setCancelled(true);

			block.setType(Material.AIR);
			block.getState().update();
			block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(5);
		}

	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		event.getDrops().add(new ItemStack(Material.DIAMOND, 1));
	}

}
