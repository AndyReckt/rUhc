package me.andyreckt.uhc.commands.arguments;

import java.text.DecimalFormat;

import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.commands.BaseCommand;
import me.andyreckt.uhc.utilties.Color;
import me.andyreckt.uhc.utilties.Msg;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class HealthCommand extends BaseCommand {
	public DecimalFormat getFormatter() {
		return formatter;
	}

	 private DecimalFormat formatter;

	public HealthCommand(UHC plugin) {
		super(plugin);
		
		this.formatter = new DecimalFormat("#.00");
		
		this.command = "health";
		this.forPlayerUseOnly = true;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		
		if(args.length == 0) {
			player.sendMessage(Color.translate("&eYour health is &9" + this.getHeartsLeftString(player.getHealth()) + "&e."));
		} else {
			Player target = Bukkit.getPlayer(args[0]);
		
			if(Msg.checkOffline(player, args[0])) return;
			
			player.sendMessage(Color.translate("&9" + target.getName() + "'s &ehealth is &9" + this.getHeartsLeftString(target.getHealth()) + "&e."));
		}
		
	}
	
	public String getHeartsLeftString(double healthLeft) {
		return Color.translate("&9" + Math.ceil(healthLeft) / 2D + "&4&l " + Msg.HEART);
	}
}
