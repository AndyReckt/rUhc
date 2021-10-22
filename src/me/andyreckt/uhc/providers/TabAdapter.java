package me.andyreckt.uhc.providers;

import me.andyreckt.uhc.managers.BorderManager;
import me.andyreckt.uhc.managers.OptionManager;
import me.andyreckt.uhc.managers.ScenarioManager;
import me.andyreckt.uhc.player.UHCData;
import me.andyreckt.uhc.scenario.Scenario;
import me.andyreckt.uhc.utilties.Color;
import me.andyreckt.uhc.utilties.Msg;
import me.andyreckt.uhc.utilties.UHCUtils;
import io.github.thatkawaiisam.ziggurat.ZigguratAdapter;
import io.github.thatkawaiisam.ziggurat.utils.BufferedTabObject;
import io.github.thatkawaiisam.ziggurat.utils.TabColumn;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.HashSet;
import java.util.Set;

public class TabAdapter implements ZigguratAdapter {

    boolean nether = OptionManager.getByName("Nether").getValue() == 1;
    boolean speed = OptionManager.getByName("Speed Potions").getValue() == 1;


    @Override
    public Set<BufferedTabObject> getSlots(Player player) {
        Set<BufferedTabObject> tab = new HashSet<>();

        UHCData data = UHCData.getByName(player.getName());

        tab.add(new BufferedTabObject().text("&9&lReality").column(TabColumn.MIDDLE).slot(2));
        tab.add(new BufferedTabObject().text("&9" + Bukkit.getOnlinePlayers().size() + " online").column(TabColumn.MIDDLE).slot(3));

                tab.add(new BufferedTabObject().text("&a&lGame Info").column(TabColumn.LEFT).slot(5));
        tab.add(new BufferedTabObject().text("&eFinal Heal&7: &9" + OptionManager.getByNameAndTranslate("Final Heal") + " min").column(TabColumn.LEFT).slot(9));
        tab.add(new BufferedTabObject().text("&eShrink Time&7: &9" + OptionManager.getByNameAndTranslate("First Shrink") + " min").column(TabColumn.LEFT).slot(10));
        tab.add(new BufferedTabObject().text("&ePvP Time&7: &9" + OptionManager.getByNameAndTranslate("PvP Period Duration") + " min").column(TabColumn.LEFT).slot(11));

        tab.add(new BufferedTabObject().text("&eType&7: &9" + UHCUtils.isPartiesEnabled()).column(TabColumn.LEFT).slot(6));
        tab.add(new BufferedTabObject().text("&eBorder&7: &9" + BorderManager.border).column(TabColumn.LEFT).slot(7));
        tab.add(new BufferedTabObject().text("&eMax Players&7: &9" + Bukkit.getMaxPlayers()).column(TabColumn.LEFT).slot(8));

        tab.add(new BufferedTabObject().text("&eNether&7: &9" + (nether ? "&aOn" : "Off")).column(TabColumn.LEFT).slot(12));
        tab.add(new BufferedTabObject().text("&eSpeed&7: &9" + (speed ? "&aOn" : "Off")).column(TabColumn.LEFT).slot(13));
        tab.add(new BufferedTabObject().text("&eSpectating&7: &aOn").column(TabColumn.LEFT).slot(14));

        tab.add(new BufferedTabObject().text("&a&lPlayer Info").column(TabColumn.MIDDLE).slot(5));
        tab.add(new BufferedTabObject().text("&eWins&7: &9" + data.getWins()).column(TabColumn.MIDDLE).slot(6));
        tab.add(new BufferedTabObject().text("&eKill Streak&7: &9" + data.getKillStreak()).column(TabColumn.MIDDLE).slot(8));
        tab.add(new BufferedTabObject().text("&eKills&7: &9" + data.getKills()).column(TabColumn.MIDDLE).slot(10));
        tab.add(new BufferedTabObject().text("&eTotal Kills&7: &9" + data.getTotalKills()).column(TabColumn.MIDDLE).slot(11));
        tab.add(new BufferedTabObject().text("&eDeaths&7: &9" + data.getDeaths()).column(TabColumn.MIDDLE).slot(12));
        tab.add(new BufferedTabObject().text("&eDiamonds&7: &9" + data.getTotalDiamondsMined()).column(TabColumn.MIDDLE).slot(9));
        tab.add(new BufferedTabObject().text("&ePlayed&7: &9" + data.getPlayed()).column(TabColumn.MIDDLE).slot(7));
        /*
        tab.add(new BufferedTabObject().text("&9&lTop Killers").column(TabColumn.LEFT).slot(11));
        tab.add(new BufferedTabObject().text("&eThere are currently").column(TabColumn.LEFT).slot(12));
        tab.add(new BufferedTabObject().text("&90 &etop killers.").column(TabColumn.LEFT).slot(13));
        */

        tab.add(new BufferedTabObject().text("&a&lScenarios").column(TabColumn.RIGHT).slot(5));
        int x = 6;
        for (Scenario scenario : ScenarioManager.scenarios) {
            if (scenario.isEnabled()) {
                tab.add(new BufferedTabObject().text(" &7" + Msg.KRUZIC + " &9" + scenario.getName()).column(TabColumn.RIGHT).slot(x));
                x++;
            }
        }
        if(x == 6) {
            tab.add(new BufferedTabObject().text("&eAll scenarios are").column(TabColumn.RIGHT).slot(6));
            tab.add(new BufferedTabObject().text("&ecurrently &9disabled&e.").column(TabColumn.RIGHT).slot(7));
        }

        return tab;
    }


    @Override
    public String getHeader() {
        return Color.translate("&9&lUHC");
    }

    @Override
    public String getFooter() {
        return Color.translate("&9reality.tk &7- &9store.reality.tk &7- &9discord.reality.tk");
    }
}
