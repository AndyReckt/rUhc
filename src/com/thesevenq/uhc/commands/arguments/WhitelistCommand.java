package com.thesevenq.uhc.commands.arguments;

import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.commands.BaseCommand;
import com.thesevenq.uhc.utilties.Color;
import com.thesevenq.uhc.utilties.Msg;
import com.thesevenq.uhc.utilties.Permission;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class WhitelistCommand extends BaseCommand {

	public WhitelistCommand(UHC plugin) {
		super(plugin);
		
		this.command = "whitelist";
		this.permission = Permission.ADMIN_PERMISSION;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length == 0) {
			sender.sendMessage(Color.translate("&cUsage: /whitelist <add|remove|on|off|clear> <player>"));
		} else if(args[0].equalsIgnoreCase("on")) {
			Bukkit.setWhitelist(true);
			
			Msg.sendMessage("&3Whitelist &bhas been &aEnabled&b.");
		} else if(args[0].equalsIgnoreCase("off")) {
			Bukkit.setWhitelist(false);
			
			Msg.sendMessage("&3Whitelist &bhas been &cDisabled&b.");
		} else if(args[0].equalsIgnoreCase("add")) {
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
			
			if(target == null) {
				sender.sendMessage("off");
				return;
			}
		
			target.setWhitelisted(true);
			
			Msg.sendMessage("&3" + target.getName() + " &bhas been &aAdded &bto whitelist by &3" + sender.getName() + "&b.", Permission.ADMIN_PERMISSION);
		} else if(args[0].equalsIgnoreCase("remove")) {
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
			
			if(target == null) {
				sender.sendMessage("off");
				return;
			}
			
			target.setWhitelisted(false);
			
			Msg.sendMessage("&3" + target.getName() + " &bhas been &cRemoved &bfrom whitelist by &3" + sender.getName() + "&b.", Permission.ADMIN_PERMISSION);
		} else if(args[0].equalsIgnoreCase("clear")) {
			for(OfflinePlayer online : Bukkit.getWhitelistedPlayers()) {
				online.setWhitelisted(false);
				return;
			}
			
			Msg.sendMessage("&3All &bplayers have been unwhitelisted by &3" + sender.getName() + "&b.");
		}	
	}
}
