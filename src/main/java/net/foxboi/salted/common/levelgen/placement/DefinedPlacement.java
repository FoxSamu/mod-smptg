package net.foxboi.salted.common.levelgen.placement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.foxboi.salted.common.misc.data.DeferredHolder;
import net.foxboi.salted.common.misc.data.Definition;
import net.foxboi.salted.common.misc.data.DefinitionContext;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.*;

public class DefinedPlacement implements Definition<PlacedFeature> {
    private final DeferredHolder<ConfiguredFeature<?, ?>> feature;
    private final List<PlacementModifier> modifiers = new ArrayList<>();

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

    public DefinedPlacement modified(PlacementModifier modifier) {
        modifiers.add(modifier);
        return this;
    }

    public DefinedPlacement modified(Collection<? extends PlacementModifier> modifier) {
        modifiers.addAll(modifier);
        return this;
    }

    public DefinedPlacement modified(Modifier modifier) {
        modifier.modify(this);
        return this;
    }

    public DefinedPlacement inSaltChunk() {
        return modified(SaltChunkFilter.inSaltChunk());
    }

    public DefinedPlacement onAverageOnceEvery(int n) {
        return modified(RarityFilter.onAverageOnceEvery(n));
    }

    public DefinedPlacement count(int n) {
        return modified(CountPlacement.of(n));
    }

    public DefinedPlacement count(IntProvider n) {
        return modified(CountPlacement.of(n));
    }

    public DefinedPlacement countExtra(int n, float extraChance, int extra) {
        return modified(PlacementUtils.countExtra(n, extraChance, extra));
    }

    public DefinedPlacement spreadInChunk() {
        return modified(InSquarePlacement.spread());
    }

    public DefinedPlacement fillChunk(float probability) {
        return modified(RepeatInSquarePlacement.fill(probability));
    }

    public DefinedPlacement atHeight(HeightProvider height) {
        return modified(HeightRangePlacement.of(height));
    }

    public DefinedPlacement atHeight(int min, int max) {
        return atHeight(UniformHeight.of(VerticalAnchor.absolute(min), VerticalAnchor.absolute(max)));
    }

    public DefinedPlacement atSeaLevel() {
        return modified(SeaLevelHeightPlacement.atSeaLevel());
    }

    public DefinedPlacement atSeaLevel(int offset) {
        return modified(SeaLevelHeightPlacement.atSeaLevel(offset));
    }

    public DefinedPlacement onlyIf(BlockPredicate predicate) {
        return modified(BlockPredicateFilter.forPredicate(predicate));
    }

    public DefinedPlacement onHeightmap(Heightmap.Types type) {
        return modified(HeightmapPlacement.onHeightmap(type));
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
        return modified(BiomeFilter.biome());
    }

    public DefinedPlacement atMaxDepth(int depth) {
        return modified(SurfaceWaterDepthFilter.forMaxDepth(depth));
    }

    public DefinedPlacement spreadRandomly(IntProvider xz, IntProvider y) {
        return modified(RandomOffsetPlacement.of(xz, y));
    }

    public DefinedPlacement spreadRandomly(int xz, int y) {
        return modified(RandomOffsetPlacement.of(UniformInt.of(-xz, xz), UniformInt.of(-y, y)));
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

    @Override
    public PlacedFeature create(ResourceKey<PlacedFeature> key, DefinitionContext context) {
        return new PlacedFeature(feature.resolveOrThrow(context), List.copyOf(modifiers));
    }

    public interface Modifier {
        void modify(DefinedPlacement placement);
    }
}
