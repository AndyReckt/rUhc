
package com.thesevenq.uhc.commands.arguments;

import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.commands.BaseCommand;
import com.thesevenq.uhc.player.UHCData;
import com.thesevenq.uhc.utilties.Color;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand extends BaseCommand {

	public StatsCommand(UHC plugin) {
		super(plugin);

		this.command = "stats";
		this.forPlayerUseOnly = true;
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		
		if(args.length == 0) {
			if(!plugin.getGameManager().isStats()) {
				sender.sendMessage(Color.translate("&cStats is currently disabled."));
				return;
			}

			UHCData data = UHCData.getByName(player.getName());

			player.sendMessage(Color.translate("&bYour stats:"));
			player.sendMessage(Color.translate("&bWins: &3" + data.getWins()));
			player.sendMessage(Color.translate("&bPlayed: &3" + data.getPlayed()));
			player.sendMessage(Color.translate("&bKills: &3" + data.getTotalKills()));
			player.sendMessage(Color.translate("&bDeaths: &3" + data.getDeaths()));
			player.sendMessage(Color.translate("&bKD: &3" + data.getKD()));
			player.sendMessage(Color.translate("&bKill Streak: &3" + data.getKillStreak()));
			player.sendMessage(Color.translate("&bDiamonds Mined: &3" + data.getTotalDiamondsMined()));
		} else {
            OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[0]);
            
            if(!target.hasPlayedBefore()) {
                sender.sendMessage(ChatColor.RED + "This player never played on this server.");
                return;
            }

			UHCData data = UHCData.getByName(target.getName());

			player.sendMessage(Color.translate("&bStats of &3" + args[0] + "&b!"));
			player.sendMessage(Color.translate("&bWins: &3" + data.getWins()));
			player.sendMessage(Color.translate("&bPlayed: &3" + data.getPlayed()));
			player.sendMessage(Color.translate("&bKills: &3" + data.getTotalKills()));
			player.sendMessage(Color.translate("&bDeaths: &3" + data.getDeaths()));
			player.sendMessage(Color.translate("&bKD: &3" + data.getKD()));
			player.sendMessage(Color.translate("&bKill Streak: &3" + data.getKillStreak()));
			player.sendMessage(Color.translate("&bDiamonds Mined: &3" + data.getTotalDiamondsMined()));
		}
		
	}
}