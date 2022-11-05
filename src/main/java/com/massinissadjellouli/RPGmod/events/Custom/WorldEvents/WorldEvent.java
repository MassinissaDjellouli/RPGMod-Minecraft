package com.massinissadjellouli.RPGmod.events.Custom.WorldEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.Random;

public abstract class WorldEvent {
    private static WorldEvent lastEvent;
    public static WorldEvent ongoingEvent;
    protected int eventProgress = 0;
    protected ServerPlayer player;
    protected ServerLevel level;
    protected int duration;
    protected boolean active = false;

    public static WorldEvent getLastEvent() {
        return lastEvent;
    }

    public void setLevel(ServerLevel level) {
        this.level = level;
    }

    public void setPlayer(ServerPlayer player) {
        this.player = player;
    }

    public static void currentEventTick() {
        if (ongoingEvent == null) return;
        if (ongoingEvent.eventProgress >= ongoingEvent.duration) {
            ongoingEvent.end();
            return;
        }
        ongoingEvent.tick();
        ongoingEvent.eventProgress++;
    }
    protected abstract void launch();
    protected abstract void tick();

    public void start() {
        if(ongoingEvent != null || player == null || level == null || duration == 0) {
            System.out.println("Event can't be launched");
            return;
        }
        ongoingEvent = this;
        lastEvent = this;
        active = true;
        launch();
    }
    public void end() {
        ongoingEvent = null;
    }

    protected int getRandomDuration(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }
}
