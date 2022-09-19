package com.massinissadjellouli.RPGmod.skills.PlayerSkillsData;

import net.minecraft.nbt.CompoundTag;

import static com.massinissadjellouli.RPGmod.skills.PlayerSkillData.PlayerSkillEnum;

public class PlayerMiningSkillData extends SkillData{
    public int blocksMined;

    public PlayerMiningSkillData(int level, int currentXp, int totalXp, int blocksMined) {
        super(PlayerSkillEnum.MINING,level, currentXp, totalXp);
        this.blocksMined = blocksMined;
    }

    public PlayerMiningSkillData() {
        blocksMined = 0;
    }

    @Override
    public void fillNbtTag(CompoundTag nbt) {
        nbt.putInt("MiningLevel",level);
        nbt.putInt("CurrentMiningXP",currentXp);
        nbt.putInt("TotalMiningXP",totalXp);
        nbt.putInt("BlocksMined",blocksMined);
    }
}
