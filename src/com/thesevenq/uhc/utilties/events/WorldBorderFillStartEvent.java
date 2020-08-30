package com.thesevenq.uhc.utilties.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;
import com.thesevenq.uhc.tasks.WorldFillTask;

@Getter
public class WorldBorderFillStartEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	private WorldFillTask fillTask;

	public WorldBorderFillStartEvent(WorldFillTask worldFillTask) {
		this.fillTask = worldFillTask;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
