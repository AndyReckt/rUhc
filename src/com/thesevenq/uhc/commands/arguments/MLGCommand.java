package com.thesevenq.uhc.commands.arguments;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.commands.BaseCommand;
import com.thesevenq.uhc.player.UHCData;
import com.thesevenq.uhc.utilties.Color;
import com.thesevenq.uhc.utilties.Msg;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;


public class MLGCommand extends BaseCommand {

	public static boolean inMLG = false;

	public static Set<UUID> mlgPlayers = new HashSet<>();
	public static Set<UUID> allowedMLGPlayers = new HashSet<>();
	
	public MLGCommand(UHC plugin) {
		super(plugin);
		
		this.command = "mlg";
		this.forPlayerUseOnly = true;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		if(!MLGCommand.allowedMLGPlayers.contains(player.getUniqueId())) {
			player.sendMessage(Color.translate("&cYou can't do MLG water bucket challange at this time."));
			return;
		}

		if(MLGCommand.inMLG) {
			player.sendMessage(Color.translate("&cMLG has already started."));
			return;
		}

		MLGCommand.allowedMLGPlayers.remove(player.getUniqueId());
		MLGCommand.mlgPlayers.add(player.getUniqueId());

		Msg.sendMessage("&3" + player.getName() + " &bis going to MLG.");
    }

	public static void doMLG() {
		MLGCommand.inMLG = true;

		if(MLGCommand.mlgPlayers.isEmpty()) {
			Msg.sendMessage("&bNone of the winners signed up for the MLG challenge?.");
			return;
		}

		new BukkitRunnable() {
			int ticks = 5;
			int delay = 0;

			int mlgCount = 0;

			@Override
			public void run() {
				if(this.delay != 0) {
					this.delay--;
					return;
				}

				if(this.ticks == 0) {
					if (this.mlgCount == 3) {
						StringBuilder sb = new StringBuilder();
						
						for(UUID uuid : MLGCommand.mlgPlayers) {
							Player player = UHC.getInstance().getServer().getPlayer(uuid);

							if(UHCData.getByName(player.getName()).isAlive()) {
								sb.append(", ");
								sb.append(player.getDisplayName());
							}
						}

						Msg.sendMessage("&bCongratulations to &3" + sb.toString().substring(2) + " &bfor being an MLG master.");

						this.cancel();
						return;
					}

					Iterator<UUID> it = MLGCommand.mlgPlayers.iterator();
					while(it.hasNext()) {
						UUID uuid = it.next();
						Player player = UHC.getInstance().getServer().getPlayer(uuid);

						if(UHCData.getByName(player.getName()).isAlive()) {

							Block block = null;
							
							while(block == null || block.isLiquid()) {
								block = player.getWorld().getHighestBlockAt(generateRandomInt(-20, 20), generateRandomInt(-20, 20));
							}

							player.getInventory().setHeldItemSlot(0);
							player.getInventory().setItem(0, new ItemStack(Material.WATER_BUCKET));
							player.updateInventory();

							player.teleport(block.getLocation().add(0, generateRandomInt(32, 72), 0));
						} else {
							it.remove();
						}
					}

					this.mlgCount++;

					this.ticks = 5;
					this.delay = 5;
				} else {
					if(MLGCommand.mlgPlayers.isEmpty()) {
						this.cancel();
						return;
					}

					boolean everyoneDied = true;

					for(UUID uuid : MLGCommand.mlgPlayers) {
						UHCData uhcData = UHCData.getByName(Bukkit.getPlayer(uuid).getName());
						
						if(uhcData.isAlive()) {
							everyoneDied = false;
							break;
						}
					}

					if(everyoneDied) {
						Msg.sendMessage("&bNo one was able to complete the MLG challenge!.");

						this.cancel();
						return;
					}

					String s = "first";

					if(this.mlgCount == 1) s = "second";
					else if(this.mlgCount == 2) s = "third";

					Msg.sendMessage("&bWinners &3" + s + " &bMLG at 0, 0 in &3" + this.ticks + "&b.");
					
					for(Player player : Bukkit.getOnlinePlayers()) {
						player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
					}
					
					this.ticks--;
				}
			}
		}.runTaskTimer(UHC.getInstance(), 20L, 20L);
	}
	
	public static int generateRandomInt(int min, int max) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}
}
