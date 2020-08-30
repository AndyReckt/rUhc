package com.thesevenq.uhc.commands.arguments;

import com.thesevenq.uhc.utilties.Color;
import com.thesevenq.uhc.utilties.Msg;
import com.thesevenq.uhc.utilties.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.commands.BaseCommand;
import com.thesevenq.uhc.listeners.MultiSpawnListener;
import com.thesevenq.uhc.managers.GameManager;
import com.thesevenq.uhc.player.UHCData;
import com.thesevenq.uhc.player.state.GameState;
import com.thesevenq.uhc.utilties.UHCUtils;

public class SpectatorCommand extends BaseCommand {

	public SpectatorCommand(UHC plugin) {
		super(plugin);

		this.command = "spectator";
		this.permission = Permission.STAFF_PERMISSION;
		this.forPlayerUseOnly = true;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		if(args.length == 0) {
			player.sendMessage(Color.translate("&cUsage: /mod <add|remove> <player>"));
		} else {
			if(args[0].equalsIgnoreCase("add")) {
				if(args.length == 1) {
					player.sendMessage(Color.translate("&cUsage: /mod <add|remove> <player>"));
					return;
				}
				
				Player target = Bukkit.getPlayer(args[1]);

				if(Msg.checkOffline(player, args[1])) return;

				if(UHCUtils.isPlayerInSpecMode(target)) {
					player.sendMessage(Color.translate("&c&l" + args[1] + " &cis already in spectator mode."));
					return;
				}

				UHCData uhcData = UHCData.getByName(target.getName());
				
				if(GameManager.getGameState().equals(GameState.PLAYING)) {
					if(uhcData.isAlive()) {
						uhcData.setAlive(false);
					}
				}

				plugin.getSpectatorManager().handleEnable(target);

				Msg.sendMessage("&bStaff Mode of &3" + args[1] + " &bhas been &aEnabled&b by &3" + player.getName() + "&b.", Permission.STAFF_PERMISSION);
			} else if(args[0].equalsIgnoreCase("remove")) {
				if(args.length == 1) {
					player.sendMessage(Color.translate("&cUsage: /mod <add|remove> <player>"));
					return;
				}
				
				if(GameManager.getGameState().equals(GameState.WINNER)) {
					player.sendMessage(Color.translate("&cYou can't do this now."));
					return;
				}
				
				Player target = Bukkit.getPlayer(args[1]);

				if(Msg.checkOffline(player, args[1])) return;

				if(!UHCUtils.isPlayerInSpecMode(target)) {
					player.sendMessage(Color.translate("&c&l" + args[1] + " &cisn't in a spectator mode."));
					return;
				}

				UHCUtils.disableSpecMode(target);

				if(player.hasPermission(Permission.STAFF_PERMISSION)) {
					if(GameManager.getGameState().equals(GameState.PLAYING)) {
						UHCUtils.scatterPlayer(target);
					} else {
						MultiSpawnListener.randomSpawn(target);
					}
				} else {
					target.kickPlayer(Color.translate("&cYou have been kicked by &l" + sender.getName()));
				}

				Msg.sendMessage("&bStaff Mode of &3" + args[1] + " &bhas been &cDisabled&b by &3" + player.getName() + "&b.", Permission.STAFF_PERMISSION);
			}
		}
	}
}
