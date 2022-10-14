package com.massinissadjellouli.RPGmod.tags;

import net.minecraft.ChatFormatting;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public enum RarityTags {
    COMMON(ModTags.Items.COMMON, "Commun", 0),
    UNCOMMON(ModTags.Items.UNCOMMON, "Peu commun", ChatFormatting.GREEN, 1),
    RARE(ModTags.Items.RARE, "Rare", ChatFormatting.BLUE, 2),
    VERY_RARE(ModTags.Items.VERY_RARE, "Très rare", ChatFormatting.DARK_PURPLE, 3),
    LEGENDARY(ModTags.Items.LEGENDARY, "Légendaire", ChatFormatting.GOLD, 4),
    MYTHICAL(ModTags.Items.MYTHICAL, "Mythique", ChatFormatting.DARK_RED, 5),
    GODLY(ModTags.Items.GODLY, "Divine", ChatFormatting.LIGHT_PURPLE, 6);

    public final TagKey<Item> tagKey;
    public final String name;
    public final int level;
    public ChatFormatting style = ChatFormatting.WHITE;

    RarityTags(TagKey<Item> rarity, String name, ChatFormatting style, int level) {
        tagKey = rarity;
        this.name = name;
        this.level = level;
        this.style = style;
    }

    RarityTags(TagKey<Item> rarity, String name, int level) {
        tagKey = rarity;
        this.name = name;
        this.level = level;
    }

    public static RarityTags getTag(String tagString) {
        List<RarityTags> tags =
                Arrays.stream(RarityTags.values()).filter(rarityTags -> rarityTags.name.equals(tagString)).toList();
        if (tags.isEmpty()) {
            return COMMON;
        }
        return tags.get(0);
    }

    public static RarityTags getTagByLevel(int level) {
        List<RarityTags> tags =
                Arrays.stream(RarityTags.values()).filter(rarityTags -> rarityTags.level == level).toList();
        if (tags.isEmpty()) {
            return COMMON;
        }
        return tags.get(0);
    }

    public static boolean itemHasNoRarity(ItemStack item) {
        return item.getTag() == null
                || getItemRarity(item).isBlank()
                || getItemRarity(item).equals("none");
    }

    public static String getItemRarity(ItemStack item) {
        return item.getTag().getString("item_rarity");
    }

}