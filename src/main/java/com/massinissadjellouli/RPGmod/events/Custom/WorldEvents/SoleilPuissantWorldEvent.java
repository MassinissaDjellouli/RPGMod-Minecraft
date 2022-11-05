package com.massinissadjellouli.RPGmod.events.Custom.WorldEvents;

import net.minecraft.server.level.ServerPlayer;

public class SoleilPuissantWorldEvent extends WorldEvent {
    @Override
    protected void launch() {
        level.setDayTime(12000);
        System.out.println("Soleil Puissant World Event launched");
    }
    public SoleilPuissantWorldEvent() {
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
            if (!level.isEmptyBlock(player.blockPosition().above())){
                return true;
            }
        }
        return false;
    }

}
