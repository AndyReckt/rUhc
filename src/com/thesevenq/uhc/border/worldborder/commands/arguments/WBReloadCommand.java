package com.thesevenq.uhc.border.worldborder.commands.arguments;

import java.util.List;

import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.border.worldborder.Config;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.border.worldborder.Config;




public class WBReloadCommand extends WBCmd
{
	public WBReloadCommand()
	{
		name = permission = "reload";
		minParams = maxParams = 0;

		addCmdExample(nameEmphasized() + "- re-load data from config.yml.");
		helpText = "If you make manual changes to config.yml while the server is running, you can use this command " +
			"to make WorldBorder load the changes without needing to restart the server.";
	}

	@Override
	public void execute(CommandSender sender, Player player, List<String> params, String worldName)
	{
		if (player != null)
			Config.log("Reloading config file at the command of player \"" + player.getName() + "\".");

		Config.load(UHC.getInstance(), true);

		if (player != null)
			sender.sendMessage("WorldBorder configuration reloaded.");
	}
}
