package com.thesevenq.uhc.commands.arguments;

import com.thesevenq.uhc.utilties.Color;
import com.thesevenq.uhc.utilties.Permission;
import com.thesevenq.uhc.utilties.StringUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.command.CommandSender;
import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.commands.BaseCommand;
import com.thesevenq.uhc.tasks.AutoStartTask;

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

                sender.sendMessage(Color.translate("&bYou have set autostart time to &3" + DurationFormatUtils.formatDurationWords(duration, true, true) + "&b."));

                new AutoStartTask(duration);
            }
        }
    }
}