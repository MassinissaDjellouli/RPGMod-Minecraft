package com.massinissadjellouli.RPGmod.item.custom;

import com.massinissadjellouli.RPGmod.Elements.Elements;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class ThorsHammerItem extends ElementalSwordItem implements SpecialItem {
    int cooldown;

    public ThorsHammerItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties, Elements element, int cooldown) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties, element);
        this.cooldown = cooldown;
    }

    @Override
    public void act(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pUsedHand != InteractionHand.MAIN_HAND) {
            return;
        }
        if (!pLevel.isClientSide && pLevel instanceof ServerLevel serverLevel) {
            pPlayer.getCooldowns().addCooldown(this, 100);

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (i < 4 && j < 4) {
                        continue;
                    }
                    BlockPos nEast = pPlayer.blockPosition().north(j).east(i);
                    BlockPos sEast = pPlayer.blockPosition().south(j).east(i);
                    BlockPos nWest = pPlayer.blockPosition().north(j).west(i);
                    BlockPos sWest = pPlayer.blockPosition().south(j).west(i);

                    while (!serverLevel.isEmptyBlock(nEast.below()))
                        nEast = nEast.below();
                    while (!serverLevel.isEmptyBlock(nWest.below()))
                        nWest = nWest.below();
                    while (!serverLevel.isEmptyBlock(sEast.below()))
                        sEast = sEast.below();
                    while (!serverLevel.isEmptyBlock(sWest.below()))
                        sWest = sWest.below();

                    EntityType.LIGHTNING_BOLT.spawn(serverLevel, null, null,
                            nEast, MobSpawnType.COMMAND, true, false);
                    EntityType.LIGHTNING_BOLT.spawn(serverLevel, null, null,
                            sEast, MobSpawnType.COMMAND, true, false);
                    EntityType.LIGHTNING_BOLT.spawn(serverLevel, null, null,
                            nWest, MobSpawnType.COMMAND, true, false);
                    EntityType.LIGHTNING_BOLT.spawn(serverLevel, null, null,
                            sWest, MobSpawnType.COMMAND, true, false);
                }
            }
        }
    }
}
