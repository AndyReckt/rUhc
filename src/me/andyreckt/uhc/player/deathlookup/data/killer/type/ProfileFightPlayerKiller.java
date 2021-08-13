package me.andyreckt.uhc.player.deathlookup.data.killer.type;

import me.andyreckt.uhc.player.deathlookup.data.ProfileFightEffect;
import me.andyreckt.uhc.player.deathlookup.data.killer.ProfileFightKiller;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class ProfileFightPlayerKiller extends ProfileFightKiller {
    public String getName() {
        return name;
    }

    public ItemStack[] getContents() {
        return contents;
    }

    public ItemStack[] getArmor() {
        return armor;
    }

    public double getHealth() {
        return health;
    }

    public double getHunger() {
        return hunger;
    }

    public int getPing() {
        return ping;
    }

    public List<ProfileFightEffect> getEffects() {
        return effects;
    }

    private final String name;
     private final ItemStack[] contents, armor;
     private final double health, hunger;
     private final int ping;
     private final List<ProfileFightEffect> effects;

    public ProfileFightPlayerKiller(Player player) {
        this(player.getName(), ((CraftPlayer)player).getHandle().ping, player.getInventory().getContents(), player.getInventory().getArmorContents(), player.getHealth(), player.getFoodLevel(), new ArrayList<>());

        for (PotionEffect effect : player.getActivePotionEffects()) {
            effects.add(new ProfileFightEffect(effect));
        }
    }

    public ProfileFightPlayerKiller(String name, int ping, ItemStack[] contents, ItemStack[] armor, double health, double hunger, List<ProfileFightEffect> effects) {
        super(EntityType.PLAYER, name);

        this.ping = ping;
        this.name = name;
        this.contents = contents;
        this.armor = armor;
        this.health = health;
        this.hunger = hunger;
        this.effects = effects;
    }

}
