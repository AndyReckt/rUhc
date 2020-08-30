package com.thesevenq.uhc.managers;

import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.utilties.Manager;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class HorseManager extends Manager {

    private HashMap<String, Integer> horses = new HashMap<>();

    public HorseManager(UHC plugin) {
        super(plugin);
    }

    public void sitPlayer(Player player) {
        Location l = player.getLocation();
        EntityHorse horse = new EntityHorse(((CraftWorld) l.getWorld()).getHandle());
        EntityCreeper creeper = new EntityCreeper(((CraftWorld) l.getWorld()).getHandle());
        CraftPlayer craftPlayer = (CraftPlayer) player;

        creeper.setLocation(l.getX(), l.getY(), l.getZ(), 0.0f, 0.0f);
        creeper.setInvisible(true);

        horse.setLocation(l.getX(), l.getY(), l.getZ(), 0.0f, 0.0f);
        horse.setInvisible(true);

        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(creeper);
        craftPlayer.getHandle().playerConnection.sendPacket(packet);

        horses.put(player.getName(), creeper.getId());

        PacketPlayOutAttachEntity sit = new PacketPlayOutAttachEntity(0, craftPlayer.getHandle(), creeper);
        craftPlayer.getHandle().playerConnection.sendPacket(sit);
    }

    public void unsitPlayer(Player player) {
        if(horses.get(player.getName()) != null) {
            PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(horses.get(player.getName()));

            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
    }
}
