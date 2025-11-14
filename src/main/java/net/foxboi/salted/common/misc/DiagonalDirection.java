package net.foxboi.salted.common.misc;

import java.util.*;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction8;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;

public enum DiagonalDirection implements StringRepresentable {
    NORTH("north", Direction8.NORTH, false, Direction.NORTH),
    NORTH_EAST("north_east", Direction8.NORTH_EAST, true, Direction.NORTH),
    EAST("east", Direction8.EAST, false, Direction.EAST),
    SOUTH_EAST("south_east", Direction8.SOUTH_EAST, true, Direction.EAST),
    SOUTH("south", Direction8.SOUTH, false, Direction.SOUTH),
    SOUTH_WEST("south_west", Direction8.SOUTH_WEST, true, Direction.SOUTH),
    WEST("west", Direction8.WEST, false, Direction.WEST),
    NORTH_WEST("north_west", Direction8.NORTH_WEST, true, Direction.WEST);

    private final String serialName;
    private final Direction8 direction;
    private final boolean isDiagonal;
    private final Direction bestCardinalDirection;

    DiagonalDirection(String serialName, Direction8 direction, boolean isDiagonal, Direction bestCardinalDirection) {
        this.serialName = serialName;
        this.direction = direction;
        this.isDiagonal = isDiagonal;
        this.bestCardinalDirection = bestCardinalDirection;
    }

    public Set<Direction> getDirections() {
        return direction.getDirections();
    }

    public int getStepX() {
        return direction.getStepX();
    }

    public int getStepZ() {
        return direction.getStepZ();
    }


    public float getNormX() {
        return direction.getStepX() * (isDiagonal ? Mth.SQRT_OF_TWO / 2 : 1f);
    }

    public float getNormZ() {
        return direction.getStepZ() * (isDiagonal ? Mth.SQRT_OF_TWO / 2 : 1f);
    }

    public boolean isDiagonal() {
        return isDiagonal;
    }

    public Direction getBestCardinalDirection() {
        return bestCardinalDirection;
    }

    public DiagonalDirection getOpposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case NORTH_EAST -> SOUTH_WEST;
            case EAST -> WEST;
            case SOUTH_EAST -> NORTH_WEST;
            case SOUTH -> NORTH;
            case SOUTH_WEST -> NORTH_EAST;
            case WEST -> EAST;
            case NORTH_WEST -> SOUTH_EAST;
        };
    }

    public DiagonalDirection getClockwise() {
        return switch (this) {
            case NORTH -> EAST;
            case NORTH_EAST -> SOUTH_EAST;
            case EAST -> SOUTH;
            case SOUTH_EAST -> SOUTH_WEST;
            case SOUTH -> WEST;
            case SOUTH_WEST -> NORTH_WEST;
            case WEST -> NORTH;
            case NORTH_WEST -> NORTH_EAST;
        };
    }

    public DiagonalDirection getCounterClockwise() {
        return switch (this) {
            case NORTH -> WEST;
            case NORTH_EAST -> NORTH_WEST;
            case EAST -> NORTH;
            case SOUTH_EAST -> NORTH_EAST;
            case SOUTH -> EAST;
            case SOUTH_WEST -> SOUTH_EAST;
            case WEST -> SOUTH;
            case NORTH_WEST -> SOUTH_WEST;
        };
    }

    public DiagonalDirection getMirrorX() {
        return switch (this) {
            case NORTH -> NORTH;
            case NORTH_EAST -> NORTH_WEST;
            case EAST -> WEST;
            case SOUTH_EAST -> SOUTH_WEST;
            case SOUTH -> SOUTH;
            case SOUTH_WEST -> SOUTH_EAST;
            case WEST -> EAST;
            case NORTH_WEST -> NORTH_EAST;
        };
    }

    public DiagonalDirection getMirrorZ() {
        return switch (this) {
            case NORTH -> SOUTH;
            case NORTH_EAST -> SOUTH_EAST;
            case EAST -> EAST;
            case SOUTH_EAST -> NORTH_EAST;
            case SOUTH -> NORTH;
            case SOUTH_WEST -> NORTH_WEST;
            case WEST -> WEST;
            case NORTH_WEST -> SOUTH_WEST;
        };
    }

    public DiagonalDirection rotate(Rotation rotation) {
        return switch (rotation) {
            case NONE -> this;
            case CLOCKWISE_90 -> getClockwise();
            case CLOCKWISE_180 -> getOpposite();
            case COUNTERCLOCKWISE_90 -> getCounterClockwise();
        };
    }

    public DiagonalDirection mirror(Mirror mirror) {
        return switch (mirror) {
            case NONE -> this;
            case LEFT_RIGHT -> getMirrorZ();
            case FRONT_BACK -> getMirrorX();
        };
    }

    public static List<DiagonalDirection> getBestFittingDirections(double x, double z) {
        var directions = values();
        Arrays.sort(directions, Comparator.comparingDouble(dir -> x * dir.getNormX() + z * dir.getNormZ()));
        return List.of(directions);
    }

    public static List<DiagonalDirection> getBestFittingDirections(Entity entity) {
        if (entity == null) {
            return List.of(values());
        }

        var angle = entity.calculateViewVector(0f, entity.getViewYRot(1f));
        return getBestFittingDirections(angle.x, angle.z);
    }

    public BlockPos offset(BlockPos pos, int amount) {
        return pos.offset(getStepX() * amount, 0, getStepZ() * amount);
    }

    @Override
    public String getSerializedName() {
        return serialName;
    }
}