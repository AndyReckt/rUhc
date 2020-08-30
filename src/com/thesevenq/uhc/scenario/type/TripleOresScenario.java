package com.thesevenq.uhc.scenario.type;

import com.thesevenq.uhc.managers.ScenarioManager;
import com.thesevenq.uhc.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import com.thesevenq.uhc.managers.ScenarioManager;
import com.thesevenq.uhc.scenario.Scenario;
import com.thesevenq.uhc.utilties.UHCUtils;

import java.util.List;

public class TripleOresScenario extends Scenario implements Listener {

	public TripleOresScenario() {
		super("Triple Ores", Material.DIAMOND, "Food and ores are tripled when mined / harvested.", "All Triple Ores games are Cut Clean.");
	}

	public static void handleBreak(Player player, Block block, BlockBreakEvent event) {
		if(UHCUtils.isPlayerInSpecMode(player)) {
			return;
		}

		switch(block.getType()) {
			case DIAMOND_ORE:
				event.setCancelled(true);

				block.setType(Material.AIR);
				block.getState().update();
				block.getDrops().clear();

				if(!ScenarioManager.getByName("Diamondless").isEnabled()) {
					block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.DIAMOND, 3));
				}

				if(!ScenarioManager.getByName("Triple Exp").isEnabled()) {
					block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(8);
				}

				if(!ScenarioManager.getByName("Double Exp").isEnabled()) {
					block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(8);
				}

				break;
			case GOLD_ORE:
				event.setCancelled(true);

				block.setType(Material.AIR);
				block.getState().update();
				block.getDrops().clear();

				if(!ScenarioManager.getByName("Goldless").isEnabled()) {
					block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.GOLD_INGOT, 3));
				}

				if(!ScenarioManager.getByName("Triple Exp").isEnabled()) {
					block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(8);
				}

				if(!ScenarioManager.getByName("Double Exp").isEnabled()) {
					block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(8);
				}

				break;
			case IRON_ORE:
				event.setCancelled(true);

				block.setType(Material.AIR);
				block.getState().update();
				block.getDrops().clear();

				block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.IRON_INGOT, 3));

				if(!ScenarioManager.getByName("Triple Exp").isEnabled()) {
					block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(8);
				}

				if(!ScenarioManager.getByName("Double Exp").isEnabled()) {
					block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(8);
				}
				break;
			case GRAVEL:
				event.setCancelled(true);

				block.setType(Material.AIR);
				block.getState().update();

				block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.FLINT, 3));
				break;
			default:
				break;

		}
	}

	public static void handleEntityDeath(List<ItemStack> drops, Entity entity) {
		if(entity instanceof Cow) {
			drops.clear();
			drops.add(new ItemStack(Material.COOKED_BEEF, 6));
			drops.add(new ItemStack(Material.LEATHER, 3));
		} else if(entity instanceof Chicken) {
			drops.clear();
			drops.add(new ItemStack(Material.COOKED_CHICKEN, 6));
			drops.add(new ItemStack(Material.FEATHER, 3));
		} else if(entity instanceof Pig) {
			drops.clear();
			drops.add(new ItemStack(Material.GRILLED_PORK, 6));
		} else if(entity instanceof Horse) {
			drops.clear();
			drops.add(new ItemStack(Material.LEATHER, 3));
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if(event.isCancelled()) return;

		Player player = event.getPlayer();

		if(UHCUtils.isPlayerInSpecMode(player)) return;

		Block block = event.getBlock();

		switch(block.getType()) {
		case DIAMOND_ORE:
			event.setCancelled(true);

			block.setType(Material.AIR);
			block.getState().update();
			block.getDrops().clear();

			if(!ScenarioManager.getByName("Diamondless").isEnabled()) {
				block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.DIAMOND, 3));
			}

			if(!ScenarioManager.getByName("Triple Exp").isEnabled()) {
				block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(8);
			}

			if(!ScenarioManager.getByName("Double Exp").isEnabled()) {
				block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(8);
			}

			break;
		case GOLD_ORE:
			event.setCancelled(true);

			block.setType(Material.AIR);
			block.getState().update();
			block.getDrops().clear();

			if(!ScenarioManager.getByName("Goldless").isEnabled()) {
				block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.GOLD_INGOT, 3));
			}

			if(!ScenarioManager.getByName("Triple Exp").isEnabled()) {
				block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(8);
			}

			if(!ScenarioManager.getByName("Double Exp").isEnabled()) {
				block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(8);
			}

			break;
		case IRON_ORE:
			event.setCancelled(true);

			block.setType(Material.AIR);
			block.getState().update();
			block.getDrops().clear();

			block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.IRON_INGOT, 3));

			if(!ScenarioManager.getByName("Triple Exp").isEnabled()) {
				block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(8);
			}

			if(!ScenarioManager.getByName("Double Exp").isEnabled()) {
				block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(8);
			}
			break;
		case GRAVEL:
			event.setCancelled(true);

			block.setType(Material.AIR);
			block.getState().update();

			block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.FLINT, 3));
			break;
		default:
			break;

		}
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if(event.getEntity() instanceof Cow) {
			event.getDrops().clear();

			event.getDrops().add(new ItemStack(Material.COOKED_BEEF, 6));
			event.getDrops().add(new ItemStack(Material.LEATHER, 3));
		} else if(event.getEntity() instanceof Chicken) {
			event.getDrops().clear();

			event.getDrops().add(new ItemStack(Material.COOKED_CHICKEN, 6));
			event.getDrops().add(new ItemStack(Material.FEATHER, 3));
		} else if(event.getEntity() instanceof Pig) {
			event.getDrops().clear();

			event.getDrops().add(new ItemStack(Material.GRILLED_PORK, 6));
		} else if(event.getEntity() instanceof Horse) {
			event.getDrops().clear();

			event.getDrops().add(new ItemStack(Material.LEATHER, 3));
		}
	}

}
