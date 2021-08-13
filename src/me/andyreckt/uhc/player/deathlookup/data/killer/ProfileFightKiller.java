package me.andyreckt.uhc.player.deathlookup.data.killer;


import org.apache.commons.lang.WordUtils;
import org.bukkit.entity.EntityType;

public class ProfileFightKiller {
    public EntityType getEntityType() {
        return entityType;
    }

    public String getName() {
        return name;
    }

    private final EntityType entityType;
     private final String name;

    public ProfileFightKiller(EntityType entityType, String name) {
        this.entityType = entityType;
        this.name = name;
    }

    public ProfileFightKiller(EntityType entityType) {
        this(entityType, WordUtils.capitalize(entityType.name()));
    }
}
