package me.andyreckt.uhc.listeners;


import me.andyreckt.uhc.managers.GameManager;
import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.player.state.GameState;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class CustomMovementHandler implements Listener {
    @EventHandler
    public void handleUpdateLocation(PlayerMoveEvent event) {
        UHC.getInstance().getSpectatorManager().handleMove(event.getPlayer(), event.getFrom(), event.getTo());

        if(!GameManager.getGameState().equals(GameState.PLAYING)) {
        MultiSpawnListener.handleMove(event.getPlayer(), event.getFrom(), event.getTo());
        }
    }


}
