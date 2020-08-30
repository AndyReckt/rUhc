package com.thesevenq.uhc.scenario.type;

import com.thesevenq.uhc.managers.ScenarioManager;
import com.thesevenq.uhc.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BareBonesScenario extends Scenario implements Listener {

	public BareBonesScenario() {
		super("BareBones", Material.BONE, "Enchantment tables/Anvils can't be crafted or used,", "Golden apples can't be crafted either.", "The Nether is disabled.", "Players drop 1 Diamond, 2 Golden apples.", "32 Arrows and 2 String on death.");
	}

	public static void handleDeath(List<ItemStack> drops) {
		if(!ScenarioManager.getByName("Time Bomb").isEnabled()) {
			drops.add(new ItemStack(Material.GOLDEN_APPLE, 2));
			drops.add(new ItemStack(Material.DIAMOND));
			drops.add(new ItemStack(Material.ARROW, 32));
			drops.add(new ItemStack(Material.STRING, 2));
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if(!ScenarioManager.getByName("Time Bomb").isEnabled()) {
			event.getDrops().add(new ItemStack(Material.GOLDEN_APPLE, 2));
			event.getDrops().add(new ItemStack(Material.DIAMOND));
			event.getDrops().add(new ItemStack(Material.ARROW, 32));
			event.getDrops().add(new ItemStack(Material.STRING, 2));
		}
	}
}
