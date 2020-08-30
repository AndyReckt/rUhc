package com.thesevenq.uhc.scenario.type;

import com.thesevenq.uhc.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import com.thesevenq.uhc.scenario.Scenario;

/**
 * Created by Marko on 05.04.2018.
 */
public class SwordlessScenario extends Scenario implements Listener {

    public SwordlessScenario() {
        super("Swordless", Material.STONE_SWORD, "Swords can't be crafted/used.");
    }

    public static void handleCraft(ItemStack stack, CraftItemEvent event) {
        switch(stack.getType()) {
            case WOOD_SWORD:
            case STONE_SWORD:
            case GOLD_SWORD:
            case IRON_SWORD:
            case DIAMOND_SWORD:
                event.setCancelled(true);
                break;
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if(!event.isCancelled()) {
            switch(event.getCurrentItem().getType()) {
                case WOOD_SWORD:
                    event.setCancelled(true);
                    break;
                case STONE_SWORD:
                    event.setCancelled(true);
                    break;
                case GOLD_SWORD:
                    event.setCancelled(true);
                    break;
                case IRON_SWORD:
                    event.setCancelled(true);
                    break;
                case DIAMOND_SWORD:
                    event.setCancelled(true);
                    break;
            }
        }
    }
}
