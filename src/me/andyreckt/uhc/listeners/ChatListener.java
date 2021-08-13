package me.andyreckt.uhc.listeners;

import me.andyreckt.uhc.managers.PartyManager;
import me.andyreckt.uhc.player.party.Party;
import me.andyreckt.uhc.utilties.BaseListener;
import me.andyreckt.uhc.utilties.Color;
import me.andyreckt.uhc.utilties.Permission;
import me.andyreckt.uhc.utilties.UHCUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatListener extends BaseListener implements Listener {

    public static List<UUID> chat = new ArrayList<>();

    public static void disable() {
        chat.clear();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (event.isCancelled()) return;

        String message = event.getMessage();
        Player player = event.getPlayer();

        event.setCancelled(false);


        if (chat.contains(player.getUniqueId())) {
            Party party = PartyManager.getByPlayer(player);

            if (party != null) {
                party.broadcast("&9[Team Chat] &d" + player.getName() + "&7: &f" + event.getMessage());
            }

            event.setCancelled(true);
            return;
        }

        if (UHCUtils.isPlayerInSpecMode(player) && !player.hasPermission(Permission.STAFF_PERMISSION)) {
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (UHCUtils.isPlayerInSpecMode(online)) {
                    online.sendMessage(this.getSpectatorFormat(player, message));
                }
            }

            event.setCancelled(true);


            return;
        }
    }

    private String getSpectatorFormat(Player player, String message) {
        if (!player.hasPermission(Permission.STAFF_PERMISSION)) {
            if (UHCUtils.isPlayerInSpecMode(player)) {
                return Color.translate("&9[Spectator Chat] &7" + player.getDisplayName() + " &7» &f") + message;
            }
        }

        return null;
    }
}
