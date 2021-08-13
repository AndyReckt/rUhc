package me.andyreckt.uhc.utilties.visualise;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public enum VisualType {

    RED() {
        private BlockFiller blockFiller = new BlockFiller() {
            VisualBlockData generate(Player player, Location location) {
                return new VisualBlockData(Material.STAINED_GLASS, DyeColor.RED.getData());
            }
        };


        BlockFiller blockFiller() {
            return blockFiller;
        }
    },

    GREEN() {
        private BlockFiller blockFiller = new BlockFiller() {
            VisualBlockData generate(Player player, Location location) {
                return new VisualBlockData(Material.STAINED_GLASS, DyeColor.GREEN.getData());
            }
        };


        BlockFiller blockFiller() {
            return blockFiller;
        }
    },

    YELLOW() {
        private BlockFiller blockFiller = new BlockFiller() {
            VisualBlockData generate(Player player, Location location) {
                return new VisualBlockData(Material.STAINED_GLASS, DyeColor.YELLOW.getData());
            }
        };


        BlockFiller blockFiller() {
            return blockFiller;
        }
    },

    PINK() {
        private BlockFiller blockFiller = new BlockFiller() {
            VisualBlockData generate(Player player, Location location) {
                return new VisualBlockData(Material.STAINED_GLASS, DyeColor.PINK.getData());
            }
        };


        BlockFiller blockFiller() {
            return blockFiller;
        }
    },

    PURPLE() {
        private BlockFiller blockFiller = new BlockFiller() {
            VisualBlockData generate(Player player, Location location) {
                return new VisualBlockData(Material.STAINED_GLASS, DyeColor.PURPLE.getData());
            }
        };


        BlockFiller blockFiller() {
            return blockFiller;
        }
    },

    ORANGE() {
        private BlockFiller blockFiller = new BlockFiller() {
            VisualBlockData generate(Player player, Location location) {
                return new VisualBlockData(Material.STAINED_GLASS, DyeColor.ORANGE.getData());
            }
        };


        BlockFiller blockFiller() {
            return blockFiller;
        }
    },

    LIME() {
        private BlockFiller blockFiller = new BlockFiller() {
            VisualBlockData generate(Player player, Location location) {
                return new VisualBlockData(Material.STAINED_GLASS, DyeColor.LIME.getData());
            }
        };


        BlockFiller blockFiller() {
            return blockFiller;
        }
    },

    LIGHT_BLUE() {
        private BlockFiller blockFiller = new BlockFiller() {
            VisualBlockData generate(Player player, Location location) {
                return new VisualBlockData(Material.STAINED_GLASS, DyeColor.LIGHT_BLUE.getData());
            }
        };


        BlockFiller blockFiller() {
            return blockFiller;
        }
    },;

    abstract BlockFiller blockFiller();
}
