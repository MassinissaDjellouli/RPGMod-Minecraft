package com.massinissadjellouli.RPGmod.thirst;

import net.minecraft.nbt.CompoundTag;

public class PlayerThirst {
    public final static int MIN_THIRST = 0;
    public final static int MAX_THIRST = 20;
    private static int reduceByTick = 1;
    private static int ticksToReduce = 1000;
    private int thirst = MAX_THIRST;

    private final int DEFAULT_TICK_TO_REDUCE_TIME = 1000;

    public void addThirst(int toAdd){
        thirst = Math.min(thirst + toAdd,MAX_THIRST);
    }
    public void reduceThirst(int toReduce){
        thirst = Math.max(thirst - toReduce,MIN_THIRST);
        System.out.println(thirst);
    }
    public void copyFrom(PlayerThirst playerThirst){
        thirst = playerThirst.thirst;
    }
    public void saveNBTData(CompoundTag nbt){
        nbt.putInt("thirst",thirst);
    }
    public void loadNBTData(CompoundTag nbt){
        thirst = nbt.getInt("thirst");
    }
    public void reduceTicks(){
        ticksToReduce -= reduceByTick;
        if(ticksToReduce <= 0){
            ticksToReduce = DEFAULT_TICK_TO_REDUCE_TIME;
            reduceThirst(1);
        }
    }
    public static void setReduceByTick(int tick){
        reduceByTick = tick;
    }
    public int getThirstLevel(){
        return thirst;
    }
}
