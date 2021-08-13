package me.andyreckt.uhc.border.worldborder.commands.arguments;

import java.util.List;

import me.andyreckt.uhc.border.worldborder.Config;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class WBWhooshCommand extends WBCmd
{
	public WBWhooshCommand()
	{
		name = permission = "whoosh";
		minParams = maxParams = 1;

		addCmdExample(nameEmphasized() + "<on|off> - turn knockback effect on or off.");
		helpText = "Default value: on. This will show a particle effect and play a sound where a player is knocked " +
			"back from the border.";
	}

	@Override
	public void cmdStatus(CommandSender sender)
	{
		sender.sendMessage(C_HEAD + "\"Whoosh\" knockback effect is " + enabledColored(Config.whooshEffect()) + C_HEAD + ".");
	}

	@Override
	public void execute(CommandSender sender, Player player, List<String> params, String worldName)
	{
		Config.setWhooshEffect(strAsBool(params.get(0)));

		if (player != null)
		{
			Config.log((Config.whooshEffect() ? "Enabled" : "Disabled") + " \"whoosh\" knockback effect at the command of player \"" + player.getName() + "\".");
			cmdStatus(sender);
		}
	}
}
