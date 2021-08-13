package me.andyreckt.uhc.scenario.type;

import me.andyreckt.uhc.scenario.Scenario;
import me.andyreckt.uhc.utilties.UHCUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class TripleExpScenario extends Scenario implements Listener {

	public TripleExpScenario() {
		super("Triple Exp", Material.EXP_BOTTLE, "When you mine ores you recevive triple exp.");
	}

	public static void handleBreak(Player player, Block block) {
		if(UHCUtils.isPlayerInSpecMode(player)) {
			return;
		}

		switch(block.getType()) {
			case DIAMOND_ORE:
				block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(14);
				break;
			case GOLD_ORE:
				block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(14);
				break;
			case IRON_ORE:
				block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(14);
				break;
			case COAL_ORE:
				block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(14);
				break;
			default:
				break;

		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if(event.isCancelled()) return;
		
		Player player = event.getPlayer();
				
		if(UHCUtils.isPlayerInSpecMode(player)) return;
		
		Block block = event.getBlock();
		
		switch(block.getType()) {
		case DIAMOND_ORE:
			block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(14);
			break;
		case GOLD_ORE:
			block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(14);
			break;
		case IRON_ORE:
			block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(14);
			break;
		case COAL_ORE:
			block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(14);
			break;	
		default:
			break;

		}
	}
}
