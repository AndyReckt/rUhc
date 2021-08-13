package me.andyreckt.uhc.commands.arguments;

import me.andyreckt.uhc.utilties.Color;
import me.andyreckt.uhc.utilties.Permission;
import me.andyreckt.uhc.utilties.StringUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.command.CommandSender;
import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.commands.BaseCommand;
import me.andyreckt.uhc.tasks.AutoStartTask;

public class AutoStartCommand extends BaseCommand {

    public AutoStartCommand(UHC plugin) {
        super(plugin);

        this.command = "autostart";
        this.permission = Permission.OP_PERMISSION;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(Color.translate("&cUsage: /autostart <time|cancel>"));
        } else {
            if(args[0].equalsIgnoreCase("cancel")) {
                if(AutoStartTask.running) {
                    AutoStartTask.starting.cancelRunnable();
                } else {
                    sender.sendMessage(Color.translate("&cGame is not starting."));
                }
            } else {
                if(AutoStartTask.running) {
                    sender.sendMessage(Color.translate("&cGame is already starting."));
                    return;
                }

                long duration = StringUtils.parse(args[0]);

                if(duration == -1) {
                    sender.sendMessage(Color.translate("&cInvalid duration."));
                    return;
                }

                sender.sendMessage(Color.translate("&eYou have set autostart time to &9" + StringUtils.getRemaining(AutoStartTask.seconds, false) + "&e."));

                new AutoStartTask(duration);
            }
        }
    }
}