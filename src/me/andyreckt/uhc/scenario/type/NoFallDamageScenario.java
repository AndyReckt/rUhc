
package me.andyreckt.uhc.scenario.type;

import me.andyreckt.uhc.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class NoFallDamageScenario extends Scenario implements Listener {

	public NoFallDamageScenario() {
		super("NoFallDamage", Material.DIAMOND_BOOTS, "You can't take fall damage.");
	}

	public static void handleEntityDamage(Entity entity, EntityDamageEvent.DamageCause cause, EntityDamageEvent event) {
		if(!(entity instanceof Player)) {
			return;
		}

		if(cause == EntityDamageEvent.DamageCause.FALL) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if(!(event.getEntity() instanceof Player)) return;

		if(event.getCause() == EntityDamageEvent.DamageCause.FALL) {
			event.setCancelled(true);
		}
	}

}
