package net.spucio.morebushes.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.spucio.morebushes.MoreBushes;
import net.spucio.morebushes.block.custom.*;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MoreBushes.MOD_ID);

    public static final DeferredHolder<Block, Block> BLUE_BERRY_BUSH = BLOCKS.register("blue_berry_bush",
            () -> new BlueBerryBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)));

    public static final DeferredHolder<Block, Block> LAVA_BERRY_BUSH = BLOCKS.register("lava_berry_bush",
            () -> new LavaBerryBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH).lightLevel((state) -> {
                int age = state.getValue(GoldenBerryBushBlock.AGE);
                if (age == 3) {
                    return 8;
                } else if (age == 2) {
                    return 5;
                } else {
                    return 0;
                }
            })));

    public static final DeferredHolder<Block, Block> GLOWSTONE_BERRY_BUSH = BLOCKS.register("glowstone_berry_bush",
            () -> new GlowstoneBerryBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH).lightLevel((state) -> {
                int age = state.getValue(GoldenBerryBushBlock.AGE);
                if (age == 3) {
                    return 12;
                } else if (age == 2) {
                    return 7;
                } else {
                    return 0;
                }
            })));

    public static final DeferredHolder<Block, Block> MINERS_BERRY_BUSH = BLOCKS.register("miners_berry_bush",
            () -> new MinersBerryBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH).lightLevel((state) -> {
                int age = state.getValue(GoldenBerryBushBlock.AGE);
                if (age == 3) {
                    return 4;
                } else if (age == 2) {
                    return 2;
                } else {
                    return 0;
                }
            })));

    public static final DeferredHolder<Block, Block> SHULKER_BERRY_BUSH = BLOCKS.register("shulker_berry_bush",
            () -> new ShulkerBerryBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)));

    public static final DeferredHolder<Block, Block> CHORUS_BERRY_BUSH = BLOCKS.register("chorus_berry_bush",
            () -> new ChorusBerryBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)));

    public static final DeferredHolder<Block, Block> GOLDEN_BERRY_BUSH = BLOCKS.register("golden_berry_bush",
            () -> new GoldenBerryBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH).lightLevel((state) -> {
                int age = state.getValue(GoldenBerryBushBlock.AGE);
                if (age == 3) {
                    return 6;
                } else {
                    return 2;
                }
            })));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}