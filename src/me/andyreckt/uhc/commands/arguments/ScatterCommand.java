package me.andyreckt.uhc.commands.arguments;

import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.commands.BaseCommand;
import me.andyreckt.uhc.player.UHCData;
import me.andyreckt.uhc.utilties.Color;
import me.andyreckt.uhc.utilties.Msg;
import me.andyreckt.uhc.utilties.Permission;
import me.andyreckt.uhc.utilties.UHCUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class ScatterCommand extends BaseCommand {

	public ScatterCommand(UHC plugin) {
		super(plugin);
		
		this.command = "scatter";
		this.permission = Permission.STAFF_PLUS_PERMISSION;
		this.forPlayerUseOnly = true;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		
		if(args.length == 0) {
			sender.sendMessage(Color.translate("&cUsage: /scatter <player>"));
		} else {
			Player target = Bukkit.getPlayer(args[0]);
			
			if(Msg.checkOffline(sender, args[0])) return;
			
			UHCData uhcData = UHCData.getByName(player.getName());
			
			if(uhcData == null) {
				sender.sendMessage(Color.translate("&c&l" + target.getName() + " &cisn't in database."));
				return;
			}
            
			if(UHCUtils.isPlayerInSpecMode(target)) {
				UHCUtils.disableSpecMode(target);
			}
			
			new BukkitRunnable() {
				public void run() {
					UHCUtils.scatterPlayer(target);
					
			    	Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> UHC.getInstance().getHorseManager().unsitPlayer(target), 3L);
				}
			}.runTaskLater(UHC.getInstance(), 60L);
			
			for(Player online : Bukkit.getOnlinePlayers()) {
				if(!online.canSee(target)) {
					online.showPlayer(target);
				}
			}
			            
            sender.sendMessage(Color.translate("&eYou have scattered &9" + target.getName() + "&e."));

            Msg.sendMessage("&9" + target.getName() + " &ehas been scattered by &9" + sender.getName(), Permission.STAFF_PERMISSION);
		}
	}
}
