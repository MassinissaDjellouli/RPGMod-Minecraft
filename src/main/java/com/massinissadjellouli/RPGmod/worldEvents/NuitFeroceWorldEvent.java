package com.massinissadjellouli.RPGmod.worldEvents;

import com.massinissadjellouli.RPGmod.client.ClientLastTitleReceived;
import net.minecraft.ChatFormatting;

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

    }

}
