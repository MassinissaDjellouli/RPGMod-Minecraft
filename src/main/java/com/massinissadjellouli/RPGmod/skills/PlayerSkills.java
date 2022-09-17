package com.massinissadjellouli.RPGmod.skills;

import net.minecraft.nbt.CompoundTag;

public class PlayerSkills {
    private PlayerSkillData playerSkillData;

    public void copyFrom(PlayerSkills playerSkills){
        playerSkillData = playerSkills.playerSkillData;
    }
    public void saveNBTData(CompoundTag nbt){
        playerSkillData.fillNbtTag(nbt);
    }
    public void loadNBTData(CompoundTag nbt){
        playerSkillData = PlayerSkillData.loadNbt(nbt);
    }
}
