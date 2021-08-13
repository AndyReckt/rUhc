package me.andyreckt.uhc.api;

import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.utilties.StringUtils;

public class API {

    public static void setMaxPlayers(int slots) {
        StringUtils.setSlots(slots);
    }

    public static String getServerName() {
        return UHC.getInstance().getConfig().getString("SERVERNAME");
    }
}
