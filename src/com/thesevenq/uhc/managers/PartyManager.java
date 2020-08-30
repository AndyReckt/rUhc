package com.thesevenq.uhc.managers;

import com.thesevenq.uhc.utilties.Color;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.player.party.Party;
import com.thesevenq.uhc.utilties.Manager;
import com.thesevenq.uhc.utilties.UHCUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class PartyManager extends Manager {

    @Getter
    private static Map<String, Party> parties = new HashMap<>();

    @Getter
    @Setter
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
        UHCUtils.loadLobbyInventory(player);
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