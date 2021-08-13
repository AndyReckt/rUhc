package me.andyreckt.uhc.tasks;

import me.andyreckt.uhc.UHC;
import org.bukkit.scheduler.BukkitRunnable;

public class DotsTask extends BukkitRunnable {

    public static DotsTask instance;

    public void setString(String string) { this.string = string; }

    public static DotsTask getInstance() { return instance; }
    public String getString() { return string; } private String string = ".";

    public DotsTask() {
        instance = this;
        runTaskTimerAsynchronously(UHC.getInstance(), 20L, 20L);
    }

    public void run() {
        switch (string) {
            case ".":
                string = "..";
                break;

            case "..":
                string = "...";
                break;

            case "...":
                string = ".";
        }
    }
}
