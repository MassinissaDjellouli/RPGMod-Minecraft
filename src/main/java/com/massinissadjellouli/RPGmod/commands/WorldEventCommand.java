package com.massinissadjellouli.RPGmod.commands;

import com.massinissadjellouli.RPGmod.events.RPGModEventFactory;
import com.massinissadjellouli.RPGmod.worldEvents.RPGModWorldEventType;
import com.massinissadjellouli.RPGmod.worldEvents.WorldEvent;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.Arrays;
import java.util.Objects;

public class WorldEventCommand {

    public WorldEventCommand(CommandDispatcher<CommandSourceStack> pDispatcher) {
        pDispatcher.register(Commands.literal("worldevent").requires((p_138819_) -> p_138819_.hasPermission(2))
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
        if (event == null) {
            context.getSource().sendFailure(Component.literal("Cet évenement n'existe pas"));
        }
        if (event.getEvent().equals(WorldEvent.ongoingEvent)) {
            context.getSource().sendFailure(Component.literal("Cet événement est déjà en cours"));
            return 0;
        }
        WorldEvent.endCurrentEventIfRunning();
        RPGModEventFactory.onCommandEventLaunch(player, level, event);
        context.getSource().sendSuccess(Component.literal("Evenement lancé"), true);
        return 1;
    }
}
