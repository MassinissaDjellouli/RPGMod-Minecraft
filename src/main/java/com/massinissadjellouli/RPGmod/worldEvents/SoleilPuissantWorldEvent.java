package com.massinissadjellouli.RPGmod.worldEvents;

import com.massinissadjellouli.RPGmod.client.ClientLastTitleReceived;
import net.minecraft.ChatFormatting;
import net.minecraft.server.level.ServerLevel;
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
        super("Soleil puissant");
        showTitle = true;
    }
    @Override
    protected void tick() {
        burnUnderSun(player,level);
    }

    @Override
    protected int[] getEventRange() {
        return new int[]{900,1700};
    }

    public static void burnUnderSun(ServerPlayer player,ServerLevel level){
        if(!isUnderBlock(player,level)) {
            player.setSecondsOnFire(1);
        }
    }
    private static boolean isUnderBlock(ServerPlayer player, ServerLevel level) {
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
