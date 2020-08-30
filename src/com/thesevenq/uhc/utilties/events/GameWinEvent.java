package com.thesevenq.uhc.utilties.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;
import com.thesevenq.uhc.player.UHCData;

@Getter
public class GameWinEvent extends Event {

    private static HandlerList handlers = new HandlerList();
    private String winner;
    private UHCData uhcData;

    public GameWinEvent(String winner, UHCData uhcData) {
        this.winner = winner;
        this.uhcData = uhcData;
    }
    
	public static HandlerList getHandlerList() {
		return handlers;
	}
    
    public HandlerList getHandlers() {
        return handlers;
    }
}
