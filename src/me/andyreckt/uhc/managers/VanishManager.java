package me.andyreckt.uhc.managers;

import me.andyreckt.uhc.utilties.Color;

import me.andyreckt.uhc.utilties.Manager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import me.andyreckt.uhc.UHC;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class VanishManager extends Manager {
    public List<UUID> getVanishedPlayers() {
        return vanishedPlayers;
    }

    public List<UUID> getSilentView() {
        return silentView;
    }

    private List<UUID> vanishedPlayers = new ArrayList<>();
    private List<UUID> silentView = new ArrayList<>();

    public VanishManager(UHC plugin) {
        super(plugin);
    }

    public void handleOnDisable() {
        vanishedPlayers.clear();
        silentView.clear();
    }

    public void handleVanish(Player player) {
        vanishedPlayers.add(player.getUniqueId());

        Bukkit.getOnlinePlayers().forEach(online -> {
            online.hidePlayer(player);
        });

        player.sendMessage(Color.translate("&eYou are now hidden from other players."));
    }

    public void handleUnvanish(Player player) {
        vanishedPlayers.remove(player.getUniqueId());

        Bukkit.getOnlinePlayers().forEach(online -> {
            if(!online.canSee(player)) {
                online.showPlayer(player);
            }
        });

        player.sendMessage(Color.translate("&ePlayers can see you now."));
    }

    public void handleRemove(Player player) {
        if(vanishedPlayers.contains(player.getUniqueId())) {
            vanishedPlayers.remove(player.getUniqueId());
        }
    }
}
