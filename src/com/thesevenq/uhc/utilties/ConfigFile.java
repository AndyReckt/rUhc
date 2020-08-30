package com.thesevenq.uhc.utilties;

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigFile extends YamlConfiguration {

	@Getter private File file;

	public ConfigFile(JavaPlugin plugin, String name) {
		this.file = new File(plugin.getDataFolder(), name);

		if(!this.file.exists()) {
			plugin.saveResource(name, false);
		}

		try {
			this.load(this.file);
		} catch(IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public void save() {
		try {
			this.save(this.file);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getInt(String path) {
		return super.getInt(path, 0);
	}

	@Override
	public double getDouble(String path) {
		return super.getDouble(path, 0.0);
	}

	@Override
	public boolean getBoolean(String path) {
		return super.getBoolean(path, false);
	}

	@Override
	public String getString(String path) {
		return Color.translate(super.getString(path, ""));
	}

	@Override
	public List<String> getStringList(String path) {
		return super.getStringList(path).stream().map(Color::translate).collect(Collectors.toList());
	}
}