package me.andyreckt.uhc.utilties.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


import me.andyreckt.uhc.tasks.WorldFillTask;


public class WorldBorderFillStartEvent extends Event {
	public WorldFillTask getFillTask() {
		return fillTask;
	}

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
