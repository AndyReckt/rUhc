package com.thesevenq.uhc.listeners;

import com.thesevenq.uhc.utilties.BaseListener;
import com.thesevenq.uhc.utilties.Color;
import com.thesevenq.uhc.utilties.Permission;
import com.thesevenq.uhc.utilties.UHCUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import com.thesevenq.uhc.player.UHCData;

public class XrayListener extends BaseListener implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if(event.isCancelled() || Bukkit.getOnlinePlayers().size() > 350) return;

		Player player = event.getPlayer();
		
		if(player.getGameMode() == GameMode.CREATIVE) return;

		UHCData uhcData = UHCData.getByName(player.getName());
		
		if(uhcData != null) {
			int uhcDiamonds = uhcData.getDiamondsMined() + 1;
			int uhcSpawner = uhcData.getSpawnersMined() + 1;
			
			Block block = event.getBlock();
			
			switch(block.getType()) {
			case DIAMOND_ORE:
				for(Player online : Bukkit.getOnlinePlayers()) {
					if(UHCUtils.isPlayerInSpecMode(online)) {
						if(plugin.getGameManager().getOreAlerts().contains(online.getUniqueId())) {
							if(online.hasPermission(Permission.STAFF_PERMISSION)) {
								online.sendMessage(Color.translate("&7[&b&l☢&7] &3" + player.getName() + " &7found &bDIAMOND_ORE&7 [&b&l" + uhcDiamonds + "&7]"));
							}
						}
					}
				}
				break;
			case MOB_SPAWNER:	
				for(Player online : Bukkit.getOnlinePlayers()) {
					if(UHCUtils.isPlayerInSpecMode(online)) {
						if(plugin.getGameManager().getOreAlerts().contains(online.getUniqueId())) {
							if(online.hasPermission(Permission.STAFF_PERMISSION)) {
								online.sendMessage(Color.translate("&7[&c&l☢&7] &3" + player.getName() + " &7found &cMOB_SPAWNER&7 [&c&l" + uhcSpawner + "&7]"));
							}
						}
					}
				}
				break;
			default:
				break;
			}	
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		if(!player.hasPermission(Permission.STAFF_PERMISSION)) return;
		
		plugin.getGameManager().getOreAlerts().add(player.getUniqueId());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		if(!plugin.getGameManager().getOreAlerts().contains(player.getUniqueId())) return;

		plugin.getGameManager().getOreAlerts().remove(player.getUniqueId());
	}
}
