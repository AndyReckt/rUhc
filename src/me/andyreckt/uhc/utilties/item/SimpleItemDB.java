package me.andyreckt.uhc.utilties.item;

import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;
import me.andyreckt.uhc.utilties.NumberUtils;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.TObjectShortMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import gnu.trove.map.hash.TObjectShortHashMap;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SimpleItemDB implements ItemDB {
	public TObjectIntMap<String> getItems() {
		return items;
	}

	public TreeMultimap<ItemData, String> getNames() {
		return names;
	}

	public Map<ItemData, String> getPrimaryName() {
		return primaryName;
	}

	public TObjectShortMap<String> getDurabilities() {
		return durabilities;
	}

	public ManagedFile getFile() {
		return file;
	}

	public Pattern getSplitPattern() {
		return splitPattern;
	}

	public static Comparator<String> getComparator() {
		return comparator;
	}

	public static Pattern getPattern() {
		return pattern;
	}

	private TObjectIntMap<String> items;
	private TreeMultimap<ItemData, String> names;
	private Map<ItemData, String> primaryName;
	private TObjectShortMap<String> durabilities;
	private ManagedFile file;
	private Pattern splitPattern;
	
	private static Comparator<String> comparator;
	private static Pattern pattern;

	static {
		comparator = new Comparator<String>() {
			
			@Override
			public int compare(String o1, String o2) {
				return o1.length() - o2.length();
			}
		};
		
		pattern = Pattern.compile("[^a-z0-9]");
	}

	public SimpleItemDB(JavaPlugin plugin) {
		this.items = new TObjectIntHashMap<>();
		this.names = TreeMultimap.create(Ordering.arbitrary(), comparator);
		this.primaryName = new HashMap<>();
		this.durabilities = new TObjectShortHashMap<>();
		this.splitPattern = Pattern.compile("((.*)[:+',;.](\\d+))");
		this.file = new ManagedFile("items.csv", plugin);
		
		this.reloadItemDatabase();
	}

	@Override
	public void reloadItemDatabase() {
		if(this.file.getFile() == null) return;
		
		List<String> lines = this.file.getLines();
		
		if(lines.isEmpty()) return;
		
		this.durabilities.clear();
		this.items.clear();
		this.names.clear();
		this.primaryName.clear();
		
		for(String line : lines) {
			line = line.trim().toLowerCase(Locale.ENGLISH);
			
			if(line.length() > 0 && line.charAt(0) == '#') {
				continue;
			}
			
			String[] parts = SimpleItemDB.pattern.split(line);
			
			if(parts.length < 2) {
				continue;
			}
			
			Material material;
			
			try {
				int numeric = Integer.parseInt(parts[1]);
				
				material = Material.getMaterial(numeric);
			} catch(IllegalArgumentException ex) {
				material = Material.getMaterial(parts[1]);
			}
			
			short data = (parts.length > 2 && !parts[2].equals("0")) ? Short.parseShort(parts[2]) : 0;
			String itemName = parts[0].toLowerCase(Locale.ENGLISH);
			
			this.durabilities.put(itemName, data);
			this.items.put(itemName, material.getId());
			
			ItemData itemData = new ItemData(material, data);
			
			if(this.names.containsKey(itemData)) {
				this.names.get(itemData).add(itemName);
			} else {
				this.names.put(itemData, itemName);
				this.primaryName.put(itemData, itemName);
			}
		}
	}

	@Override
	public ItemStack getPotion(String id) {
		return this.getPotion(id, 1);
	}

	@Override
	public ItemStack getPotion(String id, int quantity) {
		int length = id.length();
		
		if(length <= 1) return null;
		
		boolean splash = false;
		
		if(length > 1 && id.endsWith("s")) {
			id = id.substring(0, --length);
			splash = true;
			
			if(length <= 1) return null;
		}
		
		boolean extended = false;
		
		if(id.endsWith("e")) {
			id = id.substring(0, --length);
			extended = true;
			
			if(length <= 1) return null;
		}
		
		Integer level = NumberUtils.tryParseInt(id.substring(length - 1, length));
		id = id.substring(0, --length);
		String lowerCase = id.toLowerCase(Locale.ENGLISH);
		
		PotionType type = null;
		
		switch(lowerCase) {
			case "hp": {
				type = PotionType.FIRE_RESISTANCE;
				break;
			}
			
			case "rp": {
				type = PotionType.REGEN;
				break;
			}
			
			case "dp": {
				type = PotionType.INSTANT_DAMAGE;
				break;
			}
			
			case "swp": {
				type = PotionType.SPEED;
				break;
			}
			
			case "slp": {
				type = PotionType.SLOWNESS;
				break;
			}
			
			case "strp": {
				type = PotionType.STRENGTH;
				break;
			}
			
			case "wp": {
				type = PotionType.WEAKNESS;
				break;
			}
			
			case "pp": {
				type = PotionType.POISON;
				break;
			}
			
			case "frp": {
				type = PotionType.FIRE_RESISTANCE;
				break;
			}
			
			case "invp": {
				type = PotionType.INVISIBILITY;
				break;
			}
			
			case "nvp": {
				type = PotionType.NIGHT_VISION;
				break;
			} default: {
				return null;
			}
		}
		if(level == null || level > type.getMaxLevel()) return null;
		
		Potion potion = new Potion(type);
		
		potion.setLevel(level);
		potion.setSplash(splash);
		potion.setHasExtendedDuration(extended);
		
		ItemStack result = potion.toItemStack(quantity);
		
		result.setDurability((short) (result.getDurability() + 8192));
		return result;
	}

	@Override
	public ItemStack getItem(String id) {
		ItemStack result = this.getItem(id, 1);
		
		if(result == null) return null;
		
		result.setAmount(result.getMaxStackSize());
		return result;
	}

	@Override
	public ItemStack getItem(String id, int quantity) {
		ItemStack result = this.getPotion(id, quantity);
		
		if(result != null) return result;
		
		int itemId = 0;
		short metaData = 0;
		
		Matcher parts = this.splitPattern.matcher(id);
		String itemName;
		
		if(parts.matches()) {
			itemName = parts.group(2);
			metaData = Short.parseShort(parts.group(3));
		} else {
			itemName = id;
		}
		
		Integer last;
		
		if((last = NumberUtils.tryParseInt(itemName)) != null) {
			itemId = last;
		} else if((last = NumberUtils.tryParseInt(id)) != null) {
			itemId = last;
		} else {
			itemName = itemName.toLowerCase(Locale.ENGLISH);
		}
		
		if(itemId < 1) {
			if(this.items.containsKey(itemName)) {
				itemId = this.items.get(itemName);
				
				if(this.durabilities.containsKey(itemName) && metaData == 0) {
					metaData = this.durabilities.get(itemName);
				}
			} else if(Material.getMaterial(itemName.toUpperCase(Locale.ENGLISH)) != null) {
				Material bMaterial = Material.getMaterial(itemName.toUpperCase(Locale.ENGLISH));
				
				itemId = bMaterial.getId();
			} else {
				try {
					Material bMaterial = Bukkit.getUnsafe().getMaterialFromInternalName(itemName.toLowerCase(Locale.ENGLISH));
					itemId = bMaterial.getId();
				} catch(Exception ex) {
					return null;
				}
			}
		}
		
		if(itemId < 1) return null;
		
		Material mat = Material.getMaterial(itemId);
		
		if(mat == null) return null;
		
		result = new ItemStack(mat);
		
		result.setAmount(quantity);
		result.setDurability(metaData);
		return result;
	}

	@Override
	public List<ItemStack> getMatching(Player player, String[] args) {
		List<ItemStack> items = new ArrayList<ItemStack>();
		PlayerInventory inventory = player.getInventory();
		
		if(args.length < 1 || args[0].equalsIgnoreCase("hand")) {
			items.add(player.getItemInHand());
		} else if(args[0].equalsIgnoreCase("inventory") || args[0].equalsIgnoreCase("invent") || args[0].equalsIgnoreCase("all")) {
			for(ItemStack stack : inventory.getContents()) {
				if(stack != null && stack.getType() != Material.AIR) {
					items.add(stack);
				}
			}
		} else if(args[0].equalsIgnoreCase("blocks")) {
			for(ItemStack stack : inventory.getContents()) {
				if(stack != null && stack.getType() != Material.AIR && stack.getType().isBlock()) {
					items.add(stack);
				}
			}
		} else {
			items.add(this.getItem(args[0]));
		}
		if(items.isEmpty() || items.get(0).getType() == Material.AIR) {
			return null;
		}
		
		return items;
	}

	@Override
	public String getName(ItemStack item) {
		return CraftItemStack.asNMSCopy(item).getName();
	}

	@Deprecated
	@Override
	public String getPrimaryName(ItemStack item) {
		ItemData itemData = new ItemData(item.getType(), item.getDurability());
		String name = this.primaryName.get(itemData);
		
		if(name == null) {
			itemData = new ItemData(item.getType(), (short) 0);
			name = this.primaryName.get(itemData);
			
			if(name == null) return null;
			
		}
		
		return name;
	}

	@Override
	public String getNames(ItemStack item) {
		ItemData itemData = new ItemData(item.getType(), item.getDurability());
		Collection<String> nameList = this.names.get(itemData);
		
		if(nameList == null) {
			itemData = new ItemData(item.getType(), (short) 0);
			nameList = this.names.get(itemData);
			
			if(nameList == null) return null;
		}
		
		List<String> list = new ArrayList<String>(nameList);
		
		if(nameList.size() > 15) {
			list = list.subList(0, 14);
		}
		
		return StringUtils.join(list, ", ");
	}
}