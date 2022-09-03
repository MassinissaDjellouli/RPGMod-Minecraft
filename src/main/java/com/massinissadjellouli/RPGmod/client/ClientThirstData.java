package com.massinissadjellouli.RPGmod.client;

public class ClientThirstData {
    private static int playerThirst;

    public static void set(int thirst){
        playerThirst = thirst;
    }
    public static int get(){
        return playerThirst;
    }


}
