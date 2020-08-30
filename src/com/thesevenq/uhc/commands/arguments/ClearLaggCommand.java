package com.thesevenq.uhc.commands.arguments;

import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.commands.BaseCommand;
import com.thesevenq.uhc.utilties.Color;
import com.thesevenq.uhc.utilties.Permission;
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
            sender.sendMessage(Color.translate("&bLagg cleared &asuccessfully&b."));
        }
    }
}
