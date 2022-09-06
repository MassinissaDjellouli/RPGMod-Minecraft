package com.massinissadjellouli.RPGmod.tags;

import com.massinissadjellouli.RPGmod.RPGMod;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks{
        public static final TagKey<Block> NEEDS_TITANIUM_TOOLS = createTag("needs_titanium_tool");
        private static TagKey<Block> createTag(String ressourceLocation){
            return BlockTags.create(new ResourceLocation(RPGMod.MODID,ressourceLocation));
        }

    }
    public static class Items{

        public enum RarityTags {
            COMMON(ModTags.Items.COMMON,"Commun",0),
            UNCOMMON(ModTags.Items.UNCOMMON,"Peu commun",ChatFormatting.GREEN,1),
            RARE(ModTags.Items.RARE,"Rare",ChatFormatting.BLUE,2),
            VERY_RARE(ModTags.Items.VERY_RARE,"Très rare",ChatFormatting.DARK_PURPLE,3),
            LEGENDARY(ModTags.Items.LEGENDARY,"Légendaire",ChatFormatting.GOLD,4),
            MYTHICAL(ModTags.Items.MYTHICAL,"Mythique",ChatFormatting.RED,5);

            public final TagKey<Item> tagKey;
            public final String name;
            public final int level;
            public ChatFormatting style = ChatFormatting.WHITE;
            RarityTags(TagKey<Item> rarity,String name,ChatFormatting style,int level) {
                tagKey = rarity;
                this.name = name;
                this.level = level;
                this.style = style;
            }
            RarityTags(TagKey<Item> rarity,String name,int level) {
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
        private static TagKey<Item> createTag(String ressourceLocation){
            return ItemTags.create(new ResourceLocation(RPGMod.MODID,ressourceLocation));
        }
    }



}
