package com.thesevenq.uhc.listeners;

import com.thesevenq.uhc.managers.OptionManager;
import com.thesevenq.uhc.utilties.BaseListener;
import com.thesevenq.uhc.utilties.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class OptionsListener extends BaseListener implements Listener {
	
	@EventHandler
	public void onAbsorptionOption(PlayerItemConsumeEvent event) {
		Player player = event.getPlayer();
		
		if(OptionManager.getByName("Absorption").getValue() == 1) return;
		
		new BukkitRunnable() {
			public void run() {
				player.removePotionEffect(PotionEffectType.ABSORPTION);
			}
		}.runTaskLaterAsynchronously(plugin, 3L);	
	}
	
	@EventHandler
	public void onGodApplesOption(CraftItemEvent event) {
		if(!(event.getWhoClicked() instanceof Player)) return;

		Player player = (Player) event.getWhoClicked();
		
		if(OptionManager.getByName("God Apples").getValue() == 1) return;
		
		ItemStack item = event.getCurrentItem();
		
		if(!item.getType().equals(Material.GOLDEN_APPLE)) return;
		if(item.getDurability() != 1) return;
		
		event.setCancelled(true);

		player.sendMessage(Color.translate("&cYou can't craft &lGod Apples&c while &lGod Apples&c are disabled."));
	}

	@EventHandler
	public void onGoldenHeadsOption(CraftItemEvent event) {
		if(!(event.getWhoClicked() instanceof Player)) return;
		
		Player player = (Player) event.getWhoClicked();

		if(OptionManager.getByName("Golden Heads").getValue() == 1) return;

		ItemStack item = event.getCurrentItem();

		if(item == null) return;
		if(item.getType() != Material.GOLDEN_APPLE) return;
		if(!item.getItemMeta().getDisplayName().equalsIgnoreCase(Color.translate("&5&lGolden Head"))) return;

		event.setCancelled(true);

		player.sendMessage(Color.translate("&cYou can't craft &lGolden Heads&c while &lGolden Heads&c are disabled."));
	}
	
	@EventHandler
	public void onInvisibilityOption(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getItemInHand();

		if(item.getType() != Material.POTION) return;

		if(OptionManager.getByName("Invisibility Potions").getValue() == 1) return;

		switch(item.getDurability()) {
		    case 16462:
		    case 16430:
		    case 8270:
		    case 8238:
			
			event.setCancelled(true);

			player.sendMessage(Color.translate("&cYou can't use &lInvisibility Potions&c while &lInvisibility Potions&c are disabled."));

			item.setDurability((short) 0);
			break;
		}
	}
	
    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {
    	Player player = event.getPlayer();
    	
		if(OptionManager.getByName("Nether").getValue() == 1) return;
    	
		event.setCancelled(true);
        	
		player.sendMessage(Color.translate("&cYou can't enter &lNether World&c while &lNether&c is disabled.")); 
    }
    
    @EventHandler
    public void onPearlDamageOption(PlayerTeleportEvent event){
        Player player = event.getPlayer();
        
        if(OptionManager.getByName("Ender Pearl Damage").getValue() == 1) return;
        
        if(event.getCause() != TeleportCause.ENDER_PEARL) return;
        
        event.setCancelled(true);
        
        Location to = event.getTo();
        
		player.setNoDamageTicks(1);
		player.teleport(to);
    }
    
	@EventHandler
	public void onSpeedOption(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getItemInHand();

		if(item.getType() != Material.POTION) return;

		if(OptionManager.getByName("Speed Potions").getValue() == 1) return;

		switch(item.getDurability()) {
		    case 16450:
		    case 16418:
		    case 16386:
		    case 8258:
		    case 8226:
		    case 8194:
			
			event.setCancelled(true);

			player.sendMessage(Color.translate("&cYou can't use &lSpeed Potions&c while &lSpeed Potions&c are disabled."));

			item.setDurability((short) 0);
			break;
		}
	}
	
	@EventHandler
	public void onStrengthOption(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getItemInHand();

		if(item.getType() != Material.POTION) return;

		if(OptionManager.getByName("Strength Potions").getValue() == 1) return;

		switch(item.getDurability()) {
		    case 16457:
		    case 16425:
		    case 16393:
		    case 8265:
		    case 8233:
		    case 8201:
			
			event.setCancelled(true);

			player.sendMessage(Color.translate("&cYou can't use &lStrength Potions&c while &lStrength Potions&c are disabled."));

			item.setDurability((short) 0);
			break;
		}
	}
}
