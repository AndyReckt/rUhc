package me.andyreckt.uhc.listeners;

import me.andyreckt.uhc.managers.BorderManager;
import me.andyreckt.uhc.managers.GameManager;
import me.andyreckt.uhc.managers.PartyManager;
import me.andyreckt.uhc.utilties.*;
import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.player.UHCData;
import me.andyreckt.uhc.player.party.Party;
import me.andyreckt.uhc.player.state.GameState;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class GameListener extends BaseListener implements Listener {
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if(plugin.getPracticeManager().isOpen()) {
			return;
		}
		
		Player player = event.getEntity();
		
		UHCData uhcDataVictim = UHCData.getByName(player.getName());

		uhcDataVictim.setRespawnLocation(player.getLocation());
		uhcDataVictim.setArmor(player.getInventory().getArmorContents());
		uhcDataVictim.setItems(player.getInventory().getContents());

		uhcDataVictim.setAlive(false);

		Player killer = player.getKiller();
		
		if(killer != null && killer instanceof Player) {
			if(plugin.getPracticeManager().getUsers().contains(player.getUniqueId())) return;

			if(PartyManager.isEnabled()) {
				Party party = PartyManager.getByPlayer(killer);

				party.setKills(party.getKills() + 1);
			}
		}

		if(GameManager.getGameState().equals(GameState.PLAYING) && event.getEntity().getWorld() != UHCUtils.getSpawnLocation().getWorld()) {
			uhcDataVictim.setRespawnLocation(player.getLocation());
			uhcDataVictim.setArmor(player.getInventory().getArmorContents());
			uhcDataVictim.setItems(player.getInventory().getContents());
			
			UHCUtils.clearUHC(player);
			
			new BukkitRunnable() {
				public void run() {
					if(player.hasPermission(Permission.STAFF_PERMISSION)) {
						return;
					}

					if(player.hasPermission(Permission.DEFAULT_PERMISSION)) {
						plugin.getSpectatorManager().handleEnable(player);
					} else {
						plugin.getSpectatorManager().handleEnable(player);
						
						new BukkitRunnable() {
							public void run() {
								player.kickPlayer(Color.translate("&cYou can't spectate game if you are not donator."));
							}
						}.runTaskLater(UHC.getInstance(), 600L);
					}
				}
			}.runTaskLater(plugin, 5L);

			if(player.hasPermission(Permission.STAFF_PERMISSION)) {
				UHCUtils.clearPlayer(player);

				new BukkitRunnable() {
					public void run() {
						plugin.getSpectatorManager().handleEnable(player);
					}
				}.runTaskLater(plugin, 15L);
			}
		}
	}
	
	@EventHandler
	public void onItemConsume(PlayerItemConsumeEvent event) {
		ItemStack item = event.getItem();
		
		if(item == null
				|| item.getType() != Material.GOLDEN_APPLE
				|| item.getItemMeta() == null
				|| !item.getItemMeta().hasDisplayName()
				|| !item.getItemMeta().getDisplayName().equalsIgnoreCase(Color.translate("&5&lGolden Head"))) {
			return;
		}
		
		Player player = event.getPlayer();
		
		player.removePotionEffect(PotionEffectType.REGENERATION);
		player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1));
	}
	
	@EventHandler
	public void onVehicleEnterEvent(VehicleEnterEvent event) {
		if(!(event.getEntered() instanceof Player)) return;

		UHCData uhcData = UHCData.getByName(event.getEntered().getName());

		if(!uhcData.isAlive()) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerPortal(PlayerPortalEvent event) {
		Player player = event.getPlayer();
		
		if(BorderManager.border <= 500) {
			event.setCancelled(true);
			
			player.sendMessage(Color.translate("&cYou can't enter nether while Border is under 500."));
		}
		
		if(!event.isCancelled() && event.getCause() == TeleportCause.NETHER_PORTAL) {
            if(event.getFrom().getWorld().getName().equalsIgnoreCase("uhc_world")) {
                double x = player.getLocation().getX() / 8.0D;
                double y = player.getLocation().getY();
                double z = player.getLocation().getZ() / 8.0D;
                
                event.setTo(event.getPortalTravelAgent().findOrCreate(new Location(Bukkit.getServer().getWorld("uhc_nether"), x, y, z)));
            } else if(event.getFrom().getWorld().getName().equalsIgnoreCase("uhc_nether")) {
                double x = player.getLocation().getX() * 8.0D;
                double y = player.getLocation().getY();
                double z = player.getLocation().getZ() * 8.0D;
                
                event.setTo(event.getPortalTravelAgent().findOrCreate(new Location(Bukkit.getServer().getWorld("uhc_world"), x, y, z)));
            }
        }
	}
	
    @EventHandler
    public void onShears(BlockBreakEvent event) {
    	if(event.isCancelled()) {
    		return;
		}

    	Player player = event.getPlayer();

    	if(player.getGameMode() == GameMode.CREATIVE || player.getItemInHand().getType() != Material.SHEARS) {
    		return;
		}

		Block block = event.getBlock();

		if(block.getType() == Material.LEAVES || block.getType() == Material.LEAVES_2) {
    		if(Math.random() * 100.0D <= plugin.getGameManager().getShearsRate() + .0D) {
    	    	block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.APPLE));
    	    }
    	}
    }

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(!(event.getEntity() instanceof Player)) return;

		Player entity = (Player) event.getEntity();

		if(!(event.getDamager() instanceof Arrow)) return;

		Arrow arrow = (Arrow) event.getDamager();

		if(!(arrow.getShooter() instanceof Player)) return;

		Player shooter = (Player) arrow.getShooter();

		if(entity.getName().equals(shooter.getName())) return;

		double health = Math.ceil(entity.getHealth() - event.getFinalDamage()) / 2.0D;

		if(health > 0.0D) {
			shooter.sendMessage(Color.translate("&9" + entity.getName() + " &eis now at &9" + health + Msg.HEART + "&e."));
		}
	}
    
    @EventHandler
    public void onWeatherChangeEvent(WeatherChangeEvent event) {
        if(event.toWeatherState()) {
        	event.setCancelled(true);
        }
    }

    /*@EventHandler
    public void onChunkUnloadEvent(ChunkUnloadEvent event) {
        if(plugin.getGameManager().isMapGenerating()) {
        	event.setCancelled(true);
        }
    }*/
}