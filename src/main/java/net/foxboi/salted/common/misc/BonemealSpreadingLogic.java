package net.foxboi.salted.common.misc;

import java.util.Set;

import net.foxboi.salted.common.block.ModBlocks;
import net.foxboi.salted.common.levelgen.ModVegetationFeatures;
import net.foxboi.salted.common.levelgen.ModVegetationPlacements;
import net.foxboi.salted.common.levelgen.biome.ModBiomeTags;
import net.foxboi.salted.common.misc.cache.CacheKey;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public abstract class BonemealSpreadingLogic {
    public static final Set<Block> GRASS_VALID_BLOCKS = Set.of(
            Blocks.GRASS_BLOCK,
            ModBlocks.MOSSY_DIRT
    );

    protected final ServerLevel level;
    protected final RandomSource rng;
    protected final BlockPos pos;

    protected BonemealSpreadingLogic(ServerLevel level, RandomSource rng, BlockPos pos) {
        this.level = level;
        this.rng = rng;
        this.pos = pos;
    }

    protected abstract boolean isValidBlockToGrowOn(BlockState state);

    protected abstract void growAtPos(ServerLevel level, RandomSource rng, BlockPos pos, int iteration);

    protected Direction growDirection() {
        return Direction.UP;
    }

    public void apply() {
        var pos = this.pos.above();

        var mpos = pos.mutable();
        var belowMpos = pos.mutable();

        var offset = growDirection();

        iterations:
        for (var iteration = 0; iteration < 128; iteration++) {
            mpos.set(pos);

            // Move to nearby location
            for (int i = 0; i < iteration / 16; i++) {
                mpos.move(rng.nextInt(3) - 1, (rng.nextInt(3) - 1) * rng.nextInt(3) / 2, rng.nextInt(3) - 1);
                belowMpos.setWithOffset(mpos, offset.getOpposite());

                if (!isValidBlockToGrowOn(level.getBlockState(belowMpos))) {
                    continue iterations;
                }

                if (level.getBlockState(mpos).isCollisionShapeFullBlock(level, mpos)) {
                    continue iterations;
                }
            }

            growAtPos(level, rng, mpos, iteration);
        }
    }


    public static void grass(ServerLevel level, RandomSource rng, BlockPos pos) {
        new Grass(level, rng, pos).apply();
    }

    public static void grassAndFlowers(ServerLevel level, RandomSource rng, BlockPos pos) {
        new GrassAndFlowers(level, rng, pos).apply();
    }

    public static void grassFlowersAndClovers(ServerLevel level, RandomSource rng, BlockPos pos) {
        new GrassFlowersAndClovers(level, rng, pos).apply();
    }

    public static void grassAndMoss(ServerLevel level, RandomSource rng, BlockPos pos) {
        new GrassAndMoss(level, rng, pos).apply();
    }

    public static void barley(ServerLevel level, RandomSource rng, BlockPos pos) {
        new Barley(level, rng, pos).apply();
    }

    public static void underwater(ServerLevel level, RandomSource rng, BlockPos pos, Direction clickedFace) {
        new Underwater(level, rng, pos, clickedFace).apply();
    }

    public static void underwaterWithCattail(ServerLevel level, RandomSource rng, BlockPos pos, Direction clickedFace) {
        new UnderwaterWithCattail(level, rng, pos, clickedFace).apply();
    }

    private static class Grass extends BonemealSpreadingLogic {
        protected final Holder<ConfiguredFeature<?, ?>> grassFeature;

        public Grass(ServerLevel level, RandomSource rng, BlockPos pos) {
            super(level, rng, pos);

            grassFeature = level.registryAccess()
                    .get(VegetationFeatures.GRASS)
                    .orElse(null);
        }

        @Override
        protected boolean isValidBlockToGrowOn(BlockState state) {
            return GRASS_VALID_BLOCKS.contains(state.getBlock());
        }

        protected ConfiguredFeature<?, ?> getGrowFeature(ServerLevel level, RandomSource rng, BlockPos pos) {
            return grassFeature.value();
        }

        @Override
        protected void growAtPos(ServerLevel level, RandomSource rng, BlockPos pos, int iteration) {
            var localState = level.getBlockState(pos);

            // Randomly grow short grass into tall grass
            if (localState.is(Blocks.SHORT_GRASS) && rng.nextInt(10) == 0) {
                var shortGrassBonemealable = (BonemealableBlock) Blocks.SHORT_GRASS;

                if (shortGrassBonemealable.isValidBonemealTarget(level, pos, localState)) {
                    shortGrassBonemealable.performBonemeal(level, rng, pos, localState);
                }
            }

            // If no air at this position, we can't grow anything here
            if (!localState.isAir()) {
                return;
            }

            // Place plant feature
            var growFeature = getGrowFeature(level, rng, pos);
            if (growFeature != null) {
                growFeature.place(level, level.getChunkSource().getGenerator(), rng, pos);
            }
        }
    }

    private static class GrassAndFlowers extends Grass {
        public GrassAndFlowers(ServerLevel level, RandomSource rng, BlockPos pos) {
            super(level, rng, pos);
        }

        protected ConfiguredFeature<?, ?> getGrowFeature(ServerLevel level, RandomSource rng, BlockPos pos) {
            if (rng.nextInt(8) == 0) {
                var flowers = level.getBiome(pos).value().getGenerationSettings().getBoneMealFeatures();

                if (!flowers.isEmpty()) {
                    var flowerIndex = rng.nextInt(flowers.size());
                    return flowers.get(flowerIndex);
                }
            }

            return super.getGrowFeature(level, rng, pos);
        }
    }

    private static class GrassFlowersAndClovers extends GrassAndFlowers {
        private final Holder<ConfiguredFeature<?, ?>> cloverFeature;

        public GrassFlowersAndClovers(ServerLevel level, RandomSource rng, BlockPos pos) {
            super(level, rng, pos);

            cloverFeature = level.registryAccess()
                    .get(ModVegetationFeatures.CLOVERS)
                    .orElse(null);
        }

        protected ConfiguredFeature<?, ?> getGrowFeature(ServerLevel level, RandomSource rng, BlockPos pos) {
            if (rng.nextInt(4) == 0) {
                return cloverFeature.value();
            }

            return super.getGrowFeature(level, rng, pos);
        }
    }

    private static class GrassAndMoss extends Grass {
        private final Holder<ConfiguredFeature<?, ?>> mossFeature;

        public GrassAndMoss(ServerLevel level, RandomSource rng, BlockPos pos) {
            super(level, rng, pos);

            mossFeature = level.registryAccess()
                    .get(ModVegetationFeatures.MOSS_CARPET)
                    .orElse(null);
        }


        protected ConfiguredFeature<?, ?> getGrowFeature(ServerLevel level, RandomSource rng, BlockPos pos) {
            if (rng.nextInt(3) == 0) {
                return mossFeature.value();
            }

            return super.getGrowFeature(level, rng, pos);
        }
    }

    private static class Barley extends Grass {
        private final Holder<ConfiguredFeature<?, ?>> barleyFeature;

        public Barley(ServerLevel level, RandomSource rng, BlockPos pos) {
            super(level, rng, pos);

            barleyFeature = level.registryAccess()
                    .get(ModVegetationFeatures.SMALL_BARLEY)
                    .orElse(null);
        }


        protected ConfiguredFeature<?, ?> getGrowFeature(ServerLevel level, RandomSource rng, BlockPos pos) {
            if (rng.nextInt(5) != 0) {
                return barleyFeature.value();
            }

            return super.getGrowFeature(level, rng, pos);
        }

        @Override
        protected void growAtPos(ServerLevel level, RandomSource rng, BlockPos pos, int iteration) {
            var localState = level.getBlockState(pos);

            if (localState.is(ModBlocks.BARLEY) && rng.nextInt(10) == 0) {
                var bonemealable = (BonemealableBlock) ModBlocks.BARLEY;

                if (bonemealable.isValidBonemealTarget(level, pos, localState)) {
                    bonemealable.performBonemeal(level, rng, pos, localState);
                }

                return;
            }

            super.growAtPos(level, rng, pos, iteration);
        }
    }

    private static class Underwater extends BonemealSpreadingLogic {
        protected final Direction clickedFace;

        public Underwater(ServerLevel level, RandomSource rng, BlockPos pos, Direction clickedFace) {
            super(level, rng, pos);
            this.clickedFace = clickedFace;
        }

        @Override
        protected Direction growDirection() {
            return clickedFace == null ? Direction.UP : clickedFace;
        }

        @Override
        protected boolean isValidBlockToGrowOn(BlockState state) {
            return true;
        }

        protected BlockState getGrowBlock(ServerLevel level, RandomSource rng, BlockPos pos, int iteration) {
            var placeState = Blocks.SEAGRASS.defaultBlockState();

            // If in a coral biome, maybe grow coral
            if (level.getBiome(pos).is(BiomeTags.PRODUCES_CORALS_FROM_BONEMEAL)) {
                if (iteration == 0 && clickedFace != null && clickedFace.getAxis().isHorizontal()) {
                    placeState = BuiltInRegistries.BLOCK
                            .getRandomElementOf(BlockTags.WALL_CORALS, level.getRandom())
                            .map(it -> it.value().defaultBlockState())
                            .orElse(placeState);

                    if (placeState.hasProperty(BaseCoralWallFanBlock.FACING)) {
                        placeState = placeState.setValue(BaseCoralWallFanBlock.FACING, clickedFace);
                    }
                } else if (rng.nextInt(4) == 0) {
                    placeState = BuiltInRegistries.BLOCK
                            .getRandomElementOf(BlockTags.UNDERWATER_BONEMEALS, level.getRandom())
                            .map(it -> it.value().defaultBlockState())
                            .orElse(placeState);
                }
            }

            // Try orient wall coral to an attachable block
            if (placeState.is(BlockTags.WALL_CORALS, it -> it.hasProperty(BaseCoralWallFanBlock.FACING))) {
                for (var attempt = 0; attempt < 4; attempt++) {
                    if (placeState.canSurvive(level, pos)) {
                        break;
                    }

                    placeState = placeState.setValue(BaseCoralWallFanBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(rng));
                }
            }

            return placeState;
        }

        protected boolean tryGrowAt(ServerLevel level, BlockPos pos, BlockState localState, BlockState growState) {
            if (localState.is(Blocks.WATER) && level.getFluidState(pos).getAmount() == 8 && growState.canSurvive(level, pos)) {
                level.setBlock(pos, growState, 3);
                return true;
            }

            return false;
        }

        @Override
        protected void growAtPos(ServerLevel level, RandomSource rng, BlockPos pos, int iteration) {
            var localState = level.getBlockState(pos);

            // Randomly grow seagrass to a tall seagrass
            if (localState.is(Blocks.SEAGRASS) && rng.nextInt(10) == 0) {
                var bonemealable = (BonemealableBlock) Blocks.SEAGRASS;

                if (bonemealable.isValidBonemealTarget(level, pos, localState)) {
                    bonemealable.performBonemeal(level, rng, pos, localState);
                }
            }

            var growState = getGrowBlock(level, rng, pos, iteration);

            // If no full block of water, we can't place here
            tryGrowAt(level, pos, localState, growState);
        }
    }

    private static class UnderwaterWithCattail extends Underwater {
        public UnderwaterWithCattail(ServerLevel level, RandomSource rng, BlockPos pos, Direction clickedFace) {
            super(level, rng, pos, clickedFace);
        }

        protected BlockState getCattailState(ServerLevel level, RandomSource rng, BlockPos pos) {
            var localState = level.getBlockState(pos);
            if (localState.isAir()) {
                return ModBlocks.CATTAIL.defaultBlockState();
            }

            if (localState.is(Blocks.WATER) && level.getFluidState(pos).getAmount() == 8) {
                var aboveState = level.getBlockState(pos.above());
                if (aboveState.isAir()) {
                    return ModBlocks.TALL_CATTAIL.defaultBlockState();
                }
            }

            return null;
        }

        @Override
        protected BlockState getGrowBlock(ServerLevel level, RandomSource rng, BlockPos pos, int iteration) {
            if (rng.nextBoolean()) {
                var cattail = getCattailState(level, rng, pos);

                if (cattail != null) {
                    return cattail;
                }
            }

            return super.getGrowBlock(level, rng, pos, iteration);
        }

        @Override
        protected boolean tryGrowAt(ServerLevel level, BlockPos pos, BlockState localState, BlockState growState) {
            if (growState.is(ModBlocks.CATTAIL)) {
                if (localState.isAir() && growState.canSurvive(level, pos)) {
                    level.setBlock(pos, growState, 3);
                    return true;
                }

                return false;
            }

            if (growState.is(ModBlocks.TALL_CATTAIL)) {
                if (growState.canSurvive(level, pos)) {
                    // We already checked if we can grow tall cattail when determining that we want to grow tall cattail,
                    // so no need to do that again here.
                    DoublePlantBlock.placeAt(level, growState, pos, 3);
                    return true;
                }
            }

            return super.tryGrowAt(level, pos, localState, growState);
        }

        @Override
        protected void growAtPos(ServerLevel level, RandomSource rng, BlockPos pos, int iteration) {
            var localState = level.getBlockState(pos);

            // Randomly grow seagrass to a tall seagrass
            if (localState.is(ModBlocks.CATTAIL) && rng.nextInt(10) == 0) {
                var bonemealable = (BonemealableBlock) ModBlocks.CATTAIL;

                if (bonemealable.isValidBonemealTarget(level, pos, localState)) {
                    bonemealable.performBonemeal(level, rng, pos, localState);
                }
            }

            super.growAtPos(level, rng, pos, iteration);
        }
    }
}
