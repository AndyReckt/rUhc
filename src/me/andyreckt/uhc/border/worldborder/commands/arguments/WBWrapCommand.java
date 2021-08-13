package me.andyreckt.uhc.border.worldborder.commands.arguments;

import java.util.List;

import me.andyreckt.uhc.border.worldborder.BorderData;
import me.andyreckt.uhc.border.worldborder.Config;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class WBWrapCommand extends WBCmd
{
	public WBWrapCommand()
	{
		name = permission = "wrap";
		minParams = 1;
		maxParams = 2;

		addCmdExample(nameEmphasized() + "{world} <on|off> - can make border crossings wrap.");
		helpText = "When border wrapping is enabled for a world, players will be sent around to the opposite edge " +
			"of the border when they cross it instead of being knocked back. [world] is optional for players and " +
			"defaults to the world the player is in.";
	}

	@Override
	public void execute(CommandSender sender, Player player, List<String> params, String worldName)
	{
		if (player == null && params.size() == 1)
		{
			sendErrorAndHelp(sender, "When running this command from console, you must specify a world.");
			return;
		}

		boolean wrap = false;

		// world and wrap on/off specified
		if (params.size() == 2)
		{
			worldName = params.get(0);
			wrap = strAsBool(params.get(1));
		}
		// no world specified, just wrap on/off
		else
		{
			worldName = player.getWorld().getName();
			wrap = strAsBool(params.get(0));
		}

		BorderData border = Config.Border(worldName);
		if (border == null)
		{
			sendErrorAndHelp(sender, "This world (\"" + worldName + "\") doesn't have a border set.");
			return;
		}

		border.setWrapping(wrap);
		Config.setBorder(worldName, border, false);

		sender.sendMessage("Border for world \"" + worldName + "\" is now set to " + (wrap ? "" : "not ") + "wrap around.");
	}
}
