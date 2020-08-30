package com.thesevenq.uhc.commands.arguments;

import com.thesevenq.uhc.utilties.Msg;
import com.thesevenq.uhc.utilties.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.commands.BaseCommand;
import com.thesevenq.uhc.managers.InventoryManager;

public class ConfigCommand extends BaseCommand {

	public ConfigCommand(UHC plugin) {
		super(plugin);

		this.command = "config";
		this.forPlayerUseOnly = true;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		if(args.length == 0) {
			player.openInventory(InventoryManager.uhcPlayerSettings);
		} else {
			if(args[0].equalsIgnoreCase("staff")) {
				if(player.hasPermission(Permission.OP_PERMISSION)) {
					player.openInventory(InventoryManager.uhcSettings);
				} else {
					sender.sendMessage(Msg.NO_PERMISSION);
				}
			}
		}
	}
}
