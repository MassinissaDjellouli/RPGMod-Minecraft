package com.massinissadjellouli.RPGmod.worldEvents;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WorldEventCapablityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<WorldEvent> WORLD_EVENT = CapabilityManager.get(
            new CapabilityToken<>() {
            }
    );

    private WorldEvent ongoingWorldEvent;
    private final LazyOptional<WorldEvent> optional = LazyOptional.of(this::createWorldEvent);

    private WorldEvent createWorldEvent() {
        if (this.ongoingWorldEvent == null && WorldEvent.ongoingEvent != null) {
            ongoingWorldEvent = WorldEvent.ongoingEvent;
        }
        return ongoingWorldEvent;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == WORLD_EVENT) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        WorldEvent.saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        WorldEvent.loadNBTData(nbt);
    }
}
