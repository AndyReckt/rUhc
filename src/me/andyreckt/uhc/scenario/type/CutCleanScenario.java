package me.andyreckt.uhc.scenario.type;

import me.andyreckt.uhc.managers.ScenarioManager;
import me.andyreckt.uhc.scenario.Scenario;
import me.andyreckt.uhc.utilties.UHCUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class CutCleanScenario extends Scenario implements Listener {

	public static Random random = new Random();

	public CutCleanScenario() {
		super("Cut Clean", Material.IRON_INGOT, "Ores are pre-smelted", "Food is pre-cooked", "Flint/Leather/Feathers drop rates are 100%.");
	}

	public static void handleBreak(Player player, Block block, BlockBreakEvent event) {
		if(UHCUtils.isPlayerInSpecMode(player) || player.getGameMode() == GameMode.CREATIVE || player.getItemInHand() == null) return;

		World world = block.getLocation().getWorld();

		if(world.getName().equals("uhc_world") || world.getEnvironment() == World.Environment.NETHER) {
			/*switch(block.getType()) {
				case IRON_ORE:
				case GOLD_ORE:
				case GRAVEL:
					block.getDrops().clear();
					block.getDrops().add(ScenarioUtils.smelt(block, 1));
					event.setCancelled(true);

					block.setType(Material.AIR);
					block.getState().update();

					block.getWorld().dropItem(block.getLocation(), ScenarioUtils.smelt(block, 1));
					break;
				case LEAVES:
				case LEAVES_2:
					if(block.getData() % 4 == 0) {
						if(random.nextInt(200) == 0) {
							block.getDrops().add(new ItemStack(Material.APPLE));
						}
					}
					break;
				default:
					break;
			}*/

			switch(block.getType()) {
				case IRON_ORE:
					if(ScenarioManager.getByName("Triple Ores").isEnabled() || ScenarioManager.getByName("Double Ores").isEnabled() ) {
						block.getDrops().clear();
					} else {
						event.setCancelled(true);

						block.setType(Material.AIR);
						block.getState().update();

						block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.IRON_INGOT));

						if(!ScenarioManager.getByName("Triple Exp").isEnabled() || !ScenarioManager.getByName("Double Exp").isEnabled()) {
							block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(3);
						}
					}
					break;
				case GOLD_ORE:
					if(ScenarioManager.getByName("Triple Ores").isEnabled() || ScenarioManager.getByName("Double Ores").isEnabled()) {
						block.getDrops().clear();
					} else {
						event.setCancelled(true);

						block.setType(Material.AIR);
						block.getState().update();

						block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.GOLD_INGOT));

						if(!ScenarioManager.getByName("Triple Exp").isEnabled() || !ScenarioManager.getByName("Double Exp").isEnabled()) {
							block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(3);
						}
					}
					break;
				case GRAVEL:
					if(ScenarioManager.getByName("Triple Ores").isEnabled() || ScenarioManager.getByName("Double Ores").isEnabled()) {
						block.getDrops().clear();
					} else {
						event.setCancelled(true);

						block.setType(Material.AIR);
						block.getState().update();
						block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.FLINT));
					}
					break;
				default:
					break;
			}

			if(block.getType() == Material.LEAVES && block.getData() % 4 == 0) {
				if(random.nextInt(200) == 0) {
					block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.APPLE));
				}
			} else if(block.getType() == Material.LEAVES_2 && block.getData() % 4 == 1) {
				if(random.nextInt(200) == 0) {
					block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.APPLE));
				}
			}
		}
	}

	public static void handleEntityDeath(Entity entity, List<ItemStack> drops) {
		if(!ScenarioManager.getByName("Triple Ores").isEnabled() || !ScenarioManager.getByName("Double Ores").isEnabled()) {
			if(entity instanceof Cow) {
				drops.clear();
				drops.add(new ItemStack(Material.COOKED_BEEF, 3));
				drops.add(new ItemStack(Material.LEATHER));
			} else if(entity instanceof Chicken) {
				drops.clear();
				drops.add(new ItemStack(Material.COOKED_CHICKEN, 3));
				drops.add(new ItemStack(Material.FEATHER));
			} else if(entity instanceof Pig) {
				drops.clear();
				drops.add(new ItemStack(Material.GRILLED_PORK, 3));
			} else if(entity instanceof Horse) {
				drops.clear();
				drops.add(new ItemStack(Material.LEATHER));
			}
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if(event.isCancelled()) return;

		Player player = event.getPlayer();

		if(UHCUtils.isPlayerInSpecMode(player) || player.getGameMode() == GameMode.CREATIVE || player.getItemInHand() == null) return;

		Block block = event.getBlock();
		World world = block.getLocation().getWorld();

		if(world.getName().equals("uhc_world") || world.getEnvironment() == World.Environment.NETHER) {
			/*switch(block.getType()) {
				case IRON_ORE:
				case GOLD_ORE:
				case GRAVEL:
					block.getDrops().clear();
					block.getDrops().add(ScenarioUtils.smelt(block, 1));
					event.setCancelled(true);

					block.setType(Material.AIR);
					block.getState().update();

					block.getWorld().dropItem(block.getLocation(), ScenarioUtils.smelt(block, 1));
					break;
				case LEAVES:
				case LEAVES_2:
					if(block.getData() % 4 == 0) {
						if(random.nextInt(200) == 0) {
							block.getDrops().add(new ItemStack(Material.APPLE));
						}
					}
					break;
				default:
					break;
			}*/

			switch(block.getType()) {
			case IRON_ORE:
				if(ScenarioManager.getByName("Triple Ores").isEnabled() || ScenarioManager.getByName("Double Ores").isEnabled() ) {
					block.getDrops().clear();
				} else {
					event.setCancelled(true);

					block.setType(Material.AIR);
					block.getState().update();

					block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.IRON_INGOT));
					
					if(!ScenarioManager.getByName("Triple Exp").isEnabled()) {
						block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(3);
					}

					if(!ScenarioManager.getByName("Double Exp").isEnabled()) {
						block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(3);
					}
				}
				break;
			case GOLD_ORE:
				if(ScenarioManager.getByName("Triple Ores").isEnabled() || ScenarioManager.getByName("Double Ores").isEnabled()) {
					block.getDrops().clear();
				} else {
					event.setCancelled(true);

					block.setType(Material.AIR);
					block.getState().update();

					block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.GOLD_INGOT));

					if(!ScenarioManager.getByName("Triple Exp").isEnabled()) {
						block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(3);
					}

					if(!ScenarioManager.getByName("Double Exp").isEnabled()) {
						block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(3);
					}
				}
				break;
			case GRAVEL:
				if(ScenarioManager.getByName("Triple Ores").isEnabled() || ScenarioManager.getByName("Double Ores").isEnabled()) {
					block.getDrops().clear();
				} else {
					event.setCancelled(true);

					block.setType(Material.AIR);
					block.getState().update();
					block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.FLINT));
				}
				break;
			default:
				break;
			}

			if(block.getType() == Material.LEAVES && block.getData() % 4 == 0) {
				if(random.nextInt(200) == 0) {
					block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.APPLE));
				}
			} else if(block.getType() == Material.LEAVES_2 && block.getData() % 4 == 1) {
				if(random.nextInt(200) == 0) {
					block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.APPLE));
				}
			}
		}
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if(!ScenarioManager.getByName("Triple Ores").isEnabled() || !ScenarioManager.getByName("Double Ores").isEnabled()) {
			if(event.getEntity() instanceof Cow) {
				event.getDrops().clear();

				event.getDrops().add(new ItemStack(Material.COOKED_BEEF, 3));
				event.getDrops().add(new ItemStack(Material.LEATHER));
			} else if(event.getEntity() instanceof Chicken) {
				event.getDrops().clear();

				event.getDrops().add(new ItemStack(Material.COOKED_CHICKEN, 3));
				event.getDrops().add(new ItemStack(Material.FEATHER));
			} else if(event.getEntity() instanceof Pig) {
				event.getDrops().clear();

				event.getDrops().add(new ItemStack(Material.GRILLED_PORK, 3));
			} else if(event.getEntity() instanceof Horse) {
				event.getDrops().clear();

				event.getDrops().add(new ItemStack(Material.LEATHER));
			}
		}
	}
}
