package com.massinissadjellouli.RPGmod.tags;

import com.massinissadjellouli.RPGmod.Elements.Elements;
import com.massinissadjellouli.RPGmod.RPGMod;
import com.massinissadjellouli.RPGmod.item.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static net.minecraft.world.item.Items.COAL_BLOCK;
import static net.minecraft.world.item.Items.LAVA_BUCKET;

public class ModTags {
    public static class EntityTypes {
        public static final TagKey<EntityType<?>> HARMLESS = createTag("harmless");
        public static final TagKey<EntityType<?>> HARMFUL = createTag("harmful");
        public static final TagKey<EntityType<?>> DANGEROUS = createTag("dangerous");
        public static final TagKey<EntityType<?>> VERY_DANGEROUS = createTag("very_dangerous");
        public static final TagKey<EntityType<?>> BOSS = createTag("boss");
        public static final TagKey<EntityType<?>> RESISTANT_TO_FREEZE = createTag("resistant_to_freeze");
        public static final TagKey<EntityType<?>> RESISTANT_TO_FIRE = createTag("resistant_to_burning");
        public static final TagKey<EntityType<?>> RESISTANT_TO_POISON = createTag("resistant_to_poison");
        public static final TagKey<EntityType<?>> VULNERABLE_TO_FREEZE = createTag("vulnerable_to_freeze");
        public static final TagKey<EntityType<?>> VULNERABLE_TO_FIRE = createTag("vulnerable_to_burning");
        public static final TagKey<EntityType<?>> VULNERABLE_TO_POISON = createTag("vulnerable_to_poison");

        public enum EntityTags {
            HARMLESS(EntityTypes.HARMLESS),
            HARMFUL(EntityTypes.HARMFUL),
            DANGEROUS(EntityTypes.DANGEROUS),
            VERY_DANGEROUS(EntityTypes.VERY_DANGEROUS),
            BOSS(EntityTypes.BOSS),

            RESISTANT_TO_FREEZE(EntityTypes.RESISTANT_TO_FREEZE),
            RESISTANT_TO_FIRE(EntityTypes.RESISTANT_TO_FIRE),
            RESISTANT_TO_POISON(EntityTypes.RESISTANT_TO_POISON),
            VULNERABLE_TO_FREEZE(EntityTypes.VULNERABLE_TO_FREEZE),
            VULNERABLE_TO_FIRE(EntityTypes.VULNERABLE_TO_FIRE),
            VULNERABLE_TO_POISON(EntityTypes.VULNERABLE_TO_POISON),
            NO(null);

            public final TagKey<EntityType<?>> tagKey;

            EntityTags(TagKey<EntityType<?>> tagKey) {
                this.tagKey = tagKey;
            }

            public static EntityTags getResistance(Elements element) {
                switch (element) {
                    case ICE -> {
                        return RESISTANT_TO_FREEZE;
                    }
                    case FIRE -> {
                        return RESISTANT_TO_FIRE;
                    }
                    case POISON -> {
                        return RESISTANT_TO_POISON;
                    }
                }
                return EntityTags.NO;
            }

            public static EntityTags getVulnerablility(Elements element) {
                switch (element) {
                    case ICE -> {
                        return VULNERABLE_TO_FREEZE;
                    }
                    case FIRE -> {
                        return VULNERABLE_TO_FIRE;
                    }
                    case POISON -> {
                        return VULNERABLE_TO_POISON;
                    }
                }
                return EntityTags.NO;
            }
        }

        private static TagKey<EntityType<?>> createTag(String ressourceLocation) {

            return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(RPGMod.MODID, ressourceLocation));
        }
    }

    public static class Blocks {
        public static final TagKey<Block> NEEDS_TITANIUM_TOOLS = BlockTags.create(new ResourceLocation("minecraft:needs_titanium_tool"));
        public static final TagKey<Block> NEEDS_REINFORCED_TITANIUM_TOOLS = BlockTags.create(new ResourceLocation("minecraft:needs_reinforced_titanium_tool"));
        public static final TagKey<Block> NEEDS_COPPER_TOOLS = BlockTags.create(new ResourceLocation("minecraft:needs_copper_tool"));
        public static final TagKey<Block> NEEDS_STEEL_TOOLS = BlockTags.create(new ResourceLocation("minecraft:needs_steel_tool"));
        public static final TagKey<Block> ORES_TITANIUM = createTag("ores/titanium");
        public static final TagKey<Block> ORES_CRYSTAL = createTag("ores/crystal");

        private static TagKey<Block> createTag(String ressourceLocation) {
            return BlockTags.create(new ResourceLocation(RPGMod.MODID, ressourceLocation));
        }

    }

    public static class Items {

        public static final TagKey<Item> COMMON = createTag("rarity/common");
        public static final TagKey<Item> UNCOMMON = createTag("rarity/uncommon");
        public static final TagKey<Item> RARE = createTag("rarity/rare");
        public static final TagKey<Item> VERY_RARE = createTag("rarity/very_rare");
        public static final TagKey<Item> LEGENDARY = createTag("rarity/legendary");
        public static final TagKey<Item> MYTHICAL = createTag("rarity/mythical");
        public static final TagKey<Item> GODLY = createTag("rarity/godly");

        public enum CompressorFuels {
            COAL(net.minecraft.world.item.Items.COAL, 10),
            CHARCOAL(net.minecraft.world.item.Items.CHARCOAL, 15),
            COAL_BLOC(COAL_BLOCK, 95),
            COMPRESSED_COAL(ModItems.COMPRESSED_COAL.get(), 2000),
            LAVA(LAVA_BUCKET, 300);

            public final Item item;
            public final int energy;


            CompressorFuels(Item item, int energy) {
                this.item = item;
                this.energy = energy;
            }
        }

        private static TagKey<Item> createTag(String ressourceLocation) {
            return ItemTags.create(new ResourceLocation(RPGMod.MODID, ressourceLocation));
        }
    }


}
