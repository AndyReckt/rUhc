package com.thesevenq.uhc.tasks;

import com.thesevenq.uhc.managers.InventoryManager;
import com.thesevenq.uhc.managers.InventoryManager;

public class UpdateInventoryTask implements Runnable {

    @Override
    public void run() {
        InventoryManager.setupSettings();
        InventoryManager.setupPlayerSettings();
    }
}
