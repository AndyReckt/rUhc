package com.thesevenq.uhc.player.party;

import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.managers.RequestManager;
import com.thesevenq.uhc.utilties.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
@AllArgsConstructor
public class PartyRequest {
	
    private Party party;
    private Player recipient;

    public void handleDeny() {
        recipient.sendMessage(Color.translate("&bYou have denied the party invite."));
        party.getOwner().sendMessage(Color.translate("&d" + recipient.getName() + " &bhas denied the party invite."));
    }

    public static void handleRequestTimer(Player player, Party party) {
        new BukkitRunnable() {
            public void run() {
                PartyRequest partyRequest = RequestManager.getByPlayer(player);

                if(partyRequest != null) {
                    RequestManager.timedOutRequest(player);

                    player.sendMessage(Color.translate("&bYour party request from &d" + party.getOwner().getName() + " &bhas expiried."));

                    party.getOwner().sendMessage(Color.translate("&bParty invite to &d" + player.getName() + "&bhas expiried."));
                }

            }
        }.runTaskLater(UHC.getInstance(), 350L);
    }
}
