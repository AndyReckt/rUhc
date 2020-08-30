package com.thesevenq.uhc.managers;

import com.thesevenq.uhc.utilties.Color;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.config.Option;
import com.thesevenq.uhc.config.Options;
import com.thesevenq.uhc.utilties.Manager;

import java.util.*;

public class OptionManager extends Manager {

	@Getter
	private static List<Option> options = new ArrayList<>();
	
	public OptionManager(UHC plugin) {
		super(plugin);

		new Options();
		
		getByName("Border Shrink Interval").setDefaultValue(1);
		getByName("Final Heal").setDefaultValue(1);
		getByName("First Shrink").setDefaultValue(3);
		getByName("PvP Period Duration").setDefaultValue(1);
		
		getByName("Border Shrink Interval").setValue(1);
		getByName("Final Heal").setValue(1);
		getByName("First Shrink").setValue(8);
		getByName("PvP Period Duration").setValue(1);
	}

	public static Option getByName(String name) {
		for(Option option : options) {
			if(option.getName().equals(name)) {
				return option;
			}
		}

		return null;
	}
	
	public static int getByNameAndTranslate(String name) {
		for(Option option : options) {
			if(option.getName().toLowerCase().startsWith(name.toLowerCase())) {
				return getByName(name).translateValue(getByName(name).getValue());
			}
		}
		
		return 0;
	}

	public static ItemStack getConfigItem(Option option) {
		ItemStack item = new ItemStack(option.isBoolean() ? Material.SKULL_ITEM : Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3" + option.getName()));
		
		List<String> lore = new ArrayList<String>();

		lore.add("");
		lore.addAll(Arrays.asList(lore(option.getDesc(), Color.translate("&b"))));
		lore.add("");
		
		if(option.isBoolean()) {
			lore.add("&7Status: " + (option.isFinal() ? "&aEnabled" : "&cDisabled"));
			
			item.setDurability((short) (option.isFinal() ? 4 : 0));
		} else {
			String values = option.allTransValues();
			int value = option.getTranslatedValue();
			
			lore.add(Color.translate("&3" + values.replaceFirst(String.valueOf(value), "&7" + value + "&3")));
			item.setAmount(value > 64 ? option.getValue() + 1 : value);
		}
		
		lore.add("");
		lore.add("&7Default Value: &b" + option.defaultFormatted());
		lore.add("");

		meta.setLore(Color.translate(lore));
		item.setItemMeta(meta);
		return item;
	}

	public static String[] lore(String text, String color) {
		int dif = 32;
		String first = color, second = color, third = color;
		
		if(!(text.length() > dif)) return new String[] { color + text };

		if(text.length() > dif * 2) {
			first += text.substring(0, dif - 1);
			second += text.substring(dif, dif * 2);
			third += text.substring(dif * 2, text.length());
			
			return new String[] { first, second, third };
		} else if(text.length() > dif) {
			first += text.substring(0, dif - 1);
			second += text.substring(dif - 1, text.length());
			
			return new String[] { first, second };
		}
		
		return null;
	}
	
}
