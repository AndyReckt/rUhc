package com.thesevenq.uhc.player.deathlookup.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.client.model.UpdateOptions;
import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.player.deathlookup.DeathLookup;
import com.thesevenq.uhc.player.deathlookup.data.killer.ProfileFightKiller;
import com.thesevenq.uhc.utilties.StringUtils;
import com.thesevenq.uhc.utilties.inventory.InventorySerialisation;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import com.thesevenq.uhc.player.deathlookup.data.killer.type.ProfileFightEnvironmentKiller;
import com.thesevenq.uhc.player.deathlookup.data.killer.type.ProfileFightPlayerKiller;


import java.util.*;

import static com.mongodb.client.model.Filters.eq;

@Getter
@Setter
public class DeathData {

    @Getter private static Map<String, DeathData> profiles = new HashMap<>();

    private List<PotionEffect> cachedEffects;
    private List<ProfileFight> fights;
    private LinkedHashMap<UUID, Boolean> previousFights;
    private String name;
    private DeathLookup deathLookup;
    private Map.Entry<UUID, ItemStack> lastDamager;

    private boolean loaded;

    public DeathData(String name) {
        this.name = name;
        this.cachedEffects = new ArrayList<>();
        this.fights = new ArrayList<>();
        this.previousFights = new LinkedHashMap<>();

        profiles.put(name, this);
    }

    public ProfileFight getLatestFight() {
        if (!(this.fights.isEmpty())) {
            return this.fights.get(this.fights.size() - 1);
        }

        return null;
    }

    private void saveFights() {
        for (ProfileFight fight : this.fights) {
            if (!(this.previousFights.containsKey(fight.getUuid()))) {
                this.previousFights.put(fight.getUuid(), fight.getKiller() instanceof ProfileFightPlayerKiller && ((ProfileFightPlayerKiller) fight.getKiller()).getName().equals(name));
            }

            if (fight.getKiller() instanceof ProfileFightPlayerKiller && ((ProfileFightPlayerKiller) fight.getKiller()).getName().equals(this.name)) {
                continue;
            }

            Document document = new Document();
            document.put("uuid", fight.getUuid().toString());
            document.put("killed", this.name);
            document.put("ping", fight.getPing());
            document.put("occurred_at", fight.getOccurredAt());
            document.put("health", 0);
            document.put("hunger", fight.getHunger());
            document.put("location", StringUtils.stringifyLocation(fight.getLocation()));

            JsonArray effects = new JsonArray();

            for (ProfileFightEffect effect : fight.getEffects()) {
                JsonObject effectObject = new JsonObject();
                effectObject.addProperty("type", effect.getType().getName());
                effectObject.addProperty("level", effect.getLevel());
                effectObject.addProperty("duration", effect.getDuration());
                effects.add(effectObject);
            }

            document.put("effects", effects.toString());

            Document killerDocument = new Document();

            if (fight.getKiller() instanceof ProfileFightPlayerKiller) {
                ProfileFightPlayerKiller killer = (ProfileFightPlayerKiller) fight.getKiller();

                killerDocument.put("type", "PLAYER");
                killerDocument.put("name", killer.getName());
                killerDocument.put("ping", killer.getPing());
                killerDocument.put("health", killer.getHealth());
                killerDocument.put("hunger", killer.getHunger());

                JsonArray killerEffects = new JsonArray();

                for (ProfileFightEffect effect : killer.getEffects()) {
                    JsonObject effectObject = new JsonObject();
                    effectObject.addProperty("type", effect.getType().getName());
                    effectObject.addProperty("level", effect.getLevel());
                    effectObject.addProperty("duration", effect.getDuration());
                    killerEffects.add(effectObject);
                }

                killerDocument.put("effects", killerEffects.toString());
                killerDocument.put("contents", InventorySerialisation.itemStackArrayToJson(killer.getContents()));
                killerDocument.put("armor", InventorySerialisation.itemStackArrayToJson(killer.getArmor()));

                document.put("killer_name", killer.getPing());
            }
            else if (fight.getKiller() instanceof ProfileFightEnvironmentKiller) {
                ProfileFightEnvironmentKiller killer = (ProfileFightEnvironmentKiller) fight.getKiller();

                killerDocument.put("type", killer.getType().name());
            }
            else {
                ProfileFightKiller killer = fight.getKiller();

                killerDocument.put("type", "MOB");
                killerDocument.put("mob_type", killer.getEntityType().name());
                killerDocument.put("name", killer.getName());
            }

            document.put("killer", killerDocument);
            document.put("contents", InventorySerialisation.itemStackArrayToJson(fight.getContents()));
            document.put("armor", InventorySerialisation.itemStackArrayToJson(fight.getArmor()));

            UHC.getInstance().getDatabaseManager().getUhcDeaths().replaceOne(eq("uuid", fight.getUuid().toString()), document, new UpdateOptions().upsert(true));
        }

        this.fights.clear();
    }

    public void save() {
        Document document = new Document();
        document.put("name", name.toLowerCase());

        JsonArray fightsArray = new JsonArray();

        for (UUID fight : this.previousFights.keySet()) {
            JsonObject object = new JsonObject();
            object.addProperty("uuid", fight.toString());
            object.addProperty("killer", this.previousFights.get(fight));
            fightsArray.add(object);
        }

        for (ProfileFight fight : this.fights) {
            JsonObject object = new JsonObject();
            object.addProperty("uuid", fight.getUuid().toString());
            object.addProperty("killer", (fight.getKiller() instanceof ProfileFightPlayerKiller && fight.getKiller().getName().equals(name)));
            fightsArray.add(object);
        }

        if (fightsArray.size() > 0) {
            document.put("fights", fightsArray.toString());
        }

        UHC.getInstance().getDatabaseManager().getUhcDeaths().replaceOne(eq("name", this.name.toLowerCase()), document, new UpdateOptions().upsert(true));

        this.saveFights();
        this.profiles.remove(name);
    }

    public void load() {
        Document document = (Document) UHC.getInstance().getDatabaseManager().getUhcDeaths().find(eq("name", name.toLowerCase())).first();

        if (document != null) {
            if (document.containsKey("fights")) {
                if (document.get("fights") instanceof String) {
                    JsonArray fightsArray = new JsonParser().parse(document.getString("fights")).getAsJsonArray();

                    for (JsonElement jsonElement : fightsArray) {
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        try {previousFights.put(UUID.fromString(jsonObject.get("uuid").getAsString()), jsonObject.get("killer").getAsBoolean());} catch (Exception ex) {ex.printStackTrace();}
                    }
                }
            }
        }

        loaded = true;
    }

    public static DeathData getByName(String name) {
        DeathData data = profiles.get(name);

        return data == null ? new DeathData(name) : data;
    }
}