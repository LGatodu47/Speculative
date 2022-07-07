package io.github.lgatodu47.speculative.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

public class DevCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        //LiteralArgumentBuilder<CommandSource> args = Commands.literal("dev");

        //args.then(Commands.argument("structureName", StringArgumentType.string())).requires(s -> s.hasPermissionLevel(2)).executes(DevCommand::spawnStructure);

        dispatcher.register(Commands.literal("dev")
                .then(Commands.literal("oasis")
                        .executes(DevCommand::spawnStructure))
                .then(Commands.literal("debug")
                        .executes(DevCommand::debug)));
    }

    //@SuppressWarnings("unchecked")
    private static int spawnStructure(CommandContext<CommandSourceStack> ctx) {
        try {
            ServerPlayer player = ctx.getSource().getPlayerOrException();
            //String structure = StringArgumentType.getString(ctx, "structureName");

            //if (structure.equalsIgnoreCase("oasis"))
            //{
				/*((Feature<BlockStateFeatureConfig>) ForgeRegistries.FEATURES.getValue(new ResourceLocation(References.MODID, structure)))
						.withConfiguration(new BlockStateFeatureConfig(FluidsInit.SULFURIC_BLOCK.get().getDefaultState()))
						.place(player.world, player.getServerWorld().getChunkProvider().getChunkGenerator(), player.getServerWorld().getRandom(), (new Function<ServerPlayerEntity, BlockPos>()
						{
							@Override
							public BlockPos apply(ServerPlayerEntity player)
							{
								BlockPos pos = player.getPosition();

								if (pos.getY() - 4 > 4)
								{
									pos = pos.down(4);
								} else
								{
									pos = pos.up(4);
								}

								return pos;
							}
						}).apply(player));*/
            /*SpeculativeConfiguredFeatures.OASIS.place(player.getLevel(), player.getLevel().getChunkSource().getGenerator(), player.getLevel().getRandom(), (new Function<ServerPlayer, BlockPos>() {
                @Override
                public BlockPos apply(ServerPlayer player) {
                    BlockPos pos = player.blockPosition();

                    if (pos.getY() - 4 > 4) {
                        pos = pos.below(4);
                    } else {
                        pos = pos.above(4);
                    }

                    return pos;
                }
            }).apply(player));*/
            //}
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private static int debug(CommandContext<CommandSourceStack> ctx) {
        return 0;
    }
}
