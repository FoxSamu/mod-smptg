package net.foxboi.salted.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Block that implements the salt crystal growing logic. It has 4 properties:
 * <ul>
 * <li>{@code facing}: The direction it faces.</li>
 * <li>{@code waterlogged}: Whether the block is waterlogged or not.</li>
 * <li>{@code age}: The crystal age, it starts at age 1 and grows to age 8.</li>
 * <li>{@code persistent}: Whether the crystal's age is locked, as is the case for naturally generated crystals.</li>
 * </ul>
 */
public class SaltCrystalBlock extends Block implements SimpleWaterloggedBlock {
    public static final MapCodec<SaltCrystalBlock> CODEC = simpleCodec(SaltCrystalBlock::new);

    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty AGE = IntegerProperty.create("age", 1, 8);
    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;

    private static final VoxelShape[] BASE_SHAPES = {
            boxZ(10, 16 - 3, 16),
            boxZ(10, 16 - 4, 16),
            boxZ(12, 16 - 6, 16),
            boxZ(12, 16 - 7, 16),
            boxZ(14, 16 - 8, 16),
            boxZ(14, 16 - 10, 16),
            boxZ(14, 16 - 12, 16),
            boxZ(14, 16 - 14, 16)
    };

    private static final List<Map<Direction, VoxelShape>> SHAPES = Arrays.stream(BASE_SHAPES)
            .map(Shapes::rotateAll)
            .toList();

    public SaltCrystalBlock(Properties props) {
        super(props);

        registerDefaultState(
                stateDefinition.any()
                        .setValue(FACING, Direction.UP)
                        .setValue(WATERLOGGED, false)
                        .setValue(AGE, 1)
                        .setValue(PERSISTENT, false)
        );
    }

    @Override
    protected MapCodec<SaltCrystalBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, WATERLOGGED, FACING, PERSISTENT);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        var supportPos = pos.relative(state.getValue(FACING), -1);
        var supportState = level.getBlockState(supportPos);
        return supportState.isFaceSturdy(level, supportPos, state.getValue(FACING));
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction dir, BlockPos neighbour, BlockState neighbourState, RandomSource rng) {
        if (state.getValue(WATERLOGGED)) {
            ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        if (dir == state.getValue(FACING).getOpposite()) {
            if (!neighbourState.isFaceSturdy(level, neighbour, dir)) {
                return Blocks.AIR.defaultBlockState();
            }
        }

        return state;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rng) {
        int age = state.getValue(AGE);
        if (age < 8 && !state.getValue(PERSISTENT)) {
            if (shouldGrow(level, pos, state.getValue(FACING), rng)) {
                level.setBlock(pos, state.setValue(AGE, age + 1), UPDATE_ALL);
            }
        }
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return !state.getValue(PERSISTENT);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        var age = state.getValue(AGE) - 1;
        var facing = state.getValue(FACING);

        return SHAPES.get(age).get(facing);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
    }


    // Growing Logic
    // ========================================================

    private static final double MAX_GROW_PROBABILITY = 0.3;

    public static boolean shouldGrow(LevelAccessor level, BlockPos pos, Direction facing, RandomSource rng) {
        return rng.nextDouble() < growChance(level, pos, facing);
    }

    public static double growChance(LevelAccessor level, BlockPos pos, Direction facing) {
        var speed = growSpeed(level, pos, facing);
        return (1 - Math.pow(0.97, speed)) * MAX_GROW_PROBABILITY;
    }

    public static double growSpeed(LevelAccessor level, BlockPos pos, Direction facing) {
        var speed = 0.0;
        var canDrip = facing == Direction.UP;
        var canAbsorbAllDirections = false;

        var basePos = pos.relative(facing, -1);

        // Is it in water
        if (level.getFluidState(pos).is(FluidTags.WATER)) {
            speed += 2;
            canDrip = false;
            canAbsorbAllDirections = true;
        }

        // TODO Let dripstone drips grow salt faster

        if (canAbsorbAllDirections) {
            var mpos = pos.mutable();

            for (var dir : Direction.values()) {
                mpos.set(pos).move(dir);
                var state = level.getBlockState(mpos);

                if (state.is(ModBlockTags.GROWS_SALT_MORE_LIKELY)) {
                    speed += 4;
                }

                if (state.is(ModBlockTags.GROWS_SALT)) {
                    speed += 2;
                }
            }
        } else {
            var state = level.getBlockState(basePos);

            if (state.is(ModBlockTags.GROWS_SALT_MORE_LIKELY)) {
                speed += 0.8;
            }

            if (state.is(ModBlockTags.GROWS_SALT)) {
                speed += 0.5;
            }
        }

        return speed;
    }

    public static void spontaneouslyGenerateSaltCrystal(ServerLevel level, BlockPos pos) {
        var state = level.getBlockState(pos);
        if (!state.is(Blocks.WATER) || !state.getFluidState().isSource()) {
            return;
        }

        var speed = 0.0;

        var mpos = pos.mutable();

        for (var dir : Direction.values()) {
            mpos.set(pos).move(dir);
            var nearby = level.getBlockState(mpos);

            if (nearby.is(ModBlockTags.GROWS_SALT)) {
                speed += 2;
            }

            if (nearby.is(ModBlockTags.GROWS_SALT_MORE_LIKELY)) {
                speed += 4;
            }
        }

        var chance = (1 - Math.pow(0.97, speed)) * MAX_GROW_PROBABILITY;

        if (level.random.nextDouble() >= chance) {
            return;
        }

        for (var dir : Direction.allShuffled(level.random)) {
            mpos.set(pos).move(dir, -1);
            var nearby = level.getBlockState(mpos);

            if (nearby.isFaceSturdy(level, mpos, dir)) {
                level.setBlock(
                        pos,
                        ModBlocks.SALT_CRYSTAL.defaultBlockState()
                                .setValue(WATERLOGGED, true)
                                .setValue(FACING, dir),
                        UPDATE_ALL
                );
                break;
            }
        }
    }
}
