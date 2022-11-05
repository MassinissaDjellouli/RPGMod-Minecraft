package com.massinissadjellouli.RPGmod.client;

import net.minecraft.ChatFormatting;

public class ClientLastTitleReceived {
    private static String lastTitle = "";
    private static ChatFormatting color = ChatFormatting.GOLD;
    private static int ticksSinceLastMessage;
    private static final int TICKS_BEFORE_DELETE = 200;
    private static String lastSubTitle = "";

    public static String getSubtitle() {
        return lastSubTitle;
    }

    public static void set(String lastMessage) {
        reset();
        ClientLastTitleReceived.lastTitle = lastMessage;

    }
    public static void set(String lastMessage,ChatFormatting color) {
        reset();
        ClientLastTitleReceived.lastTitle = lastMessage;
        ClientLastTitleReceived.color = color;
    }
    public static void set(String lastMessage,String lastSubtitle) {
        reset();
        ClientLastTitleReceived.lastTitle = lastMessage;
        ClientLastTitleReceived.lastSubTitle = lastSubtitle;
    }
    public static void set(String lastMessage,String lastSubtitle,ChatFormatting color) {
        reset();
        ClientLastTitleReceived.lastTitle = lastMessage;
        ClientLastTitleReceived.lastSubTitle = lastSubtitle;
        ClientLastTitleReceived.color = color;
    }
    private static boolean isLastMessageEmpty(){
        return lastTitle.isEmpty();
    }
    public static void incrementTick(){
        if(isLastMessageEmpty()){
            return;
        }
        ticksSinceLastMessage++;
        if(ticksSinceLastMessage >= TICKS_BEFORE_DELETE){
            reset();
        }
    }

    private static void reset() {
        ticksSinceLastMessage = 0;
        lastTitle = "";
        lastSubTitle = "";
        color = ChatFormatting.GOLD;
    }

    public static String getTitle() {
        return lastTitle;
    }

    public static ChatFormatting getTitleColor() {
        return color;
    }
}
