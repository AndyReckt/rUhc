package com.thesevenq.uhc.border.worldborder.commands.arguments;

import java.util.List;

import com.thesevenq.uhc.border.worldborder.Config;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class WBDynmapCommand extends WBCmd
{
	public WBDynmapCommand()
	{
		name = permission = "dynmap";
		minParams = maxParams = 1;

		addCmdExample(nameEmphasized() + "<on|off> - turn DynMap border display on or off.");
		helpText = "Default value: on. If you are running the DynMap plugin and this setting is enabled, all borders will " +
			"be visually shown in DynMap.";
	}

	@Override
	public void cmdStatus(CommandSender sender)
	{
		sender.sendMessage(C_HEAD + "DynMap border display is " + enabledColored(Config.DynmapBorderEnabled()) + C_HEAD + ".");
	}

	@Override
	public void execute(CommandSender sender, Player player, List<String> params, String worldName)
	{
		Config.setDynmapBorderEnabled(strAsBool(params.get(0)));

		if (player != null)
		{
			cmdStatus(sender);
			Config.log((Config.DynmapBorderEnabled() ? "Enabled" : "Disabled") + " DynMap border display at the command of player \"" + player.getName() + "\".");
		}
	}
}
