package com.thesevenq.uhc.scenario.type;

import com.thesevenq.uhc.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import com.thesevenq.uhc.scenario.Scenario;

import java.util.Map;

public class ColdWeaponsScenario extends Scenario implements Listener {

	public ColdWeaponsScenario() {
		super("Cold Weapons", Material.PACKED_ICE, "Fire aspect enchant are disabled.", "Flame enchant are disabled.");
	}

	public static void handleEnchantItem(Map<Enchantment, Integer> enchantToAdd) {
		Map<Enchantment, Integer> toAdd = enchantToAdd;

		if(toAdd.containsKey(Enchantment.FIRE_ASPECT)) {
			toAdd.remove(Enchantment.FIRE_ASPECT);
		}

		if(toAdd.containsKey(Enchantment.ARROW_FIRE)) {
			toAdd.remove(Enchantment.ARROW_FIRE);
		}
	}

	public static void handleInventoryClick(Player player, Inventory inventory, SlotType slotType, ItemStack item, InventoryClickEvent event) {
		if(inventory.getType().equals(InventoryType.ANVIL) && slotType.equals(SlotType.RESULT)) {
			if(item.getEnchantments().containsKey(Enchantment.FIRE_ASPECT)) {
				item.removeEnchantment(Enchantment.FIRE_ASPECT);
			}

			if(item.getEnchantments().containsKey(Enchantment.ARROW_FIRE)) {
				item.removeEnchantment(Enchantment.ARROW_FIRE);
			}

			player.updateInventory();

			if(item.getType().equals(Material.ENCHANTED_BOOK)) {
				EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) item.getItemMeta();

				if(bookMeta.getStoredEnchants().containsKey(Enchantment.FIRE_ASPECT)) {
					event.setCancelled(true);

					item.removeEnchantment(Enchantment.FIRE_ASPECT);

					player.closeInventory();
				}

				if(bookMeta.getStoredEnchants().containsKey(Enchantment.ARROW_FIRE)) {
					event.setCancelled(true);

					item.removeEnchantment(Enchantment.ARROW_FIRE);

					player.closeInventory();
				}
			}
		}
	}

	@EventHandler
	public void onEnchantItem(EnchantItemEvent event) {
		Map<Enchantment, Integer> toAdd = event.getEnchantsToAdd();

		if(toAdd.containsKey(Enchantment.FIRE_ASPECT)) {
			toAdd.remove(Enchantment.FIRE_ASPECT);
		}

		if(toAdd.containsKey(Enchantment.ARROW_FIRE)) {
			toAdd.remove(Enchantment.ARROW_FIRE);
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Inventory inventory = event.getInventory();
		SlotType slotType = event.getSlotType();
		
		if(inventory.getType().equals(InventoryType.ANVIL) && slotType.equals(SlotType.RESULT)) {
			ItemStack item = event.getCurrentItem();
			
			if(item.getEnchantments().containsKey(Enchantment.FIRE_ASPECT)) {
				item.removeEnchantment(Enchantment.FIRE_ASPECT);
			}

			if(item.getEnchantments().containsKey(Enchantment.ARROW_FIRE)) {
				item.removeEnchantment(Enchantment.ARROW_FIRE);
			}

			player.updateInventory();
				
			if(item.getType().equals(Material.ENCHANTED_BOOK)) {
				EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) item.getItemMeta();

				if(bookMeta.getStoredEnchants().containsKey(Enchantment.FIRE_ASPECT)) {
					event.setCancelled(true);
					
					item.removeEnchantment(Enchantment.FIRE_ASPECT);
					
					player.closeInventory();
				}

				if(bookMeta.getStoredEnchants().containsKey(Enchantment.ARROW_FIRE)) {
					event.setCancelled(true);
					
					item.removeEnchantment(Enchantment.ARROW_FIRE);
					
					player.closeInventory();
				}
			}
		}
	}
}
