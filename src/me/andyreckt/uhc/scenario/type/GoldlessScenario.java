package me.andyreckt.uhc.scenario.type;

import me.andyreckt.uhc.scenario.Scenario;
import me.andyreckt.uhc.utilties.UHCUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GoldlessScenario extends Scenario implements Listener {

	public GoldlessScenario() {
		super("Goldless", Material.GOLD_INGOT, "You can't mine gold.", "Players drop 8 gold on death.");
	}

	public static void handleBreak(Block block, BlockBreakEvent event) {
		if(block.getType() != Material.GOLD_ORE) return;

		event.setCancelled(true);

		block.setType(Material.AIR);
		block.getState().update();

		block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(4);
	}

	public static void handleDeath(List<ItemStack> drops) {
		drops.add(new ItemStack(Material.GOLD_INGOT, 8));
		drops.add(UHCUtils.getGoldenHead());
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();

		if(block.getType() != Material.GOLD_ORE) return;
		
		event.setCancelled(true);

		block.setType(Material.AIR);
		block.getState().update();
		
		block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(4);	
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		event.getDrops().add(new ItemStack(Material.GOLD_INGOT, 8));
		
		event.getDrops().add(UHCUtils.getGoldenHead());
	}

}
