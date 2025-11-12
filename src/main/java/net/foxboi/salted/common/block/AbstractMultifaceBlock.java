package net.foxboi.salted.common.block;

import java.util.*;
import java.util.function.Function;

import com.mojang.serialization.MapCodec;
import net.foxboi.salted.common.util.ModUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class AbstractMultifaceBlock extends Block {
    protected static final List<Direction> DIRECTIONS = ModUtil.DIRECTIONS;
    protected static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION;

    private final Function<BlockState, VoxelShape> shapes;
    private final boolean canRotate;
    private final boolean canMirrorX;
    private final boolean canMirrorZ;

    @Override
    protected abstract MapCodec<? extends AbstractMultifaceBlock> codec();

    public AbstractMultifaceBlock(double radius, double thickness, Properties properties) {
        super(properties);

        shapes = makeShapes(radius, thickness);

        canRotate = ModUtil.HORIZONTAL_DIRECTIONS.stream().allMatch(this::isFaceSupported);
        canMirrorX = ModUtil.X_DIRECTIONS.stream().filter(this::isFaceSupported).count() % 2 == 0;
        canMirrorZ = ModUtil.Z_DIRECTIONS.stream().filter(this::isFaceSupported).count() % 2 == 0;

        registerDefaultState(makeDefaultState());
    }

    protected BlockState makeDefaultState() {
        var defaultState = stateDefinition.any();

        for (var faceProperty : PROPERTY_BY_DIRECTION.values()) {
            defaultState = defaultState.trySetValue(faceProperty, false);
        }

        return defaultState;
    }

    protected Function<BlockState, VoxelShape> makeShapes(double radius, double thickness) {
        var rotatedFaces = Shapes.rotateAll(Block.boxZ(radius, 0, thickness));

        return getShapeForEachState(state -> {
            var shape = Shapes.empty();

            for (var dir : DIRECTIONS) {
                if (hasFace(state, dir)) {
                    shape = Shapes.or(shape, rotatedFaces.get(dir));
                }
            }

            return shape.isEmpty() ? Shapes.block() : shape;
        });
    }

    protected boolean isFaceSupported(Direction face) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        for (var face : DIRECTIONS) {
            if (isFaceSupported(face)) {
                builder.add(getFaceProperty(face));
            }
        }
    }

    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext ctx) {
        return super.canBeReplaced(state, ctx) || hasAnyVacantFace(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        var level = ctx.getLevel();
        var pos = ctx.getClickedPos();
        var state = level.getBlockState(pos);

        return Arrays.stream(ctx.getNearestLookingDirections())
                .map(it -> getStateForPlacement(state, level, pos, it))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    public boolean isValidStateForPlacement(BlockGetter level, BlockState state, BlockPos pos, Direction addFace) {
        if (!isFaceSupported(addFace) || (state.is(this) && hasFace(state, addFace))) {
            return false;
        }

        var supportPos = pos.relative(addFace);
        return canAttachTo(level, addFace, supportPos, level.getBlockState(supportPos));
    }

    public BlockState getStateForPlacement(BlockState state, BlockGetter level, BlockPos pos, Direction addFace) {
        if (!isValidStateForPlacement(level, state, pos, addFace)) {
            return null;
        }

        if (!state.is(this)) {
            state = defaultBlockState();
        }

        return state.setValue(getFaceProperty(addFace), true);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction dir, BlockPos nearPos, BlockState nearState, RandomSource rng) {
        if (!hasAnyFace(state)) {
            return Blocks.AIR.defaultBlockState();
        }

        return hasFace(state, dir) && !canAttachTo(level, dir, nearPos, nearState)
                ? removeFace(state, dir)
                : state;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return shapes.apply(state);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        var survives = false;

        for (var face : DIRECTIONS) {
            if (hasFace(state, face)) {
                if (!canAttachTo(level, pos, face)) {
                    return false;
                }

                survives = true;
            }
        }

        return survives;
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        if (!canRotate) {
            return state;
        }

        return mapDirections(state, rotation::rotate);
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        if (mirror == Mirror.FRONT_BACK && !canMirrorX) {
            return state;
        }

        if (mirror == Mirror.LEFT_RIGHT && !canMirrorZ) {
            return state;
        }

        return mapDirections(state, mirror::mirror);
    }

    private BlockState mapDirections(BlockState state, Function<Direction, Direction> mapping) {
        var newState = state;

        for (var face : DIRECTIONS) {
            if (isFaceSupported(face)) {
                newState = newState.setValue(getFaceProperty(mapping.apply(face)), state.getValue(getFaceProperty(face)));
            }
        }

        return newState;
    }

    public boolean canAttachTo(BlockGetter level, BlockPos pos, Direction face) {
        var supportPos = pos.relative(face);
        var supportState = level.getBlockState(supportPos);
        return canAttachTo(level, face, supportPos, supportState);
    }

    public boolean canAttachTo(BlockGetter level, Direction face, BlockPos supportPos, BlockState supportState) {
        return Block.isFaceFull(supportState.getBlockSupportShape(level, supportPos), face.getOpposite())
                || Block.isFaceFull(supportState.getCollisionShape(level, supportPos), face.getOpposite());
    }

    public BlockState tryAddFace(LevelReader level, BlockPos pos, Direction face) {
        if (!canAttachTo(level, pos, face)) {
            return null;
        }

        var current = level.getBlockState(pos);

        var placeState = defaultBlockState().setValue(getFaceProperty(face), true);
        if (current.getBlock() == this) {
            if (current.getValue(getFaceProperty(face))) {
                return null;
            }

            placeState = current.setValue(getFaceProperty(face), true);
        }

        return placeState;
    }

    public static BlockState tryAddVanillaMultiface(MultifaceBlock block, LevelAccessor level, BlockPos pos, Direction face) {
        if (!MultifaceBlock.canAttachTo(level, pos, face)) {
            return null;
        }

        var current = level.getBlockState(pos);

        var placeState = block.defaultBlockState().setValue(getFaceProperty(face), true);
        if (current.getBlock() == block) {
            if (current.getValue(getFaceProperty(face))) {
                return null;
            }

            placeState = current.setValue(getFaceProperty(face), true);
        }

        return placeState;
    }

    public static boolean hasFace(BlockState state, Direction face) {
        return state.getValueOrElse(getFaceProperty(face), false);
    }

    private static BlockState removeFace(BlockState state, Direction face) {
        var newState = state.setValue(getFaceProperty(face), false);

        return hasAnyFace(newState)
                ? newState
                : Blocks.AIR.defaultBlockState();
    }

    public static BooleanProperty getFaceProperty(Direction face) {
        return PROPERTY_BY_DIRECTION.get(face);
    }

    public static boolean hasAnyFace(BlockState state) {
        for (var face : DIRECTIONS) {
            if (hasFace(state, face)) {
                return true;
            }
        }

        return false;
    }

    public static boolean hasAnyVacantFace(BlockState state) {
        for (var face : DIRECTIONS) {
            if (!hasFace(state, face)) {
                return true;
            }
        }

        return false;
    }

    public static Set<Direction> availableFaces(BlockState state) {
        var block = state.getBlock();

        if (!(block instanceof AbstractMultifaceBlock)) {
            return MultifaceBlock.availableFaces(state);
        }

        var faces = EnumSet.noneOf(Direction.class);

        for (var face : DIRECTIONS) {
            if (hasFace(state, face)) {
                faces.add(face);
            }
        }

        return faces;
    }

    /**
     * Called from a mixin on {@link #updateOrDestroy} to cause a destroy effect on a specific set of faces of a
     * multiface block. This is not only to make a shape update effect more consistent on a multiface block, but also
     * to allow getting block drops when faces are removed from a multiface block. This also deals with vanilla
     * multiface blocks.
     */
    public static void destroyFaces(LevelAccessor level, BlockPos pos, BlockState oldState, BlockState newState, int setFlags, int recursion) {
        if (oldState == newState) {
            return;
        }

        if (newState.isAir()) {
            level.destroyBlock(pos, (setFlags & UPDATE_SUPPRESS_DROPS) == 0, null, recursion);
            return;
        }

        if (newState.getBlock() != oldState.getBlock()) {
            level.setBlock(pos, newState, (setFlags & ~UPDATE_SUPPRESS_DROPS), recursion);
            return;
        }

        // Compute the block state that has only the faces that were removed
        var diffState = oldState;
        for (var face : DIRECTIONS) {
            var faceProperty = getFaceProperty(face);
            diffState = diffState.setValue(faceProperty, oldState.getValue(faceProperty) && !newState.getValue(faceProperty));
        }

        if (!level.isClientSide()) {
            // Temporarily place this difference block state and destroy it to create the destroy effect.
            // Giving 0 as a remaining recursion depth ensures that no neighbour updates occur unnecessarily from these
            // two intermediate block updates.
            level.setBlock(pos, diffState, 0, 0);
            level.destroyBlock(pos, (setFlags & UPDATE_SUPPRESS_DROPS) == 0, null, 0);
        }

        level.setBlock(pos, newState, (setFlags & ~UPDATE_SUPPRESS_DROPS), recursion);
    }

    public MultifaceSpreader createMultifaceSpreader() {
        return new MultifaceSpreader(new SpreaderConfig(this));
    }

    public static class SpreaderConfig implements MultifaceSpreader.SpreadConfig {
        protected final AbstractMultifaceBlock block;
        protected final boolean waterloggable;

        public SpreaderConfig(AbstractMultifaceBlock block) {
            this.block = block;
            this.waterloggable = block.stateDefinition.getProperties().contains(BlockStateProperties.WATERLOGGED);
        }

        @Override
        public BlockState getStateForPlacement(BlockState state, BlockGetter level, BlockPos pos, Direction addFace) {
            return block.getStateForPlacement(state, level, pos, addFace);
        }

        protected boolean stateCanBeReplaced(BlockGetter level, BlockPos fromPos, BlockPos toPos, Direction toFace, BlockState state) {
            if (state.isAir() || state.is(block)) {
                return true;
            }

            if (waterloggable) {
                return state.is(Blocks.WATER) && state.getFluidState().isSource();
            }

            return false;
        }

        @Override
        public boolean canSpreadInto(BlockGetter level, BlockPos fromPos, MultifaceSpreader.SpreadPos spreadPos) {
            var toPos = spreadPos.pos();
            var toFace = spreadPos.face();

            var stateAtSpreadPos = level.getBlockState(toPos);

            return stateCanBeReplaced(level, fromPos, toPos, toFace, stateAtSpreadPos)
                    && block.isValidStateForPlacement(level, stateAtSpreadPos, toPos, toFace);
        }
    }
}
