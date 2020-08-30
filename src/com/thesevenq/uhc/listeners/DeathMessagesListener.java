package com.thesevenq.uhc.listeners;

import com.thesevenq.uhc.utilties.BaseListener;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import com.thesevenq.uhc.player.UHCData;
import com.thesevenq.uhc.utilties.BaseListener;

public class DeathMessagesListener extends BaseListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        String message = event.getDeathMessage();
        
        if(message == null || message.isEmpty()) return;
        
        if(plugin.getPracticeManager().isOpen()) {
			event.setDeathMessage(null);

			for(Player player : Bukkit.getOnlinePlayers()) {
				if(plugin.getPracticeManager().getUsers().contains(player.getUniqueId())) {
					player.sendMessage(this.getPracticeDeathMessage(message, event.getEntity(), getKiller(event)));
				}
			}
		} else {
			event.setDeathMessage(getDeathMessage(message, event.getEntity(), getKiller(event)));
		}
	}

    private CraftEntity getKiller(PlayerDeathEvent event) {
        EntityLiving lastAttacker = ((CraftPlayer) event.getEntity()).getHandle().getLastDamager();
        
        return lastAttacker == null ? null : lastAttacker.getBukkitEntity();
    }
    
    private String getDeathMessage(String input, Entity entity, Entity killer) {
        if(entity != null) {
            input = input.replaceFirst("(?i)" + getEntityName(entity), ChatColor.GREEN + getPlayerDisplayName(entity) + ChatColor.GRAY);
        }

        if(killer != null && (entity == null || !killer.equals(entity))) {
            input = input.replaceFirst("(?i)" + getEntityName(killer), ChatColor.RED + getKillerDisplayName(killer) + ChatColor.GRAY);
        }

        return input;
    }

    private String getPracticeDeathMessage(String input, Entity entity, Entity killer) {
        if(entity != null) {
            input = input.replaceFirst("(?i)" + getEntityName(entity), ChatColor.RED + getPracticePlayerDisplayName(entity) + ChatColor.YELLOW);
        }

        if(killer != null && (entity == null || !killer.equals(entity))) {
            input = input.replaceFirst("(?i)" + getEntityName(killer), ChatColor.RED + getPracticeKillerDisplayName(killer) + ChatColor.YELLOW);
        }

        return input;
    }
    
    public static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)" + regex + "(?!.*?" + regex + ')', replacement);
    }

    public static String getEntityName(Entity entity) {        
        return entity instanceof Player ? ((Player) entity).getName() : ((CraftEntity) entity).getHandle().getName();
    }

    public static String getPlayerDisplayName(Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            
            UHCData uhcData = UHCData.getByName(player.getName());
            
            return player.getName() + ChatColor.DARK_GREEN + '[' + ChatColor.DARK_GREEN + uhcData.getKills() + ChatColor.DARK_GREEN + ']';
        } else {
            return WordUtils.capitalizeFully(entity.getType().name().replace('_', ' '));
        }
    }
    
    public static String getKillerDisplayName(Entity entity) {
        if(entity instanceof Player) {
            Player player = (Player) entity;

            UHCData uhcData = UHCData.getByName(player.getName());
            int kills = (uhcData.getKills() + 1);

            return player.getName() + ChatColor.DARK_RED + '[' + ChatColor.DARK_RED + kills + ChatColor.DARK_RED + ']';
        } else {
            return WordUtils.capitalizeFully(entity.getType().name().replace('_', ' '));
        }
    }
    
    private String getPracticePlayerDisplayName(Entity entity) {
        if(entity instanceof Player) {
            Player player = (Player) entity;
            
            return player.getName();
        } else {
            return WordUtils.capitalizeFully(entity.getType().name().replace('_', ' '));
        }
    }
    
    private String getPracticeKillerDisplayName(Entity entity) {
        if(entity instanceof Player) {
            Player player = (Player) entity;
            
            return player.getName();
        } else {
            return WordUtils.capitalizeFully(entity.getType().name().replace('_', ' '));
        }
    }
}
