package me.andyreckt.uhc.listeners;

import me.andyreckt.uhc.utilties.BaseListener;
import me.andyreckt.uhc.utilties.Color;
import me.andyreckt.uhc.utilties.Msg;
import me.andyreckt.uhc.player.deathlookup.DeathLookup;
import me.andyreckt.uhc.player.deathlookup.data.DeathData;
import me.andyreckt.uhc.player.deathlookup.data.ProfileFight;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;

public class DeathLookupListener extends BaseListener implements Listener {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        DeathData deathData = DeathData.getByName(player.getName());
        ItemStack itemStack = event.getCurrentItem();

        if (itemStack != null && itemStack.getType() != Material.AIR) {
            String title = event.getInventory().getTitle();
            String displayName = itemStack.getItemMeta().getDisplayName();
            DeathLookup lookup = deathData.getDeathLookup();

            if (((title.contains("Deaths") || title.contains("Death #") || title.contains("Inventory #")) && player.hasPermission("death.lookup") && lookup != null)) {
                event.setCancelled(true);

                if (displayName == null) {
                    return;
                }

                int page = 0;
                int total = 0;
                if (title.contains("Deaths")) {
                    page = Integer.parseInt(title.substring(title.lastIndexOf("/") - 1, title.lastIndexOf("/")));
                    total = Integer.parseInt(title.substring(title.lastIndexOf("/") + 1, title.lastIndexOf("/") + 2));
                }

                if (displayName.contains("Next Page")) {
                    if (page + 1 > total) {
                        player.sendMessage(ChatColor.RED + "There are no more pages.");
                        return;
                    }
                    player.openInventory(lookup.getDeathInventory(page + 1));
                    return;
                }

                if (displayName.contains("Previous Page")) {
                    if (page == 1) {
                        player.sendMessage(ChatColor.RED + "You're on the first page.");
                        return;
                    }
                    player.openInventory(lookup.getDeathInventory(page - 1));
                    return;
                }

                if (displayName.contains("Previous Death")) {
                    ProfileFight fight = lookup.getData().getFight();
                    if (fight != null) {

                        if (lookup.getData().getIndex() == 0 && lookup.getData().getPage() == 1) {
                            player.sendMessage(ChatColor.RED + "You're viewing the latest death.");
                            return;
                        }

                        ProfileFight previousFight;
                        int previousPage;
                        int previousIndex;
                        if (lookup.getData().getIndex() == 0) {
                            previousFight = lookup.getDeaths(lookup.getData().getPage() - 1).get(8);
                            previousPage = lookup.getData().getPage() - 1;
                            previousIndex = 8;

                        } else {
                            previousFight = lookup.getDeaths(lookup.getData().getPage()).get(lookup.getData().getIndex() - 1);
                            previousPage = lookup.getData().getPage();
                            previousIndex = lookup.getData().getIndex() - 1;
                        }

                        lookup.getData().setFight(previousFight);
                        lookup.getData().setPage(previousPage);
                        lookup.getData().setIndex(previousIndex);

                        player.openInventory(lookup.getFightInventory(previousFight));
                    }

                    return;
                }

                if (displayName.contains("Next Death")) {
                    ProfileFight fight = lookup.getData().getFight();
                    if (fight != null) {
                        if (lookup.getData().getIndex() == (lookup.getDeaths(lookup.getTotalPages()).size() - 1) && lookup.getData().getPage() == lookup.getTotalPages()) {
                            player.sendMessage(ChatColor.RED + "You're viewing the last death.");
                            return;
                        }

                        ProfileFight nextFight;
                        int nextPage;
                        int nextIndex;
                        if (lookup.getData().getIndex() == 8) {
                            nextFight = lookup.getDeaths(lookup.getData().getPage() + 1).get(0);
                            nextPage = lookup.getData().getPage() + 1;
                            nextIndex = 0;

                        } else {
                            nextFight = lookup.getDeaths(lookup.getData().getPage()).get(lookup.getData().getIndex() + 1);
                            nextPage = lookup.getData().getPage();
                            nextIndex = lookup.getData().getIndex() + 1;
                        }

                        lookup.getData().setFight(nextFight);
                        lookup.getData().setPage(nextPage);
                        lookup.getData().setIndex(nextIndex);

                        player.openInventory(lookup.getFightInventory(nextFight));
                    }

                    return;
                }

                if (displayName.contains("Death Location")) {
                    player.teleport(lookup.getData().getFight().getLocation());
                    return;
                }

                if (displayName.contains("Inventory Contents")) {
                    ProfileFight fight = lookup.getData().getFight();
                    if (fight != null) {
                        player.openInventory(lookup.getFightItemInventory(fight));
                    }
                }

                if (displayName.contains("Return")) {
                    ProfileFight fight = lookup.getData().getFight();
                    if (fight != null) {
                        player.openInventory(lookup.getFightInventory(fight));
                    }
                }

                if (displayName.contains("Rollback Inventory")) {
                    ProfileFight fight = lookup.getData().getFight();
                    if (fight != null) {
                        Player death = Bukkit.getPlayer(lookup.getDeathData().getName());

                        if(death == null) {
                            player.sendMessage(Color.translate("&cThat player isn't online."));
                            player.closeInventory();
                            return;
                        }

                        death.getInventory().setContents(fight.getContents());
                        death.getInventory().setArmorContents(fight.getArmor());
                        death.updateInventory();

                        player.sendMessage(Color.translate("&aYou have rollbacked inventory of &l" + death.getName()));
                        Msg.logConsole("&2" + player.getName() + " &arollbacked inventory of &2" + death.getName());
                        death.sendMessage(Color.translate("&aYour inventory was rollbacked by &l" + player.getName()));

                        player.closeInventory();
                    }
                }

                if (displayName.contains("Copy Inventory")) {
                    ProfileFight fight = lookup.getData().getFight();
                    if (fight != null) {
                        Bukkit.dispatchCommand(player, "lastinv " + lookup.getDeathData().getName() + " " + fight.getUuid());
                        player.closeInventory();
                    }
                }

                if (displayName.contains("Revive")) {
                    ProfileFight fight = lookup.getData().getFight();
                    if (fight != null) {
                        Bukkit.dispatchCommand(player, "deathban revive " + lookup.getDeathData().getName());
                        player.closeInventory();
                    }
                }

                if (itemStack.getType() == Material.SKULL_ITEM && itemStack.getDurability() == 0) {
                    ProfileFight fight = lookup.getDeaths(page).get(event.getRawSlot() - 9);
                    lookup.getData().setFight(fight);
                    lookup.getData().setPage(page);
                    lookup.getData().setIndex(event.getRawSlot() - 9);
                    player.openInventory(lookup.getFightInventory(fight));
                }

            }
        }

    }

}
