package me.andyreckt.uhc.commands.arguments;

import me.andyreckt.uhc.utilties.Msg;
import me.andyreckt.uhc.utilties.Permission;
import me.andyreckt.uhc.managers.InventoryManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.commands.BaseCommand;

public class ScenariosCommand extends BaseCommand {

	public ScenariosCommand(UHC plugin) {
		super(plugin);
		
		this.command = "scenarios";
		this.forPlayerUseOnly = true;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		
		if(args.length == 0) {
			InventoryManager.setScenarioInfo(player);
		} else {
			if(args[0].equalsIgnoreCase("listall") || args[0].equalsIgnoreCase("all")) {
				if(!player.hasPermission(Permission.OP_PERMISSION)) {
					player.sendMessage(Msg.NO_PERMISSION);
					return;
				}
				player.openInventory(InventoryManager.toggleScenarios);
			}
		}
	}
}
