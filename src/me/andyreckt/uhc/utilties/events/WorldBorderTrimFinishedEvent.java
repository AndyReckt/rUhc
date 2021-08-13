package me.andyreckt.uhc.utilties.events;

import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;



public class WorldBorderTrimFinishedEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	private World world;
	private long totalChunks;

	public WorldBorderTrimFinishedEvent(World world, long totalChunks) {
		this.world = world;
		this.totalChunks = totalChunks;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public long getTotalChunks() {
		return totalChunks;
	}

	public void setTotalChunks(long totalChunks) {
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