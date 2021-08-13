package me.andyreckt.uhc.commands.arguments;

import me.andyreckt.uhc.utilties.Color;
import me.andyreckt.uhc.utilties.NumberUtils;
import me.andyreckt.uhc.utilties.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.border.Border;
import me.andyreckt.uhc.commands.BaseCommand;


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
