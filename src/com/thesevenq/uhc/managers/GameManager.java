package com.thesevenq.uhc.managers;

import com.thesevenq.uhc.api.API;
import com.thesevenq.uhc.utilties.Color;
import com.thesevenq.uhc.utilties.Msg;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.player.state.GameState;
import com.thesevenq.uhc.utilties.Manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
public class GameManager extends Manager {

    @Getter
    @Setter
    private static GameState gameState = GameState.LOBBY;

    private List<UUID> saveUsers = new ArrayList<>();
    private List<UUID> invTask = new ArrayList<>();
    private List<UUID> oreAlerts = new ArrayList<>();

    private String winner = "";
    private String nextuhc = "Unknown";

    private int shearsRate = 1;
    private int appleRate = 2;
    private int initial;

    private boolean pvp = false;
    private boolean mapGenerating = false;
    private boolean worldUsed = false;
    private boolean generated = false;
    private boolean restarted = false;
    private boolean world = false;
    private boolean borderTime = false;
    private boolean borderShrink = true;
    private boolean stats = true;
    private boolean noScenarios = true;

    public GameManager(UHC plugin) {
        super(plugin);

        Date now = new Date();
        int hours = now.getHours();

        // UHC-1 12 16 20 24

        // UHC-2 14 18 22

        if(API.getServerName().equals("UHC-1")) {
            if(hours >= 21) {
                nextuhc = "00:00";
            } else if(hours >= 17) {
                nextuhc = "20:00";
            } else if(hours >= 13) {
                nextuhc = "16:00";
            } else {
                nextuhc = "12:00";
            }
        } else if(API.getServerName().equals("UHC-2")) {
            if(hours >= 19) {
                nextuhc = "22:00";
            } else if(hours >= 15) {
                nextuhc = "18:00";
            } else {
                nextuhc = "14:00";
            }
        }
    }

    public void handleOnDisable() {
        saveUsers.clear();
        invTask.clear();
        oreAlerts.clear();
    }

    public void handleOreAlerts(Player player) {
        if(oreAlerts.contains(player.getUniqueId())) {
            oreAlerts.remove(player.getUniqueId());

            player.sendMessage(Color.translate("&bYou have &cdisabled&b xray alerts."));
        } else {
            oreAlerts.add(player.getUniqueId());

            player.sendMessage(Color.translate("&bYou have &aenabled&b xray alerts."));
        }
    }

    public void handleRemoveSaveUser(Player player) {
        if(saveUsers.contains(player.getUniqueId())) {
            saveUsers.remove(player.getUniqueId());
        }
    }

    public void setPvP(boolean bol) {
        if(Bukkit.getWorld("uhc_world") != null) {
            pvp = bol;

            Bukkit.getWorld("uhc_world").setPVP(bol);

            if(Bukkit.getWorld("uhc_nether") != null) {
                Bukkit.getWorld("uhc_nether").setPVP(bol);
            }

            if(bol) {
                Msg.sendMessage("&bPvP is now &aEnabled&b.");
            } else {
                Msg.sendMessage("&bPvP is now &cDisabled&b.");
            }
        }
    }

    public class InventoryTask extends BukkitRunnable {

        public InventoryTask() {
            runTaskTimerAsynchronously(UHC.getInstance(), 1L, 1L);
        }

        @Override
        public void run() {
            Bukkit.getOnlinePlayers().forEach(player -> {
                if(invTask.contains(player.getUniqueId()) && !player.getOpenInventory().getTitle().equals(Color.translate("&bScatter Inventory"))) {
                    player.openInventory(InventoryManager.scatter);
                }
            });
        }
    }
}
