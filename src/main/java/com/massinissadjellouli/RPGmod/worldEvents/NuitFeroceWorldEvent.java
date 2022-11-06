package com.massinissadjellouli.RPGmod.worldEvents;

import com.massinissadjellouli.RPGmod.client.ClientLastTitleReceived;
import net.minecraft.ChatFormatting;

import static com.massinissadjellouli.RPGmod.worldEvents.SoleilPuissantWorldEvent.burnUnderSun;

public class NuitFeroceWorldEvent extends WorldEvent {
    public NuitFeroceWorldEvent() {
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
    protected int[] getEventRange() {
        return new int[]{2000,5000};
    }

}
