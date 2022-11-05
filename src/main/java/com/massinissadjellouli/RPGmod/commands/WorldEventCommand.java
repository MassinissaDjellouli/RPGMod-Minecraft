package com.massinissadjellouli.RPGmod.commands;

import com.massinissadjellouli.RPGmod.events.Custom.WorldEvents.RPGModWorldEventType;
import com.massinissadjellouli.RPGmod.events.RPGModEventFactory;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.commands.arguments.EntitySummonArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.Objects;

public class WorldEventCommand {
    private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType(Component.translatable("commands.summon.failed"));
    private static final SimpleCommandExceptionType ERROR_DUPLICATE_UUID = new SimpleCommandExceptionType(Component.translatable("commands.summon.failed.uuid"));
    private static final SimpleCommandExceptionType INVALID_POSITION = new SimpleCommandExceptionType(Component.translatable("commands.summon.invalidPosition"));

    public WorldEventCommand(CommandDispatcher<CommandSourceStack> pDispatcher){
        pDispatcher.register(Commands.literal("worldevent").requires((p_138819_) ->  p_138819_.hasPermission(2))
                .then(Commands.argument("event", StringArgumentType.string()).suggests((context, builder) -> SharedSuggestionProvider.suggest(
            Arrays.stream(RPGModWorldEventType.values())
            .map(RPGModWorldEventType::getName)
                        .filter(Objects::nonNull), builder)).executes(WorldEventCommand::launch)));
    }
    private static int launch(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        ServerLevel level = player.getLevel();
        String eventType = StringArgumentType.getString(context, "event");
        RPGModWorldEventType event = RPGModWorldEventType.getTypeFromName(eventType);
        RPGModEventFactory.onCommandEventLaunch(player,level,event);
        System.out.println("Commande lancée");
        return 1;
    }
}
