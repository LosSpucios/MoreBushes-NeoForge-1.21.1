package net.spucio.morebushes.datagen;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.spucio.morebushes.block.ModBlocks;
import net.spucio.morebushes.item.ModItems;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends BlockLootSubProvider {

    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        addBushLoot(ModBlocks.BLUE_BERRY_BUSH, ModItems.BLUEBERRIES, false, 2.0F, 3.0F);
        addBushLoot(ModBlocks.LAVA_BERRY_BUSH, ModItems.LAVABERRIES, false, 2.0F, 3.0F);
        addBushLoot(ModBlocks.GLOWSTONE_BERRY_BUSH, ModItems.GLOWSTONEBERRIES, false, 2.0F, 3.0F);
        addBushLoot(ModBlocks.MINERS_BERRY_BUSH, ModItems.MINERSBERRIES, false, 2.0F, 3.0F);
        addBushLoot(ModBlocks.SHULKER_BERRY_BUSH, ModItems.SHULKERBERRIES, false, 2.0F, 3.0F);
        addBushLoot(ModBlocks.CHORUS_BERRY_BUSH, ModItems.CHORUSBERRIES, false, 2.0F, 3.0F);
        addBushLoot(ModBlocks.GOLDEN_BERRY_BUSH, ModItems.GOLDENBERRIES, true, 1.0F, 2.0F);
    }

    private void addBushLoot(DeferredHolder<Block, ? extends Block> bush, DeferredHolder<Item, ? extends Item> berry, boolean dropAtAge2, float min, float max) {
        LootItemCondition.Builder isMature = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(bush.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 3));

        LootTable.Builder builder = LootTable.lootTable()
                .withPool(LootPool.lootPool().when(isMature)
                        .add(LootItem.lootTableItem(berry.get()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
                        .apply(ApplyBonusCount.addUniformBonusCount(this.registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE))));

        if (dropAtAge2) {
            LootItemCondition.Builder isGrowing = LootItemBlockStatePropertyCondition
                    .hasBlockStateProperties(bush.get())
                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 2));

            builder.withPool(LootPool.lootPool().when(isGrowing)
                    .add(LootItem.lootTableItem(berry.get()))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                    .apply(ApplyBonusCount.addUniformBonusCount(this.registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE))));
        }

        this.add(bush.get(), this.applyExplosionDecay(bush.get(), builder));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream()
                .map(holder -> (Block) holder.get())
                .toList();
    }
}