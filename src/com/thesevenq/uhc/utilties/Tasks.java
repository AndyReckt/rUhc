package com.thesevenq.uhc.utilties;

import com.thesevenq.uhc.UHC;
import org.bukkit.Bukkit;

public class Tasks {

    public static void run(Callable callable) {
        Bukkit.getScheduler().runTask(UHC.getInstance(), callable::call);
    }

    public static void runAsync(Callable callable) {
        Bukkit.getScheduler().runTaskAsynchronously(UHC.getInstance(), callable::call);
    }

    public static void runLater(Callable callable, long delay) {
        Bukkit.getScheduler().runTaskLater(UHC.getInstance(), callable::call, delay);
    }

    public static void runAsyncLater(Callable callable, long delay) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(UHC.getInstance(), callable::call, delay);
    }

    public static void runTimer(Callable callable, long delay, long interval) {
        Bukkit.getScheduler().runTaskTimer(UHC.getInstance(), callable::call, delay, interval);
    }

    public static void runAsyncTimer(Callable callable, long delay, long interval) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(UHC.getInstance(), callable::call, delay, interval);
    }

    public interface Callable {
        void call();
    }
}
