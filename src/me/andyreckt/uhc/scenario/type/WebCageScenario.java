package me.andyreckt.uhc.scenario.type;

import java.util.List;

import me.andyreckt.uhc.scenario.Scenario;
import me.andyreckt.uhc.utilties.UHCUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.andyreckt.uhc.UHC;

public class WebCageScenario extends Scenario implements Listener {

	public WebCageScenario() {
		super("WebCage", Material.WEB, "When you kill a player a sphere of", "cobwebs surrounds you.");
	}

	public static void handleDeath(Entity entity) {
		Player player = (Player) entity;

		if(UHC.getInstance().getPracticeManager().getUsers().contains(player.getUniqueId())) {
			return;
		}

		Location location = player.getLocation();
		Player killer = player.getKiller();

		if(killer == null) {
			return;
		}

		List<Location> locations = UHCUtils.getSphere(location, 5, true);

		for(Location blocks : locations) {
			if(blocks.getBlock().getType() == Material.AIR) {
				blocks.getBlock().setType(Material.WEB);
			}
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();

		if(plugin.getPracticeManager().getUsers().contains(player.getUniqueId())) return;

		Location location = player.getLocation();

		Player killer = player.getKiller();

		if(killer == null) return;
		
		List<Location> locations = UHCUtils.getSphere(location, 5, true);

		for(Location blocks : locations) {
			if(blocks.getBlock().getType() == Material.AIR) {
				blocks.getBlock().setType(Material.WEB);
			}
		}
	}

}