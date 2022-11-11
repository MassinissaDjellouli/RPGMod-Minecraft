package com.massinissadjellouli.RPGmod.skills.PlayerSkillsData;

import com.massinissadjellouli.RPGmod.skills.PlayerSkillData.PlayerSkillEnum;
import net.minecraft.nbt.CompoundTag;

public class PlayerAttackSkillData extends SkillData {
    public int entitiesKilled;

    public PlayerAttackSkillData(int level, int currentXp, int totalXp, int entitiesKilled) {
        super(PlayerSkillEnum.ATTACKING, level, currentXp, totalXp);
        this.entitiesKilled = entitiesKilled;
    }

    public PlayerAttackSkillData() {
        entitiesKilled = 0;
    }

    @Override
    public void fillNbtTag(CompoundTag nbt) {
        nbt.putInt("AttackingLevel", level);
        nbt.putInt("CurrentAttackingXP", currentXp);
        nbt.putInt("TotalAttackingXP", totalXp);
        nbt.putInt("BlocksMined", entitiesKilled);
    }
}
