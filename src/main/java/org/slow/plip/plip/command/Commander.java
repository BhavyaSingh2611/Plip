package org.slow.plip.plip.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import org.slow.plip.plip.Plip;

import java.util.List;
import java.util.Objects;

public class Commander {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            LiteralCommandNode<ServerCommandSource> plipNode = CommandManager
                    .literal("plip")
                    .build();

            LiteralCommandNode<ServerCommandSource> spectatorNode = CommandManager
                    .literal("spectator")
                    .executes(SubCommands::spectator)
                    .build();

            LiteralCommandNode<ServerCommandSource> adventureNode = CommandManager
                    .literal("adventure")
                    .executes(SubCommands::adventure)
                    .build();

            dispatcher.getRoot().addChild(plipNode);

            plipNode.addChild(spectatorNode);
            plipNode.addChild(adventureNode);
        });
    }

    public static class SubCommands {
        public static int spectator(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
            ServerPlayerEntity player = context.getSource().getPlayer();
            var list = (List) Plip.getConfig().get("names");
            if (player != null) {
                if (list.contains(player.getName().getString())) {
                    player.changeGameMode(GameMode.SPECTATOR);
                    context.getSource().sendMessage(Text.literal("Successfully set gamemode to spectator"));
                    return 1;
                } else {
                    context.getSource().sendError(Text.literal("Insufficient permissions to change gamemode"));
                    return -1;
                }
            }
            return 0;
        }

        public static int adventure(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
            ServerPlayerEntity player = context.getSource().getPlayer();
            var list = (List) Plip.getConfig().get("names");
            if (player != null) {
                if (list.contains(Objects.requireNonNull(player.getName()).getString())) {
                    player.changeGameMode(GameMode.ADVENTURE);
                    context.getSource().sendMessage(Text.literal("Successfully set gamemode to adventure"));
                    return 1;
                } else {
                    context.getSource().sendError(Text.literal("Insufficient permissions to change gamemode"));
                    return -1;
                }
            }
            return 0;
        }
    }
}