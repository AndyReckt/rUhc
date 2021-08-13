package me.andyreckt.uhc.utilties.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


import me.andyreckt.uhc.config.Option;


public class ConfigOptionChangeEvent extends Event {
	public Player getWhoChanged() {
		return whoChanged;
	}

	public Option getOption() {
		return option;
	}

	public int getFrom() {
		return from;
	}

	public int getTo() {
		return to;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	private static HandlerList handlers = new HandlerList();

	private Player whoChanged;
	private Option option;
	private int from;
	private int to;
	private boolean cancelled;

	public ConfigOptionChangeEvent(Option option, Player whoChanged, int from, int to) {
		if(cancelled) return;
		
		this.option = option;
		this.from = from;
		this.to = to;
		this.whoChanged = whoChanged;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
}
