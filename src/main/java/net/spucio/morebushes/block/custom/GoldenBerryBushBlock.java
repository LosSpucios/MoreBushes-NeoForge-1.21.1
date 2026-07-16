package net.spucio.morebushes.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.spucio.morebushes.item.ModItems;

public class GoldenBerryBushBlock extends SweetBerryBushBlock implements BonemealableBlock {

    private static final VoxelShape SHAPE_AGE_0 = Block.box(3.5D, 0.0D, 3.1D, 12.5D, 9.0D, 12.5D);
    private static final VoxelShape SHAPE_AGE_1 = Block.box(2.5D, 0.0D, 2.5D, 13.5D, 12.0D, 13.5D);
    private static final VoxelShape SHAPE_AGE_2 = Block.box(1.65D, 0.0D, 1.65D, 14.27D, 14.0D, 14.27D);
    private static final VoxelShape SHAPE_AGE_3 = Block.box(0.67D, 0.0D, 0.67D, 15.15D, 16.0D, 15.15D);

    public GoldenBerryBushBlock(Properties properties) {
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
        return new ItemStack(ModItems.GOLDENBERRIES.get());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHit) {
        int age = pState.getValue(AGE);

        if (age == 3) {
            int j = 1 + pLevel.random.nextInt(2);
            popResource(pLevel, pPos, new ItemStack(ModItems.GOLDENBERRIES.get(), j));
            pLevel.playSound(null, pPos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);

            BlockState blockstate = pState.setValue(AGE, 1);
            pLevel.setBlock(pPos, blockstate, 2);
            pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pPlayer, blockstate));
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        int age = pState.getValue(AGE);
        if (pStack.is(Items.GOLD_INGOT) && age < 3) {
            if (!pLevel.isClientSide) {
                if (pLevel.random.nextFloat() < 0.4F) {
                    pLevel.playSound(null, pPos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    if (pLevel instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.WAX_ON, pPos.getX() + 0.5D, pPos.getY() + 0.5D, pPos.getZ() + 0.5D, 10, 0.3D, 0.3D, 0.3D, 0.05D);
                    }
                    pLevel.setBlock(pPos, pState.setValue(AGE, age + 1), 2);
                } else {
                    pLevel.playSound(null, pPos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 0.5F, 1.0F);
                    if (pLevel instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.WAX_ON, pPos.getX() + 0.5D, pPos.getY() + 0.5D, pPos.getZ() + 0.5D, 10, 0.3D, 0.3D, 0.3D, 0.05D);
                    }
                }
                if (!pPlayer.isCreative()) pStack.shrink(1);
            }
            return ItemInteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        return super.useItemOn(pStack, pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState) { return false; }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) { return false; }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {}

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextFloat() < 0.5F) {
            super.randomTick(pState, pLevel, pPos, pRandom);
        }
    }
}