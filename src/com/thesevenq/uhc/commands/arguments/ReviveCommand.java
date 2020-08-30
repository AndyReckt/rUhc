package com.thesevenq.uhc.commands.arguments;

import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.commands.BaseCommand;
import com.thesevenq.uhc.utilties.Color;
import com.thesevenq.uhc.utilties.Msg;
import com.thesevenq.uhc.utilties.Permission;
import com.thesevenq.uhc.utilties.UHCUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.thesevenq.uhc.player.UHCData;

public class ReviveCommand extends BaseCommand {

	public ReviveCommand(UHC plugin) {
		super(plugin);
		
		this.command = "revive";
		this.permission = Permission.STAFF_PLUS_PERMISSION;
		this.forPlayerUseOnly = true;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length == 0) {
			sender.sendMessage(Color.translate("&cUsage: /revive <player>"));
		} else {
			Player target = Bukkit.getPlayer(args[0]);
			
			if(Msg.checkOffline(sender, args[0])) return;
			
			UHCData uhcData = UHCData.getByName(target.getName());
			
			if(uhcData == null) {
				sender.sendMessage(Color.translate("&c&l" + target.getName() + " &cisn't in database."));
				return;
			}
			
			if(uhcData.isAlive()) {
				sender.sendMessage(Color.translate("&c&l" + target.getName() + " &cis still alive."));
				return;
			}
            
            if(uhcData.getRespawnLocation() == null) {
                sender.sendMessage(Color.translate("&cCould not found spawn location for &l" + target.getName() + "&c."));
                return;
            }
            
            target.setWhitelisted(true);
            
			this.revivePlayer(target);
			
			for(Player online : Bukkit.getOnlinePlayers()) {
				if(!online.canSee(target)) {
					online.showPlayer(target);
				}
			}
            
            sender.sendMessage(Color.translate("&bYou have revived &3" + target.getName() + "&b."));
            
            Msg.sendMessage("&3" + target.getName() + " &bhas been revived by &3" + sender.getName(), Permission.STAFF_PERMISSION);
		}
	}
	
	public void revivePlayer(Player target) {
		if(UHCUtils.isPlayerInSpecMode(target)) {
			UHCUtils.disableSpecMode(target);
		}

		for (Player on : Bukkit.getOnlinePlayers()) {
			if(UHCUtils.isPlayerInSpecMode(on)) {
				target.hidePlayer(on);
			}
		}

		UHCData uhcData = UHCData.getByName(target.getName());

		target.setHealth(20.0);
		target.setFoodLevel(20);
		target.teleport(uhcData.getRespawnLocation());
		target.setNoDamageTicks(60);
		target.setGameMode(GameMode.SURVIVAL);
		target.setFlying(false);
		target.setAllowFlight(false);

		target.getInventory().setArmorContents(uhcData.getArmor());
		target.getInventory().setContents(uhcData.getItems());
		target.setLevel(uhcData.getLevel());

		uhcData.setAlive(true);
	}

}
