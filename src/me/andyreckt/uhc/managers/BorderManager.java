package me.andyreckt.uhc.managers;

import me.andyreckt.uhc.utilties.Color;
import me.andyreckt.uhc.utilties.Manager;
import me.andyreckt.uhc.utilties.UHCUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.border.Border;
import me.andyreckt.uhc.border.InvisibleFix;
import me.andyreckt.uhc.player.party.Party;
import me.andyreckt.uhc.tasks.BorderTask;
import me.andyreckt.uhc.tasks.BorderTimeTask;

import java.util.Arrays;

public class BorderManager extends Manager {

    public static int border;
    public static Player player;

    public BorderManager(UHC plugin) {
        super(plugin);
    }

    public static void checkBorder(Player player) {
        int size = border;
        World world = player.getWorld();

        if (world.getName().equalsIgnoreCase("uhc_world")) {
            if (player.getLocation().getBlockX() > size) {
                player.teleport(new Location(world, size - 2, player.getLocation().getBlockY(), player.getLocation().getBlockZ()));
                if (player.getLocation().getBlockY() < world.getHighestBlockYAt(player.getLocation().getBlockX(), player.getLocation().getBlockZ())) {
                    player.teleport(new Location(world, player.getLocation().getBlockX(), world.getHighestBlockYAt(player.getLocation().getBlockX(), player.getLocation().getBlockZ()) + 2, player.getLocation().getBlockZ()));
                    player.sendMessage(Color.translate("&eYou have reached the border."));
                }
            }
            if (player.getLocation().getBlockZ() > size) {
                player.teleport(new Location(world, player.getLocation().getBlockX(), player.getLocation().getBlockY(), size - 2));
                if (player.getLocation().getBlockY() < world.getHighestBlockYAt(player.getLocation().getBlockX(), player.getLocation().getBlockZ())) {
                    player.teleport(new Location(world, player.getLocation().getBlockX(), world.getHighestBlockYAt(player.getLocation().getBlockX(), player.getLocation().getBlockZ()) + 2, player.getLocation().getBlockZ()));
                    player.sendMessage(Color.translate("&eYou have reached the border."));
                }
            }
            if (player.getLocation().getBlockX() < -size) {
                player.teleport(new Location(world, -size + 2, player.getLocation().getBlockY(), player.getLocation().getBlockZ()));
                if (player.getLocation().getBlockY() < world.getHighestBlockYAt(player.getLocation().getBlockX(), player.getLocation().getBlockZ())) {
                    player.teleport(new Location(world, player.getLocation().getBlockX(), world.getHighestBlockYAt(player.getLocation().getBlockX(), player.getLocation().getBlockZ()) + 2, player.getLocation().getBlockZ()));
                    player.sendMessage(Color.translate("&eYou have reached the border."));
                }
            }
            if (player.getLocation().getBlockZ() < -size) {
                player.teleport(new Location(world, player.getLocation().getBlockX(), player.getLocation().getBlockY(), -size + 2));
                if (player.getLocation().getBlockY() < world.getHighestBlockYAt(player.getLocation().getBlockX(), player.getLocation().getBlockZ())) {
                    player.teleport(new Location(world, player.getLocation().getBlockX(), world.getHighestBlockYAt(player.getLocation().getBlockX(), player.getLocation().getBlockZ()) + 2, player.getLocation().getBlockZ()));
                    player.sendMessage(Color.translate("&eYou have reached the border."));
                }
            }
        }
    }

    public static void shrinkBorder(int size, BukkitRunnable runnable) {
        new BorderTask().runTaskTimerAsynchronously(UHC.getInstance(), 200L, 200L);
        runnable.cancel();

        border = size;

        World w = Bukkit.getWorld("uhc_world");

        new Border(w, size);

        if (size > 500) {
            for (Player player : w.getPlayers()) {
                if (player.getLocation().getBlockX() > size) {
                    player.setNoDamageTicks(59);
                    player.setFallDistance(0.0f);

                    player.teleport(new Location(w, size - 4, (w.getHighestBlockYAt(size - 4, player.getLocation().getBlockZ()) + 0.5), player.getLocation().getBlockZ()));
                    player.setFallDistance(0.0f);

                    player.getLocation().add(0, 2, 0).getBlock().setType(Material.AIR);
                    player.getLocation().add(0, 3, 0).getBlock().setType(Material.AIR);
                    player.getLocation().add(0, 4, 0).getBlock().setType(Material.AIR);

                    player.teleport(new Location(w, player.getLocation().getBlockX(), w.getHighestBlockAt(player.getLocation().getBlockX(), player.getLocation().getBlockZ()).getLocation().getBlockY() + 0.5, player.getLocation().getBlockZ()));
                }

                if (player.getLocation().getBlockZ() > size) {
                    player.setNoDamageTicks(59);
                    player.setFallDistance(0.0f);

                    player.teleport(new Location(w, player.getLocation().getBlockX(), (w.getHighestBlockYAt(player.getLocation().getBlockX(), size - 4) + 0.5), size - 4));
                    player.setFallDistance(0.0f);

                    player.getLocation().add(0, 2, 0).getBlock().setType(Material.AIR);
                    player.getLocation().add(0, 3, 0).getBlock().setType(Material.AIR);
                    player.getLocation().add(0, 4, 0).getBlock().setType(Material.AIR);

                    player.teleport(new Location(w, player.getLocation().getBlockX(), w.getHighestBlockAt(player.getLocation().getBlockX(), player.getLocation().getBlockZ()).getLocation().getBlockY() + 0.5, player.getLocation().getBlockZ()));
                }

                if (player.getLocation().getBlockX() < -size) {
                    player.setNoDamageTicks(59);
                    player.setFallDistance(0.0f);

                    player.teleport(new Location(w, -size + 4, (w.getHighestBlockYAt(-size + 4, player.getLocation().getBlockZ()) + 0.5), player.getLocation().getBlockZ()));
                    player.setFallDistance(0.0f);

                    player.getLocation().add(0, 2, 0).getBlock().setType(Material.AIR);
                    player.getLocation().add(0, 3, 0).getBlock().setType(Material.AIR);
                    player.getLocation().add(0, 4, 0).getBlock().setType(Material.AIR);

                    player.teleport(new Location(w, player.getLocation().getBlockX(), w.getHighestBlockAt(player.getLocation().getBlockX(), player.getLocation().getBlockZ()).getLocation().getBlockY() + 0.5, player.getLocation().getBlockZ()));

                }

                if (player.getLocation().getBlockZ() < -size) {
                    player.setNoDamageTicks(59);
                    player.setFallDistance(0.0f);

                    player.teleport(new Location(w, player.getLocation().getBlockX(), (w.getHighestBlockYAt(player.getLocation().getBlockX(), -size + 4) + 0.5), -size + 4));
                    player.setFallDistance(0.0f);

                    player.getLocation().add(0, 2, 0).getBlock().setType(Material.AIR);
                    player.getLocation().add(0, 3, 0).getBlock().setType(Material.AIR);
                    player.getLocation().add(0, 4, 0).getBlock().setType(Material.AIR);

                    player.teleport(new Location(w, player.getLocation().getBlockX(), w.getHighestBlockAt(player.getLocation().getBlockX(), player.getLocation().getBlockZ()).getLocation().getBlockY() + 0.5, player.getLocation().getBlockZ()));
                }
            }
        } else {
            if (PartyManager.isEnabled()) {
                for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                    if ((!online.getWorld().equals(UHCUtils.getSpawnLocation().getWorld())) && ((online.getLocation().getBlockX() > size) || (online.getLocation().getBlockZ() > size) || (online.getLocation().getBlockX() < -size) || (online.getLocation().getBlockZ() < -size) || (online.getWorld().getName().equalsIgnoreCase("uhc_nether")))) {
                        Party party = PartyManager.getByPlayer(online);
                        party.setScatterLocation(UHCUtils.getScatterLocation());

                        for (String teamPlayers : party.getPlayers()) {
                            Player player = Bukkit.getServer().getPlayer(teamPlayers);
                            if (player != null) {
                                online.setNoDamageTicks(59);
                                online.setFallDistance(0.0f);

                                player.teleport(party.getScatterLocation());

                                online.setFallDistance(0.0f);

                                online.getLocation().add(0, 2, 0).getBlock().setType(Material.AIR);
                                online.getLocation().add(0, 3, 0).getBlock().setType(Material.AIR);
                                online.getLocation().add(0, 4, 0).getBlock().setType(Material.AIR);


                                online.teleport(new Location(w, online.getLocation().getBlockX(), w.getHighestBlockAt(online.getLocation().getBlockX(), online.getLocation().getBlockZ()).getLocation().getBlockY() + 0.5, online.getLocation().getBlockZ()));
                                online.sendMessage(Color.translate("&eYou were shrunk in the border."));
                            }
                        }
                    }
                }
            } else {
                for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                    if ((!online.getWorld().equals(UHCUtils.getSpawnLocation().getWorld())) && ((online.getLocation().getBlockX() > size) || (online.getLocation().getBlockZ() > size) || (online.getLocation().getBlockX() < -size) || (online.getLocation().getBlockZ() < -size) || (online.getWorld().getName().equalsIgnoreCase("uhc_nether")))) {
                        online.setNoDamageTicks(59);
                        online.setFallDistance(0.0f);

                        online.teleport(UHCUtils.getScatterLocation());

                        online.setFallDistance(0.0f);

                        online.getLocation().add(0, 2, 0).getBlock().setType(Material.AIR);
                        online.getLocation().add(0, 3, 0).getBlock().setType(Material.AIR);
                        online.getLocation().add(0, 4, 0).getBlock().setType(Material.AIR);


                        online.teleport(new Location(w, online.getLocation().getBlockX(), w.getHighestBlockAt(online.getLocation().getBlockX(), online.getLocation().getBlockZ()).getLocation().getBlockY() + 0.5, online.getLocation().getBlockZ()));
                        online.sendMessage(Color.translate("&eYou were shrunk in the border."));
                    }
                }
            }
        }
    }

    public static void startSeconds() {
        new BukkitRunnable() {
            int i = 11;

            World w = Bukkit.getWorld("uhc_world");

            @Override
            public void run() {
                i--;
                if (i >= 1) {
                    if (Arrays.asList(10, 5, 4, 3, 2, 1).contains(i)) {
                        String seconds = i > 0 ? "seconds" : "second";

                        Bukkit.broadcastMessage(Color.translate("&eBorder will shrink in &9" + i + " &e" + seconds + "."));
                    }
                } else if (i == 0) {
                    if (border == 2000) {
                        new Border(w, 1500);

                        InvisibleFix.fixPlayer(player);

                        shrinkBorder(1500, this);

                        BorderTimeTask.setSeconds();

                        Bukkit.broadcastMessage(Color.translate("&eBorder has shrunk to &9" + border + "&e."));
                    } else if (border == 1500) {
                        new Border(w, 1000);

                        InvisibleFix.fixPlayer(player);

                        shrinkBorder(1000, this);

                        BorderTimeTask.setSeconds();

                        Bukkit.broadcastMessage(Color.translate("&eBorder has shrunk to &9" + border + "&e."));
                    } else if (border == 1000) {
                        new Border(w, 500);

                        InvisibleFix.fixPlayer(player);

                        shrinkBorder(500, this);

                        BorderTimeTask.setSeconds();

                        Bukkit.broadcastMessage(Color.translate("&eBorder has shrunk to &9" + border + "&e."));
                    } else if (border == 500) {
                        new Border(w, 100);

                        InvisibleFix.fixPlayer(player);

                        shrinkBorder(100, this);
                        UHC.getInstance().getGlassManager().setup();
                        BorderTimeTask.setSeconds();

                        Bukkit.broadcastMessage(Color.translate("&eBorder has shrunk to &9" + border + "&e."));
                    } else if (border == 100) {
                        UHCUtils.flat(55, w);

                        BorderTimeTask.setSeconds();

                        new Border(w, 50);

                        InvisibleFix.fixPlayer(player);

                        shrinkBorder(50, this);

                        Bukkit.broadcastMessage(Color.translate("&eBorder has shrunk to &9" + border + "&e."));
                    } else if (border == 50) {
                        flat(w);

                        BorderTimeTask.setSeconds();

                        new Border(w, 25);

                        InvisibleFix.fixPlayer(player);

                        shrinkBorder(25, this);

                        Bukkit.broadcastMessage(Color.translate("&eBorder has shrunk to &9" + border + "&e."));
                    } else if (border == 25) {
                        new Border(w, 5);

                        UHC.getInstance().getGameManager().setBorderTime(false);

                        InvisibleFix.fixPlayer(player);

                        shrinkBorder(5, this);

                        Bukkit.broadcastMessage(Color.translate("&eBorder has shrunk to &9" + border + "&e."));
                    }
                }
            }
        }.runTaskTimer(UHC.getInstance(), 20, 20);
    }

    public static void flat(World world) {
        for (int x = -30; x < 30; x++) {
            for (int y = 59; y < 150; y++) {
                for (int z = -30; z < 30; z++) {
                    Location location = new Location(world, x, y, z);
                    location.getBlock().setType(Material.AIR);
                }
            }
        }
        for (int x = -30; x < 30; x++) {
            for (int y = 59; y < 60; y++) {
                for (int z = -30; z < 30; z++) {
                    Location location = new Location(world, x, y, z);
                    location.getBlock().setType(Material.BEDROCK);
                }
            }
        }
        for (int x = -30; x < 30; x++) {
            for (int y = 60; y < 61; y++) {
                for (int z = -30; z < 30; z++) {
                    Location location = new Location(world, x, y, z);
                    location.getBlock().setType(Material.GRASS);
                }
            }
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.teleport(teleportToTop(player.getLocation()));
        }
    }

    public static Location teleportToTop(Location location) {
        return new Location(location.getWorld(), location.getX(), location.getWorld().getHighestBlockYAt(location.getBlockX(), location.getBlockZ()), location.getZ(), location.getYaw(), location.getPitch());
    }

    public static void startBorderShrink() {
        Bukkit.broadcastMessage(Color.translate("&eBorder will shrink every &9" + OptionManager.getByNameAndTranslate("Border Shrink Interval") + " &eminutes, by &9500 &eblocks, until &925&ex&925&e."));

        new BorderTask().runTaskTimerAsynchronously(UHC.getInstance(), 200L, 200L);
    }

    public static void enablePermaDay() {
        Bukkit.getWorld("uhc_world").setTime(0);
        Bukkit.getWorld("uhc_world").setGameRuleValue("doDaylightCycle", "false");

        Bukkit.broadcastMessage(Color.translate("&eThe Permanent day has been &aEnabled&e."));
    }
}