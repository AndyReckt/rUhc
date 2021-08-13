package me.andyreckt.uhc.utilties;

import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerUtils {

    public static void kick(AsyncPlayerPreLoginEvent event) {
        event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
        event.setKickMessage(Color.translate("&cYour data hasn't been loaded. Please try re-joining!"));
        return;
    }

    public static void kick(PlayerJoinEvent event) {
        event.getPlayer().kickPlayer(Color.translate("&cYour data hasn't been loaded. Please try re-joining!"));
        return;
    }

}
