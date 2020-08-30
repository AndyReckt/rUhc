
package com.thesevenq.uhc.tasks;

import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.managers.BorderManager;
import com.thesevenq.uhc.managers.OptionManager;
import com.thesevenq.uhc.managers.PartyManager;
import com.thesevenq.uhc.managers.PlayerManager;
import com.thesevenq.uhc.player.party.Party;
import com.thesevenq.uhc.utilties.*;
import com.thesevenq.uhc.utilties.events.GameWinEvent;
import com.thesevenq.uhc.utilties.events.GameWinTeamEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import com.thesevenq.uhc.player.UHCData;

public class GameTask extends BukkitRunnable {

	private UHC plugin = UHC.getInstance();
	public static int seconds = 0;

	public static boolean broadcasted = false;
	
	public static int pvp_time = (OptionManager.getByNameAndTranslate("PvP Period Duration") * 60);
	public static int heal_time = (OptionManager.getByNameAndTranslate("Final Heal") * 60);

	public GameTask() {
		runTaskTimer(UHC.getInstance(), 20L, 20L);
    }

	@Override
    public void run() {
    	seconds++;

		Bukkit.getOnlinePlayers().forEach(BorderManager::checkBorder);
		
		if((seconds % 5) == 0) {
			if(PartyManager.isEnabled()) {
				if(!broadcasted) {
					if(plugin.getPartyManager().getPartiesAlive() == 1) {
						Party party = plugin.getPartyManager().getLastParty();
						
						Bukkit.getPluginManager().callEvent(new GameWinTeamEvent(party));
						
						broadcasted = true;
						
						for(Player players : Bukkit.getOnlinePlayers()) {
							players.playSound(players.getLocation(), Sound.WITHER_DEATH, 1F, 1F);
						}
					}
				}

			} else {
				if(!broadcasted) {
					if(PlayerManager.getAlivePlayers() == 1) {
						for(UHCData uhcData : UHCData.getUhcDatas().values()) {
							if(uhcData.isAlive()) {
								if(!broadcasted) {
									Bukkit.getPluginManager().callEvent(new GameWinEvent(uhcData.getName(), uhcData));

									plugin.getGameManager().setWinner(uhcData.getName());
									
									broadcasted = true;
									
									for(Player players : Bukkit.getOnlinePlayers()) {
										players.playSound(players.getLocation(), Sound.WITHER_DEATH, 1F, 1F);
									}
								}
							}
						}
					}
				}
			}
		 } else {
			for(Player online : Bukkit.getOnlinePlayers()) {
				if(seconds <= 5) {
					if(online.getLocation().getBlockY() < Bukkit.getWorld("uhc_world").getHighestBlockAt(online.getLocation()).getLocation().getBlockY()) {
						if(Bukkit.getWorld("uhc_world").getHighestBlockAt(online.getLocation()).getType() == Material.LEAVES) return;

						online.teleport(online.getLocation().getWorld().getHighestBlockAt(online.getLocation()).getLocation().add(0, 1, 0));
					}

					online.setHealth(20.0);
				}

				BorderManager.checkBorder(online);
			}
		}

		int border_time = BorderTimeTask.seconds;

		if(seconds < heal_time) {
			BossBar.display("&3Final Heal: &5" + StringUtils.formatInt(heal_time - seconds), 1F - (((float) seconds) / heal_time));
		} else if (seconds < pvp_time) {
			BossBar.display("&3PvP Time: &5" + StringUtils.formatInt(pvp_time - seconds), 1F - (((float) (seconds - heal_time)) / (pvp_time - heal_time)));
		} else if(plugin.getGameManager().isBorderTime()) {
			BossBar.display("&3Border shrinking to &5" + UHCUtils.getNextBorder() + " &3in &5" + StringUtils.formatInt(border_time), 1F - (((float) seconds) / border_time));
		} else if(BorderManager.border == 25) {
			Bukkit.getOnlinePlayers().forEach(BossBar::remove);
		} else {
			Bukkit.getOnlinePlayers().forEach(BossBar::remove);
		}

		this.getHealEnablingTime();

    	if(heal_time == seconds) {
    		for(Player player : Bukkit.getOnlinePlayers()) {
    			player.setHealth(20.0);
    		}

			Bukkit.broadcastMessage(Color.translate("&bFinal Heal received."));

			handleSound();
		}

    	this.getPvPEnablingTime();

    	if(pvp_time == seconds) {
    		plugin.getGameManager().setPvP(true);
			handleSound();
		}

        if(((OptionManager.getByNameAndTranslate("First Shrink") - OptionManager.getByNameAndTranslate("Border Shrink Interval")) * 60) == seconds) {
            BorderManager.enablePermaDay();

            plugin.getGameManager().setBorderShrink(true);

    		new BorderTimeTask();

			plugin.getGameManager().setBorderTime(true);

            BorderTimeTask.setSeconds();
            BorderManager.startBorderShrink();

			handleSound();
		}
    }

	private void getHealEnablingTime() {
		int heal = OptionManager.getByNameAndTranslate("Final Heal");

      	if(((heal - 5)  * 60) == seconds) {
      		Msg.sendMessage("&bFinal Heal will be in &35 &bminutes.");
      		handleSound();
        }

       	if(((heal - 4)  * 60) == seconds) {
       		Msg.sendMessage("&bFinal Heal will be in &34 &bminutes.");
			handleSound();
		}

       	if(((heal - 3)  * 60) == seconds) {
       		Msg.sendMessage("&bFinal Heal will be in &33 &bminutes.");
			handleSound();
		}

       	if(((heal - 2)  * 60) == seconds) {
       		Msg.sendMessage("&bFinal Heal will be in &32 &bminutes.");
			handleSound();
		}

       	if(((heal - 1)  * 60) == seconds) {
       		Msg.sendMessage("&bFinal Heal will be in &31 &bminute.");
			handleSound();
		}
	}

	private void getPvPEnablingTime() {
    	int pvp = OptionManager.getByNameAndTranslate("PvP Period Duration");

      	if(((pvp - 5)  * 60) == seconds) {
    		Msg.sendMessage("&bPvP will be enabled in &35 &bminutes.");
			handleSound();
		}

       	if(((pvp - 4)  * 60) == seconds) {
    		Msg.sendMessage("&bPvP will be enabled in &34 &bminutes.");
			handleSound();
		}

       	if(((pvp - 3)  * 60) == seconds) {
    		Msg.sendMessage("&bPvP will be enabled in &33 &bminutes.");
			handleSound();
		}

       	if(((pvp - 2)  * 60) == seconds) {
    		Msg.sendMessage("&bPvP will be enabled in &32 &bminutes.");
			handleSound();
		}

       	if(((pvp - 1)  * 60) == seconds) {
    		Msg.sendMessage("&bPvP will be enabled in &31 &bminute.");
			handleSound();
		}
	}

	private void handleSound() {
		Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1F, 1F));
	}
}