package me.andyreckt.uhc.commands.arguments;

import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.commands.BaseCommand;
import me.andyreckt.uhc.utilties.Color;
import me.andyreckt.uhc.utilties.Permission;
import org.bukkit.command.CommandSender;

public class NextGameCommand extends BaseCommand {

	public NextGameCommand(UHC plugin) {
		super(plugin);
		
		this.command = "nextgame";
		this.permission = Permission.OP_PERMISSION;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length == 0) {
			sender.sendMessage(Color.translate("&cUsage: /nextuhc <time>"));
			return;
		}

		String time = args[0];

		if(!time.toLowerCase().contains(":")) {
			sender.sendMessage(Color.translate("&cTime must contain : (Example: 16:00)"));
			return;
		}

		plugin.getGameManager().setNextuhc(time);
		sender.sendMessage(Color.translate("&cSet next UHC time to " + time));
	}
}
