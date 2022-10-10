package com.massinissadjellouli.RPGmod.client;

public class ClientLastMessageReceived {
    private static String lastMessage = "";
    private static boolean important = false;
    private static int ticksSinceLastMessage;
    private static final int TICKS_BEFORE_DELETE = 100;
    public static String get() {
        return lastMessage;
    }

    public static void set(String lastMessage) {
        if(important){
            return;
        }
        ticksSinceLastMessage = 0;
        ClientLastMessageReceived.lastMessage = lastMessage;
    }
    public static void setImportant(String lastMessage) {
        ticksSinceLastMessage = 0;
        ClientLastMessageReceived.lastMessage = lastMessage;
        important = true;
    }
    public static boolean isLastMessageEmpty(){
        return lastMessage.isEmpty();
    }
    public static void incrementTick(){
        if(isLastMessageEmpty()){
            return;
        }
        ticksSinceLastMessage++;
        if(ticksSinceLastMessage >= TICKS_BEFORE_DELETE){
            ticksSinceLastMessage = 0;
            lastMessage = "";
            important = false;
        }
    }

    public static boolean isImportant() {
        return important;
    }
}
