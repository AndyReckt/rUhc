package com.thesevenq.uhc.player.deathlookup;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.client.MongoCursor;
import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.utilties.StringUtils;
import com.thesevenq.uhc.utilties.inventory.InventorySerialisation;
import com.thesevenq.uhc.utilties.item.ItemBuilder;
import lombok.Getter;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import com.thesevenq.uhc.player.deathlookup.data.DeathData;
import com.thesevenq.uhc.player.deathlookup.data.ProfileFight;
import com.thesevenq.uhc.player.deathlookup.data.ProfileFightEffect;
import com.thesevenq.uhc.player.deathlookup.data.ProfileFightEnvironment;
import com.thesevenq.uhc.player.deathlookup.data.killer.ProfileFightKiller;
import com.thesevenq.uhc.player.deathlookup.data.killer.type.ProfileFightEnvironmentKiller;
import com.thesevenq.uhc.player.deathlookup.data.killer.type.ProfileFightPlayerKiller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;

/*
    This is in beta, not going to be making it configurable for quite some time.
 */
public class DeathLookup {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
    private static final int PAGE_SIZE = 9;
    private static final String INVENTORY_TITLE = ChatColor.RED + "Deaths - %PAGE%/%TOTAL%";

    @Getter private final DeathData deathData;
    @Getter private final DeathLookupData data;

    public DeathLookup(DeathData deathData) {
        this.deathData = deathData;
        this.data = new DeathLookupData();
    }

    public Inventory getDeathInventory(int page) {
        int total = (int) Math.ceil(getDeathsCount() / 9.0);

        if (total == 0) {
            total = 1;
        }

        Inventory toReturn = Bukkit.createInventory(null, 18, INVENTORY_TITLE.replace("%PLAYER%", deathData.getName()).replace("%PAGE%", page + "").replace("%TOTAL%", total + ""));
        toReturn.setItem(0, new ItemBuilder(Material.CARPET).durability(7).name(ChatColor.RED + "Previous Page").build());
        toReturn.setItem(8, new ItemBuilder(Material.CARPET).durability(7).name(ChatColor.RED + "Next Page").build());
        toReturn.setItem(4, new ItemBuilder(Material.PAPER).name(ChatColor.RED + "Page " + page + "/" + total).lore(Arrays.asList(ChatColor.YELLOW + "Player: " + ChatColor.RED + deathData.getName())).build());

        int count = 0;

        for (ProfileFight death : getDeaths(page)) {
            ItemBuilder builder = new ItemBuilder(Material.SKULL_ITEM).name(ChatColor.YELLOW + DATE_FORMAT.format(new Date(death.getOccurredAt())));

            List<String> lore = new ArrayList<>();
            lore.addAll(Arrays.asList(
                    "&7&m------------------------------",
                    "&eKilled by: &c" + (death.getKiller().getName() == null ? "Environment (" + WordUtils.capitalize(((ProfileFightEnvironmentKiller) death.getKiller()).getType().name().toLowerCase()) + ")" : death.getKiller().getName())
            ));

            if (!(death.getEffects().isEmpty())) {
                lore.add("&7&m------------------------------");
                lore.add("&cEffects:");
                for (ProfileFightEffect effect : death.getEffects()) {
                    String name = WordUtils.capitalize(effect.getType().getName().replace("_", " ").toLowerCase());
                    lore.add(" &e" + name + " " + toRomanNumeral(effect.getLevel()) + "&c for &e" + DurationFormatUtils.formatDuration(effect.getDuration(), "mm:ss") + "m");
                }
            }

            lore.add("&7&m------------------------------");

            builder.lore(lore);

            toReturn.setItem(PAGE_SIZE + count, builder.build());
            count++;
        }

        return toReturn;
    }

    public int getTotalPages() {
        int total = (int) Math.ceil(getDeathsCount() / 9.0);
        if (total == 0) {
            total = 1;
        }
        return total;
    }

    public long getDeathsCount() {
        return UHC.getInstance().getDatabaseManager().getUhcDeaths().count(eq("killed", deathData.getName())) + deathData.getFights().size();
    }

    public List<ProfileFight> getDeaths(int page) {
        List<ProfileFight> toReturn = new ArrayList<>();

        int toLimit = 0;
        for (ProfileFight fight : deathData.getFights()) {
            if (fight.getKiller() instanceof ProfileFightPlayerKiller && ((ProfileFightPlayerKiller) fight.getKiller()).getName().equals(deathData.getName())) {
                continue;
            }
            toReturn.add(fight);
            toLimit++;
        }

        MongoCursor cursor = UHC.getInstance().getDatabaseManager().getUhcDeaths().find(eq("killed", deathData.getName().toString())).skip((page - 1) * PAGE_SIZE).limit(PAGE_SIZE - toLimit).sort(descending("occurred_at")).iterator();
        while (cursor.hasNext()) {
            Document document = (Document) cursor.next();
            List<ProfileFightEffect> effects = new ArrayList<>();

            for (JsonElement effectElement : (JsonArray) new JsonParser().parse(document.getString("effects"))) {
                JsonObject effectObject = (JsonObject) effectElement;
                effects.add(new ProfileFightEffect(new PotionEffect(PotionEffectType.getByName(effectObject.get("type").getAsString()), effectObject.get("duration").getAsInt() / 1000 * 20, effectObject.get("level").getAsInt() - 1)));
            }

            try {
                Document killerDocument = (Document) document.get("killer");

                ProfileFightKiller killer;
                if (killerDocument.get("type").equals("PLAYER")) {
                    List<ProfileFightEffect> killerEffects = new ArrayList<>();

                    for (JsonElement effectElement : (JsonArray) new JsonParser().parse(killerDocument.getString("effects"))) {
                        JsonObject effectObject = (JsonObject) effectElement;
                        killerEffects.add(new ProfileFightEffect(new PotionEffect(PotionEffectType.getByName(effectObject.get("type").getAsString()), effectObject.get("duration").getAsInt() / 1000 * 20, effectObject.get("level").getAsInt() - 1)));
                    }

                    killer = new ProfileFightPlayerKiller(killerDocument.getString("name"), killerDocument.getInteger("ping"), InventorySerialisation.itemStackArrayFromJson(killerDocument.getString("contents")), InventorySerialisation.itemStackArrayFromJson(killerDocument.getString("armor")), killerDocument.getDouble("health"), killerDocument.getDouble("hunger"), killerEffects);
                } else if (killerDocument.get("type").equals("MOB")){
                    killer = new ProfileFightKiller(EntityType.valueOf(killerDocument.getString("mob_type")), killerDocument.getString("name"));
                } else {
                    killer = new ProfileFightEnvironmentKiller(ProfileFightEnvironment.valueOf(killerDocument.getString("type")));
                }

                toReturn.add(new ProfileFight(UUID.fromString(document.getString("uuid")), document.getInteger("ping"), document.getLong("occurred_at"), InventorySerialisation.itemStackArrayFromJson(document.getString("contents")), InventorySerialisation.itemStackArrayFromJson(document.getString("armor")), document.getDouble("hunger"), effects, killer, StringUtils.destringifyLocation(document.getString("location"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return toReturn;
    }

    public Inventory getFightItemInventory(ProfileFight fight) {
        int deathNumber = (int) (getDeathsCount() - (((getData().getPage() - 1) * 9) + getData().getIndex()));
        Inventory toReturn = Bukkit.createInventory(null, 9 * 6, ChatColor.RED + "Inventory #" + deathNumber);

        toReturn.setItem(0, new ItemBuilder(Material.CARPET).durability(7).name(ChatColor.RED + "Return").build());
        toReturn.setItem(8, new ItemBuilder(Material.CARPET).durability(7).name(ChatColor.RED + "Return").build());
        toReturn.setItem(4, new ItemBuilder(Material.PAPER).name(ChatColor.RED + "Inventory Contents").lore(Arrays.asList(ChatColor.YELLOW + "Player: " + ChatColor.RED + deathData.getName())).build());

        List<ItemStack> contents = new ArrayList<>(Arrays.asList(fight.getContents()));
        List<ItemStack> armor = new ArrayList<>(Arrays.asList(fight.getArmor()));

        for (int i = 0; i < contents.size(); i++) {
            if (i <= 8) {
                ItemStack item = contents.get(i);
                if (item != null) {
                    toReturn.setItem(i + 9, item);
                }
            }
        }

        for (int i = 0; i < contents.size(); i++) {
            if (i > 8) {
                ItemStack item = contents.get(i);
                if (item != null) {
                    int position = i;

                    if (position <= 17) {
                        position += 27;
                    } else if (position > 17 && position < 27) {
                        position += 9;
                    } else if (position >= 27) {
                        position -= 18;
                    }

                    while (toReturn.getItem(position) != null) {
                        position++;
                        if (position == toReturn.getSize()) break;
                    }

                    if (position != toReturn.getSize()) {
                        toReturn.setItem(position, item);
                    }
                }
            }
        }

        for (int i = 0; i < 2; i++) {
            toReturn.setItem(49 + i, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name(" ").build());
        }

        for (int i = 0; i < armor.size(); i++) {
            ItemStack item = armor.get(i);
            if (item != null && item.getType() != Material.AIR) {
                toReturn.setItem(45 + i, item);
            } else {
                toReturn.setItem(45 + i, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name(" ").build());
            }
        }

        List<String> lore = new ArrayList<>();
        if (!fight.getEffects().isEmpty()) {
            lore.add("&7&m------------------------------");
            for (ProfileFightEffect effect : fight.getEffects()) {
                String name = WordUtils.capitalize(effect.getType().getName().replace("_", " ").toLowerCase());
                lore.add("&e" + name + " " + toRomanNumeral(effect.getLevel()) + "&c for &e" + DurationFormatUtils.formatDuration(effect.getDuration(), "mm:ss") + "m");
            }
            lore.add("&7&m------------------------------");
        }

        ItemStack effects = new ItemBuilder(Material.POTION).name(ChatColor.RED + (fight.getEffects().isEmpty() ? "No Potion Effects" : fight.getEffects().size() + " Effect" + (fight.getEffects().size() == 1 ? "" : "s"))).lore(lore).build();

        toReturn.setItem(50, new ItemBuilder(Material.ANVIL).name(ChatColor.RED + "Rollback Inventory").build());
        toReturn.setItem(51, new ItemBuilder(Material.CHEST).name(ChatColor.RED + "Copy Inventory").build());
        toReturn.setItem(52, new ItemBuilder(Material.PUMPKIN_PIE).name(ChatColor.RED + "Food Level of " + ((int)fight.getHunger() / 2)).build());
        toReturn.setItem(53, effects);

        return toReturn;
    }

    public Inventory getFightInventory(ProfileFight fight) {
        int deathNumber = (int) (getDeathsCount() - (((getData().getPage() - 1) * 9) + getData().getIndex()));
        Inventory toReturn = Bukkit.createInventory(null, 18, ChatColor.RED + "Death #" + deathNumber);

        toReturn.setItem(0, new ItemBuilder(Material.CARPET).durability(7).name(ChatColor.RED + "Previous Death").build());
        toReturn.setItem(8, new ItemBuilder(Material.CARPET).durability(7).name(ChatColor.RED + "Next Death").build());
        toReturn.setItem(4, new ItemBuilder(Material.PAPER).name(ChatColor.RED + "Death Information").lore(Arrays.asList(ChatColor.YELLOW + "Player: " + ChatColor.RED + deathData.getName())).build());

        ItemStack inventoryContents = new ItemBuilder(Material.CHEST).name(ChatColor.RED + "Inventory Contents").lore(Arrays.asList(
                "&7&m------------------------------",
                (fight.getInventorySize() == 0 ? "&e" + deathData.getName() + "&c had an empty inventory" : "&e" + deathData.getName() + "&c had " + fight.getInventorySize() + " items"),
                (fight.hasArmor() ? "&e" + deathData.getName() + "&c was wearing armor" : "&e" + deathData.getName() + "&c wasn't wearing armor"),
                "&7&m------------------------------",
                "&eClick to view expanded details",
                "&7&m------------------------------"
        )).build();

        ItemStack deathLocation = new ItemBuilder(Material.EMPTY_MAP).name(ChatColor.RED + "Death Location").lore(Arrays.asList(
                "&7&m------------------------------",
                "&eWorld: &c" + WordUtils.capitalize(fight.getLocation().getWorld().getName().replace("_", " ").toLowerCase()),
                "&eCoordinates: &cX: " + fight.getLocation().getBlockX() + ", Y: " + fight.getLocation().getBlockY() + ", Z: " + fight.getLocation().getBlockZ(),
                "&7&m------------------------------",
                "&eClick to teleport to death location",
                "&7&m------------------------------"
        )).build();


        toReturn.setItem(11, inventoryContents);
        toReturn.setItem(15, deathLocation);

        return toReturn;
    }

    public static String toRomanNumeral(int input) {
        if (input < 1 || input > 3999)
            return "Invalid Roman Number Value";
        String s = "";
        while (input >= 1000) {
            s += "M";
            input -= 1000;        }
        while (input >= 900) {
            s += "CM";
            input -= 900;
        }
        while (input >= 500) {
            s += "D";
            input -= 500;
        }
        while (input >= 400) {
            s += "CD";
            input -= 400;
        }
        while (input >= 100) {
            s += "C";
            input -= 100;
        }
        while (input >= 90) {
            s += "XC";
            input -= 90;
        }
        while (input >= 50) {
            s += "L";
            input -= 50;
        }
        while (input >= 40) {
            s += "XL";
            input -= 40;
        }
        while (input >= 10) {
            s += "X";
            input -= 10;
        }
        while (input >= 9) {
            s += "IX";
            input -= 9;
        }
        while (input >= 5) {
            s += "V";
            input -= 5;
        }
        while (input >= 4) {
            s += "IV";
            input -= 4;
        }
        while (input >= 1) {
            s += "I";
            input -= 1;
        }
        return s;
    }
}
