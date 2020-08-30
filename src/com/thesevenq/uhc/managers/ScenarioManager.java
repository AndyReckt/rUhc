package com.thesevenq.uhc.managers;

import com.thesevenq.uhc.scenario.type.*;
import com.thesevenq.uhc.utilties.Color;
import com.thesevenq.uhc.utilties.Msg;
import com.thesevenq.uhc.utilties.item.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.scenario.Scenario;
import com.thesevenq.uhc.utilties.Manager;

import java.util.ArrayList;
import java.util.List;

public class ScenarioManager extends Manager {

	@Getter
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

		meta.setDisplayName(Color.translate("&3&lScenarios"));

		List<String> lore = new ArrayList<>();

		lore.add("");


		for(Scenario scenario : ScenarioManager.scenarios) {
			if(scenario.isEnabled()) {
				lore.add(Color.translate("&3&l" + Msg.KRUZIC + "&b " + scenario.getName()));
			}
		}

		if(getActiveScenarios() == 0) {
			lore.add(Color.translate("&bNo active scenarios."));
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
		lore.add(Color.translate("&bDescription"));
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
		lore.add("&bStatus: " + (scenario.isEnabled() ? "&aEnabled" : "&cDisabled"));
		lore.add("&bDescription:");
		lore.add("");

		for(String text : scenario.getFeatures()) {
			lore.add(" &9&l" + Msg.KRUZIC + " &9 " + text);
		}

		lore.add("");
		lore.add("&bClick to toggle!");
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
