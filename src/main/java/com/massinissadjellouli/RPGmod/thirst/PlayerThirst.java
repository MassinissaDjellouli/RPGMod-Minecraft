package com.massinissadjellouli.RPGmod.thirst;

import net.minecraft.nbt.CompoundTag;

public class PlayerThirst {
    public final static int DELAY_JUMP = 60;
    public final static int MIN_THIRST = 0;
    public final static int MAX_THIRST = 20;
    public static final float DEFAULT_TICK_TO_REDUCE_TIME = 1500f;
    public static final float MAX_REDUCE = 10.5f;
    private static float reduceByTick = 1;
    private static float ticksToReduce = DEFAULT_TICK_TO_REDUCE_TIME;
    private int thirst = MAX_THIRST;
    private static int ticksSinceLastJump = 0;

    public static int getTicksSinceLastJump(){
        return ticksSinceLastJump;
    }
    public static void addTickSinceLastJump(int add) {
        ticksSinceLastJump += add;

    }

    public static float getReduceByTick() {
        return reduceByTick;
    }

    public void addThirst(int toAdd){
        thirst = Math.min(thirst + toAdd,MAX_THIRST);
    }
    public void reduceThirst(int toReduce){
        thirst = Math.max(thirst - toReduce,MIN_THIRST);
        System.out.println(thirst + ":" + reduceByTick);
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
    public static void setReduceByTick(float tick){
        reduceByTick = Math.min(tick,PlayerThirst.MAX_REDUCE);
    }
    public int getThirstLevel(){
        return thirst;
    }
}
