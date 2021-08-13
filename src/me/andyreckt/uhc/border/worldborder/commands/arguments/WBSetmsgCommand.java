package me.andyreckt.uhc.border.worldborder.commands.arguments;

import java.util.List;

import me.andyreckt.uhc.border.worldborder.Config;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class WBSetmsgCommand extends WBCmd
{
	public WBSetmsgCommand()
	{
		name = permission = "setmsg";
		minParams = 1;

		addCmdExample(nameEmphasized() + "<text> - set border message.");
		helpText = "Default value: \"&cYou have reached the edge of this world.\". This command lets you set the message shown to players who are knocked back from the border.";
	}

	@Override
	public void cmdStatus(CommandSender sender)
	{
		sender.sendMessage(C_HEAD + "Border message is set to:");
		sender.sendMessage(Config.MessageRaw());
		sender.sendMessage(C_HEAD + "Formatted border message:");
		sender.sendMessage(Config.Message());
	}

	@Override
	public void execute(CommandSender sender, Player player, List<String> params, String worldName)
	{
		StringBuilder message = new StringBuilder();
		boolean first = true;
		for (String param : params)
		{
			if (!first)
				message.append(" ");
			message.append(param);
			first = false;
		}

		Config.setMessage(message.toString());

		cmdStatus(sender);
	}
}
