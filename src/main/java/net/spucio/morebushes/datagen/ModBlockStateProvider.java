package net.spucio.morebushes.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.spucio.morebushes.MoreBushes;
import net.spucio.morebushes.block.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MoreBushes.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerBush(ModBlocks.BLUE_BERRY_BUSH, "blue_berry_bush_stage");
        registerBush(ModBlocks.LAVA_BERRY_BUSH, "lava_berry_bush_stage");
        registerBush(ModBlocks.GLOWSTONE_BERRY_BUSH, "glowstone_berry_bush_stage");
        registerBush(ModBlocks.MINERS_BERRY_BUSH, "miners_berry_bush_stage");
        registerBush(ModBlocks.SHULKER_BERRY_BUSH, "shulker_berry_bush_stage");
        registerBush(ModBlocks.CHORUS_BERRY_BUSH, "chorus_berry_bush_stage");
        registerBush(ModBlocks.GOLDEN_BERRY_BUSH, "golden_berry_bush_stage");
    }

    private void registerBush(DeferredHolder<Block, ? extends Block> block, String texturePrefix) {
        getVariantBuilder(block.get()).forAllStates(state -> {
            int age = state.getValue(SweetBerryBushBlock.AGE);
            return new ConfiguredModel[]{
                    new ConfiguredModel(models().cross(texturePrefix + age,
                                    ResourceLocation.fromNamespaceAndPath(MoreBushes.MOD_ID, "block/" + texturePrefix + age))
                            .renderType("cutout"))
            };
        });
    }
}