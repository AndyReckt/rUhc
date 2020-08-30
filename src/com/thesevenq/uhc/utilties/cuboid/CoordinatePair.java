package com.thesevenq.uhc.utilties.cuboid;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;

@Getter
public class CoordinatePair {

	private String worldName;
	private int x;
	private int z;

	public CoordinatePair(Block block) {
		this(block.getWorld(), block.getX(), block.getZ());
	}

	public CoordinatePair(String worldName, int x, int z) {
		this.worldName = worldName;
		this.x = x;
		this.z = z;
	}
	
	public CoordinatePair(World world, int x, int z) {
		this.worldName = world.getName();
		this.x = x;
		this.z = z;
	}

	public World getWorld() {
		return Bukkit.getWorld(this.worldName);
	}
}
