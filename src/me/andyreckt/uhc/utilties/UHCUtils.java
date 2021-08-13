package me.andyreckt.uhc.utilties;

import com.google.common.collect.ImmutableSet;
import me.andyreckt.uhc.managers.BorderManager;
import me.andyreckt.uhc.managers.GameManager;
import me.andyreckt.uhc.managers.OptionManager;
import me.andyreckt.uhc.managers.PartyManager;
import me.andyreckt.uhc.utilties.item.ItemBuilder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.listeners.VanishListener;
import me.andyreckt.uhc.player.party.Party;
import me.andyreckt.uhc.player.UHCData;
import me.andyreckt.uhc.player.state.GameState;

import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class UHCUtils {
	
	private static int teamColorNumber = 0;

	public static void clearPlayer(Player player) {
		player.setHealth(20.0D);
		player.setFoodLevel(20);
		player.setSaturation(12.8F);
		player.setMaximumNoDamageTicks(20);
		player.setFireTicks(0);
		player.setFallDistance(0.0F);
		player.setLevel(0);
		player.setExp(0.0F);
		player.setWalkSpeed(0.2F);
		player.getInventory().setHeldItemSlot(0);
		player.setAllowFlight(false);
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		player.closeInventory();
		player.setGameMode(GameMode.SURVIVAL);
		player.getActivePotionEffects().stream().map(PotionEffect::getType).forEach(player::removePotionEffect);
		((CraftPlayer) player).getHandle().getDataWatcher().watch(9, (byte) 0);
		player.updateInventory();
	}

	public static void spawnHead(Player player) {
		player.getLocation().getBlock().setType(Material.NETHER_FENCE);
		player.getWorld().getBlockAt(player.getLocation().add(0.0D, 1.0D, 0.0D)).setType(Material.SKULL);

		Skull skull = (Skull) player.getLocation().add(0.0D, 1.0D, 0.0D).getBlock().getState();

		skull.setOwner(player.getName());
		skull.update();

		Block block = player.getLocation().add(0.0D, 1.0D, 0.0D).getBlock();
		block.setData((byte) 1);
	}

	public static void scatterPlayer(Player player) {
        UHCData uhcData = UHCData.getByName(player.getName());
        
        UHCUtils.disableSpecMode(player);
                
        player.setFallDistance(0.0f);
        
        uhcData.setAlive(true);
        
        player.setFoodLevel(20);
        player.setLevel(0);
        player.setHealth(20.0);
        
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        
        player.setGameMode(GameMode.SURVIVAL);
        player.setLevel(0);
        player.setAllowFlight(false);
                
		try {
			String items = UHC.getInstance().getUtiltiesConfig().getString("uhcitems.inventory");

			player.getInventory().setContents(InventoryUtils.fromBase64(items).getContents());
	        player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, OptionManager.getByNameAndTranslate("Starter Food")));

			player.updateInventory();
		} catch(IOException e) {
			e.printStackTrace();
		}
		        
        if(PartyManager.isEnabled()) {
            Party party = PartyManager.getByPlayer(player);
            
            if(party == null) {
				UHC.getInstance().getPartyManager().handleCreateParty(player);

				party = PartyManager.getByPlayer(player);
				
				Location teamLoc = party.getScatterLocation();
				
				player.teleport(teamLoc);
				player.setHealth(20.0);
				player.teleport(teamLoc);
				player.setHealth(20.0);
				
				if(!GameManager.getGameState().equals(GameState.PLAYING)) {
					UHC.getInstance().getHorseManager().sitPlayer(player);
				}
            } else {
				Location teamLoc = party.getScatterLocation();
				
				player.teleport(teamLoc);
				player.setHealth(20.0);
				player.teleport(teamLoc);
				player.setHealth(20.0);
				
				if(!GameManager.getGameState().equals(GameState.PLAYING)) {
					UHC.getInstance().getHorseManager().sitPlayer(player);
				}
            }
        } else {
			Location location = UHCUtils.getScatterLocation();
			
            player.teleport(location);
            player.setHealth(20.0);
            player.teleport(location);
            player.setHealth(20.0);
            
			if(!GameManager.getGameState().equals(GameState.PLAYING)) {
				UHC.getInstance().getHorseManager().sitPlayer(player);
			}
        }
                
        player.setFallDistance(0.0f);

		UHC.getInstance().getGameManager().setInitial(UHC.getInstance().getGameManager().getInitial() + 1);
	}

	public static int getNextBorder() {
		int border = BorderManager.border;

		/*if(border == 3000) {
			border = 2500;
		} else */if(border == 2500) {
			border = 2000;
		} else if(border == 2000) {
			border = 1500;
		} else if(border == 1500) {
			border = 1000;
		} else if(border == 1000) {
			border = 500;
		} else if(border == 500) {
			border = 100;
		} else if(border == 100) {
			border = 50;
		} else if(border == 50) {
			border = 25;
		}

		return border;
	}
	
	public static boolean maxSpecSize(Location location, int size) {
		return Math.abs(location.getBlockX()) <= size && Math.abs(location.getBlockZ()) <= size;
	}
	
	public static boolean isPlayerInSpecMode(Player player) {
		return UHC.getInstance().getSpectatorManager().getSpectators().containsKey(player.getUniqueId());
	}
	
    public static ChatColor[] validTeamColors = new ChatColor[] {
            ChatColor.DARK_GREEN, ChatColor.DARK_PURPLE, ChatColor.GOLD,
            ChatColor.GRAY, ChatColor.BLUE, ChatColor.GREEN, ChatColor.RED, ChatColor.LIGHT_PURPLE,
            ChatColor.YELLOW, ChatColor.WHITE
    };

	public static ChatColor getRandomTeamChatColor() {
        return validTeamColors[teamColorNumber++ % validTeamColors.length];
	}
	
	public static void clearUHC(Player player) {
		player.getInventory().clear();
		
		player.getInventory().setHelmet(new ItemStack(Material.AIR));
		player.getInventory().setChestplate(new ItemStack(Material.AIR));
		player.getInventory().setLeggings(new ItemStack(Material.AIR));
		player.getInventory().setBoots(new ItemStack(Material.AIR));
		
		player.setLevel(0);
		player.setFoodLevel(20);
		player.setSaturation(10);
		player.setHealth(20.0);
		player.setFireTicks(0);
		player.setGameMode(GameMode.SURVIVAL);
		
		for(PotionEffect effects : player.getActivePotionEffects()) {
			player.removePotionEffect(effects.getType());
		}
	}
	
	public static void disableSpecMode(Player player) {
		if(isPlayerInSpecMode(player)) {
			UHC.getInstance().getSpectatorManager().handleDisable(player);
		}
		
		if(VanishListener.isVanished(player)) {
			UHC.getInstance().getVanishManager().handleUnvanish(player);
		}
	}

	public static void loadLobby(Player player) {
		player.getInventory().setItem(0, new ItemBuilder(Material.BOOK).name("&7» &a&lGame Settings &7«").build());
		player.getInventory().setItem(4, new ItemBuilder(Material.DIAMOND_SWORD).name("&7» &e&lJoin Practice &7«").build());

		if(PartyManager.isEnabled()) {
			Party party = PartyManager.getByPlayer(player);

			if(party == null) {
				player.getInventory().setItem(1, new ItemBuilder(Material.NAME_TAG).name("&7» &e&lCreate Party &7«").build());
			} else {
				player.getInventory().setItem(1, new ItemBuilder(Material.SKULL_ITEM).name("&7» &e&lParty Information &7«").build());
				player.getInventory().setItem(7, new ItemBuilder(Material.REDSTONE).name("&7» &c&lLeave Party &7«").build());
			}
		}

		player.getInventory().setItem(8, new ItemBuilder(Material.EMERALD).name("&7» &d&lView Leaderboards &7«").build());
		//player.getInventory().setItem(8, new ItemBuilder(Material.WATCH).name("&9Edit Settings").build());

		/*player.getInventory().setItem(0,
				new ItemBuilder(Material.EMERALD).
				name("&7" + Msg.DOUBLE_ARROW_RIGHT + " &e&lLeaderboards &7" + Msg.DOUBLE_ARROW_LEFT).
				lore("&7Click this item to see leaderboards.").build());
		
		player.getInventory().setItem(4,
				new ItemBuilder(Material.BOOK).
				name("&7" + Msg.DOUBLE_ARROW_RIGHT + " &a&lUHC Settings &7" + Msg.DOUBLE_ARROW_LEFT).
				lore("&7Click this item to open uhc settings.").build());
		
		player.getInventory().setItem(8,
				new ItemBuilder(Material.DIAMOND_SWORD).
				name("&7" + Msg.DOUBLE_ARROW_RIGHT + " &e&lPractice Arena &7" + Msg.DOUBLE_ARROW_LEFT).
				lore("&7Click this item to join practice arena.").build());*/
		player.teleport(new Location(Bukkit.getWorld("spawn"), 0, 52, 0));
		player.setGameMode(GameMode.ADVENTURE);
		player.updateInventory();
	}
	
	public static ItemStack getYes() {		
		return new ItemBuilder(Material.STAINED_GLASS).amount(1).data(3).
				name("&a&lYes").
				lore("&eIf you want to play").
				lore("&ethis game you can easily").
				lore("&ebe scattered by clicking").
				lore("&ethis item").
				lore("").
				lore("&eIf PvP is &aEnabled&e you").
				lore("&ewill be set as spectator.").build();
	}
	
	public static ItemStack getNo() {		
		return new ItemBuilder(Material.STAINED_GLASS).amount(1).data(14).
				name("&c&lNo").
				lore("&eIf you want to spectate").
				lore("&ethis game you can easily").
				lore("&ebe spectator by clicking").
				lore("&ethis item").build();
	}
	
	public static void loadSpectatorInventory(Player player) {
		player.getInventory().setItem(0,
				new ItemBuilder(Material.COMPASS).
				name("&7" + Msg.DOUBLE_ARROW_RIGHT + " &a&lAlive Players &7" + Msg.DOUBLE_ARROW_LEFT).
				lore("&7Click this item to see alive players.").build());
		
		player.getInventory().setItem(4,
				new ItemBuilder(Material.WATCH).
				name("&7" + Msg.DOUBLE_ARROW_RIGHT + " &e&lRandom Teleport &7" + Msg.DOUBLE_ARROW_LEFT).
				lore("&7Click this item to random teleport.").build());
		/*
		if(player.hasPermission(Permission.STAFF_PERMISSION)) {
			player.getInventory().setItem(4,
					new ItemBuilder(Material.PACKED_ICE).
					name("&7" + Msg.DOUBLE_ARROW_RIGHT + " &d&lFreeze Player &7" + Msg.DOUBLE_ARROW_LEFT).
					lore("&7Click this item to freeze player.").build());
		}
		*/
		
		player.getInventory().setItem(8,
				new ItemBuilder(Material.BOOK).
				name("&7" + Msg.DOUBLE_ARROW_RIGHT + " &e&lUHC Settings &7" + Msg.DOUBLE_ARROW_LEFT).
				lore("&7Click this item to see uhc settings.").build());
		
		player.updateInventory();
	}
	
	public static ItemStack getGoldenHead() {
        return new ItemBuilder(Material.GOLDEN_APPLE).data(1).name("&5&lGolden Head").lore("&5Some say consuming the need of a").lore("&5fallenn foe strengthens the blood").build();
    }
    
    public static String isPartiesEnabled() {
		if(PartyManager.isEnabled()) {
			return "To" + UHC.getInstance().getPartyManager().getMaxSize();
		} else {
			return "FFA";
		}
    }
    
    public static String isUHCPracticeEnabled() {
  		if(UHC.getInstance().getPracticeManager() != null && UHC.getInstance().getPracticeManager().isOpen()) {
  			return "Enabled";
  		} else {
  			return "Disabled";
  		}
      }
    
    public static Location getSpawnLocation() {
        return new Location(Bukkit.getWorld("world"), 100, 100, 100);
    }

    public static Location getScatterLocation() {
        Random random = new Random();
        
        int x = random.nextInt(BorderManager.border * 2) - (BorderManager.border - 5);
        int z = random.nextInt(BorderManager.border * 2) - (BorderManager.border - 5);
        
        return new Location(Bukkit.getWorld("uhc_world"), x, (Bukkit.getWorld("uhc_world").getHighestBlockYAt(x, z) + 0.5), z);
    }
    
    public static List<Location> getSphere(Location centerBlock, int radius, boolean hollow) {
        List<Location> circleBlocks = new ArrayList<Location>();

        int bx = centerBlock.getBlockX();
        int by = centerBlock.getBlockY();
        int bz = centerBlock.getBlockZ();

        for(int x = bx - radius; x <= bx + radius; x++) {
            for(int y = by - radius; y <= by + radius; y++) {
                for(int z = bz - radius; z <= bz + radius; z++) {

                    double distance = ((bx-x) * (bx-x) + ((bz-z) * (bz-z)) + ((by-y) * (by-y)));

                    if(distance < radius * radius && !(hollow && distance < ((radius - 1) * (radius - 1)))) {

                        Location l = new Location(centerBlock.getWorld(), x, y, z);

                        circleBlocks.add(l);

                    }

                }
            }
        }

        return circleBlocks;
    }
    
    public static void flat(int size, World world) {
    	for(int x = -size; x < size; x++) {
    		for(int y = 59; y < 150; y++) {
    			for(int z = -size; z < size; z++) {
    				Location location = new Location(world, x, y, z);
    				location.getBlock().setType(Material.AIR);
    			}
    		}
    	}
    	
    	for(int x = -size; x < size; x++) {
    		for(int y = 59; y < 60; y++) {
    			for(int z = -size; z < size; z++) {
    				Location location = new Location(world, x, y, z);
    				location.getBlock().setType(Material.BEDROCK);
    			}
    		}
    	}
    	
    	for(int x = -size; x < size; x++) {
    		for(int y = 60; y < 61; y++) {
    			for(int z = -size; z < size; z++) {
    				Location location = new Location(world, x, y, z);
    				location.getBlock().setType(Material.GRASS);
    			}
    		}
    	}
    	
    	for(Player player : Bukkit.getOnlinePlayers()) {
    		player.teleport(BorderManager.teleportToTop(player.getLocation()));
    	}
    }
    
    public static void buildWalls(Material mat, int h, World w, int size, int size2) {
    	for(int x = -size2; x < size2; x++) {
    		for(int y = 59; y < 150; y++) {
    			for(int z = -size2; z < size2; z++) {
    				Location location = new Location(w, x, y, z);
    				if(location.getBlock().getType() == Material.WATER 
    					|| location.getBlock().getType() == Material.STATIONARY_WATER
    					|| location.getBlock().getType() == Material.LAVA
    					|| location.getBlock().getType() == Material.STATIONARY_LAVA) {
    					location.getBlock().setType(Material.AIR);
    				}
    			}
    		}
    	}
    	
        Location loc = new Location(w, 0, 59, 0);
        int i = h;
        while (i < h + h) {
            for (int x = loc.getBlockX() - size; x <= loc.getBlockX() + size; x++) {
                for (int y = 58; y <= 58; y++) {
                    for (int z = loc.getBlockZ() - size; z <= loc.getBlockZ() + size; z++) {
                        if ((x == loc.getBlockX() - size) || (x == loc.getBlockX() + size) || (z == loc.getBlockZ() - size) || (z == loc.getBlockZ() + size)) {
                            Location loc1 = new Location(w, x, y, z);
                            loc1.setY(w.getHighestBlockYAt(loc1));
                            loc1.getBlock().setType(mat);
                        }
                    }
                }
            }
            
            i++;
        }
    }

	public static Collection<Class<?>> getClassesInPackage(Plugin plugin, String packageName) {
		Collection<Class<?>> classes = new ArrayList<>();

		CodeSource codeSource = plugin.getClass().getProtectionDomain().getCodeSource();
		URL resource = codeSource.getLocation();
		String relPath = packageName.replace('.', '/');
		String resPath = resource.getPath().replace("%20", " ");
		String jarPath = resPath.replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "");
		JarFile jarFile;

		try {
			jarFile = new JarFile(jarPath);
		} catch (IOException e) {
			throw (new RuntimeException("Unexpected IOException reading JAR File '" + jarPath + "'", e));
		}

		Enumeration<JarEntry> entries = jarFile.entries();

		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			String entryName = entry.getName();
			String className = null;

			if(entryName.endsWith(".class") && entryName.startsWith(relPath) && entryName.length() > (relPath.length() + "/".length())) {
				className = entryName.replace('/', '.').replace('\\', '.').replace(".class", "");
			}

			if(className != null) {
				Class<?> clazz = null;

				try {
					clazz = Class.forName(className);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

				if (clazz != null) {
					classes.add(clazz);
				}
			}
		}

		try {
			jarFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return (ImmutableSet.copyOf(classes));
	}
}
