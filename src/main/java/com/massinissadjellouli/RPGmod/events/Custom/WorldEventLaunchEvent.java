package com.massinissadjellouli.RPGmod.events.Custom;

import com.massinissadjellouli.RPGmod.worldEvents.RPGModWorldEventType;
import com.massinissadjellouli.RPGmod.worldEvents.WorldEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.Event;

public class WorldEventLaunchEvent extends Event {
    private final RPGModWorldEventType eventType;
    private final ServerPlayer player;
    private final ServerLevel level;
    private final boolean fromCommand;

    public WorldEventLaunchEvent(ServerPlayer player, ServerLevel level) {
        this.player = player;
        this.level = level;
        eventType = getRandomEventType(level);
        fromCommand = false;
    }

    public WorldEventLaunchEvent(ServerPlayer player, ServerLevel level, RPGModWorldEventType eventType) {
        this.player = player;
        this.level = level;
        this.eventType = eventType;
        fromCommand = true;
    }

    private RPGModWorldEventType getRandomEventType(Level level) {
        boolean invalidEvent = true;
        RPGModWorldEventType value = null;
        while (invalidEvent) {
            value = RPGModWorldEventType.values()[level.random.nextInt(RPGModWorldEventType.values().length)];
            if (WorldEvent.getLastEvent() != null && WorldEvent.getLastEvent().equals(value.getEvent())) {
                continue;
            }
            switch (value) {
                case SOLEIL_PUISSANT, ECLIPSE -> {
                    if (!level.isDay()) continue;
                }
                case INVASION_NETHER -> {
                    if (!level.dimension().location().toString().equals("minecraft:overworld")) continue;
                }
            }
            invalidEvent = false;
        }
        return value;
    }

    public void launch(boolean fromCommand) {
        WorldEvent event = eventType.getEvent();
        event.setLevel(level);
        event.setPlayer(player);
        event.start(fromCommand);
    }

    public boolean isFromCommand() {
        return fromCommand;
    }
}
