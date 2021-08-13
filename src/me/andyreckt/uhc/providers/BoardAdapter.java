package me.andyreckt.uhc.providers;


import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.adapters.board.AssembleAdapter;
import me.andyreckt.uhc.commands.arguments.GameCommand;
import me.andyreckt.uhc.managers.*;
import me.andyreckt.uhc.player.UHCData;
import me.andyreckt.uhc.player.party.Party;
import me.andyreckt.uhc.player.state.GameState;
import me.andyreckt.uhc.scenario.type.NoCleanScenario;
import me.andyreckt.uhc.tasks.*;
import me.andyreckt.uhc.utilties.*;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BoardAdapter implements AssembleAdapter {


    @Override
    public String getTitle(Player player) {
        // return Color.translate("&9&lSicaro &7" + Msg.LINE + " &eUHC-1");
        return Color.translate("&9&lUHC");
    }

    @Override
    public List<String> getLines(Player player) {
        final List<String> board = new ArrayList<>();

        String line = Color.translate("&7&m--------------------");
        UHCData uhcData = UHCData.getByName(player.getName());

        if (UHC.getInstance().getPracticeManager().getUsers().contains(player.getUniqueId())) {
            add(board, line);
            add(board, "&eOnline: &9" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers());
            add(board, "&eIn Arena: &9" + UHC.getInstance().getPracticeManager().getUsers().size() + "/" + UHC.getInstance().getPracticeManager().getSlots());
            if (AutoStartTask.running) {
                add(board, "&eStarting in &9" + StringUtils.getRemaining(AutoStartTask.seconds, false));
            }
            add(board, "");
            add(board, "&7&oReality.tk");
            add(board, line);
        } else {
                if (GameManager.getGameState() == GameState.LOBBY) {
                    add(board, line);
                    add(board, "&eOnline: &9" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers());

                    if (UHC.getInstance().getPracticeManager().isOpen()) {
                        if (player.hasPermission(Permission.STAFF_PERMISSION)) {
                            add(board, "&ePractice: &9" + UHC.getInstance().getPracticeManager().getUsers().size() + "/" + UHC.getInstance().getPracticeManager().getSlots());
                        }
                    }

                    add(board, "&eGame Type: &9" + UHCUtils.isPartiesEnabled());
                    if(PartyManager.isEnabled()) {
                        Party party = PartyManager.getByPlayer(player);

                        if(party == null) {
                        } else {
                            add(board, "");
                            add(board, "&9Party&7:");
                            add(board, " &eLeader&7: &9" + party.getOwner().getName());
                            add(board, " &eMembers&7: &9" + party.getPlayers().size());
                        }
                    }


                    if(OptionManager.getByName("Nether").getValue() != 1) {
                        add(board, "");
                        add(board, "&eNether&7: " + "&c&l" + Symbols.X); //bog te gde je border ovde
                    } else {
                        add(board, "");
                        add(board, "&eNether&7: " + "&a&l" +Symbols.CHECKMARK);
                    }
                    /*add(board, "&eScenarios:");

                    if (ScenarioManager.getActiveScenarios() == 0) {
                        add(board, "&eAll scenarios are");
                        add(board, "&ecurrently &9disabled&e.");
                    } else {
                        for (Scenario scenario : ScenarioManager.scenarios) {
                            if (scenario.isEnabled()) {
                                add(board, " &7" + Msg.KRUZIC + " &9" + scenario.getName());
                            }
                        }
                    }*/
                    if (AutoStartTask.running) {
                        add(board, "");
                        //add(board, "&eStarting in &9" + StringUtils.getRemaining(AutoStartTask.seconds, false));
                        add(board, "&eGame will start");
                        add(board, "&ein &9" + StringUtils.getRemaining(AutoStartTask.seconds, false) + "&e" + DotsTask.getInstance().getString());
                    }
                    add(board, "");
                    add(board, "&9&9reality.tk");
                    add(board, line);
                }

                if(GameManager.getGameState() == GameState.SCATTERING) {
                    add(board, line);

                    if (GameCommand.getInt() < 1) {
                        add(board, "&eStarting&9...");
                    } else {
                        add(board, "&eStarting in: &9" + StringUtils.formatInt(GameCommand.startsIn()));
                    }

                    add(board, "");

                    if (GameCommand.getInt() < 1) {
                        add(board, "&eEveryone is scattered&7.");
                    } else {
                        add(board, "&eScattering: &9" + GameCommand.getInt() + 1 + " players");
                    }
                    add(board, "&eScattered: &9" + PlayerManager.getAlivePlayers() + " players");

                    add(board, "");
                    add(board, "&9&9reality.tk");
                    add(board, line);

                }

            if(GameManager.getGameState() == GameState.PLAYING) {
                    if (player.hasPermission(Permission.STAFF_PERMISSION)) {
                        if (UHCUtils.isPlayerInSpecMode(player)) {
                            add(board, line);
                            add(board, "&9&lStaff Mode:");
                            add(board, " &7* &eOnline: &9" + Bukkit.getOnlinePlayers().size());

                            DecimalFormat dc = new DecimalFormat("##.#");
                            String tps = this.getTPS();

                            add(board, " &7* &eTPS: &9" + tps.toString());
                        }
                    }

                    add(board, line);
                    add(board, "&eGame Time&7: &9" + StringUtils.formatInt(GameTask.seconds));
                    if (uhcData.isAlive()) {
                        add(board, "&eYour Kills&7: &9" + uhcData.getKills());

                        if (PartyManager.isEnabled()) {
                            add(board, "&eTeam Kills&7: &9" + PartyManager.getByPlayer(player).getKills());
                        }
                    }
                    add(board, "&eRemaining&7: &9" + PlayerManager.getAlivePlayers() + "/" + UHC.getInstance().getGameManager().getInitial());
                    if (UHC.getInstance().getGameManager().isBorderTime()) {
                        int i = BorderTimeTask.seconds;

                        if (BorderManager.border > 25) {
                            if (i < 60) {
                                add(board, "&eBorder&7: &9" + BorderManager.border + " &7(&9" + i + "s&7)");
                            } else {
                                add(board, "&eBorder&7: &9" + BorderManager.border + " &7(&9" + (i / 60 + 1) + "m&7)");
                            }

                            if (i < 0) {
                                add(board, "&eBorder&7: &9" + BorderManager.border);
                            }
                        }
                    } else {
                        add(board, "&eBorder&7: &9" + BorderManager.border);
                    }

                    if (NoCleanScenario.isActive(player)) {
                        add(board, "&cInvincibility&7: &c" + StringUtils.getRemaining(NoCleanScenario.getMillisecondsLeft(player), true));
                    }

                    if (GameTask.seconds < GameTask.heal_time) {
                        board.add("");
                        board.add("&9&lEvents&7:");
                        board.add("&eFinal Heal: &9" + StringUtils.formatInt(GameTask.heal_time - GameTask.seconds));
                    }

                    if (GameTask.seconds < GameTask.pvp_time) {
                        board.add("&ePvP Enable: &9" + StringUtils.formatInt(GameTask.pvp_time - GameTask.seconds));
                    }

                    add(board, "");
                    add(board, "&9&9reality.tk");
                    add(board, line);

                }

            if(GameManager.getGameState() == GameState.WINNER) {
                    add(board, line);
                    if (PartyManager.isEnabled()) {
                        if (UHC.getInstance().getPartyManager().getPartiesAlive() == 1) {
                            add(board, "&eWinners:");

                            for (String team : UHC.getInstance().getPartyManager().getLastParty().getPlayers()) {
                                UHCData teamPlayer = UHCData.getByName(Bukkit.getPlayer(team).getName());

                                if (teamPlayer.isAlive()) {
                                    add(board, " &7* &9" + teamPlayer.getName());
                                }
                            }
                        }
                    } else {
                        if (PlayerManager.getAlivePlayers() < 2) {
                            add(board, "&eWinner: &9" + UHC.getInstance().getGameManager().getWinner());
                        }
                    }
                    add(board, "");
                    add(board, "&9&9reality.tk");
                    add(board, line);

                }
            }

        return board;
    }
    private String getTPS(){
        StringBuilder sb = new StringBuilder(" ");
        for ( double tps : MinecraftServer.getServer().recentTps )
        {
            sb.append(format(tps));
            sb.append( ", " );
        }
        return sb.substring( 0, sb.length() - 2 );
    }

    private void add(List list, String text) {
        list.add(Color.translate(text));
    }
    private String format(double tps)
    {
        return ( ( tps > 18.0 ) ? org.bukkit.Color.GREEN : ( tps > 16.0 ) ? org.bukkit.Color.YELLOW : org.bukkit.Color.RED ).toString()
                + ( ( tps > 20.0 ) ? "*" : "" ) + Math.min( Math.round( tps * 100.0 ) / 100.0, 20.0 );
    }

}

