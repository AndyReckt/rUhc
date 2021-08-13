package me.andyreckt.uhc.listeners;

import me.andyreckt.uhc.utilties.BaseListener;
import me.andyreckt.uhc.utilties.ServerUtils;
import me.andyreckt.uhc.utilties.events.WorldBorderFillFinishedEvent;
import me.andyreckt.uhc.border.worldborder.BorderData;
import me.andyreckt.uhc.border.worldborder.Config;
import me.andyreckt.uhc.tasks.BorderCheckTask;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class WorldBorderListener extends BaseListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		if(Config.KnockBack() == 0.0) return;

		if(Config.Debug()) Config.log("Teleport cause: " + event.getCause().toString());

		Location newLoc = BorderCheckTask.checkPlayer(event.getPlayer(), event.getTo(), true, true);
				
		if(newLoc != null) {
			if(event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL && Config.getDenyEnderpearl()) {
				event.setCancelled(true);
				return;
			}

			event.setTo(newLoc);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerPortal(PlayerPortalEvent event) {
		if(Config.KnockBack() == 0.0 || !Config.portalRedirection()) return;

		Location newLoc = BorderCheckTask.checkPlayer(event.getPlayer(), event.getTo(), true, false);
		
		if(newLoc != null) event.setTo(newLoc);
	}

	/*@EventHandler(priority = EventPriority.MONITOR)
	public void onChunkLoad(ChunkLoadEvent event) {
		if(Config.isBorderTimerRunning()) return;

		Config.logWarn("Border-checking task wasn't running! Something on your server apparently killed it. It will now be restarted.");
		
		Config.StartBorderTimer();
	}*/
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		Location loc = event.getEntity().getLocation();
		
		if(loc == null) return;

		World world = loc.getWorld();
		
		if(world == null) return;
		
		BorderData border = Config.Border(world.getName());
		
		if(border == null) return;

		if(border.insideBorder(loc.getX(), loc.getZ(), Config.ShapeRound())) return;
			
		event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		Location loc = event.getBlockPlaced().getLocation();
		
		if(loc == null) return;

		World world = loc.getWorld();
		
		if(world == null) return;
		
		BorderData border = Config.Border(world.getName());
		
		if(border == null) return;

		if(border.insideBorder(loc.getX(), loc.getZ(), Config.ShapeRound())) return;
		
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onWorldBorderFillFinished(WorldBorderFillFinishedEvent event) {
		plugin.getGameManager().setGenerated(true);
		plugin.getGameManager().setMapGenerating(false);

		ServerUtils.bungeeBroadcast("");
		ServerUtils.bungeeBroadcast("&8[&9&lUHC Info&8] &fUHC Map &9has been generated. &fUHC &9is now joinable!");
		ServerUtils.bungeeBroadcast("");
	}
}
