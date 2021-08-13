package me.andyreckt.uhc.border;

import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.managers.BorderManager;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Border extends BukkitRunnable {
	
	private World world;
	private int toPaste;
	private boolean canStart;
	private List<int[]> locations;
	private List<Material> blockedWallBlocks;

	public Border(World world, int border) {
		this.world = world;
		this.toPaste = 0;
		this.canStart = false;
		this.locations = new ArrayList<>();

		this.blockedWallBlocks = Arrays.asList(Material.LOG, Material.LOG_2, Material.LEAVES, Material.LEAVES_2,
				Material.AIR, Material.WATER, Material.STATIONARY_WATER, Material.LAVA, Material.STATIONARY_LAVA,
				Material.HUGE_MUSHROOM_1, Material.HUGE_MUSHROOM_2, Material.DOUBLE_PLANT, Material.LONG_GRASS,
				Material.VINE, Material.YELLOW_FLOWER, Material.RED_ROSE, Material.CACTUS, Material.DEAD_BUSH,
				Material.SUGAR_CANE_BLOCK, Material.ICE, Material.SNOW);

		this.calculateWalls(border);
		BorderManager.border = border;
		
		this.runTaskTimer(UHC.getInstance(), 0L, 1L);
	}

	private void calculateWall(int a, int b) {
		Block block = world.getHighestBlockAt(a, b);
		int y = block.getY();

		while(this.blockedWallBlocks.contains(block.getRelative(BlockFace.DOWN).getType())) {
			block = block.getRelative(BlockFace.DOWN);
			y--;
		}

		for(int j = y; j < y + 4; j++) {
			this.locations.add(new int[] { a, j, b });
		}
	}

	private void calculateWalls(int border) {
		for(int i = -border; i < border + 1; i++) this.calculateWall(i, border);

		for(int i = -border + 1; i < border; i++) this.calculateWall(border, i);

		for(int i = -border; i < border + 1; i++) this.calculateWall(i, -border);

		for(int i = -border + 1; i < border; i++) this.calculateWall(-border, i);

		this.toPaste = this.locations.size() / 20;
		this.canStart = true;
	}

	@Override
	public void run() {
		if(!this.canStart) return;

		if(this.locations.isEmpty()) {
			this.cancel();
			return;
		}

		int toPaste = (this.locations.size() < this.toPaste) ? this.locations.size() : this.toPaste;

		for(int i = 0; i < toPaste; i++) {
			int[] coords = this.locations.get(i);
			this.world.getBlockAt(coords[0], coords[1], coords[2]).setType(Material.BEDROCK);
		}

		this.locations.subList(0, toPaste).clear();
	}
}
