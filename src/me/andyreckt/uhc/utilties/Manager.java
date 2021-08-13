package me.andyreckt.uhc.utilties;


import me.andyreckt.uhc.UHC;

/**
 * Created by Marko on 07.07.2018.
 */


public class Manager {

    public UHC getPlugin() {
        return plugin;
    }

    public void setPlugin(UHC plugin) {
        this.plugin = plugin;
    }

    protected UHC plugin;

    public Manager(UHC plugin) {
        this.plugin = plugin;
    }
}
