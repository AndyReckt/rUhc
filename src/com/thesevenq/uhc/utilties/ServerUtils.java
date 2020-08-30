package com.thesevenq.uhc.utilties;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.thesevenq.uhc.UHC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ServerUtils {
	
	public static void hook() {
		Bukkit.getMessenger().registerOutgoingPluginChannel(UHC.getInstance(), "BungeeCord");
		Bukkit.getMessenger().registerOutgoingPluginChannel(UHC.getInstance(), "BungeeBroadcast");
		Bukkit.getMessenger().registerOutgoingPluginChannel(UHC.getInstance(), "Filter");
		Bukkit.getMessenger().registerOutgoingPluginChannel(UHC.getInstance(), "Command");
		Bukkit.getMessenger().registerOutgoingPluginChannel(UHC.getInstance(), "Permissions");
	}

	public static void sendToServer(Player player, String server) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);

		try {
			out.writeUTF("Connect");
			out.writeUTF(server);
		} catch (IOException e) {
			e.printStackTrace();
		}

		player.sendPluginMessage(UHC.getInstance(), "BungeeCord", b.toByteArray());
	}
	
	public static void filterToBungee(Player player, String server, String message) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		
		try {
			out.writeUTF("Filtered");
			out.writeUTF(player.getName());
			out.writeUTF(server);
			out.writeUTF(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		player.sendPluginMessage(UHC.getInstance(), "Filter", b.toByteArray());
	}

	public static void sendPermissionToBungee(Player player, String permission) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);

		try {
			out.writeUTF("PermissionsChannel");
			out.writeUTF(player.getName());
			out.writeUTF(permission);
		} catch (IOException e) {
			e.printStackTrace();
		}

		player.sendPluginMessage(UHC.getInstance(), "Permissions", b.toByteArray());
	}
	
	public static void commandToBungee(String command) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		
		try {
			out.writeUTF("BungeeCommands");
			out.writeUTF(command);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(Bukkit.getOnlinePlayers().size() > 0) ImmutableList.copyOf(Bukkit.getOnlinePlayers()).get(0).sendPluginMessage(UHC.getInstance(), "Command", b.toByteArray());
	}
	
	public static void bungeeBroadcast(String message) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		
		try {
			out.writeUTF("BroadcastChannel");
			out.writeUTF(message);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(Bukkit.getOnlinePlayers().size() > 0) ImmutableList.copyOf(Bukkit.getOnlinePlayers()).get(0).sendPluginMessage(UHC.getInstance(), "BungeeBroadcast", b.toByteArray());
	}

	public static void bungeeBroadcast(String message, String permission) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);

		try {
			out.writeUTF("BroadcastPChannel");
			out.writeUTF(message);
			out.writeUTF(permission);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(Bukkit.getOnlinePlayers().size() > 0) ImmutableList.copyOf(Bukkit.getOnlinePlayers()).get(0).sendPluginMessage(UHC.getInstance(), "BungeeBroadcast", b.toByteArray());
	}

	public static Collection<Class<?>> getClassesInPackage(Plugin plugin, String packageName) {
		Collection<Class<?>> classes = new ArrayList<>();

		CodeSource codeSource = plugin.getClass().getProtectionDomain().getCodeSource();
		URL resource = codeSource.getLocation();
		String relPath = packageName.replace('.', '/');
		String resPath = resource.getPath().replace("%20", " ");
		String jarPath = resPath.replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "");
		JarFile jarFile;

		try {
			jarFile = new JarFile(jarPath);
		} catch (IOException e) {
			throw (new RuntimeException("Unexpected IOException reading JAR File '" + jarPath + "'", e));
		}

		Enumeration<JarEntry> entries = jarFile.entries();

		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			String entryName = entry.getName();
			String className = null;

			if(entryName.endsWith(".class") && entryName.startsWith(relPath) && entryName.length() > (relPath.length() + "/".length())) {
				className = entryName.replace('/', '.').replace('\\', '.').replace(".class", "");
			}

			if(className != null) {
				Class<?> clazz = null;

				try {
					clazz = Class.forName(className);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

				if (clazz != null) {
					classes.add(clazz);
				}
			}
		}

		try {
			jarFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return (ImmutableSet.copyOf(classes));
	}
}