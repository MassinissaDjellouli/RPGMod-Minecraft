package com.massinissadjellouli.RPGmod.worldEvents;

import com.massinissadjellouli.RPGmod.client.ClientGamemodeData;
import com.massinissadjellouli.RPGmod.client.ClientLastTitleReceived;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.Random;

public abstract class WorldEvent {
    protected static final int TICK_PER_SECONDS = 40;
    private static final int SECONDS_BEFORE_START = 5;
    private static final int SECONDS_TITLE_SHOWN = 3;
    private static WorldEvent lastEvent;
    public static WorldEvent ongoingEvent;
    protected int eventProgress = 0;
    protected boolean showTitle = false;
    private int startProgress = 0;
    private int titleShown = 0;
    protected ServerPlayer player;
    protected ServerLevel level;
    protected int duration;

    public static WorldEvent getLastEvent() {
        return lastEvent;
    }

    public static void endCurrentEventIfRunning() {
        if(ongoingEvent != null){
            ongoingEvent.end();
        }
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
        if(titleShown(ongoingEvent)){
            return;
        }
        if(ongoingEvent.startProgress < ongoingEvent.SECONDS_BEFORE_START * TICK_PER_SECONDS){
            ongoingEvent.startProgress++;
            if(ongoingEvent.startProgress % TICK_PER_SECONDS == 0){
                int secondsRemaining = ongoingEvent.SECONDS_BEFORE_START - ongoingEvent.startProgress / TICK_PER_SECONDS;
                ClientLastTitleReceived.set(secondsRemaining == 0
                        ?""
                        :String.valueOf(secondsRemaining));
            }
            return;
        }
        ongoingEvent.tick();
        ongoingEvent.eventProgress++;
    }

    private static boolean titleShown(WorldEvent ongoingEvent) {
        if(!ongoingEvent.showTitle){
            return false;
        }
        ongoingEvent.titleShown++;
        return ongoingEvent.titleShown  < ongoingEvent.SECONDS_TITLE_SHOWN * TICK_PER_SECONDS;
    }

    protected abstract void launch();
    protected abstract void tick();

    public void start(boolean fromCommand) {
        if(ongoingEvent != null){
            System.out.println("Event can't be launched. Ongoing event");
            return;
        }
        duration = getRandomDuration(1000, 2000);
        if(!fromCommand &&
                (player == null ||
                        level == null|| !ClientGamemodeData.isSurvival())) {
            System.out.println("Event " + this.getClass().toString() + " can't be launched");
            return;
        }
        ongoingEvent = this;
        launch();
    }
    public void end() {
        resetOngoingEvent();
        lastEvent = ongoingEvent;
        ongoingEvent = null;
    }

    protected int getRandomDuration(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    public String getRemainingTime() {
        int ticksLeft = duration - eventProgress;
        int additionnalTicks = ticksLeft % TICK_PER_SECONDS;
        return ((ticksLeft - additionnalTicks) / TICK_PER_SECONDS) + "s";
    }

    void resetOngoingEvent() {
        ongoingEvent.eventProgress = 0;
        ongoingEvent.startProgress = 0;
        ongoingEvent.showTitle = false;
        ongoingEvent.level = null;
        ongoingEvent.player = null;
        ongoingEvent.duration = 0;
    }
}
