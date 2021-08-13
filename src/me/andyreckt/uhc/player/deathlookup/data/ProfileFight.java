package me.andyreckt.uhc.player.deathlookup.data;

import me.andyreckt.uhc.player.deathlookup.data.killer.ProfileFightKiller;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProfileFight {
    public UUID getUuid() {
        return uuid;
    }

    public ProfileFightKiller getKiller() {
        return killer;
    }

    public long getOccurredAt() {
        return occurredAt;
    }

    public ItemStack[] getContents() {
        return contents;
    }

    public ItemStack[] getArmor() {
        return armor;
    }

    public double getHunger() {
        return hunger;
    }

    public List<ProfileFightEffect> getEffects() {
        return effects;
    }

    public Location getLocation() {
        return location;
    }

    public int getPing() {
        return ping;
    }

    private final UUID uuid;
     private final ProfileFightKiller killer;
     private final long occurredAt;
     private final ItemStack[] contents, armor;
     private final double hunger;
     private final List<ProfileFightEffect> effects;
     private final Location location;
     private final int ping;

    public ProfileFight(Player player, ProfileFightKiller killer) {

        this(UUID.randomUUID(), ((CraftPlayer)player).getHandle().ping, System.currentTimeMillis(), player.getInventory().getContents(), player.getInventory().getArmorContents(), player.getFoodLevel(), new ArrayList<>(), killer, player.getLocation());

        for (PotionEffect effect : player.getActivePotionEffects()) {
            effects.add(new ProfileFightEffect(effect));
        }
    }

    public ProfileFight(UUID uuid, int ping, long occurredAt, ItemStack[] contents, ItemStack[] armor, double hunger, List<ProfileFightEffect> effects, ProfileFightKiller killer, Location location) {
        this.uuid = uuid;

        this.occurredAt = occurredAt;
        this.contents = contents;
        this.armor = armor;
        this.hunger = hunger;
        this.killer = killer;
        this.effects = effects;
        this.location = location;
        this.ping = ping;
    }

    public boolean hasArmor() {
        for (ItemStack itemStack : armor) {
            if (itemStack != null && itemStack.getType() != Material.AIR) {
                return true;
            }
        }
        return false;
    }

    public int getInventorySize() {
        int toReturn = 0;

        for (ItemStack itemStack : contents) {
            if (itemStack != null) {
                toReturn++;
            }
        }

        return toReturn;
    }



}
