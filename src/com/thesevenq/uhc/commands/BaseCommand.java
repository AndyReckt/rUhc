package com.thesevenq.uhc.commands;

import com.thesevenq.uhc.UHC;
import org.bukkit.command.CommandSender;

import lombok.Getter;

@Getter
public abstract class BaseCommand {

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
