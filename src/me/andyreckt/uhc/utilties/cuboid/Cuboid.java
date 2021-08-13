package me.andyreckt.uhc.utilties.cuboid;

import com.google.common.base.Preconditions;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;


public class Cuboid implements Iterable<Block>, Cloneable, ConfigurationSerializable {
    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getZ1() {
        return z1;
    }

    public void setZ1(int z1) {
        this.z1 = z1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public int getZ2() {
        return z2;
    }

    public void setZ2(int z2) {
        this.z2 = z2;
    }

    private String worldName;
    private int x1;
    private int y1;
    private int z1;
    private int x2;
    private int y2;
    private int z2;

    public Cuboid() {}

    public Cuboid(Map<String, Object> map) {
        this.worldName = (String) map.get("worldName");

        this.x1 = (int) map.get("x1");
        this.y1 = (int) map.get("y1");
        this.z1 = (int) map.get("z1");
        this.x2 = (int) map.get("x2");
        this.y2 = (int) map.get("y2");
        this.z2 = (int) map.get("z2");
    }

    public Cuboid(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
        this(Preconditions.checkNotNull(world).getName(), x1, y1, z1, x2, y2, z2);
    }

    private Cuboid(String worldName, int x1, int y1, int z1, int x2, int y2, int z2) {
        this.worldName = worldName;

        this.x1 = Math.min(x1, x2);
        this.y1 = Math.min(y1, y2);
        this.z1 = Math.min(z1, z2);
        this.x2 = Math.max(x1, x2);
        this.y2 = Math.max(y1, y2);
        this.z2 = Math.max(z1, z2);
    }

    public Cuboid(Location first, Location second) {
        this.worldName = first.getWorld().getName();

        this.x1 = Math.min(first.getBlockX(), second.getBlockX());
        this.y1 = Math.min(first.getBlockY(), second.getBlockY());
        this.z1 = Math.min(first.getBlockZ(), second.getBlockZ());
        this.x2 = Math.max(first.getBlockX(), second.getBlockX());
        this.y2 = Math.max(first.getBlockY(), second.getBlockY());
        this.z2 = Math.max(first.getBlockZ(), second.getBlockZ());
    }

    public Cuboid(Location location) {
        this(location, location);
    }

    public Cuboid(Cuboid other) {
        this(other.getWorld().getName(), other.x1, other.y1, other.z1, other.x2, other.y2, other.z2);
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        
        map.put("worldName", this.worldName);
        map.put("x1", this.x1);
        map.put("y1", this.y1);
        map.put("z1", this.z1);
        map.put("x2", this.x2);
        map.put("y2", this.y2);
        map.put("z2", this.z2);
        return map;
    }

    public boolean hasBothPositionsSet() {
        return this.getMinimumPoint() != null && this.getMaximumPoint() != null;
    }

    public int getMinimumX() {
        return Math.min(this.x1, this.x2);
    }

    public int getMinimumZ() {
        return Math.min(this.z1, this.z2);
    }

    public int getMaximumX() {
        return Math.max(this.x1, this.x2);
    }

    public int getMaximumZ() {
        return Math.max(this.z1, this.z2);
    }

    public List<Vector> edges() {
        return this.edges(-1, -1, -1, -1);
    }

    public List<Vector> edges(int fixedMinX, int fixedMaxX, int fixedMinZ, int fixedMaxZ) {
        Vector v1 = this.getMinimumPoint().toVector();
        Vector v2 = this.getMaximumPoint().toVector();
        
        int minX = v1.getBlockX();
        int maxX = v2.getBlockX();
        int minZ = v1.getBlockZ();
        int maxZ = v2.getBlockZ();
        
        int capacity = (maxX - minX) * 4 + (maxZ - minZ) * 4;
        
        capacity += 4;
        
        List<Vector> result = new ArrayList<Vector>(capacity);
        if(capacity <= 0) {
            return result;
        }
        
        int minY = v1.getBlockY();
        int maxY = v1.getBlockY();
        
        for(int x = minX; x <= maxX; ++x) {
            result.add(new Vector(x, minY, minZ));
            result.add(new Vector(x, minY, maxZ));
            result.add(new Vector(x, maxY, minZ));
            result.add(new Vector(x, maxY, maxZ));
        }
        
        for(int z = minZ; z <= maxZ; ++z) {
            result.add(new Vector(minX, minY, z));
            result.add(new Vector(minX, maxY, z));
            result.add(new Vector(maxX, minY, z));
            result.add(new Vector(maxX, maxY, z));
        }
        
        return result;
    }

    public Set<Player> getPlayers() {
        Set<Player> players = new HashSet<Player>();
        
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(this.contains(player)) {
                players.add(player);
            }
        }
        
        return players;
    }

    public Location getLowerNE() {
        return new Location(this.getWorld(), (double) this.x1, (double) this.y1, (double) this.z1);
    }

    public Location getUpperSW() {
        return new Location(this.getWorld(), (double) this.x2, (double) this.y2, (double) this.z2);
    }

    public Location getCenter() {
        int x1 = this.x2 + 1;
        int y1 = this.y2 + 1;
        int z1 = this.z2 + 1;
        return new Location(this.getWorld(), this.x1 + (x1 - this.x1) / 2.0, this.y1 + (y1 - this.y1) / 2.0, this.z1 + (z1 - this.z1) / 2.0);
    }

    public World getWorld() {
        return Bukkit.getWorld(this.worldName);
    }

    public int getSizeX() {
        return this.x2 - this.x1 + 1;
    }

    public int getSizeY() {
        return this.y2 - this.y1 + 1;
    }

    public int getSizeZ() {
        return this.z2 - this.z1 + 1;
    }

    public Location[] getCornerLocations() {
        Location[] result = new Location[8];
        Block[] cornerBlocks = this.getCornerBlocks();
        
        for(int i = 0; i < cornerBlocks.length; ++i) {
            result[i] = cornerBlocks[i].getLocation();
        }
        
        return result;
    }

    public Block[] getCornerBlocks() {
        Block[] result = new Block[8];
        World world = this.getWorld();
        
        result[0] = world.getBlockAt(this.x1, this.y1, this.z1);
        result[1] = world.getBlockAt(this.x1, this.y1, this.z2);
        result[2] = world.getBlockAt(this.x1, this.y2, this.z1);
        result[3] = world.getBlockAt(this.x1, this.y2, this.z2);
        result[4] = world.getBlockAt(this.x2, this.y1, this.z1);
        result[5] = world.getBlockAt(this.x2, this.y1, this.z2);
        result[6] = world.getBlockAt(this.x2, this.y2, this.z1);
        result[7] = world.getBlockAt(this.x2, this.y2, this.z2);
        
        return result;
    }

    public Cuboid shift(CuboidDirection direction, int amount) throws IllegalArgumentException {
        return this.expand(direction, amount).expand(direction.opposite(), -amount);
    }

    public Cuboid inset(CuboidDirection direction, int amount) throws IllegalArgumentException {
        return this.outset(direction, -amount);
    }

    public Cuboid expand(CuboidDirection direction, int amount) throws IllegalArgumentException {
        switch(direction) {
        case NORTH: {
            return new Cuboid(this.worldName, this.x1 - amount, this.y1, this.z1, this.x2, this.y2, this.z2);
        }
        case SOUTH: {
            return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2 + amount, this.y2, this.z2);
        }
        case EAST: {
            return new Cuboid(this.worldName, this.x1, this.y1, this.z1 - amount, this.x2, this.y2, this.z2);
        }
        case WEST: {
            return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2, this.z2 + amount);
        }
        case DOWN: {
            return new Cuboid(this.worldName, this.x1, this.y1 - amount, this.z1, this.x2, this.y2, this.z2);
        }
        case UP: {
            return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2 + amount, this.z2);
        }
        default: {
            throw new IllegalArgumentException("Invalid direction " + direction);
        }
        }
    }

    public Cuboid outset(CuboidDirection direction, int amount) throws IllegalArgumentException {
        switch(direction) {
        case HORIZONTAL: {
            return this.expand(CuboidDirection.NORTH, amount).expand(CuboidDirection.SOUTH, amount).expand(CuboidDirection.EAST, amount).expand(CuboidDirection.WEST, amount);
        }
        case VERTICAL: {
            return this.expand(CuboidDirection.DOWN, amount).expand(CuboidDirection.UP, amount);
        }
        case BOTH: {
            return this.outset(CuboidDirection.HORIZONTAL, amount).outset(CuboidDirection.VERTICAL, amount);
        }
        default: {
            throw new IllegalArgumentException("Invalid direction " + direction);
        }
        }
    }

    public boolean contains(Cuboid cuboid) {
        return this.contains(cuboid.getMinimumPoint()) || this.contains(cuboid.getMaximumPoint());
    }

    public boolean contains(Player player) {
        return this.contains(player.getLocation());
    }

    public boolean contains(World world, int x, int z) {
        return (world == null || this.getWorld().equals(world)) && x >= this.x1 && x <= this.x2 && z >= this.z1 && z <= this.z2;
    }

    public boolean contains(int x, int y, int z) {
        return x >= this.x1 && x <= this.x2 && y >= this.y1 && y <= this.y2 && z >= this.z1 && z <= this.z2;
    }

    public boolean contains(Block block) {
        return this.contains(block.getLocation());
    }

    public boolean contains(Location location) {
        if(location == null || this.worldName == null) return false;
        
        World world = location.getWorld();
        
        return world != null && this.worldName.equals(location.getWorld().getName()) && this.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public int getVolume() {
        return this.getSizeX() * this.getSizeY() * this.getSizeZ();
    }

    public int getArea() {
        Location min = this.getMinimumPoint();
        Location max = this.getMaximumPoint();
        
        return (max.getBlockX() - min.getBlockX() + 1) * (max.getBlockZ() - min.getBlockZ() + 1);
    }

    public byte getAverageLightLevel() {
        long total = 0L;
        int count = 0;
        
        for(Block block : this) {
            if(block.isEmpty()) {
                total += block.getLightLevel();
                
                ++count;
            }
        }
        
        return (byte) ((count > 0) ? ((byte) (total / count)) : 0);
    }

    public Location getMinimumPoint() {
        return new Location(this.getWorld(), (double) Math.min(this.x1, this.x2), (double) Math.min(this.y1, this.y2), (double) Math.min(this.z1, this.z2));
    }

    public Location getMaximumPoint() {
        return new Location(this.getWorld(), (double) Math.max(this.x1, this.x2), (double) Math.max(this.y1, this.y2), (double) Math.max(this.z1, this.z2));
    }

    public int getWidth() {
        return this.getMaximumPoint().getBlockX() - this.getMinimumPoint().getBlockX();
    }

    public int getHeight() {
        return this.getMaximumPoint().getBlockY() - this.getMinimumPoint().getBlockY();
    }

    public int getLength() {
        return this.getMaximumPoint().getBlockZ() - this.getMinimumPoint().getBlockZ();
    }

    public Cuboid contract() {
        return this.contract(CuboidDirection.DOWN).contract(CuboidDirection.SOUTH).contract(CuboidDirection.EAST).contract(CuboidDirection.UP).contract(CuboidDirection.NORTH).contract(CuboidDirection.WEST);
    }

    public Cuboid contract(CuboidDirection direction) {
        Cuboid face = this.getFace(direction.opposite());
        
        switch(direction) {
        case DOWN: {
            while(face.containsOnly(Material.AIR) && face.y1 > this.y1) {
                face = face.shift(CuboidDirection.DOWN, 1);
            }
            
            return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, face.y2, this.z2);
        }
        case UP: {
            while(face.containsOnly(Material.AIR) && face.y2 < this.y2) {
                face = face.shift(CuboidDirection.UP, 1);
            }
            
            return new Cuboid(this.worldName, this.x1, face.y1, this.z1, this.x2, this.y2, this.z2);
        }
        case NORTH: {
            while(face.containsOnly(Material.AIR) && face.x1 > this.x1) {
                face = face.shift(CuboidDirection.NORTH, 1);
            }
            
            return new Cuboid(this.worldName, this.x1, this.y1, this.z1, face.x2, this.y2, this.z2);
        }
        case SOUTH: {
            while (face.containsOnly(Material.AIR) && face.x2 < this.x2) {
                face = face.shift(CuboidDirection.SOUTH, 1);
            }
            return new Cuboid(this.worldName, face.x1, this.y1, this.z1, this.x2, this.y2, this.z2);
        }
        case EAST: {
            while (face.containsOnly(Material.AIR) && face.z1 > this.z1) {
                face = face.shift(CuboidDirection.EAST, 1);
            }
            return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2, face.z2);
        }
        case WEST: {
            while (face.containsOnly(Material.AIR) && face.z2 < this.z2) {
                face = face.shift(CuboidDirection.WEST, 1);
            }
            return new Cuboid(this.worldName, this.x1, this.y1, face.z1, this.x2, this.y2, this.z2);
        }
        default: {
            throw new IllegalArgumentException("Invalid direction " + direction);
        }
        }
    }

    public Cuboid getFace(CuboidDirection direction) {
        switch (direction) {
        case DOWN: {
            return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y1, this.z2);
        }
        case UP: {
            return new Cuboid(this.worldName, this.x1, this.y2, this.z1, this.x2, this.y2, this.z2);
        }
        case NORTH: {
            return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x1, this.y2, this.z2);
        }
        case SOUTH: {
            return new Cuboid(this.worldName, this.x2, this.y1, this.z1, this.x2, this.y2, this.z2);
        }
        case EAST: {
            return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2, this.z1);
        }
        case WEST: {
            return new Cuboid(this.worldName, this.x1, this.y1, this.z2, this.x2, this.y2, this.z2);
        }
        default: {
            throw new IllegalArgumentException("Invalid direction " + direction);
        }
        }
    }

    public boolean containsOnly(Material material) {
        for(Block block : this) {
            if(block.getType() != material) {
                return false;
            }
        }
        
        return true;
    }

    public Cuboid getBoundingCuboid(Cuboid other) {
        if(other == null) return this;
        
        int xMin = Math.min(this.x1, other.x1);
        int yMin = Math.min(this.y1, other.y1);
        int zMin = Math.min(this.z1, other.z1);
        int xMax = Math.max(this.x2, other.x2);
        int yMax = Math.max(this.y2, other.y2);
        int zMax = Math.max(this.z2, other.z2);
        
        return new Cuboid(this.worldName, xMin, yMin, zMin, xMax, yMax, zMax);
    }

    public Block getRelativeBlock(int x, int y, int z) {
        return this.getWorld().getBlockAt(this.x1 + x, this.y1 + y, this.z1 + z);
    }

    public Block getRelativeBlock(World world, int x, int y, int z) {
        return world.getBlockAt(this.x1 + x, this.y1 + y, this.z1 + z);
    }

    public List<Chunk> getChunks() {
        World world = this.getWorld();
        
        int x1 = this.x1 & 0xFFFFFFF0;
        int x2 = this.x2 & 0xFFFFFFF0;
        int z1 = this.z1 & 0xFFFFFFF0;
        int z2 = this.z2 & 0xFFFFFFF0;
        
        List<Chunk> result = new ArrayList<Chunk>(x2 - x1 + 16 + (z2 - z1) * 16);
        for(int x3 = x1; x3 <= x2; x3 += 16) {
            for(int z3 = z1; z3 <= z2; z3 += 16) {
                result.add(world.getChunkAt(x3 >> 4, z3 >> 4));
            }
        }
        
        return result;
    }

    @Override
    public Iterator<Block> iterator() {
        return new CuboidBlockIterator(this.getWorld(), this.x1, this.y1, this.z1, this.x2, this.y2, this.z2);
    }

    public Iterator<Location> locationIterator() {
        return new CuboidLocationIterator(this.getWorld(), this.x1, this.y1, this.z1, this.x2, this.y2, this.z2);
    }

    public Cuboid clone() {
        try {
            return (Cuboid) super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("This could never happen", ex);
        }
    }

    @Override
    public String toString() {
        return "Cuboid: " + this.worldName + ',' + this.x1 + ',' + this.y1 + ',' + this.z1 + "=>" + this.x2 + ',' + this.y2 + ',' + this.z2;
    }
}
