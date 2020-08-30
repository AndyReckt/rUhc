package com.thesevenq.uhc.utilties;

import org.apache.commons.lang.StringEscapeUtils;

public class Symbols {

    public static String STAR = StringEscapeUtils.unescapeJava("\u2606");
    public static String CHECKMARK = Color.translate("&a&l" + StringEscapeUtils.unescapeJava("\u2713"));
    public static String X = Color.translate("&c&l" + StringEscapeUtils.unescapeJava("\u2717"));
    public static String LUNAR = StringEscapeUtils.unescapeJava("\u272A");
    public static String HEART = StringEscapeUtils.unescapeJava("\u2764");
    public static String LINE = StringEscapeUtils.unescapeJava("\u2503");
    public static String RADIOACTIVE = StringEscapeUtils.unescapeJava("\u2622");
}
