package me.andyreckt.uhc.managers;


import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.utilties.Manager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Marko on 08.07.2018.
 */


public class PracticeManager extends Manager {

    private boolean open = false;
    private List<UUID> users = new ArrayList<>();
    private int slots = 50;

    public PracticeManager(UHC plugin) {
        super(plugin);
    }

    public void handleOnDisable() {
        users.clear();
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public List<UUID> getUsers() {
        return users;
    }

    public void setUsers(List<UUID> users) {
        this.users = users;
    }

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }
}
