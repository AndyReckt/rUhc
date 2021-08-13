package me.andyreckt.uhc.commands.arguments;

import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.commands.BaseCommand;
import me.andyreckt.uhc.player.UHCData;
import me.andyreckt.uhc.utilties.Color;
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

			player.sendMessage(Color.translate("&eYou are now " + (uhcData.isHideLocation() ? "&ahiding" : "&cnot hiding") + " &etab location."));
		}
	}

}
