package me.andyreckt.uhc.tasks;

import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.managers.BorderManager;
import org.bukkit.scheduler.BukkitRunnable;

public class BorderTimeTask extends BukkitRunnable {

	private UHC plugin = UHC.getInstance();
	public static int seconds = 0;
    
    public BorderTimeTask() {
    	this.runTaskTimerAsynchronously(UHC.getInstance(), 20L, 20L);
    }

    @Override
    public void run() {
        if(BorderManager.border <= 25) {
            this.cancel();
            return;
        }
    	
		if(plugin.getGameManager().isBorderTime()) {
			seconds--;
		} else {
			this.cancel();
		}
    }

	public static void setSeconds() {
		seconds = 300;
	}
}