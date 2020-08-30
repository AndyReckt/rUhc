package com.thesevenq.uhc.scenario.type;

import com.thesevenq.uhc.scenario.Scenario;
import com.thesevenq.uhc.utilties.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LimitationsScenario extends Scenario implements Listener {

	private static Map<UUID, Integer> diamondsMined = new HashMap<>();
	private static Map<UUID, Integer> goldMined = new HashMap<>();
	private static Map<UUID, Integer> ironMined = new HashMap<>();
	
	public LimitationsScenario() {
		super("Limitations", Material.LEASH, "Max of 16 diamonds can be mined", "Max of 32 gold can be mined", "Max of 64 iron can be mined");
	}

	public static void handleJoin(Player player) {
		if(!diamondsMined.containsKey(player.getUniqueId())) {
			diamondsMined.put(player.getUniqueId(), 0);
			goldMined.put(player.getUniqueId(), 0);
			ironMined.put(player.getUniqueId(), 0);
		}
	}

	public static void handleBreak(Player player, Block block, BlockBreakEvent event) {
		if(!diamondsMined.containsKey(player.getUniqueId())) {
			diamondsMined.put(player.getUniqueId(), 0);
		}

		if(!goldMined.containsKey(player.getUniqueId())) {
			goldMined.put(player.getUniqueId(), 0);
		}

		if(!ironMined.containsKey(player.getUniqueId())) {
			ironMined.put(player.getUniqueId(), 0);
		}
		
		if(block.getType() == Material.DIAMOND_ORE) {
			diamondsMined.put(player.getUniqueId(), diamondsMined.get(player.getUniqueId()) + 1);

			if(diamondsMined.get(player.getUniqueId()) > 16) {
				event.setCancelled(true);

				block.setType(Material.AIR);

				player.sendMessage(Color.translate("&cYou can't mine any more diamonds."));
			}
		} else if(block.getType() == Material.GOLD_ORE) {
			goldMined.put(player.getUniqueId(), goldMined.get(player.getUniqueId()) + 1);

			if(goldMined.get(player.getUniqueId()) > 32) {
				event.setCancelled(true);

				block.setType(Material.AIR);
				player.sendMessage(Color.translate("&cYou can't mine any more gold."));
			}
		} else if(block.getType() == Material.IRON_ORE) {
			ironMined.put(player.getUniqueId(), ironMined.get(player.getUniqueId()) + 1);

			if(ironMined.get(player.getUniqueId()) > 64) {
				event.setCancelled(true);

				block.setType(Material.AIR);
				player.sendMessage(Color.translate("&cYou can't mine any more iron."));
			}
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if(!diamondsMined.containsKey(event.getPlayer().getUniqueId())) {
			diamondsMined.put(event.getPlayer().getUniqueId(), 0);
		}
		
		if(!goldMined.containsKey(event.getPlayer().getUniqueId())) {
			goldMined.put(event.getPlayer().getUniqueId(), 0);
		}
		
		if(!ironMined.containsKey(event.getPlayer().getUniqueId())) {
			ironMined.put(event.getPlayer().getUniqueId(), 0);
		}

		Player player = event.getPlayer();
		Block block = event.getBlock();

		if(block.getType() == Material.DIAMOND_ORE) {
			diamondsMined.put(player.getUniqueId(), diamondsMined.get(player.getUniqueId()) + 1);
			
			if(diamondsMined.get(player.getUniqueId()) > 16) {
				event.setCancelled(true);
				
				block.setType(Material.AIR);
				
				player.sendMessage(Color.translate("&cYou can't mine any more diamonds."));
			}
		} else if(block.getType() == Material.GOLD_ORE) {
			goldMined.put(player.getUniqueId(), goldMined.get(player.getUniqueId()) + 1);
			
			if(goldMined.get(player.getUniqueId()) > 32) {
				event.setCancelled(true);
				
				block.setType(Material.AIR);
				player.sendMessage(Color.translate("&cYou can't mine any more gold."));
			}
		} else if(block.getType() == Material.IRON_ORE) {
			ironMined.put(player.getUniqueId(), ironMined.get(player.getUniqueId()) + 1);
			
			if(ironMined.get(player.getUniqueId()) > 64) {
				event.setCancelled(true);
				
				block.setType(Material.AIR);
				player.sendMessage(Color.translate("&cYou can't mine any more iron."));
			}
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if(!diamondsMined.containsKey(event.getPlayer().getUniqueId())) {
			diamondsMined.put(event.getPlayer().getUniqueId(), 0);
			goldMined.put(event.getPlayer().getUniqueId(), 0);
			ironMined.put(event.getPlayer().getUniqueId(), 0);
		}
	}

	public static void disable() {
		diamondsMined.clear();
		goldMined.clear();
		ironMined.clear();
	}
}
