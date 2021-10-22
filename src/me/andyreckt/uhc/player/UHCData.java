package me.andyreckt.uhc.player;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import me.andyreckt.uhc.UHC;

import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UHCData {
	public static void setUhcDatas(Map<String, UHCData> uhcDatas) {
		UHCData.uhcDatas = uhcDatas;
	}

	public void setName(String name) {
		this.name = name;
		this.save();
	}

	public void setRealName(String realName) {
		this.realName = realName;
		this.save();
	}

	public void setKills(int kills) {
		this.kills = kills;
		this.save();
	}

	public void setLevel(int level) {
		this.level = level;
		this.save();
	}

	public void setDiamondsMined(int diamondsMined) {
		this.diamondsMined = diamondsMined;
		this.save();
	}

	public void setSpawnersMined(int spawnersMined) {
		this.spawnersMined = spawnersMined;
		this.save();
	}

	public void setArmor(ItemStack[] armor) {
		this.armor = armor;
		this.save();
	}

	public void setItems(ItemStack[] items) {
		this.items = items;
		this.save();
	}

	public void setKillCount(List<String> killCount) {
		this.killCount = killCount;
		this.save();
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
		this.save();
	}

	public void setTotalKills(int totalKills) {
		this.totalKills = totalKills;
		this.save();
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
		this.save();
	}

	public void setWins(int wins) {
		this.wins = wins;
		this.save();
	}

	public void setKillStreak(int killStreak) {
		this.killStreak = killStreak;
		this.save();
	}

	public void setPlayed(int played) {
		this.played = played;
		this.save();
	}

	public void setTotalDiamondsMined(int totalDiamondsMined) {
		this.totalDiamondsMined = totalDiamondsMined;
		this.save();
	}

	public void setRespawnLocation(Location respawnLocation) {
		this.respawnLocation = respawnLocation;
		this.save();
	}

	public void setHideLocation(boolean hideLocation) {
		this.hideLocation = hideLocation;
		this.save();
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
		this.save();
	}

	public static Map<String, UHCData> getUhcDatas() {
		return uhcDatas;
	}

	public String getName() {
		return name;
	}

	public String getRealName() {
		return realName;
	}

	public int getKills() {
		return kills;
	}

	public int getLevel() {
		return level;
	}

	public int getDiamondsMined() {
		return diamondsMined;
	}

	public int getSpawnersMined() {
		return spawnersMined;
	}

	public ItemStack[] getArmor() {
		return armor;
	}

	public ItemStack[] getItems() {
		return items;
	}

	public List<String> getKillCount() {
		return killCount;
	}

	public boolean isAlive() {
		return alive;
	}

	public int getTotalKills() {
		return totalKills;
	}

	public int getDeaths() {
		return deaths;
	}

	public int getWins() {
		return wins;
	}

	public int getKillStreak() {
		return killStreak;
	}

	public int getPlayed() {
		return played;
	}

	public int getTotalDiamondsMined() {
		return totalDiamondsMined;
	}

	public Location getRespawnLocation() {
		return respawnLocation;
	}

	public boolean isHideLocation() {
		return hideLocation;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public static Map<String, UHCData> uhcDatas = new HashMap<>();

	private String name;
	private String realName;

	private int kills = 0;
	private int level = 0;
	private int diamondsMined = 0;
	private int spawnersMined = 0;
	
    private ItemStack[] armor;
    private ItemStack[] items;
    
    private List<String> killCount = new ArrayList<>();
    
	private boolean alive = false;

	private int totalKills = 0;
	private int deaths = 0;
	private int wins = 0;
	private int killStreak = 0;
	private int played = 0;
	private int totalDiamondsMined = 0;

    private Location respawnLocation;
    private boolean hideLocation = false;

    private boolean loaded;

	public UHCData(String name) {
		this.name = name;

		this.load();
		uhcDatas.put(name, this);
	}

	public void save() {
		if(!loaded) return;

		Document document = new Document();
		document.put("name", name.toLowerCase());
		document.put("realName", name);

		document.put("kills", this.totalKills);
		document.put("deaths", this.deaths);
		document.put("wins", this.wins);
		document.put("killStreak", this.killStreak);
		document.put("played", this.played);
		document.put("diamonds", this.totalDiamondsMined);

		UHC.getInstance().getDatabaseManager().getUhcProfiles().replaceOne(Filters.eq("name", this.name.toLowerCase()), document, new UpdateOptions().upsert(true));

		loaded = false;
	}

	public void load() {
		Document document = (Document) UHC.getInstance().getDatabaseManager().getUhcProfiles().find(Filters.eq("name", this.name.toLowerCase())).first();

		if(document != null) {
			this.realName = document.getString("realName");
			this.totalKills = document.getInteger("kills");
			this.deaths = document.getInteger("deaths");
			this.wins = document.getInteger("wins");
			this.killStreak = document.getInteger("killStreak");
			this.played = document.getInteger("played");
			this.totalDiamondsMined = document.getInteger("diamonds");
		} else {
			Document doc = new Document();
			doc.append("name", this.name.toLowerCase())
					.append("realName", this.name)
					.append("kills", 0)
					.append("deaths", 0)
					.append("wins", 0)
					.append("killStreak", 0)
					.append("played", 0)
					.append("diamonds", 0);
			UHC.getInstance().getDatabaseManager().getUhcProfiles().insertOne(doc);
			this.realName = this.name;
			this.totalKills = 0;
			this.deaths = 0;
			this.wins = 0;
			this.killStreak = 0;
			this.played = 0;
			this.totalDiamondsMined = 0;
		}

		loaded = true;
	}

	public static UHCData getByName(String name) {

		UHCData data = uhcDatas.get(name);
		return data == null ? new UHCData(name) : uhcDatas.get(name);
	}
    
    public double getKD() {
        double kd;
        
        if(this.totalKills > 0 && this.deaths == 0) {
            kd = this.totalKills;
        } else if(this.totalKills == 0 && this.deaths == 0) {
            kd = 0.0;
        } else {
            kd = this.totalKills / this.deaths;
        }
        
        return kd;
    }

	public boolean isNotAlive() {
		return !alive;
	}
}