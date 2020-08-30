package com.thesevenq.uhc.listeners;

import com.thesevenq.uhc.managers.GameManager;
import com.thesevenq.uhc.utilties.BaseListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.player.state.GameState;

import java.util.Random;

public class MultiSpawnListener extends BaseListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

        if(GameManager.getGameState().equals(GameState.PLAYING)) {
            return;
        }

        randomSpawn(player);
	}

	public static void handleMove(Player player, Location from, Location to) {
        if(GameManager.getGameState().equals(GameState.PLAYING)) {
            return;
        }

        if(from.getBlockX() != to.getBlockX() || from.getBlockZ() != to.getBlockZ() || from.getPitch() != to.getPitch() || from.getYaw() != to.getYaw()) return;

        if(to.getBlockY() <= -15 && !player.isDead()) {
            randomSpawn(player);
        }
    }

    public static void randomSpawn(Player player) {
        if(UHC.getInstance().getUtiltiesConfig().getConfigurationSection("Spawns") == null) return;
        
        int size = UHC.getInstance().getUtiltiesConfig().getConfigurationSection("Spawns").getKeys(false).size();
        
        if(size == 0) return;
        
        int ii = randInt(0, size - 1);
        int i = 0;
        
        for(String key : UHC.getInstance().getUtiltiesConfig().getConfigurationSection("Spawns").getKeys(false)) {
            if(i == ii) {
                int lX = UHC.getInstance().getUtiltiesConfig().getInt("Spawns." + key + ".X");
                int lY = UHC.getInstance().getUtiltiesConfig().getInt("Spawns." + key + ".Y");
                int lZ = UHC.getInstance().getUtiltiesConfig().getInt("Spawns." + key + ".Z");
                
                String lWorld = UHC.getInstance().getUtiltiesConfig().getString("Spawns." + key + ".World");

                Location location;
                location = player.getLocation();
                location.setX(lX);
                location.setY(lY);
                location.setZ(lZ);
                
                if (Bukkit.getWorld(lWorld) != null) {
                    location.setWorld(Bukkit.getWorld(lWorld));
                }
                
                player.teleport(location);
                break;
            }
            i++;
        }
    }
    
    public static void randomSpawnUHCPractice(Player player) {
        if(UHC.getInstance().getUtiltiesConfig().getConfigurationSection("UHCPracticeSpawns") == null) return;
        
        int size = UHC.getInstance().getUtiltiesConfig().getConfigurationSection("UHCPracticeSpawns").getKeys(false).size();
        
        if(size == 0) return;
        
        int ii = randInt(0, size - 1);
        int i = 0;
        
        for(String key : UHC.getInstance().getUtiltiesConfig().getConfigurationSection("UHCPracticeSpawns").getKeys(false)) {
            if(i == ii) {
                int lX = UHC.getInstance().getUtiltiesConfig().getInt("UHCPracticeSpawns." + key + ".X");
                int lY = UHC.getInstance().getUtiltiesConfig().getInt("UHCPracticeSpawns." + key + ".Y");
                int lZ = UHC.getInstance().getUtiltiesConfig().getInt("UHCPracticeSpawns." + key + ".Z");
                
                String lWorld = UHC.getInstance().getUtiltiesConfig().getString("UHCPracticeSpawns." + key + ".World");

                Location location;
                location = player.getLocation();
                location.setX(lX);
                location.setY(lY + 40);
                location.setZ(lZ);
                
                if (Bukkit.getWorld(lWorld) != null) {
                    location.setWorld(Bukkit.getWorld(lWorld));
                }
                
                player.teleport(location);
                break;
            }
            i++;
        }
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

}
