package me.andyreckt.uhc;

import me.andyreckt.uhc.adapters.board.Assemble;
import me.andyreckt.uhc.adapters.board.AssembleStyle;
import me.andyreckt.uhc.backend.DatabaseManager;
import me.andyreckt.uhc.listeners.CustomMovementHandler;
import me.andyreckt.uhc.listeners.PlayerListener;
import me.andyreckt.uhc.managers.*;
import me.andyreckt.uhc.providers.BoardAdapter;
import me.andyreckt.uhc.providers.TabAdapter;
import me.andyreckt.uhc.scenario.ScenarioListeners;
import me.andyreckt.uhc.tasks.DotsTask;
import me.andyreckt.uhc.utilties.*;
import me.andyreckt.uhc.utilties.command.UHCCommandHandler;
import me.andyreckt.uhc.utilties.item.ItemDB;
import me.andyreckt.uhc.utilties.item.SimpleItemDB;
import io.github.thatkawaiisam.ziggurat.Ziggurat;
import me.andyreckt.uhc.border.worldborder.Config;
import me.andyreckt.uhc.border.worldborder.DynMapFeatures;
import me.andyreckt.uhc.border.worldborder.commands.WBCommand;
import me.andyreckt.uhc.commands.CommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import me.andyreckt.uhc.utilties.BaseListener;
import me.andyreckt.uhc.utilties.UHCUtils;
import me.andyreckt.uhc.utilties.WorldCreator;


import java.util.ArrayList;
import java.util.List;


public class UHC extends JavaPlugin {


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
		registerTabAndScoreboard();

		UHCCommandHandler.hook();
		this.itemDB = new SimpleItemDB(this);

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
		PlayerListener.getInstance().onServerShutdown();
	}

	private void registerConfigs() {
		mainConfig = new ConfigFile(this, "config.yml");
		utiltiesConfig = new ConfigFile(this, "utilities.yml");
	}

	private void setupWorld() {
		Bukkit.getScheduler().runTaskLater(this, () -> {
			new WorldCreator(true, true); // true, gameManager.isWorld()
		}, 60L);
		Bukkit.getScheduler().runTaskLater(this, () -> {

			World uhc = Bukkit.getWorld("uhc_world");
			uhc.setTime(0);
            uhc.setGameRuleValue("doFireTick", "false");
            uhc.setGameRuleValue("naturalRegeneration", "false");
			uhc.setGameRuleValue("doDaylightCycle", "false");

			World uhcNether = Bukkit.getWorld("uhc_nether");
			uhcNether.setTime(0);
			uhcNether.setGameRuleValue("naturalRegeneration", "false");
			uhcNether.setGameRuleValue("doDaylightCycle", "false");
		}, 120L);
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

		UHCUtils.getClassesInPackage(this, "me.andyreckt.uhc.listeners").stream().filter(BaseListener.class::isAssignableFrom).forEach(clazz -> {
			try {
				Bukkit.getPluginManager().registerEvents((Listener) clazz.newInstance(), this);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		Bukkit.getPluginManager().registerEvents(new CustomMovementHandler(), this);
	}

	private void registerTabAndScoreboard() {
		Assemble assemble = new Assemble(this, new BoardAdapter());
		new Ziggurat(this, new TabAdapter());
		assemble.setTicks(2);
		assemble.setAssembleStyle(AssembleStyle.MODERN);
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

		ShapelessRecipe string = new ShapelessRecipe(new ItemStack(Material.STRING, 1));
		string.addIngredient(4, Material.WOOL);

		Bukkit.addRecipe(string);
	}

	public static UHC getInstance() {
		return instance;
	}

	public WBCommand getWbCommand() {
		return wbCommand;
	}

	public BorderManager getBorderManager() {
		return borderManager;
	}

	public CombatLoggerManager getCombatLoggerManager() {
		return combatLoggerManager;
	}

	public GameManager getGameManager() {
		return gameManager;
	}

	public GlassManager getGlassManager() {
		return glassManager;
	}

	public InventoryManager getInventoryManager() {
		return inventoryManager;
	}

	public MobStackManager getMobStackManager() {
		return mobStackManager;
	}

	public OptionManager getOptionManager() {
		return optionManager;
	}

	public PartyManager getPartyManager() {
		return partyManager;
	}

	public PlayerManager getPlayerManager() {
		return playerManager;
	}

	public PracticeManager getPracticeManager() {
		return practiceManager;
	}

	public RequestManager getRequestManager() {
		return requestManager;
	}

	public ScenarioManager getScenarioManager() {
		return scenarioManager;
	}

	public SpectatorManager getSpectatorManager() {
		return spectatorManager;
	}

	public VanishManager getVanishManager() {
		return vanishManager;
	}

	public HorseManager getHorseManager() {
		return horseManager;
	}

	public DatabaseManager getDatabaseManager() {
		return databaseManager;
	}

	public ItemDB getItemDB() {
		return itemDB;
	}

	public ConfigFile getMainConfig() {
		return mainConfig;
	}

	public ConfigFile getUtiltiesConfig() {
		return utiltiesConfig;
	}
}