package me.andyreckt.uhc.commands.arguments;

import me.andyreckt.uhc.utilties.Color;
import me.andyreckt.uhc.managers.GameManager;
import me.andyreckt.uhc.managers.PartyManager;
import me.andyreckt.uhc.managers.ScenarioManager;
import me.andyreckt.uhc.utilties.UHCUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.commands.BaseCommand;
import me.andyreckt.uhc.player.party.Party;
import me.andyreckt.uhc.player.state.GameState;

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
