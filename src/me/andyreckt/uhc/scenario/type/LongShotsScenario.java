package me.andyreckt.uhc.scenario.type;

import me.andyreckt.uhc.scenario.Scenario;
import me.andyreckt.uhc.utilties.Msg;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class LongShotsScenario extends Scenario implements Listener {

    public LongShotsScenario() {
        super("Long Shots", Material.ARROW, "If you get a shot from more than 50 blocks, ", "you get healed for 1 heart, and do 1.5x the damage.");
    }

    public static void handleEntityDamageByEntity(Entity entity, Entity damager, EntityDamageByEntityEvent event) {
        if(!(entity instanceof Player)) return;
        if(!(damager instanceof Arrow)) return;

        Arrow arrow = (Arrow) damager;

        if(!(arrow.getShooter() instanceof Player)) return;

        Player shooter = (Player) arrow.getShooter();
        Player damaged = (Player) entity;

        Location shooterLocation = shooter.getLocation();
        Location damagedLocation = damaged.getLocation();

        int x = (int) shooterLocation.distance(damagedLocation);

        if(x >= 50) {
            event.setDamage(event.getDamage() * 1.5);

            if(shooter.getHealth() > 18) {
                shooter.setHealth(20L);
            } else {
                shooter.setHealth(shooter.getHealth() + 2.0);
            }

            Msg.sendMessage("&d" + shooter.getName() + " &egot &dLong Shot of &d" + x + " &eblocks.");
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        if(!(event.getDamager() instanceof Arrow)) return;

        Arrow arrow = (Arrow) event.getDamager();

        if(!(arrow.getShooter() instanceof Player)) return;

        Player shooter = (Player) arrow.getShooter();
        Player damaged = (Player) event.getEntity();

        Location shooterLocation = shooter.getLocation();
        Location damagedLocation = damaged.getLocation();

        int x = (int) shooterLocation.distance(damagedLocation);

        if(x >= 50) {
            event.setDamage(event.getDamage() * 1.5);

            if(shooter.getHealth() > 18) {
                shooter.setHealth(20L);
            } else {
                shooter.setHealth(((Damageable) shooter).getHealth() + 2.0);
            }

            Msg.sendMessage("&d" + shooter.getName() + " &egot &dLong Shot of &d" + x + " &eblocks.");
        }
    }
}
