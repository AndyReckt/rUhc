package me.andyreckt.uhc.scenario.type;

import me.andyreckt.uhc.managers.GameManager;
import me.andyreckt.uhc.managers.ScenarioManager;
import me.andyreckt.uhc.scenario.Scenario;
import me.andyreckt.uhc.utilties.Msg;
import me.andyreckt.uhc.utilties.UHCUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.player.state.GameState;

import java.util.List;

public class TimeBombScenario extends Scenario implements Listener {

	public TimeBombScenario() {
		super("Time Bomb", Material.TNT, "When player dies,", "their loot will drop into a chest.", "After 30s, the chest will explode.");
	}

	public static void handleDeath(List<ItemStack> drops, Entity entity) {
		if(!GameManager.getGameState().equals(GameState.PLAYING)) return;

		if(!(entity instanceof Player)) return;

		Player victim = (Player) entity;

		Location where = victim.getLocation();

		drops.clear();

		where.getBlock().setType(Material.CHEST);
		Chest chest = (Chest) where.getBlock().getState();

		where.add(1, 0, 0).getBlock().setType(Material.CHEST);
		where.add(0, 1, 0).getBlock().setType(Material.AIR);
		where.add(1, 1, 0).getBlock().setType(Material.AIR);

		chest.getInventory().addItem(UHCUtils.getGoldenHead());

		for(ItemStack itemStack : victim.getInventory().getArmorContents()) {
			if(itemStack == null || itemStack.getType() == Material.AIR) {
				continue;
			}

			chest.getInventory().addItem(itemStack);
		}

		for(ItemStack itemStack : victim.getInventory().getContents()) {
			if(itemStack == null || itemStack.getType() == Material.AIR) {
				continue;
			}

			chest.getInventory().addItem(itemStack);
		}

		if(ScenarioManager.getByName("Golden Retriever").isEnabled()) {
			chest.getInventory().addItem(UHCUtils.getGoldenHead());
		}

		if(ScenarioManager.getByName("Diamondless").isEnabled()) {
			chest.getInventory().addItem(new ItemStack(Material.DIAMOND, 1));
		}

		if(ScenarioManager.getByName("Goldless").isEnabled()) {
			chest.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, 8));
			chest.getInventory().addItem(UHCUtils.getGoldenHead());
		}

		if(ScenarioManager.getByName("BareBones").isEnabled()) {
			chest.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 2));
			chest.getInventory().addItem(new ItemStack(Material.DIAMOND));
			chest.getInventory().addItem(new ItemStack(Material.ARROW, 32));
			chest.getInventory().addItem(new ItemStack(Material.STRING, 2));
		}

		/*Hologram hologram = HologramAPI.createHologram(chest.getLocation().clone().add(0.5, 1, 0), Color.translate("&a31s"));

		hologram.spawn();

		new BukkitRunnable() {
			private int time = 31;

			public void run() {
				time--;

				if(time == 0) {
					hologram.despawn();

					this.cancel();
					return;
				} else if(time == 1) {
					hologram.setText(Color.translate("&4" + time + "s"));
				} else if(time == 2) {
					hologram.setText(Color.translate("&c" + time + "s"));
				} else if(time == 3) {
					hologram.setText(Color.translate("&6" + time + "s"));
				} else if(time <= 15) {
					hologram.setText(Color.translate("&e" + time + "s"));
				} else {
					hologram.setText(Color.translate("&a" + time + "s"));
				}
			}
		}.runTaskTimer(UHC.getInstance(), 0L, 20L);*/

		String name = victim.getName();

		new BukkitRunnable() {
			public void run() {

				if((where.getBlockX() < 101 && where.getBlockZ() < 101)
						|| (where.getBlockX() < -101 && where.getBlockZ() < -101)
						|| (where.getBlockX() < 101 && where.getBlockZ() < -101)
						|| (where.getBlockX() < -101 && where.getBlockZ() < 101)) {
					for(int x = where.getBlockX() - 3; x < where.getBlockX() + 3; x++) {
						for(int y = where.getBlockY() - 3; y < where.getBlockY() + 3; y++) {
							for(int z = where.getBlockZ() - 3; z < where.getBlockZ() + 3; z++) {
								Location location = new Location(Bukkit.getWorld("uhc_world"), x, y, z);
								if(location.getBlock().getType() == Material.CHEST) {
									location.getBlock().setType(Material.AIR);
								}
							}
						}
					}

					Msg.sendMessage("&9" + name + "'s &ecorpse has exploded.");

					where.getWorld().createExplosion(where.getBlockX() + 0.5D, where.getBlockY() + 1, where.getBlockZ() + 0.5D, 10.0F, false, false);
					where.getWorld().strikeLightning(where);
				} else {
					where.getBlock().setType(Material.AIR);

					Msg.sendMessage("&9" + name + "'s &ecorpse has exploded.");

					where.getWorld().createExplosion(where.getBlockX() + 0.5D, where.getBlockY() + 1, where.getBlockZ() + 0.5D, 10.0F, false, true);
					where.getWorld().strikeLightning(where);
				}
			}
		}.runTaskLater(UHC.getInstance(), 600L);
	}

	@EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
		event.getDrops().clear();
    }
}
