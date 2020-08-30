package com.thesevenq.uhc.utilties.visualise;

import club.minemen.spigot.handler.PacketHandler;
import com.thesevenq.uhc.UHC;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Marko on 27.07.2018.
 */
public class CustomPacketHandler implements PacketHandler {

    @Override
    public void handleReceivedPacket(PlayerConnection playerConnection, Packet packet) {
        try {
            Player player = playerConnection.getPlayer();

            String simpleName = packet.getClass().getSimpleName();
            switch (simpleName) {
                case "PacketPlayInBlockDig": {
                    PacketPlayInBlockDig.EnumPlayerDigType digType = ((PacketPlayInBlockDig) packet).c();
                    BlockPosition blockPosition = ((PacketPlayInBlockDig) packet).a();

                    if (digType == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK
                            || digType == PacketPlayInBlockDig.EnumPlayerDigType.STOP_DESTROY_BLOCK) {
                        int x = blockPosition.getX();
                        int y = blockPosition.getY();
                        int z = blockPosition.getZ();

                        Location location = new Location(player.getWorld(), x, y, z);
                        VisualBlock visualBlock = VisualiseHandler.getVisualBlockAt(player, location);

                        if (visualBlock != null) {
                            VisualBlockData data = visualBlock.getBlockData();
                            player.sendBlockChange(location, data.getBlockType(), data.getData());
                        }
                    }

                    break;
                }

                case "PacketPlayInBlockPlace": {
                    PacketPlayInBlockPlace blockPlace = ((PacketPlayInBlockPlace) packet);

                    int face = blockPlace.getFace();

                    if (face == 255) {
                        return;
                    }

                    BlockPosition blockPosition = blockPlace.a();

                    int x = blockPosition.getX();
                    int y = blockPosition.getY();
                    int z = blockPosition.getZ();

                    Location clickedBlock = new Location(player.getWorld(), x, y, z);
                    if(VisualiseHandler.getVisualBlockAt(player, clickedBlock) != null) {
                        Location placedLocation = clickedBlock.clone();
                        switch (face) {
                            case 0:
                                placedLocation.add(0.0, -1.0, 0.0);
                                break;
                            case 1:
                                placedLocation.add(0.0, 1.0, 0.0);
                                break;
                            case 2:
                                placedLocation.add(0, 0, -1);
                                break;
                            case 3:
                                placedLocation.add(0, 0, 1);
                                break;
                            case 4:
                                placedLocation.add(-1, 0, 0);
                                break;
                            case 5:
                                placedLocation.add(1, 0, 0);
                                break;
                            default:
                                return;
                        }

                        VisualBlock vBlock = VisualiseHandler.getVisualBlockAt(player, clickedBlock);

                        if(vBlock != null) {
                            new BukkitRunnable(){
                                public void run() {
                                    VisualBlockData visualBlockData = vBlock.getBlockData();
                                    player.sendBlockChange(clickedBlock, visualBlockData.getBlockType(), visualBlockData.getData());
                                }
                            }.runTask(UHC.getInstance());
                        } else {
                            new BukkitRunnable(){
                                public void run() {
                                    org.bukkit.block.Block block = clickedBlock.getBlock();
                                    player.sendBlockChange(clickedBlock, block.getType(), block.getData());
                                }
                            }.runTask(UHC.getInstance());
                        }
                    }

                    break;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleSentPacket(PlayerConnection playerConnection, Packet packet) {

    }
}