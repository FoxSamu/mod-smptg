package net.foxboi.salted.common.levelgen.placement;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import net.foxboi.salted.common.misc.reg.DeferredHolder;
import net.foxboi.salted.common.misc.reg.Definition;
import net.foxboi.salted.common.misc.reg.DefinitionContext;

import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.*;

public class DefinedPlacement implements Definition<PlacedFeature> {
    private final DeferredHolder<ConfiguredFeature<?, ?>> feature;
    private final List<Function<DefinitionContext, PlacementModifier>> modifiers = new ArrayList<>();

    public DefinedPlacement(DeferredHolder<ConfiguredFeature<?, ?>> feature) {
        this.feature = feature;
    }

    public static DefinedPlacement place(Holder<ConfiguredFeature<?, ?>> feature) {
        return new DefinedPlacement(DeferredHolder.direct(feature));
    }

    public static DefinedPlacement place(ConfiguredFeature<?, ?> feature) {
        return new DefinedPlacement(DeferredHolder.direct(feature));
    }

    public static DefinedPlacement place(ResourceKey<ConfiguredFeature<?, ?>> feature) {
        return new DefinedPlacement(DeferredHolder.reference(feature));
    }

    public DefinedPlacement with(PlacementModifier modifier) {
        modifiers.add(_ -> modifier);
        return this;
    }

    public DefinedPlacement with(Function<DefinitionContext, PlacementModifier> modifier) {
        modifiers.add(modifier);
        return this;
    }

    public DefinedPlacement modified(Modifier modifier) {
        modifier.modify(this);
        return this;
    }

    public DefinedPlacement onAverageOnceEvery(int n) {
        return with(RarityFilter.onAverageOnceEvery(n));
    }

    public DefinedPlacement count(int n) {
        return with(CountPlacement.of(n));
    }

    public DefinedPlacement count(IntProvider n) {
        return with(CountPlacement.of(n));
    }

    public DefinedPlacement countExtra(int n, float extraChance, int extra) {
        return with(PlacementUtils.countExtra(n, extraChance, extra));
    }

    public DefinedPlacement spreadInChunk() {
        return with(InSquarePlacement.spread());
    }

    public DefinedPlacement fillChunk(float probability) {
        return with(RepeatInSquarePlacement.fill(probability));
    }

    public DefinedPlacement atHeight(HeightProvider height) {
        return with(HeightRangePlacement.of(height));
    }

    public DefinedPlacement atHeight(int min, int max) {
        return atHeight(UniformHeight.of(VerticalAnchor.absolute(min), VerticalAnchor.absolute(max)));
    }

    public DefinedPlacement scan(Direction dir, BlockPredicate target, int maxSteps) {
        return with(EnvironmentScanPlacement.scanningFor(dir, target, maxSteps));
    }

    public DefinedPlacement atSeaLevel() {
        return with(SeaLevelHeightPlacement.atSeaLevel());
    }

    public DefinedPlacement atSeaLevel(int offset) {
        return with(SeaLevelHeightPlacement.atSeaLevel(offset));
    }

    public DefinedPlacement onlyIf(BlockPredicate predicate) {
        return with(BlockPredicateFilter.forPredicate(predicate));
    }

    public DefinedPlacement onHeightmap(Heightmap.Types type) {
        return with(HeightmapPlacement.onHeightmap(type));
    }

    public DefinedPlacement onOceanFloorWg() {
        return onHeightmap(Heightmap.Types.OCEAN_FLOOR_WG);
    }

    public DefinedPlacement onWorldSurfaceWg() {
        return onHeightmap(Heightmap.Types.WORLD_SURFACE_WG);
    }

    public DefinedPlacement onOceanFloor() {
        return onHeightmap(Heightmap.Types.OCEAN_FLOOR);
    }

    public DefinedPlacement onMotionBlocking() {
        return onHeightmap(Heightmap.Types.MOTION_BLOCKING);
    }

    public DefinedPlacement onMotionBlockingNoLeaves() {
        return onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES);
    }

    public DefinedPlacement inBiome() {
        return with(BiomeFilter.biome());
    }

    public DefinedPlacement atMaxDepth(int depth) {
        return with(SurfaceWaterDepthFilter.forMaxDepth(depth));
    }

    public DefinedPlacement spreadRandomly(IntProvider xz, IntProvider y) {
        return with(RandomOffsetPlacement.of(xz, y));
    }

    public DefinedPlacement spreadRandomly(int xz, int y) {
        return with(RandomOffsetPlacement.of(UniformInt.of(-xz, xz), UniformInt.of(-y, y)));
    }

    public DefinedPlacement randomPatch(BlockPredicate predicate, int attempts, int xzSpread, int ySpread) {
        return count(attempts)
                .spreadRandomly(xzSpread, ySpread)
                .onlyIf(predicate);
    }

    public DefinedPlacement randomPatch(BlockPredicate predicate, int attempts, int xzSpread) {
        return randomPatch(predicate, attempts, xzSpread, 3);
    }

    public DefinedPlacement randomPatch(BlockPredicate predicate, int attempts) {
        return randomPatch(predicate, attempts, 7, 3);
    }

    public DefinedPlacement onlyInBiomes(Function<DefinitionContext, HolderSet<Biome>> biomes) {
        return with(ctx -> BiomeListFilter.onlyInBiomes(biomes.apply(ctx)));
    }

    public DefinedPlacement notInBiomes(Function<DefinitionContext, HolderSet<Biome>> biomes) {
        return with(ctx -> BiomeListFilter.notInBiomes(biomes.apply(ctx)));
    }

    public final DefinedPlacement onlyInBiomes(TagKey<Biome> biomes) {
        return onlyInBiomes(ctx -> ctx.getOrThrow(biomes));
    }

    public final DefinedPlacement onlyInBiomes(HolderSet<Biome> biomes) {
        return onlyInBiomes(_ -> biomes);
    }

    @SafeVarargs
    public final DefinedPlacement onlyInBiomes(ResourceKey<Biome>... biomes) {
        return onlyInBiomes(ctx -> HolderSet.direct(Stream.of(biomes).map(ctx::getOrThrow).toList()));
    }

    @SafeVarargs
    public final DefinedPlacement onlyInBiomes(Holder<Biome>... biomes) {
        return onlyInBiomes(HolderSet.direct(biomes));
    }

    public final DefinedPlacement notInBiomes(TagKey<Biome> biomes) {
        return notInBiomes(ctx -> ctx.getOrThrow(biomes));
    }

    public final DefinedPlacement notInBiomes(HolderSet<Biome> biomes) {
        return notInBiomes(_ -> biomes);
    }

    @SafeVarargs
    public final DefinedPlacement notInBiomes(ResourceKey<Biome>... biomes) {
        return notInBiomes(ctx -> HolderSet.direct(Stream.of(biomes).map(ctx::getOrThrow).toList()));
    }

    @SafeVarargs
    public final DefinedPlacement notInBiomes(Holder<Biome>... biomes) {
        return notInBiomes(HolderSet.direct(biomes));
    }

    @Override
    public PlacedFeature create(ResourceKey<PlacedFeature> key, DefinitionContext context) {
        return new PlacedFeature(feature.resolveOrThrow(context), modifiers.stream().map(fn -> fn.apply(context)).toList());
    }

    public interface Modifier {
        void modify(DefinedPlacement placement);
    }
}
