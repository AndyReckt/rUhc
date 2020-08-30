package com.thesevenq.uhc.player.deathlookup;

import lombok.Getter;
import lombok.Setter;
import com.thesevenq.uhc.player.deathlookup.data.ProfileFight;

@Getter
@Setter
public class DeathLookupData {

    private ProfileFight fight;
    private int page;
    private int index;

}
