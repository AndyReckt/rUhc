package com.thesevenq.uhc.providers;

import com.thesevenq.uhc.managers.BorderManager;
import com.thesevenq.uhc.managers.OptionManager;
import com.thesevenq.uhc.managers.ScenarioManager;
import com.thesevenq.uhc.player.UHCData;
import com.thesevenq.uhc.scenario.Scenario;
import com.thesevenq.uhc.utilties.Color;
import com.thesevenq.uhc.utilties.Msg;
import com.thesevenq.uhc.utilties.UHCUtils;
import me.allen.ziggurat.ZigguratAdapter;
import me.allen.ziggurat.objects.BufferedTabObject;
import me.allen.ziggurat.objects.TabColumn;
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

        tab.add(new BufferedTabObject().text("&3&lSicaro Network").column(TabColumn.MIDDLE).slot(2));
        tab.add(new BufferedTabObject().text("&b" + Bukkit.getOnlinePlayers().size() + " online").column(TabColumn.MIDDLE).slot(3));

        tab.add(new BufferedTabObject().text("&3&lGame Info").column(TabColumn.MIDDLE).slot(5));
        tab.add(new BufferedTabObject().text("&fFinal Heal&7: &b" + OptionManager.getByNameAndTranslate("Final Heal") + " min").column(TabColumn.MIDDLE).slot(6));
        tab.add(new BufferedTabObject().text("&fShrink Time&7: &b" + OptionManager.getByNameAndTranslate("First Shrink") + " min").column(TabColumn.MIDDLE).slot(7));
        tab.add(new BufferedTabObject().text("&fPvP Time&7: &b" + OptionManager.getByNameAndTranslate("PvP Period Duration") + " min").column(TabColumn.MIDDLE).slot(8));

        tab.add(new BufferedTabObject().text("&fType&7: &b" + UHCUtils.isPartiesEnabled()).column(TabColumn.LEFT).slot(6));
        tab.add(new BufferedTabObject().text("&fBorder&7: &b" + BorderManager.border).column(TabColumn.LEFT).slot(7));
        tab.add(new BufferedTabObject().text("&fMax Players&7: &b" + Bukkit.getMaxPlayers()).column(TabColumn.LEFT).slot(8));

        tab.add(new BufferedTabObject().text("&fNether&7: &b" + (nether ? "On" : "Off")).column(TabColumn.RIGHT).slot(6));
        tab.add(new BufferedTabObject().text("&fSpeed&7: &b" + (speed ? "On" : "Off")).column(TabColumn.RIGHT).slot(7));
        tab.add(new BufferedTabObject().text("&fSpectating&7: &bOn").column(TabColumn.RIGHT).slot(8));

        tab.add(new BufferedTabObject().text("&3&lPlayer Info").column(TabColumn.MIDDLE).slot(10));
        tab.add(new BufferedTabObject().text("&fWins&7: &b" + data.getWins()).column(TabColumn.MIDDLE).slot(11));
        tab.add(new BufferedTabObject().text("&fKill Streak&7: &b" + data.getKillStreak()).column(TabColumn.MIDDLE).slot(12));

        tab.add(new BufferedTabObject().text("&fKills&7: &b" + data.getKills()).column(TabColumn.LEFT).slot(11));
        tab.add(new BufferedTabObject().text("&fDeaths&7: &b" + data.getDeaths()).column(TabColumn.LEFT).slot(12));

        tab.add(new BufferedTabObject().text("&fDiamonds&7: &b" + data.getTotalDiamondsMined()).column(TabColumn.RIGHT).slot(11));
        tab.add(new BufferedTabObject().text("&fPlayed&7: &b" + data.getPlayed()).column(TabColumn.RIGHT).slot(12));

        tab.add(new BufferedTabObject().text("&3&lTop Killers").column(TabColumn.LEFT).slot(14));
        tab.add(new BufferedTabObject().text("&fThere are currently").column(TabColumn.LEFT).slot(15));
        tab.add(new BufferedTabObject().text("&b0 &ftop killers.").column(TabColumn.LEFT).slot(16));

        tab.add(new BufferedTabObject().text("&3&lScenarios").column(TabColumn.RIGHT).slot(14));
        int x = 15;
        for (Scenario scenario : ScenarioManager.scenarios) {
            if (scenario.isEnabled()) {
                tab.add(new BufferedTabObject().text(" &7" + Msg.KRUZIC + " &b" + scenario.getName()).column(TabColumn.RIGHT).slot(x));
                x++;
            }
        }
        if(x == 14) {
            tab.add(new BufferedTabObject().text("&fAll scenarios are").column(TabColumn.RIGHT).slot(14));
            tab.add(new BufferedTabObject().text("&fcurrently &bdisabled&f.").column(TabColumn.RIGHT).slot(15));

        }

        return tab;
    }


    @Override
    public String getFooter() {
        return "&b&lUHC";
    }

    @Override
    public String getHeader() {
        return "&bsicaro.club &7- &bstore.sicaro.club &7- &bts.sicaro.club";
    }
}
