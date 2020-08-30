package com.thesevenq.uhc.providers;


import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.adapters.board.AssembleAdapter;
import com.thesevenq.uhc.commands.arguments.GameCommand;
import com.thesevenq.uhc.managers.*;
import com.thesevenq.uhc.player.UHCData;
import com.thesevenq.uhc.player.party.Party;
import com.thesevenq.uhc.scenario.Scenario;
import com.thesevenq.uhc.scenario.type.NoCleanScenario;
import com.thesevenq.uhc.tasks.AutoStartTask;
import com.thesevenq.uhc.tasks.BorderTimeTask;
import com.thesevenq.uhc.tasks.DotsTask;
import com.thesevenq.uhc.tasks.GameTask;
import com.thesevenq.uhc.utilties.*;
import io.netty.handler.codec.http.multipart.AbstractDiskHttpData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BoardAdapter implements AssembleAdapter {

    @Override
    public String getTitle(Player player) {
        // return Color.translate("&3&lSicaro &7" + Msg.LINE + " &fUHC-1");
        return Color.translate("&b&lUHC");
    }

    @Override
    public List<String> getLines(Player player) {
        final List<String> board = new ArrayList<>();

        String line = Color.translate("&7&m--------------------");

        UHCData uhcData = UHCData.getByName(player.getName());

        if (UHC.getInstance().getPracticeManager().getUsers().contains(player.getUniqueId())) {
            add(board, line);
            add(board, "&fOnline: &b" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers());
            add(board, "&fIn Arena: &b" + UHC.getInstance().getPracticeManager().getUsers().size() + "/" + UHC.getInstance().getPracticeManager().getSlots());
            if (AutoStartTask.running) {
                add(board, "&fStarting in &b" + StringUtils.getRemaining(AutoStartTask.seconds, false));
            }
            add(board, "");
            add(board, "&b&brujtex.eu");
            add(board, line);
        } else {
            switch (GameManager.getGameState()) {
                case LOBBY: {
                    add(board, line);
                    add(board, "&fOnline: &b" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers());

                    if (UHC.getInstance().getPracticeManager().isOpen()) {
                        if (player.hasPermission(Permission.STAFF_PERMISSION)) {
                            add(board, "&fPractice: &b" + UHC.getInstance().getPracticeManager().getUsers().size() + "/" + UHC.getInstance().getPracticeManager().getSlots());
                        }
                    }

                    add(board, "&fGame Type: &b" + UHCUtils.isPartiesEnabled());
                    if(PartyManager.isEnabled()) {
                        Party party = PartyManager.getByPlayer(player);

                        if(party == null) {
                        } else {
                            add(board, "");
                            add(board, "&bParty&7:");
                            add(board, " &fLeader&7: &b" + party.getOwner().getName());
                            add(board, " &fMembers&7: &b" + party.getPlayers().size());
                        }
                    }

                    if(OptionManager.getByName("Nether").getValue() != 1) {
                        add(board, "");
                        add(board, "&fNether&7: " + "&c&l" + Symbols.X); //bog te gde je border ovde
                    } else {
                        add(board, "");
                        add(board, "&fNether&7: " + "&a&l" +Symbols.CHECKMARK);
                    }
                    /*add(board, "&fScenarios:");

                    if (ScenarioManager.getActiveScenarios() == 0) {
                        add(board, "&fAll scenarios are");
                        add(board, "&fcurrently &bdisabled&f.");
                    } else {
                        for (Scenario scenario : ScenarioManager.scenarios) {
                            if (scenario.isEnabled()) {
                                add(board, " &7" + Msg.KRUZIC + " &b" + scenario.getName());
                            }
                        }
                    }*/
                    if (AutoStartTask.running) {
                        add(board, "");
                        //add(board, "&fStarting in &b" + StringUtils.getRemaining(AutoStartTask.seconds, false));
                        add(board, "&fGame will start");
                        add(board, "&fin &b" + StringUtils.getRemaining(AutoStartTask.seconds, false) + "&f" + DotsTask.getInstance().getString());
                    }
                    add(board, "");
                    add(board, "&b&brujtex.eu");
                    add(board, line);
                    break;
                }

                case SCATTERING: {
                    add(board, line);

                    if (GameCommand.getInt() < 1) {
                        add(board, "&fStarting&b...");
                    } else {
                        add(board, "&fStarting in: &b" + StringUtils.formatInt(GameCommand.startsIn()));
                    }

                    add(board, "");

                    if (GameCommand.getInt() < 1) {
                        add(board, "&fEveryone is scattered&7.");
                    } else {
                        add(board, "&fScattering: &b" + GameCommand.getInt() + " players");
                    }
                    add(board, "&fScattered: &b" + PlayerManager.getAlivePlayers() + " players");

                    add(board, "");
                    add(board, "&b&brujtex.eu");
                    add(board, line);
                    break;
                }

                case PLAYING: {
                    if (player.hasPermission(Permission.STAFF_PERMISSION)) {
                        if (UHCUtils.isPlayerInSpecMode(player)) {
                            add(board, line);
                            add(board, "&3&lStaff Mode:");
                            add(board, " &7* &fOnline: &b" + Bukkit.getOnlinePlayers().size());

                            DecimalFormat dc = new DecimalFormat("##.#");
                            double tps = Bukkit.spigot().getTPS()[0];

                            add(board, " &7* &fTPS: &b" + dc.format(tps));
                            add(board, line);
                        }
                    }

                    add(board, line);
                    add(board, "&fGame Time&7: &b" + StringUtils.formatInt(GameTask.seconds));
                    if (uhcData.isAlive()) {
                        add(board, "&fYour Kills&7: &b" + uhcData.getKills());

                        if (PartyManager.isEnabled()) {
                            add(board, "&fTeam Kills&7: &b" + PartyManager.getByPlayer(player).getKills());
                        }
                    }
                    add(board, "&fRemaining&7: &b" + PlayerManager.getAlivePlayers() + "/" + UHC.getInstance().getGameManager().getInitial());
                    if (UHC.getInstance().getGameManager().isBorderTime()) {
                        int i = BorderTimeTask.seconds;

                        if (BorderManager.border > 25) {
                            if (i < 60) {
                                add(board, "&fBorder&7: &b" + BorderManager.border + " &7(&3" + i + "s&7)");
                            } else {
                                add(board, "&fBorder&7: &b" + BorderManager.border + " &7(&3" + (i / 60 + 1) + "m&7)");
                            }

                            if (i < 0) {
                                add(board, "&fBorder&7: &b" + BorderManager.border);
                            }
                        }
                    } else {
                        add(board, "&fBorder&7: &b" + BorderManager.border);
                    }

                    if (NoCleanScenario.isActive(player)) {
                        add(board, "&cInvincibility&7: &c" + StringUtils.getRemaining(NoCleanScenario.getMillisecondsLeft(player), true));
                    }

                    if (GameTask.seconds < GameTask.heal_time) {
                        board.add("");
                        board.add("&b&lEvents&7:");
                        board.add("&fFinal Heal: &b" + StringUtils.formatInt(GameTask.heal_time - GameTask.seconds));
                    }

                    if (GameTask.seconds < GameTask.pvp_time) {
                        board.add("&fPvP Enable: &b" + StringUtils.formatInt(GameTask.pvp_time - GameTask.seconds));
                    }

                    add(board, "");
                    add(board, "&b&brujtex.eu");
                    add(board, line);
                    break;
                }

                case WINNER: {
                    add(board, line);
                    if (PartyManager.isEnabled()) {
                        if (UHC.getInstance().getPartyManager().getPartiesAlive() == 1) {
                            add(board, "&fWinners:");

                            for (String team : UHC.getInstance().getPartyManager().getLastParty().getPlayers()) {
                                UHCData teamPlayer = UHCData.getByName(Bukkit.getPlayer(team).getName());

                                if (teamPlayer.isAlive()) {
                                    add(board, " &7* &b" + teamPlayer.getName());
                                }
                            }
                        }
                    } else {
                        if (PlayerManager.getAlivePlayers() < 2) {
                            add(board, "&fWinner: &b" + UHC.getInstance().getGameManager().getWinner());
                        }
                    }
                    add(board, "");
                    add(board, "&b&brujtex.eu");
                    add(board, line);
                    break;
                }
            }
        }

        return board;
    }

    private void add(List list, String text) {
        list.add(Color.translate(text));
    }
}

