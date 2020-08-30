package com.thesevenq.uhc.commands;

import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.commands.arguments.*;
import com.thesevenq.uhc.player.deathlookup.DeathLookupCommand;
import com.thesevenq.uhc.player.deathlookup.LastInventoryCommand;
import com.thesevenq.uhc.utilties.Manager;
import com.thesevenq.uhc.utilties.Msg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler extends Manager implements CommandExecutor {
	
	private List<BaseCommand> commands;

	public CommandHandler(UHC plugin) {
		super(plugin);

		this.commands = new ArrayList<BaseCommand>();

		this.commands.add(new AutoStartCommand(plugin));
		this.commands.add(new BackpackCommand(plugin));
		this.commands.add(new BorderCommand(plugin));
		this.commands.add(new ConfigCommand(plugin));
		this.commands.add(new FlatCommand(plugin));
		this.commands.add(new GameCommand(plugin));
		this.commands.add(new HealthCommand(plugin));
		this.commands.add(new HideLocationCommand(plugin));
		this.commands.add(new KillCountCommand(plugin));
		this.commands.add(new MLGCommand(plugin));
		this.commands.add(new SpectatorCommand(plugin));
		this.commands.add(new MultiSpawnCommand(plugin));
		this.commands.add(new NextGameCommand(plugin));
		this.commands.add(new OnlineStaffCommand(plugin));
		this.commands.add(new ReviveCommand(plugin));
		this.commands.add(new ScatterCommand(plugin));
		this.commands.add(new ScenariosCommand(plugin));
		this.commands.add(new SpectatorCommand(plugin));
		this.commands.add(new StatsCommand(plugin));
		this.commands.add(new PartyCommand(plugin));
		this.commands.add(new PartyListCommand(plugin));
		this.commands.add(new TopKillsCommand(plugin));
		this.commands.add(new UHCPracticeCommand(plugin));
		this.commands.add(new WhitelistCommand(plugin));
		this.commands.add(new XRayCommand(plugin));
		this.commands.add(new DeathLookupCommand(plugin));
		this.commands.add(new LastInventoryCommand(plugin));
		this.commands.add(new LeaderboardCommand(plugin));
		this.commands.add(new ClearLaggCommand(plugin));

		for(BaseCommand command : this.commands) {
			plugin.getCommand(command.getCommand()).setExecutor(this);
		}
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		for(BaseCommand baseCommand : this.commands) {
			if(command.getName().equalsIgnoreCase(baseCommand.getCommand())) {
				if(((sender instanceof ConsoleCommandSender)) && (baseCommand.isForPlayerUseOnly())) {
					sender.sendMessage(Msg.NO_CONSOLE);
					return true;
				}
				
				if((!sender.hasPermission(baseCommand.getPermission())) && (!baseCommand.getPermission().equals(""))) {
					sender.sendMessage(Msg.NO_PERMISSION);
					return true;
				}
				
				baseCommand.execute(sender, args);
				return true;
			}
		}
		
		return true;
	}
}
