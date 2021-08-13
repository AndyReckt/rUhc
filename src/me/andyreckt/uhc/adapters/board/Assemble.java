package me.andyreckt.uhc.adapters.board;

import me.andyreckt.uhc.adapters.board.events.AssembleBoardCreateEvent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class Assemble {

	private JavaPlugin plugin;
	private AssembleAdapter adapter;
	private Map<UUID, AssembleBoard> boards;
	private AssembleThread thread;
	private AssembleListener listeners;
	private long ticks = 2;
	private boolean hook = false;
	private AssembleStyle assembleStyle = AssembleStyle.MODERN;
	private boolean debugMode = true;

	public Assemble(JavaPlugin plugin, AssembleAdapter adapter) {
		if (plugin == null) {
			throw new RuntimeException("Assemble can not be instantiated without a plugin instance!");
		}

		this.plugin = plugin;
		this.adapter = adapter;
		this.boards = new ConcurrentHashMap<>();

		this.setup();
	}

	public void setup() {
		//Register Events
		this.listeners = new AssembleListener(this);
		this.plugin.getServer().getPluginManager().registerEvents(listeners, this.plugin);

		//Ensure that the thread has stopped running
		if (this.thread != null) {
			this.thread.stop();
			this.thread = null;
		}

		//Register new boards for existing online players
		for (Player player : Bukkit.getOnlinePlayers()) {
			//Make sure it doesn't double up
			AssembleBoardCreateEvent createEvent = new AssembleBoardCreateEvent(player);

			Bukkit.getPluginManager().callEvent(createEvent);
			if (createEvent.isCancelled()) {
				return;
			}

			getBoards().putIfAbsent(player.getUniqueId(), new AssembleBoard(player, this));
		}

		//Start Thread
		this.thread = new AssembleThread(this);
	}

	public void cleanup() {
		if (this.thread != null) {
			this.thread.stop();
			this.thread = null;
		}

		if (listeners != null) {
			HandlerList.unregisterAll(listeners);
			listeners = null;
		}

		for (UUID uuid : getBoards().keySet()) {
			Player player = Bukkit.getPlayer(uuid);

			if (player == null || !player.isOnline()) {
				continue;
			}

			getBoards().remove(uuid);
			player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		}
	}

	public JavaPlugin getPlugin() {
		return plugin;
	}

	public void setPlugin(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	public AssembleAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(AssembleAdapter adapter) {
		this.adapter = adapter;
	}

	public Map<UUID, AssembleBoard> getBoards() {
		return boards;
	}

	public void setBoards(Map<UUID, AssembleBoard> boards) {
		this.boards = boards;
	}

	public AssembleThread getThread() {
		return thread;
	}

	public void setThread(AssembleThread thread) {
		this.thread = thread;
	}

	public AssembleListener getListeners() {
		return listeners;
	}

	public void setListeners(AssembleListener listeners) {
		this.listeners = listeners;
	}

	public long getTicks() {
		return ticks;
	}

	public void setTicks(long ticks) {
		this.ticks = ticks;
	}

	public boolean isHook() {
		return hook;
	}

	public void setHook(boolean hook) {
		this.hook = hook;
	}

	public AssembleStyle getAssembleStyle() {
		return assembleStyle;
	}

	public void setAssembleStyle(AssembleStyle assembleStyle) {
		this.assembleStyle = assembleStyle;
	}

	public boolean isDebugMode() {
		return debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}
}
