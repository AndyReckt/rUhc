package com.thesevenq.uhc.commands.arguments;

import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.commands.BaseCommand;
import com.thesevenq.uhc.player.UHCData;
import com.thesevenq.uhc.utilties.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HideLocationCommand extends BaseCommand {

	public HideLocationCommand(UHC plugin) {
		super(plugin);
		
		this.command = "hidelocation";
		this.forPlayerUseOnly = true;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		if (args.length == 0) {
			UHCData uhcData = UHCData.getByName(player.getName());

			uhcData.setHideLocation(!uhcData.isHideLocation());

			player.sendMessage(Color.translate("&bYou are now " + (uhcData.isHideLocation() ? "&ahiding" : "&cnot hiding") + " &btab location."));
		}
	}

}
