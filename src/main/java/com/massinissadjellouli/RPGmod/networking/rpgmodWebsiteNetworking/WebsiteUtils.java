package com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking;

public class WebsiteUtils {
    public static final String MINING = "Minage";
    public static final String FORAGING = "Buchage";
    public static final String COMBAT = "Combat";
    public static String getEndpoint(String endpoint) {
        return "http://localhost:5555/api/" + endpoint;
    }
}
