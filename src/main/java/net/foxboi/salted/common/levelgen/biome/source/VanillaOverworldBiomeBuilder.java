package net.foxboi.salted.common.levelgen.biome.source;

import com.mojang.datafixers.util.Pair;

import java.util.List;
import java.util.function.Consumer;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseRouterData;

import static net.minecraft.world.level.biome.Climate.Parameter.*;

@SuppressWarnings("unchecked")
public final class VanillaOverworldBiomeBuilder {
    private static final float VALLEY_SIZE = 0.05F;
    private static final float LOW_START = 0.26666668F;
    public static final float HIGH_START = 0.4F;
    private static final float HIGH_END = 0.93333334F;
    private static final float PEAK_SIZE = 0.1F;
    public static final float PEAK_START = 0.56666666F;
    private static final float PEAK_END = 0.7666667F;
    public static final float NEAR_INLAND_START = -0.11F;
    public static final float MID_INLAND_START = 0.03F;
    public static final float FAR_INLAND_START = 0.3F;
    public static final float EROSION_INDEX_1_START = -0.78F;
    public static final float EROSION_INDEX_2_START = -0.375F;
    private static final float EROSION_DEEP_DARK_DRYNESS_THRESHOLD = -0.225F;
    private static final float DEPTH_DEEP_DARK_DRYNESS_THRESHOLD = 0.9F;
    private final Climate.Parameter fullRange = span(-1.0F, 1.0F);
    private final Climate.Parameter[] temperatures = new Climate.Parameter[]{
            span(-1.0F, -0.45F),
            span(-0.45F, -0.15F),
            span(-0.15F, 0.2F),
            span(0.2F, 0.55F),
            span(0.55F, 1.0F)
    };
    private final Climate.Parameter[] humidities = new Climate.Parameter[]{
            span(-1.0F, -0.35F),
            span(-0.35F, -0.1F),
            span(-0.1F, 0.1F),
            span(0.1F, 0.3F),
            span(0.3F, 1.0F)
    };
    private final Climate.Parameter[] erosions = new Climate.Parameter[]{
            span(-1.0F, -0.78F),
            span(-0.78F, -0.375F),
            span(-0.375F, -0.2225F),
            span(-0.2225F, 0.05F),
            span(0.05F, 0.45F),
            span(0.45F, 0.55F),
            span(0.55F, 1.0F)
    };

    private final Climate.Parameter frozen = temperatures[0];
    private final Climate.Parameter unfrozen = span(temperatures[1], temperatures[4]);

    private final Climate.Parameter mushroomFieldsContinentalness = span(-1.2F, -1.05F);
    private final Climate.Parameter deepOceanContinentalness = span(-1.05F, -0.455F);
    private final Climate.Parameter oceanContinentalness = span(-0.455F, -0.19F);
    private final Climate.Parameter coastContinentalness = span(-0.19F, -0.11F);
    private final Climate.Parameter inlandContinentalness = span(-0.11F, 0.55F);
    private final Climate.Parameter nearInlandContinentalness = span(-0.11F, 0.03F);
    private final Climate.Parameter midInlandContinentalness = span(0.03F, 0.3F);
    private final Climate.Parameter farInlandContinentalness = span(0.3F, 1.0F);

    private final ResourceKey<Biome>[][] oceans = new ResourceKey[][]{
            {Biomes.DEEP_FROZEN_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.WARM_OCEAN},
            {Biomes.FROZEN_OCEAN, Biomes.COLD_OCEAN, Biomes.OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN}
    };

    private final ResourceKey<Biome>[][] middle = new ResourceKey[][]{
            {Biomes.SNOWY_PLAINS, Biomes.SNOWY_PLAINS, Biomes.SNOWY_PLAINS, Biomes.SNOWY_TAIGA, Biomes.TAIGA},
            {Biomes.PLAINS, Biomes.PLAINS, Biomes.FOREST, Biomes.TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA},
            {Biomes.FLOWER_FOREST, Biomes.PLAINS, Biomes.FOREST, Biomes.BIRCH_FOREST, Biomes.DARK_FOREST},
            {Biomes.SAVANNA, Biomes.SAVANNA, Biomes.FOREST, Biomes.JUNGLE, Biomes.JUNGLE},
            {Biomes.DESERT, Biomes.DESERT, Biomes.DESERT, Biomes.DESERT, Biomes.DESERT}
    };

    private final ResourceKey<Biome>[][] middleVariant = new ResourceKey[][]{
            {Biomes.ICE_SPIKES, null, Biomes.SNOWY_TAIGA, null, null},
            {null, null, null, null, Biomes.OLD_GROWTH_PINE_TAIGA},
            {Biomes.SUNFLOWER_PLAINS, null, null, Biomes.OLD_GROWTH_BIRCH_FOREST, null},
            {null, null, Biomes.PLAINS, Biomes.SPARSE_JUNGLE, Biomes.BAMBOO_JUNGLE},
            {null, null, null, null, null}
    };

    private final ResourceKey<Biome>[][] plateau = new ResourceKey[][]{
            {Biomes.SNOWY_PLAINS, Biomes.SNOWY_PLAINS, Biomes.SNOWY_PLAINS, Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA},
            {Biomes.MEADOW, Biomes.MEADOW, Biomes.FOREST, Biomes.TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA},
            {Biomes.MEADOW, Biomes.MEADOW, Biomes.MEADOW, Biomes.MEADOW, Biomes.PALE_GARDEN},
            {Biomes.SAVANNA_PLATEAU, Biomes.SAVANNA_PLATEAU, Biomes.FOREST, Biomes.FOREST, Biomes.JUNGLE},
            {Biomes.BADLANDS, Biomes.BADLANDS, Biomes.BADLANDS, Biomes.WOODED_BADLANDS, Biomes.WOODED_BADLANDS}
    };

    private final ResourceKey<Biome>[][] plateauVariant = new ResourceKey[][]{
            {Biomes.ICE_SPIKES, null, null, null, null},
            {Biomes.CHERRY_GROVE, null, Biomes.MEADOW, Biomes.MEADOW, Biomes.OLD_GROWTH_PINE_TAIGA},
            {Biomes.CHERRY_GROVE, Biomes.CHERRY_GROVE, Biomes.FOREST, Biomes.BIRCH_FOREST, null},
            {null, null, null, null, null},
            {Biomes.ERODED_BADLANDS, Biomes.ERODED_BADLANDS, null, null, null}
    };

    private final ResourceKey<Biome>[][] shattered = new ResourceKey[][]{
            {Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_FOREST},
            {Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_FOREST},
            {Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_FOREST},
            {null, null, null, null, null},
            {null, null, null, null, null}
    };

    public List<Climate.ParameterPoint> spawnTarget() {
        Climate.Parameter parameter = point(0);
        float f = 0.16F;
        return List.of(
                new Climate.ParameterPoint(
                        fullRange,
                        fullRange,
                        span(inlandContinentalness, fullRange),
                        fullRange,
                        parameter,
                        span(-1.0F, -0.16F),
                        0L
                ),
                new Climate.ParameterPoint(
                        fullRange,
                        fullRange,
                        span(inlandContinentalness, fullRange),
                        fullRange,
                        parameter,
                        span(0.16F, 1.0F),
                        0L
                )
        );
    }

    public void addBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> output) {
        addOffCoastBiomes(output);
        addInlandBiomes(output);
        addUndergroundBiomes(output);
    }

    private void addOffCoastBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> output) {
        addSurfaceBiome(output, fullRange, fullRange, mushroomFieldsContinentalness, fullRange, fullRange, 0, Biomes.MUSHROOM_FIELDS);

        for (var ti = 0; ti < temperatures.length; ti++) {
            var temp = temperatures[ti];
            addSurfaceBiome(output, temp, fullRange, deepOceanContinentalness, fullRange, fullRange, 0, oceans[0][ti]);
            addSurfaceBiome(output, temp, fullRange, oceanContinentalness, fullRange, fullRange, 0, oceans[1][ti]);
        }
    }

    private void addInlandBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> output) {
        addMidSlice(output, span(-1.0F, -0.93333334F));
        addHighSlice(output, span(-0.93333334F, -0.7666667F));
        addPeaks(output, span(-0.7666667F, -0.56666666F));
        addHighSlice(output, span(-0.56666666F, -0.4F));
        addMidSlice(output, span(-0.4F, -0.26666668F));
        addLowSlice(output, span(-0.26666668F, -0.05F));
        addValleys(output, span(-0.05F, 0.05F));
        addLowSlice(output, span(0.05F, 0.26666668F));
        addMidSlice(output, span(0.26666668F, 0.4F));
        addHighSlice(output, span(0.4F, 0.56666666F));
        addPeaks(output, span(0.56666666F, 0.7666667F));
        addHighSlice(output, span(0.7666667F, 0.93333334F));
        addMidSlice(output, span(0.93333334F, 1.0F));
    }

    private void addPeaks(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> output, Climate.Parameter weirdness) {
        for (int tmpI = 0; tmpI < temperatures.length; tmpI++) {
            var tmp = temperatures[tmpI];

            for (int humI = 0; humI < humidities.length; humI++) {
                var hum = humidities[humI];

                var middle = pickMiddleBiome(tmpI, humI, weirdness);
                var middleOrBadlands = pickMiddleBiomeOrBadlandsIfHot(tmpI, humI, weirdness);
                var middleOrBadlandsOrSlope = pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(tmpI, humI, weirdness);
                var plateau = pickPlateauBiome(tmpI, humI, weirdness);
                var shattered = pickShatteredBiome(tmpI, humI, weirdness);
                var windsweptSavanna = maybePickWindsweptSavannaBiome(tmpI, humI, weirdness, shattered);
                var peak = pickPeakBiome(tmpI, humI, weirdness);

                addSurfaceBiome(output, tmp, hum, span(coastContinentalness, farInlandContinentalness), erosions[0], weirdness, 0, peak);
                addSurfaceBiome(output, tmp, hum, span(coastContinentalness, nearInlandContinentalness), erosions[1], weirdness, 0, middleOrBadlandsOrSlope);
                addSurfaceBiome(output, tmp, hum, span(midInlandContinentalness, farInlandContinentalness), erosions[1], weirdness, 0, peak);
                addSurfaceBiome(output, tmp, hum, span(coastContinentalness, nearInlandContinentalness), span(erosions[2], erosions[3]), weirdness, 0, middle);
                addSurfaceBiome(output, tmp, hum, span(midInlandContinentalness, farInlandContinentalness), erosions[2], weirdness, 0, plateau);
                addSurfaceBiome(output, tmp, hum, midInlandContinentalness, erosions[3], weirdness, 0, middleOrBadlands);
                addSurfaceBiome(output, tmp, hum, farInlandContinentalness, erosions[3], weirdness, 0, plateau);
                addSurfaceBiome(output, tmp, hum, span(coastContinentalness, farInlandContinentalness), erosions[4], weirdness, 0, middle);
                addSurfaceBiome(output, tmp, hum, span(coastContinentalness, nearInlandContinentalness), erosions[5], weirdness, 0, windsweptSavanna);
                addSurfaceBiome(output, tmp, hum, span(midInlandContinentalness, farInlandContinentalness), erosions[5], weirdness, 0, shattered);
                addSurfaceBiome(output, tmp, hum, span(coastContinentalness, farInlandContinentalness), erosions[6], weirdness, 0, middle);
            }
        }
    }

    private void addHighSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> output, Climate.Parameter weirdness) {
        for (int tmpI = 0; tmpI < temperatures.length; tmpI++) {
            var tmp = temperatures[tmpI];

            for (int humI = 0; humI < humidities.length; humI++) {
                var hum = humidities[humI];

                var middle = pickMiddleBiome(tmpI, humI, weirdness);
                var middleOrBadlands = pickMiddleBiomeOrBadlandsIfHot(tmpI, humI, weirdness);
                var middleOrBadlandsOrSlope = pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(tmpI, humI, weirdness);
                var plateau = pickPlateauBiome(tmpI, humI, weirdness);
                var shattered = pickShatteredBiome(tmpI, humI, weirdness);
                var windsweptSavanna = maybePickWindsweptSavannaBiome(tmpI, humI, weirdness, middle);
                var slopeBiome = pickSlopeBiome(tmpI, humI, weirdness);
                var peakBiome = pickPeakBiome(tmpI, humI, weirdness);

                addSurfaceBiome(output, tmp, hum, coastContinentalness, span(erosions[0], erosions[1]), weirdness, 0, middle);
                addSurfaceBiome(output, tmp, hum, nearInlandContinentalness, erosions[0], weirdness, 0, slopeBiome);
                addSurfaceBiome(output, tmp, hum, span(midInlandContinentalness, farInlandContinentalness), erosions[0], weirdness, 0, peakBiome);
                addSurfaceBiome(output, tmp, hum, nearInlandContinentalness, erosions[1], weirdness, 0, middleOrBadlandsOrSlope);
                addSurfaceBiome(output, tmp, hum, span(midInlandContinentalness, farInlandContinentalness), erosions[1], weirdness, 0, slopeBiome);
                addSurfaceBiome(output, tmp, hum, span(coastContinentalness, nearInlandContinentalness), span(erosions[2], erosions[3]), weirdness, 0, middle);
                addSurfaceBiome(output, tmp, hum, span(midInlandContinentalness, farInlandContinentalness), erosions[2], weirdness, 0, plateau);
                addSurfaceBiome(output, tmp, hum, midInlandContinentalness, erosions[3], weirdness, 0, middleOrBadlands);
                addSurfaceBiome(output, tmp, hum, farInlandContinentalness, erosions[3], weirdness, 0, plateau);
                addSurfaceBiome(output, tmp, hum, span(coastContinentalness, farInlandContinentalness), erosions[4], weirdness, 0, middle);
                addSurfaceBiome(output, tmp, hum, span(coastContinentalness, nearInlandContinentalness), erosions[5], weirdness, 0, windsweptSavanna);
                addSurfaceBiome(output, tmp, hum, span(midInlandContinentalness, farInlandContinentalness), erosions[5], weirdness, 0, shattered);
                addSurfaceBiome(output, tmp, hum, span(coastContinentalness, farInlandContinentalness), erosions[6], weirdness, 0, middle);
            }
        }
    }

    private void addMidSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> output, Climate.Parameter weirdness) {
        addSurfaceBiome(output, fullRange, fullRange, coastContinentalness, span(erosions[0], erosions[2]), weirdness, 0, Biomes.STONY_SHORE);
        addSurfaceBiome(output, span(temperatures[1], temperatures[2]), fullRange, span(nearInlandContinentalness, farInlandContinentalness), erosions[6], weirdness, 0, Biomes.SWAMP);
        addSurfaceBiome(output, span(temperatures[3], temperatures[4]), fullRange, span(nearInlandContinentalness, farInlandContinentalness), erosions[6], weirdness, 0, Biomes.MANGROVE_SWAMP);

        for (var tmpI = 0; tmpI < temperatures.length; tmpI++) {
            var tmp = temperatures[tmpI];

            for (int humI = 0; humI < humidities.length; humI++) {
                var hum = humidities[humI];

                var middle = pickMiddleBiome(tmpI, humI, weirdness);
                var middleOrBadlands = pickMiddleBiomeOrBadlandsIfHot(tmpI, humI, weirdness);
                var middleOrBadlandsOrSlope = pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(tmpI, humI, weirdness);
                var shattered = pickShatteredBiome(tmpI, humI, weirdness);
                var plateau = pickPlateauBiome(tmpI, humI, weirdness);
                var beach = pickBeachBiome(tmpI, humI);
                var windsweptSavanna = maybePickWindsweptSavannaBiome(tmpI, humI, weirdness, middle);
                var shatteredCoast = pickShatteredCoastBiome(tmpI, humI, weirdness);
                var slope = pickSlopeBiome(tmpI, humI, weirdness);

                addSurfaceBiome(output, tmp, hum, span(nearInlandContinentalness, farInlandContinentalness), erosions[0], weirdness, 0, slope);
                addSurfaceBiome(output, tmp, hum, span(nearInlandContinentalness, midInlandContinentalness), erosions[1], weirdness, 0, middleOrBadlandsOrSlope);
                addSurfaceBiome(output, tmp, hum, farInlandContinentalness, erosions[1], weirdness, 0, tmpI == 0 ? slope : plateau);
                addSurfaceBiome(output, tmp, hum, nearInlandContinentalness, erosions[2], weirdness, 0, middle);
                addSurfaceBiome(output, tmp, hum, midInlandContinentalness, erosions[2], weirdness, 0, middleOrBadlands);
                addSurfaceBiome(output, tmp, hum, farInlandContinentalness, erosions[2], weirdness, 0, plateau);
                addSurfaceBiome(output, tmp, hum, span(coastContinentalness, nearInlandContinentalness), erosions[3], weirdness, 0, middle);
                addSurfaceBiome(output, tmp, hum, span(midInlandContinentalness, farInlandContinentalness), erosions[3], weirdness, 0, middleOrBadlands);

                if (weirdness.max() < 0L) {
                    addSurfaceBiome(output, tmp, hum, coastContinentalness, erosions[4], weirdness, 0, beach);
                    addSurfaceBiome(output, tmp, hum, span(nearInlandContinentalness, farInlandContinentalness), erosions[4], weirdness, 0, middle);
                } else {
                    addSurfaceBiome(output, tmp, hum, span(coastContinentalness, farInlandContinentalness), erosions[4], weirdness, 0, middle);
                }

                addSurfaceBiome(output, tmp, hum, coastContinentalness, erosions[5], weirdness, 0, shatteredCoast);
                addSurfaceBiome(output, tmp, hum, nearInlandContinentalness, erosions[5], weirdness, 0, windsweptSavanna);
                addSurfaceBiome(output, tmp, hum, span(midInlandContinentalness, farInlandContinentalness), erosions[5], weirdness, 0, shattered);

                if (weirdness.max() < 0L) {
                    addSurfaceBiome(output, tmp, hum, coastContinentalness, erosions[6], weirdness, 0, beach);
                } else {
                    addSurfaceBiome(output, tmp, hum, coastContinentalness, erosions[6], weirdness, 0, middle);
                }

                if (tmpI == 0) {
                    addSurfaceBiome(output, tmp, hum, span(nearInlandContinentalness, farInlandContinentalness), erosions[6], weirdness, 0, middle);
                }
            }
        }
    }

    private void addLowSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> output, Climate.Parameter weirdness) {
        addSurfaceBiome(output, fullRange, fullRange, coastContinentalness, span(erosions[0], erosions[2]), weirdness, 0, Biomes.STONY_SHORE);
        addSurfaceBiome(output, span(temperatures[1], temperatures[2]), fullRange, span(nearInlandContinentalness, farInlandContinentalness), erosions[6], weirdness, 0, Biomes.SWAMP);
        addSurfaceBiome(output, span(temperatures[3], temperatures[4]), fullRange, span(nearInlandContinentalness, farInlandContinentalness), erosions[6], weirdness, 0, Biomes.MANGROVE_SWAMP);

        for (int tmpI = 0; tmpI < temperatures.length; tmpI++) {
            var tmp = temperatures[tmpI];

            for (int humI = 0; humI < humidities.length; humI++) {
                var hum = humidities[humI];

                var middle = pickMiddleBiome(tmpI, humI, weirdness);
                var middleOrBadlands = pickMiddleBiomeOrBadlandsIfHot(tmpI, humI, weirdness);
                var middleOrBadlandsOrSlope = pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(tmpI, humI, weirdness);
                var beach = pickBeachBiome(tmpI, humI);
                var windsweptSavanna = maybePickWindsweptSavannaBiome(tmpI, humI, weirdness, middle);
                var shatteredCoast = pickShatteredCoastBiome(tmpI, humI, weirdness);

                addSurfaceBiome(output, tmp, hum, nearInlandContinentalness, span(erosions[0], erosions[1]), weirdness, 0, middleOrBadlands);
                addSurfaceBiome(output, tmp, hum, span(midInlandContinentalness, farInlandContinentalness), span(erosions[0], erosions[1]), weirdness, 0, middleOrBadlandsOrSlope);
                addSurfaceBiome(output, tmp, hum, nearInlandContinentalness, span(erosions[2], erosions[3]), weirdness, 0, middle);
                addSurfaceBiome(output, tmp, hum, span(midInlandContinentalness, farInlandContinentalness), span(erosions[2], erosions[3]), weirdness, 0, middleOrBadlands);
                addSurfaceBiome(output, tmp, hum, coastContinentalness, span(erosions[3], erosions[4]), weirdness, 0, beach);
                addSurfaceBiome(output, tmp, hum, span(nearInlandContinentalness, farInlandContinentalness), erosions[4], weirdness, 0, middle);
                addSurfaceBiome(output, tmp, hum, coastContinentalness, erosions[5], weirdness, 0, shatteredCoast);
                addSurfaceBiome(output, tmp, hum, nearInlandContinentalness, erosions[5], weirdness, 0, windsweptSavanna);
                addSurfaceBiome(output, tmp, hum, span(midInlandContinentalness, farInlandContinentalness), erosions[5], weirdness, 0, middle);
                addSurfaceBiome(output, tmp, hum, coastContinentalness, erosions[6], weirdness, 0, beach);
                if (tmpI == 0) {
                    addSurfaceBiome(output, tmp, hum, span(nearInlandContinentalness, farInlandContinentalness), erosions[6], weirdness, 0, middle);
                }
            }
        }
    }

    private void addValleys(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> output, Climate.Parameter weirdness) {
        addSurfaceBiome(output, frozen, fullRange, coastContinentalness, span(erosions[0], erosions[1]), weirdness, 0, weirdness.max() < 0L ? Biomes.STONY_SHORE : Biomes.FROZEN_RIVER);
        addSurfaceBiome(output, unfrozen, fullRange, coastContinentalness, span(erosions[0], erosions[1]), weirdness, 0, weirdness.max() < 0L ? Biomes.STONY_SHORE : Biomes.RIVER);
        addSurfaceBiome(output, frozen, fullRange, nearInlandContinentalness, span(erosions[0], erosions[1]), weirdness, 0, Biomes.FROZEN_RIVER);
        addSurfaceBiome(output, unfrozen, fullRange, nearInlandContinentalness, span(erosions[0], erosions[1]), weirdness, 0, Biomes.RIVER);
        addSurfaceBiome(output, frozen, fullRange, span(coastContinentalness, farInlandContinentalness), span(erosions[2], erosions[5]), weirdness, 0, Biomes.FROZEN_RIVER);
        addSurfaceBiome(output, unfrozen, fullRange, span(coastContinentalness, farInlandContinentalness), span(erosions[2], erosions[5]), weirdness, 0, Biomes.RIVER);
        addSurfaceBiome(output, frozen, fullRange, coastContinentalness, erosions[6], weirdness, 0, Biomes.FROZEN_RIVER);
        addSurfaceBiome(output, unfrozen, fullRange, coastContinentalness, erosions[6], weirdness, 0, Biomes.RIVER);
        addSurfaceBiome(output, span(temperatures[1], temperatures[2]), fullRange, span(inlandContinentalness, farInlandContinentalness), erosions[6], weirdness, 0, Biomes.SWAMP);
        addSurfaceBiome(output, span(temperatures[3], temperatures[4]), fullRange, span(inlandContinentalness, farInlandContinentalness), erosions[6], weirdness, 0, Biomes.MANGROVE_SWAMP);
        addSurfaceBiome(output, frozen, fullRange, span(inlandContinentalness, farInlandContinentalness), erosions[6], weirdness, 0, Biomes.FROZEN_RIVER);

        for (int tmpI = 0; tmpI < temperatures.length; tmpI++) {
            var tmp = temperatures[tmpI];

            for (int humI = 0; humI < humidities.length; humI++) {
                var hum = humidities[humI];
                var middleOrBadlands = pickMiddleBiomeOrBadlandsIfHot(tmpI, humI, weirdness);

                addSurfaceBiome(output, tmp, hum, span(midInlandContinentalness, farInlandContinentalness), span(erosions[0], erosions[1]), weirdness, 0, middleOrBadlands);
            }
        }
    }

    private void addUndergroundBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> output) {
        addUndergroundBiome(output, fullRange, fullRange, span(0.8F, 1.0F), fullRange, fullRange, 0, Biomes.DRIPSTONE_CAVES);
        addUndergroundBiome(output, fullRange, span(0.7F, 1.0F), fullRange, fullRange, fullRange, 0, Biomes.LUSH_CAVES);
        addBottomBiome(output, fullRange, fullRange, fullRange, span(erosions[0], erosions[1]), fullRange, 0, Biomes.DEEP_DARK);
    }

    private ResourceKey<Biome> pickMiddleBiome(int tmpI, int humI, Climate.Parameter parameter) {
        var base = middle[tmpI][humI];

        if (parameter.max() < 0L) {
            return base;
        }

        var variant = middleVariant[tmpI][humI];
        return variant == null ? base : variant;
    }

    private ResourceKey<Biome> pickMiddleBiomeOrBadlandsIfHot(int tmpI, int humI, Climate.Parameter weirdness) {
        return tmpI == 4
                ? pickBadlandsBiome(humI, weirdness)
                : pickMiddleBiome(tmpI, humI, weirdness);
    }

    private ResourceKey<Biome> pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(int tmpI, int humI, Climate.Parameter weirdness) {
        return tmpI == 0
                ? pickSlopeBiome(tmpI, humI, weirdness)
                : pickMiddleBiomeOrBadlandsIfHot(tmpI, humI, weirdness);
    }

    private ResourceKey<Biome> maybePickWindsweptSavannaBiome(int tmpI, int humI, Climate.Parameter weirdness, ResourceKey<Biome> fallback) {
        return tmpI > 1 && humI < 4 && weirdness.max() >= 0L
                ? Biomes.WINDSWEPT_SAVANNA
                : fallback;
    }

    private ResourceKey<Biome> pickShatteredCoastBiome(int tmpI, int humI, Climate.Parameter weirdness) {
        var base = weirdness.max() >= 0L
                ? pickMiddleBiome(tmpI, humI, weirdness)
                : pickBeachBiome(tmpI, humI);

        return maybePickWindsweptSavannaBiome(tmpI, humI, weirdness, base);
    }

    private ResourceKey<Biome> pickBeachBiome(int tmpI, int humI) {
        if (tmpI == 0) {
            return Biomes.SNOWY_BEACH;
        }

        if (tmpI == 4) {
            return Biomes.DESERT;
        }

        return Biomes.BEACH;
    }

    private ResourceKey<Biome> pickBadlandsBiome(int humI, Climate.Parameter weirdness) {
        if (humI < 2) {
            return weirdness.max() < 0L ? Biomes.BADLANDS : Biomes.ERODED_BADLANDS;
        }

        if (humI < 3) {
            return Biomes.BADLANDS;
        }

        return Biomes.WOODED_BADLANDS;
    }

    private ResourceKey<Biome> pickPlateauBiome(int tmpI, int humI, Climate.Parameter weirdness) {
        var base = plateau[tmpI][humI];

        if (weirdness.max() < 0L) {
            return base;
        }

        var variant = plateauVariant[tmpI][humI];
        return variant == null ? base : variant;
    }

    private ResourceKey<Biome> pickPeakBiome(int tmpI, int humI, Climate.Parameter weirdness) {
        if (tmpI <= 2) {
            return weirdness.max() < 0L ? Biomes.JAGGED_PEAKS : Biomes.FROZEN_PEAKS;
        }

        if (tmpI == 3) {
            return Biomes.STONY_PEAKS;
        }

        return pickBadlandsBiome(humI, weirdness);
    }

    private ResourceKey<Biome> pickSlopeBiome(int tmpI, int humI, Climate.Parameter weirdness) {
        if (tmpI >= 3) {
            return pickPlateauBiome(tmpI, humI, weirdness);
        }

        if (humI <= 1) {
            return Biomes.SNOWY_SLOPES;
        }

        return Biomes.GROVE;
    }

    private ResourceKey<Biome> pickShatteredBiome(int tmpI, int humI, Climate.Parameter weirdness) {
        var shatteredBiome = shattered[tmpI][humI];

        return shatteredBiome == null
                ? pickMiddleBiome(tmpI, humI, weirdness)
                : shatteredBiome;
    }

    private void addSurfaceBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> output, Climate.Parameter temperature, Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion, Climate.Parameter weirdness, float offset, ResourceKey<Biome> biome) {
        output.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, point(0), weirdness, offset), biome));
        output.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, point(1.0F), weirdness, offset), biome));
    }

    private void addUndergroundBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> output, Climate.Parameter temperature, Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion, Climate.Parameter weirdness, float offset, ResourceKey<Biome> biome) {
        output.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, span(0.2F, 0.9F), weirdness, offset), biome));
    }

    private void addBottomBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter temperature, Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion, Climate.Parameter weirdness, float offset, ResourceKey<Biome> resourceKey) {
        consumer.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, point(1.1F), weirdness, offset), resourceKey));
    }

    public static boolean isDeepDarkRegion(DensityFunction erosion, DensityFunction depth, DensityFunction.FunctionContext ctx) {
        return erosion.compute(ctx) < -0.225F && depth.compute(ctx) > 0.9F;
    }

    public static String getDebugStringForPeaksAndValleys(double d) {
        if (d < NoiseRouterData.peaksAndValleys(0.05F)) {
            return "Valley";
        } else if (d < NoiseRouterData.peaksAndValleys(0.26666668F)) {
            return "Low";
        } else if (d < NoiseRouterData.peaksAndValleys(0.4F)) {
            return "Mid";
        } else {
            return d < NoiseRouterData.peaksAndValleys(0.56666666F) ? "High" : "Peak";
        }
    }

    public String getDebugStringForContinentalness(double d) {
        double e = Climate.quantizeCoord((float) d);
        if (e < mushroomFieldsContinentalness.max()) {
            return "Mushroom fields";
        } else if (e < deepOceanContinentalness.max()) {
            return "Deep ocean";
        } else if (e < oceanContinentalness.max()) {
            return "Ocean";
        } else if (e < coastContinentalness.max()) {
            return "Coast";
        } else if (e < nearInlandContinentalness.max()) {
            return "Near inland";
        } else {
            return e < midInlandContinentalness.max() ? "Mid inland" : "Far inland";
        }
    }

    public String getDebugStringForErosion(double d) {
        return getDebugStringForNoiseValue(d, erosions);
    }

    public String getDebugStringForTemperature(double d) {
        return getDebugStringForNoiseValue(d, temperatures);
    }

    public String getDebugStringForHumidity(double d) {
        return getDebugStringForNoiseValue(d, humidities);
    }

    private static String getDebugStringForNoiseValue(double d, Climate.Parameter[] parameters) {
        double e = Climate.quantizeCoord((float) d);

        for (int i = 0; i < parameters.length; i++) {
            if (e < parameters[i].max()) {
                return i + "";
            }
        }

        return "?";
    }
}
