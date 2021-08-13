package me.andyreckt.uhc.utilties.cuboid;


import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;


public class CoordinatePair {
	public String getWorldName() {
		return worldName;
	}

	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}

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
