package me.andyreckt.uhc.commands.arguments;

import me.andyreckt.uhc.utilties.Msg;
import me.andyreckt.uhc.utilties.Permission;
import me.andyreckt.uhc.managers.InventoryManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.commands.BaseCommand;

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
