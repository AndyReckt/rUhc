package com.thesevenq.uhc.managers;

import com.thesevenq.uhc.utilties.Permission;
import com.thesevenq.uhc.utilties.item.ItemBuilder;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.player.UHCData;
import com.thesevenq.uhc.utilties.Manager;
import com.thesevenq.uhc.utilties.UHCUtils;
import com.thesevenq.uhc.utilties.spectator.SpectatorData;
import com.thesevenq.uhc.utilties.Color;

import java.util.*;

@Getter
public class SpectatorManager extends Manager {

    private Map<UUID, SpectatorData> spectators = new HashMap<>();
    private List<UUID> deathInventory = new ArrayList<>();
    //private Inventory aliveInventory;

    public SpectatorManager(UHC plugin) {
        super(plugin);
    }

    public void handleOnDisable() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            handleDisable(player);
        }

        spectators.clear();
        deathInventory.clear();
    }

    public void handleEnable(Player player) {
        SpectatorData data = new SpectatorData();

        data.setContents(player.getInventory().getContents());
        data.setArmor(player.getInventory().getArmorContents());
        data.setGameMode(player.getGameMode());

        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0));

        player.getInventory().clear();
        player.getInventory().setArmorContents(new ItemStack[4]);

        player.setGameMode(GameMode.CREATIVE);

        UHCUtils.loadSpectatorInventory(player);

        if(Bukkit.getWorld("uhc_world") != null) {
            player.teleport(new Location(Bukkit.getWorld("uhc_world"), 0, 100, 0));
        }

        plugin.getVanishManager().handleVanish(player);

        spectators.put(player.getUniqueId(), data);
        deathInventory.add(player.getUniqueId());
    }

    public void handleDisable(Player player) {
        if(spectators.containsKey(player.getUniqueId())) {
            SpectatorData data = spectators.get(player.getUniqueId());

            player.getInventory().setContents(data.getContents());
            player.getInventory().setArmorContents(data.getArmor());
            player.setGameMode(data.getGameMode());

            if(player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            }

            spectators.remove(player.getUniqueId());
        }
    }

    public void handleMove(Player player, Location from, Location to) {
        if(!UHCUtils.isPlayerInSpecMode(player)) return;
        if(player.hasPermission(Permission.STAFF_PERMISSION)) return;

        if(from.getBlockX() == to.getBlockX() && from.getBlockZ() == to.getBlockZ()) return;

        Location loc = player.getLocation();

        if(to.getBlockY() <= 40) {
            loc.setY(from.getBlockY() + 5);

            player.teleport(loc);

            player.sendMessage(Color.translate("&cYou can't xray for other players :P."));
        }

        if(player.hasPermission(Permission.XENON_PERMISSION)
                || player.hasPermission(Permission.MEDIA_PERMISSION)
                || player.hasPermission(Permission.PARTNER_PERMISSION)) {

            if(!UHCUtils.maxSpecSize(to, 500) && UHCUtils.maxSpecSize(from, 500)) {
                player.sendMessage(Color.translate("&cYou can't spectate more than &l500&c blocks."));
                player.teleport(from);
            }
        } else if(player.hasPermission(Permission.KRYPTON_PERMISSION)) {
            if(!UHCUtils.maxSpecSize(to, 400) && UHCUtils.maxSpecSize(from, 400)) {
                player.sendMessage(Color.translate("&cYou can't spectate more than &l400&c blocks."));
                player.teleport(from);
            }
        } else if(player.hasPermission(Permission.TITANIUM_PERMISSION)) {
            if(!UHCUtils.maxSpecSize(to, 300) && UHCUtils.maxSpecSize(from, 300)) {
                player.sendMessage(Color.translate("&cYou can't spectate more than &l300&c blocks."));
                player.teleport(from);
            }
        } else if(player.hasPermission(Permission.NITROGEN_PERMISSION)) {
            if(!UHCUtils.maxSpecSize(to, 200) && UHCUtils.maxSpecSize(from, 200)) {
                player.sendMessage(Color.translate("&cYou can't spectate more than &l200&c blocks."));
                player.teleport(from);
            }
        } else if(player.hasPermission(Permission.HYDROGEN_PERMISSION)) {
            if(!UHCUtils.maxSpecSize(to, 100) && UHCUtils.maxSpecSize(from, 100)) {
                player.sendMessage(Color.translate("&cYou can't spectate more than &l100&c blocks."));
                player.teleport(from);
            }
        } else {
            if(!UHCUtils.maxSpecSize(to, 50) && UHCUtils.maxSpecSize(from, 50)) {
                player.sendMessage(Color.translate("&cYou can't spectate more than &l50&c blocks."));
                player.teleport(from);
            }
        }
    }

    public void handleRandomTeleport(Player player) {
        List<Player> online = new ArrayList<>();

        for(Player players : Bukkit.getOnlinePlayers()) {
            UHCData p = UHCData.getByName(players.getName());

            if(players != player && p.isAlive()) {
                online.add(players);
            }
        }

        if(online.size() != 0) {
            Player target = online.get(new Random().nextInt(online.size()));
            player.teleport(target);
            player.sendMessage(Color.translate("&bYou have been randomly teleported to &3" + target.getName() + "&b."));
        }
    }

    public Inventory getInventory(int page) {
        int alive = PlayerManager.getAlivePlayers();

        int total = (int) Math.ceil(alive / 9.0);
        if(total == 0) {
            total = 1;
        }

        Inventory inventory = Bukkit.createInventory(null, 18, ChatColor.RED + "Alive Players - " + page + "/" + total);

        inventory.setItem(0, new ItemBuilder(Material.CARPET).durability(7).name("&cPrevious Page").build());
        inventory.setItem(8, new ItemBuilder(Material.CARPET).durability(7).name("&cNext Page").build());
        inventory.setItem(4, new ItemBuilder(Material.PAPER).name("&cPage " + page + "/" + total).build());

        List<UHCData> toLoop = new ArrayList<>(UHCData.getUhcDatas().values());
        Collections.reverse(toLoop);
        toLoop.removeIf(UHCData::isNotAlive);

        toLoop.forEach(data -> {
            if(toLoop.indexOf(data) >= page * 9 - 9 && toLoop.indexOf(data) < page * 9) {
                String name = data.getRealName() != null ? data.getRealName() : data.getName();

                ItemBuilder builder = new ItemBuilder(Material.SKULL_ITEM);
                builder.name("&7" + name);
                builder.lore("&bClick to teleport to the &3" + data.getRealName());

                inventory.setItem(9 + toLoop.indexOf(data) % 9, builder.build());
            }
        });


        return inventory;
    }

    /*public Inventory getAliveInventory(Player player) {
        aliveInventory = Bukkit.createInventory(null, 54, "Alive Players:");

        aliveInventory.clear();

        for(UHCData uhcPlayers : UHCData.getUhcDatas().values()) {
            if(uhcPlayers.isAlive()) {
                OfflinePlayer targetOffline = Bukkit.getOfflinePlayer(uhcPlayers.getName());

                if(player.hasPermission(Permission.STAFF_PERMISSION)) {
                    aliveInventory.addItem(new ItemBuilder(Material.SKULL_ITEM).name("&7" + targetOffline.getName()).lore("&eLeft click to teleport to the &d" + targetOffline.getName()).build());
                }

                if(targetOffline.isOnline()) {
                    Player target = Bukkit.getPlayer(uhcPlayers.getName());
                    if(player.hasPermission(Permission.XENON_PERMISSION)
                            || player.hasPermission(Permission.PARTNER_PERMISSION)) {

                        if(UHCUtils.maxSpecSize(target.getLocation(), 500)) {
                            aliveInventory.addItem(new ItemBuilder(Material.SKULL_ITEM).name("&7" + target.getName()).lore("&eLeft click to teleport to the &d" + target.getName()).build());
                        }
                    } else if(player.hasPermission(Permission.KRYPTON_PERMISSION)) {
                        if(UHCUtils.maxSpecSize(target.getLocation(), 400)) {
                            aliveInventory.addItem(new ItemBuilder(Material.SKULL_ITEM).name("&7" + target.getName()).lore("&eLeft click to teleport to the &d" + target.getName()).build());
                        }
                    } else if(player.hasPermission(Permission.TITANIUM_PERMISSION)) {
                        if(UHCUtils.maxSpecSize(target.getLocation(), 300)) {
                            aliveInventory.addItem(new ItemBuilder(Material.SKULL_ITEM).name("&7" + target.getName()).lore("&eLeft click to teleport to the &d" + target.getName()).build());
                        }
                    } else if(player.hasPermission(Permission.NITROGEN_PERMISSION)) {
                        if(UHCUtils.maxSpecSize(target.getLocation(), 200)) {
                            aliveInventory.addItem(new ItemBuilder(Material.SKULL_ITEM).name("&7" + target.getName()).lore("&eLeft click to teleport to the &d" + target.getName()).build());
                        }
                    } else if(player.hasPermission(Permission.HYDROGEN_PERMISSION)) {
                        if(UHCUtils.maxSpecSize(target.getLocation(), 100)) {
                            aliveInventory.addItem(new ItemBuilder(Material.SKULL_ITEM).name("&7" + target.getName()).lore("&eLeft click to teleport to the &d" + target.getName()).build());
                        }
                    } else {
                        if(UHCUtils.maxSpecSize(target.getLocation(), 50)) {
                            aliveInventory.addItem(new ItemBuilder(Material.SKULL_ITEM).name("&7" + target.getName()).lore("&eLeft click to teleport to the &d" + target.getName()).build());
                        }
                    }
                }
            }
        }

        return aliveInventory;
    }*/

    public Inventory createInventory(Player player, Player target) {
        Inventory inv = Bukkit.createInventory(null, 54, "Inventory preview");

        ItemStack[] contents = target.getInventory().getContents();
        ItemStack[] armor = target.getInventory().getArmorContents();

        inv.setContents(contents);

        inv.setItem(45, armor[0]);
        inv.setItem(46, armor[1]);
        inv.setItem(47, armor[2]);
        inv.setItem(48, armor[3]);

        inv.setItem(36, this.createGlass(ChatColor.RED + "Inventory Preview"));
        inv.setItem(37, this.createGlass(ChatColor.RED + "Inventory Preview"));
        inv.setItem(38, this.createGlass(ChatColor.RED + "Inventory Preview"));
        inv.setItem(39, this.createGlass(ChatColor.RED + "Inventory Preview"));
        inv.setItem(40, this.createGlass(ChatColor.RED + "Inventory Preview"));
        inv.setItem(41, this.createGlass(ChatColor.RED + "Inventory Preview"));
        inv.setItem(42, this.createGlass(ChatColor.RED + "Inventory Preview"));
        inv.setItem(43, this.createGlass(ChatColor.RED + "Inventory Preview"));
        inv.setItem(44, this.createGlass(ChatColor.RED + "Inventory Preview"));
        inv.setItem(49, this.createGlass(ChatColor.RED + "Inventory Preview"));

        inv.setItem(50, this.createItem(Material.SPECKLED_MELON, ChatColor.RED + "Health", (int)((Damageable)target).getHealth()));
        inv.setItem(51, this.createItem(Material.GRILLED_PORK, ChatColor.RED + "Hunger", target.getFoodLevel()));
        inv.setItem(52, this.createSkull(target, ChatColor.GREEN + target.getName()));
        inv.setItem(53, this.createWool(ChatColor.RED + "Close Preview", 14));

        return inv;
    }

    public ItemStack createItem(Material material, String name, int amount) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setDisplayName(name);
        item.setItemMeta(itemmeta);

        return item;
    }

    public ItemStack createGlass(String name) {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setDisplayName(name);
        item.setItemMeta(itemmeta);

        return item;
    }

    public ItemStack createWool(String name, int value) {
        ItemStack item = new ItemStack(Material.WOOL, 1, (short) value);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setDisplayName(name);
        item.setItemMeta(itemmeta);
        return item;
    }

    public ItemStack createSkull(Player player, String name) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullmeta = (SkullMeta) item.getItemMeta();
        skullmeta.setDisplayName(name);
        skullmeta.setOwner(player.getName());
        item.setItemMeta(skullmeta);

        return item;
    }
}
