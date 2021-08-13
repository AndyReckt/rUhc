package me.andyreckt.uhc.listeners;

import me.andyreckt.uhc.utilties.BaseListener;
import me.andyreckt.uhc.utilties.Color;
import me.andyreckt.uhc.utilties.spectator.OptionType;
import me.andyreckt.uhc.player.UHCData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.inventory.Inventory;
import me.andyreckt.uhc.UHC;

public class VanishListener extends BaseListener implements Listener {
	
	@EventHandler(ignoreCancelled = true)
	public void onVehicleDestroy(VehicleDestroyEvent event) {
		OptionType interact = OptionType.INTERACT;
		
		if((event.getAttacker() instanceof Player) && (isVanished((Player) event.getAttacker())) && (!interact.getPlayers().contains(((Player) event.getAttacker()).getUniqueId()))) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler(ignoreCancelled=true)
	public void onVehicleEntityCollision(VehicleEntityCollisionEvent event) {		
		if((event.getEntity() instanceof Player) && (isVanished((Player) event.getEntity()))) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityTarget(EntityTargetEvent event) {
		if((event.getTarget() instanceof Player) && (isVanished((Player) event.getTarget()))) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		OptionType option = OptionType.PLACE;
		
		if(isVanished(player)) {
			if(!option.getPlayers().contains(player.getUniqueId())) {
				event.setCancelled(true);
				
				player.sendMessage(Color.translate("&eYou can't do that while you are hidden."));
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		OptionType option = OptionType.BREAK;
		
		if(isVanished(player)) {
			if(!option.getPlayers().contains(player.getUniqueId())) {
				event.setCancelled(true);
				
				player.sendMessage(Color.translate("&eYou can't do that while you are hidden."));
			}
		}
	}
	
	@EventHandler
	public void onPlayerItemPickup(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		OptionType option = OptionType.PICKUP;
		
        UHCData uhcData = UHCData.getByName(player.getName());

        if(uhcData.isAlive()) return;

		if(isVanished(player)) {
			if(!option.getPlayers().contains(player.getUniqueId())) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		OptionType chestOption = OptionType.CHEST;
		OptionType interact = OptionType.INTERACT;
		
		if(!isVanished(player)) return;
		if(chestOption.getPlayers().contains(player.getUniqueId())) return;
		
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			Block block = event.getClickedBlock();

			if(!block.getType().equals(Material.CHEST)) return;
			if(block.getType().equals(Material.TRAPPED_CHEST)) return;
			if(player.isSneaking()) return;
			
			event.setCancelled(true);

			plugin.getVanishManager().getSilentView().add(player.getUniqueId());

			Chest chest = (Chest) block.getState();
			Inventory inventory = Bukkit.createInventory(null, chest.getInventory().getSize());

			inventory.setContents(chest.getInventory().getContents());
			player.openInventory(inventory);

			player.sendMessage(Color.translate("&4&l[Silent] &7Opening silently. Can not edit."));
		}
				
		if(interact.getPlayers().contains(player.getUniqueId())) return;
		
		if(event.getAction().equals(Action.PHYSICAL)) {
			event.setCancelled(true);
		}

		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			Block block = event.getClickedBlock();

			if(!block.getType().equals(Material.LEVER)) return;
			if(!block.getType().equals(Material.WOOD_BUTTON)) return;
			if(!block.getType().equals(Material.STONE_BUTTON)) return;
			
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		InventoryAction action = event.getAction();
		
		if(plugin.getVanishManager().getSilentView().contains(player.getUniqueId())) {
			if(action.equals(InventoryAction.HOTBAR_SWAP) || action.equals(InventoryAction.SWAP_WITH_CURSOR)) {
				event.setCancelled(true);
			}
			
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		
		if(isVanished(player)) {
			if(plugin.getVanishManager().getSilentView().contains(player.getUniqueId())) {
				plugin.getVanishManager().getSilentView().remove(player.getUniqueId());
			}
		}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		OptionType option = OptionType.DAMAGE;

		if(!(event.getEntity() instanceof Player)) return;
		
		Player player = (Player) event.getEntity();
		
		if(isVanished(player)) {
			if(!option.getPlayers().contains(player.getUniqueId())) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		OptionType option = OptionType.DAMAGE;
		
		if(!(event.getEntity() instanceof Player)) return;
		if(!(event.getDamager() instanceof Player)) return;
		
		Player damager = (Player) event.getDamager();

		if(isVanished(damager)) {
			if(!option.getPlayers().contains(damager.getUniqueId())) {
				event.setCancelled(true);

				damager.sendMessage(Color.translate("&eYou can't deal damage to other players while you are hidden."));
			}
		}
			
	
		if(!(event.getEntity() instanceof Villager)) return;

		Villager villager = (Villager) event.getEntity();

		if(!(event.getDamager() instanceof Player)) return;

		if(isVanished(damager)) {
			if(villager.hasMetadata("CombatLogger")) {
				event.setCancelled(true);
				
				damager.sendMessage(Color.translate("&eYou can't deal damage to other players while you are hidden."));
			}
		}
	}

	public static boolean isVanished(Player player) {
		return UHC.getInstance().getVanishManager().getVanishedPlayers().contains(player.getUniqueId());
	}
}
