package com.thesevenq.uhc.utilties.items;

import com.mongodb.BasicDBObject;
import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.utilties.Color;
import com.thesevenq.uhc.utilties.Msg;
import com.thesevenq.uhc.utilties.item.ItemBuilder;
import org.bson.Document;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.thesevenq.uhc.player.UHCData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StatsItems {

	private static Comparator<UHCData> TOP_KD_COMPARATOR = Comparator.comparingDouble(UHCData::getKD).reversed();

	public static ItemStack getTopKillsItem() {
		return getItem(Material.DIAMOND_SWORD, "Total Kills", "kills");
	}
	
	public static ItemStack getTopWinsItem() {
		return getItem(Material.NETHER_STAR, "Wins", "wins");
	}

	public static ItemStack getTopDiamondsItem() {
		return getItem(Material.DIAMOND, "Total Diamonds", "diamonds");
	}
	
	public static ItemStack getTopPlayedItem() {
		return getItem(Material.BOOK, "Played", "played");
	}
	
	public static ItemStack getTopDeathsItem() {
		return getItem(Material.BONE, "Deaths", "deaths");
	}
	
	public static ItemStack getTopStreakItem() {
		return getItem(Material.BEACON, "Top Killstreak", "killStreak");
	}
	
	public static ItemStack getTopKDItem() {
		ItemStack stack = new ItemStack(Material.ARROW);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3KD &7| &bTop 10"));
		
		List<String> lore = new ArrayList<>();
		lore.add(Msg.BIG_LINE);
		
		List<UHCData> data = new ArrayList<>(UHCData.getUhcDatas().values().stream().filter(x -> x instanceof UHCData).map(x -> (UHCData) x).filter(x -> x.getKD() > 0).collect(Collectors.toSet()));
		Collections.sort(data, TOP_KD_COMPARATOR);
		Collections.reverse(data);
		
		for(int i = 0; i < 10; i++) {
			if(i >= data.size()) {
				break;
			}

			UHCData next = data.get(i);
			
			lore.add(Color.translate("&b#" + (i + 1) + ") &3" + next.getName() + " &b(" + next.getKD() + ")"));
		}

		lore.add(Msg.BIG_LINE);

		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}

	public static ItemStack getItem(Material material, String textName, String mongoValue) {
		List<Document> documents = (List<Document>) UHC.getInstance().getDatabaseManager().getUhcProfiles().find().limit(10).sort(new BasicDBObject(mongoValue, Integer.valueOf(-1))).into(new ArrayList());

		ItemBuilder builder = new ItemBuilder(material).name("&3" + textName + " &7| &bTop 10");
		int index = 1;

		List<String> tLore = new ArrayList<>();

		tLore.add(Msg.BIG_LINE);

		for(Document document : documents) {
			String name = document.getString("realName");
			if(name == null) name = document.getString("name");
			int value = document.getInteger(mongoValue).intValue();

			tLore.add("&b#" + index++ + ": &3" + name + " &b(" + value + ")");
		}

		tLore.add(Msg.BIG_LINE);
		builder.lore(tLore);

		return builder.build();
	}
}
