package com.thesevenq.uhc.utilties.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;
import lombok.Setter;
import com.thesevenq.uhc.config.Option;

@Getter
@Setter
public class ConfigOptionChangeEvent extends Event {

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
