package com.thesevenq.uhc.api;

import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.utilties.StringUtils;

public class API {

    public static void setMaxPlayers(int slots) {
        StringUtils.setSlots(slots);
    }

    public static String getServerName() {
        return UHC.getInstance().getConfig().getString("SERVERNAME");
    }
}
