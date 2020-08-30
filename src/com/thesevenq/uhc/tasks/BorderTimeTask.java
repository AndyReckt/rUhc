package com.thesevenq.uhc.tasks;

import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.managers.BorderManager;
import org.bukkit.scheduler.BukkitRunnable;
import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.managers.BorderManager;

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