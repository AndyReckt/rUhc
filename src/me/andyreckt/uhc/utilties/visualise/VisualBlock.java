package me.andyreckt.uhc.utilties.visualise;


import org.bukkit.Location;


public class VisualBlock {
    public VisualType getVisualType() {
        return visualType;
    }

    public VisualBlockData getBlockData() {
        return blockData;
    }

    public Location getLocation() {
        return location;
    }

    public VisualBlock(VisualType visualType, VisualBlockData blockData, Location location) {
        this.blockData = blockData;
        this.visualType = visualType;
        this.location = location;
    }

    private VisualType visualType;
    private VisualBlockData blockData;
    private Location location;
}
