package com.thesevenq.uhc.utilties.item;

import java.util.LinkedHashMap;
import java.util.Map;

import com.thesevenq.uhc.UHC;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import lombok.Getter;

@Getter
public class ItemData implements ConfigurationSerializable {
	
	private Material material;
	private short itemData;

	public ItemData(MaterialData data) {
		this(data.getItemType(), data.getData());
	}

	public ItemData(ItemStack stack) {
		this(stack.getType(), stack.getData().getData());
	}

	public ItemData(Material material, short itemData) {
		this.material = material;
		this.itemData = itemData;
	}

	public ItemData(Map<String, Object> map) {
		Object object = map.get("itemType");
		
		if(!(object instanceof String)) {
			throw new AssertionError("Incorrectly configurised");
		}
		
		this.material = Material.getMaterial(((String) object));
		
		if((object = map.get("itemData")) instanceof Short) {
			this.itemData = (short) object;
			return;
		}
		
		throw new AssertionError("Incorrectly configurised");
	}

	public static ItemData fromItemName(String string) {
		ItemStack stack = UHC.getInstance().getItemDB().getItem(string);
		
		return new ItemData(stack.getType(), stack.getData().getData());
	}

	public static ItemData fromStringValue(String value) {
		int firstBracketIndex = value.indexOf(40);
		
		if(firstBracketIndex == -1) {
			return null;
		}
		
		int otherBracketIndex = value.indexOf(41);
		
		if(otherBracketIndex == -1) {
			return null;
		}
		
		String itemName = value.substring(0, firstBracketIndex);
		String itemData = value.substring(firstBracketIndex + 1, otherBracketIndex);
		
		Material material = Material.getMaterial(itemName);
		
		return new ItemData(material, Short.parseShort(itemData));
	}

	public Map<String, Object> serialize() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		
		map.put("itemType", this.material.name());
		map.put("itemData", this.itemData);
		
		return map;
	}

	public String getItemName() {
		return UHC.getInstance().getItemDB().getName(new ItemStack(this.material, 1, this.itemData));
	}

	@Override
	public String toString() {
		return String.valueOf(this.material.name()) + "(" + String.valueOf(this.itemData) + ")";
	}
}
