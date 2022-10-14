package com.massinissadjellouli.effects;
//Source : https://www.youtube.com/watch?v=fm7urzE4pmo&t=206s&ab_channel=ModdingbyKaupenjoe
import com.massinissadjellouli.RPGmod.events.RPGModEventFactory;
import com.massinissadjellouli.RPGmod.tags.ModTags;
import net.minecraft.server.commands.SummonCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;

public class LightningEffect extends MobEffect implements DamagingMobEffect{

    int ticksSinceLastLigtning = 0;
    boolean isVulnerable = false;
    final int TICKS_BETWEEN_LIGHTNINGS = 20;
    protected LightningEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if(!pLivingEntity.level.isClientSide && pLivingEntity.getLevel() instanceof ServerLevel serverLevel){
            if(ticksSinceLastLigtning >= TICKS_BETWEEN_LIGHTNINGS){
                ticksSinceLastLigtning = 0;
                EntityType.LIGHTNING_BOLT.spawn(serverLevel,null,null,
                        pLivingEntity.blockPosition(), MobSpawnType.COMMAND,true,false);
                if(isVulnerable){
                    pLivingEntity.hurt(DamageSource.MAGIC,4);
                }
            }else{
                ticksSinceLastLigtning++;
            }
        }

        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

    @Override
    public void applyElementalEffectTickFromPlayer(LivingEntity pLivingEntity) {
        applyEffectTick(pLivingEntity,1);
        if(pLivingEntity.getHealth() == 0 && !pLivingEntity.isAlive()){
            RPGModEventFactory.onKilledBySwordEffect(pLivingEntity);
            pLivingEntity.removeEffect(this);
        }
    }

    @Override
    public void setIsVulnerable() {
        isVulnerable = true;
    }
}
