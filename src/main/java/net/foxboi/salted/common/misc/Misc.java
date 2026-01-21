package net.foxboi.salted.common.misc;

import java.util.*;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.foxboi.salted.common.block.ModBlockTags;
import net.foxboi.salted.common.levelgen.ModVegetationPlacements;
import net.foxboi.salted.common.levelgen.biome.ModBiomeTags;
import net.foxboi.salted.common.misc.cache.CacheKey;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.phys.AABB;

public class Misc {
    public static final List<Direction> DIRECTIONS = List.of(Direction.values());
    public static final List<Direction.Axis> AXES = List.of(Direction.Axis.values());
    public static final List<Direction.AxisDirection> AXIS_DIRECTIONS = List.of(Direction.AxisDirection.values());
    public static final List<Direction.Plane> PLANES = List.of(Direction.Plane.values());
    public static final List<Direction> HORIZONTAL_DIRECTIONS = Direction.Plane.HORIZONTAL.stream().toList();
    public static final List<Direction> X_DIRECTIONS = Direction.Plane.HORIZONTAL.stream().filter(Direction.Axis.X).toList();
    public static final List<Direction> Z_DIRECTIONS = Direction.Plane.HORIZONTAL.stream().filter(Direction.Axis.Z).toList();
    public static final List<Direction> VERTICAL_DIRECTIONS = Direction.Plane.VERTICAL.stream().toList();

    public static BlockPos getDoubleBlockPos(BlockPos pos, BlockState state) {
        return switch (state.getValueOrElse(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER)) {
            case UPPER -> pos.below();
            case LOWER -> pos;
        };
    }

    private static final Map<ResourceKey<PlacedFeature>, CacheKey<Biome, Boolean>> HAS_FEATURE_CACHES = new HashMap<>();

    private static boolean computeHasFeature(Biome biome, ResourceKey<PlacedFeature> key) {
        var features = biome.getGenerationSettings().features();
        for (var step : features) {
            for (var feature : step) {
                if (feature.is(key)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean hasFeature(Holder<Biome> biome, ResourceKey<PlacedFeature> key) {
        return HAS_FEATURE_CACHES
                .computeIfAbsent(key, k -> CacheKey.of(it -> computeHasFeature(it, k)))
                .get(biome);
    }

    private static boolean biomeHasCattail(Holder<Biome> biome) {
        return hasFeature(biome, ModVegetationPlacements.PATCH_CATTAIL_IN_WATER)
                || hasFeature(biome, ModVegetationPlacements.PATCH_CATTAIL_NEAR_WATER)
                || hasFeature(biome, ModVegetationPlacements.PATCH_CATTAIL_IN_WATER_SWAMP)
                || hasFeature(biome, ModVegetationPlacements.PATCH_CATTAIL_SWAMP);
    }

    public static boolean tryModifiedUnderwaterBonemealEffect(ServerLevel level, RandomSource rng, BlockPos pos, Direction clickedFace) {
        var biome = level.getBiome(pos);

        if (biomeHasCattail(biome)) {
            BonemealSpreadingLogic.underwaterWithCattail(level, rng, pos, clickedFace);
            return true;
        }

        return false;
    }

    public static boolean tryModifiedGrassBlockBonemealEffect(ServerLevel level, RandomSource rng, BlockPos pos) {
        var biome = level.getBiome(pos);

        if (biome.is(ModBiomeTags.GRASS_BLOCK_BONEMEAL_GROWS_BARLEY)) {
            BonemealSpreadingLogic.barley(level, rng, pos);
            return true;
        }

        if (biome.is(ModBiomeTags.GRASS_BLOCK_BONEMEAL_GROWS_CLOVERS)) {
            BonemealSpreadingLogic.grassFlowersAndClovers(level, rng, pos);
            return true;
        }

        return false;
    }




    public static boolean isSaltChunk(WorldGenLevel level, int x, int z) {
        var seed = level.getSeed();
        var rng = level.getRandom().fork();

        rng.setSeed(seed);
        rng.consumeCount(847);

        rng = rng.forkPositional().at(x * 16, 0, z * 16);

        return rng.nextInt(10) == 3;
    }

    public static boolean isInDamagingSalt(Mob mob) {
        return isInDamagingSalt(mob.level(), mob.getBoundingBox(), mob.onGround());
    }

    public static boolean isInDamagingSalt(Level level, AABB box, boolean checkGround) {
        var yPadding = checkGround ? 1 / 64d : 0d;

        for (var pos : BlockPos.betweenClosed(box.setMinY(box.minY - yPadding))) {
            if (level.getBlockState(pos).is(ModBlockTags.DAMAGES_SLIMES)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isNextToSalt(LevelReader level, BlockPos pos) {
        for (var dir : Direction.values()) {
            if (level.getBlockState(pos.relative(dir)).is(ModBlockTags.GROWS_SALT_MORE_LIKELY)) {
                return true;
            }
            if (level.getBlockState(pos.relative(dir)).is(ModBlockTags.GROWS_SALT)) {
                return true;
            }
        }

        return false;
    }

    public static void meltFromSalt(Level level, BlockPos pos, RandomSource rng) {
        var toVisit = new ArrayList<BlockPos>();
        var visted = new HashSet<BlockPos>();
        var iter = rng.nextInt(24, 32);

        toVisit.add(pos);

        for (int i = 0; i < iter; i ++) {
            if (toVisit.isEmpty())
                return;

            if (rng.nextInt(4) == 0) {
                pos = toVisit.get(rng.nextInt(toVisit.size()));
            } else {
                pos = toVisit.removeFirst();
            }

            var state = level.getBlockState(pos);
            if (state.is(Blocks.SNOW)) {
                Block.dropResources(state, level, pos);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
            } else if (state.is(Blocks.ICE)) {
                level.setBlock(pos, Blocks.WATER.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
            }

            visted.add(pos);

            for (var dir : Direction.allShuffled(rng)) {
                var relative = pos.relative(dir);
                if (!visted.contains(relative)) {
                    toVisit.add(relative);
                }
            }
        }
    }

    public static <A> Codec<Optional<A>> optionalCodec(Codec<A> baseCodec, String emptyName) {
        var emptyCodec = Codec.STRING.flatXmap(
                it -> it.equals(emptyName) ? DataResult.success(emptyName) : DataResult.error(() -> "Expected '" + emptyName + "'"),
                it -> DataResult.success(emptyName)
        );

        var eitherCodec = Codec.either(baseCodec, emptyCodec);

        return eitherCodec.xmap(
                it -> it.left(),
                it -> it.isPresent() ? Either.left(it.get()) : Either.right(emptyName)
        );
    }

    public static <A> Codec<Optional<A>> optionalCodec(Codec<A> baseCodec) {
        return optionalCodec(baseCodec, "none");
    }
}
