package com.thesevenq.uhc.listeners;

import com.thesevenq.uhc.managers.PartyManager;
import com.thesevenq.uhc.utilties.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import com.thesevenq.uhc.player.party.Party;
import com.thesevenq.uhc.utilties.BaseListener;
import com.thesevenq.uhc.utilties.UHCUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatListener extends BaseListener implements Listener {

    public static List<UUID> chat = new ArrayList<>();

    public static void disable() {
        chat.clear();
    }

    @EventHandler
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
                return Color.translate("&9[Spectator Chat] &7" + player.getDisplayName() + " &6» &f") + message;
            }
        }

        return null;
    }
}
