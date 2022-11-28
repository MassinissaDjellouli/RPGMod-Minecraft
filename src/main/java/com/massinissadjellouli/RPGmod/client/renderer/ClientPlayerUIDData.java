package com.massinissadjellouli.RPGmod.client.renderer;

public class ClientPlayerUIDData {
    private static String uid;

    public static String getUid() {
        return uid;
    }

    public static void setUid(String uid) {
        ClientPlayerUIDData.uid = uid;
    }
}
