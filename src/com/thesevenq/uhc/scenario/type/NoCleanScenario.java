package com.thesevenq.uhc.scenario.type;

import com.thesevenq.uhc.managers.BorderManager;
import com.thesevenq.uhc.scenario.Scenario;
import com.thesevenq.uhc.utilties.Color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import com.thesevenq.uhc.UHC;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NoCleanScenario extends Scenario implements Listener {
	
	private static Map<UUID, Long> noClean = new HashMap<>();

	public NoCleanScenario() {
		super("No Clean", Material.DIAMOND_SWORD, "When player dies he will be invincible for 30 seconds.");
	}

	public static void handleDeath(Entity entity, Player killer) {
		if(!(entity instanceof Player)) return;

		if(killer == null) return;
		if(UHC.getInstance().getPracticeManager().getUsers().contains(killer.getUniqueId())) return;

		applyCooldown(killer);


		killer.sendMessage(Color.translate("&a[No Clean] Your No Clean invincibility has been added."));

		Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> {
			if(killer.isOnline()) {
			}
		}, 20 * (BorderManager.border < 51 ? 60 : 30));
	}

	public static void handleEntityDamageByEntity(Entity entity, Entity entityDamager, EntityDamageByEntityEvent event) {
		if(!(entity instanceof Player)) return;
		if(!(entityDamager instanceof Player)) return;

		Player target = (Player) entity;
		Player damager = (Player) entityDamager;

		if(isActive(target)) {
			damager.sendMessage(Color.translate("&c[No Clean] That player has No Clean invincibility."));
			event.setCancelled(true);
			return;
		}

		if(isActive(damager)) {
			removeCooldown(damager);
			damager.sendMessage(Color.translate("&c[No Clean] Your No Clean invincibility has been removed."));
		}
	}

	public static void handleEntityDamage(Entity entity, EntityDamageEvent event) {
		if(!(entity instanceof Player)) return;

		Player player = (Player) entity;

		if(isActive(player)) {
			event.setCancelled(true);
		}
	}

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(EntityDeathEvent event) {    	
    	if(!(event.getEntity() instanceof Player)) return;

    	Player killer = event.getEntity().getKiller();
    	
    	if(killer == null) return;
    	if(plugin.getPracticeManager().getUsers().contains(killer.getUniqueId())) return;
    	
    	applyCooldown(killer);

    	
    	killer.sendMessage(Color.translate("&a[No Clean] Your No Clean invincibility has been added."));

		Bukkit.getScheduler().runTaskLater(plugin, () -> {
			if(killer != null) {
			}
		}, 20 * (BorderManager.border < 51 ? 60 : 30));
    }
    
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
    	if(event.isCancelled()) return;
    	    	
    	if(!(event.getEntity() instanceof Player)) return;
    	if(!(event.getDamager() instanceof Player)) return;
    	
    	Player target = (Player) event.getEntity();
    	Player damager = (Player) event.getDamager();
    	
    	if(isActive(target)) {
    		damager.sendMessage(Color.translate("&c[No Clean] That player has No Clean invincibility."));
    		event.setCancelled(true);
    		return;
    	}
     	
    	if(isActive(damager)) {
    		removeCooldown(damager);
    		damager.sendMessage(Color.translate("&c[No Clean] Your No Clean invincibility has been removed."));
    	}
    }
    
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
    	if(event.isCancelled()) return;
    	
    	if(!(event.getEntity() instanceof Player)) return;
    	
    	Player player = (Player) event.getEntity();
    	
    	if(isActive(player)) event.setCancelled(true);
    }
    
    public static void applyCooldown(Player player) {
    	noClean.put(player.getUniqueId(), System.currentTimeMillis() + ((BorderManager.border < 51 ? 60 : 30) * 1000));
    }
	
	public static boolean isActive(Player player) {
        return noClean.containsKey(player.getUniqueId()) && System.currentTimeMillis() < noClean.get(player.getUniqueId());
	}
	
	public static void removeCooldown(Player player) {
		noClean.remove(player.getUniqueId());
	}
	
	public static long getMillisecondsLeft(Player player) {
	    if(noClean.containsKey(player.getUniqueId())) {
	    	return Math.max(noClean.get(player.getUniqueId()) - System.currentTimeMillis(), 0L);
	    }
	    return 0L;
	}
}
