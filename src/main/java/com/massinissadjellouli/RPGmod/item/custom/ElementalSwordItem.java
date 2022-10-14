package com.massinissadjellouli.RPGmod.item.custom;

import com.massinissadjellouli.RPGmod.Elements.Elements;
import com.massinissadjellouli.effects.ElementalMobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

import static com.massinissadjellouli.RPGmod.tags.RarityTags.*;

public class ElementalSwordItem extends SwordItem {
    Elements element;
    public ElementalSwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties, Elements element) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        this.element = element;
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if(entityResistantToElement(pTarget)){
            return super.hurtEnemy(pStack, pTarget, pAttacker);
        }
        int amplifier = getAmplifier(pStack,pTarget);
        pTarget.addEffect(new ElementalMobEffectInstance(element.effect,20 * (amplifier + 1),pAttacker instanceof Player));
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    private int getAmplifier(ItemStack pStack, LivingEntity entity) {
        int amplifier = 1;
        if(entityVulnerableToElement(entity)){
            amplifier = 2;
        }
        if(itemHasNoRarity(pStack)){
            return  0;
        }else{
            return amplifier * getTag(getItemRarity(pStack)).level;
        }
    }
    //TODO implement this
    private boolean entityResistantToElement(LivingEntity pTarget) {
        return false;
    }
    private boolean entityVulnerableToElement(LivingEntity pTarget) {
        return false;
    }
}
