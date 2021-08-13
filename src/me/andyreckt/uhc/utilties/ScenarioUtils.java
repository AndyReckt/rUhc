package me.andyreckt.uhc.utilties;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class ScenarioUtils {

    public static ItemStack smelt(Block block, int amount) {
        switch(block.getType()) {
            case IRON_ORE:
                return new ItemStack(Material.IRON_INGOT, amount);
            case GOLD_ORE:
                return new ItemStack(Material.GOLD_INGOT, amount);
            case GRAVEL:
                return new ItemStack(Material.FLINT, amount);
                default:
                    return new ItemStack(Material.AIR, amount);
        }
    }
}
