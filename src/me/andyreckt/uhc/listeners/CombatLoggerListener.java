package me.andyreckt.uhc.listeners;

import me.andyreckt.uhc.managers.GameManager;
import me.andyreckt.uhc.utilties.BaseListener;
import me.andyreckt.uhc.utilties.Color;
import me.andyreckt.uhc.utilties.Msg;
import me.andyreckt.uhc.utilties.Permission;

import me.andyreckt.uhc.player.UHCData;
import me.andyreckt.uhc.player.state.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class CombatLoggerListener extends BaseListener implements Listener {

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		LivingEntity entity = event.getEntity();
		
		if(!(entity instanceof Villager)) return;
 			Villager villager = (Villager) entity;
			Entity killer = entity.getKiller();
			
			if(villager.hasMetadata("CombatLogger")) {
				Player player = (Player) villager.getMetadata("Player").get(0).value();

				int pKills = UHCData.getByName(player.getName()).getKills();
				
				if(killer instanceof Player) {
					int kKills = (UHCData.getByName(player.getName()).getKills() + 1);
					
					UHCData vplayer = UHCData.getByName(player.getName());
					
					vplayer.setAlive(false);
					
					Bukkit.broadcastMessage(Color.translate("&c" + villager.getCustomName() + "&7[&f" + pKills + "&7] &7(Combat Logger) &ewas slain by &c" + ((Player) killer).getName() + "&7[&f" + kKills + "&7]"));
				} else {
					UHCData vplayer = UHCData.getByName(player.getName());
					
					vplayer.setAlive(false);
					
					Bukkit.broadcastMessage(Color.translate("&c" + villager.getCustomName() + "&7[&f" + pKills + "&7] &7(Combat Logger) &edied"));
				}
				
				
				ItemStack[] contentsArray = (ItemStack[]) villager.getMetadata("Contents").get(0).value();
				ItemStack[] armorArray = (ItemStack[]) villager.getMetadata("Armor").get(0).value();
				
				for(ItemStack content : contentsArray) {
					if(content != null && !content.getType().equals(Material.AIR)) {
						villager.getWorld().dropItemNaturally(villager.getLocation(), content);
					}
				}
				
				for(ItemStack armor : armorArray) {
					if(armor != null && !armor.getType().equals(Material.AIR)) {
						villager.getWorld().dropItemNaturally(villager.getLocation(), armor);
					}
				}
				
				UHCData uhcData = UHCData.getByName(player.getName());
				
				if(!Bukkit.getOfflinePlayer(player.getUniqueId()).isOnline()) {
					uhcData.setRespawnLocation(player.getLocation());
            		uhcData.setAlive(false);
    				
    				uhcData.setArmor(player.getInventory().getArmorContents());
    				uhcData.setItems(player.getInventory().getContents());

    				player.setWhitelisted(player.hasPermission(Permission.DEFAULT_PERMISSION) ? true : false);
            	}

				plugin.getCombatLoggerManager().getDead().add(player.getUniqueId());
				plugin.getCombatLoggerManager().getPlayers().add(player.getUniqueId());

				if(killer != null) {
					UHCData uhcDataKiller = UHCData.getByName(killer.getName());

					uhcDataKiller.setKills(uhcDataKiller.getKills() + 1);
				}
			}

	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(event.isCancelled()) return;

		if(event.getEntity() instanceof Villager && event.getDamager() instanceof Player) {
			Villager villager = (Villager) event.getEntity();
			Player player = (Player) event.getDamager();

			if(villager.hasMetadata("CombatLogger") && plugin.getCombatLoggerManager().getCombatLoggers().containsKey(villager.getCustomName())) {
				if(!plugin.getGameManager().isPvp()) {
					event.setCancelled(true);
					return;
				}

				if(plugin.getCombatLoggerManager().getCombatLoggers().get(villager.getCustomName()).contains(player)) {
					event.setCancelled(true);
				} else {
					new BukkitRunnable() {
						public void run() {
							villager.setVelocity(new Vector(0, 0, 0));
						}
					}.runTaskLater(plugin, 1L);
				}
			}
		}
	}

	@EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if(event.getRightClicked().hasMetadata("CombatLogger")) {
        	event.setCancelled(true);
        }
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        for(Entity entity : event.getChunk().getEntities()) {
            if(entity.hasMetadata("CombatLogger") && !entity.isDead()) {
            	event.setCancelled(true);
            }
        }
    }

    @EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		plugin.getCombatLoggerManager().handleJoin(player);

		if(plugin.getCombatLoggerManager().getPlayers().contains(player.getUniqueId())) {
			player.getInventory().clear();

			player.getInventory().setArmorContents(new ItemStack[4]);
		}

		plugin.getCombatLoggerManager().getPlayers().remove(player.getUniqueId());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		plugin.getCombatLoggerManager().handleQuit(player);
		
		if(!GameManager.getGameState().equals(GameState.PLAYING)) {
			return;
		}
		
		new BukkitRunnable() {
			public void run() {
				if(!Bukkit.getOfflinePlayer(player.getUniqueId()).isOnline()) {
					UHCData uhcData = UHCData.getByName(player.getName());
					
					if(uhcData.isAlive()) {
						uhcData.setRespawnLocation(player.getLocation());
						uhcData.setAlive(false);

						uhcData.setArmor(player.getInventory().getArmorContents());
						uhcData.setItems(player.getInventory().getContents());

						Msg.sendMessage("&c" + player.getName() + "&7[&f" + uhcData.getKills() + "&7] &ehas disconected for too long.");
					}
				}
			}
		}.runTaskLater(plugin, 6000L);
	}
}