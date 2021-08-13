package me.andyreckt.uhc.utilties;

public class NumberUtils {

	public static boolean isInteger(String value) {
	    try {
	        Integer.parseInt(value);
	    } catch (NumberFormatException e) {
	        return false;
	    }
	    return true;
	}
	
    public static Integer tryParseInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
