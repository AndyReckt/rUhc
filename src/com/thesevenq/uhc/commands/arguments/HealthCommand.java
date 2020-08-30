package com.thesevenq.uhc.commands.arguments;

import java.text.DecimalFormat;

import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.commands.BaseCommand;
import com.thesevenq.uhc.utilties.Color;
import com.thesevenq.uhc.utilties.Msg;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lombok.Getter;

public class HealthCommand extends BaseCommand {
	
    @Getter private DecimalFormat formatter;

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
			player.sendMessage(Color.translate("&bYour health is &3" + this.getHeartsLeftString(player.getHealth()) + "&b."));
		} else {
			Player target = Bukkit.getPlayer(args[0]);
		
			if(Msg.checkOffline(player, args[0])) return;
			
			player.sendMessage(Color.translate("&3" + target.getName() + "'s &bhealth is &3" + this.getHeartsLeftString(target.getHealth()) + "&b."));
		}
		
	}
	
	public String getHeartsLeftString(double healthLeft) {
		return Color.translate("&3" + Math.ceil(healthLeft) / 2D + "&4&l " + Msg.HEART);
	}
}
