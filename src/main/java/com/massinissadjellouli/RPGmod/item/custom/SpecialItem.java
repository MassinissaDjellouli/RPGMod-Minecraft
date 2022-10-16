package com.massinissadjellouli.RPGmod.item.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface SpecialItem {
    void act(Level pLevel, Player pPlayer, InteractionHand pUsedHand);
}
