package com.thesevenq.uhc.utilties.command;

import co.aikar.timings.Timing;
import co.aikar.timings.Timings;
import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.utilties.command.param.ParameterData;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

final class CommandData {

    @Getter private String[] names;
    @Getter private String permissionNode;
    @Getter private boolean async;
    @Getter private List<ParameterData> parameters = new ArrayList<>();
    @Getter private Method method;
    @Getter private Timing timingsHandler;
    @Getter private boolean consoleAllowed;

    public CommandData(Command commandAnnotation, List<ParameterData> parameters, Method method, boolean consoleAllowed) {
        this.names = commandAnnotation.names();
        this.permissionNode = commandAnnotation.permissionNode();
        this.async = commandAnnotation.async();
        this.parameters = parameters;
        this.method = method;
        this.consoleAllowed = consoleAllowed;
        this.timingsHandler = Timings.of(UHC.getInstance(), "CommandHandler - " + getName());
    }

    public String getName() {
        return (names[0]);
    }

    public boolean canAccess(CommandSender sender) {
        // Console can do anything.
        if (!(sender instanceof Player)) {
            return (true);
        }

        // We accept more than just permissions, so we do a switch!
        switch (permissionNode) {
            case "op":
                return (sender.isOp());
            case "":
                return (true);
            default:
                return (sender.hasPermission(permissionNode));
        }
    }

    public String getUsageString() {
        return (getUsageString(getName()));
    }

    public String getUsageString(String aliasUsed) {
        StringBuilder stringBuilder = new StringBuilder();

        for (ParameterData paramHelp : getParameters()) {
            boolean needed = paramHelp.getDefaultValue().isEmpty();
            stringBuilder.append(needed ? "<" : "[").append(paramHelp.getName());
            stringBuilder.append(needed ? ">" : "]").append(" ");
        }

        return ("/" + aliasUsed.toLowerCase() + " " + stringBuilder.toString().trim().toLowerCase());
    }

    public void execute(CommandSender sender, String[] params) {
        // We start to build the parameters we call the method with here.
        List<Object> transformedParameters = new ArrayList<>();

        // Add the sender.
        // If the method is expecting a Player or a general CommandSender will be handled by Java.
        transformedParameters.add(sender);

        // Fill in / validate parameters
        for (int parameterIndex = 0; parameterIndex < getParameters().size(); parameterIndex++) {
            ParameterData parameter = getParameters().get(parameterIndex);
            String passedParameter = (parameterIndex < params.length ? params[parameterIndex] : parameter.getDefaultValue()).trim();

            // We needed a parameter where we didn't get one (where there's no default value available)
            if (parameterIndex >= params.length && (parameter.getDefaultValue() == null || parameter.getDefaultValue().isEmpty())) {
                sender.sendMessage(ChatColor.RED + "Usage: " + getUsageString());
                return;
            }

            // Wildcards "capture" all strings after them
            if (parameter.isWildcard() && !passedParameter.trim().equals(parameter.getDefaultValue().trim())) {
                passedParameter = toString(params, parameterIndex);
            }

            // We try to transform the parameter given to us.
            Object result = UHCCommandHandler.transformParameter(sender, passedParameter, parameter.getParameterClass());

            // If it's null that means the transformer tried (and failed) at transforming the value.
            // It'll have sent them a message and such, so we can just return.
            if (result == null) {
                return;
            }

            transformedParameters.add(result);

            // If it was a wildcard we don't want to bother parsing anything else
            // (even though there shouldn't have been anything else)
            if (parameter.isWildcard()) {
                break;
            }
        }

        // and actually execute the command.
        timingsHandler.startTiming();

        try {
            // null = static method.
            method.invoke(null, transformedParameters.toArray());
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "It appears there was some issues processing your command...");
            e.printStackTrace();
        }

        timingsHandler.stopTiming();
    }

    public static String toString(String[] args, int start) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int arg = start; arg < args.length; arg++) {
            stringBuilder.append(args[arg]).append(" ");
        }

        return (stringBuilder.toString().trim());
    }

}