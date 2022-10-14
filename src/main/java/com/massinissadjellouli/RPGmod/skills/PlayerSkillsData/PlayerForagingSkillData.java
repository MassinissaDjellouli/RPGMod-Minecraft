package com.massinissadjellouli.RPGmod.skills.PlayerSkillsData;

import net.minecraft.nbt.CompoundTag;

import static com.massinissadjellouli.RPGmod.skills.PlayerSkillData.PlayerSkillEnum.FORAGING;

public class PlayerForagingSkillData extends SkillData{
    public int woodCut;

    public PlayerForagingSkillData(int level, int currentXp, int totalXp, int woodCut) {
        super(FORAGING,level, currentXp, totalXp);
        this.woodCut = woodCut;
    }

    public PlayerForagingSkillData() {
        woodCut = 0;
    }

    @Override
    public void fillNbtTag(CompoundTag nbt) {
        nbt.putInt("ForagingLevel",level);
        nbt.putInt("CurrentForagingXP",currentXp);
        nbt.putInt("TotalForagingXP",totalXp);
        nbt.putInt("WoodCut",woodCut);
    }
}
