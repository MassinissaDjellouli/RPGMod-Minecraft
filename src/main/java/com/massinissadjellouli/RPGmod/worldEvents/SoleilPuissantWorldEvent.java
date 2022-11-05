package com.massinissadjellouli.RPGmod.worldEvents;

import com.massinissadjellouli.RPGmod.client.ClientLastTitleReceived;
import net.minecraft.ChatFormatting;
import net.minecraft.server.level.ServerPlayer;

public class SoleilPuissantWorldEvent extends WorldEvent {
    @Override
    protected void launch() {
        ClientLastTitleReceived.set("Attention! Un event soleil puissant commence!",
                " Abritez vous du soleil ou vous allez bruler!",
                ChatFormatting.RED);
        level.setDayTime(0);
    }
    public SoleilPuissantWorldEvent() {
        showTitle = true;
        duration = getRandomDuration(1000, 2000);
    }
    @Override
    protected void tick() {
        if(!isUnderBlock(player)) {
            player.setSecondsOnFire(1);
        }
    }

    private boolean isUnderBlock(ServerPlayer player) {
        int maxBuildHeight = level.getMaxBuildHeight();
        int y = player.blockPosition().getY();
        int blocksOverPlayer = maxBuildHeight - y;
        for (int i = 0; i < blocksOverPlayer; i++) {
            if (!level.isEmptyBlock(player.blockPosition().above(i))){
                return true;
            }
        }
        return false;
    }

}
