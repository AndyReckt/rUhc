package me.andyreckt.uhc.scenario.type;

import me.andyreckt.uhc.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class VanillaPlusScenario extends Scenario implements Listener {

	private static Random random = new Random();

	public VanillaPlusScenario() {
		super("Vanilla+", Material.FLINT, "Flint and Apple rates are up.");
	}

	public static void handleBreak(Block block) {
		if(block.getType() == Material.GRAVEL) {
			if(random.nextInt(10) == 0) {
				block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.FLINT));
			}
		} else if(block.getType() == Material.LEAVES && block.getData() % 4 == 0) {
			if(random.nextInt(200) == 0) {
				block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.APPLE));
			}
		} else if (block.getType() == Material.LEAVES_2 && block.getData() % 4 == 1) {
			if (random.nextInt(200) == 0) {
				block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.APPLE));
			}
		}
	}

	public static void handleDecay(Block block) {
		if(block.getType() == Material.LEAVES && block.getData() % 4 == 0) {
			if(random.nextInt(200) == 0) {
				block.getDrops().add(new ItemStack(Material.APPLE));
			}
		} else if(block.getType() == Material.LEAVES_2 && block.getData() % 4 == 1) {
			if(random.nextInt(200) == 0) {
				block.getDrops().add(new ItemStack(Material.APPLE));
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		
		if(block.getType() == Material.GRAVEL) {
			if(this.random.nextInt(10) == 0) {
				block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.FLINT));
			}
		} else if(block.getType() == Material.LEAVES && block.getData() % 4 == 0) {
			if(this.random.nextInt(200) == 0) {
				block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.APPLE));
			}
		} else if (block.getType() == Material.LEAVES_2 && block.getData() % 4 == 1) {
			if (this.random.nextInt(200) == 0) {
				block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.APPLE));
			}
		}

	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onLeavesDecay(LeavesDecayEvent event) {
		Block block = event.getBlock();
		
		if(block.getType() == Material.LEAVES && block.getData() % 4 == 0) {
			if(this.random.nextInt(200) == 0) {
				block.getDrops().add(new ItemStack(Material.APPLE));
			}
		} else if(block.getType() == Material.LEAVES_2 && block.getData() % 4 == 1) {
			if(this.random.nextInt(200) == 0) {
				block.getDrops().add(new ItemStack(Material.APPLE));
			}
		}

	}

}
