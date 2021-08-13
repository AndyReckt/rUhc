package me.andyreckt.uhc.scenario.type;

import me.andyreckt.uhc.scenario.Scenario;
import me.andyreckt.uhc.utilties.Color;
import me.andyreckt.uhc.utilties.Tasks;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import me.andyreckt.uhc.UHC;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Marko on 03.06.2018.
 */
public class DoNotDisturbScenario extends Scenario implements Listener {

    private static Map<UUID, UUID> disturb = new HashMap();

    public DoNotDisturbScenario() {
        super("Do Not Disturb", Material.BED, "Once you hit a player your fight can't be interfered with", "Your tag lasts 30 seconds whenever you hit the player.");
    }

    public static void handleEntityDamageByEntity(Entity entity, Entity damage, EntityDamageByEntityEvent event) {
        if(!(entity instanceof Player)) return;
        if(!(damage instanceof Player)) return;

        Player player = (Player) entity;
        Player damager = (Player) damage;

        if(disturb.containsKey(damager.getUniqueId())) {
            if(!player.getUniqueId().equals(disturb.get(damager.getUniqueId()))) {
                event.setCancelled(true);
                damager.sendMessage(Color.translate("&cYou can't hit that player, he isn't linked to you."));
                return;
            }
        } else {
            if(disturb.containsKey(player.getUniqueId())) {
                event.setCancelled(true);
                damager.sendMessage(Color.translate("&cYou can't hit that player, because he is already linked."));
                return;
            }

            disturb.put(damager.getUniqueId(), player.getUniqueId());
            disturb.put(player.getUniqueId(), damager.getUniqueId());

            damager.sendMessage(Color.translate("&eYou can fight now only with &d" + player.getName() + "&e."));
            player.sendMessage(Color.translate("&eYou can fight now only with &d" + damager.getName() + "&e."));

            Tasks.runLater(() -> {
                disturb.remove(player.getUniqueId());
                disturb.remove(damager.getUniqueId());

                if(player.isOnline()) {
                    player.sendMessage(Color.translate("&eYour &dDo Not Disturb&e status has been removed."));
                }

                if(damager.isOnline()) {
                    damager.sendMessage(Color.translate("&eYour &dDo Not Disturb&e status has been removed."));
                }
            }, 25 * 20L);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        if(!(event.getDamager() instanceof Player)) return;

        Player player = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        if(disturb.containsKey(damager.getUniqueId())) {
            if(!player.getUniqueId().equals(disturb.get(damager.getUniqueId()))) {
                event.setCancelled(true);
                damager.sendMessage(Color.translate("&cYou can't hit that player, he isn't linked to you."));
                return;
            }
        } else {
            if(disturb.containsKey(player.getUniqueId())) {
                damager.sendMessage(Color.translate("&cYou can't hit that player, because he is already linked."));
                return;
            }

            disturb.put(damager.getUniqueId(), player.getUniqueId());
            disturb.put(player.getUniqueId(), damager.getUniqueId());

            new BukkitRunnable() {
                public void run() {
                    disturb.remove(player.getUniqueId());
                    disturb.remove(damager.getUniqueId());

                    if(player != null) {
                        player.sendMessage(Color.translate("&eYour &dDo Not Disturb&e status has been removed."));
                    }

                    if(damager != null) {
                        damager.sendMessage(Color.translate("&eYour &dDo Not Disturb&e status has been removed."));
                    }
                }
            }.runTaskLater(UHC.getInstance(), 25 * 20L);
        }
    }
}
