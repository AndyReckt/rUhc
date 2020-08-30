package com.thesevenq.uhc.commands.arguments;

import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.commands.BaseCommand;
import com.thesevenq.uhc.player.UHCData;
import com.thesevenq.uhc.utilties.Color;
import com.thesevenq.uhc.utilties.Msg;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KillCountCommand extends BaseCommand {
	
	public KillCountCommand(UHC plugin) {
		super(plugin);
				
		this.command = "killcount";
		this.forPlayerUseOnly = true;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		
		if(args.length == 0) {
			UHCData uhcData = UHCData.getByName(player.getName());
			
			player.sendMessage(Color.translate("&bYour kills: &3" + uhcData.getKills()));
		} else {
			Player target = Bukkit.getPlayer(args[0]);
		
			if(Msg.checkOffline(player, args[0])) return;
			
			UHCData uhcData = UHCData.getByName(target.getName());
			
			if(uhcData.getKillCount().isEmpty()) {
				sender.sendMessage(Color.translate("&c&l" + args[0] + " &cdon't have any kills."));
				return;
			}
			
			sender.sendMessage(Color.translate("&7&m----------------------------"));
			sender.sendMessage(Color.translate("&bKill Count of &3" + args[0] + " &bis &3" + uhcData.getKills() + "&b."));
			sender.sendMessage(Color.translate(""));
			
			for(String dms : uhcData.getKillCount()) {
				sender.sendMessage(Color.translate(" &9&l" + Msg.KRUZIC + " &9" + dms));
			}
			
			sender.sendMessage(Color.translate("&7&m----------------------------"));			
		}
	}
}
