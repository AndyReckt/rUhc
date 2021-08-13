package me.andyreckt.uhc.utilties.events;

import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;




public class WorldBorderFillFinishedEvent extends Event {
	public World getWorld() {
		return world;
	}

	public long getTotalChunks() {
		return totalChunks;
	}

	private static final HandlerList handlers = new HandlerList();
	private World world;
	private long totalChunks;

	public WorldBorderFillFinishedEvent(World world, long totalChunks) {
		this.world = world;
		this.totalChunks = totalChunks;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
