package com.massinissadjellouli.RPGmod.skills;

import com.massinissadjellouli.RPGmod.client.ClientGamemodeData;
import com.massinissadjellouli.RPGmod.client.ClientLastMessageReceived;
import com.massinissadjellouli.RPGmod.events.RPGModEventFactory;
import com.massinissadjellouli.RPGmod.skills.PlayerSkillData.PlayerSkillEnum;
import com.massinissadjellouli.RPGmod.skills.PlayerSkillsData.SkillData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public class PlayerSkills {
    private PlayerSkillData playerSkillData = new PlayerSkillData();

    public void blockMined() {
        playerSkillData.playerMiningSkillData.blocksMined++;
    }

    public void woodCut() {
        playerSkillData.playerForagingSkillData.woodCut++;
    }

    public void entityKilled() {
        playerSkillData.playerAttackSkillData.entitiesKilled++;
    }

    public Optional<SkillData> getSkill(PlayerSkillEnum skill) {
        switch (skill) {
            case MINING -> {
                return Optional.of(playerSkillData.playerMiningSkillData);
            }
            case ATTACKING -> {
                return Optional.of(playerSkillData.playerAttackSkillData);
            }
            case FORAGING -> {
                return Optional.of(playerSkillData.playerForagingSkillData);
            }
        }
        return Optional.empty();
    }

    private int getXpNeededForNextLevel(int level) {
        float xpNeededPerLevel = 150f;
        for (int i = 0; i < level; i++) {
            float XP_PERCENT_INCREASE_PER_LEVEL = 25f;
            xpNeededPerLevel += xpNeededPerLevel * (XP_PERCENT_INCREASE_PER_LEVEL / 100f);
        }
        return Math.round(xpNeededPerLevel);
    }

    public void addXP(int xp, PlayerSkillEnum skill, Player player) {
        if (!ClientGamemodeData.isSurvival()) {
            return;
        }
        switch (skill) {
            case MINING -> increaseXp(xp, playerSkillData.playerMiningSkillData, player, skill);
            case FORAGING -> increaseXp(xp, playerSkillData.playerForagingSkillData, player, skill);
            case ATTACKING -> increaseXp(xp, playerSkillData.playerAttackSkillData, player, skill);
        }

    }

    private String getTranslatedEnum(PlayerSkillEnum skill) {
        switch (skill) {
            case MINING -> {
                return "de minage";
            }
            case FORAGING -> {
                return "de bûchage";
            }
            case ATTACKING -> {
                return "d'attaque";
            }
        }
        return "";
    }

    private void increaseXp(int xp, SkillData dataToIncrease, Player player, PlayerSkillEnum skill) {
        dataToIncrease.currentXp += xp;
        dataToIncrease.totalXp += xp;
        ClientLastMessageReceived.set("+" + xp + "XP " + getTranslatedEnum(dataToIncrease.skill) +
                " (" + dataToIncrease.currentXp + "XP / " + getXpNeededForNextLevel(dataToIncrease.level) + "XP)!");
        if (dataToIncrease.currentXp >=
                getXpNeededForNextLevel(dataToIncrease.level)) {
            dataToIncrease.currentXp -= getXpNeededForNextLevel(dataToIncrease.level);
            dataToIncrease.level++;
            RPGModEventFactory.onLevelUp(player, skill, dataToIncrease.level);
            ClientLastMessageReceived.setImportant("Vous avez atteint le niveau " + dataToIncrease.level + " de " +
                    getTranslatedEnum(dataToIncrease.skill) + "! (XP total: " + dataToIncrease.totalXp + " XP)");
        }

    }

    public void copyFrom(PlayerSkills playerSkills) {
        playerSkillData = playerSkills.playerSkillData;
    }

    public void saveNBTData(CompoundTag nbt) {
        playerSkillData.fillNbtTag(nbt);
    }

    public void loadNBTData(CompoundTag nbt) {
        playerSkillData = PlayerSkillData.loadNbt(nbt);
    }
}
