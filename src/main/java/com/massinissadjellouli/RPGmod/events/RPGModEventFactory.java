package com.massinissadjellouli.RPGmod.events;

import com.massinissadjellouli.RPGmod.events.Custom.KilledBySwordEffectEvent;
import com.massinissadjellouli.RPGmod.events.Custom.LevelUpEvent;
import com.massinissadjellouli.RPGmod.events.Custom.RandomEventLaunchEvent;
import com.massinissadjellouli.RPGmod.skills.PlayerSkillData.PlayerSkillEnum;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;

public class RPGModEventFactory {
    public static boolean onLevelUp(@NotNull Player player, PlayerSkillEnum skill,int level){
        LevelUpEvent levelUpEvent = new LevelUpEvent(player,skill,level);
        return MinecraftForge.EVENT_BUS.post(levelUpEvent);
    }

    public static boolean onKilledBySwordEffect(LivingEntity target) {
        KilledBySwordEffectEvent killedBySwordEffectEvent = new KilledBySwordEffectEvent(target);
        return MinecraftForge.EVENT_BUS.post(killedBySwordEffectEvent);
    }

    public static boolean onRandomEventLaunch(ServerPlayer player, ServerLevel level) {
        RandomEventLaunchEvent randomEventLaunchEvent = new RandomEventLaunchEvent(player,level);
        return MinecraftForge.EVENT_BUS.post(randomEventLaunchEvent);
    }
}
