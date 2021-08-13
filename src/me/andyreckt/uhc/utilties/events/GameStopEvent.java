package me.andyreckt.uhc.utilties.events;

import me.andyreckt.uhc.utilties.Color;
import me.andyreckt.uhc.utilties.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;




public class GameStopEvent extends Event {
	public String getReason() {
		return reason;
	}

	private static HandlerList handlers = new HandlerList();
    private String reason;

    public GameStopEvent(String reason) {
        this.reason = reason;
		for (Player p : Bukkit.getOnlinePlayers()) {
			if(!p.hasPermission(Permission.STAFF_PERMISSION)){
				p.kickPlayer(Color.translate("&eThanks you for playing this UHC!"));
			}
		}
    }
    
	public static HandlerList getHandlerList() {
		return handlers;
	}
    
	public HandlerList getHandlers() {
		return handlers;
	}

}
