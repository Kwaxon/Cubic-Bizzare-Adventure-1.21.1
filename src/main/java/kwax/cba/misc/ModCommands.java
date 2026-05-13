package kwax.cba.misc;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import kwax.cba.bloodlines.Bloodlines;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ModCommands {

    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    literal("cba")
                            .then(literal("bloodlines")
                                    .then(argument("player", EntityArgumentType.player())
                                            .then(literal("get")
                                                    .executes(ModCommands::executeGet)
                                            )
                                            .then(literal("set")
                                                    .then(argument("value", StringArgumentType.word())
                                                            .suggests((context, builder) -> {
                                                                for (String bloodline : Bloodlines.getBloodlines()) {
                                                                    builder.suggest(bloodline);
                                                                }
                                                                return builder.buildFuture();
                                                            })
                                                            .executes(ModCommands::executeSet)
                                                    )
                                            )
                                    )
                            )
            );
        });
    }

    private static int executeGet(CommandContext<ServerCommandSource> context) {
        try {
            ServerPlayerEntity target = EntityArgumentType.getPlayer(context, "player");
            ModStates state = ModStates.getServerState(context.getSource().getServer());

            String bloodline = state.bloodlines.getOrDefault(target.getUuid(), "none");
            context.getSource().sendMessage(Text.literal(
                    target.getName().getString() + "'s bloodline: " + bloodline
            ));
        } catch (Exception e) {
            context.getSource().sendMessage(Text.literal("Player not found!"));
        }
        return 1;
    }

    private static int executeSet(CommandContext<ServerCommandSource> context) {
        try {
            ServerPlayerEntity target = EntityArgumentType.getPlayer(context, "player");
            String value = StringArgumentType.getString(context, "value");
            ModStates state = ModStates.getServerState(context.getSource().getServer());

            state.bloodlines.put(target.getUuid(), value);
            state.markDirty();

            context.getSource().sendMessage(Text.literal(
                    "Set " + target.getName().getString() + "'s bloodline to: " + value
            ));
        } catch (Exception e) {
            context.getSource().sendMessage(Text.literal("Player not found!"));
        }
        return 1;
    }
}