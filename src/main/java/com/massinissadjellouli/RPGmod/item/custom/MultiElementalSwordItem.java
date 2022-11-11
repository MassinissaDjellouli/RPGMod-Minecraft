package com.massinissadjellouli.RPGmod.item.custom;

import com.massinissadjellouli.RPGmod.Elements.Elements;
import com.massinissadjellouli.RPGmod.effects.ElementalMobEffectInstance;
import com.massinissadjellouli.RPGmod.tags.ModTags.EntityTypes.EntityTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

import java.util.List;

import static com.massinissadjellouli.RPGmod.tags.RarityTags.*;

public class MultiElementalSwordItem extends SwordItem {
    List<Elements> elements;

    public MultiElementalSwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties, List<Elements> elements) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        this.elements = elements;
    }


    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        elements.forEach(element -> {
            applyElement(pTarget, pAttacker, element, pStack);
        });
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    private void applyElement(LivingEntity pTarget, LivingEntity pAttacker, Elements element, ItemStack pStack) {
        if (entityResistantToElement(pTarget, element)) {
            return;
        }
        int amplifier = getAmplifier(pStack);
        pTarget.addEffect(new ElementalMobEffectInstance(element.effect, 20 * (amplifier + 1),
                pAttacker instanceof Player,
                entityVulnerableToElement(pTarget, element),
                entityResistantToElement(pTarget, element)));
    }

    private int getAmplifier(ItemStack pStack) {
        if (itemHasNoRarity(pStack)) {
            return 0;
        } else {
            return getTag(getItemRarity(pStack)).level;
        }
    }

    private static boolean entityTagHas(EntityTags tag, EntityType<?> entity) {
        if (tag == null || tag == EntityTags.NO) return false;
        return entity.is(tag.tagKey);
    }

    private boolean entityResistantToElement(LivingEntity pTarget, Elements element) {
        return entityTagHas(EntityTags.getResistance(element), pTarget.getType());

    }

    private static boolean entityVulnerableToElement(LivingEntity pTarget, Elements element) {
        return entityTagHas(EntityTags.getVulnerablility(element), pTarget.getType());
    }
}
