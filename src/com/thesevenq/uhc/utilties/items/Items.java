package com.thesevenq.uhc.utilties.items;

import com.thesevenq.uhc.utilties.Color;
import com.thesevenq.uhc.utilties.UHCUtils;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.thesevenq.uhc.UHC;

import java.util.ArrayList;
import java.util.List;

public class Items {
	
	@SuppressWarnings("deprecation")
	public static ItemStack getBack() {
		ItemStack stack = new ItemStack(Material.WOOL, 1, DyeColor.RED.getData());
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&c&lBack"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&c» Right Click to go back «"));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getUHCPractice() {
		ItemStack stack = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lPractice"));

		List<String> lore = new ArrayList<String>();
		lore.add("");
		if(UHC.getInstance().getPracticeManager().isOpen()) {
			lore.add(Color.translate("&7Status&7: &aEnabled"));
			lore.add(Color.translate("&7Default Value&7: &cDisabled"));
		} else {
			lore.add(Color.translate("&7Status&7: &cDisabled"));
			lore.add(Color.translate("&7Default Value&7: &cDisabled"));
		}

		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getStartInventory() {
		ItemStack stack = new ItemStack(Material.COOKED_BEEF);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lStarter Inventory"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Custom items in inventory when uhc starts."));

		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack getEnablePractice() {
		ItemStack stack = new ItemStack(Material.WOOL, 1, DyeColor.GREEN.getData());
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lToggle UHCPractice ON"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Right click to enable UHCPracttice."));

		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack getRates() {
		ItemStack stack = new ItemStack(Material.APPLE);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lRates"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Change apple and shears rate."));

		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack getDisablePractice() {
		ItemStack stack = new ItemStack(Material.WOOL, 1, DyeColor.RED.getData());
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lToggle UHCPractice OFF"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Right click to enable UHCPracttice."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getCurrentScenarios() {
		ItemStack stack = new ItemStack(Material.CHEST);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lCurrent Scenarios"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Right click to open"));
		lore.add(Color.translate("&7current scenarios inventory."));
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getToggleScenarios() {
		ItemStack stack = new ItemStack(Material.CHEST);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lToggle Scenarios"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Right click to open"));
		lore.add(Color.translate("&7toggle scenarios inventory."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getDisableAllScenarios() {
		ItemStack stack = new ItemStack(Material.REDSTONE);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lDisable All Scenarios"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Right click to disable"));
		lore.add(Color.translate("&7all scenarios."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getParties() {
		ItemStack stack = new ItemStack(Material.PAPER);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lParties"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Right click to open"));
		lore.add(Color.translate("&7Parties inventory."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getPartiesEnable() {
		ItemStack stack = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lEnable Parties"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Left Click to enable Parties."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getScenarios() {
		ItemStack stack = new ItemStack(Material.CHEST);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lScenarios"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Right click to open"));
		lore.add(Color.translate("&7scenarios inventory."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getOptions() {
		ItemStack stack = new ItemStack(Material.WATCH);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lOptions"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Right click to open"));
		lore.add(Color.translate("&7options inventory."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getMaxPlayers() {
		ItemStack stack = new ItemStack(Material.IRON_HELMET);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lMax Players"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Right click to open"));
		lore.add(Color.translate("&7max players inventory."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}

	public static ItemStack getConfigItem() {
		ItemStack stack = new ItemStack(Material.BOOK);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lEdit Config/Scenarios"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Right click this item"));
		lore.add(Color.translate("&7to edit configs scenarios etc.."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getMaxOnlinePlus50() {
		ItemStack stack = new ItemStack(Material.DIAMOND);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lMax Players &f+ 50"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Left Click to add"));
		lore.add(Color.translate("&750 slots to max players."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getMaxOnlinePlus10() {
		ItemStack stack = new ItemStack(Material.GOLD_INGOT);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lMax Players &f+ 10"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Left Click to add"));
		lore.add(Color.translate("&710 slots to max players."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getMaxOnlinePlus5() {
		ItemStack stack = new ItemStack(Material.IRON_INGOT);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lMax Players &f+ 5"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Left Click to add"));
		lore.add(Color.translate("&75 slots to max players."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getCurrentMaxPlayers() {
		ItemStack stack = new ItemStack(Material.SKULL_ITEM);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lCurrent Max Players &f- " + Bukkit.getMaxPlayers()));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Hold this item to"));
		lore.add(Color.translate("&7see max players."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getMaxOnlineMinus50() {
		ItemStack stack = new ItemStack(Material.DIAMOND);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lMax Players &f- 50"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Left Click to remove"));
		lore.add(Color.translate("&750 slots from max players."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getMaxOnlineMinus10() {
		ItemStack stack = new ItemStack(Material.GOLD_INGOT);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lMax Players &f- 10"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Left Click to remove"));
		lore.add(Color.translate("&710 slots from max players."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getMaxOnlineMinus5() {
		ItemStack stack = new ItemStack(Material.IRON_INGOT);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lMax Players &f- 5"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Left Click to remove"));
		lore.add(Color.translate("&75 slots from max players."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getAppleRateMinus1() {
		ItemStack stack = new ItemStack(Material.APPLE);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lApple Rate &f- 1%"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Left Click to remove"));
		lore.add(Color.translate("&71% from apple rate."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getAppleRateMinus2() {
		ItemStack stack = new ItemStack(Material.GOLDEN_APPLE, 1, ((short) 0));
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lApple Rate &f- 2%"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Left Click to remove"));
		lore.add(Color.translate("&72% from apple rate."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getAppleRatePlus1() {
		ItemStack stack = new ItemStack(Material.APPLE);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lApple Rate &f+ 1%"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Left Click to add"));
		lore.add(Color.translate("&71% to apple rate."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getAppleRatePlus2() {
		ItemStack stack = new ItemStack(Material.GOLDEN_APPLE, 1, ((short) 0));
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lApple Rate &f+ 2%"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Left Click to add"));
		lore.add(Color.translate("&72% to apple rate."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getShearsRateMinus1() {
		ItemStack stack = new ItemStack(Material.APPLE);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lShears Rate &f- 1%"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Left Click to remove"));
		lore.add(Color.translate("&71% from shears."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getShearsRateMinus2() {
		ItemStack stack = new ItemStack(Material.GOLDEN_APPLE, 1, ((short) 0));
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lShears Rate &f- 2%"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Left Click to remove"));
		lore.add(Color.translate("&72% from shears."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getShearsRatePlus1() {
		ItemStack stack = new ItemStack(Material.APPLE);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lShears Rate &f+ 1%"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Left Click to add"));
		lore.add(Color.translate("&b1% &7to shears."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getShearsRatePlus2() {
		ItemStack stack = new ItemStack(Material.GOLDEN_APPLE, 1, ((short) 0));
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lShears Rate &f+ 2%"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Left Click to add"));
		lore.add(Color.translate("b72% &7to shears."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getPartiesOpenMoreInfo() {
		ItemStack stack = new ItemStack(Material.PAPER);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lOpen for more info"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Left Click to open"));
		lore.add(Color.translate("&7for more info."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	
	public static ItemStack getPartiesDisable() {
		ItemStack stack = new ItemStack(Material.GOLD_SWORD);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lDisable Parties"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Left Click to disable Parties."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getIsPartiesEnabled() {
		ItemStack stack = new ItemStack(Material.IRON_CHESTPLATE);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lTeam Size &7- &f" + UHCUtils.isPartiesEnabled()));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Hold this item to see"));
		lore.add(Color.translate("&7current party size."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getIsUHCPracticeEnabled() {
		ItemStack stack = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lPractice &7- &f" + UHCUtils.isUHCPracticeEnabled()));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Hold this item to see"));
		lore.add(Color.translate("&7is uhcpractice enabled."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getTeamAdd1() {
		ItemStack stack = new ItemStack(Material.DIAMOND);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lTeam Size &f+ 1"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Left Click to add"));
		lore.add(Color.translate("&71 party to party size."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getTeamRemove1() {
		ItemStack stack = new ItemStack(Material.GOLD_INGOT);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lTeam Size &f- 1"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Left Click to remove"));
		lore.add(Color.translate("&71 party from party size."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getAppleRate() {
		ItemStack stack = new ItemStack(Material.APPLE);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lApple Rate &f- " + UHC.getInstance().getGameManager().getAppleRate() + ".0%"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(Color.translate("&7Hold this item to see"));
		lore.add(Color.translate("&7current apple rate."));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack getShears() {
		ItemStack stack = new ItemStack(Material.SHEARS);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(Color.translate("&3&lShears &f- Enabled"));
		
		List<String> lore = new ArrayList<>();
		lore.add(Color.translate("&7Hold this item to see"));
		lore.add(Color.translate("&7is shears enabled."));
		lore.add("");
		lore.add(Color.translate("&bRate " + UHC.getInstance().getGameManager().getShearsRate() + ".0%"));
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}

	public static ItemStack getGlass() {
		ItemStack stack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7);
		ItemMeta meta = stack.getItemMeta();

		meta.setDisplayName(Color.translate("&3-"));
		stack.setItemMeta(meta);

		return stack;
	}

}