package com.thesevenq.uhc.utilties.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;

@Getter
public class GameStopEvent extends Event {

    private static HandlerList handlers = new HandlerList();
    private String reason;

    public GameStopEvent(String reason) {
        this.reason = reason;
    }
    
	public static HandlerList getHandlerList() {
		return handlers;
	}
    
	public HandlerList getHandlers() {
		return handlers;
	}

}
