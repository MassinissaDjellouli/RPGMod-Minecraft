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

public class PlayerUIDCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    private String uid;
    public static Capability<String> PLAYER_UID = CapabilityManager.get(
            new CapabilityToken<>() {
            }
    );
    private final LazyOptional<String> optional = LazyOptional.of(this::createPlayerUID);

    private String createPlayerUID() {
        if (this.uid == null) {
            uid = generateId();
        }
        ClientPlayerUIDData.setUid(uid);
        return uid;
    }

    private String generateId() {
        List<Character> letters = new ArrayList<>();
        List<Character> numbers = new ArrayList<>();
        String longId = UUID.randomUUID().toString().replace("-", "");
        for (char c : longId.toCharArray()) {
            if (Character.isDigit(c)) {
                numbers.add(c);
            }else {
                letters.add(c);
            }
        }
        Optional<Integer> reduce = numbers.stream().map(character -> Integer.parseInt(character.toString())).reduce(Integer::sum);
        int sum = reduce.orElse(0);
        return letters.stream().map(String::valueOf).reduce("", String::concat) + sum;
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
