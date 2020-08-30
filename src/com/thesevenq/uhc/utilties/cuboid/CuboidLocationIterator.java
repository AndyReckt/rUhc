package com.thesevenq.uhc.utilties.cuboid;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Iterator;

@Getter

public class CuboidLocationIterator implements Iterator<Location> {

    private World world;
    private int baseX;
    private int baseY;
    private int baseZ;
    private int sizeX;
    private int sizeY;
    private int sizeZ;
    private int x;
    private int y;
    private int z;

    public CuboidLocationIterator(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
        this.world = world;
        this.baseX = x1;
        this.baseY = y1;
        this.baseZ = z1;
        this.sizeX = Math.abs(x2 - x1) + 1;
        this.sizeY = Math.abs(y2 - y1) + 1;
        this.sizeZ = Math.abs(z2 - z1) + 1;
        boolean x3 = false;
        this.z = (x3 ? 1 : 0);
        this.y = (x3 ? 1 : 0);
        this.x = (x3 ? 1 : 0);
    }

    @Override
    public boolean hasNext() {
        return this.x < this.sizeX && this.y < this.sizeY && this.z < this.sizeZ;
    }

    @Override
    public Location next() {
        Location location = new Location(this.world, (double) (this.baseX + this.x), (double) (this.baseY + this.y), (double) (this.baseZ + this.z));
       
        if(++this.x >= this.sizeX) {
            this.x = 0;
            
            if(++this.y >= this.sizeY) {
                this.y = 0;
                
                ++this.z;
            }
        }
        
        return location;
    }

    @Override
    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
