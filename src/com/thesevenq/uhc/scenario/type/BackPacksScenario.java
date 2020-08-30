package com.thesevenq.uhc.scenario.type;

import com.thesevenq.uhc.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.event.Listener;

import com.thesevenq.uhc.scenario.Scenario;

public class BackPacksScenario extends Scenario implements Listener {

	public BackPacksScenario() {
		super("BackPacks", Material.CHEST, "Use - /backpack to open the party inventory.");
	}

}
