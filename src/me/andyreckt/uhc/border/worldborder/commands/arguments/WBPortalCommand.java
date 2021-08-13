package me.andyreckt.uhc.border.worldborder.commands.arguments;

import java.util.List;

import me.andyreckt.uhc.border.worldborder.Config;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class WBPortalCommand extends WBCmd
{
	public WBPortalCommand()
	{
		name = permission = "portal";
		minParams = maxParams = 1;

		addCmdExample(nameEmphasized() + "<on|off> - turn portal redirection on or off.");
		helpText = "Default value: on. This feature monitors new portal creation and changes the target new portal " +
			"location if it is outside of the border. Try disabling this if you have problems with other plugins " +
			"related to portals.";
	}

	@Override
	public void cmdStatus(CommandSender sender)
	{
		sender.sendMessage(C_HEAD + "Portal redirection is " + enabledColored(Config.portalRedirection()) + C_HEAD + ".");
	}

	@Override
	public void execute(CommandSender sender, Player player, List<String> params, String worldName)
	{
		Config.setPortalRedirection(strAsBool(params.get(0)));

		if (player != null)
		{
			Config.log((Config.portalRedirection() ? "Enabled" : "Disabled") + " portal redirection at the command of player \"" + player.getName() + "\".");
			cmdStatus(sender);
		}
	}
}
