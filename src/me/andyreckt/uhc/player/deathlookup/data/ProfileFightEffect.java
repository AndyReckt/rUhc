package me.andyreckt.uhc.player.deathlookup.data;


import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ProfileFightEffect {
    public PotionEffectType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public int getDuration() {
        return duration;
    }

    private final PotionEffectType type;
     private final int level, duration;

    public ProfileFightEffect(PotionEffect effect) {
        this.type = effect.getType();
        this.level = effect.getAmplifier() + 1;
        this.duration = (effect.getDuration() / 20) * 1000; //convert to seconds then to ms
    }
}
