package com.thesevenq.uhc.scenario.type;

import com.thesevenq.uhc.scenario.Scenario;
import com.thesevenq.uhc.utilties.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import com.thesevenq.uhc.utilties.UHCUtils;

public class GoneFishingScenario extends Scenario implements Listener {

    public GoneFishingScenario() {
        super("Gone Fishing", Material.RAW_FISH, "You will get Unbreaking 200 and ", "Luck of the Sea 200 fishing rod along with 64 anvils.");
    }

    public static void handlePrepareItemCraft(ItemStack item, CraftingInventory inventory) {
        if(item.getType() == Material.ENCHANTMENT_TABLE) {
            inventory.setResult(new ItemStack(Material.AIR));
        }
    }

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        ItemStack item = event.getRecipe().getResult();

        if(item.getType() == Material.ENCHANTMENT_TABLE) {
            event.getInventory().setResult(new ItemStack(Material.AIR));
        }
    }

    public static void giveItems() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if(!UHCUtils.isPlayerInSpecMode(player)) {
                player.getInventory().addItem(new ItemBuilder(Material.FISHING_ROD).enchantment(Enchantment.DURABILITY, 200).enchantment(Enchantment.LUCK, 200).build());
                player.getInventory().addItem(new ItemBuilder(Material.ANVIL).amount(64).build());
            }
        });
    }
}
