package me.andyreckt.uhc.commands.arguments;

import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.commands.BaseCommand;
import me.andyreckt.uhc.player.UHCData;
import me.andyreckt.uhc.utilties.Color;
import me.andyreckt.uhc.utilties.Msg;
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
			
			player.sendMessage(Color.translate("&eYour kills: &9" + uhcData.getKills()));
		} else {
			Player target = Bukkit.getPlayer(args[0]);
		
			if(Msg.checkOffline(player, args[0])) return;
			
			UHCData uhcData = UHCData.getByName(target.getName());
			
			if(uhcData.getKillCount().isEmpty()) {
				sender.sendMessage(Color.translate("&c&l" + args[0] + " &cdon't have any kills."));
				return;
			}
			
			sender.sendMessage(Color.translate("&7&m----------------------------"));
			sender.sendMessage(Color.translate("&eKill Count of &9" + args[0] + " &eis &9" + uhcData.getKills() + "&e."));
			sender.sendMessage(Color.translate(""));
			
			for(String dms : uhcData.getKillCount()) {
				sender.sendMessage(Color.translate(" &9&l" + Msg.KRUZIC + " &9" + dms));
			}
			
			sender.sendMessage(Color.translate("&7&m----------------------------"));			
		}
	}
}
