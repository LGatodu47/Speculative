package io.github.lgatodu47.speculative.server.commands;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.lgatodu47.speculative.common.init.SpeculativeFeatures;
import io.github.lgatodu47.speculative.common.init.SpeculativeFluids;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;

public class DevCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        //LiteralArgumentBuilder<CommandSource> args = Commands.literal("dev");

        //args.then(Commands.argument("structureName", StringArgumentType.string())).requires(s -> s.hasPermissionLevel(2)).executes(DevCommand::spawnStructure);

        dispatcher.register(Commands.literal("dev")
                .then(Commands.literal("oasis")
                        .executes(DevCommand::spawnStructure))
                .then(Commands.literal("debug")
                        .executes(DevCommand::debug)));
    }

    //@SuppressWarnings("unchecked")
    private static int spawnStructure(CommandContext<CommandSource> ctx) {
        try {
            ServerPlayerEntity player = ctx.getSource().asPlayer();
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
            SpeculativeFeatures.OASIS.withConfiguration(new BlockStateFeatureConfig(SpeculativeFluids.UNSTABLE_WATER.getBlock().get().getDefaultState())).generate(player.getServerWorld(), player.getServerWorld().getChunkProvider().getChunkGenerator(), player.getServerWorld().getRandom(), (new Function<ServerPlayerEntity, BlockPos>() {
                @Override
                public BlockPos apply(ServerPlayerEntity player) {
                    BlockPos pos = player.getPosition();

                    if (pos.getY() - 4 > 4) {
                        pos = pos.down(4);
                    } else {
                        pos = pos.up(4);
                    }

                    return pos;
                }
            }).apply(player));
            //}
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private static int debug(CommandContext<CommandSource> ctx) {
        return 0;
    }
}
