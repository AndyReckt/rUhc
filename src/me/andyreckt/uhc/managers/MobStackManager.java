package me.andyreckt.uhc.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.utilties.Manager;

import java.util.ArrayList;
import java.util.List;

public class MobStackManager extends Manager {

    private List<EntityType> mobList = new ArrayList<>();
    private String[] mobs = new String[] { "ZOMBIE", "SKELETON", "SPIDER", "CAVE_SPIDER", "PIG", "SHEEP", "COW", "CHICKEN" };
    
    public MobStackManager(UHC plugin) {
        super(plugin);

        handleLoad();
        handleStartTask();
    }

    public void handleOnDisable() {
        if(!mobList.isEmpty()) mobList.clear();

        for(World world : Bukkit.getWorlds()) {
            for(LivingEntity entity : world.getLivingEntities()) {
                if(entity instanceof Monster && entity.isCustomNameVisible()) {
                    entity.remove();
                }
            }
        }
    }

    public void handleLoad() {
        if(!mobList.isEmpty()) mobList.clear();
        for(String entityName : mobs) {
            EntityType entityType = EntityType.valueOf(entityName.toUpperCase());

            mobList.add(entityType);
        }
    }

    public void handleStartTask() {
        new BukkitRunnable() {
            public void run() {
                int radius = 5;

                List<EntityType> entityTypes = mobList;

                for(World world : Bukkit.getServer().getWorlds()) {
                    for(LivingEntity entity : world.getLivingEntities()) {
                        if(entityTypes.contains(entity.getType()) && entity.isValid()) {
                            for(Entity nearby : entity.getNearbyEntities((double)radius, (double)radius, (double)radius)) {
                                if(nearby instanceof LivingEntity && nearby.isValid() && entityTypes.contains(nearby.getType())) {
                                    handleStackOne(entity, (LivingEntity)nearby, ChatColor.RED);
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(UHC.getInstance(), 100L, 100L);
    }

    public void handleUnstackOne(LivingEntity livingEntity, ChatColor color) {
        String displayName = livingEntity.getCustomName();
        int stackSize = getAmount(displayName, color);
        if(stackSize <= 1) return;
        --stackSize;
        String newDisplayName = color + "x" + stackSize;
        LivingEntity newEntity = (LivingEntity)livingEntity.getWorld().spawnEntity(livingEntity.getLocation(), livingEntity.getType());

        newEntity.setCustomName(newDisplayName);
        newEntity.setCustomNameVisible(false);
        livingEntity.setHealth(0.0);

        if(newEntity instanceof Ageable) {
            ((Ageable) newEntity).setAdult();
        }

        if(newEntity instanceof Zombie) {
            ((Zombie) newEntity).setBaby(false);
        }
    }

    public void handleStackOne(LivingEntity target, LivingEntity stackee, ChatColor color) {
        if (target.getType() != stackee.getType()) return;

        String displayName = target.getCustomName();
        int oldAmount = getAmount(displayName, color);
        int newAmount = 1;
        if(isStacked(stackee, color)) newAmount = getAmount(stackee.getCustomName(), color);
        stackee.remove();
        if(oldAmount == 0) {
            int amount = newAmount + 1;
            String newDisplayName = color + "x" + amount;
            target.setCustomName(newDisplayName);
            target.setCustomNameVisible(true);
        } else {
            int amount = oldAmount + newAmount;
            String newDisplayName = color + "x" + amount;
            target.setCustomName(newDisplayName);
        }
    }

    public int getAmount(String displayName, ChatColor color) {
        if(displayName == null) return 0;

        String nameColor = ChatColor.getLastColors(displayName);
        if (nameColor.equals('§' + color.getChar())) return 0;
        String name1 = displayName.replace("x", "");
        String name2 = ChatColor.stripColor(name1.replace("§f", ""));
        name2 = ChatColor.stripColor(name2);
        if(!name2.matches("[0-9]+")) return 0;
        if(name2.length() > 4) return 0;

        return Integer.parseInt(name2);
    }

    public boolean isStacked(LivingEntity entity, ChatColor color) {
        return getAmount(entity.getCustomName(), color) != 0;
    }
}
