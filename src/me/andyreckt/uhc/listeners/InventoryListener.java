package me.andyreckt.uhc.listeners;

import me.andyreckt.uhc.api.API;
import me.andyreckt.uhc.managers.*;
import me.andyreckt.uhc.scenario.Scenario;
import me.andyreckt.uhc.utilties.BaseListener;
import me.andyreckt.uhc.utilties.Permission;
import me.andyreckt.uhc.utilties.UHCUtils;
import me.andyreckt.uhc.utilties.events.ConfigOptionChangeEvent;
import me.andyreckt.uhc.config.Option;
import me.andyreckt.uhc.player.state.GameState;
import me.andyreckt.uhc.tasks.UpdateInventoryTask;
import me.andyreckt.uhc.utilties.Color;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import me.andyreckt.uhc.UHC;

public class InventoryListener extends BaseListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();

        if(event.getCurrentItem() == null
				|| event.getCurrentItem().getType() == Material.AIR
				|| !event.getCurrentItem().hasItemMeta()
				|| event.getCurrentItem().getItemMeta() == null) return;

        String title = event.getClickedInventory().getTitle();
		String itemName = event.getCurrentItem().getItemMeta().getDisplayName();

		if(title.equals(InventoryManager.uhcSettings.getTitle())) {
        	event.setCancelled(true);

            if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Toggle Scenarios"))) {
                player.openInventory(InventoryManager.toggleScenarios);
            } else if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Max Players"))) {
                player.openInventory(InventoryManager.maxPlayers);
            } else if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Parties"))) {
                player.openInventory(InventoryManager.team);
            } else if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Options"))) {
                player.openInventory(InventoryManager.options);
            } else if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Practice"))) {
                player.openInventory(InventoryManager.uhcPractice);
            } else if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Rates"))) {
                player.openInventory(InventoryManager.rates);
            } else if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Current Scenarios"))) {
                InventoryManager.setScenarioInfo(player);
            } else if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Starter Inventory"))) {
                player.closeInventory();

                player.getInventory().clear();
                plugin.getGameManager().getSaveUsers().add(player.getUniqueId());
                player.setGameMode(GameMode.CREATIVE);

                player.sendMessage(me.andyreckt.uhc.utilties.Color.translate("&eType &9/game saveitems&e to save starter inventory."));
            }
		} else if(title.equals(InventoryManager.maxPlayers.getTitle())) {
		    event.setCancelled(true);

            if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Max Players &f- 50"))) {
                if(Bukkit.getMaxPlayers() < 50) return;

                API.setMaxPlayers(Bukkit.getMaxPlayers() - 50);
            } else if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Max Players &f- 10"))) {
                if(Bukkit.getMaxPlayers() < 10) return;

                API.setMaxPlayers(Bukkit.getMaxPlayers() - 10);


            } else if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Max Players &f- 5"))) {
                if(Bukkit.getMaxPlayers() < 5) return;

                API.setMaxPlayers(Bukkit.getMaxPlayers() - 5);

            } else if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Max Players &f+ 5"))) {
                API.setMaxPlayers(Bukkit.getMaxPlayers() + 5);
            } else if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Max Players &f+ 10"))) {
                API.setMaxPlayers(Bukkit.getMaxPlayers() + 10);
            } else if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Max Players &f+ 50"))) {
                API.setMaxPlayers(Bukkit.getMaxPlayers() + 50);
            } else if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&c&lBack"))) {
                player.openInventory(InventoryManager.uhcSettings);
            }
        } else if(title.equals(InventoryManager.team.getTitle())) {
            event.setCancelled(true);

            if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Enable Parties"))) {
                PartyManager.setEnabled(true);

                Bukkit.getOnlinePlayers().forEach(online -> {
                    if(GameManager.getGameState().equals(GameState.LOBBY) &&
                            !plugin.getPracticeManager().getUsers().contains(online.getUniqueId())
                            && online.getInventory().getItem(4) == null) {

                        online.getInventory().clear();
                        UHCUtils.loadLobby(online);
                    }
                });
            } else if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Disable Parties"))) {
                PartyManager.setEnabled(false);

                Bukkit.getOnlinePlayers().forEach(online -> {
                    if(GameManager.getGameState().equals(GameState.LOBBY) &&
                            !plugin.getPracticeManager().getUsers().contains(online.getUniqueId())
                            && online.getInventory().getItem(4) != null) {

                        online.getInventory().clear();
                        UHCUtils.loadLobby(online);
                    }
                });
            } else if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&c&lBack"))) {
                event.setCancelled(true);

                player.openInventory(InventoryManager.uhcSettings);
            } else if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Team Size &f+ 1"))) {
                event.setCancelled(true);

                plugin.getPartyManager().setMaxSize(plugin.getPartyManager().getMaxSize() + 1);
            } else if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Team Size &f- 1"))) {
                event.setCancelled(true);

                if(plugin.getPartyManager().getMaxSize() < 3) return;

                plugin.getPartyManager().setMaxSize(plugin.getPartyManager().getMaxSize() - 1);
            }
        } else if(title.equals(InventoryManager.toggleScenarios.getTitle())) {
            event.setCancelled(true);

            if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Disable All Scenarios"))) {
                event.setCancelled(true);

                for(Scenario scenario : ScenarioManager.scenarios) {
                    if(scenario.isEnabled()) {
                        scenario.setEnabled(false);
                        scenario.disableScenarios();
                    }
                }

                Bukkit.getScheduler().runTaskAsynchronously(UHC.getInstance(),
                        new UpdateInventoryTask());
            } else if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&c&lBack"))) {
                event.setCancelled(true);

                player.openInventory(InventoryManager.uhcSettings);
            }

            String optionName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());

            Scenario scenario = ScenarioManager.getByName(optionName);

            if(scenario != null) {
                scenario.toggle();

                event.getInventory().setItem(event.getRawSlot(), ScenarioManager.getScenarioItem(scenario));

                Bukkit.getScheduler().runTaskAsynchronously(UHC.getInstance(),
                        new UpdateInventoryTask());
            }
        } else if(title.equals(me.andyreckt.uhc.utilties.Color.translate("&eScenarios"))) {
            event.setCancelled(true);

            if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Toggle Scenarios"))) {
                event.setCancelled(true);
                player.openInventory(InventoryManager.toggleScenarios);
            } else if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&c&lBack"))) {
                event.setCancelled(true);

                player.openInventory(InventoryManager.uhcSettings);
            }
        } else if(title.contains(me.andyreckt.uhc.utilties.Color.translate("&9Leaderboards")) || title.contains(me.andyreckt.uhc.utilties.Color.translate("&9Your Stats")) || title.contains(me.andyreckt.uhc.utilties.Color.translate("&9Stats ")) || title.equals(InventoryManager.scenarioInfo.getTitle())) {
		    event.setCancelled(true);
        } else if(title.equals(InventoryManager.uhcPractice.getTitle())) {
		    event.setCancelled(true);

            if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Toggle UHCPractice ON"))) {
                event.setCancelled(true);

                player.closeInventory();
                player.performCommand("uhcpractice on");
            } else if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Toggle UHCPractice OFF"))) {
                event.setCancelled(true);

                player.closeInventory();
                player.performCommand("uhcpractice off");
            } else if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&c&lBack"))) {
                event.setCancelled(true);

                player.openInventory(InventoryManager.uhcSettings);
            }
        } else if(title.equals(InventoryManager.options.getTitle())) {
            event.setCancelled(true);

            if(player.hasPermission(Permission.OP_PERMISSION)) {
                if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&c&lBack"))) {
                    event.setCancelled(true);

                    player.openInventory(InventoryManager.uhcSettings);
                }

                String optionName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());

                Option option = OptionManager.getByName(optionName);

                if(option != null) {
                    int currentValue = option.getValue();
                    option.nextValue();

                    Bukkit.getPluginManager().callEvent(new ConfigOptionChangeEvent(option, player, currentValue, option.getValue()));
                    event.getInventory().setItem(event.getRawSlot(), OptionManager.getConfigItem(option));
                }
            }
        } else if(title.equals(InventoryManager.scatter.getTitle())) {
            if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&a&lYes"))) {
                event.setCancelled(true);

                if(!plugin.getGameManager().isPvp()) {
                    if(plugin.getGameManager().getInvTask().contains(player.getUniqueId())) {
                        plugin.getGameManager().getInvTask().remove(player.getUniqueId());
                        player.closeInventory();
                    }

                    player.getInventory().setArmorContents(null);

                    UHCUtils.disableSpecMode(player);

                    UHCUtils.scatterPlayer(player);

                    UHC.getInstance().getServer().getScheduler().runTaskLater(UHC.getInstance(), () -> UHC.getInstance().getHorseManager().unsitPlayer(player), 3L);
                }
            } else if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&c&lNo"))) {
                event.setCancelled(true);

                if(plugin.getGameManager().getInvTask().contains(player.getUniqueId())) {
                    plugin.getGameManager().getInvTask().remove(player.getUniqueId());
                    player.closeInventory();
                }

                player.getInventory().setHelmet(new ItemStack(Material.AIR));
                player.getInventory().setChestplate(new ItemStack(Material.AIR));
                player.getInventory().setLeggings(new ItemStack(Material.AIR));
                player.getInventory().setBoots(new ItemStack(Material.AIR));

                plugin.getSpectatorManager().handleEnable(player);

                Location loc = UHCUtils.getScatterLocation();

                new BukkitRunnable() {
                    public void run() {
                        player.teleport(loc);
                    }
                }.runTaskLater(plugin, 10L);
            }
        } else if(title.equals(InventoryManager.rates.getTitle())) {
            event.setCancelled(true);

            if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Shears Rate &f- 1%"))) {
                event.setCancelled(true);

                if(plugin.getGameManager().getShearsRate() < 1) return;

                plugin.getGameManager().setShearsRate(plugin.getGameManager().getShearsRate() - 1);
            } else if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Shears Rate &f- 2%"))) {
                event.setCancelled(true);

                if(plugin.getGameManager().getShearsRate() < 2) return;

                plugin.getGameManager().setShearsRate(plugin.getGameManager().getShearsRate() - 2);
            } else if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Shears Rate &f+ 1%"))) {
                event.setCancelled(true);

                plugin.getGameManager().setShearsRate(plugin.getGameManager().getShearsRate() + 1);
            } else if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Shears Rate &f+ 2%"))) {
                event.setCancelled(true);

                plugin.getGameManager().setShearsRate(plugin.getGameManager().getShearsRate() + 2);
            } else 	if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Apple Rate &f- 1%"))) {
                event.setCancelled(true);

                if(plugin.getGameManager().getAppleRate() < 1) return;

                plugin.getGameManager().setAppleRate(plugin.getGameManager().getAppleRate() - 1);
            } else if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Apple Rate &f- 2%"))) {
                event.setCancelled(true);

                if(plugin.getGameManager().getAppleRate() < 2) return;

                plugin.getGameManager().setAppleRate(plugin.getGameManager().getAppleRate() - 2);
            } else if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Apple Rate &f+ 1%"))) {
                event.setCancelled(true);

                plugin.getGameManager().setAppleRate(plugin.getGameManager().getAppleRate() + 1);
            } else if(itemName.equalsIgnoreCase(me.andyreckt.uhc.utilties.Color.translate("&9Apple Rate &f+ 2%"))) {
                event.setCancelled(true);

                plugin.getGameManager().setAppleRate(plugin.getGameManager().getAppleRate() + 2);
            } else if(itemName.equalsIgnoreCase(Color.translate("&c&lBack"))) {
                event.setCancelled(true);

                player.openInventory(InventoryManager.uhcSettings);
            }
        }
	}
}
