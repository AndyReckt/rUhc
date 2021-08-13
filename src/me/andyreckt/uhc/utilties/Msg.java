package me.andyreckt.uhc.utilties;

import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Msg {
	
    public static char HEART = '\u2764';
    public static char KRUZIC = '●';
    public static char DOUBLE_ARROW_RIGHT = '»';
    public static char DOUBLE_ARROW_LEFT = '«';
	public static String NO_PERMISSION = Color.translate("&cNo permission.");
	public static String NO_CONSOLE = Color.translate("&cNo console.");
	public static String CONSOLE = Color.translate("&4&lCONSOLE");
	public static String BIG_LINE = Color.translate("&7&m---------------");

	public static String STAR = StringEscapeUtils.unescapeJava("\u2606");
	public static String CHECKMARK = StringEscapeUtils.unescapeJava("\u2713");
	public static String X = StringEscapeUtils.unescapeJava("\u2717");
	public static String LUNAR = StringEscapeUtils.unescapeJava("\u272A");
	public static String LINE = StringEscapeUtils.unescapeJava("\u2503");

	public static boolean checkOffline(CommandSender sender, String args) {
		Player offline = Bukkit.getPlayer(args);

		if(offline == null) {
			sender.sendMessage(Color.translate("&cNo player with the name \"" + args + "\" found."));
			return true;
		}

		return false;
	}
	
	public static void sendMessage(String text) {
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(player != null) {
				player.sendMessage(Color.translate(text));
			}
		}

		Msg.logConsole(text);
	}
	
	public static void sendMessage(String text, String permission) {
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(player != null) {
				if(player.hasPermission(permission)) {
					player.sendMessage(Color.translate(text));
				}
			}
		}

		Msg.logConsole(text);
	}
	
	public static void log(CommandSender sender, String text) {
		Command.broadcastCommandMessage(sender, text);
	}
	
	public static void logConsole(String text) {
		Bukkit.getConsoleSender().sendMessage(Color.translate(text));
	}
}
