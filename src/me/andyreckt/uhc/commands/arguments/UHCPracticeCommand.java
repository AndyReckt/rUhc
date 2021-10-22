package me.andyreckt.uhc.commands.arguments;

import me.andyreckt.uhc.utilties.*;
import me.andyreckt.uhc.managers.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.commands.BaseCommand;
import me.andyreckt.uhc.listeners.MultiSpawnListener;
import me.andyreckt.uhc.player.state.GameState;
import me.andyreckt.uhc.tasks.PracticeTask;

import java.io.IOException;

public class UHCPracticeCommand extends BaseCommand {

	public UHCPracticeCommand(UHC plugin) {
		super(plugin);
		
		this.command = "uhcpractice";
		this.forPlayerUseOnly = true;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		
		if(args.length == 0) {
			this.sendUsage(sender);
			return;
		}
		
		if(args[0].equalsIgnoreCase("join")) {
			if(!plugin.getPracticeManager().isOpen()) {
				player.sendMessage(Color.translate("&cPractice is currently disabled."));
				return;
			}
			
			if(GameManager.getGameState().equals(GameState.PLAYING)) {
				player.sendMessage(Color.translate("&cUHC is currently running."));
				return;
			}
			
			if(plugin.getPracticeManager().getUsers().size() > plugin.getPracticeManager().getSlots()) {
				player.sendMessage(Color.translate("&cPractice is full."));
				return;
			}
									
			if(plugin.getPracticeManager().getUsers().contains(player.getUniqueId())) {
				player.sendMessage(Color.translate("&cYou are already in Practice."));
				return;
			}
			
			plugin.getPracticeManager().getUsers().add(player.getUniqueId());


			
			player.sendMessage(Color.translate("&eYou have &ejoined &9Practice&e."));
			
			new BukkitRunnable() {
				public void run() {
					try {

						player.getInventory().clear();
						player.setGameMode(GameMode.SURVIVAL);

						player.getInventory().setHelmet(new ItemStack(Material.AIR));
						player.getInventory().setChestplate(new ItemStack(Material.AIR));
						player.getInventory().setLeggings(new ItemStack(Material.AIR));
						player.getInventory().setBoots(new ItemStack(Material.AIR));

						String items = plugin.getUtiltiesConfig().getString("uhcpractice.inventory");
						String armor = plugin.getUtiltiesConfig().getString("uhcpractice.armor");

						player.getInventory().setContents(InventoryUtils.fromBase64(items).getContents());
						player.getInventory().setArmorContents(InventoryUtils.itemStackArrayFromBase64(armor));

						player.updateInventory();
						MultiSpawnListener.randomSpawnUHCPractice(player);
						player.sendMessage(Color.translate("&eYou have &ereceived &9Kit&e."));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.runTaskLater(this.getInstance(), 20L);
		} else if(args[0].equalsIgnoreCase("leave")) {
			if(plugin.getPracticeManager().getUsers().contains(player.getUniqueId())) {
				plugin.getPracticeManager().getUsers().remove(player.getUniqueId());
				
				player.getInventory().clear();
				
				player.getInventory().setHelmet(new ItemStack(Material.AIR));
				player.getInventory().setChestplate(new ItemStack(Material.AIR));
				player.getInventory().setLeggings(new ItemStack(Material.AIR));
				player.getInventory().setBoots(new ItemStack(Material.AIR));
				
				for(PotionEffect effect : player.getActivePotionEffects()) {
					player.removePotionEffect(effect.getType());
				}
				
				if(!GameManager.getGameState().equals(GameState.PLAYING)) {
					if(player.hasPermission(Permission.OP_PERMISSION)) {
						new BukkitRunnable() {
							public void run() {
								UHCUtils.loadLobby(player);
								
								player.setHealth(20.0);
								player.setFoodLevel(20);
								player.setSaturation(10);
								player.setGameMode(GameMode.CREATIVE);
							}
						}.runTaskLater(this.getInstance(), 10L);
					} else {
						new BukkitRunnable() {
							public void run() {
								UHCUtils.loadLobby(player);
								
								player.setHealth(20.0);
								player.setFoodLevel(20);
								player.setSaturation(10);
								player.setGameMode(GameMode.ADVENTURE);
							}
						}.runTaskLater(this.getInstance(), 10L);
					}
				}
				
				MultiSpawnListener.randomSpawn(player);
			} else {
				sender.sendMessage(Color.translate("&cYou are not in UHCPractice arena."));
			}
		} else if(args[0].equalsIgnoreCase("setkit")) {
			if(player.hasPermission(Permission.OP_PERMISSION)) {
				UHC.getInstance().getUtiltiesConfig().set("uhcpractice.inventory", InventoryUtils.toBase64(player.getInventory()));
				UHC.getInstance().getUtiltiesConfig().set("uhcpractice.armor", InventoryUtils.itemStackArrayToBase64(player.getInventory().getArmorContents()));

				try {
					plugin.getUtiltiesConfig().save(plugin.getUtiltiesConfig().getFile());
				} catch (IOException e) {
					e.printStackTrace();
				}

				player.getInventory().clear();
				player.getInventory().setHelmet(new ItemStack(Material.AIR));
				player.getInventory().setChestplate(new ItemStack(Material.AIR));
				player.getInventory().setLeggings(new ItemStack(Material.AIR));
				player.getInventory().setBoots(new ItemStack(Material.AIR));

				player.sendMessage(Color.translate("&eYou have set &9UHCPractice &eitems."));
			}
		} else if(args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("true")  || args[0].equalsIgnoreCase("enable")) {
			if(player.hasPermission(Permission.OP_PERMISSION)) {
				if(GameManager.getGameState().equals(GameState.PLAYING)) {
					player.sendMessage(Color.translate("&cUHC is currently running."));
					return;
				}
				
				if(plugin.getPracticeManager().isOpen()) {
					sender.sendMessage(Color.translate("&cUHCPractice is already enabled."));
					return;
				}
				
				Msg.sendMessage("&9Practice&e has been &aEnabled&e by &9" + sender.getName() + "&e.");
				Msg.sendMessage("&eYou can join this by typing &9/uhcpractice join&e.");

				new PracticeTask();
				plugin.getPracticeManager().setOpen(true);
			}
		} else if(args[0].equalsIgnoreCase("off") || args[0].equalsIgnoreCase("false")  || args[0].equalsIgnoreCase("disable")) {
			if(player.hasPermission(Permission.OP_PERMISSION)) {
				if(!plugin.getPracticeManager().isOpen()) {
					sender.sendMessage(Color.translate("&cUHCPractice is already disabled."));
					return;
				}
				
				Msg.sendMessage("&9Practice&e has been &cDisabled&e by &9" + sender.getName() + "&e.");

				for (Player online : Bukkit.getOnlinePlayers()) {
					if(plugin.getPracticeManager().getUsers().contains(online.getUniqueId())) {
						online.getInventory().clear();

						online.getInventory().setHelmet(new ItemStack(Material.AIR));
						online.getInventory().setChestplate(new ItemStack(Material.AIR));
						online.getInventory().setLeggings(new ItemStack(Material.AIR));
						online.getInventory().setBoots(new ItemStack(Material.AIR));
						
						if(!GameManager.getGameState().equals(GameState.PLAYING)) {
							if(online.hasPermission(Permission.OP_PERMISSION)) {
								new BukkitRunnable() {
									public void run() {
										UHCUtils.loadLobby(online);
										
										online.setHealth(20.0);
										online.setFoodLevel(20);
										online.setSaturation(10);
										online.setGameMode(GameMode.ADVENTURE);
									}
								}.runTaskLater(this.getInstance(), 10L);
							} else {
								new BukkitRunnable() {
									public void run() {
										UHCUtils.loadLobby(online);
										
										online.setHealth(20.0);
										online.setFoodLevel(20);
										online.setSaturation(10);
										online.setGameMode(GameMode.ADVENTURE);
									}
								}.runTaskLater(this.getInstance(), 10L);
							}
						}

						plugin.getPracticeManager().getUsers().remove(online.getUniqueId());

						MultiSpawnListener.randomSpawn(online);
					}
				}

				plugin.getPracticeManager().setOpen(false);
			}
		} else if(args[0].equalsIgnoreCase("slots")) {
			if(sender.hasPermission(Permission.OP_PERMISSION)) {
				if(args.length < 2) {
					this.sendUsage(sender);
					return;
				}
				
				int slots = Integer.valueOf(args[1]);
				
				if(!NumberUtils.isInteger(args[1])) {
					sender.sendMessage(Color.translate("&cInvalid number."));
					return;
				}
				
				if(slots > 150) {
					sender.sendMessage(Color.translate("&cMaximum slots for UHCPractice is &l150&c."));
					return;
				}

				plugin.getPracticeManager().setSlots(slots);

				Msg.sendMessage("&9Practice&e slots have been set to &9" + slots + "&e by &9" + sender.getName() + "&e.");
			}
		}
		
	}
	
	public void sendUsage(CommandSender sender) {
		sender.sendMessage(Color.translate("&cUHCPractice - Help Commands:"));
		sender.sendMessage(Color.translate("&c/uhcpractice join - Join UHCPractice."));
		sender.sendMessage(Color.translate("&c/uhcpractice leave - Leave UHCPractice."));
		if(sender.hasPermission(Permission.OP_PERMISSION)) {
			sender.sendMessage(Color.translate("&c/uhcpractice setkit - Set Kit for UHCPractice."));
			sender.sendMessage(Color.translate("&c/uhcpractice on|off - Enable/Disable UHCPractice."));
			sender.sendMessage(Color.translate("&c/uhcpractice slots <slots> - Set Slots."));
		}
	}

}
