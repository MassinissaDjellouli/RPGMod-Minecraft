package com.massinissadjellouli.RPGmod.worldEvents;

import com.massinissadjellouli.RPGmod.client.ClientLastTitleReceived;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;

public class NuitFeroceWorldEvent extends WorldEvent {
    public NuitFeroceWorldEvent() {
        super("Nuit féroce");
        showTitle = true;
    }


    @Override
    protected void launch() {
        ClientLastTitleReceived.set("Attention! Un event nuit féroce commence!",
                "Des monstres féroces vont aparaitres!",
                ChatFormatting.AQUA);
        System.out.println("Nuit Feroce World Event launched");
    }

    @Override
    protected void tick() {
        level.setDayTime(42000);
    }

    @Override
    protected void loadData(CompoundTag nbt) {

    }

    @Override
    protected int[] getEventRange() {
        return new int[]{2000, 5000};
    }

    @Override
    protected void saveData(CompoundTag nbt) {

    }

    @Override
    protected RPGModWorldEventType getEventType() {
        return RPGModWorldEventType.NUIT_FEROCE;
    }

}
