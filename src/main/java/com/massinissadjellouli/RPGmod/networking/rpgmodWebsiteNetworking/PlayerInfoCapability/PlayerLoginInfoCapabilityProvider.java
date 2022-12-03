package com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.PlayerInfoCapability;

import com.massinissadjellouli.RPGmod.client.renderer.ClientPlayerUIDData;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PlayerLoginInfoCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    private PlayerLoginInfo playerLoginInfo;
    public static Capability<PlayerLoginInfo> PLAYER_LOGIN_INFO = CapabilityManager.get(
            new CapabilityToken<>() {
            }
    );
    private final LazyOptional<PlayerLoginInfo> optional = LazyOptional.of(this::createPlayerLoginInfo);

    private PlayerLoginInfo createPlayerLoginInfo() {
        if (this.playerLoginInfo == null) {
            playerLoginInfo = new PlayerLoginInfo();
        }
        return playerLoginInfo;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_LOGIN_INFO) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerLoginInfo().serialize(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
         createPlayerLoginInfo().deserializeNBT(nbt.getCompound("player_login_info"));
    }
}
