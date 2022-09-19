package com.massinissadjellouli.RPGmod.skills.PlayerSkillsData;

import com.massinissadjellouli.RPGmod.skills.PlayerSkillData.PlayerSkillEnum;
import net.minecraft.nbt.CompoundTag;

public abstract class SkillData {
    public PlayerSkillEnum skill;
    public int level;
    public int currentXp;
    public int totalXp;

    public SkillData(PlayerSkillEnum skill, int level, int currentXp, int totalXp) {
        this.skill = skill;
        this.level = level;
        this.currentXp = currentXp;
        this.totalXp = totalXp;
    }

    public SkillData() {
        skill = PlayerSkillEnum.MINING;
        level = 0;
        currentXp = 0;
        totalXp = 0;
    }

    public abstract void fillNbtTag(CompoundTag nbt);
}
