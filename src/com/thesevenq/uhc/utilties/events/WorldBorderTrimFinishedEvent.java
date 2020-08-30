package com.thesevenq.uhc.utilties.events;

import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;

@Getter
public class WorldBorderTrimFinishedEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	private World world;
	private long totalChunks;

	public WorldBorderTrimFinishedEvent(World world, long totalChunks) {
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