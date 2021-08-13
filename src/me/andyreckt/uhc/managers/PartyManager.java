package me.andyreckt.uhc.managers;

import me.andyreckt.uhc.utilties.Color;

import me.andyreckt.uhc.utilties.Manager;
import me.andyreckt.uhc.utilties.UHCUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.player.party.Party;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class PartyManager extends Manager {


    private static Map<String, Party> parties = new HashMap<>();

    public static Map<String, Party> getParties() {
        return parties;
    }

    public static void setParties(Map<String, Party> parties) {
        PartyManager.parties = parties;
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean enabled) {
        PartyManager.enabled = enabled;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getCurrentparties() {
        return currentparties;
    }

    public void setCurrentparties(int currentparties) {
        this.currentparties = currentparties;
    }

    private static boolean enabled = false;

    private int maxSize = 2;
    private int currentparties = 0;

    public PartyManager(UHC plugin) {
        super(plugin);
    }
    
    public void handleClearParties() {
        parties.clear();
        
        for(String uuid : parties.keySet()) {
            handleUnregisterParty(uuid);
        }

        currentparties = 0;
    }

    public void handleUnregisterParty(String uuid) {
        parties.get(uuid).removePlayer(Bukkit.getOfflinePlayer(uuid));
        parties.remove(uuid);
    }

    public void handleAutoPlace() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            Party party = parties.get(player.getName());

            if(party == null) {
                handleCreateParty(player);
            }
        }
    }

    public void handleCreateParty(Player player) {
        currentparties++;
        
        Party currentParty = parties.get(player.getName());
        
        if(currentParty != null) {
            currentParty.removePlayer(player);
        }

        handleRegisterParty(player, new Party(player, currentparties));

        player.getInventory().clear();
        UHCUtils.loadLobby(player);
    }

    public void handleRegisterParty(Player player, Party party) {
        if(party.getSize() == maxSize) {
            player.sendMessage(Color.translate("&cParty can't be longer than &l" + maxSize + "&c."));
            return;
        }
        
        party.addPlayer(player);
        parties.put(player.getName(), party);

    }
    
    public void handleDisbandTeam(Party party) {
        for(String uuid : party.getPlayers()) {
            handleUnregisterParty(uuid);
        }
    }

    public Set<Party> getPartiesSet() {
        Set<Party> toReturn = new HashSet<>();

        toReturn.addAll(parties.values());
        return toReturn;
    }

    public int getPartiesAlive() {
        int i = 0;
        
        for(Party party : getPartiesSet()) {
            if(party.isAlive()) {
                i++;
            }
        }
        
        return i;
    }

    public Party getLastParty() {
        if(getPartiesAlive() == 1) {
            for(Party parties : getPartiesSet()) {
                if(parties.isAlive()) {
                    return parties;
                }
            }
        }
        
        return null;
    }

    public static Party getByPlayer(OfflinePlayer player) {
        return parties.get(player.getName());
    }
}