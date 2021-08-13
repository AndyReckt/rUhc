package me.andyreckt.uhc.commands.arguments;

import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.commands.BaseCommand;
import me.andyreckt.uhc.utilties.Color;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.concurrent.atomic.AtomicInteger;

public class ClearLaggCommand extends BaseCommand {

    public ClearLaggCommand(UHC plugin) {
        super(plugin);

        this.command = "clearlagg";
        this.permission = "uhc.lagg";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        AtomicInteger amount = new AtomicInteger();
        for(World world : Bukkit.getWorlds()) {
            world.getEntities().clear();

        }
        sender.sendMessage(Color.translate("&9Lagg cleared &asuccessfully&e."));
    }
}
