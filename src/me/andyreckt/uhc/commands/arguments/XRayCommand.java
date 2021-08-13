package me.andyreckt.uhc.commands.arguments;

import me.andyreckt.uhc.utilties.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.commands.BaseCommand;

public class XRayCommand extends BaseCommand {

	public XRayCommand(UHC plugin) {
		super(plugin);
		
		this.command = "xray";
		this.permission = Permission.STAFF_PERMISSION;
		this.forPlayerUseOnly = true;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		
		plugin.getGameManager().handleOreAlerts(player);
	}
}
