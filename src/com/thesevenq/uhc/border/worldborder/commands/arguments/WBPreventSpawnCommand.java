package com.thesevenq.uhc.border.worldborder.commands.arguments;

import java.util.List;

import com.thesevenq.uhc.border.worldborder.Config;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.thesevenq.uhc.border.worldborder.Config;

public class WBPreventSpawnCommand extends WBCmd {

	public WBPreventSpawnCommand() {
		name = permission = "preventmobspawn";
		minParams = maxParams = 1;

		addCmdExample(nameEmphasized() + "<on|off> - stop mob spawning past border.");
		helpText = "Default value: off. When enabled, this setting will prevent mobs from naturally spawning outside the world's border.";
	}
	
	@Override
	public void cmdStatus(CommandSender sender)
	{
		sender.sendMessage(C_HEAD + "Prevention of mob spawning outside the border is " + enabledColored(Config.preventMobSpawn()) + C_HEAD + ".");
	}

	@Override
	public void execute(CommandSender sender, Player player, List<String> params, String worldName)
	{
		if (player != null)
		{
			Config.log((Config.preventMobSpawn() ? "Enabled" : "Disabled") + " preventmobspawn at the command of player \"" + player.getName() + "\".");
			cmdStatus(sender);
		}
	}
}
