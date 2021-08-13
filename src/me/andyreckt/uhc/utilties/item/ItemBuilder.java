package me.andyreckt.uhc.utilties.item;

import me.andyreckt.uhc.utilties.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder implements Listener {

	private ItemStack is;

	public ItemBuilder(Material mat) {
		is = new ItemStack(mat);
	}

	public ItemBuilder(ItemStack is) {
		this.is = is.clone();
	}

	public ItemBuilder amount(int amount) {
		is.setAmount(amount);
		return this;
	}

	public ItemBuilder name(String name) {
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(Color.translate(name));
		is.setItemMeta(meta);
		return this;
	}

	public ItemBuilder lore(String name) {
		ItemMeta meta = is.getItemMeta();
		List<String> lore = meta.getLore();
		if (lore == null) {
			lore = new ArrayList<>();
		}
		lore.add(Color.translate(name));
		meta.setLore(lore);
		is.setItemMeta(meta);
		return this;
	}

	public ItemBuilder lore(List<String> lore) {
		ItemMeta meta = is.getItemMeta();

		meta.setLore(Color.translate(lore));
		
		is.setItemMeta(meta);
		return this;
	}

	public ItemBuilder durability(int durability) {
		is.setDurability((short) durability);
		return this;
	}

	public ItemBuilder data(int data) {
		is.setData(new MaterialData(is.getType(), (byte) data));
		return this;
	}

	public ItemBuilder enchantment(Enchantment enchantment, int level) {
		is.addUnsafeEnchantment(enchantment, level);
		return this;
	}

	public ItemBuilder enchantment(Enchantment enchantment) {
		is.addUnsafeEnchantment(enchantment, 1);
		return this;
	}

	public ItemBuilder type(Material material) {
		is.setType(material);
		return this;
	}

	public ItemBuilder clearLore() {
		ItemMeta meta = is.getItemMeta();
		meta.setLore(new ArrayList<>());
		is.setItemMeta(meta);
		return this;
	}

	public ItemBuilder clearEnchantments() {
		for (Enchantment e : is.getEnchantments().keySet()) {
			is.removeEnchantment(e);
		}

		return this;
	}

	public ItemBuilder color(org.bukkit.Color color) {
		if (is.getType() == Material.LEATHER_BOOTS || is.getType() == Material.LEATHER_CHESTPLATE || is.getType() == Material.LEATHER_HELMET || is.getType() == Material.LEATHER_LEGGINGS) {
			LeatherArmorMeta meta = (LeatherArmorMeta) is.getItemMeta();
			meta.setColor(color);
			is.setItemMeta(meta);
			return this;
		} else {
			throw new IllegalArgumentException("color() only applicable for leather armor.");
		}
	}

	public ItemBuilder owner(String owner) {
		SkullMeta meta = (SkullMeta) is.getItemMeta();
		meta.setOwner(owner);
		is.setItemMeta(meta);
		return this;
	}

	public ItemStack build() {
		return is;
	}
	
}