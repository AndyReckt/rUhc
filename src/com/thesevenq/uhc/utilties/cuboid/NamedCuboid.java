package com.thesevenq.uhc.utilties.cuboid;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.Map;

public class NamedCuboid extends Cuboid {

    protected String name;

    public NamedCuboid() {}

    public NamedCuboid(Cuboid other) {
        super(other.getWorld(), other.getX1(), other.getY1(), other.getZ1(), other.getX2(), other.getY2(), other.getZ2());
    }

    public NamedCuboid(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
        super(world, x1, y1, z1, x2, y2, z2);
    }

    public NamedCuboid(Location location) {
        super(location, location);
    }

    public NamedCuboid(Location first, Location second) {
        super(first, second);
    }

    public NamedCuboid(Map<String, Object> map) {
        super(map);
        this.name = (String) map.get("name");
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();
        
        map.put("name", this.name);
        return map;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public NamedCuboid clone() {
        return (NamedCuboid) super.clone();
    }

    @Override
    public String toString() {
        return "NamedCuboid: " + this.getWorldName() + ',' + this.getX1() + ',' + this.getY1() + ',' + this.getZ1() + "=>" + this.getX2() + ',' + this.getY2() + ',' + this.getZ2() + ':' + this.name;
    }
}
