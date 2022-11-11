package com.massinissadjellouli.RPGmod.worldEvents;

import com.massinissadjellouli.RPGmod.client.ClientLastTitleReceived;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;

public class GoldRushWorldEvent extends WorldEvent {
    public GoldRushWorldEvent() {
        super("Gold Rush");
        showTitle = true;
    }

    @Override
    protected void launch() {
        ClientLastTitleReceived.set("Un event gold rush commence!",
                "À chaque bloc miné, vous avez une chance d'obtenir un bonus!",
                ChatFormatting.GOLD);
        System.out.println("Gold Rush World Event launched");
    }

    @Override
    protected void tick() {

    }


    @Override
    protected int[] getEventRange() {
        return new int[]{5000, 6000};
    }

    @Override
    protected void saveData(CompoundTag nbt) {}
    @Override
    protected void loadData(CompoundTag nbt) {}

    @Override
    protected RPGModWorldEventType getEventType() {
        return RPGModWorldEventType.GOLD_RUSH;
    }
}
