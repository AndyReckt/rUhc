package me.andyreckt.uhc.utilties.events;

import java.util.Set;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


import me.andyreckt.uhc.player.party.Party;


public class GameWinTeamEvent extends Event {
    public Party getParty() {
        return party;
    }

    private static HandlerList handlers = new HandlerList();
    private Party party;

    public GameWinTeamEvent(Party party) {
        this.party = party;
    }

    public Set<String> getNames() {
        return party.getPlayers();
    }
    
	public static HandlerList getHandlerList() {
		return handlers;
	}
    
    public HandlerList getHandlers() {
        return handlers;
    }
}
