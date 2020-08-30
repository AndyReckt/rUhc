package com.thesevenq.uhc.commands.arguments;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.commands.BaseCommand;
import com.thesevenq.uhc.managers.PlayerManager;

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
