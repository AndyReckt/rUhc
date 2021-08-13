package me.andyreckt.uhc.utilties.command;

import me.andyreckt.uhc.utilties.command.param.ParameterData;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;

final class UHCCommandMap extends SimpleCommandMap {

    protected static Map<UUID, String[]> parameters = new HashMap<>();

    public UHCCommandMap(Server server) {
        super(server);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String cmdLine) {
        if (!(sender instanceof Player)) {
            return (null);
        }

        Player player = (Player) sender;
        parameters.put(player.getUniqueId(), cmdLine.split(" "));

        try {
            int spaceIndex = cmdLine.indexOf(' ');
            Set<String> completions = new HashSet<String>();

            boolean doneHere = false;

            CommandLoop:
            for (CommandData command : UHCCommandHandler.getCommands()) {
                if (!command.canAccess(player)) {
                    continue;
                }

                for (String alias : command.getNames()) {
                    String split = alias.split(" ")[0];

                    if (spaceIndex != -1) {
                        split = alias;
                    }

                    if (StringUtil.startsWithIgnoreCase(split.trim(), cmdLine.trim()) || StringUtil.startsWithIgnoreCase(cmdLine.trim(), split.trim())) {
                        if (spaceIndex == -1 && cmdLine.length() < alias.length()) {
                            // Complete the command
                            completions.add("/" + split.toLowerCase());
                        } else if (cmdLine.toLowerCase().startsWith(alias.toLowerCase() + " ") && command.getParameters().size() > 0) {
                            // Complete the params
                            int paramIndex = (cmdLine.split(" ").length - alias.split(" ").length);

                            // If they didn't hit space, complete the param before it.
                            if (paramIndex == command.getParameters().size() || !cmdLine.endsWith(" ")) {
                                paramIndex = paramIndex - 1;
                            }

                            if (paramIndex < 0) {
                                paramIndex = 0;
                            }

                            ParameterData paramData = command.getParameters().get(paramIndex);
                            String[] params = cmdLine.split(" ");

                            for (String completion : UHCCommandHandler.tabCompleteParameter(player, cmdLine.endsWith(" ") ? "" : params[params.length - 1], paramData.getParameterClass(), paramData.getTabCompleteFlags())) {
                                completions.add(completion);
                            }
                            doneHere = true;

                            break CommandLoop;
                        } else {
                            String halfSplitString = split.toLowerCase().replaceFirst(alias.split(" ")[0].toLowerCase(), "").trim();
                            String[] splitString = halfSplitString.split(" ");

                            String fixedAlias = splitString[splitString.length - 1].trim();
                            String lastArg = cmdLine.endsWith(" ") ? "" : cmdLine.split(" ")[cmdLine.split(" ").length - 1];

                            if (fixedAlias.length() >= lastArg.length()) {
                                completions.add(fixedAlias);
                            }

                            doneHere = true;
                        }
                    }
                }
            }

            List<String> completionList = new ArrayList<String>(completions);

            if (!doneHere) {
                List<String> vanillaCompletionList = super.tabComplete(sender, cmdLine);

                if (vanillaCompletionList == null) {
                    vanillaCompletionList = new ArrayList<String>();
                }

                for (String vanillaCompletion : vanillaCompletionList) {
                    completionList.add(vanillaCompletion);
                }

            }
            Collections.sort(completionList, new Comparator<String>() {

                @Override
                public int compare(String o1, String o2) {
                    return (o2.length() - o1.length());
                }

            });

            completionList.remove("w");

            return (completionList);
        } catch (Exception e) {
            e.printStackTrace();

            return (new ArrayList<>());
        } finally {
            parameters.remove(player.getUniqueId());
        }
    }

}