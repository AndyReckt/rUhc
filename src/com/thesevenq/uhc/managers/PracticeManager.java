package com.thesevenq.uhc.managers;

import lombok.Getter;
import lombok.Setter;
import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.utilties.Manager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Marko on 08.07.2018.
 */

@Getter
@Setter
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
}
