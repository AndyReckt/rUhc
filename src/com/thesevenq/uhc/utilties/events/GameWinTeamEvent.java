package com.thesevenq.uhc.utilties.events;

import java.util.Set;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;
import com.thesevenq.uhc.player.party.Party;

@Getter
public class GameWinTeamEvent extends Event {

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
