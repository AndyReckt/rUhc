package com.thesevenq.uhc.scenario.type;

import com.thesevenq.uhc.scenario.Scenario;
import com.thesevenq.uhc.utilties.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;

public class NoEnchantsScenario extends Scenario implements Listener {

	public NoEnchantsScenario() {
		super("No Enchants", Material.ENCHANTMENT_TABLE, "You can't enchant or use anvils.");
	}

	public static void handleEnchantItem(Player player, EnchantItemEvent event) {
		event.setCancelled(true);
		player.sendMessage(Color.translate("&cYou can't use enchant while &lNo Enchants&c scenario is enabled."));
	}

	public static void handleInventoryClick(Player player, Inventory inventory, SlotType slotType, InventoryClickEvent event) {
		if(inventory.getType().equals(InventoryType.ANVIL) && slotType.equals(SlotType.RESULT)) {
			event.setCancelled(true);

			player.sendMessage(Color.translate("&cYou can't use anvils while &lNo Enchants&c scenario is enabled."));
		}
	}

    @EventHandler
    public void onEnchantItem(EnchantItemEvent event) {    	
    	Player player = event.getEnchanter();
    	
    	event.setCancelled(true);
    	
    	player.sendMessage(Color.translate("&cYou can't use enchant while &lNo Enchants&c scenario is enabled."));
    }
    
    @EventHandler
	public void onInventoryClick(InventoryClickEvent event) {    	
		Player player = (Player) event.getWhoClicked();
		Inventory inventory = event.getInventory();
		SlotType slotType = event.getSlotType();
		
		if(inventory.getType().equals(InventoryType.ANVIL) && slotType.equals(SlotType.RESULT)) {
			event.setCancelled(true);
			
	    	player.sendMessage(Color.translate("&cYou can't use anvils while &lNo Enchants&c scenario is enabled."));
		}
    }

}
