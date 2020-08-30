package com.thesevenq.uhc.managers;

import com.thesevenq.uhc.utilties.ActionMessage;
import com.thesevenq.uhc.utilties.Color;
import lombok.Getter;
import org.bukkit.entity.Player;
import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.player.party.Party;
import com.thesevenq.uhc.player.party.PartyRequest;
import com.thesevenq.uhc.utilties.Manager;

import java.util.HashMap;
import java.util.Map;

public class RequestManager extends Manager {

	@Getter
	private static Map<Player, PartyRequest> requests = new HashMap<>();
	
    public RequestManager(UHC plugin) {
		super(plugin);
    }

    public static void handleSendRequest(Player sender, Player player, Party party) {
		if(requests.containsKey(player)) {
			sender.sendMessage(Color.translate("&cThat player has already party request."));
			return;
		}
		
		if(PartyManager.getByPlayer(player) != null) {
			sender.sendMessage(Color.translate("&cThat player is already in a party."));
			return;
		}
		
		requests.put(player, new PartyRequest(party, player));

		player.sendMessage(Color.translate("&bYou have &areceived&b a party request from &3" + party.getOwner().getName() + "&b."));

		ActionMessage actionMessage = new ActionMessage();
		actionMessage.addText("&bClick ");
		actionMessage.addText("&a&lYES").setClickEvent(ActionMessage.ClickableType.RunCommand, "/party accept").addHoverText(Color.translate("&bClick this to join their party."));
		actionMessage.addText(" &bor click ");
		actionMessage.addText("&c&lNO").setClickEvent(ActionMessage.ClickableType.RunCommand, "/party deny").addHoverText(Color.translate("&bClick this to deny their party invite."));
		actionMessage.sendToPlayer(player);

		party.broadcast("&3" + party.getOwner().getName() + " &bhas invited &3" + player.getName() + " &bto the party.");

		PartyRequest.handleRequestTimer(player, party);
	}

    public static PartyRequest getByPlayer(Player player) {
        return requests.get(player);
    }

    public static void declined(Player player) {
        requests.remove(player).handleDeny();
    }

    public static void timedOutRequest(Player player) {
        requests.remove(player);
    }
}
