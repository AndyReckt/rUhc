package me.andyreckt.uhc.listeners;

import me.andyreckt.uhc.utilties.BaseListener;
import me.andyreckt.uhc.utilties.Color;
import me.andyreckt.uhc.utilties.Permission;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class IPVPListener extends BaseListener implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onEntityDamage(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player && event.getCause().equals(DamageCause.BLOCK_EXPLOSION) && !plugin.getGameManager().isPvp()) {
			Player player = (Player) event.getEntity();

			player.sendMessage(Color.translate("&c&liPvP&c isn't allowed on this server."));

			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerBucketEmptyy(PlayerBucketEmptyEvent event) {
		Player player = event.getPlayer();

		if(plugin.getGameManager().isPvp()) {
			return;
		}

		for(Entity nearby : player.getNearbyEntities(5,5,5)) {
			if(nearby instanceof Player && event.getBucket().equals(Material.LAVA_BUCKET)) {
				event.setCancelled(true);

				player.sendMessage(Color.translate("&c&liPvP&c isn't allowed on this server."));
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if(player.hasPermission(Permission.OP_PERMISSION)
				|| event.getAction() != Action.RIGHT_CLICK_BLOCK
				|| event.getItem() == null
				|| event.getItem().getType() != Material.FLINT_AND_STEEL) {
			return;
		}

		if(plugin.getGameManager().isPvp()) {
			return;
		}

		for(Entity nearby : player.getNearbyEntities(5,5,5)) {
			if(!(nearby instanceof Player)) return;

			event.setCancelled(true);
			player.sendMessage(Color.translate("&c&liPvP&c isn't allowed on this server."));
		}
	}
}
