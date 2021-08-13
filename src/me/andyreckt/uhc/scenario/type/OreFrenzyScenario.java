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
import org.bukkit.inventory.ItemStack;

public class OreFrenzyScenario extends Scenario implements Listener {

	public OreFrenzyScenario() {
		super("Ore Frenzy", Material.EMERALD, "When you mine lapis ore it drops a health splash potion.", "When you mine emerald ore it drops 32 arrows.", "When you mine redstone ore it drops an unenchanted book.", "When you mine diamond ore it drops a diamond and 4 bottles of exp.", "When you mine quartz ore it drops a block of TNT.");
	}

	public static void handleBreak(Player player, Block block, BlockBreakEvent event) {
		if(UHCUtils.isPlayerInSpecMode(player)) return;

		if(block.getType() == Material.LAPIS_ORE) {
			event.setCancelled(true);

			block.setType(Material.AIR);
			block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.POTION, 1, (short) 16453));

			block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(7);
		} else if(block.getType() == Material.EMERALD_ORE) {
			event.setCancelled(true);

			block.setType(Material.AIR);
			block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.ARROW, 32));

			block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(7);
		} else if(block.getType() == Material.REDSTONE_ORE || block.getType() == Material.GLOWING_REDSTONE_ORE) {
			event.setCancelled(true);

			block.setType(Material.AIR);
			block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.BOOK, 1));

			block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(7);
		} else if(block.getType() == Material.DIAMOND_ORE) {
			block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.EXP_BOTTLE, 4));
		} else if(block.getType() == Material.QUARTZ_ORE) {
			event.setCancelled(true);

			block.setType(Material.AIR);
			block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.TNT));

			block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(7);
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		Player player = event.getPlayer();
				
		if(UHCUtils.isPlayerInSpecMode(player)) return;

		if(block.getType() == Material.LAPIS_ORE) {
			event.setCancelled(true);
			
			block.setType(Material.AIR);
			block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.POTION, 1, (short) 16453));

			block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(7);
		} else if(block.getType() == Material.EMERALD_ORE) {
			event.setCancelled(true);
			
			block.setType(Material.AIR);
			block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.ARROW, 32));
			
			block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(7);
		} else if(block.getType() == Material.REDSTONE_ORE || block.getType() == Material.GLOWING_REDSTONE_ORE) {
			event.setCancelled(true);
			
			block.setType(Material.AIR);
			block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.BOOK, 1));
			
			block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(7);
		} else if(block.getType() == Material.DIAMOND_ORE) {
			block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.EXP_BOTTLE, 4));
		} else if(block.getType() == Material.QUARTZ_ORE) {
			event.setCancelled(true);
			
			block.setType(Material.AIR);
			block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.TNT));
			
			block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(7);
		}
	}

}
