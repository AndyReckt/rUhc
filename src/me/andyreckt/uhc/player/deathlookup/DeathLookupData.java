package me.andyreckt.uhc.player.deathlookup;

;
import me.andyreckt.uhc.player.deathlookup.data.ProfileFight;


public class DeathLookupData {
    public ProfileFight getFight() {
        return fight;
    }

    public void setFight(ProfileFight fight) {
        this.fight = fight;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private ProfileFight fight;
    private int page;
    private int index;

}
