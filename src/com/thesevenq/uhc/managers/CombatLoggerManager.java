package com.thesevenq.uhc.managers;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.player.UHCData;
import com.thesevenq.uhc.player.state.GameState;
import com.thesevenq.uhc.utilties.Manager;

import java.util.*;

/**
 * Created by Marko on 07.07.2018.
 */

@Getter
public class CombatLoggerManager extends Manager {

    private Map<String, List<Player>> combatLoggers = new HashMap<>();
    private List<UUID> players = new ArrayList<>();
    private List<UUID> dead = new ArrayList<>();

    public CombatLoggerManager(UHC plugin) {
        super(plugin);
        
        handleRunnable();
    }

    public void handleOnDisable() {
        combatLoggers.clear();
        players.clear();
        dead.clear();
    }

    private void handleRunnable() {
        new BukkitRunnable() {
            public void run() {
                handleRemove();
            }
        }.runTaskLater(plugin, 200L);
    }

    public void handleRemove() {
        Bukkit.getWorlds().forEach(world -> {
            world.getEntitiesByClass(Villager.class).forEach(villager -> {
                villager.remove();
            });

            world.getEntities().forEach(entity -> {
                if(entity instanceof Villager) {
                    entity.remove();
                }
            });
        });


    }

    public void handleJoin(Player player) {
        player.getWorld().getEntitiesByClass(Villager.class).forEach(villager -> {
            if(!villager.isDead() && villager.hasMetadata("CombatLogger")
                    && villager.getCustomName().equals(player.getName())) {
                villager.removeMetadata("CombatLogger", UHC.getInstance());
                villager.removeMetadata("Player", UHC.getInstance());
                villager.removeMetadata("Contents", UHC.getInstance());
                villager.removeMetadata("Armor", UHC.getInstance());

                villager.remove();
            }
        });
    }

    public void handleQuit(Player player) {
        if(player.getHealth() == 0.0 && !GameManager.getGameState().equals(GameState.PLAYING) && !plugin.getGameManager().isPvp()) {
            return;
        }

        UHCData uhcData = UHCData.getByName(player.getName());

        if(!uhcData.isAlive()) {
            return;
        }

        combatLoggers.put(player.getName(), Arrays.asList(player));
        this.handleSpawn(player);
    }

    public void handleSpawn(Player player) {
        Villager villager = (Villager) player.getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);

        villager.setCustomName(player.getName());
        villager.setCustomNameVisible(true);

        villager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 100));
        villager.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 100));

        villager.setMetadata("CombatLogger", new FixedMetadataValue(UHC.getInstance(), player.getUniqueId()));
        villager.setMetadata("Player", new FixedMetadataValue(UHC.getInstance(), player));
        villager.setMetadata("Contents", new FixedMetadataValue(UHC.getInstance(), player.getInventory().getContents()));
        villager.setMetadata("Armor", new FixedMetadataValue(UHC.getInstance(), player.getInventory().getArmorContents()));

        villager.setMaxHealth(40);
        villager.setHealth(villager.getMaxHealth());

        new BukkitRunnable() {
            public void run() {
                if(villager != null && !villager.isDead()) {
                    villager.removeMetadata("CombatLogger", UHC.getInstance());
                    villager.removeMetadata("Player", UHC.getInstance());
                    villager.removeMetadata("Contents", UHC.getInstance());
                    villager.removeMetadata("Armor", UHC.getInstance());

                    villager.remove();
                }
            }
        }.runTaskLater(UHC.getInstance(), 1200L);
    }
}
