package me.andyreckt.uhc.listeners;

import me.andyreckt.uhc.utilties.BaseListener;
import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.player.deathlookup.data.DeathData;
import me.andyreckt.uhc.player.deathlookup.data.ProfileFight;
import me.andyreckt.uhc.player.deathlookup.data.ProfileFightEnvironment;
import me.andyreckt.uhc.player.deathlookup.data.killer.type.ProfileFightEnvironmentKiller;
import me.andyreckt.uhc.player.deathlookup.data.killer.type.ProfileFightPlayerKiller;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ProfileFightListener extends BaseListener implements Listener {


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        DeathData.getByName(event.getPlayer().getName()).save();
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if(plugin.getPracticeManager().getUsers().contains(player.getUniqueId())) {
            return;
        }

        DeathData deathData = DeathData.getByName(player.getName());
        EntityDamageEvent damageEvent = player.getLastDamageCause();

        if (player.getKiller() != null) {
            ProfileFight fight = new ProfileFight(player, new ProfileFightPlayerKiller(player.getKiller()));
            deathData.getFights().add(fight);

            DeathData.getByName(player.getKiller().getName()).getFights().add(fight);

            new BukkitRunnable() {
                @Override
                public void run() {
                    deathData.save();
                }
            }.runTaskAsynchronously(UHC.getInstance());
            return;
        }

        if (damageEvent == null) {
            deathData.getFights().add(new ProfileFight(player, new ProfileFightEnvironmentKiller(ProfileFightEnvironment.CUSTOM)));
            new BukkitRunnable() {
                @Override
                public void run() {
                    deathData.save();
                }
            }.runTaskAsynchronously(UHC.getInstance());
            return;
        }

        DamageCause cause = damageEvent.getCause();

        if (cause == DamageCause.PROJECTILE || cause == DamageCause.ENTITY_ATTACK || cause == DamageCause.POISON || cause == DamageCause.MAGIC || cause == DamageCause.ENTITY_EXPLOSION) {
            return;
        }

        try {
            deathData.getFights().add(new ProfileFight(player, new ProfileFightEnvironmentKiller(ProfileFightEnvironment.valueOf(cause.name().toUpperCase()))));
            new BukkitRunnable() {
                @Override
                public void run() {
                    deathData.save();
                }
            }.runTaskAsynchronously(UHC.getInstance());
        } catch (Exception ignored) {
            deathData.getFights().add(new ProfileFight(player, new ProfileFightEnvironmentKiller(ProfileFightEnvironment.CUSTOM)));
            new BukkitRunnable() {
                @Override
                public void run() {
                    deathData.save();
                }
            }.runTaskAsynchronously(UHC.getInstance());
        }
    }
}
