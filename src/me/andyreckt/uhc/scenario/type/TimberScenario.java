package me.andyreckt.uhc.scenario.type;

import me.andyreckt.uhc.managers.GameManager;
import me.andyreckt.uhc.scenario.Scenario;
import me.andyreckt.uhc.utilties.UHCUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.player.state.GameState;

public class TimberScenario extends Scenario implements Listener {

	public TimberScenario() {
		super("Timber", Material.STONE_AXE, "When you break one log", "it automatically breaks whole tree.");
	}

	public static void handleBreak(Player player, Block block) {
		if(UHCUtils.isPlayerInSpecMode(player)) return;
		if((block.getType() != Material.LOG) && (block.getType() != Material.LOG_2)) return;
		if(player.getGameMode() == GameMode.CREATIVE) return;
		if(!GameManager.getGameState().equals(GameState.PLAYING)) return;
		if(UHC.getInstance().getPracticeManager().isOpen()) return;
		if(block.getLocation().getWorld().getName().equalsIgnoreCase("world")) return;

		Block up = block.getRelative(BlockFace.UP);
		Block down = block.getRelative(BlockFace.DOWN);

		block.setType(Material.AIR);

		while((up.getType() == Material.LOG) || (up.getType() == Material.LOG_2)) {
			if(player.getInventory().firstEmpty() == -1) {
				up.breakNaturally();
			} else {
				if(up.getType() == Material.LOG) {
					up.getDrops().clear();

					player.getInventory().addItem(new ItemStack(Material.LOG));
					up.setType(Material.AIR);
				} else if(up.getType() == Material.LOG_2) {
					up.getDrops().clear();

					player.getInventory().addItem(new ItemStack(Material.LOG));
					up.setType(Material.AIR);
				}
			}

			up = up.getRelative(BlockFace.UP);
		}

		while((down.getType() == Material.LOG) || (down.getType() == Material.LOG_2)) {
			if(player.getInventory().firstEmpty() == -1) {
				down.breakNaturally();
			} else {
				if(down.getType() == Material.LOG) {
					down.getDrops().clear();
					player.getInventory().addItem(new ItemStack(Material.LOG));
					down.setType(Material.AIR);
				} else if(down.getType() == Material.LOG_2) {
					down.getDrops().clear();
					player.getInventory().addItem(new ItemStack(Material.LOG));
					down.setType(Material.AIR);
				}
			}

			down = down.getRelative(BlockFace.DOWN);
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if(event.isCancelled()) return;

		Block block = event.getBlock();
		Player player = event.getPlayer();

		if(UHCUtils.isPlayerInSpecMode(player)) return;
		
		if((block.getType() != Material.LOG) && (block.getType() != Material.LOG_2)) return;
		if(player.getGameMode() == GameMode.CREATIVE) return;
		
		if(!GameManager.getGameState().equals(GameState.PLAYING)) return;
		if(plugin.getPracticeManager().isOpen()) return;
		
		if(block.getLocation().getWorld().getName().equalsIgnoreCase("world")) return;

		Block up = block.getRelative(BlockFace.UP);
		Block down = block.getRelative(BlockFace.DOWN);

		block.setType(Material.AIR);

		while((up.getType() == Material.LOG) || (up.getType() == Material.LOG_2)) {
			if(player.getInventory().firstEmpty() == -1) {
				up.breakNaturally();
			} else {
				if(up.getType() == Material.LOG) {
					up.getDrops().clear();

					player.getInventory().addItem(new ItemStack(Material.LOG));
					up.setType(Material.AIR);
				} else if(up.getType() == Material.LOG_2) {
					up.getDrops().clear();

					player.getInventory().addItem(new ItemStack(Material.LOG));
					up.setType(Material.AIR);
				}
			}
			
			up = up.getRelative(BlockFace.UP);
		}

		while((down.getType() == Material.LOG) || (down.getType() == Material.LOG_2)) {
			if(player.getInventory().firstEmpty() == -1) { 
				down.breakNaturally();
			} else {
				if(down.getType() == Material.LOG) {
					down.getDrops().clear();
					player.getInventory().addItem(new ItemStack(Material.LOG));
					down.setType(Material.AIR);
				} else if(down.getType() == Material.LOG_2) {
					down.getDrops().clear();
					player.getInventory().addItem(new ItemStack(Material.LOG));
					down.setType(Material.AIR);
				}
			}
			
			down = down.getRelative(BlockFace.DOWN);
		}
	}
}
