package me.andyreckt.uhc.commands;

import me.andyreckt.uhc.UHC;
import org.bukkit.command.CommandSender;




public abstract class BaseCommand {
	public UHC getInstance() {
		return instance;
	}

	public UHC getPlugin() {
		return plugin;
	}

	public boolean isForPlayerUseOnly() {
		return forPlayerUseOnly;
	}

	public String getCommand() {
		return command;
	}

	public String getPermission() {
		return permission;
	}

	protected UHC instance;
	protected UHC plugin = UHC.getInstance();

	public boolean forPlayerUseOnly;
	public String command;
	public String permission;

	public BaseCommand(UHC plugin) {
		this.instance = plugin;

		this.command = "";
		this.permission = "";

		this.forPlayerUseOnly = false;
	}

	public abstract void execute(CommandSender sender, String[] args);
}
