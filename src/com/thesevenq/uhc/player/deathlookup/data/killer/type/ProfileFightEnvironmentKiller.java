package com.thesevenq.uhc.player.deathlookup.data.killer.type;

import com.thesevenq.uhc.player.deathlookup.data.ProfileFightEnvironment;
import com.thesevenq.uhc.player.deathlookup.data.killer.ProfileFightKiller;
import lombok.Getter;

public class ProfileFightEnvironmentKiller extends ProfileFightKiller {

    @Getter private final ProfileFightEnvironment type;

    public ProfileFightEnvironmentKiller(ProfileFightEnvironment type) {
        super(null, null);

        this.type = type;
    }
}
