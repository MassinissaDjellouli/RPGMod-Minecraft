package com.massinissadjellouli.RPGmod.classSystem;

import com.massinissadjellouli.RPGmod.thirst.PlayerThirst;
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

public class PlayerClassProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerClass> PLAYER_CLASS = CapabilityManager.get(
            new CapabilityToken<>() {}
    );

    private PlayerClass playerClass;
    private final LazyOptional<PlayerClass> optional = LazyOptional.of(this::createPlayerClass);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_CLASS){
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerClass().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerClass().loadNBTData(nbt);
    }
    private @NotNull PlayerClass createPlayerClass() {
        if(this.playerClass == null){
            playerClass = new PlayerClass();
        }
        return playerClass;
    }



}
