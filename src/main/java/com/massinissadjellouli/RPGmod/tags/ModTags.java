package com.massinissadjellouli.RPGmod.tags;

import com.massinissadjellouli.RPGmod.RPGMod;
import com.massinissadjellouli.RPGmod.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.Arrays;
import java.util.List;

import static net.minecraft.world.item.Items.COAL_BLOCK;
import static net.minecraft.world.item.Items.LAVA_BUCKET;

public class ModTags {
    public static class EntityTypes {
        //TODO: Fix it or smth
        public static final TagKey<EntityType<?>> HARMLESS = createTag("harmless");
        public static final TagKey<EntityType<?>> HARMFUL = createTag("harmful");
        public static final TagKey<EntityType<?>> DANGEROUS = createTag("dangerous");
        public static final TagKey<EntityType<?>> VERY_DANGEROUS = createTag("very_dangerous");
        public static final TagKey<EntityType<?>> BOSS = createTag("boss");

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



        public static final TagKey<Item> COMMON = createTag("rarity/common");
        public static final TagKey<Item> UNCOMMON = createTag("rarity/uncommon");
        public static final TagKey<Item> RARE = createTag("rarity/rare");
        public static final TagKey<Item> VERY_RARE = createTag("rarity/very_rare");
        public static final TagKey<Item> LEGENDARY = createTag("rarity/legendary");
        public static final TagKey<Item> MYTHICAL = createTag("rarity/mythical");

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
