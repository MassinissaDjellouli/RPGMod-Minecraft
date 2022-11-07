package com.massinissadjellouli.RPGmod.worldEvents;

import com.massinissadjellouli.RPGmod.client.ClientLastTitleReceived;
import net.minecraft.ChatFormatting;

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
        return new int[]{5000,6000};
    }
}
