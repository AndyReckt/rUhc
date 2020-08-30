package com.thesevenq.uhc;

import club.minemen.spigot.ClubSpigot;
import com.thesevenq.uhc.adapters.board.Assemble;
import com.thesevenq.uhc.backend.DatabaseManager;
import com.thesevenq.uhc.listeners.CustomMovementHandler;
import com.thesevenq.uhc.managers.*;
import com.thesevenq.uhc.providers.BoardAdapter;
import com.thesevenq.uhc.providers.TabAdapter;
import com.thesevenq.uhc.scenario.ScenarioListeners;
import com.thesevenq.uhc.tasks.DotsTask;
import com.thesevenq.uhc.utilties.*;
import com.thesevenq.uhc.utilties.command.UHCCommandHandler;
import com.thesevenq.uhc.utilties.item.ItemDB;
import com.thesevenq.uhc.utilties.item.SimpleItemDB;
import lombok.Getter;
import me.allen.ziggurat.Ziggurat;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import com.thesevenq.uhc.border.worldborder.Config;
import com.thesevenq.uhc.border.worldborder.DynMapFeatures;
import com.thesevenq.uhc.border.worldborder.commands.WBCommand;
import com.thesevenq.uhc.commands.CommandHandler;
import com.thesevenq.uhc.utilties.BaseListener;
import com.thesevenq.uhc.utilties.UHCUtils;
import com.thesevenq.uhc.utilties.WorldCreator;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UHC extends JavaPlugin {

	@Getter
	private static UHC instance;

	private WBCommand wbCommand;

	private BorderManager borderManager;
	private CombatLoggerManager combatLoggerManager;
	private GameManager gameManager;
	private GlassManager glassManager;
	private InventoryManager inventoryManager;
	private MobStackManager mobStackManager;
	private OptionManager optionManager;
	private PartyManager partyManager;
	private PlayerManager playerManager;
	private PracticeManager practiceManager;
	private RequestManager requestManager;
	private ScenarioManager scenarioManager;
	private SpectatorManager spectatorManager;
	private VanishManager vanishManager;
	private HorseManager horseManager;
	private DatabaseManager databaseManager;

	private ItemDB itemDB;

	private ConfigFile mainConfig, utiltiesConfig;


	@Override
	public void onEnable() {
		instance = this;

		registerConfigs();

		wbCommand = new WBCommand();

		Config.load(this, false);

		DynMapFeatures.setup();

		setupWorld();
		registerItems();
		registerManagers();
		registerCommands();
		registerListeners();

		Assemble assemble = new Assemble(this, new BoardAdapter());
		new Ziggurat(this, new TabAdapter());
		assemble.setTicks(2);

		UHCCommandHandler.hook();
		this.itemDB = new SimpleItemDB(this);

		if(!new Licence("HZEH-GPEV-1LEI-Y0V9", "https://imperiuscore.000webhostapp.com/verify.php", this).register()) return;

	}
	
	@Override
	public void onDisable() {
		DynMapFeatures.removeAllBorders();
		Config.StopBorderTimer();
		Config.StoreFillTask();
		Config.StopFillTask();

		combatLoggerManager.handleOnDisable();
		gameManager.handleOnDisable();
		practiceManager.handleOnDisable();
		spectatorManager.handleOnDisable();
		vanishManager.handleOnDisable();
		mobStackManager.handleOnDisable();
	}

	private void registerConfigs() {
		mainConfig = new ConfigFile(this, "config.yml");
		utiltiesConfig = new ConfigFile(this, "utilities.yml");
	}

	private void setupWorld() {
		Bukkit.getScheduler().runTaskLater(this, () -> {
			new WorldCreator(true, gameManager.isWorld() ? true : false);

			World uhc = Bukkit.getWorld("uhc_world");
			uhc.setGameRuleValue("doDaylightCycle", "false");
			uhc.setTime(0);
            uhc.setGameRuleValue("doFireTick", "false");
            uhc.setGameRuleValue("naturalRegeneration", "false");

			World uhcNether = Bukkit.getWorld("world_nether");
			uhcNether.setGameRuleValue("doDaylightCycle", "false");
			uhcNether.setTime(0);
			uhcNether.setGameRuleValue("naturalRegeneration", "false");
		}, 60L);
	}

	private void registerCommands() {
		getCommand("wborder").setExecutor(wbCommand);
	}

	private void registerManagers() {
		new CommandHandler(this);

		borderManager = new BorderManager(this);
		combatLoggerManager = new CombatLoggerManager(this);
		gameManager = new GameManager(this);
		glassManager = new GlassManager(this);
		inventoryManager = new InventoryManager(this);
		mobStackManager = new MobStackManager(this);
		optionManager = new OptionManager(this);
		partyManager = new PartyManager(this);
		playerManager = new PlayerManager(this);
		practiceManager = new PracticeManager(this);
		requestManager = new RequestManager(this);
		scenarioManager = new ScenarioManager(this);
		spectatorManager = new SpectatorManager(this);
		vanishManager = new VanishManager(this);
		horseManager = new HorseManager(this);
		databaseManager = new DatabaseManager(this);

	}

	private void registerListeners() {
		Bukkit.getPluginManager().registerEvents(new ScenarioListeners(), this);
		new DotsTask();

		UHCUtils.getClassesInPackage(this, "com.thesevenq.uhc.listeners").stream().filter(BaseListener.class::isAssignableFrom).forEach(clazz -> {
			try {
				Bukkit.getPluginManager().registerEvents((Listener) clazz.newInstance(), this);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
	}

	private void registerItems() {
		ItemStack stack = new ItemStack(Material.GOLDEN_APPLE, 1);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(Color.translate("&5&lGolden Head"));

		List<String> lore = new ArrayList<>();
		lore.add(Color.translate("&5Some say consuming the head of a"));
		lore.add(Color.translate("&5fallen foe strengthens the blood"));

		meta.setLore(lore);
		stack.setItemMeta(meta);

		ShapedRecipe shapedRecipe = new ShapedRecipe(stack);

		shapedRecipe.shape("EEE", "ERE", "EEE");
		shapedRecipe.setIngredient('E', Material.GOLD_INGOT).setIngredient('R', Material.SKULL_ITEM, 3);

		Bukkit.addRecipe(shapedRecipe);
		ShapelessRecipe glmelon = new ShapelessRecipe(new ItemStack(Material.SPECKLED_MELON, 1));

		glmelon.addIngredient(Material.GOLD_BLOCK);
		glmelon.addIngredient(Material.MELON);

		Bukkit.addRecipe(glmelon);
	}
}