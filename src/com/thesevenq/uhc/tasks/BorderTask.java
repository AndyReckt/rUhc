package com.thesevenq.uhc.tasks;

import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.managers.BorderManager;
import com.thesevenq.uhc.managers.OptionManager;
import com.thesevenq.uhc.utilties.Color;
import com.thesevenq.uhc.utilties.UHCUtils;
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
                Bukkit.broadcastMessage(Color.translate("&bBorder will shrink in &3" + (seconds / 60) + " &bminutes to &3" + UHCUtils.getNextBorder() + "&bx&3" + UHCUtils.getNextBorder() + "&b."));
            }
        }
    }
}