package me.andyreckt.uhc.player.deathlookup.data.killer.type;

import me.andyreckt.uhc.player.deathlookup.data.ProfileFightEnvironment;
import me.andyreckt.uhc.player.deathlookup.data.killer.ProfileFightKiller;


public class ProfileFightEnvironmentKiller extends ProfileFightKiller {

     private final ProfileFightEnvironment type;

    public ProfileFightEnvironment getType() {
        return type;
    }

    public ProfileFightEnvironmentKiller(ProfileFightEnvironment type) {
        super(null, null);

        this.type = type;
    }
}
