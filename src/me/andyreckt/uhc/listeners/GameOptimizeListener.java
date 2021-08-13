package me.andyreckt.uhc.listeners;

import me.andyreckt.uhc.managers.GameManager;
import me.andyreckt.uhc.utilties.BaseListener;
import me.andyreckt.uhc.player.state.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.*;

public class GameOptimizeListener extends BaseListener implements Listener {

	private Material[] shit = { Material.SAPLING, Material.SEEDS };

	@EventHandler
	public void onBlockFromTo(BlockFromToEvent event) {
		if(!GameManager.getGameState().equals(GameState.PLAYING)) {
			event.setCancelled(true);
		}

		if(event.getBlock().getLocation().getWorld().getName().equalsIgnoreCase("world")) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onItemSpawn(ItemSpawnEvent event) {
		for(Material material : shit) {
			if(event.getEntity().getItemStack().getType() == material) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if(event.getEntity() instanceof Player && event.getEntity().getKiller() instanceof Player) {
			Player killer = event.getEntity().getKiller();

			if(killer.getItemInHand() != null) {
				killer.getWorld().spawn(killer.getLocation(), ExperienceOrb.class).setExperience(event.getDroppedExp() * 2);
			}
		}
	}

	/*@EventHandler
	public void onSpread(BlockSpreadEvent event) {
		int fromType = event.getSource().getTypeId();

		if(fromType == 2) {
			if(event.getBlock().getLocation().getWorld().getName().equalsIgnoreCase("world")) {
				event.setCancelled(true);
			}

			if(!GameManager.getGameState().equals(GameState.PLAYING)) {
				event.setCancelled(true);
			}
		}
	}*/

	@EventHandler
	public void onBlockFade(BlockFadeEvent event) {
		if(event.getBlock().getLocation().getWorld().getName().equalsIgnoreCase("world")) {
			if(((event.getBlock().getType() == Material.ICE) || (event.getBlock().getType() == Material.SNOW) || (event.getBlock().getType() == Material.SNOW_BLOCK))) {
				event.setCancelled(true);
			}
		}

		if(!GameManager.getGameState().equals(GameState.PLAYING)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityInteract(EntityInteractEvent event) {
		if(event.getBlock().getLocation().getWorld().getName().equalsIgnoreCase("world")) {
			if(event.getBlock().getType() == Material.CROPS && event.getEntity() instanceof LivingEntity) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onBlockForm(BlockFormEvent event) {
		if(event.getBlock().getLocation().getWorld().getName().equalsIgnoreCase("world")) {
			event.setCancelled(true);
		}

		if(!GameManager.getGameState().equals(GameState.PLAYING)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onFoodLevelChangeEvent(FoodLevelChangeEvent event) {
		if(!(event.getEntity() instanceof Player)) return;

		if(event.getEntity().getWorld().equals("world")) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onAgro(EntityTargetEvent event) {
		if(Bukkit.spigot().getTPS().length > 18 && Bukkit.getOnlinePlayers().size() < 350) {
			return;
		}

		event.setCancelled(true);
	}
	
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		if(event.getEntity().getLocation().getWorld().getName().equalsIgnoreCase("world")) {
			event.setCancelled(true);
			return;
		}

		if(Bukkit.spigot().getTPS().length < 19.5) {
			if(event.getEntity() instanceof Bat 
				|| event.getEntity() instanceof Enderman 
				|| event.getEntity() instanceof Pig 
				|| event.getEntity() instanceof Ghast
				|| event.getEntity() instanceof PigZombie
				|| event.getEntity() instanceof Silverfish
				|| event.getEntity() instanceof MagmaCube
				|| event.getEntity() instanceof Zombie
				|| event.getEntity() instanceof Creeper
				|| event.getEntity() instanceof Skeleton
				|| event.getEntity() instanceof Spider) {
				event.setCancelled(true);
			}
		}
		
		if(event.getEntity() instanceof Witch) {
			event.getEntity().remove();
		}

		if(event.getEntity() instanceof Squid) {
			event.getEntity().remove();
		}

		if(event.getEntity() instanceof Slime) {
			event.getEntity().remove();
		}

		if(event.getEntity() instanceof Sheep) {
			event.getEntity().remove();
			event.getEntity().getLocation().getWorld().spawnEntity(event.getLocation(), EntityType.COW);
		}
	}

	@EventHandler
    public void onHungerChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            player.setSaturation(1000.0f);
            player.setSaturation(10.0f);
        }
    }
}
