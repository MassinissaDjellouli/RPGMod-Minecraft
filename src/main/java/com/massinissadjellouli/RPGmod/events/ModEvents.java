package com.massinissadjellouli.RPGmod.events;

import com.massinissadjellouli.RPGmod.RPGMod;
import com.massinissadjellouli.RPGmod.classSystem.PlayerClassProvider;
import com.massinissadjellouli.RPGmod.commands.WorldEventCommand;
import com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.PlayerInfoCapability.PlayerLoginInfoCapabilityProvider;
import com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.PlayerInfoCapability.PlayerUIDCapabilityProvider;
import com.massinissadjellouli.RPGmod.skills.PlayerSkillProvider;
import com.massinissadjellouli.RPGmod.skills.PlayerSkills;
import com.massinissadjellouli.RPGmod.skills.PlayerSkillsData.SkillData;
import com.massinissadjellouli.RPGmod.thirst.PlayerThirst;
import com.massinissadjellouli.RPGmod.thirst.PlayerThirstProvider;
import com.massinissadjellouli.RPGmod.worldEvents.WorldEvent;
import com.massinissadjellouli.RPGmod.worldEvents.WorldEventCapablityProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import static com.massinissadjellouli.RPGmod.skills.PlayerSkillData.PlayerSkillEnum.*;

@Mod.EventBusSubscriber(modid = RPGMod.MODID, value = Dist.CLIENT)
public class ModEvents {
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent event) {
        if (event.getObject() instanceof Player) {
            if (!((Player) event.getObject()).getCapability(PlayerThirstProvider.PLAYER_THIRST).isPresent()) {
                event.addCapability(new ResourceLocation(RPGMod.MODID, "thirst"), new PlayerThirstProvider());
            }
            if (!((Player) event.getObject()).getCapability(PlayerSkillProvider.PLAYER_SKILLS).isPresent()) {
                event.addCapability(new ResourceLocation(RPGMod.MODID, "skills"), new PlayerSkillProvider());
            }
            if (!((Player) event.getObject()).getCapability(PlayerClassProvider.PLAYER_CLASS).isPresent()) {
                event.addCapability(new ResourceLocation(RPGMod.MODID, "class"), new PlayerClassProvider());
            }
            if (!((Player) event.getObject()).getCapability(PlayerUIDCapabilityProvider.PLAYER_UID).isPresent()) {
                event.addCapability(new ResourceLocation(RPGMod.MODID, "uid"), new PlayerUIDCapabilityProvider());
            }
            if (!((Player) event.getObject()).getCapability(PlayerLoginInfoCapabilityProvider.PLAYER_LOGIN_INFO).isPresent()) {
                event.addCapability(new ResourceLocation(RPGMod.MODID, "log_info"), new PlayerLoginInfoCapabilityProvider());
            }
        }
        if (event.getObject() instanceof Level) {
            if (!((Level)event.getObject()).getCapability(WorldEventCapablityProvider.WORLD_EVENT).isPresent()) {
                event.addCapability(new ResourceLocation(RPGMod.MODID, "event"), new WorldEventCapablityProvider());
            }
        }
    }



    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new WorldEventCommand(event.getDispatcher());
        ConfigCommand.register(event.getDispatcher());

    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        event.getOriginal().reviveCaps();
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerSkillProvider.PLAYER_SKILLS).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerSkillProvider.PLAYER_SKILLS).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            event.getOriginal().getCapability(PlayerClassProvider.PLAYER_CLASS).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerClassProvider.PLAYER_CLASS).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
        event.getEntity().getAttributes().assignValues(event.getOriginal().getAttributes());
        resetBonusesEffects(event.getEntity());
        if (WorldEvent.ongoingEvent != null && event.getEntity() instanceof ServerPlayer) {
            WorldEvent.ongoingEvent.setPlayer((ServerPlayer) event.getEntity());
        }
    }

    private static void resetBonusesEffects(Player player) {
        player.getCapability(PlayerSkillProvider.PLAYER_SKILLS).ifPresent((capability) -> {
            if (capability.getSkill(MINING).isEmpty()) return;
            if (capability.getSkill(ATTACKING).isEmpty()) return;
            if (capability.getSkill(FORAGING).isEmpty()) return;
            SkillData mining = capability.getSkill(MINING).get();
            SkillData foraging = capability.getSkill(FORAGING).get();
            SkillData attacking = capability.getSkill(ATTACKING).get();
            if (!player.level.isClientSide) {
                int hasteLevel = mining.level / 10;
                int foragingLevel = foraging.level / 8;
                int attackingLevel = attacking.level / 15;
                if (foragingLevel < 4) {
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100000, foragingLevel));
                } else {
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100000, 3));
                    player.addEffect(new MobEffectInstance(MobEffects.JUMP, 100000, foragingLevel));
                }
                if (attackingLevel < 4) {
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100000, attackingLevel));
                } else {
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100000, 3));
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100000, attackingLevel));
                }
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 100000, hasteLevel));

                if (attacking.level >= 30) {
                    player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100000, 1));
                }
                if (attacking.level >= 35) {
                    player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 100000, 1));
                }
            }
        });
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerThirst.class);
        event.register(PlayerSkills.class);
    }


}
