package com.thesevenq.uhc.utilties.inventory;

import com.google.gson.reflect.TypeToken;
import com.thesevenq.uhc.utilties.GsonFactory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class InventorySerialisation {

    public static String itemStackArrayToJson(ItemStack[] items) throws IllegalStateException {
        return GsonFactory.getCompactGson().toJson(items);
    }

    public static ItemStack[] itemStackArrayFromJson(String data) throws IOException {
        return GsonFactory.getCompactGson().fromJson(data, new TypeToken<ItemStack[]>() {}.getType());
    }
}
