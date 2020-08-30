package com.thesevenq.uhc.scenario.type;

import com.thesevenq.uhc.scenario.Scenario;
import com.thesevenq.uhc.utilties.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class SwitcherooScenario extends Scenario implements Listener {

    public SwitcherooScenario() {
        super("Switcheroo", Material.ENDER_PEARL, "Everytime you shoot a player, you switch positions", "with him.");
    }

    public static void handleDamageByEntity(Entity entity, Entity damager) {
        if(!(entity instanceof Player)) return;
        if(!(damager instanceof Arrow)) return;

        Arrow arrow = (Arrow) damager;

        if(!(arrow.getShooter() instanceof Player)) return;

        Player shooter = (Player) arrow.getShooter();
        Player damaged = (Player) entity;

        Location shooterLocation = shooter.getLocation();
        Location damagedLocation = damaged.getLocation();

        shooter.teleport(damagedLocation);
        damaged.teleport(shooterLocation);

        shooter.sendMessage(Color.translate("&eYou got &dSwitcheroo'd &ewith &d" + damaged.getName() + "&e."));
        damaged.sendMessage(Color.translate("&eYou got &dSwitcheroo'd &ewith &d" + shooter.getName() + "&e."));
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

        shooter.teleport(damagedLocation);
        damaged.teleport(shooterLocation);

        shooter.sendMessage(Color.translate("&eYou got &dSwitcheroo'd &ewith &d" + damaged.getName() + "&e."));
        damaged.sendMessage(Color.translate("&eYou got &dSwitcheroo'd &ewith &d" + shooter.getName() + "&e."));
    }
}
