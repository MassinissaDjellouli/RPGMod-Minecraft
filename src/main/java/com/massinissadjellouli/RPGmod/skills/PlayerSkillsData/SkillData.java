package com.massinissadjellouli.RPGmod.skills.PlayerSkillsData;

import net.minecraft.nbt.CompoundTag;

public abstract class SkillData {
    public int level;
    public int currentXp;
    public int totalXp;

    public SkillData(int level, int currentXp, int totalXp) {
        this.level = level;
        this.currentXp = currentXp;
        this.totalXp = totalXp;
    }

    public SkillData() {
        level = 0;
        currentXp = 0;
        totalXp = 0;
    }

    public abstract void fillNbtTag(CompoundTag nbt);
}
