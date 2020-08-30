package com.thesevenq.uhc.commands.arguments;

import com.thesevenq.uhc.utilties.Color;
import com.thesevenq.uhc.utilties.NumberUtils;
import com.thesevenq.uhc.utilties.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.border.Border;
import com.thesevenq.uhc.commands.BaseCommand;


public class BorderCommand extends BaseCommand {

	public BorderCommand(UHC plugin) {
		super(plugin);

		this.command = "border";
		this.permission = Permission.OP_PERMISSION;
		this.forPlayerUseOnly = true;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		if(args.length == 0) {
			player.sendMessage(Color.translate("&cUsage: /border <amount>"));
		} else {
			if(!NumberUtils.isInteger(args[0])) {
				player.sendMessage(Color.translate("&cThis must be an integer."));
				return;
			}
			
			int amount = Integer.parseInt(args[0]);

			if(amount > 3000) {
				player.sendMessage(Color.translate("&cBorder limit is 3000."));
				return;
			}

			new Border(Bukkit.getWorld("uhc_world"), amount);
		}
	}
}
