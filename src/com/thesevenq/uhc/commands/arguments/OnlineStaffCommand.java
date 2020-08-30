package com.thesevenq.uhc.commands.arguments;

import com.thesevenq.uhc.utilties.Color;
import com.thesevenq.uhc.utilties.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.commands.BaseCommand;
import com.thesevenq.uhc.listeners.VanishListener;

import java.util.ArrayList;
import java.util.List;

public class OnlineStaffCommand extends BaseCommand {

	public OnlineStaffCommand(UHC plugin) {
		super(plugin);
		
		this.command = "onlinestaff";
		this.permission = Permission.STAFF_PERMISSION;
		this.forPlayerUseOnly = true;
	}
	
	private ItemStack playerHead;

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		
		player.openInventory(this.openOnlineStaff());
	}
	
	private Inventory openOnlineStaff() {
		Inventory inv = Bukkit.createInventory(null, 54, Color.translate("&a&lOnline Staff"));
		for(Player all : Bukkit.getOnlinePlayers()) {
    		if(all.hasPermission(Permission.STAFF_PERMISSION)) {
    			Player player = Bukkit.getPlayer(all.getName());
    			
    			if(player != null) {
    				this.playerHead = new ItemStack(Material.SKULL_ITEM, 1, ((short) 3));
    				ItemMeta meta = playerHead.getItemMeta();
    				List<String> lore = new ArrayList<String>();
    				meta.setDisplayName("§c§l" + player.getName());
    				if (VanishListener.isVanished(player)) {
    					lore.add("§7Vanish:" + ChatColor.GREEN + " Enabled");
    				} else {
    					lore.add("§7Vanish:" + ChatColor.RED + " Disabled");
    				}
    				if (player.getAllowFlight()) {
    					lore.add("§7Flight: §aEnabled");
    				} else {
    					lore.add("§7Flight: §cDisabled");
    				}
    				if (player.getGameMode() == GameMode.CREATIVE) {
    					lore.add("§7Gamemode:" + ChatColor.GREEN + " Creative");
    				} else if (player.getGameMode() == GameMode.SURVIVAL) {
    					lore.add("§7Gamemode:" + ChatColor.RED + " Survival");
    				} else if (player.getGameMode() == GameMode.ADVENTURE) {
    					lore.add("§7Gamemode:" + ChatColor.YELLOW + " Adventure");
    				}
    				    				
    				//lore.add("§7Rank: §f" + PowerfulPerms.getPrimaryGroup(player));
    				lore.add("");
    				lore.add("§7Left Click to teleport");
    				lore.add("§7to the §f" + player.getName() + "§7.");
    				meta.setLore(lore);
    				this.playerHead.setItemMeta(meta);
    				
    				inv.addItem(this.playerHead);
    			}
    		}
		}
		return inv;
	}

}
