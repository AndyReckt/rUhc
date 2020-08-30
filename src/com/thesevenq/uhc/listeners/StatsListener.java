package com.thesevenq.uhc.listeners;

import com.thesevenq.uhc.managers.GameManager;
import com.thesevenq.uhc.managers.PlayerManager;
import com.thesevenq.uhc.utilties.BaseListener;
import com.thesevenq.uhc.utilties.Color;
import com.thesevenq.uhc.utilties.Msg;
import com.thesevenq.uhc.utilties.events.GameWinEvent;
import com.thesevenq.uhc.utilties.events.GameWinTeamEvent;
import org.apache.commons.lang.time.FastDateFormat;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import com.thesevenq.uhc.commands.arguments.MLGCommand;
import com.thesevenq.uhc.player.UHCData;
import com.thesevenq.uhc.player.state.GameState;

import java.util.Locale;
import java.util.TimeZone;

public class StatsListener extends BaseListener implements Listener {
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onGameWin(GameWinEvent event) {
		Msg.sendMessage("&7&m-------------------------------------------");
		Msg.sendMessage("");
		Msg.sendMessage("&bCongratulations to &3" + event.getWinner() + " &bfor winning the UHC.");
		Msg.sendMessage("");
		Msg.sendMessage("&7&m-------------------------------------------");

		boolean enabled = plugin.getGameManager().isStats();
		
		if(enabled) {
			event.getUhcData().setWins(event.getUhcData().getWins() + 1);

			Bukkit.getOnlinePlayers().forEach(player -> {
				UHCData.getByName(player.getName()).save();

				if(UHCData.getByName(player.getName()).isAlive()) {
					player.setHealth(20.0);
					player.setNoDamageTicks(150);
				}
			});
		}

		GameManager.setGameState(GameState.WINNER);
		
		if(event.getUhcData().isAlive()) {
		    Player player = Bukkit.getPlayer(event.getUhcData().getName());
		    
		    if(player != null) {
			    MLGCommand.allowedMLGPlayers.add(player.getUniqueId());
			    
			    player.sendMessage(Color.translate("&bYou have &320 seconds&b to type /mlg to try the MLG water bucket challenge."));
		    }
	    }
		
		new BukkitRunnable() {
			public void run() {
				MLGCommand.doMLG();
			}
		}.runTaskLater(plugin, 20L * 20);

		Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "game forcestop"), 20L * 30);
	}

	@EventHandler(priority = EventPriority.HIGH)
    public void onGameWinTeam(GameWinTeamEvent event) {
        String names = "";
        
        for(String uuid : event.getNames()) {
            names = names + Bukkit.getOfflinePlayer(uuid).getName() + ", ";
        }
        
		Msg.sendMessage("&7&m-------------------------------------------");
		Msg.sendMessage("");
		Msg.sendMessage("&bCongratulations to &3" + names + " &bfor winning the UHC.");
		Msg.sendMessage("");
		Msg.sendMessage("&7&m-------------------------------------------");
        
		boolean enabled = plugin.getGameManager().isStats();
		
        if(enabled) {
            for(UHCData uhcData : PlayerManager.getUHCPlayerSet(event.getNames())) {
                uhcData.setWins(uhcData.getWins() + 1);
            }

			Bukkit.getOnlinePlayers().forEach(player -> {
				UHCData.getByName(player.getName()).save();

				if(UHCData.getByName(player.getName()).isAlive()) {
					player.setHealth(20.0);
					player.setNoDamageTicks(150);
				}
			});
        }

		GameManager.setGameState(GameState.WINNER);

		new BukkitRunnable() {
			public void run() {
				MLGCommand.doMLG();
			}
		}.runTaskLater(plugin, 20L * 10);

		Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "game forcestop"), 20L * 30);
    }

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if(plugin.getPracticeManager().isOpen()) {
			return;
		}
		
		Player player = event.getEntity();
		
		if(plugin.getPracticeManager().getUsers().contains(player.getUniqueId())) {
			return;
		}

		boolean enabled = plugin.getGameManager().isStats();
		
		UHCData uhcData = UHCData.getByName(player.getName());
				
		uhcData.setLevel(event.getDroppedExp() / 7);

		FastDateFormat date = FastDateFormat.getInstance("dd.MM HH:mm:ss", TimeZone.getTimeZone("Europe/Zagreb"), Locale.ENGLISH);
		
		if(enabled) {
			uhcData.setDeaths(uhcData.getDeaths() + 1);
		}
		
		Player killer = player.getKiller();
		
		if(killer == null) return;
		if(!(killer instanceof Player)) return;
		
		UHCData uhcKiller = UHCData.getByName(killer.getName());
		
		uhcKiller.setKills(uhcKiller.getKills() + 1);

				
		if(uhcKiller.getKillCount().size() == 10) {
			uhcKiller.getKillCount().remove(4);
			
			uhcKiller.getKillCount().add(0, (date).format(System.currentTimeMillis()) + " - " + event.getDeathMessage());
		} else {
			uhcKiller.getKillCount().remove("None");
			
			uhcKiller.getKillCount().add(0, (date).format(System.currentTimeMillis()) + " - " + event.getDeathMessage());
		}
		
		if(enabled) {
			uhcKiller.setTotalKills(uhcKiller.getTotalKills() + 1);

			if(uhcKiller.getKills() > uhcKiller.getKillStreak()) {
				uhcKiller.setKillStreak(uhcKiller.getKills());
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if(event.isCancelled()) return;

		Player player = event.getPlayer();
		Block block = event.getBlock();
		
		if(!event.isCancelled()) {
			UHCData uhcData = UHCData.getByName(player.getName());
			
			switch(block.getType()) {
			case DIAMOND_ORE:
				if(uhcData.isAlive()) {
					uhcData.setDiamondsMined(uhcData.getDiamondsMined() + 1);
					uhcData.setTotalDiamondsMined(uhcData.getTotalDiamondsMined() + 1);
				}
				
				break;
			case MOB_SPAWNER:
				if(uhcData.isAlive()) {
					uhcData.setSpawnersMined(uhcData.getSpawnersMined() + 1);
				}
				break;
			default:
				break;
			
			}
		}
 	}
	
}
