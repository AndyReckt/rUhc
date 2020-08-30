package com.thesevenq.uhc.commands.arguments;

import com.thesevenq.uhc.utilties.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.commands.BaseCommand;
import com.thesevenq.uhc.managers.GameManager;
import com.thesevenq.uhc.managers.PartyManager;
import com.thesevenq.uhc.managers.ScenarioManager;
import com.thesevenq.uhc.player.party.Party;
import com.thesevenq.uhc.player.state.GameState;
import com.thesevenq.uhc.utilties.UHCUtils;

public class BackpackCommand extends BaseCommand {

	public BackpackCommand(UHC plugin) {
		super(plugin);

		this.command = "backpack";
		this.forPlayerUseOnly = true;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		if(args.length == 0) {
			if(!PartyManager.isEnabled()) {
				sender.sendMessage(Color.translate("&cParties are currently disabled."));
				return;
			}
			
			if(!ScenarioManager.getByName("BackPacks").isEnabled()) {
				sender.sendMessage(Color.translate("&cYou can't use BackPacks while &lBackPacks&c scenario is disabled."));
				return;
			}
			
			if(!GameManager.getGameState().equals(GameState.PLAYING)) {
				sender.sendMessage(Color.translate("&cThe game isn't running."));
				return;
			}
			
			if(UHCUtils.isPlayerInSpecMode(player)) {
				sender.sendMessage(Color.translate("&cYou can't use this while you are in &lSpectaotr Mode&c."));
				return;
			}

			if (PartyManager.getByPlayer(player) != null) {
				Party party = PartyManager.getByPlayer(player.getPlayer());
				
				player.openInventory(party.getBackPack());
			}
		}
	}

}
