package com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking;

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

import java.util.UUID;

public class PlayerUIDCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    private String uid;
    public static Capability<String> PLAYER_UID = CapabilityManager.get(
            new CapabilityToken<>() {
            }
    );
    private final LazyOptional<String> optional = LazyOptional.of(this::createPlayerUID);

    private String createPlayerUID() {
        if (this.uid == null) {
            uid = UUID.randomUUID().toString().replace("-", "");
        }
        ClientPlayerUIDData.setUid(uid);
        return uid;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_UID) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("player_uid", createPlayerUID());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        uid = (nbt.getString("player_uid"));
        ClientPlayerUIDData.setUid(uid);
    }
}
