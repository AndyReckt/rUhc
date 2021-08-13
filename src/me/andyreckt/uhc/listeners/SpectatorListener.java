package me.andyreckt.uhc.listeners;

import me.andyreckt.uhc.utilties.BaseListener;
import me.andyreckt.uhc.utilties.Color;
import me.andyreckt.uhc.utilties.Permission;
import me.andyreckt.uhc.utilties.UHCUtils;
import me.andyreckt.uhc.player.UHCData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import me.andyreckt.uhc.UHC;

public class SpectatorListener extends BaseListener implements Listener {
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if(!(event.getEntity() instanceof Player)) return;
		
		Player player = (Player) event.getEntity();

		if(isInSpectatorMode(player)) {
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();

		if(isInSpectatorMode(player)) {
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();

		if(isInSpectatorMode(player)) {
			event.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();

		if(isInSpectatorMode(player)) {
			event.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(!(event.getDamager() instanceof Player)) return;

		Player damager = (Player) event.getDamager();

		if(isInSpectatorMode(damager)) {
			event.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void onPlayerItemDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();

		if(isInSpectatorMode(player)) {
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();

		if(isInSpectatorMode(player)) {
			if(event.getClickedInventory() != null && event.getClickedInventory().getTitle().contains("Alive Players - ")) {
				event.setCancelled(true);

				ItemStack stack = event.getCurrentItem();

				if(stack != null && stack.getType() != Material.AIR) {
					String title = event.getClickedInventory().getTitle();
					String displayName = stack.getItemMeta().getDisplayName();

					int page = Integer.parseInt(title.substring(title.lastIndexOf("/") - 1, title.lastIndexOf("/")));
					int total = Integer.parseInt(title.substring(title.lastIndexOf("/") + 1, title.lastIndexOf("/") + 2));

					System.out.println("Page: " + page + ", Total: " + total);

					if (displayName.contains("Next Page")) {
						if (page + 1 > total) {
							player.sendMessage(Color.translate("&cThere are no more pages."));
							return;
						}

						player.openInventory(plugin.getSpectatorManager().getInventory(page + 1));
						return;
					} else if (displayName.contains("Previous Page")) {
						if (page == 1) {
							player.sendMessage(Color.translate("&cYou're on the first page."));
							return;
						}

						player.openInventory(plugin.getSpectatorManager().getInventory(page - 1));
					}

					if(stack.getType().equals(Material.SKULL_ITEM)) {
						String name = displayName.replace("&7", "").replace("§7", "");
						Player target = Bukkit.getPlayer(name);

						if(target != null) {
							if(player.isOp() || player.hasPermission(Permission.STAFF_PERMISSION)) {
								player.teleport(target.getLocation());
								player.sendMessage(Color.translate("&eYou have been teleported to &9" + target.getName() + "&e."));
								return;
							}
							/*
							if(player.hasPermission(Permission.XENON_PERMISSION)
									|| player.hasPermission(Permission.PARTNER_PERMISSION)) {

								if(UHCUtils.maxSpecSize(target.getLocation(), 500)) {
									player.teleport(target.getLocation());
									player.sendMessage(Color.translate("&eYou have been teleported to &9" + target.getName() + "&e."));
								}
							} else if(player.hasPermission(Permission.KRYPTON_PERMISSION)) {
								if(UHCUtils.maxSpecSize(target.getLocation(), 400)) {
									player.teleport(target.getLocation());
									player.sendMessage(Color.translate("&eYou have been teleported to &9" + target.getName() + "&e."));
								}
							} else if(player.hasPermission(Permission.TITANIUM_PERMISSION)) {
								if(UHCUtils.maxSpecSize(target.getLocation(), 300)) {
									player.teleport(target.getLocation());
									player.sendMessage(Color.translate("&eYou have been teleported to &9" + target.getName() + "&e."));
								}
							} else if(player.hasPermission(Permission.NITROGEN_PERMISSION)) {
								if(UHCUtils.maxSpecSize(target.getLocation(), 200)) {
									player.teleport(target.getLocation());
									player.sendMessage(Color.translate("&eYou have been teleported to &9" + target.getName() + "&e."));
								}
							} else if(player.hasPermission(Permission.HYDROGEN_PERMISSION)) {
								if(UHCUtils.maxSpecSize(target.getLocation(), 100)) {
									player.teleport(target.getLocation());
									player.sendMessage(Color.translate("&eYou have been teleported to &9" + target.getName() + "&e."));
								}


							} */
							else {
								if(UHCUtils.maxSpecSize(target.getLocation(), 100)) {
									player.teleport(target.getLocation());
									player.sendMessage(Color.translate("&eYou have been teleported to &9" + target.getName() + "&e."));
								}
							}
						}
					}
				}
			}

			if(event.getInventory().getTitle().equalsIgnoreCase("Alive Players:")) {
				event.setCancelled(true);

				if(event.getCurrentItem().getType() != Material.SKULL_ITEM) {
					return;
				}

				Player clicked = Bukkit.getPlayer(event.getCurrentItem().getItemMeta().getDisplayName().replace("§7", ""));
				player.teleport(clicked);
			}

			if(event.getSlotType().equals(SlotType.OUTSIDE)) {
				event.setCancelled(true);
				return;
			}

			ItemStack item = event.getCurrentItem();
			InventoryAction action = event.getAction();

			if(item == null || item.getType().equals(Material.AIR)) {
				if(action.equals(InventoryAction.HOTBAR_SWAP) || action.equals(InventoryAction.SWAP_WITH_CURSOR)) {
					event.setCancelled(true);
					return;
				}
			}

			event.setCancelled(true);

			if(item.hasItemMeta()) {
				if(item.getItemMeta().hasDisplayName()) {
					if(item.getItemMeta().getDisplayName().equals(ChatColor.RED + "Close Preview")) {
						new BukkitRunnable() {
							public void run() {
								player.closeInventory();
							}
						}.runTaskLater(UHC.getInstance(), 1L);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		ItemStack item = event.getItem();
		
		if(item == null
				|| item.getType().equals(Material.AIR)
				|| !item.hasItemMeta()
				|| !item.getItemMeta().hasDisplayName()) return;
		
		if(isInSpectatorMode(player)) {
			if(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
				switch (item.getType()) {
					case COMPASS: {
						player.openInventory(plugin.getSpectatorManager().getInventory(1));
						break;
					}

					case WATCH: {
						plugin.getSpectatorManager().handleRandomTeleport(player);
						break;
					}

					case BOOK: {
						player.performCommand("config" + (player.hasPermission(Permission.STAFF_PERMISSION) ? " staff" : ""));
						return;
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getItemInHand();

		if(item == null
				|| item.getType().equals(Material.AIR)
				|| !item.hasItemMeta()
				|| !item.getItemMeta().hasDisplayName()
				|| !player.hasPermission(Permission.STAFF_PERMISSION)) return;

		if(UHCUtils.isPlayerInSpecMode(player)) {
			if(event.getRightClicked() instanceof Player) {
				Player rightClicked = (Player) event.getRightClicked();

				if(item.getType() == Material.PACKED_ICE) {
					player.performCommand("freeze " + rightClicked.getName());
				}
			}
		}
	}
	
	@EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {	
		if(event.isCancelled()) return;

		Player player = event.getPlayer();

		if(player.getItemInHand().getType() != Material.AIR) return;
		if(!(event.getRightClicked() instanceof Player)) return;

		Player rightClicked = (Player) event.getRightClicked();

		if(UHCUtils.isPlayerInSpecMode(player)) {
			if(player.hasPermission(Permission.STAFF_PERMISSION)) {
				event.getPlayer().openInventory(plugin.getSpectatorManager().createInventory(event.getPlayer(), rightClicked));
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();

        UHCData uhcData = UHCData.getByName(player.getName());

        if(uhcData.isAlive()) {
        	return;
		}

		if(isInSpectatorMode(player)) {
			event.setCancelled(true);
			return;
		}
	}

	private boolean isInSpectatorMode(Player player) {
		if(plugin.getSpectatorManager().getSpectators().containsKey(player.getUniqueId())) {
			return true;
		}

		return false;
	}
}
