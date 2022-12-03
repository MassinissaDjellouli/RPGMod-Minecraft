package com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.dtos;

import com.massinissadjellouli.RPGmod.classSystem.PlayerClassType;

public class PlayerStatsDTOBuilder {
    private String worldId;
    private String username;
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

    public PlayerStatsDTOBuilder blocksMined(int blocksMined) {
        this.blocksMined = blocksMined;
        return this;
    }

    public PlayerStatsDTOBuilder mobsKilled(int mobsKilled) {
        this.mobsKilled = mobsKilled;
        return this;
    }

    public PlayerStatsDTOBuilder blocksForaged(int blocksForaged) {
        this.blocksForaged = blocksForaged;
        return this;
    }

    public PlayerStatsDTOBuilder currentClass(PlayerClassType currentClass) {
        this.currentClass = currentClass;
        return this;
    }

    public PlayerStatsDTOBuilder miningLevel(int miningLevel) {
        this.miningLevel = miningLevel;
        return this;
    }

    public PlayerStatsDTOBuilder miningXp(int miningXp) {
        this.miningXp = miningXp;
        return this;
    }

    public PlayerStatsDTOBuilder foragingLevel(int foragingLevel) {
        this.foragingLevel = foragingLevel;
        return this;
    }

    public PlayerStatsDTOBuilder foragingXp(int foragingXp) {
        this.foragingXp = foragingXp;
        return this;
    }

    public PlayerStatsDTOBuilder combatLevel(int combatLevel) {
        this.combatLevel = combatLevel;
        return this;
    }

    public PlayerStatsDTOBuilder combatXp(int combatXp) {
        this.combatXp = combatXp;
        return this;
    }

    public PlayerStatsDTOBuilder totalMiningXp(int totalMiningXp) {
        this.totalMiningXp = totalMiningXp;
        return this;
    }

    public PlayerStatsDTOBuilder totalForagingXp(int totalForagingXp) {
        this.totalForagingXp = totalForagingXp;
        return this;
    }

    public PlayerStatsDTOBuilder totalCombatXp(int totalCombatXp) {
        this.totalCombatXp = totalCombatXp;
        return this;
    }
    public PlayerStatsDTOBuilder worldId(String worldId) {
        this.worldId = worldId;
        return this;
    }
    public PlayerStatsDTOBuilder username(String username) {
        this.username = username;
        return this;
    }

    public PlayerStatsDTO build() {
        return new PlayerStatsDTO(
                username ,
                worldId,
                blocksMined,
                mobsKilled,
                blocksForaged,
                currentClass,
                miningLevel,
                miningXp,
                totalMiningXp,
                foragingLevel,
                foragingXp,
                totalForagingXp,
                combatLevel,
                combatXp,
                totalCombatXp);
    }

    public boolean isEmpty(){
        return username == null
                && worldId == null
                    && currentClass == null;

    }
    public boolean isFull(){
        return username != null
                && worldId != null
                    && currentClass != null;
    }

}