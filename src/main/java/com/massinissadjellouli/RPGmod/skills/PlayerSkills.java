package com.massinissadjellouli.RPGmod.skills;

import com.massinissadjellouli.RPGmod.skills.PlayerSkillData.PlayerSkillEnum;
import com.massinissadjellouli.RPGmod.skills.PlayerSkillsData.SkillData;
import net.minecraft.nbt.CompoundTag;

public class PlayerSkills {
    private PlayerSkillData playerSkillData = new PlayerSkillData();

    public void blockMined(){
        playerSkillData.playerMiningSkillData.blocksMined++;
    }

    public void woodCut(){
        playerSkillData.playerForagingSkillData.woodCut++;
    }

    public void entityKilled(){
        playerSkillData.playerAttackSkillData.entitiesKilled++;
    }

    private int getXpNeededForNextLevel(int level){
        float xpNeededPerLevel = 150f;
        for (int i = 0; i < level; i++) {
            float XP_PERCENT_INCREASE_PER_LEVEL = 60f;
            xpNeededPerLevel += xpNeededPerLevel * (XP_PERCENT_INCREASE_PER_LEVEL / 100f);
        }
        return Math.round(xpNeededPerLevel);
    }

    public void addXP(int xp, PlayerSkillEnum skill){
        switch (skill){
            case Mining -> increaseXp(xp, playerSkillData.playerMiningSkillData);
            case Foraging -> increaseXp(xp, playerSkillData.playerForagingSkillData);
            case Attacking -> increaseXp(xp, playerSkillData.playerAttackSkillData);
        }
    }

    private void increaseXp(int xp, SkillData dataToIncrease){
        dataToIncrease.currentXp += xp;
        dataToIncrease.totalXp += xp;
        if(dataToIncrease.currentXp >=
                getXpNeededForNextLevel(dataToIncrease.level)){
            dataToIncrease.currentXp = 0;
            dataToIncrease.level++;
            System.out.println("LEVEL UP! lvl:" + dataToIncrease.level);
        }
    }
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
