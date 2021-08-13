package me.andyreckt.uhc.commands.arguments;

import me.andyreckt.uhc.managers.PlayerManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.commands.BaseCommand;

public class LeaderboardCommand extends BaseCommand {

    public LeaderboardCommand(UHC plugin) {
        super(plugin);

        this.command = "leaderboard";
        this.forPlayerUseOnly = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        PlayerManager.getLeaderboard((Player) sender);
    }
}
