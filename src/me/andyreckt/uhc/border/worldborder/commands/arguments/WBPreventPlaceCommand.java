package me.andyreckt.uhc.border.worldborder.commands.arguments;

import java.util.List;

import me.andyreckt.uhc.border.worldborder.Config;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WBPreventPlaceCommand extends WBCmd {

	public WBPreventPlaceCommand() {
		name = permission = "preventblockplace";
		minParams = maxParams = 1;

		addCmdExample(nameEmphasized() + "<on|off> - stop block placement past border.");
		helpText = "Default value: off. When enabled, this setting will prevent players from placing blocks outside the world's border.";
	}
	
	@Override
	public void cmdStatus(CommandSender sender)
	{
		sender.sendMessage(C_HEAD + "Prevention of block placement outside the border is " + enabledColored(Config.preventBlockPlace()) + C_HEAD + ".");
	}

	@Override
	public void execute(CommandSender sender, Player player, List<String> params, String worldName)
	{
		if (player != null)
		{
			Config.log((Config.preventBlockPlace() ? "Enabled" : "Disabled") + " preventblockplace at the command of player \"" + player.getName() + "\".");
			cmdStatus(sender);
		}
	}
}
