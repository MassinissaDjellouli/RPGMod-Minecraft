package com.massinissadjellouli.RPGmod.worldEvents;

import com.massinissadjellouli.RPGmod.client.ClientGamemodeData;
import com.massinissadjellouli.RPGmod.client.ClientLastTitleReceived;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.Random;

public abstract class WorldEvent {
    private boolean waitingForLevel = true;

    public boolean isWaitingForLevel() {
        return waitingForLevel;
    }

    public boolean isWaitingForPlayer() {
        return waitingForPlayer;
    }

    private boolean waitingForPlayer = true;
    private boolean loadedFromNBT = false;

    public boolean isLoadedFromNBT() {
        return loadedFromNBT;
    }

    protected abstract void launch();
    protected abstract void tick();
    protected abstract void loadData(CompoundTag nbt);
    protected abstract void saveData(CompoundTag nbt);
    protected abstract RPGModWorldEventType getEventType();
    protected abstract int[] getEventRange();
    protected static final int TICK_PER_SECONDS = 40;
    private static final int SECONDS_BEFORE_START = 5;
    private static final int SECONDS_TITLE_SHOWN = 3;
    private String name;
    private static WorldEvent lastEvent;
    public static WorldEvent ongoingEvent;
    protected int eventProgress = 0;
    protected boolean showTitle = false;
    private int startProgress = 0;
    private int titleShown = 0;
    protected ServerPlayer player;
    protected ServerLevel level;
    protected int duration;

    public static boolean ongoingEventIsStarting(){
        return ongoingEvent.startProgress < SECONDS_BEFORE_START * TICK_PER_SECONDS;
    }

    public static WorldEvent getLastEvent() {
        return lastEvent;
    }

    public static void endCurrentEventIfRunning() {
        if (ongoingEvent != null) {
            ongoingEvent.end();
        }
    }

    public WorldEvent(String name) {
        this.name = name;
    }


    public void setLevel(ServerLevel level) {
        waitingForLevel = false;
        this.level = level;
    }

    public void setPlayer(ServerPlayer player) {
        waitingForPlayer = false;
        this.player = player;
    }

    public static void currentEventTick() {
        if (ongoingEvent == null) return;
        if(ongoingEvent.waitingForPlayer || ongoingEvent.waitingForLevel){
            return;
        }
        ongoingEvent.loadedFromNBT = false;
        if (ongoingEvent.eventProgress >= ongoingEvent.duration) {
            ongoingEvent.end();
            return;
        }
        if (titleShown(ongoingEvent)) {
            return;
        }
        if (ongoingEventIsStarting()) {
            ongoingEvent.startProgress++;
            if (ongoingEvent.startProgress % TICK_PER_SECONDS == 0) {
                int secondsRemaining = SECONDS_BEFORE_START - ongoingEvent.startProgress / TICK_PER_SECONDS;
                ClientLastTitleReceived.set(secondsRemaining == 0
                        ? ""
                        : String.valueOf(secondsRemaining));
            }
            return;
        }
        ongoingEvent.tick();
        ongoingEvent.eventProgress++;
    }

    private static boolean titleShown(WorldEvent ongoingEvent) {
        if (!ongoingEvent.showTitle) {
            return false;
        }
        ongoingEvent.titleShown++;
        return ongoingEvent.titleShown < SECONDS_TITLE_SHOWN * TICK_PER_SECONDS;
    }



    public void start(boolean fromCommand) {
        if(loadedFromNBT && (waitingForLevel || waitingForPlayer)) {
            return;
        }
        if (ongoingEvent != null) {
            System.out.println("Event can't be launched. Ongoing event");
            return;
        }
        duration = getRandomDuration(getEventRange());
        if (!fromCommand &&
                (player == null ||
                        level == null || !ClientGamemodeData.isSurvival())) {
            System.out.println("Event " + this.getClass().toString() + " can't be launched");
            return;
        }
        ongoingEvent = this;
        try {
            launch();
        } catch (Exception e) {
            e.printStackTrace();
            end();
        }
    }


    public void end() {
        resetOngoingEvent();
        lastEvent = ongoingEvent;
        ongoingEvent = null;
    }

    protected int getRandomDuration(int[] range) {
        if (range.length != 2) {
            throw new IllegalArgumentException("Range needs to have 2 numbers. Minimum and maximum");
        }
        return new Random().nextInt(range[1] - range[0]) + range[0];
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

    public String getName() {
        return name;
    }


    public static void saveNBTData(CompoundTag nbt) {
        if (ongoingEvent == null) {
            return;
        }
        nbt.putString("name", ongoingEvent.name);
        nbt.putInt("event_progress", ongoingEvent.eventProgress);
        nbt.putInt("start_progress", ongoingEvent.startProgress);
        nbt.putInt("duration", ongoingEvent.duration);
        nbt.putBoolean("show_title", ongoingEvent.showTitle);
        nbt.putInt("title_shown", ongoingEvent.titleShown);
        nbt.putString("event_type", ongoingEvent.getEventType().name());
        ongoingEvent.saveData(nbt);
    }

    public static void loadNBTData(CompoundTag nbt) {
        if (nbt == null) {
            return;
        }
        try {
            RPGModWorldEventType eventType = RPGModWorldEventType.valueOf(nbt.getString("event_type"));
            ongoingEvent = eventType.getEvent();
            ongoingEvent.loadData(nbt);
            ongoingEvent.waitingForPlayer = ongoingEvent.player == null;
            ongoingEvent.waitingForLevel = ongoingEvent.level == null;
            ongoingEvent.loadedFromNBT = true;
            ongoingEvent.name = nbt.getString("name");
            ongoingEvent.eventProgress = nbt.getInt("event_progress");
            ongoingEvent.startProgress = nbt.getInt("start_progress");
            ongoingEvent.duration = nbt.getInt("duration");
            ongoingEvent.showTitle = nbt.getBoolean("show_title");
            ongoingEvent.titleShown = nbt.getInt("title_shown");
        }catch (Exception e){
            ongoingEvent = null;
        }
    }
    public boolean is(RPGModWorldEventType eventType){
        return RPGModWorldEventType.getName(eventType).equals(RPGModWorldEventType.getName(getEventType()));
    }
}
