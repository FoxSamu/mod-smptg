package net.foxboi.salted.common.item;

import net.foxboi.salted.common.block.DiagonallyAttachableBlock;
import net.foxboi.salted.common.misc.DiagonalDirection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class DiagonallyAttachableBlockItem extends BlockItem {
    public DiagonallyAttachableBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    private boolean isCorrectBlock() {
        return getBlock() instanceof DiagonallyAttachableBlock;
    }

    private DiagonallyAttachableBlock getCorrectBlock() {
        return (DiagonallyAttachableBlock) getBlock();
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!isCorrectBlock()) {
            return super.useOn(context);
        }

        var result = place(new PlaceContext(context));

        return !result.consumesAction() && context.getItemInHand().has(DataComponents.CONSUMABLE)
                ? super.use(context.getLevel(), context.getPlayer(), context.getHand())
                : result;
    }

    private BlockState getPlacementStateWithoutObstructionCheck(BlockPlaceContext context) {
        if (context.getClickedFace().getAxis().isVertical()) {
            return findBestState(context.getLevel(), context.getClickedPos(), context);
        }

        if (context.replacingClickedOnBlock() || !(context instanceof PlaceContext placeContext)) {
            return findBestState(context.getLevel(), context.getClickedPos(), context);
        }

        var state = getBlock().defaultBlockState().setValue(DiagonallyAttachableBlock.FACING, placeContext.getDiagonalDirection());
        if (!state.canSurvive(context.getLevel(), context.getClickedPos())) {
            return findBestState(context.getLevel(), context.getClickedPos(), context);
        }

        return state;
    }

    @Override
    protected BlockState getPlacementState(BlockPlaceContext context) {
        if (!isCorrectBlock()) {
            return super.getPlacementState(context);
        }

        var state = getPlacementStateWithoutObstructionCheck(context);
        return state != null && canPlace(context, state)
                ? state : null;
    }

    private BlockState findBestState(LevelReader level, BlockPos pos, BlockPlaceContext context) {
        var bestDirections = DiagonalDirection.getBestFittingDirections(context.getPlayer());

        for (var ddir : bestDirections) {
            var state = getBlock().defaultBlockState().setValue(DiagonallyAttachableBlock.FACING, ddir);
            if (state.canSurvive(level, pos)) {
                return state;
            }
        }

        return null;
    }

    private static final double ALWAYS_CORNER_INCIDENT_ANGLE_THRESHOLD = 60d;

    // From an UseOnContext, determines the angle between the hit normal and the incident vector, the incident vector
    // being the vector from the hit point towards the source of the raycast. Only computed on the XZ plane, the X angle
    // is not considered and assumed to be 0 for simplicity.
    private static double incidentAngleInDegrees(Direction face, Vec3 playerDir) {
        if (face.getAxis().isVertical()) {
            return 90; // We assume X angle is 0 so incident on vertical faces is always 90 - we don't really need this anyway.
        }

        var normalX = (double) face.getStepX();
        var normalZ = (double) face.getStepZ();

        var incidentX = -playerDir.x;
        var incidentZ = -playerDir.z;

        var dotProduct = normalX * incidentX + normalZ * incidentZ;

        // Dot product is the cosine of the angle between the two vectors.
        var angleRadians = Math.acos(dotProduct);
        return Math.toDegrees(angleRadians);
    }

    private static DiagonalDirection determinePlaceDirection(UseOnContext context) {
        var hit = context.getClickLocation();
        var pos = context.getClickedPos();
        var level = context.getLevel();

        var hitX = hit.x - (pos.getX());
        var hitZ = hit.z - (pos.getZ());

        var player = context.getPlayer();
        var face = context.getClickedFace();

        // No player, so hit point is probably weird and will cause weird behaviour. Let's stick with a simpler
        // behaviour.
        if (player == null) {
            return switch (context.getClickedFace()) {
                case NORTH -> DiagonalDirection.NORTH;
                case SOUTH -> DiagonalDirection.SOUTH;
                case WEST -> DiagonalDirection.WEST;
                case EAST -> DiagonalDirection.EAST;

                default -> null;
            };
        }

        var playerDir = player.calculateViewVector(0f, player.getViewYRot(1f));
        var incidentAngle = incidentAngleInDegrees(face, playerDir);
        var alwaysCorner = incidentAngle > ALWAYS_CORNER_INCIDENT_ANGLE_THRESHOLD;

        var dir = switch (face) {
            case NORTH -> {
                if (alwaysCorner && playerDir.x < 0) {
                    yield DiagonalDirection.NORTH_EAST;
                }
                if (alwaysCorner && playerDir.x > 0) {
                    yield DiagonalDirection.NORTH_WEST;
                }
                if (hitX < CORNER_THRESHOLD) {
                    yield DiagonalDirection.NORTH_WEST;
                }
                if (hitX > 1 - CORNER_THRESHOLD) {
                    yield DiagonalDirection.NORTH_EAST;
                }
                yield DiagonalDirection.NORTH;
            }
            case SOUTH -> {
                if (alwaysCorner && playerDir.x < 0) {
                    yield DiagonalDirection.SOUTH_EAST;
                }
                if (alwaysCorner && playerDir.x > 0) {
                    yield DiagonalDirection.SOUTH_WEST;
                }
                if (hitX < CORNER_THRESHOLD) {
                    yield DiagonalDirection.SOUTH_WEST;
                }
                if (hitX > 1 - CORNER_THRESHOLD) {
                    yield DiagonalDirection.SOUTH_EAST;
                }
                yield DiagonalDirection.SOUTH;
            }
            case WEST -> {
                if (alwaysCorner && playerDir.z < 0) {
                    yield DiagonalDirection.SOUTH_WEST;
                }
                if (alwaysCorner && playerDir.z > 0) {
                    yield DiagonalDirection.NORTH_WEST;
                }
                if (hitZ < CORNER_THRESHOLD) {
                    yield DiagonalDirection.NORTH_WEST;
                }
                if (hitZ > 1 - CORNER_THRESHOLD) {
                    yield DiagonalDirection.SOUTH_WEST;
                }
                yield DiagonalDirection.WEST;
            }
            case EAST -> {
                if (alwaysCorner && playerDir.z < 0) {
                    yield DiagonalDirection.SOUTH_EAST;
                }
                if (alwaysCorner && playerDir.z > 0) {
                    yield DiagonalDirection.NORTH_EAST;
                }
                if (hitZ < CORNER_THRESHOLD) {
                    yield DiagonalDirection.NORTH_EAST;
                }
                if (hitZ > 1 - CORNER_THRESHOLD) {
                    yield DiagonalDirection.SOUTH_EAST;
                }
                yield DiagonalDirection.EAST;
            }

            default -> null;
        };

        if (dir == null) {
            return null;
        }

        // If we place on a corner, but that corner is not available, just pretend we placed on that face.
        if (dir.isDiagonal() && !DiagonallyAttachableBlock.isCornerAvailable(level, pos, dir)) {
            return switch (context.getClickedFace()) {
                case NORTH -> DiagonalDirection.NORTH;
                case SOUTH -> DiagonalDirection.SOUTH;
                case WEST -> DiagonalDirection.WEST;
                case EAST -> DiagonalDirection.EAST;

                default -> null;
            };
        }

        return dir;
    }

    private static final double CORNER_THRESHOLD = 3d / 16d;

    private static class PlaceContext extends BlockPlaceContext {
        private final DiagonalDirection diagonalDirection;
        private final BlockPos placePos;

        public PlaceContext(UseOnContext context) {
            super(context);

            var dir = determinePlaceDirection(context);
            this.diagonalDirection = dir;


            var placePos = super.getClickedPos();
            if (dir != null) {
                placePos = dir.offset(placePos.relative(getClickedFace(), -1), 1);
            }

            this.placePos = placePos;
        }

        @Override
        public BlockPos getClickedPos() {
            return replacingClickedOnBlock() ? super.getClickedPos() : placePos;
        }

        public DiagonalDirection getDiagonalDirection() {
            return diagonalDirection;
        }

        @Override
        public boolean canPlace() {
            return super.canPlace();
        }
    }
}
