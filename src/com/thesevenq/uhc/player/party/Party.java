package com.thesevenq.uhc.player.party;

import com.thesevenq.uhc.managers.PlayerManager;
import com.thesevenq.uhc.utilties.Color;
import com.thesevenq.uhc.utilties.UHCUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import com.thesevenq.uhc.player.UHCData;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Party {

    private Set<String> players = new HashSet<>();
    private Player owner;
    private int id;
    private int kills = 0;
    private Inventory backPack;
    private Location scatterLocation = UHCUtils.getScatterLocation();
    public boolean damageMembers;
    private ChatColor teamColor;

    public Party(Player owner, int id) {
        this.owner = owner;
        this.id = id;
        this.damageMembers = true;
        this.teamColor = UHCUtils.getRandomTeamChatColor();
        
        this.backPack = Bukkit.createInventory(null, 27, Color.translate("&5&lBackpack&7: &d" + id));
    }

    public boolean isAlive() {
        for(UHCData uhcData : PlayerManager.getUHCPlayerSet(players)) {
            if(uhcData != null) {
                if(uhcData.isAlive()) return true;
            }
        }
        
        return false;
    }

     public void removePlayer(OfflinePlayer player) {
        players.remove(player.getName());
        
        if(player.isOnline()) {
            player.getPlayer().sendMessage(Color.translate("&aYou have left."));
        }
        
        this.broadcast(Color.translate("&c&l" + player.getName() + " &chas left your party."));
    }

    public void addPlayer(Player player) {
        this.broadcast(Color.translate("&d" + player.getName() + " &bhas joined your party."));
        
        players.add(player.getName());
        
        player.sendMessage(Color.translate("&bYou have joined party &d" + id + "&b."));
    }

    public void broadcast(String message) {
        for(String uuid : players) {
            Player player = Bukkit.getPlayer(uuid);
            
            if(player != null) {
                player.sendMessage(Color.translate(message));
            }
        }
    }
    
	public boolean contains(Player player) {
		return players.contains(player);
	}
    
    public int getSize() {
        return getPlayers().size();
    }
}