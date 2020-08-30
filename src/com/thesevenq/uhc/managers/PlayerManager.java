package com.thesevenq.uhc.managers;

import com.thesevenq.uhc.utilties.Color;
import com.thesevenq.uhc.utilties.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.player.UHCData;
import com.thesevenq.uhc.utilties.Manager;
import com.thesevenq.uhc.utilties.items.StatsItems;
import java.util.HashSet;
import java.util.Set;

public class PlayerManager extends Manager {

	public PlayerManager(UHC plugin) {
		super(plugin);
	}

    
	public static Inventory getLeaderboard(Player player) {
		Inventory inventory = Bukkit.createInventory(null, 27, Color.translate("&bLeaderboards"));
				
		new BukkitRunnable() {
			public void run() {
				inventory.setItem(11, StatsItems.getTopKillsItem());
				inventory.setItem(12, StatsItems.getTopStreakItem());
				inventory.setItem(13, StatsItems.getTopWinsItem());
				inventory.setItem(14, StatsItems.getTopPlayedItem());
				inventory.setItem(15, StatsItems.getTopDiamondsItem());

				for(int i = 0; i < inventory.getSize(); i++) {
					if(inventory.getItem(i) == null) {
						inventory.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).name(" ").durability(7).build());
					}
				}
			}
		}.runTaskAsynchronously(UHC.getInstance());
		
		player.openInventory(inventory);
		
		return inventory;
	}
	
	 public static int getAlivePlayers() {
		int i = 0;

		for(UHCData uhcData : UHCData.uhcDatas.values()) {
			if(uhcData.isAlive()) {
				i++;
			}
		}

		return i;
	}
	    
	public static Set<UHCData> getUHCPlayerSet(Set<String> s) {
		Set<UHCData> set = new HashSet<>();

		for (String name : s) {
			set.add(UHCData.getByName(name));
		}

		return set;
	}
}
