package com.massinissadjellouli.RPGmod.worldEvents;

import com.massinissadjellouli.RPGmod.client.ClientLastTitleReceived;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;

import static com.massinissadjellouli.RPGmod.worldEvents.SoleilPuissantWorldEvent.burnUnderSun;

public class EclipseWorldEvent extends WorldEvent {
    private long currentDayTime;

    public EclipseWorldEvent() {
        super("Éclipse solaire");
        showTitle = true;
    }

    @Override
    protected void launch() {
        currentDayTime = level.getDayTime();
        ClientLastTitleReceived.set("Attention! Une éclipse solaire commence!",
                "La lune va vous bruler!",
                ChatFormatting.GOLD);
        System.out.println("Eclipse World Event launched");
    }

    @Override
    protected void tick() {
        level.setDayTime(42000);
        burnUnderSun(player, level);
    }



    @Override
    protected int[] getEventRange() {
        return new int[]{1000, 1500};
    }

    @Override
    public void end() {
        level.setDayTime(currentDayTime);
        super.end();
        currentDayTime = 0;
    }

    @Override
    protected void saveData(CompoundTag nbt) {
        nbt.putLong("current_day_time", currentDayTime);
    }
    @Override
    protected void loadData(CompoundTag nbt) {
        currentDayTime = nbt.getLong("current_day_time");
    }
    @Override
    protected RPGModWorldEventType getEventType() {
        return RPGModWorldEventType.ECLIPSE;
    }
}
