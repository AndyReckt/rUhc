package com.thesevenq.uhc.config;

public class Options {

    public Options() {
        new AbsorptionOption();
        new BorderShrinkOption();
        new FinalHealOption();
        new FirstShrinkOption();
        new GodApplesOption();
        new GoldenHeadsOption();
        new InvisibilityOption();
        new NetherOption();
        new PearlDamageOption();
        new PvPEnableOption();
        new SpeedOption();
        new StarterFoodOption();
        new StrengthOption();
    }

    private class AbsorptionOption extends Option {
        public AbsorptionOption() {
            super("Absorption", "Should Absorption be enabled when player eat golden apples?", 1);
        }
    }
    
    private class BorderShrinkOption extends Option {
        public BorderShrinkOption() {
            super("Border Shrink Interval", "How often does the worldborder shrink. (in minutes)", 0, 3, 5, 10, 15);
        }
    }
    
    private class FinalHealOption extends Option {
        public FinalHealOption() {
            super("Final Heal", "What time should all players be healed? (in minutes)", 0, 5, 10, 15, 20, 25);
        }
    }

    private class FirstShrinkOption extends Option {
        public FirstShrinkOption() {
            super("First Shrink", "What time should the first border shrink. (in minutes)", 7, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60);
        }
    }

    private class GodApplesOption extends Option {
        public GodApplesOption() {
            super("God Apples", "Should God Apples be enabled?", 0);
        }
    }

    private class GoldenHeadsOption extends Option {
        public GoldenHeadsOption() {
            super("Golden Heads", "Should Golden Heads be enabled?", 1);
        }
    }

    private class InvisibilityOption extends Option {
        public InvisibilityOption() {
            super("Invisibility Potions", "Should players be able to brew Invisibility Potions at all?", 1);
        }
    }

    private class NetherOption extends Option {
        public NetherOption() {
            super("Nether", "Should the Nether be enabled?", 1);
        }
    }

    private class PearlDamageOption extends Option {
        public PearlDamageOption() {
            super("Ender Pearl Damage", "Should players take damage when an Ender Pearl lands?", 1);
        }
    }

    private class PvPEnableOption extends Option {
        public PvPEnableOption() {
            super("PvP Period Duration", "How long should the PvP Period last? (in minutes)", 0, 10, 20, 25, 30, 45, 60);
        }
    }

    private class SpeedOption extends Option {
        public SpeedOption() {
            super("Speed Potions", "Should players be able to brew Speed Potions at all?", 1);
        }
    }

    private class StarterFoodOption extends Option {
        public StarterFoodOption() {
            super("Starter Food", "How much steak should players receive on uhc start?", 0, 16, 32, 64);
        }
    }

    private class StrengthOption extends Option {
        public StrengthOption() {
            super("Strength Potions", "Should players be able to brew Strength Potions at all?", 0);
        }
    }
}
