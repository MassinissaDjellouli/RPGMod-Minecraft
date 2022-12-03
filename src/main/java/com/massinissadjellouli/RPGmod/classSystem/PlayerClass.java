package com.massinissadjellouli.RPGmod.classSystem;

import com.massinissadjellouli.RPGmod.client.ClientLastMessageReceived;
import net.minecraft.nbt.CompoundTag;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PlayerClass {
    private PlayerClassType currentClass;
    private Map<PlayerClassType, PlayerClassLevel> classLevels;

    public PlayerClass() {
        currentClass = PlayerClassType.MINEUR;
        classLevels = initClassLevels();
    }

    private Map<PlayerClassType, PlayerClassLevel> initClassLevels() {
        HashMap<PlayerClassType, PlayerClassLevel> levels = new HashMap<>();
        Arrays.stream(PlayerClassType.values()).forEach(playerClassType -> {
            levels.put(playerClassType, new PlayerClassLevel(0, 0));
        });
        return levels;
    }

    public void copyFrom(PlayerClass playerClass) {
        currentClass = playerClass.currentClass;
        classLevels = playerClass.classLevels;
    }

    public int getCurrentClassLevel() {
        return classLevels.containsKey(currentClass) ? classLevels.get(currentClass).getLevel() : 0;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putString("current_class", currentClass.toString());
        CompoundTag classLevelsMap = new CompoundTag();
        classLevels.forEach((playerClassType, level) -> {
            classLevelsMap.put(playerClassType.name(), level.saveData(playerClassType));
        });
        nbt.put("class_levels", classLevelsMap);
    }

    public void loadNBTData(CompoundTag nbt) {
        currentClass = PlayerClassType.valueOf(nbt.getString("current_class"));
        CompoundTag classLevelsTag = nbt.getCompound("class_levels");
        classLevels = new HashMap<>();
        Arrays.stream(PlayerClassType.values())
                .forEach(playerClassType -> {
                    if (classLevelsTag.contains(playerClassType.name())) {
                        CompoundTag compound = classLevelsTag.getCompound(playerClassType.name());
                        classLevels.put(playerClassType, PlayerClassLevel.loadNBT(compound, playerClassType));
                    } else {
                        classLevels.put(playerClassType, new PlayerClassLevel(0, 0));
                    }
                });
    }

    public boolean isCurrently(PlayerClassType playerClassType) {
        return playerClassType == currentClass;
    }

    public void increaseXp(int xp) {
        PlayerClassLevel playerClassLevel = classLevels.get(currentClass);
        int levelMultiplier = playerClassLevel.getLevel() % 2 == 0 ? playerClassLevel.getLevel() / 2
                : (playerClassLevel.getLevel() - 1) / 2;
        playerClassLevel.addXp(xp * levelMultiplier);
        if (playerClassLevel.getXp() >=
                getXpNeededForNextLevel(playerClassLevel.getLevel())) {
            playerClassLevel.resetXp(
                    playerClassLevel.getXp() - getXpNeededForNextLevel(playerClassLevel.getLevel())
            );
            playerClassLevel.levelUp();
            ClientLastMessageReceived.setImportant("Vous avez amélioré votre classe de " + currentClass.name() +
                    "! Niveau " + (playerClassLevel.getLevel() + 1) + " de " +
                    currentClass.name() + "! (Bonus d'xp: " + (playerClassLevel.getLevel() + 1) + "x");
        }

    }

    private int getXpNeededForNextLevel(int level) {
        return (int) (PlayerClassLevel.XP_THRESHOLD * (1 + level * 0.75));
    }

    public void change(PlayerClassType type) {
        currentClass = type;
    }

    public PlayerClassType getCurrentClass() {
        return currentClass;
    }
}
