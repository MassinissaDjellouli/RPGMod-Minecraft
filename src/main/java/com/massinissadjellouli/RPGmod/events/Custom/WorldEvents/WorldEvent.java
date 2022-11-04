package com.massinissadjellouli.RPGmod.events.Custom.WorldEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public abstract class WorldEvent {
    ServerPlayer player;
    ServerLevel level;

    public void setLevel(ServerLevel level) {
        this.level = level;
    }

    public void setPlayer(ServerPlayer player) {
        this.player = player;
    }
    public abstract void launch();

}
