package com.thesevenq.uhc.utilties.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;
import com.thesevenq.uhc.tasks.WorldTrimTask;

@Getter
public class WorldBorderTrimStartEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	private WorldTrimTask trimTask;

	public WorldBorderTrimStartEvent(WorldTrimTask trimTask) {
		this.trimTask = trimTask;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}