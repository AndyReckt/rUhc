package com.thesevenq.uhc.managers;

import com.thesevenq.uhc.utilties.Color;
import com.thesevenq.uhc.utilties.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.config.Option;
import com.thesevenq.uhc.scenario.Scenario;
import com.thesevenq.uhc.player.state.GameState;
import com.thesevenq.uhc.utilties.Manager;
import com.thesevenq.uhc.utilties.UHCUtils;
import com.thesevenq.uhc.utilties.items.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class InventoryManager extends Manager {

	public static Inventory
			uhcSettings, uhcPlayerSettings, options,
			scatter, uhcPractice, toggleScenarios,
			maxPlayers, rates, team,
			scenarioInfo;

	public InventoryManager(UHC plugin) {
		super(plugin);

		scenarioInfo = Bukkit.createInventory(null, 45, Color.translate("&bCurrent Scenarios"));
		uhcSettings = Bukkit.createInventory(null, 18, Color.translate("&bUHC Settings"));
		uhcPlayerSettings = Bukkit.createInventory(null, 45, Color.translate("&bUHC Settings"));
		options = Bukkit.createInventory(null, 27, Color.translate("&bOptions Inventory"));
		scatter = Bukkit.createInventory(null, 9, Color.translate("&bScatter Inventory"));
		uhcPractice = Bukkit.createInventory(null, 9, Color.translate("&bPractice"));
		toggleScenarios = Bukkit.createInventory(null, 45, Color.translate("&bToggle Scenarios"));
		maxPlayers = Bukkit.createInventory(null, 45, Color.translate("&bMax Players"));
		rates = Bukkit.createInventory(null, 36, Color.translate("&bRates Inventory"));
		team = Bukkit.createInventory(null, 27, Color.translate("&bParties"));

		runInventories();
	}

	public static void setupSettings() {
		uhcSettings.clear();


		uhcSettings.setItem(0, Items.getParties());
		uhcSettings.setItem(1, Items.getUHCPractice());
		uhcSettings.setItem(2, ScenarioManager.getScenariosItem());
		uhcSettings.setItem(3, Items.getMaxPlayers());
		uhcSettings.setItem(4, Items.getToggleScenarios());
		uhcSettings.setItem(5, Items.getRates());
		uhcSettings.setItem(6, Items.getOptions());
		uhcSettings.setItem(7, Items.getStartInventory());
		uhcSettings.setItem(8, Items.getGlass());
	}

	public static void setupPlayerSettings() {
		uhcPlayerSettings.clear();

		uhcPlayerSettings.setItem(13, ScenarioManager.getScenariosItem());
		uhcPlayerSettings.setItem(28, Items.getIsPartiesEnabled());
		uhcPlayerSettings.setItem(29, Items.getCurrentMaxPlayers());
		uhcPlayerSettings.setItem(30, Items.getShears());
		uhcPlayerSettings.setItem(31, Items.getAppleRate());
		uhcPlayerSettings.setItem(32, Items.getCurrentScenarios());
		uhcPlayerSettings.setItem(33, Items.getIsUHCPracticeEnabled());
		uhcPlayerSettings.setItem(34, Items.getOptions());

		uhcPlayerSettings.setItem(0, Items.getGlass());
		uhcPlayerSettings.setItem(1, Items.getGlass());
		uhcPlayerSettings.setItem(2, Items.getGlass());
		uhcPlayerSettings.setItem(3, Items.getGlass());
		uhcPlayerSettings.setItem(4, Items.getGlass());
		uhcPlayerSettings.setItem(5, Items.getGlass());
		uhcPlayerSettings.setItem(6, Items.getGlass());
		uhcPlayerSettings.setItem(7, Items.getGlass());
		uhcPlayerSettings.setItem(8, Items.getGlass());
		uhcPlayerSettings.setItem(9, Items.getGlass());
		uhcPlayerSettings.setItem(10, Items.getGlass());
		uhcPlayerSettings.setItem(11, Items.getGlass());
		uhcPlayerSettings.setItem(12, Items.getGlass());
		uhcPlayerSettings.setItem(14, Items.getGlass());
		uhcPlayerSettings.setItem(15, Items.getGlass());
		uhcPlayerSettings.setItem(16, Items.getGlass());
		uhcPlayerSettings.setItem(17, Items.getGlass());
		uhcPlayerSettings.setItem(18, Items.getGlass());
		uhcPlayerSettings.setItem(19, Items.getGlass());
		uhcPlayerSettings.setItem(20, Items.getGlass());
		uhcPlayerSettings.setItem(21, Items.getGlass());
		uhcPlayerSettings.setItem(22, Items.getGlass());
		uhcPlayerSettings.setItem(23, Items.getGlass());
		uhcPlayerSettings.setItem(24, Items.getGlass());
		uhcPlayerSettings.setItem(25, Items.getGlass());
		uhcPlayerSettings.setItem(26, Items.getGlass());
		uhcPlayerSettings.setItem(27, Items.getGlass());
		uhcPlayerSettings.setItem(35, Items.getGlass());
		uhcPlayerSettings.setItem(36, Items.getGlass());
		uhcPlayerSettings.setItem(37, Items.getGlass());
		uhcPlayerSettings.setItem(38, Items.getGlass());
		uhcPlayerSettings.setItem(39, Items.getGlass());
		uhcPlayerSettings.setItem(40, Items.getGlass());
		uhcPlayerSettings.setItem(41, Items.getGlass());
		uhcPlayerSettings.setItem(42, Items.getGlass());
		uhcPlayerSettings.setItem(43, Items.getGlass());
		uhcPlayerSettings.setItem(44, Items.getGlass());

	}
	public static void setupScatter() {
		scatter.clear();

		scatter.setItem(2, UHCUtils.getYes());
		scatter.setItem(6, UHCUtils.getNo());
	}

	private static int getInteger(String name) {
		return OptionManager.getByName(name).getTranslatedValue();
	}

	private static boolean isActive(String name) {
		return OptionManager.getByName(name).getValue() != 1;
	}

	public static void setupMaxPlayers() {
		maxPlayers.clear();

		maxPlayers.setItem(0, Items.getBack());
		maxPlayers.setItem(19, Items.getMaxOnlineMinus50());
		maxPlayers.setItem(20, Items.getMaxOnlineMinus10());
		maxPlayers.setItem(21, Items.getMaxOnlineMinus5());
		maxPlayers.setItem(22, Items.getCurrentMaxPlayers());
		maxPlayers.setItem(23, Items.getMaxOnlinePlus5());
		maxPlayers.setItem(24, Items.getMaxOnlinePlus10());
		maxPlayers.setItem(25, Items.getMaxOnlinePlus50());
	}

	public static void setupRates() {
		rates.clear();

		rates.setItem(0, Items.getBack());
		rates.setItem(10, Items.getShearsRateMinus2());
		rates.setItem(11, Items.getShearsRateMinus1());
		rates.setItem(13, Items.getShears());
		rates.setItem(15, Items.getShearsRatePlus1());
		rates.setItem(16, Items.getShearsRatePlus2());
		rates.setItem(19, Items.getAppleRateMinus2());
		rates.setItem(20, Items.getAppleRateMinus1());
		rates.setItem(22, Items.getAppleRate());
		rates.setItem(24, Items.getAppleRatePlus1());
		rates.setItem(25, Items.getAppleRatePlus2());
	}

	public static void setupTeam() {
		team.clear();

		team.setItem(0, Items.getBack());
		team.setItem(10, Items.getPartiesEnable());
		team.setItem(11, Items.getTeamAdd1());
		team.setItem(13, Items.getIsPartiesEnabled());
		team.setItem(15, Items.getTeamRemove1());
		team.setItem(16, Items.getPartiesDisable());
	}

	public static void setupUHCPractice() {
		uhcPractice.setItem(2, Items.getEnablePractice());
		uhcPractice.setItem(6, Items.getDisablePractice());
	}

	public static void runInventories() {
		setupUHCPractice();
		setupScatter();

		new BukkitRunnable() {
			public void run() {
				setToggleScenario();
				setupPlayerSettings();

				if(!GameManager.getGameState().equals(GameState.PLAYING)) {
					setupSettings();
					setupMaxPlayers();
					setupRates();
					setupTeam();
					setOptions();
				}
			}
		}.runTaskTimerAsynchronously(UHC.getInstance(), 20L, 20L);
	}

	public static void setOptions() {
		options.clear();

		OptionManager.getOptions().stream()
				.sorted(Comparator.comparing(Option::isBoolean))
				.forEach(option -> options.addItem(OptionManager.getConfigItem(option)));
	}

	public static void setToggleScenario() {
		toggleScenarios.clear();

		ScenarioManager.getScenarios().stream()
				.sorted(Comparator.comparing(Scenario::getName))
				.forEach(scenario -> toggleScenarios.addItem(ScenarioManager.getScenarioItem(scenario)));

		toggleScenarios.setItem(44, Items.getDisableAllScenarios());
	}

	public static Inventory setScenarioInfo(Player player) {
		scenarioInfo.clear();

		ScenarioManager.getScenarios().stream()
				.sorted(Comparator.comparing(Scenario::getName))
				.forEach(scenario -> {
					if(scenario.isEnabled()) {
						scenarioInfo.addItem(ScenarioManager.getScenarioDescription(scenario));
					}
				});

		player.openInventory(scenarioInfo);
		return scenarioInfo;
	}
}
