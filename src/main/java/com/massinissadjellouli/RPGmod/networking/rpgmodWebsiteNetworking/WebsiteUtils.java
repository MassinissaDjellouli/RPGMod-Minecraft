package com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking;

public class WebsiteUtils {
    public static String getWebsiteURL() {
        return "http://localhost:5173";
    }
    public static String getEndpoint(String endpoint) {
        return "http://localhost:5173/api/" + endpoint;
    }
}
