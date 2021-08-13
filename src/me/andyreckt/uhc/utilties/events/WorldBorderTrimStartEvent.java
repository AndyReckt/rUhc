package me.andyreckt.uhc.utilties.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


import me.andyreckt.uhc.tasks.WorldTrimTask;


public class WorldBorderTrimStartEvent extends Event {
	public WorldTrimTask getTrimTask() {
		return trimTask;
	}

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