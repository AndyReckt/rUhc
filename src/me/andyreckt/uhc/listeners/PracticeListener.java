package me.andyreckt.uhc.listeners;

import me.andyreckt.uhc.managers.GameManager;
import me.andyreckt.uhc.player.state.GameState;
import me.andyreckt.uhc.utilties.BaseListener;
import me.andyreckt.uhc.utilties.InventoryUtils;
import me.andyreckt.uhc.utilties.UHCUtils;
import me.andyreckt.uhc.UHC;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

public class PracticeListener extends BaseListener implements Listener {


	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		if(!isInPractice(player)) return;
		
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);

		plugin.getPracticeManager().getUsers().remove(player.getUniqueId());
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if(!(event.getEntity() instanceof Player)) return;

		Player player = ((Player) event.getEntity()).getPlayer();

		if(!isInPractice(player)) return;

		if(event.getCause() != EntityDamageEvent.DamageCause.FALL) return;

		event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity().getPlayer();

		if(!isInPractice(player)) return;

		event.getDrops().clear();

		player.setHealth(20.0);
		player.setFoodLevel(20);
		player.setSaturation(10);



		new BukkitRunnable() {
			public void run() {
				try {
					player.getInventory().clear();
					player.getInventory().setArmorContents(null);

					String items = plugin.getUtiltiesConfig().getString("uhcpractice.inventory");
					String armor = plugin.getUtiltiesConfig().getString("uhcpractice.armor");

					player.getInventory().setContents(InventoryUtils.fromBase64(items).getContents());
					player.getInventory().setArmorContents(InventoryUtils.itemStackArrayFromBase64(armor));
					MultiSpawnListener.randomSpawnUHCPractice(player);
					player.updateInventory();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.runTaskLater(UHC.getInstance(), 10L);

		if (player.getKiller() != null) {
			player.getKiller().setFoodLevel(20);
			player.getKiller().setSaturation(20);
			player.getKiller().getInventory().addItem(UHCUtils.getGoldenHead());

			player.getKiller().updateInventory();
		}
	}

	public static boolean isInPractice(Player player) {
		return UHC.getInstance().getPracticeManager().getUsers().contains(player.getUniqueId());
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPracticePvP(EntityDamageByEntityEvent event){

		if(!GameManager.getGameState().equals(GameState.LOBBY)) return;

		if(!event.getEntity().getType().equals(EntityType.PLAYER)) return;
		Player player = (Player) event.getEntity();
		if(!isInPractice(player)) return;
		if(!event.getDamager().getType().equals(EntityType.PLAYER)) return;
		Player damager = (Player) event.getDamager();
		if(!isInPractice(damager)) return;
		event.setCancelled(false);


	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPracticePvP(EntityDamageEvent event){

		if(!GameManager.getGameState().equals(GameState.LOBBY)) return;

		if(!event.getEntity().getType().equals(EntityType.PLAYER)) return;
		Player player = (Player) event.getEntity();
		if(!isInPractice(player)) return;
		if(!event.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)) return;

		event.setCancelled(false);
	}

}
