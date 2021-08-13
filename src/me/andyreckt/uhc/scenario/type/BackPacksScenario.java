package me.andyreckt.uhc.scenario.type;

import me.andyreckt.uhc.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.event.Listener;

public class BackPacksScenario extends Scenario implements Listener {

	public BackPacksScenario() {
		super("BackPacks", Material.CHEST, "Use - /backpack to open the party inventory.");
	}

}
