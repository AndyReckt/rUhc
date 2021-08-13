package me.andyreckt.uhc.managers;

import me.andyreckt.uhc.api.API;
import me.andyreckt.uhc.utilties.Color;
import me.andyreckt.uhc.utilties.Msg;

import me.andyreckt.uhc.utilties.Manager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.player.state.GameState;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;



public class GameManager extends Manager {

    public static GameState getGameState() {
        return gameState;
    }

    public static void setGameState(GameState gameState) {
        GameManager.gameState = gameState;
    }

    public List<UUID> getSaveUsers() {
        return saveUsers;
    }

    public void setSaveUsers(List<UUID> saveUsers) {
        this.saveUsers = saveUsers;
    }

    public List<UUID> getInvTask() {
        return invTask;
    }

    public void setInvTask(List<UUID> invTask) {
        this.invTask = invTask;
    }

    public List<UUID> getOreAlerts() {
        return oreAlerts;
    }

    public void setOreAlerts(List<UUID> oreAlerts) {
        this.oreAlerts = oreAlerts;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getNextuhc() {
        return nextuhc;
    }

    public void setNextuhc(String nextuhc) {
        this.nextuhc = nextuhc;
    }

    public int getShearsRate() {
        return shearsRate;
    }

    public void setShearsRate(int shearsRate) {
        this.shearsRate = shearsRate;
    }

    public int getAppleRate() {
        return appleRate;
    }

    public void setAppleRate(int appleRate) {
        this.appleRate = appleRate;
    }

    public int getInitial() {
        return initial;
    }

    public void setInitial(int initial) {
        this.initial = initial;
    }

    public boolean isPvp() {
        return pvp;
    }

    public void setPvp(boolean pvp) {
        this.pvp = pvp;
    }

    public boolean isMapGenerating() {
        return mapGenerating;
    }

    public void setMapGenerating(boolean mapGenerating) {
        this.mapGenerating = mapGenerating;
    }

    public boolean isWorldUsed() {
        return worldUsed;
    }

    public void setWorldUsed(boolean worldUsed) {
        this.worldUsed = worldUsed;
    }

    public boolean isGenerated() {
        return generated;
    }

    public void setGenerated(boolean generated) {
        this.generated = generated;
    }

    public boolean isRestarted() {
        return restarted;
    }

    public void setRestarted(boolean restarted) {
        this.restarted = restarted;
    }

    public boolean isWorld() {
        return world;
    }

    public void setWorld(boolean world) {
        this.world = world;
    }

    public boolean isBorderTime() {
        return borderTime;
    }

    public void setBorderTime(boolean borderTime) {
        this.borderTime = borderTime;
    }

    public boolean isBorderShrink() {
        return borderShrink;
    }

    public void setBorderShrink(boolean borderShrink) {
        this.borderShrink = borderShrink;
    }

    public boolean isStats() {
        return stats;
    }

    public void setStats(boolean stats) {
        this.stats = stats;
    }

    public boolean isNoScenarios() {
        return noScenarios;
    }

    public void setNoScenarios(boolean noScenarios) {
        this.noScenarios = noScenarios;
    }

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

            player.sendMessage(Color.translate("&eYou have &cdisabled&e xray alerts."));
        } else {
            oreAlerts.add(player.getUniqueId());

            player.sendMessage(Color.translate("&eYou have &aenabled&e xray alerts."));
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
                Msg.sendMessage("&ePvP is now &aEnabled&e.");
            } else {
                Msg.sendMessage("&ePvP is now &cDisabled&e.");
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
                if(invTask.contains(player.getUniqueId()) && !player.getOpenInventory().getTitle().equals(Color.translate("&eScatter Inventory"))) {
                    player.openInventory(InventoryManager.scatter);
                }
            });
        }
    }
}
