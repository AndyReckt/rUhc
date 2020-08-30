package com.thesevenq.uhc.tasks;

import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.api.API;
import com.thesevenq.uhc.managers.ScenarioManager;
import com.thesevenq.uhc.scenario.Scenario;
import com.thesevenq.uhc.utilties.Color;
import com.thesevenq.uhc.utilties.Msg;
import com.thesevenq.uhc.utilties.ServerUtils;
import com.thesevenq.uhc.utilties.UHCUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AutoStartTask extends BukkitRunnable {

	public static long seconds = 0;
	public static boolean running = false;
	public static AutoStartTask starting;
	
	public AutoStartTask(long time) {
		this.runTaskTimerAsynchronously(UHC.getInstance(), 20L, 20L);

		starting = this;
		seconds = time;
		running = true;
		
		this.broadcast();
	}
	
	@Override
	public void run() {
		seconds-= 1000;
		
		if(seconds == 0) {
			this.start();
			running = false;
			this.cancel();
		}
		
		if(seconds < 1000) return;

		if(seconds <= 10000 || seconds == 15000 || seconds == 30000 || seconds == 45000 || seconds == 60000 || seconds == 120000 || seconds == 180000 || seconds == 240000 || seconds == 300000 || seconds == 600000 || seconds == 900000 || seconds == 1200000 || seconds == 1500000 || seconds == 1800000) {
			this.broadcast();
		}
	}
	
	public void start() {
		Msg.sendMessage(Color.translate("&4&lStarting game..."));

		new BukkitRunnable() {
			public void run() {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "game start");
			}
		}.runTaskLater(UHC.getInstance(), 20L);
	}
	
	public void broadcast() {
		ServerUtils.bungeeBroadcast("");

		List<String> scenarios = new ArrayList<>();

		ScenarioManager.getScenarios().stream().sorted(Comparator.comparing(Scenario::getName)).forEach(scenario -> {
			if(scenario.isEnabled()) {
				scenarios.add(scenario.getName());
			}
		});

		StringBuilder builder = new StringBuilder();

		scenarios.forEach(scenario -> {
			if(builder.length() > 0) {
				builder.append(Color.translate("&7, "));
			}

			builder.append(Color.translate("&d")).append(scenario);
		});

		String toBroadcast = scenarios.toString().replace("[", "").replace("]", "");

		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(Color.translate("" + "&8[&3&lUHC Info&8] &3" + UHCUtils.isPartiesEnabled() + " &3" + API.getServerName() + " &bgame is starting in &3" + DurationFormatUtils.formatDurationWords(seconds, true, true)
				+ " &bwith scenarios &3" + toBroadcast + "&b." ));
		Bukkit.broadcastMessage("");
	}
	
	public void cancelRunnable() {
		cancel();

		Msg.sendMessage("&eCanceled auto-start!");
		
		running = false;
	}
}
