package com.thesevenq.uhc.scenario.type;

import com.thesevenq.uhc.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import com.thesevenq.uhc.scenario.Scenario;

public class BloodEnchantsScenario extends Scenario implements Listener {

	public BloodEnchantsScenario() {
		super("Blood Enchants", Material.ENCHANTED_BOOK, "You lose half a heart for every level you enchant.");
	}

	public static void handlePlayerLevelChange(Player player, int old, int newDamage) {
		if(old > newDamage) {
			double damage = old - newDamage;

			player.damage(damage);
		}
	}

    @EventHandler
    public void onPlayerLevelChange(PlayerLevelChangeEvent event) {
        if(event.getOldLevel() > event.getNewLevel()) {
	        double damage = event.getOldLevel() - event.getNewLevel();

	        event.getPlayer().damage(damage);
        }
    }
}
