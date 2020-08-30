
package com.thesevenq.uhc.managers;

import com.thesevenq.uhc.utilties.cuboid.Cuboid;
import com.thesevenq.uhc.utilties.visualise.VisualType;
import com.thesevenq.uhc.utilties.visualise.VisualiseHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.utilties.Manager;

import java.util.Arrays;
import java.util.Iterator;

public class GlassManager extends Manager {

    private int border_height_below_diff = 3;
    private int border_height_above_diff = 4;
    private int border_horizontal_distance = 5;

    public GlassManager(UHC plugin) {
		super(plugin);
    }

    public void setup() {
        new BukkitRunnable() {
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    Location location = player.getLocation();

                    if(Arrays.asList(100, 50, 25, 5).contains(BorderManager.border)) {
                        handleMove(player, player.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
                    }
                }
            }
        }.runTaskTimerAsynchronously(plugin, 2L, 2L);
    }

    private void handleMove(Player player, World toWorld, int toX, int toY, int toZ) {
        VisualType visualType = VisualType.RED;

        VisualiseHandler.clearVisualBlocks(player, visualType, visualBlock -> {
            assert visualBlock != null;
            Location other = visualBlock.getLocation();

            return other.getWorld().equals(toWorld) && (Math.abs(toX - other.getX()) > border_horizontal_distance || Math.abs(toY - other.getY()) > border_height_above_diff || Math.abs(toZ - other.getZ()) > border_horizontal_distance);
        });

        int minHeight = toY - border_height_below_diff;
        int maxHeight = toY + border_height_above_diff;
        
        Location loc = new Location(toWorld, BorderManager.border, 0, -BorderManager.border);
        Iterator<Vector> iterator = new Cuboid(loc, new Location(toWorld, -BorderManager.border, 0, BorderManager.border)).edges().iterator();
        
        while(iterator.hasNext()) {
			Vector vector = iterator.next();
			
			if(Math.abs(vector.getBlockX() - toX) > border_horizontal_distance) continue;
            if(Math.abs(vector.getBlockZ() - toZ) > border_horizontal_distance) continue;
			
			Location location = vector.toLocation(toWorld);
			
			if(location != null) {
				Location first = location.clone();

				first.setY(minHeight);

				Location second = location.clone();

				second.setY(maxHeight);
                
				VisualiseHandler.generate(player, new Cuboid(first, second), visualType, false).size();
			}
			
			iterator.remove();
        }
	}
}
