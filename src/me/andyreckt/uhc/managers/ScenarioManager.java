package me.andyreckt.uhc.managers;

import me.andyreckt.uhc.scenario.type.*;
import me.andyreckt.uhc.utilties.Color;
import me.andyreckt.uhc.utilties.Msg;
import me.andyreckt.uhc.utilties.item.ItemBuilder;

import me.andyreckt.uhc.scenario.Scenario;
import me.andyreckt.uhc.utilties.Manager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import me.andyreckt.uhc.UHC;

import java.util.ArrayList;
import java.util.List;

public class ScenarioManager extends Manager {

	public static List<Scenario> getScenarios() {
		return scenarios;
	}

	public static List<Scenario> scenarios = new ArrayList<>();

	public ScenarioManager(UHC plugin) {
		super(plugin);

		new BackPacksScenario();
		new BareBonesScenario();
		new BestPVEScenario();
		new BloodDiamondsScenario();
		new BloodEnchantsScenario();
		new BowlessScenario();
		new ColdWeaponsScenario();
		new CutCleanScenario();
		new DiamondlessScenario();
		new DoNotDisturbScenario();
		new DoubleExpScenario();
		new DoubleOresScenario();
		new FirelessScenario();
		new GoldenRetrieverScenario();
		new GoldlessScenario();
		new GoneFishingScenario();
		new HorselessScenario();
		new InfiniteEnchanterScenario();
		new IronlessScenario();
		new LimitationsScenario();
		new LimitedEnchantsScenario();
		new LongShotsScenario();
		new LuckyLeavesScenario();
		new NineSlotScenario();
		new NoCleanScenario();
		new NoEnchantsScenario();
		new NoFallDamageScenario();
		new OreFrenzyScenario();
		new RiskyRetrievalScenario();
		new RodlessScenario();
		new SeasonsScenario();
		new SoupScenario();
		new SwitcherooScenario();
		new SwordlessScenario();
		new TimberScenario();
		new TimeBombScenario();
		new TripleExpScenario();
		new TripleOresScenario();
		new VanillaPlusScenario();
		new WebCageScenario();
		new HasteyBoysScenario();
	}

	public static void disable() {
		BestPVEScenario.list.clear();
		LimitationsScenario.disable();
	}

	public static ItemStack getScenariosItem() {
		ItemStack item = new ItemStack(Material.ANVIL);
		ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(Color.translate("&9Scenarios"));

		List<String> lore = new ArrayList<>();

		lore.add("");


		for(Scenario scenario : ScenarioManager.scenarios) {
			if(scenario.isEnabled()) {
				lore.add(Color.translate("&9&l" + Msg.KRUZIC + "&e " + scenario.getName()));
			}
		}

		if(getActiveScenarios() == 0) {
			lore.add(Color.translate("&eNo active scenarios."));
		}

		lore.add("");

		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getScenarioDescription(Scenario scenario) {
		ItemBuilder builder = new ItemBuilder(scenario.getMaterial());
		builder.name((scenario.isEnabled() ? "&a" : "&c") + scenario.getName());

		List<String> lore = new ArrayList<>();

		lore.add("");
		lore.add(Color.translate("&eDescription"));
		lore.add("");

		for(String text : scenario.getFeatures()) {
			lore.add(Color.translate(" &9&l" + Msg.KRUZIC +"&9 " + text));
		}

		builder.lore(lore);

		return builder.build();
	}

	public static ItemStack getScenarioItem(Scenario scenario) {
		ItemBuilder builder = new ItemBuilder(scenario.getMaterial());
		builder.name((scenario.isEnabled() ? "&a" : "&c") + scenario.getName());

		List<String> lore = new ArrayList<>();

		lore.add("");
		lore.add("&eStatus: " + (scenario.isEnabled() ? "&aEnabled" : "&cDisabled"));
		lore.add("&eDescription:");
		lore.add("");

		for(String text : scenario.getFeatures()) {
			lore.add(" &9&l" + Msg.KRUZIC + " &9 " + text);
		}

		lore.add("");
		lore.add("&eClick to toggle!");
		lore.add("");

		builder.lore(lore);

		return builder.build();
	}

	public static Scenario getByName(String name) {
		for(Scenario scenario : scenarios) {
			if(name.equalsIgnoreCase(scenario.getName())) {
				return scenario;
			}
		}

		return null;
	}

	public static int getActiveScenarios() {
		int i = 0;

		for(Scenario scenario : scenarios) {
			if(scenario.isEnabled()) {
				i++;
			}
		}

		return i;
	}

}
