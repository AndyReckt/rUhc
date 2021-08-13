package me.andyreckt.uhc.utilties.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


import me.andyreckt.uhc.player.UHCData;


public class GameWinEvent extends Event {
    public String getWinner() {
        return winner;
    }

    public UHCData getUhcData() {
        return uhcData;
    }

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
