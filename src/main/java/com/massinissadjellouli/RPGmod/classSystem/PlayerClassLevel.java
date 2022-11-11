package com.massinissadjellouli.RPGmod.classSystem;

import net.minecraft.nbt.CompoundTag;

public class PlayerClassLevel {
    private int level;
    private int xp;
    public static final int XP_THRESHOLD = 6000;

    public PlayerClassLevel(int level, int xp) {
        this.level = level;
        this.xp = xp;
    }

    public int getLevel() {
        return level;
    }

    public void levelUp() {
        this.level++;
    }

    public int getXp() {
        return xp;
    }

    public void addXp(int xp) {
        this.xp += xp;
    }

    public void resetXp(int leftover) {
        this.xp = leftover;
    }

    public static PlayerClassLevel loadNBT(CompoundTag nbt, PlayerClassType playerClassType) {
        int level = nbt.getInt(playerClassType.name() + "_level");
        int xp = nbt.getInt(playerClassType.name() + "_xp");
        return new PlayerClassLevel(level, xp);
    }

    public CompoundTag saveData(PlayerClassType playerClassType) {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putInt(playerClassType.name() + "_level", level);
        compoundTag.putInt(playerClassType.name() + "_xp", xp);
        return compoundTag;
    }
}
