package com.thesevenq.uhc.scenario.type;

import com.thesevenq.uhc.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import com.thesevenq.uhc.scenario.Scenario;

import java.util.HashSet;
import java.util.Set;

public class HasteyBoysScenario extends Scenario implements Listener {

    private Set<Material> materials;

    public HasteyBoysScenario() {
        super("HasteyBoys", Material.IRON_PICKAXE, "When you craft item", "it automatically enchants to EF3");

        this.materials = new HashSet<>();

        this.materials.add(Material.WOOD_SPADE);
        this.materials.add(Material.WOOD_AXE);
        this.materials.add(Material.WOOD_PICKAXE);

        this.materials.add(Material.STONE_SPADE);
        this.materials.add(Material.STONE_AXE);
        this.materials.add(Material.STONE_PICKAXE);

        this.materials.add(Material.IRON_SPADE);
        this.materials.add(Material.IRON_AXE);
        this.materials.add(Material.IRON_PICKAXE);

        this.materials.add(Material.DIAMOND_SPADE);
        this.materials.add(Material.DIAMOND_AXE);
        this.materials.add(Material.DIAMOND_PICKAXE);

        this.materials.add(Material.GOLD_SPADE);
        this.materials.add(Material.GOLD_AXE);
        this.materials.add(Material.GOLD_PICKAXE);
    }

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        if (materials.contains(event.getRecipe().getResult().getType())) {
            ItemStack item = new ItemStack(event.getRecipe().getResult().getType());
            item.addEnchantment(Enchantment.DIG_SPEED, 3);
            item.addEnchantment(Enchantment.DURABILITY, 3);
            event.getInventory().setResult(item);
        }
    }
}
