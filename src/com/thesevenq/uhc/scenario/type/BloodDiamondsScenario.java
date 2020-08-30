package com.thesevenq.uhc.scenario.type;

import com.thesevenq.uhc.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import com.thesevenq.uhc.scenario.Scenario;

public class BloodDiamondsScenario extends Scenario implements Listener {

	public BloodDiamondsScenario() {
		super("Blood Diamonds", Material.DIAMOND_ORE, "Take 1/2 a heart for each diamond mined.");
	}

	public static void handleBreak(Player player, Block block) {
		if(block.getType() == Material.DIAMOND_ORE) {
			player.damage(1);
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if(event.getBlock().getType() == Material.DIAMOND_ORE) {
			event.getPlayer().damage(1);
		}
	}
}