package me.andyreckt.uhc.utilties.command.param.defaults;

import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.utilties.command.param.ParameterType;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PlayerParameterType implements ParameterType<Player> {

    public Player transform(CommandSender sender, String source) {
        if (sender instanceof Player && (source.equalsIgnoreCase("self") || source.equals(""))) {
            return ((Player) sender);
        }

        Player player = UHC.getInstance().getServer().getPlayer(source);

        if (player == null) {
            sender.sendMessage(ChatColor.RED + "No player with the name '" + source + "' found.");
            return (null);
        }

        return (player);
    }

    public List<String> tabComplete(Player sender, Set<String> flags, String source) {
        List<String> completions = new ArrayList<>();

        for (Player player : UHC.getInstance().getServer().getOnlinePlayers()) {
            if (StringUtils.startsWithIgnoreCase(player.getName(), source) && sender.canSee(player)) {
                completions.add(player.getName());
            }
        }

        return (completions);
    }

}