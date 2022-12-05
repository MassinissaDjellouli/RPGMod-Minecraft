package com.massinissadjellouli.RPGmod.worldEvents;

import com.massinissadjellouli.RPGmod.client.ClientLastTitleReceived;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class NuitFeroceWorldEvent extends WorldEvent {
    public NuitFeroceWorldEvent() {
        super("Nuit féroce");
        showTitle = true;
    }

    boolean invisible = true;
    int spawnTimer = 0;
    int upTimer = 0;
    int downTimer = 0;
    final int MAX_UP_TIMER = 50;
    final int MAX_DOWN_TIMER = 100;
    final int MAX_SPAWN_TIMER = 350;
    final int RADIUS = 60;

    @Override
    protected void launch() {
        ClientLastTitleReceived.set("Attention! Un event nuit féroce commence!",
                "Des monstres féroces vont aparaitres!",
                ChatFormatting.AQUA);
        System.out.println("Nuit Feroce World Event launched");
    }

    @Override
    protected void tick() {
        level.setDayTime(42000);
        List<Zombie> nearbyZombies =
                level.getNearbyEntities(Zombie.class,
                        TargetingConditions.DEFAULT,
                        player,
                        new AABB(player.blockPosition().north(RADIUS).west(RADIUS),
                                player.blockPosition().south(RADIUS).east(RADIUS)));
        List<Skeleton> nearbySkeletons = level.getNearbyEntities(Skeleton.class,
                TargetingConditions.DEFAULT,
                player,
                new AABB(player.blockPosition().north(RADIUS).west(RADIUS),
                        player.blockPosition().south(RADIUS).east(RADIUS)));

        List<Creeper> nearbyCreepers = level.getNearbyEntities(Creeper.class,
                TargetingConditions.DEFAULT,
                player,
                new AABB(player.blockPosition().north(RADIUS).west(RADIUS),
                        player.blockPosition().south(RADIUS).east(RADIUS)));
        upTimer++;
        spawnTimer++;
        if (upTimer >= MAX_UP_TIMER) {
            downTimer++;
            invisible = true;
            if (downTimer >= MAX_DOWN_TIMER) {
                upTimer = 0;
                downTimer = 0;
                invisible = false;
            }
        }
        if(spawnTimer >= MAX_SPAWN_TIMER){
            spawnTimer = 0;
            EntityType.ZOMBIE.spawn(level,null,player, player.blockPosition().north(5), MobSpawnType.COMMAND, false, false);
            EntityType.SKELETON.spawn(level,null,player, player.blockPosition().east(5), MobSpawnType.COMMAND, false, false);
            EntityType.SPIDER.spawn(level,null,player, player.blockPosition().west(5), MobSpawnType.COMMAND, false, false);
            EntityType.CREEPER.spawn(level,null,player, player.blockPosition().south(5), MobSpawnType.COMMAND, false, false);
        }
        buffZombies(nearbyZombies);
        buffSkeletons(nearbySkeletons);
        buffCreepers(nearbyCreepers);
    }

    private void buffCreepers(List<Creeper> nearbyCreepers) {
        for(Creeper creeper : nearbyCreepers){
            creeper.setHealth(100);
            creeper.setAggressive(true);
            creeper.setInvisible(invisible);
        }
    }

    private void buffSkeletons(List<Skeleton> nearbySkeletons) {
        for(Skeleton skeleton : nearbySkeletons){
            skeleton.setHealth(100);
            skeleton.setAggressive(true);
            skeleton.setInvisible(invisible);
            skeleton.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,5, 2));
        }
    }


    private void buffZombies(List<Zombie> nearbyZombies) {
        for(Zombie zombie : nearbyZombies){
            zombie.setHealth(100);
            zombie.setAggressive(true);
            zombie.setInvisible(invisible);
            zombie.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,5, 2));
            zombie.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,5, 2));
        }

    }

    @Override
    protected void loadData(CompoundTag nbt) {

    }

    @Override
    protected int[] getEventRange() {
        return new int[]{2000, 5000};
    }

    @Override
    protected void saveData(CompoundTag nbt) {

    }

    @Override
    protected RPGModWorldEventType getEventType() {
        return RPGModWorldEventType.NUIT_FEROCE;
    }

}
