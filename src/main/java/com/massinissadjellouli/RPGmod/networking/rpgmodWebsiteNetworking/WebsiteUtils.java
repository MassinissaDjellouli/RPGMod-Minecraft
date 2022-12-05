package com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking;

public class WebsiteUtils {
    public static final String MINING = "Mineur";
    public static final String FORAGING = "Bucheron";
    public static final String COMBAT = "Soldat";
    public static String getEndpoint(String endpoint) {
        return "https://morning-scrubland-27211.herokuapp.com/api/" + endpoint;
    }
}
