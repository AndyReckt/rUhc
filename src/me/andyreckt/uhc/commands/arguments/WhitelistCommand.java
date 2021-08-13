package me.andyreckt.uhc.commands.arguments;

import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.commands.BaseCommand;
import me.andyreckt.uhc.utilties.Color;
import me.andyreckt.uhc.utilties.Msg;
import me.andyreckt.uhc.utilties.Permission;
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
			
			Msg.sendMessage("&9Whitelist &ehas been &aEnabled&e.");
		} else if(args[0].equalsIgnoreCase("off")) {
			Bukkit.setWhitelist(false);
			
			Msg.sendMessage("&9Whitelist &ehas been &cDisabled&e.");
		} else if(args[0].equalsIgnoreCase("add")) {
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
			
			if(target == null) {
				sender.sendMessage("off");
				return;
			}
		
			target.setWhitelisted(true);
			
			Msg.sendMessage("&9" + target.getName() + " &ehas been &aAdded &eto whitelist by &9" + sender.getName() + "&e.", Permission.ADMIN_PERMISSION);
		} else if(args[0].equalsIgnoreCase("remove")) {
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
			
			if(target == null) {
				sender.sendMessage("off");
				return;
			}
			
			target.setWhitelisted(false);
			
			Msg.sendMessage("&9" + target.getName() + " &ehas been &cRemoved &efrom whitelist by &9" + sender.getName() + "&e.", Permission.ADMIN_PERMISSION);
		} else if(args[0].equalsIgnoreCase("clear")) {
			for(OfflinePlayer online : Bukkit.getWhitelistedPlayers()) {
				online.setWhitelisted(false);
				return;
			}
			
			Msg.sendMessage("&9All &eplayers have been unwhitelisted by &9" + sender.getName() + "&e.");
		}	
	}
}
