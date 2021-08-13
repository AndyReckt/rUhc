package me.andyreckt.uhc.scenario.type;

import me.andyreckt.uhc.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Marko on 03.04.2018.
 */
public class NineSlotScenario extends Scenario implements Listener {

    public NineSlotScenario() {
        super("Nine Slot", Material.WORKBENCH, "You can only use your hotbar for items (9 slots)");
    }

    public static void handlePickup(Player player, Item item, PlayerPickupItemEvent event) {
        player.getInventory().addItem(item.getItemStack());
        item.remove();
        event.setCancelled(true);
        cleanInventory(player);
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {
        event.getPlayer().getInventory().addItem(event.getItem().getItemStack());
        event.getItem().remove();

        event.setCancelled(true);

        this.cleanInventory(event.getPlayer());
    }

    public static void cleanInventory(Player player) {
        for(int i = 9; i < 36; i++) {
            ItemStack stack = player.getInventory().getItem(i);

            if(stack != null && stack.getType() != Material.AIR) {
                player.getInventory().setItem(i, null);

                player.getWorld().dropItemNaturally(player.getLocation().add(0, 2, 0), stack);
            }
        }
    }
}
