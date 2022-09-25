package com.massinissadjellouli.RPGmod.events;

import com.massinissadjellouli.RPGmod.skills.PlayerSkillData;
import com.massinissadjellouli.RPGmod.skills.PlayerSkillData.PlayerSkillEnum;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;

public class RPGModEventFactory {
    public static boolean onLevelUp(@NotNull Player player, PlayerSkillEnum skill,int level){
        LevelUpEvent levelUpEvent = new LevelUpEvent(player,skill,level);
        return MinecraftForge.EVENT_BUS.post(levelUpEvent);
    }
}
