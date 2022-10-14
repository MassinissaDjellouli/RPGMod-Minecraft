package com.massinissadjellouli.RPGmod.item.custom;

import com.massinissadjellouli.RPGmod.Elements.Elements;
import com.massinissadjellouli.RPGmod.tags.ModTags.EntityTypes.EntityTags;
import com.massinissadjellouli.effects.ElementalMobEffectInstance;
import net.minecraft.world.entity.EntityType;
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
        int amplifier = getAmplifier(pStack);
        pTarget.addEffect(new ElementalMobEffectInstance(element.effect,20 * (amplifier + 1),
                        pAttacker instanceof Player,
                entityVulnerableToElement(pTarget),
                entityResistantToElement(pTarget)));
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    private int getAmplifier(ItemStack pStack) {
        if(itemHasNoRarity(pStack)){
            return  0;
        }else{
            return getTag(getItemRarity(pStack)).level;
        }
    }
    private static boolean entityTagHas(EntityTags tag, EntityType<?> entity) {
        if(tag == null || tag == EntityTags.NO) return false;
        return entity.is(tag.tagKey);
    }
    private boolean entityResistantToElement(LivingEntity pTarget) {
        return entityTagHas(EntityTags.getResistance(element),pTarget.getType());

    }
    private boolean entityVulnerableToElement(LivingEntity pTarget) {
        return entityTagHas(EntityTags.getVulnerablility(element),pTarget.getType());
    }
}
