package me.andyreckt.uhc.player.party;

import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.managers.RequestManager;
import me.andyreckt.uhc.utilties.Color;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class PartyRequest {
    public Party getParty() {
        return party;
    }

    public Player getRecipient() {
        return recipient;
    }

    private Party party;
    private Player recipient;

    public PartyRequest(Party party, Player recipient) {
        this.party = party;
        this.recipient = recipient;
    }

    public void handleDeny() {
        recipient.sendMessage(Color.translate("&eYou have denied the party invite."));
        party.getOwner().sendMessage(Color.translate("&d" + recipient.getName() + " &ehas denied the party invite."));
    }

    public static void handleRequestTimer(Player player, Party party) {
        new BukkitRunnable() {
            public void run() {
                PartyRequest partyRequest = RequestManager.getByPlayer(player);

                if(partyRequest != null) {
                    RequestManager.timedOutRequest(player);

                    player.sendMessage(Color.translate("&eYour party request from &d" + party.getOwner().getName() + " &ehas expiried."));

                    party.getOwner().sendMessage(Color.translate("&eParty invite to &d" + player.getName() + "&ehas expiried."));
                }

            }
        }.runTaskLater(UHC.getInstance(), 350L);
    }
}
