package me.andyreckt.uhc.listeners;

import me.andyreckt.uhc.utilties.BaseListener;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobStackListener extends BaseListener implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if(event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = event.getEntity();
            if(entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.VILLAGER) {
                plugin.getMobStackManager().handleUnstackOne(entity, ChatColor.RED);
            }
        }
    }
}
