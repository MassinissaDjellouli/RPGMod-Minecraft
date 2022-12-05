package com.massinissadjellouli.RPGmod.worldEvents;

import com.massinissadjellouli.RPGmod.tags.RarityTags;
import net.minecraft.world.item.Item;

public class GoldRushReward {
    private Item reward;
    private String message;

    private int rarity;
    private String rarityName;
    public GoldRushReward(Item reward, String message) {
        rarity = 0;
        rarityName = "Commun";
        this.reward = reward;
        this.message = message + " [" + rarityName + "]";
    }

    public GoldRushReward(Item reward, String message, int rarity) {
        this.rarityName = getRarityName(rarity);
        this.rarity = rarity;
        this.reward = reward;
        this.message = message + " [" + rarityName + "]";
    }

    private String getRarityName(int rarity) {
        return switch (rarity) {
            case 1 -> "Peu commun";
            case 2 -> "Rare";
            case 3 -> "Très rare";
            case 4 -> "Légendaire";
            case 5 -> "Mythique";
            case 6 -> "Divin";
            default -> "Commun";
        };
    }

    public Item getReward() {
        return reward;
    }

    public void setReward(Item reward) {
        this.reward = reward;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }
}
