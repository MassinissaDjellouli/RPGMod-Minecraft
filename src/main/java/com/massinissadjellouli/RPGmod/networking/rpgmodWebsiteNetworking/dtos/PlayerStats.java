package com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.dtos;

import com.massinissadjellouli.RPGmod.classSystem.PlayerClass;
import com.massinissadjellouli.RPGmod.classSystem.PlayerClassType;

public class PlayerStats {
    private final String MINING = "Minage";
    private final String FORAGING = "Buchage";
    private final String COMBAT = "Combat";
    private final String userId;
    private final int blocksMined;
    private final int mobsKilled;
    private final int blocksForaged;
    private final String currentClass;
    private final int miningLevel;
    private final int miningXp;
    private final int totalMiningXp;
    private final int foragingLevel;
    private final int foragingXp;
    private final int totalForagingXp;
    private final int combatLevel;
    private final int combatXp;
    private final int totalCombatXp;
    private final String bestClass;
    private final int highestLevel;
    private final int totalXp;

    public PlayerStats(String userId,int blocksMined, int mobsKilled, int blocksForaged, PlayerClassType currentClass, int miningLevel, int miningXp, int totalMiningXp, int foragingLevel, int foragingXp, int totalForagingXp, int combatLevel, int combatXp, int totalCombatXp) {
        this.userId = userId;
        this.blocksMined = blocksMined;
        this.mobsKilled = mobsKilled;
        this.blocksForaged = blocksForaged;
        this.currentClass = getPlayerClass(currentClass);
        this.miningLevel = miningLevel;
        this.miningXp = miningXp;
        this.totalMiningXp = totalMiningXp;
        this.foragingLevel = foragingLevel;
        this.foragingXp = foragingXp;
        this.totalForagingXp = totalForagingXp;
        this.combatLevel = combatLevel;
        this.combatXp = combatXp;
        this.totalCombatXp = totalCombatXp;
        this.bestClass = getBestClass();
        this.highestLevel = getHighestLevel();
        this.totalXp = getTotalXp();
    }

    private String getPlayerClass(PlayerClassType currentClass) {
        return switch (currentClass) {
            case MINEUR -> MINING;
            case BUCHERON -> FORAGING;
            case SOLDAT -> COMBAT;
        };
    }

    private int getTotalXp() {
        return totalMiningXp + totalForagingXp + totalCombatXp;
    }

    private int getHighestLevel() {
        return Math.max(Math.max(miningLevel, foragingLevel), combatLevel);
    }

    private String getBestClass() {
        int highestLvl = getHighestLevel();
        if(highestLvl == miningLevel) {
            return MINING;
        }
        if(highestLvl == foragingLevel) {
            return FORAGING;
        }
        if(highestLvl == combatLevel) {
            return COMBAT;
        }
        return "unknown";
    }
}
