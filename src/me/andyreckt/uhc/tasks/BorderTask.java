package me.andyreckt.uhc.tasks;

import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.managers.BorderManager;
import me.andyreckt.uhc.managers.OptionManager;
import me.andyreckt.uhc.utilties.Color;
import me.andyreckt.uhc.utilties.UHCUtils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;


import java.util.Arrays;

public class BorderTask extends BukkitRunnable {

    private UHC plugin = UHC.getInstance();
    private int seconds = (OptionManager.getByNameAndTranslate("Border Shrink Interval") * 60);

    @Override
    public void run() {
        if(BorderManager.border <= 25) {
            this.cancel();
            return;
        }
        
        if(!plugin.getGameManager().isBorderShrink()) {
            this.cancel();
        }

        seconds -= 10;
        
        if(seconds == 10) {
        	BorderManager.startSeconds();
        	
            this.cancel();
        } else if (seconds > 10) {
            if(Arrays.asList(600, 540, 480, 420, 360, 300, 240, 180, 120, 60).contains(seconds)) {
                Bukkit.broadcastMessage(Color.translate("&eBorder will shrink in &9" + (seconds / 60) + " &eminutes to &9" + UHCUtils.getNextBorder() + "&ex&9" + UHCUtils.getNextBorder() + "&e."));
            }
        }
    }
}