package me.andyreckt.uhc.utilties;

import me.andyreckt.uhc.managers.BorderManager;
import me.andyreckt.uhc.managers.GameManager;
import me.andyreckt.uhc.utilties.events.GameStopEvent;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.border.Border;
import me.andyreckt.uhc.player.UHCData;
import me.andyreckt.uhc.player.state.GameState;

import java.io.File;

public class WorldCreator {

    public WorldCreator(boolean create, boolean force) {
    	UHC.getInstance().getGameManager().setMapGenerating(true);
        
        if(force && !create) {
            deleteWorld(false);
            return;
        }
        
        if(force && create) {
            deleteWorld(true);
            return;
        }
        
        if(!force && !create) {
            deleteWorld(false);
            return;
        }
        
        if(!force && create) {
            if (!UHC.getInstance().getGameManager().isWorldUsed()) {
                createWorld();
            } else {
                deleteWorld(true);
            }
        }
    }

    public static void stop(CommandSender sender) {
        if(GameManager.getGameState().equals(GameState.PLAYING)) {
            sender.sendMessage(Color.translate("&eStopping UHC..."));

            for(LivingEntity entity : Bukkit.getWorld("uhc_world").getLivingEntities()) {
                if(entity.getType() == EntityType.VILLAGER) {
                    entity.remove();
                }
            }

            GameManager.setGameState(GameState.LOBBY);

            UHCData.getUhcDatas().clear();

            UHC.getInstance().getPartyManager().handleClearParties();

            Bukkit.getServer().getPluginManager().callEvent(new GameStopEvent("Force stopped."));

            new WorldCreator(true, true);

            for(OfflinePlayer whitelisted : Bukkit.getWhitelistedPlayers()) {
                whitelisted.setWhitelisted(false);
            }
        }
    }

    private void createWorld() {
		int sizeBig = 2500;

        World w = Bukkit.createWorld(new org.bukkit.WorldCreator("uhc_world").environment(World.Environment.NORMAL).type(WorldType.NORMAL));
        w.setTime(0);
        
        w.setPVP(false);
        UHC.getInstance().getGameManager().setPvP(false);

        w.setDifficulty(Difficulty.NORMAL);
        w.setGameRuleValue("doDaylightCycle", "false");
        w.setSpawnLocation(0, 100, 0);

        UHC.getInstance().getGameManager().setMapGenerating(false);
        UHC.getInstance().getGameManager().setWorldUsed(false);
        
        World w_nether = Bukkit.createWorld(new org.bukkit.WorldCreator("uhc_nether").environment(World.Environment.NETHER).type(WorldType.NORMAL));
        w_nether.setPVP(false);
        
		if (!UHC.getInstance().getGameManager().isGenerated()) {
            UHC.getInstance().getGameManager().setMapGenerating(true);
	    	
			new BukkitRunnable() {
				public void run() {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb whoosh off");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb denypearl on");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb " + w.getName() + " setcorners " + sizeBig + " -" + sizeBig + " -" + sizeBig + " " + sizeBig);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb " + w_nether.getName() + " setcorners " + (sizeBig / 8) + " -" + (sizeBig / 8) + " -" + (sizeBig / 8) + " " + (sizeBig / 8));
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb shape square");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb setmsg \"\"");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb portal on");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb knockback 2");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb " + w.getName() + " fill " + 100);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb " + w.getName() + " fill confirm");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb " + w_nether.getName() + " fill " + 50);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb " + w_nether.getName() + " fill confirm");

                    BorderManager.border = 2000;
                    new Border(Bukkit.getWorld("uhc_world"), 2000);

                    Bukkit.getWorld("uhc_world").setGameRuleValue("naturalRegeneration", "false");
					Bukkit.getWorld("uhc_nether").setGameRuleValue("naturalRegeneration", "false");
				}
			}.runTaskLater(UHC.getInstance(), 20 * 5);
		}
    }

    private void deleteWorld(boolean createAfter) {
    	UHC.getInstance().getGameManager().setRestarted(false);
        
        World w = Bukkit.getWorld("uhc_world");
        World w_nether = Bukkit.getWorld("uhc_nether");
        
        if(w != null) {
            for(Player players : w.getPlayers()) {
               players.teleport(UHCUtils.getSpawnLocation()); 
            }
            
            Bukkit.unloadWorld(w, false);
            
            deleteFile(w.getWorldFolder());
            deleteFile(w.getWorldFolder());
        }
        
        if(w_nether != null) {
            for(Player players : w_nether.getPlayers()) {
                players.teleport(UHCUtils.getSpawnLocation());
            }
            
            Bukkit.unloadWorld(w_nether, false);
            
            deleteFile(w_nether.getWorldFolder());
            deleteFile(w_nether.getWorldFolder());
        }
        
        if(createAfter) {
            new BukkitRunnable() {
                public void run() {
                    createWorld();
                }
            }.runTaskLater(UHC.getInstance(), 20 * 6);

        }
    }

     private boolean deleteFile(File file) {
        if(file.isDirectory()) {
            for(File subfile : file.listFiles()) {
                if(!deleteFile(subfile)) {
                    return false;
                }
            }
        }
        return file.delete();
    }
}
