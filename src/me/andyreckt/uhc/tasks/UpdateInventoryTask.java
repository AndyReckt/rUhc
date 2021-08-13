package me.andyreckt.uhc.tasks;

import me.andyreckt.uhc.managers.InventoryManager;

public class UpdateInventoryTask implements Runnable {

    @Override
    public void run() {
        InventoryManager.setupSettings();
        InventoryManager.setupPlayerSettings();
    }
}
