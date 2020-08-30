package com.thesevenq.uhc.tasks;

import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.managers.GameManager;
import com.thesevenq.uhc.player.state.GameState;
import com.thesevenq.uhc.utilties.ActionMessage;
import com.thesevenq.uhc.utilties.Color;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class PracticeTask extends BukkitRunnable {

	private UHC plugin = UHC.getInstance();

	public PracticeTask() {
		this.runTaskTimer(UHC.getInstance(), 2000L, 2000L);
	}
	
	@Override
	public void run() {
		if(plugin.getPracticeManager().isOpen()) {
			ActionMessage actionMessage = new ActionMessage();
			actionMessage.addText("&3Practice&b is now open! ");
			actionMessage.addText("&7[Join]").setClickEvent(ActionMessage.ClickableType.RunCommand, "/practice join").addHoverText(Color.translate("&bClick to join!"));

			Bukkit.getOnlinePlayers().forEach(player -> actionMessage.sendToPlayer(player));
		} else {
			this.cancel();
		}
		
		if(GameManager.getGameState().equals(GameState.PLAYING)) {
			this.cancel();
		}
	}
}
