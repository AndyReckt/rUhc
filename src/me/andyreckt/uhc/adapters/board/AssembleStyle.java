package me.andyreckt.uhc.adapters.board;


public enum AssembleStyle {
    KOHI(true, 15),
    VIPER(true, -1),
    MODERN(false, 1);

    public boolean isDecending() {
        return decending;
    }

    public int getStartNumber() {
        return startNumber;
    }

    private boolean decending;
    private int startNumber;

    AssembleStyle(boolean decending, int startNumber) {
        this.decending = decending;
        this.startNumber = startNumber;
    }
}
