package me.andyreckt.uhc.utilties.item;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface ItemDB {
	
	void reloadItemDatabase();

	ItemStack getPotion(String potion);

	ItemStack getPotion(String potion, int i);

	ItemStack getItem(String item);

	ItemStack getItem(String item, int i);

	String getName(ItemStack name);

	String getPrimaryName(ItemStack primaryName);

	String getNames(ItemStack names);

	List<ItemStack> getMatching(Player player, String[] list);
}
