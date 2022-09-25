package com.massinissadjellouli.RPGmod.tags;

import com.massinissadjellouli.RPGmod.RPGMod;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class EntityTypes {
        //TODO: Fix it or smth
        public static final TagKey<EntityType<?>> HARMLESS = createTag("entity_types/harmless");
        public static final TagKey<EntityType<?>> HARMFUL = createTag("entity_types/harful");
        public static final TagKey<EntityType<?>> DANGEROUS = createTag("entity_types/dangerous");
        public static final TagKey<EntityType<?>> VERY_DANGEROUS = createTag("entity_types/very_dangerous");
        public static final TagKey<EntityType<?>> BOSS = createTag("entity_types/boss");

        public enum EntityTags {
            HARMLESS(EntityTypes.HARMLESS),
            HARMFUL(EntityTypes.HARMFUL),
            DANGEROUS(EntityTypes.DANGEROUS),
            VERY_DANGEROUS(EntityTypes.VERY_DANGEROUS),
            BOSS(EntityTypes.BOSS);

            public final TagKey<EntityType<?>> tagKey;

            EntityTags(TagKey<EntityType<?>> tagKey) {
                this.tagKey = tagKey;
            }
        }

        private static TagKey<EntityType<?>> createTag(String ressourceLocation) {

            return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(RPGMod.MODID, ressourceLocation));
        }
    }

    public static class Blocks {
        public static final TagKey<Block> NEEDS_TITANIUM_TOOLS = createTag("needs_titanium_tool");
        public static final TagKey<Block> ORES_TITANIUM = createTag("ores/titanium");
        public static final TagKey<Block> ORES_CRYSTAL = createTag("ores/crystal");

        private static TagKey<Block> createTag(String ressourceLocation) {
            return BlockTags.create(new ResourceLocation(RPGMod.MODID, ressourceLocation));
        }

    }

    public static class Items {

        public enum RarityTags {
            COMMON(ModTags.Items.COMMON, "Commun", 0),
            UNCOMMON(ModTags.Items.UNCOMMON, "Peu commun", ChatFormatting.GREEN, 1),
            RARE(ModTags.Items.RARE, "Rare", ChatFormatting.BLUE, 2),
            VERY_RARE(ModTags.Items.VERY_RARE, "Très rare", ChatFormatting.DARK_PURPLE, 3),
            LEGENDARY(ModTags.Items.LEGENDARY, "Légendaire", ChatFormatting.GOLD, 4),
            MYTHICAL(ModTags.Items.MYTHICAL, "Mythique", ChatFormatting.DARK_RED, 5);

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
        }

        public static final TagKey<Item> COMMON = createTag("rarity/common");
        public static final TagKey<Item> UNCOMMON = createTag("rarity/uncommon");
        public static final TagKey<Item> RARE = createTag("rarity/rare");
        public static final TagKey<Item> VERY_RARE = createTag("rarity/very_rare");
        public static final TagKey<Item> LEGENDARY = createTag("rarity/legendary");
        public static final TagKey<Item> MYTHICAL = createTag("rarity/mythical");

        private static TagKey<Item> createTag(String ressourceLocation) {
            return ItemTags.create(new ResourceLocation(RPGMod.MODID, ressourceLocation));
        }
    }


}
