package com.massinissadjellouli.RPGmod.events;

import com.massinissadjellouli.RPGmod.skills.PlayerSkillData.PlayerSkillEnum;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class LevelUpEvent extends PlayerEvent {
    private final PlayerSkillEnum skill;
    private final int level;
    public LevelUpEvent(Player player,PlayerSkillEnum skill,int level) {
        super(player);
        this.skill = skill;
        this.level = level;
    }

    public PlayerSkillEnum getSkill(){
        return skill;
    }
    public int getSkillLevel(){
        return level;
    }
}
