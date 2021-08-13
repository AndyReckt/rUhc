package me.andyreckt.uhc.scenario.type;

import me.andyreckt.uhc.scenario.Scenario;
import me.andyreckt.uhc.utilties.Color;
import me.andyreckt.uhc.utilties.Msg;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import me.andyreckt.uhc.UHC;

import java.util.ArrayList;

public class BestPVEScenario extends Scenario implements Listener {
	
	public static ArrayList<String> list = new ArrayList<String>();

	public BestPVEScenario() {
		super("BestPvE", Material.WATCH, "Everyone starts on", "a list called bestpve list", "if you take damage", "you are removed from the list." , "The only way to get back", "on the list is getting a kill.", "All players on the bestpve list", "gets 1 extra heart each 10 minutes.");
	}

	public static void handleDeath(Player killer) {
		if(killer.getKiller() == null) return;

		Player player = killer.getKiller();

		if(list.contains(player.getName())) return;

		Msg.sendMessage("&d" + player.getName() + " &ehas gotten a kill! He is back on the &dBest PvE List&e.");

		new BukkitRunnable() {
			public void run() {
				list.add(player.getName());
			}
		}.runTaskLater(UHC.getInstance(), 20L);
	}

	public static void handleEntityDamage(Entity entity, DamageCause cause) {
		if(!(entity instanceof Player)) return;

		if(cause == DamageCause.SUICIDE) return;

		Player player = (Player) entity;

		if(!list.contains(player.getName())) return;

		Msg.sendMessage("&d" + player.getName() + " &etook damage&e.");

		list.remove(player.getName());
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if(event.getEntity().getKiller() == null) return;

		Player player = event.getEntity().getKiller();

		if(list.contains(player.getName())) return;

		Msg.sendMessage("&d" + player.getName() + " &ehas gotten a kill! He is back on the &dBest PvE List&e.");

		new BukkitRunnable() {
			public void run() {
				list.add(player.getName());
			}
		}.runTaskLater(UHC.getInstance(), 20L);
	}

	@EventHandler(ignoreCancelled = true)
	public void onEntityDamage(EntityDamageEvent event) {		
		if(!(event.getEntity() instanceof Player)) return;
		
		if(event.isCancelled()) return;
		
		if(event.getCause() == DamageCause.SUICIDE) return;
		
		Player player = (Player) event.getEntity();

		if(!list.contains(player.getName())) return;
		
		Msg.sendMessage("&d" + player.getName() + " &etook damage&e.");
		
		list.remove(player.getName());	
	}
	
	public static void enable() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			list.add(player.getName());
		}

		new BukkitRunnable() {
			public void run() {
				for(Player player : Bukkit.getOnlinePlayers()) {
					if(!list.contains(player.getName())) return;
					
					player.setMaxHealth(player.getMaxHealth() + 2);
					player.setHealth(player.getHealth() + 2);

					player.sendMessage(Color.translate("&eYou were rewarded for your &dPvE skills&e."));
				}
			}
		}.runTaskTimer(UHC.getInstance(), 10800L, 12000L);
	}
}