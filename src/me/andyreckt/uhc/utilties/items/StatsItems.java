package me.andyreckt.uhc.utilties.items;

import com.mongodb.BasicDBObject;
import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.utilties.Color;
import me.andyreckt.uhc.utilties.Msg;
import me.andyreckt.uhc.utilties.item.ItemBuilder;
import org.bson.Document;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import me.andyreckt.uhc.player.UHCData;

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
		
		meta.setDisplayName(Color.translate("&9KD &7| &eTop 10"));
		
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
			
			lore.add(Color.translate("&e#" + (i + 1) + ") &9" + next.getName() + " &e(" + next.getKD() + ")"));
		}

		lore.add(Msg.BIG_LINE);

		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}

	public static ItemStack getItem(Material material, String textName, String mongoValue) {
		List<Document> documents = (List<Document>) UHC.getInstance().getDatabaseManager().getUhcProfiles().find().limit(10).sort(new BasicDBObject(mongoValue, Integer.valueOf(-1))).into(new ArrayList());

		ItemBuilder builder = new ItemBuilder(material).name("&9" + textName + " &7| &eTop 10");
		int index = 1;

		List<String> tLore = new ArrayList<>();

		tLore.add(Msg.BIG_LINE);

		for(Document document : documents) {
			String name = document.getString("realName");
			if(name == null) name = document.getString("name");
			int value = document.getInteger(mongoValue).intValue();

			tLore.add("&e#" + index++ + ": &9" + name + " &e(" + value + ")");
		}

		tLore.add(Msg.BIG_LINE);
		builder.lore(tLore);

		return builder.build();
	}
}
