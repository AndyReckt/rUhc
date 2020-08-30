package com.thesevenq.uhc.scenario;

import com.thesevenq.uhc.managers.ScenarioManager;
import com.thesevenq.uhc.utilties.Msg;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import com.thesevenq.uhc.UHC;

@Getter
@Setter
public abstract class Scenario {

	protected UHC plugin = UHC.getInstance();

	private String name;
	private String[] features;
	private Material material;
	private boolean enabled;

	public Scenario(String name, Material icon, String... features) {
		this.name = name;
		this.features = features;
		this.material = icon;
		this.enabled = false;
		
		ScenarioManager.getScenarios().add(this);
	}

	public void toggle() {
		if(!this.enabled) {
			//Bukkit.getPluginManager().registerEvents((Listener) this, UHC.getInstance());
			
			Msg.sendMessage("&bScenario &3" + name + " &bhas been &aEnabled&b.");
			
			this.enabled = true;
		} else {
			//HandlerList.unregisterAll((Listener) this);
			
			Msg.sendMessage("&bScenario &3" + name + " &bhas been &cDisabled&b.");
			
			this.enabled = false;
		}
	}

	public void disableScenarios() {
		if(enabled) {
			//HandlerList.unregisterAll((Listener) this);

			Msg.sendMessage("&bAll scenarios have been disabled.");

			this.enabled = false;
		}
	}
}
