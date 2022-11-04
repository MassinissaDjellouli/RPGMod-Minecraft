package com.massinissadjellouli.RPGmod.events.Custom;

import com.massinissadjellouli.RPGmod.events.Custom.WorldEvents.RPGModWorldEventType;
import com.massinissadjellouli.RPGmod.events.Custom.WorldEvents.WorldEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.Event;

import java.util.HashMap;

public class RandomEventLaunchEvent extends Event {
    private final RPGModWorldEventType eventType;
    private final ServerPlayer player;
    private final ServerLevel level;
    public RandomEventLaunchEvent(ServerPlayer player, ServerLevel level) {
        this.player = player;
        this.level = level;
        eventType = getEventType(level);
    }

    private RPGModWorldEventType getEventType(Level level) {
        boolean invalidEvent = true;
        RPGModWorldEventType value = null;
        while (invalidEvent){
            value = RPGModWorldEventType.values()[level.random.nextInt(RPGModWorldEventType.values().length)];
            invalidEvent = false;
        }
        return value;
    }

    public void launch() {
        WorldEvent event = eventType.getEvent();
        event.setLevel(level);
        event.setPlayer(player);
        event.launch();
    }
}
