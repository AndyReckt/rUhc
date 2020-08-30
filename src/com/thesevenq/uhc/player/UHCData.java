package com.thesevenq.uhc.player;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.thesevenq.uhc.UHC;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class UHCData {

	@Getter
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
		if(alive) {
			return false;
		}

		return true;
	}
}