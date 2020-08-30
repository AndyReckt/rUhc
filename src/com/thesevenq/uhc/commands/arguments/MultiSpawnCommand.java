package com.thesevenq.uhc.commands.arguments;

import com.thesevenq.uhc.utilties.Color;
import com.thesevenq.uhc.utilties.Permission;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.commands.BaseCommand;

import java.io.IOException;

public class MultiSpawnCommand extends BaseCommand {

	public MultiSpawnCommand(UHC plugin) {
		super(plugin);
		
		this.command = "multispawn";
		this.permission = Permission.OP_PERMISSION;
		this.forPlayerUseOnly = true;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		
		if(args.length == 0) {
			this.sendUsage(sender);
		} else {
			if(args[0].equalsIgnoreCase("create")) {
		        Location location = player.getLocation();
		        String name = args[1];

				UHC.getInstance().getUtiltiesConfig().set("Spawns." + name + ".X", location.getBlockX());
				UHC.getInstance().getUtiltiesConfig().set("Spawns." + name + ".Y", location.getBlockY());
				UHC.getInstance().getUtiltiesConfig().set("Spawns." + name + ".Z", location.getBlockZ());
				UHC.getInstance().getUtiltiesConfig().set("Spawns." + name + ".World", location.getWorld().getName());

				try {
					plugin.getUtiltiesConfig().save(plugin.getUtiltiesConfig().getFile());
				} catch (IOException e) {
					e.printStackTrace();
				}
		        
		        sender.sendMessage(Color.translate("&aYou have created spawn point named &l" + name + "&a."));
			} else if(args[0].equalsIgnoreCase("delete")) {
				String name = args[1];
				
				if(!UHC.getInstance().getUtiltiesConfig().isSet("Spawns." + name)) {
					sender.sendMessage(Color.translate("&cThe spawn point named &l" + name + " &cdoesn't exists."));
					return;
				}
				
				UHC.getInstance().getUtiltiesConfig().set("Spawns." + name, null);
				sender.sendMessage(Color.translate("&aYou have deleted spawn point named &l" + name + "&a."));
			} else if(args[0].equalsIgnoreCase("createuhcpractice")) {
		        Location location = player.getLocation();
		        String name = args[1];

				UHC.getInstance().getUtiltiesConfig().set("UHCPracticeSpawns." + name + ".X", location.getBlockX());
				UHC.getInstance().getUtiltiesConfig().set("UHCPracticeSpawns." + name + ".Y", location.getBlockY());
				UHC.getInstance().getUtiltiesConfig().set("UHCPracticeSpawns." + name + ".Z", location.getBlockZ());
				UHC.getInstance().getUtiltiesConfig().set("UHCPracticeSpawns." + name + ".World", location.getWorld().getName());

				try {
					plugin.getUtiltiesConfig().save(plugin.getUtiltiesConfig().getFile());
				} catch (IOException e) {
					e.printStackTrace();
				}

		        sender.sendMessage(Color.translate("&aYou have created UHCPractice point named &l" + name + "&a."));
			} else if(args[0].equalsIgnoreCase("deleteuhcpractice")) {
				String name = args[1];
				
				if(!UHC.getInstance().getUtiltiesConfig().isSet("UHCPracticeSpawns." + name)) {
					sender.sendMessage(Color.translate("&cThe UHCPractice point named &l" + name + " &cdoesn't exists."));
					return;
				}
				
				UHC.getInstance().getUtiltiesConfig().set("UHCPracticeSpawns." + name, null);
				sender.sendMessage(Color.translate("&aYou have deleted UHCPractice point named &l" + name + "&a."));
			}
		}
	}
	
	public void sendUsage(CommandSender sender) {
		sender.sendMessage(Color.translate("&cMultiSpawn - Help Commands:"));
		sender.sendMessage(Color.translate("&c/multispawn create <name> - Create spawn point."));
		sender.sendMessage(Color.translate("&c/multispawn delete <name>. - Delete spawn point."));
		sender.sendMessage(Color.translate("&c/multispawn createuhcpractice <name> - Create UHCPractice point."));
		sender.sendMessage(Color.translate("&c/multispawn deleteuhcpractice <name>. - Delete UHCPractice point."));
	}

}
