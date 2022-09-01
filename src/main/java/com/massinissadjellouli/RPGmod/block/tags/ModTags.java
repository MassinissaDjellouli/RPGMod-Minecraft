package com.massinissadjellouli.RPGmod.block.tags;

import com.massinissadjellouli.RPGmod.RPGMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static final TagKey<Block> NEEDS_TITANIUM_TOOLS = createTag("mineable/needs_titanium_tool");

    private static TagKey<Block> createTag(String ressourceLocation){
        return BlockTags.create(new ResourceLocation(RPGMod.MODID,ressourceLocation));
    }
}
