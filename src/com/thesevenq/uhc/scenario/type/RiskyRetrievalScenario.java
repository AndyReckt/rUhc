package com.thesevenq.uhc.scenario.type;

import com.thesevenq.uhc.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import com.thesevenq.uhc.utilties.UHCUtils;

/**
 * Created by Marko on 02.06.2018.
 */
public class RiskyRetrievalScenario extends Scenario {

    public RiskyRetrievalScenario() {
        super("Risky Retrieval", Material.ENDER_CHEST, "All gold and diamonds you mine,", "will go to the enderchest which is placed in 0,0.");
    }

    public static void handleBreak(Player player, Block block, BlockBreakEvent event) {
        if(UHCUtils.isPlayerInSpecMode(player)) return;

        switch (block.getType()) {
            case DIAMOND_ORE:
                event.setCancelled(true);

                block.setType(Material.AIR);
                block.getState().update();

                block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(5);
                player.getEnderChest().addItem(new ItemStack(Material.DIAMOND_ORE));
                break;
            case GOLD_ORE:
                event.setCancelled(true);

                block.setType(Material.AIR);
                block.getState().update();

                block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(5);
                player.getEnderChest().addItem(new ItemStack(Material.GOLD_ORE));
                break;
            default:
                break;
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(event.isCancelled()) return;

        Player player = event.getPlayer();

        if(UHCUtils.isPlayerInSpecMode(player)) return;

        Block block = event.getBlock();

        switch (block.getType()) {
            case DIAMOND_ORE:
                event.setCancelled(true);

                event.getBlock().setType(Material.AIR);
                block.getState().update();

                block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(5);
                player.getEnderChest().addItem(new ItemStack(Material.DIAMOND_ORE));
                break;
            case GOLD_ORE:
                event.setCancelled(true);

                event.getBlock().setType(Material.AIR);
                block.getState().update();

                block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(5);
                player.getEnderChest().addItem(new ItemStack(Material.GOLD_ORE));
                break;
            default:
                break;
        }
    }
}
