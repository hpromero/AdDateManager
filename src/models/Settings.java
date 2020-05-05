package models;

import java.time.LocalTime;

public class Settings {
    private static LocalTime startday = LocalTime.of(8,00);
    private static LocalTime startBar2 = LocalTime.of(11,0);
    private static LocalTime startBar3 = LocalTime.of(14,0);
    private static LocalTime startBar4 = LocalTime.of(17,0);
    private static LocalTime startBar5 = LocalTime.of(20,0);
    private static LocalTime endday = LocalTime.of(23,0);
    private static String colorBarfull = "#27ae60";
    private static String colorBarMedium = "#f39c12";
    private static String colorBarEmpty = "#c0392b";
    private static String colorBarNull = "#7f8c8d";

    public static LocalTime getStartday() { return startday; }
    public static LocalTime getStartBar2() { return startBar2; }
    public static LocalTime getStartBar3() { return startBar3; }
    public static LocalTime getStartBar4() { return startBar4; }
    public static LocalTime getStartBar5() { return startBar5; }
    public static LocalTime getEndday() { return endday; }
    public static String getColorBarfull() { return colorBarfull; }
    public static String getColorBarMedium() { return colorBarMedium; }
    public static String getColorBarEmpty() { return colorBarEmpty; }
    public static String getColorBarNull() { return colorBarNull; }
}
