package com.thesevenq.uhc.scenario.type;

import com.thesevenq.uhc.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class HorselessScenario extends Scenario implements Listener {

	public HorselessScenario() {
		super("Horseless", Material.DIAMOND_BARDING, "You can't tame horses", "You can't tame donkeys.");
	}

	public static void handlePlayerInteractEntity(Entity entity, PlayerInteractEntityEvent event) {
		if(entity.getType() != EntityType.HORSE) return;

		event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if(event.getRightClicked().getType() != EntityType.HORSE) return;
		
		event.setCancelled(true);	
	}

}
