package me.andyreckt.uhc.border.worldborder.commands.arguments;

import java.util.List;

import me.andyreckt.uhc.border.worldborder.Config;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class WBDebugCommand extends WBCmd
{
	public WBDebugCommand()
	{
		name = permission = "debug";
		minParams = maxParams = 1;

		addCmdExample(nameEmphasized() + "<on|off> - turn console debug output on or off.");
		helpText = "Default value: off. Debug mode will show some extra debugging data in the server console/log when " +
			"players are knocked back from the border or are teleported.";
	}

	@Override
	public void cmdStatus(CommandSender sender)
	{
		sender.sendMessage(C_HEAD + "Debug mode is " + enabledColored(Config.Debug()) + C_HEAD + ".");
	}

	@Override
	public void execute(CommandSender sender, Player player, List<String> params, String worldName)
	{
		Config.setDebug(strAsBool(params.get(0)));

		if (player != null)
		{
			Config.log((Config.Debug() ? "Enabled" : "Disabled") + " debug output at the command of player \"" + player.getName() + "\".");
			cmdStatus(sender);
		}
	}
}
