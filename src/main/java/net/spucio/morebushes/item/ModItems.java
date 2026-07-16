package net.spucio.morebushes.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.spucio.morebushes.item.JuiceItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.spucio.morebushes.MoreBushes;
import net.spucio.morebushes.block.ModBlocks;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MoreBushes.MOD_ID);

    public static final DeferredItem<Item> BLUEBERRIES = ITEMS.register("blueberries",
            () -> new ItemNameBlockItem(ModBlocks.BLUE_BERRY_BUSH.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1F).effect(new MobEffectInstance(MobEffects.WATER_BREATHING, 60), 1.0F).build())));

    public static final DeferredItem<Item> GOLDENBERRIES = ITEMS.register("golden_berries",
            () -> new ItemNameBlockItem(ModBlocks.GOLDEN_BERRY_BUSH.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.25F).effect(new MobEffectInstance(MobEffects.REGENERATION, 200, 1), 1.0F).effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 3000, 0), 1.0F).effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 3000, 0), 1.0F).effect(new MobEffectInstance(MobEffects.ABSORPTION, 1200, 3), 1.0F).alwaysEdible().build())));

    public static final DeferredItem<Item> LAVABERRIES = ITEMS.register("lava_berries",
            () -> new ItemNameBlockItem(ModBlocks.LAVA_BERRY_BUSH.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1F).effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 60), 1.0F).build())));

    public static final DeferredItem<Item> GLOWSTONEBERRIES = ITEMS.register("glowstone_berries",
            () -> new ItemNameBlockItem(ModBlocks.GLOWSTONE_BERRY_BUSH.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1F).effect(new MobEffectInstance(MobEffects.GLOWING, 60), 1.0F).effect(new MobEffectInstance(MobEffects.REGENERATION, 60), 1.0F).build())));

    public static final DeferredItem<Item> MINERSBERRIES = ITEMS.register("miners_berries",
            () -> new ItemNameBlockItem(ModBlocks.MINERS_BERRY_BUSH.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1F).effect(new MobEffectInstance(MobEffects.DIG_SPEED, 60), 1.0F).effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 60), 1.0F).build())));

    public static final DeferredItem<Item> SHULKERBERRIES = ITEMS.register("shulker_berries",
            () -> new ItemNameBlockItem(ModBlocks.SHULKER_BERRY_BUSH.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1F).effect(new MobEffectInstance(MobEffects.LEVITATION, 60), 1.0F).build())));

    public static final DeferredItem<Item> CHORUSBERRIES = ITEMS.register("chorus_berries",
            () -> new ItemNameBlockItem(ModBlocks.CHORUS_BERRY_BUSH.get(),
                    new Item.Properties().food(new FoodProperties.Builder()
                            .nutrition(2)
                            .saturationModifier(0.1F)
                            .build())) {

                @Override
                public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
                    ItemStack result = super.finishUsingItem(stack, level, entityLiving);

                    if (!level.isClientSide) {
                        double startX = entityLiving.getX();
                        double startY = entityLiving.getY();
                        double startZ = entityLiving.getZ();

                        for (int i = 0; i < 16; ++i) {
                            double d3 = entityLiving.getX() + (entityLiving.getRandom().nextDouble() - 0.5D) * 32.0D;
                            double d4 = Math.min(Math.max(entityLiving.getY() + (double) (entityLiving.getRandom().nextInt(32) - 16),
                                    (double) level.getMinBuildHeight()), (double) (level.getHeight() - 1 + level.getMinBuildHeight()));
                            double d5 = entityLiving.getZ() + (entityLiving.getRandom().nextDouble() - 0.5D) * 32.0D;

                            if (entityLiving.isPassenger()) {
                                entityLiving.stopRiding();
                            }

                            if (entityLiving.randomTeleport(d3, d4, d5, true)) {
                                level.playSound(null, startX, startY, startZ, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);// Dźwięk u gracza
                                entityLiving.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);

                                entityLiving.resetFallDistance();
                                break;
                            }
                        }
                    }
                    return result;
                }
            });

    public static final DeferredItem<Item> SWEETBERRYJUICE = ITEMS.register("sweet_berry_juice",
            () -> new JuiceItem(new Item.Properties().stacksTo(16).food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1F).effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 480), 1.0F).alwaysEdible().build())));

    public static final DeferredItem<Item> BLUEBERRYJUICE = ITEMS.register("blueberry_juice",
            () -> new JuiceItem(new Item.Properties().stacksTo(16).food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1F).effect(new MobEffectInstance(MobEffects.WATER_BREATHING, 480), 1.0F).alwaysEdible().build())));

    public static final DeferredItem<Item> GOLDENBERRYJUICE = ITEMS.register("golden_berry_juice",
            () -> new JuiceItem(new Item.Properties().stacksTo(16).food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.25F).effect(new MobEffectInstance(MobEffects.REGENERATION, 800, 1), 1.0F).effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 12000, 0), 1.0F).effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 12000, 0), 1.0F).effect(new MobEffectInstance(MobEffects.ABSORPTION, 4800, 3), 1.0F).alwaysEdible().build())));

    public static final DeferredItem<Item> LAVABERRYJUICE = ITEMS.register("lava_berry_juice",
            () -> new JuiceItem(new Item.Properties().stacksTo(16).food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1F).effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 480), 1.0F).alwaysEdible().build())));

    public static final DeferredItem<Item> GLOWSTONEBERRYJUICE = ITEMS.register("glowstone_berry_juice",
            () -> new JuiceItem(new Item.Properties().stacksTo(16).food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1F).effect(new MobEffectInstance(MobEffects.GLOWING, 480), 1.0F).effect(new MobEffectInstance(MobEffects.REGENERATION, 480), 1.0F).alwaysEdible().build())));

    public static final DeferredItem<Item> MINERSBERRYJUICE = ITEMS.register("miners_berry_juice",
            () -> new JuiceItem(new Item.Properties().stacksTo(16).food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1F).effect(new MobEffectInstance(MobEffects.DIG_SPEED, 480), 1.0F).effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 480), 1.0F).alwaysEdible().build())));

    public static final DeferredItem<Item> SHULKERBERRYJUICE = ITEMS.register("shulker_berry_juice",
            () -> new JuiceItem(new Item.Properties().stacksTo(16).food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1F).effect(new MobEffectInstance(MobEffects.LEVITATION, 480), 1.0F).alwaysEdible().build())));

    public static final DeferredItem<Item> CHORUSBERRYJUICE = ITEMS.register("chorus_berry_juice",
            () -> new JuiceItem(new Item.Properties().stacksTo(16)
                    .food(new FoodProperties.Builder()
                    .nutrition(8)
                    .saturationModifier(0.1F)
                    .alwaysEdible()
                    .build())) {

                @Override
                public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
                    ItemStack result = super.finishUsingItem(stack, level, entityLiving);

                    if (!level.isClientSide) {
                        double startX = entityLiving.getX();
                        double startY = entityLiving.getY();
                        double startZ = entityLiving.getZ();

                        for (int i = 0; i < 16; ++i) {
                            double d3 = entityLiving.getX() + (entityLiving.getRandom().nextDouble() - 0.5D) * 64.0D;
                            double d4 = Math.min(Math.max(entityLiving.getY() + (double) (entityLiving.getRandom().nextInt(64) - 32),
                                    (double) level.getMinBuildHeight()), (double) (level.getHeight() - 1 + level.getMinBuildHeight()));
                            double d5 = entityLiving.getZ() + (entityLiving.getRandom().nextDouble() - 0.5D) * 64.0D;

                            if (entityLiving.isPassenger()) {
                                entityLiving.stopRiding();
                            }

                            if (entityLiving.randomTeleport(d3, d4, d5, true)) {
                                level.playSound(null, startX, startY, startZ, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);// Dźwięk u gracza
                                entityLiving.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);

                                entityLiving.resetFallDistance();
                                break;
                            }
                        }
                    }
                    return result;
                }
            });

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}