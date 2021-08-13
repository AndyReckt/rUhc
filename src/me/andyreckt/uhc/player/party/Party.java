package me.andyreckt.uhc.player.party;

import me.andyreckt.uhc.managers.PlayerManager;
import me.andyreckt.uhc.utilties.Color;
import me.andyreckt.uhc.utilties.UHCUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import me.andyreckt.uhc.player.UHCData;

import java.util.HashSet;
import java.util.Set;


public class Party {
    public Set<String> getPlayers() {
        return players;
    }

    public void setPlayers(Set<String> players) {
        this.players = players;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public Inventory getBackPack() {
        return backPack;
    }

    public void setBackPack(Inventory backPack) {
        this.backPack = backPack;
    }

    public Location getScatterLocation() {
        return scatterLocation;
    }

    public void setScatterLocation(Location scatterLocation) {
        this.scatterLocation = scatterLocation;
    }

    public boolean isDamageMembers() {
        return damageMembers;
    }

    public void setDamageMembers(boolean damageMembers) {
        this.damageMembers = damageMembers;
    }

    public ChatColor getTeamColor() {
        return teamColor;
    }

    public void setTeamColor(ChatColor teamColor) {
        this.teamColor = teamColor;
    }

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
        this.broadcast(Color.translate("&d" + player.getName() + " &ehas joined your party."));
        
        players.add(player.getName());
        
        player.sendMessage(Color.translate("&eYou have joined party &d" + id + "&e."));
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