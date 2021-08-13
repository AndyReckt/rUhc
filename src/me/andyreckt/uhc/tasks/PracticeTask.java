package me.andyreckt.uhc.tasks;

import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.managers.GameManager;
import me.andyreckt.uhc.player.state.GameState;
import me.andyreckt.uhc.utilties.ActionMessage;
import me.andyreckt.uhc.utilties.Color;
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
			actionMessage.addText("&9Practice&e is now open! ");
			actionMessage.addText("&7[Join]").setClickEvent(ActionMessage.ClickableType.RunCommand, "/practice join").addHoverText(Color.translate("&eClick to join!"));

			Bukkit.getOnlinePlayers().forEach(player -> actionMessage.sendToPlayer(player));
		} else {
			this.cancel();
		}
		
		if(GameManager.getGameState().equals(GameState.PLAYING)) {
			this.cancel();
		}
	}
}
