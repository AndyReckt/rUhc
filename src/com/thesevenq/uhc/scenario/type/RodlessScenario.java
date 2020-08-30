package com.thesevenq.uhc.scenario.type;

import com.thesevenq.uhc.scenario.Scenario;
import com.thesevenq.uhc.utilties.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class RodlessScenario extends Scenario implements Listener {

	public RodlessScenario() {
		super("Rodless", Material.FISHING_ROD, "Fishing Rods can't be crafted/used.");
	}

	public static void handleCraft(Player player, Recipe recipe, CraftingInventory inventory, CraftItemEvent event) {
		if(recipe.getResult().getType() != Material.FISHING_ROD) {
			return;
		}

		inventory.setResult(new ItemStack(Material.AIR));
		player.sendMessage(Color.translate("&cYou can't craft fishing rods while &lRodless&c scenario is active."));
		event.setCancelled(true);
	}

	public static void handleInteract(Player player, ItemStack item) {
		if(item == null || item.getType() != Material.FISHING_ROD) {
			return;
		}

		player.setItemInHand(null);
		player.updateInventory();

		player.sendMessage(Color.translate("&cYou can't use fishing rods while &lRodless&c scenario is active."));
	}

	@EventHandler
	public void onCraftItem(CraftItemEvent event) {
		Player player = (Player) event.getView().getPlayer();

		if(event.getRecipe().getResult().getType() != Material.FISHING_ROD) return;
		
		event.getInventory().setResult(new ItemStack(Material.AIR));

		player.sendMessage(Color.translate("&cYou can't craft fishing rods while &lRodless&c scenario is active."));

		event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack item = event.getItem();

		if(item == null) return;
		
		if(item.getType() != Material.FISHING_ROD) return;
		
		player.setItemInHand(null);
		player.updateInventory();

		player.sendMessage(Color.translate("&cYou can't use fishing rods while &lRodless&c scenario is active."));
	}

}
