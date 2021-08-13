package me.andyreckt.uhc.player.deathlookup;

import com.mongodb.client.FindIterable;
import me.andyreckt.uhc.utilties.Permission;
import me.andyreckt.uhc.utilties.inventory.InventorySerialisation;
import me.andyreckt.uhc.commands.BaseCommand;
import me.andyreckt.uhc.player.deathlookup.data.DeathData;
import me.andyreckt.uhc.player.deathlookup.data.ProfileFight;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import me.andyreckt.uhc.UHC;

import java.io.IOException;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;

public class LastInventoryCommand extends BaseCommand {

    public LastInventoryCommand(UHC plugin) {
        super(plugin);

        this.command = "lastinventory";
        this.permission = Permission.STAFF_PERMISSION;
        this.forPlayerUseOnly = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        DeathData toRestore;
        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "/lastinventory <player>");
            return;
        } else {
            Player toRestorePlayer = Bukkit.getPlayer(args[0]);
            if (toRestorePlayer != null) {
                toRestore = DeathData.getByName(toRestorePlayer.getName());
            } else {
                toRestore = DeathData.getByName(args[0]);
            }
        }

        if (toRestore == null) {
            player.sendMessage(ChatColor.RED + "No player named '" + args[0] + "' found.");
            return;
        }

        UUID uuid = null;
        if (args.length > 1) {
            try {
                uuid = UUID.fromString(args[1]);
            } catch (Exception ex) {
                player.sendMessage(ChatColor.RED + "Invalid query.");
                return;
            }
        }

        ItemStack[] contents = null, armor = null;
        if (!toRestore.getFights().isEmpty()) {
            for (ProfileFight oneFight : toRestore.getFights()) {
                if (oneFight.getKiller() != null && oneFight.getKiller().getName() != null && oneFight.getKiller().getName().equals(toRestore.getName())) continue;
                if (uuid != null && !oneFight.getUuid().equals(uuid)) continue;
                contents = oneFight.getContents();
                armor = oneFight.getArmor();
            }
        } else {
            Document fightDocument;
            FindIterable iterable = UHC.getInstance().getDatabaseManager().getUhcDeaths().find(eq("killed", toRestore.getName().toLowerCase())).sort(descending("occurred_at"));
            if (uuid == null) {
                fightDocument = (Document) iterable.first();
            } else {
                fightDocument = (Document) iterable.filter(eq("name", uuid.toString())).first();
            }

            if (fightDocument == null) {
                player.sendMessage(ChatColor.RED + toRestore.getName() + " has no previous inventories to restore. (document)");
                return;
            }

            try {
                contents = InventorySerialisation.itemStackArrayFromJson(fightDocument.getString("contents"));
                armor = InventorySerialisation.itemStackArrayFromJson(fightDocument.getString("armor"));
            } catch (IOException e) {
                player.sendMessage(ChatColor.RED + "An error occurred when attempting to grab that inventory.");
                return;
            }
        }

        if (contents == null || armor == null) {
            player.sendMessage(ChatColor.RED + "An error occurred when attempting to grab that inventory.");
            return;
        }

        player.getInventory().setContents(contents);
        player.getInventory().setArmorContents(armor);
        player.sendMessage(ChatColor.RED + "Inventory received.");
    }
}
