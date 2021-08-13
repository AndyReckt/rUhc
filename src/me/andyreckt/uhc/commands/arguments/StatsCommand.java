
package me.andyreckt.uhc.commands.arguments;

import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.commands.BaseCommand;
import me.andyreckt.uhc.player.UHCData;
import me.andyreckt.uhc.utilties.Color;
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

			player.sendMessage(Color.translate("&eYour stats:"));
			player.sendMessage(Color.translate("&eWins: &9" + data.getWins()));
			player.sendMessage(Color.translate("&ePlayed: &9" + data.getPlayed()));
			player.sendMessage(Color.translate("&eKills: &9" + data.getTotalKills()));
			player.sendMessage(Color.translate("&eDeaths: &9" + data.getDeaths()));
			player.sendMessage(Color.translate("&eKD: &9" + data.getKD()));
			player.sendMessage(Color.translate("&eKill Streak: &9" + data.getKillStreak()));
			player.sendMessage(Color.translate("&eDiamonds Mined: &9" + data.getTotalDiamondsMined()));
		} else {
            OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[0]);
            
            if(!target.hasPlayedBefore()) {
                sender.sendMessage(ChatColor.RED + "This player never played on this server.");
                return;
            }

			UHCData data = UHCData.getByName(target.getName());

			player.sendMessage(Color.translate("&eStats of &9" + args[0] + "&e!"));
			player.sendMessage(Color.translate("&eWins: &9" + data.getWins()));
			player.sendMessage(Color.translate("&ePlayed: &9" + data.getPlayed()));
			player.sendMessage(Color.translate("&eKills: &9" + data.getTotalKills()));
			player.sendMessage(Color.translate("&eDeaths: &9" + data.getDeaths()));
			player.sendMessage(Color.translate("&eKD: &9" + data.getKD()));
			player.sendMessage(Color.translate("&eKill Streak: &9" + data.getKillStreak()));
			player.sendMessage(Color.translate("&eDiamonds Mined: &9" + data.getTotalDiamondsMined()));
		}
		
	}
}