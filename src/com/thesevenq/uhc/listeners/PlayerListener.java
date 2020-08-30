package com.thesevenq.uhc.listeners;

import com.thesevenq.uhc.listeners.MultiSpawnListener;
import com.thesevenq.uhc.listeners.PracticeListener;
import com.thesevenq.uhc.managers.GameManager;
import com.thesevenq.uhc.managers.InventoryManager;
import com.thesevenq.uhc.managers.PartyManager;
import com.thesevenq.uhc.managers.PlayerManager;
import com.thesevenq.uhc.utilties.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;
import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.player.party.Party;
import com.thesevenq.uhc.player.UHCData;
import com.thesevenq.uhc.player.state.GameState;

public class PlayerListener extends BaseListener implements Listener {


	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {

		Player player = event.getPlayer();
				
		event.setJoinMessage("");

		UHCData uhcData = UHCData.getByName(player.getName());

		switch (GameManager.getGameState()) {
			case LOBBY: {
				UHCUtils.clearPlayer(player);

				new BukkitRunnable() {
					public void run() {
						UHCUtils.loadLobbyInventory(player);
					}
				}.runTaskLater(plugin, 1L);
				break;
			}

			case PLAYING: {
				if(uhcData.isAlive()) {
					for(Player on : Bukkit.getOnlinePlayers()) {
						if(UHCUtils.isPlayerInSpecMode(on)) {
							player.hidePlayer(on);
						}
					}
				}

				new BukkitRunnable() {
					public void run() {
						if(UHCUtils.isPlayerInSpecMode(player)) {
							for(Player on : Bukkit.getOnlinePlayers()) {
								if(UHCUtils.isPlayerInSpecMode(on)) {
									player.hidePlayer(on);
								}
							}
						}
					}
				}.runTaskLater(plugin, 2L);

				if(PartyManager.isEnabled()) {
					Party party = PartyManager.getByPlayer(player);

					if(party == null) {
						plugin.getPartyManager().handleCreateParty(player);
					}
				}

				if(!uhcData.isAlive()) {
					if(plugin.getSpectatorManager().getDeathInventory().contains(player.getUniqueId())) {
						plugin.getSpectatorManager().handleEnable(player);
					}

					if(!player.hasPermission(Permission.DONOR_PERMISSION)) {
						plugin.getSpectatorManager().handleEnable(player);

						Location loc = UHCUtils.getScatterLocation();

						new BukkitRunnable() {
							public void run() {
								player.teleport(loc);
							}
						}.runTaskLater(plugin, 2L);

						if(player.hasPermission(Permission.STAFF_PERMISSION) || player.hasPermission(Permission.DONOR_PERMISSION)) {
							return;
						}

						new BukkitRunnable() {
							public void run() {
								if(!uhcData.isAlive()) {
									player.kickPlayer(Color.translate("&cYou can't spectate if you aren't donator!"));
								}
							}
						}.runTaskLater(plugin, 100L);
					}

					if(player.hasPermission(Permission.DONOR_PERMISSION)) {
						if(!plugin.getGameManager().isPvp()) {
							new BukkitRunnable() {
								public void run() {
									if(!UHCUtils.isPlayerInSpecMode(player)) {
										plugin.getGameManager().getInvTask().add(player.getUniqueId());

										MultiSpawnListener.randomSpawn(player);

										player.openInventory(InventoryManager.scatter);
									}
								}
							}.runTaskLater(UHC.getInstance(), 5L);
						} else {
							if(UHCUtils.isPlayerInSpecMode(player)) {
								return;
							}

							if(plugin.getGameManager().getInvTask().contains(player.getUniqueId())) {
								plugin.getGameManager().getInvTask().remove(player.getUniqueId());
								player.closeInventory();
							}

							player.getInventory().setArmorContents(null);

							plugin.getSpectatorManager().handleEnable(player);

							Location loc = UHCUtils.getScatterLocation();

							new BukkitRunnable() {
								public void run() {
									player.teleport(loc);
								}
							}.runTaskLater(plugin, 5L);
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();

		UHCData uhcData = UHCData.getByName(player.getName());
		uhcData.save();

		plugin.getGameManager().handleRemoveSaveUser(player);
		plugin.getSpectatorManager().handleDisable(player);
		plugin.getVanishManager().handleRemove(player);

		if(GameManager.getGameState().equals(GameState.SCATTERING)) {
			Msg.sendMessage("&3" + player.getName() + " &bhas disconnected while &3Scatter&b was &aRunning&b. &b(&bHe has 5 minutes to come back&b)", Permission.STAFF_PERMISSION);

			new BukkitRunnable() {
				public void run() {
					if(!Bukkit.getOfflinePlayer(player.getUniqueId()).isOnline()) {
						uhcData.setAlive(false);

						player.setWhitelisted(false);

						Msg.sendMessage("&a" + player.getName() + "&a[" + uhcData.getKills() + "&a] &7has disconected for to long.");
						Msg.sendMessage("&a" + player.getName() + " &7has disconnected while &3Scatter&7 was &aRunning&b. &7(&cDied&7)", Permission.STAFF_PERMISSION);
					}
				}
			}.runTaskLater(plugin, 6000L);
		}

		if(uhcData.isAlive()) {
			if ((GameManager.getGameState().equals(GameState.PLAYING) || GameManager.getGameState().equals(GameState.SCATTERING)) && player.getWorld().equals("uhc_world")) {
				uhcData.setArmor(player.getInventory().getArmorContents());
				uhcData.setItems(player.getInventory().getContents());

				uhcData.setRespawnLocation(player.getLocation());
			}
		}

		UHCUtils.disableSpecMode(player);

		player.setWhitelisted(true);
	}

	/*@EventHandler
	public void onBlockBurn(BlockBurnEvent event) {
		event.setCancelled(true);
	}*/

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        if(player.hasPermission(Permission.STAFF_PERMISSION)) {
        	player.setWhitelisted(true);
		}

        if(GameManager.getGameState().equals(GameState.SCATTERING)) {
        	event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Color.translate("&cThe scatter is currently running! \n\n Please wait until the scatter finishes."));
        } else if(GameManager.getGameState().equals(GameState.PLAYING)  && ((!player.hasPermission(Permission.STAFF_PERMISSION)) && (!player.isWhitelisted()) && (!player.hasPermission(Permission.DONOR_PERMISSION)))) {
        	event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Color.translate("&cThe game is already running! \n\n  &cStore: store.rujtex.eu"));
		} else if (Bukkit.getServer().hasWhitelist() && !player.isWhitelisted() && !player.hasPermission(Permission.STAFF_PERMISSION)) {
			event.disallow(PlayerLoginEvent.Result.KICK_FULL, Color.translate("&cThe server is currently whitelisted! \n\n &cStore: &lstore.rujtex.eu"));
        } else if(Bukkit.getMaxPlayers() <= Bukkit.getOnlinePlayers().size()  && (!player.hasPermission(Permission.DONOR_PERMISSION) && (!player.hasPermission(Permission.STAFF_PERMISSION) && (!player.isWhitelisted())))) {
        	event.disallow(PlayerLoginEvent.Result.KICK_FULL, Color.translate("&cThe server is currently full! \n\n &cStore: &lstore.rujtex.eu"));
        } else {
			event.allow();
		}
    }

   // @EventHandler
	//public void onServerShutdown(ServerShutdownEvent event) {
		//WorldCreator.stop(Bukkit.getConsoleSender());

		//plugin.getGameManager().setRestarted(false);
		//plugin.getGameManager().setWorldUsed(true);
		//plugin.getGameManager().setGenerated(false);
	//}

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
    	Player player = event.getPlayer();
    	
    	if(event.getMessage().toLowerCase().startsWith("/tc") || event.getMessage().toLowerCase().startsWith("/fc")) {
    		event.setCancelled(true);
    		
    		player.chat("/party chat");
    	} else if(event.getMessage().toLowerCase().startsWith("/sendcoords")) {
    		event.setCancelled(true);
    		
    		player.chat("/party coords");
    	}
    }
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        
        if(player.getItemInHand() == null) {
        	return;
		}
         
		if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
			if(GameManager.getGameState().equals(GameState.LOBBY)) {
				switch (player.getItemInHand().getType()) {
					case PAPER: {
						player.performCommand("config" + (player.hasPermission(Permission.STAFF_PERMISSION) ? " staff" : ""));
						break;
					}

					case DIAMOND_SWORD: {
						player.performCommand("uhcpractice join");
						break;
					}

					case NAME_TAG: {
						player.performCommand("party create");
						break;
					}

					case SKULL_ITEM: {
						player.performCommand("party show");
						break;
					}

					case REDSTONE: {
						player.performCommand("party leave");
						break;
					}

					case EMERALD: {
						PlayerManager.getLeaderboard(player);
						break;
					}

					case WATCH: {
						player.performCommand("settings");
						break;
					}
				}
			}
		}
	}
	
    @EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
    	if(event.isCancelled()) return;
    	
		Player player = event.getPlayer();
		
		if(player.isOp() && player.hasPermission(Permission.OP_PERMISSION) && !event.getBlock().getType().equals(Material.REDSTONE) && !event.getBlock().getType().equals(Material.SKULL_ITEM)) {
			return;
		}

		if(player.getLocation().getWorld().getName().equalsIgnoreCase("world") || !GameManager.getGameState().equals(GameState.PLAYING)) {
			event.setCancelled(true);
		}
	}
    
    @EventHandler
 	public void onBlockPlace(BlockPlaceEvent event) {
    	if(event.isCancelled()) return;
    	
 		Player player = event.getPlayer();

		if(player.isOp() && player.hasPermission(Permission.OP_PERMISSION) && !event.getBlock().getType().equals(Material.REDSTONE) && !event.getBlock().getType().equals(Material.SKULL_ITEM)) {
			return;
		}

		if(player.getLocation().getWorld().getName().equalsIgnoreCase("world") || !GameManager.getGameState().equals(GameState.PLAYING)) {
			event.setCancelled(true);
		}
 	}
    
    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent event) {
		if(!GameManager.getGameState().equals(GameState.PLAYING)) {
			Player player = (Player) event.getInitiator().getHolder();

			if(player.isOp() || player.hasPermission(Permission.OP_PERMISSION) || PracticeListener.isInPractice(player)) {
				return;
			}

    		event.setCancelled(true);
    	}
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
    	if(!GameManager.getGameState().equals(GameState.PLAYING)) {
    		Player player = (Player) event.getWhoClicked();

			if(player.isOp() || player.hasPermission(Permission.OP_PERMISSION) || PracticeListener.isInPractice(player)) {
				return;
			}
    		
    		event.setCancelled(true);
    	}
    }
    
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			
			if(player.getLocation().getWorld().getName().equalsIgnoreCase("world")) {
				if(PracticeListener.isInPractice(player)) {
					return;
				}
				
				event.setCancelled(true);
				return;
			}

			if(!GameManager.getGameState().equals(GameState.PLAYING)) {
				event.setCancelled(true);
			}
		}
    }
    
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			
			if(player.getLocation().getWorld().getName().equalsIgnoreCase("world")) {
				if(PracticeListener.isInPractice(player)) {
					return;
				}

				event.setCancelled(true);
				return;
			}

			if(!GameManager.getGameState().equals(GameState.PLAYING)) {
				event.setCancelled(true);
			}
		}
	}
	
    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        UHCData uhcData = UHCData.getByName(event.getPlayer().getName());

        if(uhcData.isAlive()) {
        	return;
		}

		if(!GameManager.getGameState().equals(GameState.PLAYING)) {
			event.setCancelled(true);
		}
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
		if(!GameManager.getGameState().equals(GameState.PLAYING)) {
			event.setCancelled(true);
		}
    }
    
    @EventHandler
    public void onFoodLeveLChange(FoodLevelChangeEvent event) {
    	Player player = (Player) event.getEntity();
    	
		if(plugin.getPracticeManager().getUsers().contains(player.getUniqueId())) {
			event.setCancelled(true);
			return;
		}
    	
		if(!GameManager.getGameState().equals(GameState.PLAYING)) {
    		event.setCancelled(true);
    	}
    }
}
