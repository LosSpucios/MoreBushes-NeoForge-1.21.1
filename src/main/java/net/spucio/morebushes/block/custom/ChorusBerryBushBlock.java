package net.spucio.morebushes.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.spucio.morebushes.item.ModItems;

public class ChorusBerryBushBlock extends SweetBerryBushBlock {

    private static final VoxelShape SHAPE_AGE_0 = Block.box(3.5D, 0.0D, 3.1D, 12.5D, 9.0D, 12.5D);
    private static final VoxelShape SHAPE_AGE_1 = Block.box(2.5D, 0.0D, 2.5D, 13.5D, 12.0D, 13.5D);
    private static final VoxelShape SHAPE_AGE_2 = Block.box(1.65D, 0.0D, 1.65D, 14.27D, 14.0D, 14.27D);
    private static final VoxelShape SHAPE_AGE_3 = Block.box(0.67D, 0.0D, 0.67D, 15.15D, 16.0D, 15.15D);

    public ChorusBerryBushBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return net.minecraft.world.phys.shapes.Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        int age = pState.getValue(AGE);
        return switch (age) {
            case 0 -> SHAPE_AGE_0;
            case 1 -> SHAPE_AGE_1;
            case 2 -> SHAPE_AGE_2;
            default -> SHAPE_AGE_3;
        };
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        return new ItemStack(ModItems.CHORUSBERRIES.get());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHit) {
        int i = pState.getValue(AGE);
        boolean flag = i == 3;

        if (i > 1) {
            int j = 1 + pLevel.random.nextInt(2);
            popResource(pLevel, pPos, new ItemStack(ModItems.CHORUSBERRIES.get(), j + (flag ? 1 : 0)));
            pLevel.playSound(null, pPos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);

            BlockState blockstate = pState.setValue(AGE, 1);
            pLevel.setBlock(pPos, blockstate, 2);
            pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pPlayer, blockstate));

            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }

        return super.useWithoutItem(pState, pLevel, pPos, pPlayer, pHit);
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        super.entityInside(pState, pLevel, pPos, pEntity);

        if (!pLevel.isClientSide && pEntity instanceof LivingEntity livingEntity && !(pEntity instanceof Fox)) {
            if (pLevel.random.nextInt(4) == 0) {
                teleportRandomly(livingEntity, pLevel);
            }
        }
    }

    private void teleportRandomly(LivingEntity entity, Level level) {
        double d0 = entity.getX();
        double d1 = entity.getY();
        double d2 = entity.getZ();

        for(int i = 0; i < 16; ++i) {
            double d3 = entity.getX() + (entity.getRandom().nextDouble() - 0.5D) * 16.0D;
            double d4 = Math.min(Math.max(entity.getY() + (double)(entity.getRandom().nextInt(16) - 8), (double)level.getMinBuildHeight()), (double)(level.getHeight() - 1 + level.getMinBuildHeight()));
            double d5 = entity.getZ() + (entity.getRandom().nextDouble() - 0.5D) * 16.0D;

            if (entity.isPassenger()) {
                entity.stopRiding();
            }

            if (entity.randomTeleport(d3, d4, d5, true)) {
                level.playSound(null, d0, d1, d2, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
                entity.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
                break;
            }
        }
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(Blocks.END_STONE);
    }
}