package com.massinissadjellouli.RPGmod.client;

public class ClientGamemodeData {
    private static boolean isSurvival;

    public static boolean isSurvival() {
        return isSurvival;
    }

    public static void setIsSurvival(boolean survival) {
        isSurvival = survival;
    }
}
