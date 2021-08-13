package me.andyreckt.uhc.border.worldborder.commands.arguments;

import java.util.List;
import java.util.Set;

import me.andyreckt.uhc.border.worldborder.Config;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class WBListCommand extends WBCmd
{
	public WBListCommand()
	{
		name = permission = "list";
		minParams = maxParams = 0;

		addCmdExample(nameEmphasized() + "- show border information for all worlds.");
		helpText = "This command will list full information for every border you have set including position, " +
			"radius, and shape. The default border shape will also be indicated.";
	}

	@Override
	public void execute(CommandSender sender, Player player, List<String> params, String worldName)
	{
		sender.sendMessage("Default border shape for all worlds is \"" + Config.ShapeName() + "\".");

		Set<String> list = Config.BorderDescriptions();

		if (list.isEmpty())
		{
			sender.sendMessage("There are no borders currently set.");
			return;
		}

		for(String borderDesc : list)
		{
			sender.sendMessage(borderDesc);
		}
	}
}
