package com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.dtos;

import com.massinissadjellouli.RPGmod.classSystem.PlayerClassType;

public class PlayerStatsBuilder {
    private String userId;
    private int blocksMined;
    private int mobsKilled;
    private int blocksForaged;
    private PlayerClassType currentClass;
    private int miningLevel;
    private int miningXp;
    private int foragingLevel;
    private int foragingXp;
    private int combatLevel;
    private int combatXp;
    private int totalMiningXp;
    private int totalForagingXp;
    private int totalCombatXp;

    public PlayerStatsBuilder blocksMined(int blocksMined) {
        this.blocksMined = blocksMined;
        return this;
    }

    public PlayerStatsBuilder mobsKilled(int mobsKilled) {
        this.mobsKilled = mobsKilled;
        return this;
    }

    public PlayerStatsBuilder blocksForaged(int blocksForaged) {
        this.blocksForaged = blocksForaged;
        return this;
    }

    public PlayerStatsBuilder currentClass(PlayerClassType currentClass) {
        this.currentClass = currentClass;
        return this;
    }

    public PlayerStatsBuilder miningLevel(int miningLevel) {
        this.miningLevel = miningLevel;
        return this;
    }

    public PlayerStatsBuilder miningXp(int miningXp) {
        this.miningXp = miningXp;
        return this;
    }

    public PlayerStatsBuilder foragingLevel(int foragingLevel) {
        this.foragingLevel = foragingLevel;
        return this;
    }

    public PlayerStatsBuilder foragingXp(int foragingXp) {
        this.foragingXp = foragingXp;
        return this;
    }

    public PlayerStatsBuilder combatLevel(int combatLevel) {
        this.combatLevel = combatLevel;
        return this;
    }

    public PlayerStatsBuilder combatXp(int combatXp) {
        this.combatXp = combatXp;
        return this;
    }

    public PlayerStatsBuilder totalMiningXp(int totalMiningXp) {
        this.totalMiningXp = totalMiningXp;
        return this;
    }

    public PlayerStatsBuilder totalForagingXp(int totalForagingXp) {
        this.totalForagingXp = totalForagingXp;
        return this;
    }

    public PlayerStatsBuilder totalCombatXp(int totalCombatXp) {
        this.totalCombatXp = totalCombatXp;
        return this;
    }
    public PlayerStatsBuilder userId(String userId) {
        this.userId = userId;
        return this;
    }

    public PlayerStats build() {
        return new PlayerStats(userId, blocksMined, mobsKilled, blocksForaged, currentClass, miningLevel, miningXp, totalMiningXp, foragingLevel, foragingXp, totalForagingXp, combatLevel, combatXp, totalCombatXp);
    }

}