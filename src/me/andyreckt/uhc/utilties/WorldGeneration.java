package me.andyreckt.uhc.utilties;

import me.andyreckt.uhc.UHC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.UUID;

public class WorldGeneration {

    public static boolean finished;

    private static Double percentage = 0D;

    private Double currentChunkLoad;

    private final Double totalChunkToLoad;

    private Integer cx;

    private Integer cz;

    private final Integer radius;

    private final World world;
    private final HashMap<UUID, Long> messageLimiter = new HashMap<>();
    private static boolean uhcLoaded = false;
    private static boolean netherLoaded = false;



    public WorldGeneration(World world, Integer r) {
        finished = false;
        r = r + 250;
        percentage = 0.0D;
        this.totalChunkToLoad = Math.pow(r, 2.0D) / 64.0D;
        this.currentChunkLoad = 0.0D;
        this.cx = -r;
        this.cz = -r;
        this.world = world;
        this.radius = r;
    }

    public void load() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (int i = 0; i < 30 && !isFinished(); i++) {
                    Location loc = new Location(world, cx, 0.0D, cz);
                    if (!loc.getChunk().isLoaded())
                        loc.getWorld().loadChunk(loc.getChunk().getX(), loc.getChunk().getZ(), true);
                    cx = cx + 16;
                    currentChunkLoad = currentChunkLoad + 1.0D;
                    //sendMessage();
                    if (cx > radius) {
                        cx = -radius;
                        cz = cz + 16;
                        if (cz > radius) {
                            currentChunkLoad = totalChunkToLoad;
                            setFinished(true);
                        }
                    }
                }
                percentage = currentChunkLoad / totalChunkToLoad * 100.0D;
                if (isFinished()) {
                    if(!netherLoaded) {
                        Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> loadNether(radius - 250), 100L);
                    }
                    cancel();
                }
            }
        }.runTaskTimer(UHC.getInstance(), 0L, 3L);

    }
    public static void loadNether(int uhcsize){
        if(uhcLoaded && !netherLoaded) {
            setNetherLoaded(true);
            new WorldGeneration(Bukkit.getWorld("uhc_nether"), uhcsize / 8).load();
        }
    }
    public static void loadUhc(int uhcsize){
        if(!uhcLoaded) {
            setUhcLoaded(true);
            new WorldGeneration(Bukkit.getWorld("uhc_world"), uhcsize).load();
        }
    }

    public static boolean isFinished() {
        return finished;
    }

    public static Double getPercentage() {
        return percentage;
    }
    public static String getFormattedPercentage() {
        DecimalFormat format = new DecimalFormat("#.#");
        return format.format(getPercentage());
    }

    public static void setFinished(boolean finished) {
        WorldGeneration.finished = finished;
    }

    private void sendMessage() {
        DecimalFormat format = new DecimalFormat("#.#");
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (messageLimiter.containsKey(player.getUniqueId())) {
                long lastTime = messageLimiter.get(player.getUniqueId());
                if ((System.currentTimeMillis() - lastTime) > 3000) {
                    messageLimiter.remove(player.getUniqueId());
                } else {
                    return;
                }
            } else {
                player.sendMessage(Color.translate("&eGenerating &7(&9" + getFormattedPercentage() + "%&7)"));
                messageLimiter.put(player.getUniqueId(), System.currentTimeMillis());
            }
            //Title.sendActionBar(player, "§eLe chargement est à §f" + format.format(getPercentage()) + "/100");
        }
    }
    public boolean isUhcLoaded() {
        return uhcLoaded;
    }
    public static void setUhcLoaded(boolean b){
        uhcLoaded = b;
    }

    public boolean isNetherLoaded() {
        return netherLoaded;
    }

    public static void setNetherLoaded(boolean b) {
        netherLoaded = b;
    }
}