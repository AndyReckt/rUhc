package com.thesevenq.uhc.border.worldborder.commands.arguments;

import java.util.List;

import com.thesevenq.uhc.border.worldborder.Config;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.thesevenq.uhc.border.worldborder.Config;




public class WBDelayCommand extends WBCmd
{
	public WBDelayCommand()
	{
		name = permission = "delay";
		minParams = maxParams = 1;

		addCmdExample(nameEmphasized() + "<amount> - time between border checks.");
		helpText = "Default value: 5. The <amount> is in server ticks, of which there are roughly 20 every second, each " +
			"tick taking ~50ms. The default value therefore has border checks run about 4 times per second.";
	}

	@Override
	public void cmdStatus(CommandSender sender)
	{
		int delay = Config.TimerTicks();
		sender.sendMessage(C_HEAD + "Timer delay is set to " + delay + " tick(s). That is roughly " + (delay * 50) + "ms.");
	}

	@Override
	public void execute(CommandSender sender, Player player, List<String> params, String worldName)
	{
		int delay = 0;
		try
		{
			delay = Integer.parseInt(params.get(0));
			if (delay < 1)
				throw new NumberFormatException();
		}
		catch(NumberFormatException ex)
		{
			sendErrorAndHelp(sender, "The timer delay must be an integer of 1 or higher.");
			return;
		}

		Config.setTimerTicks(delay);

		if (player != null)
			cmdStatus(sender);
	}
}
