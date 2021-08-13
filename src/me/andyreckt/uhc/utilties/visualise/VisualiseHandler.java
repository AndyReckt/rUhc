package me.andyreckt.uhc.utilties.visualise;

import com.google.common.base.Predicate;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import me.andyreckt.uhc.utilties.cuboid.Cuboid;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;

public class VisualiseHandler {

	public static final Table<UUID, Location, VisualBlock> storedVisualises = HashBasedTable.create();

    public static Table<UUID, Location, VisualBlock> getStoredVisualises() {
        return storedVisualises;
    }
    
    @Deprecated
    public static VisualBlock getVisualBlockAt(Player player, int x, int y, int z) throws NullPointerException {
        return getVisualBlockAt(player, new Location(player.getWorld(), x, y, z));
    }

    public static VisualBlock getVisualBlockAt(Player player, Location location) throws NullPointerException {
        synchronized(storedVisualises) {
            return storedVisualises.get(player.getUniqueId(), location);
        }
    }

    public static Map<Location, VisualBlock> getVisualBlocks(Player player) {
        synchronized(storedVisualises) {
            return new HashMap<>(storedVisualises.row(player.getUniqueId()));
        }
    }

    public static Map<Location, VisualBlock> getVisualBlocks(Player player, VisualType visualType) {
        return Maps.filterValues(getVisualBlocks(player), visualBlock -> visualType == visualBlock.getVisualType());
    }

    public static LinkedHashMap<Location, VisualBlockData> generate(Player player, Cuboid cuboid, VisualType visualType, boolean canOverwrite) {
        Collection<Location> locations = new HashSet<>(cuboid.getSizeX() * cuboid.getSizeY() * cuboid.getSizeZ());
       
        Iterator<Location> iterator = cuboid.locationIterator();
        
        while(iterator.hasNext()) {
            locations.add(iterator.next());
        }

        return generate(player, locations, visualType, canOverwrite);
    }

    public static LinkedHashMap<Location, VisualBlockData> generate(Player player, Iterable<Location> locations, VisualType visualType, boolean canOverwrite) {
        synchronized(storedVisualises) {
            LinkedHashMap<Location, VisualBlockData> results = new LinkedHashMap<>();

            ArrayList<VisualBlockData> filled = visualType.blockFiller().bulkGenerate(player, locations);
            
            if(filled != null) {
                int count = 0;

                for(Location location : locations) {
                    if(!canOverwrite && storedVisualises.contains(player.getUniqueId(), location)) {
                        continue;
                    }

                    Material previousType = location.getBlock().getType();
                  
                    if(previousType.isSolid() || previousType != Material.AIR) {
                        continue;
                    }

                    VisualBlockData visualBlockData = filled.get(count++);
                    
                    results.put(location, visualBlockData);
                    
                    player.sendBlockChange(location, visualBlockData.getBlockType(), visualBlockData.getData());
                   
                    storedVisualises.put(player.getUniqueId(), location, new VisualBlock(visualType, visualBlockData, location));
                }
            }
            return results;
        }
    }

    public static boolean clearVisualBlock(Player player, Location location) {
        return clearVisualBlock(player, location, true);
    }

    public static boolean clearVisualBlock(Player player, Location location, boolean sendRemovalPacket) {
        synchronized(storedVisualises) {
            VisualBlock visualBlock = storedVisualises.remove(player.getUniqueId(), location);

            if(sendRemovalPacket && visualBlock != null) {
                Block block = location.getBlock();
                VisualBlockData visualBlockData = visualBlock.getBlockData();
                
                if(visualBlockData.getBlockType() != block.getType() || visualBlockData.getData() != block.getData()) {
                    player.sendBlockChange(location, block.getType(), block.getData());
                }

                return true;
            }
        }

        return false;
    }

    public static Map<Location, VisualBlock> clearVisualBlocks(Player player) {
        return clearVisualBlocks(player, null, null);
    }

    public static Map<Location, VisualBlock> clearVisualBlocks(Player player, VisualType visualType, Predicate<VisualBlock> predicate) {
        return clearVisualBlocks(player, visualType, predicate, true);
    }

    public static Map<Location, VisualBlock> clearVisualBlocks(Player player, VisualType visualType, Predicate<VisualBlock> predicate, boolean sendRemovalPackets) {
        synchronized(storedVisualises) {
            if(!storedVisualises.containsRow(player.getUniqueId())) return Collections.emptyMap();
            
            Map<Location, VisualBlock> results = new HashMap<>(storedVisualises.row(player.getUniqueId()));
            Map<Location, VisualBlock> removed = new HashMap<>();

            results.forEach((key, value) -> {
                if((predicate == null || predicate.apply(value)) && (visualType == null || value.getVisualType() == visualType)) {
                    if(removed.put(key, value) == null) {
                        clearVisualBlock(player, key, sendRemovalPackets);
                    }
                }
            });

            return removed;
        }
    }
}
