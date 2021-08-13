package me.andyreckt.uhc.scenario.type;

import me.andyreckt.uhc.scenario.Scenario;
import me.andyreckt.uhc.utilties.Color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class LimitedEnchantsScenario extends Scenario implements Listener {

	public LimitedEnchantsScenario() {
		super("Limited Enchants", Material.ENCHANTMENT_TABLE, "You can't craft/break/place", "enchantment tables.", "Enchantment tables are", "spawned in 0|0 and 1500 each quadrant.");
	}

	public static void handleCraft(Player player, ItemStack item, CraftItemEvent event) {
		if(item.getType() != Material.ENCHANTMENT_TABLE) return;

		event.setCancelled(true);
		item = null;
		event.setResult(Result.DENY);

		player.sendMessage(Color.translate("&cYou can't craft enchantment tables while &lLimited Enchants&c scenario is active."));
	}

	public static void handleBreak(Player player, Block block, BlockBreakEvent event) {
		if(block.getType() != Material.ENCHANTMENT_TABLE) return;

		event.setCancelled(true);

		player.sendMessage(Color.translate("&cYou can't break enchantment tables while &lLimited Enchants&c scenario is active."));
	}

	public static void handlePlace(Player player, Block block, BlockPlaceEvent event) {
		if(block.getType() != Material.ENCHANTMENT_TABLE) return;

		event.setCancelled(true);

		player.sendMessage(Color.translate("&cYou can't place enchantment tables while &lLimited Enchants&c scenario is active."));
	}

	@EventHandler
	public void onCraftItem(CraftItemEvent event) {
		ItemStack item = event.getCurrentItem();
		
		if(item.getType() != Material.ENCHANTMENT_TABLE) return;

		event.setCancelled(true);

		event.setCurrentItem(null);
		event.setCursor(null);

		event.setResult(Result.DENY);
		
		Player player = (Player) event.getWhoClicked();

		player.sendMessage(Color.translate("&cYou can't craft enchantment tables while &lLimited Enchants&c scenario is active."));
		
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();

		if(block.getType() != Material.ENCHANTMENT_TABLE) return;

		event.setCancelled(true);
		
		Player player = event.getPlayer();
		
		player.sendMessage(Color.translate("&cYou can't break enchantment tables while &lLimited Enchants&c scenario is active."));
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Block block = event.getBlock();

		if(block.getType() != Material.ENCHANTMENT_TABLE) return;

		event.setCancelled(true);
		
		Player player = event.getPlayer();
		
		player.sendMessage(Color.translate("&cYou can't place enchantment tables while &lLimited Enchants&c scenario is active."));
	}
	
	public static void spawnEnchantmentTables() {
		World world = Bukkit.getWorld("uhc_world");
		
		world.getHighestBlockAt(0, 0).getLocation().getBlock().setType(Material.ENCHANTMENT_TABLE);

		world.getHighestBlockAt(1500, 1500).getLocation().getBlock().setType(Material.ENCHANTMENT_TABLE);
		world.getHighestBlockAt(1500, -1500).getLocation().getBlock().setType(Material.ENCHANTMENT_TABLE);
		world.getHighestBlockAt(-1500, 1500).getLocation().getBlock().setType(Material.ENCHANTMENT_TABLE);
		world.getHighestBlockAt(-1500, -1500).getLocation().getBlock().setType(Material.ENCHANTMENT_TABLE);
	}
 }
