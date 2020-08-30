package com.thesevenq.uhc.commands.arguments;

import com.thesevenq.uhc.utilties.Msg;
import com.thesevenq.uhc.utilties.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.commands.BaseCommand;
import com.thesevenq.uhc.managers.InventoryManager;

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
