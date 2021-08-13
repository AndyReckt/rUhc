package me.andyreckt.uhc.scenario;

import me.andyreckt.uhc.managers.ScenarioManager;
import me.andyreckt.uhc.utilties.Msg;

import org.bukkit.Material;
import me.andyreckt.uhc.UHC;


public abstract class Scenario {
	public UHC getPlugin() {
		return plugin;
	}

	public void setPlugin(UHC plugin) {
		this.plugin = plugin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getFeatures() {
		return features;
	}

	public void setFeatures(String[] features) {
		this.features = features;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

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
			
			Msg.sendMessage("&eScenario &9" + name + " &ehas been &aEnabled&e.");
			
			this.enabled = true;
		} else {
			//HandlerList.unregisterAll((Listener) this);
			
			Msg.sendMessage("&eScenario &9" + name + " &ehas been &cDisabled&e.");
			
			this.enabled = false;
		}
	}

	public void disableScenarios() {
		if(enabled) {
			//HandlerList.unregisterAll((Listener) this);

			Msg.sendMessage("&eAll scenarios have been disabled.");

			this.enabled = false;
		}
	}
}
