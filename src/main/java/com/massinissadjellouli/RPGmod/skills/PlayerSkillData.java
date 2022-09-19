package com.massinissadjellouli.RPGmod.skills;

import com.massinissadjellouli.RPGmod.skills.PlayerSkillsData.PlayerAttackSkillData;
import com.massinissadjellouli.RPGmod.skills.PlayerSkillsData.PlayerForagingSkillData;
import com.massinissadjellouli.RPGmod.skills.PlayerSkillsData.PlayerMiningSkillData;
import net.minecraft.nbt.CompoundTag;

public class PlayerSkillData {

    public PlayerAttackSkillData playerAttackSkillData;
    public PlayerMiningSkillData playerMiningSkillData;
    public PlayerForagingSkillData playerForagingSkillData;

    public enum PlayerSkillEnum{
        MINING,
        ATTACKING,
        FORAGING
    }
    public void fillNbtTag(CompoundTag nbt) {
        playerAttackSkillData.fillNbtTag(nbt);
        playerMiningSkillData.fillNbtTag(nbt);
        playerForagingSkillData.fillNbtTag(nbt);
    }

    public PlayerSkillData() {
        playerAttackSkillData = new PlayerAttackSkillData();
        playerMiningSkillData = new PlayerMiningSkillData();
        playerForagingSkillData = new PlayerForagingSkillData();
    }

    public PlayerSkillData(
            PlayerAttackSkillData playerAttackSkillData,
            PlayerMiningSkillData playerMiningSkillData,
            PlayerForagingSkillData playerForagingSkillData) {
        this.playerAttackSkillData = playerAttackSkillData;
        this.playerMiningSkillData = playerMiningSkillData;
        this.playerForagingSkillData = playerForagingSkillData;
    }

    public static PlayerSkillData loadNbt(CompoundTag nbt) {
        return new PlayerSkillData(
                new PlayerAttackSkillData(
                        nbt.getInt("AttackingLevel"),
                        nbt.getInt("CurrentAttackingXP"),
                        nbt.getInt("TotalAttackingXP"),
                        nbt.getInt("EntitiesKilled")
                ),
                new PlayerMiningSkillData(
                        nbt.getInt("MiningLevel"),
                        nbt.getInt("CurrentMiningXP"),
                        nbt.getInt("TotalMiningXP"),
                        nbt.getInt("BlocksMined")
                ),
                new PlayerForagingSkillData(
                        nbt.getInt("ForagingLevel"),
                        nbt.getInt("CurrentForagingXP"),
                        nbt.getInt("TotalForagingXP"),
                        nbt.getInt("WoodCut")
                ));
    }
}
