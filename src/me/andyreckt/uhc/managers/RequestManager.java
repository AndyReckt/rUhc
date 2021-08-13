package me.andyreckt.uhc.managers;

import me.andyreckt.uhc.utilties.ActionMessage;
import me.andyreckt.uhc.utilties.Color;

import me.andyreckt.uhc.utilties.Manager;
import org.bukkit.entity.Player;
import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.player.party.Party;
import me.andyreckt.uhc.player.party.PartyRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestManager extends Manager {
	public static Map<Player, PartyRequest> getRequests() {
		return requests;
	}

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

		player.sendMessage(Color.translate("&eYou have &areceived&e a party request from &9" + party.getOwner().getName() + "&e."));

		ActionMessage actionMessage = new ActionMessage();
		actionMessage.addText("&eClick ");
		actionMessage.addText("&a&lYES").setClickEvent(ActionMessage.ClickableType.RunCommand, "/party accept").addHoverText(Color.translate("&eClick this to join their party."));
		actionMessage.addText(" &eor click ");
		actionMessage.addText("&c&lNO").setClickEvent(ActionMessage.ClickableType.RunCommand, "/party deny").addHoverText(Color.translate("&eClick this to deny their party invite."));
		actionMessage.sendToPlayer(player);

		party.broadcast("&9" + party.getOwner().getName() + " &ehas invited &9" + player.getName() + " &eto the party.");

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
