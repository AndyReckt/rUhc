package me.andyreckt.uhc.utilties.inventory;

import me.andyreckt.uhc.utilties.Color;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public abstract class VituzMenu {

    private static Map<UUID, VituzMenu> menus = new HashMap<>();

    public static Map<UUID, VituzMenu> getMenus() {
        return menus;
    }

    public Player getPlayer() {
        return player;
    }

    public Inventory getInventory() {
        return inventory;
    }

    private Player player;
    private Inventory inventory;

    public VituzMenu(Player player, String title, int size) {
        this.player = player;
        this.inventory = Bukkit.createInventory(null, size, Color.translate(title));

        menus.put(player.getUniqueId(), this);

        this.updateInventory(player);
    }

    public void fill(ItemStack itemStack) {
        for (int i = 0; i < this.inventory.getSize(); i++) {
            if (this.inventory.getItem(i) == null) {
                this.inventory.setItem(i, itemStack);
            }
        }
    }
    
    public void openInventory(Player player) {
        player.openInventory(this.inventory);
    }
    
    public void set(int slot, ItemStack material) {
        this.inventory.setItem(slot, material);
    }

    public void close() {
        this.player.closeInventory();
    }

    public void destroy() {
        menus.remove(this.player.getUniqueId());

        this.player = null;
        this.inventory = null;
    }

    public abstract void updateInventory(Player player);

    public abstract void onClickItem(Player player, ItemStack itemStack, boolean isRightClicked);

    public abstract void onClose();

    public static VituzMenu getByPlayer(Player player) {
        return menus.get(player.getUniqueId());
    }

}