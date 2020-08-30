package com.thesevenq.uhc.utilties.visualise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;

@Getter
@AllArgsConstructor
public class VisualBlock {

    private VisualType visualType;
    private VisualBlockData blockData;
    private Location location;
}
