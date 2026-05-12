package net.foxboi.salted.common.levelgen.biome.source;

import java.util.List;
import java.util.function.Consumer;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseRouterData;

import static net.foxboi.salted.common.levelgen.biome.ModBiomes.*;
import static net.minecraft.world.level.biome.Biomes.*;
import static net.minecraft.world.level.biome.Climate.Parameter.point;
import static net.minecraft.world.level.biome.Climate.Parameter.span;

@SuppressWarnings("unchecked")
public final class ModifiedOverworldBiomeBuilder {
    public static final float VALLEY_SIZE = 5 / 100f;

    public static final float LOW_START = 8 / 30f;
    public static final float HIGH_START = 12 / 30f;
    public static final float HIGH_END = 28 / 30f;

    public static final float PEAK_SIZE = 3 / 30f;
    public static final float PEAK_START = 20 / 30f - PEAK_SIZE;
    public static final float PEAK_END = 20 / 30f + PEAK_SIZE;

    public static final float MUSHROOM_FIELD_END = -1.05F;
    public static final float DEEP_OCEAN_END = -0.455F;
    public static final float COAST_START = -0.19F;
    public static final float NEAR_INLAND_START = -0.11F;
    public static final float MID_INLAND_START = 0.03F;
    public static final float FAR_INLAND_START = 0.3F;


    private static final float EROSION_DEEP_DARK_DRYNESS_THRESHOLD = -0.225F;
    private static final float DEPTH_DEEP_DARK_DRYNESS_THRESHOLD = 0.9F;

    private final Climate.Parameter fullRange = span(-1.0F, 1.0F);


    private final Climate.Parameter[] temperatures = segmentedRange(
            -20 / 20f,
            -9 / 20f,
            -3 / 20f,
            4 / 20f,
            11 / 20f,
            20 / 20f
    );
    private final Climate.Parameter[] humidities = segmentedRange(
            -20 / 20f,
            -8 / 20f,
            -4 / 20f,
            0 / 20f,
            3 / 20f,
            6 / 20f,
            20 / 20f
    );

    private final Climate.Parameter[] erosions = segmentedRange(
            -400 / 400f,
            -312 / 400f,
            -150 / 400f,
            -89 / 400f,
            20 / 400f,
            180 / 400f,
            220 / 400f,
            400 / 400f
    );

    private final Climate.Parameter[] peaksAndValleys = segmentedRange(
            -1f,
            -HIGH_END,
            -PEAK_END,
            -PEAK_START,
            -HIGH_START,
            -LOW_START,
            -VALLEY_SIZE,
            VALLEY_SIZE,
            LOW_START,
            HIGH_START,
            PEAK_START,
            PEAK_END,
            HIGH_END,
            1f
    );

    private final Climate.Parameter frozen = temperatures[0];
    private final Climate.Parameter unfrozen = span(temperatures[1], temperatures[4]);

    private final Climate.Parameter mushroomFieldsContinentalness = span(-1.2F, MUSHROOM_FIELD_END);
    private final Climate.Parameter deepOceanContinentalness = span(MUSHROOM_FIELD_END, DEEP_OCEAN_END);
    private final Climate.Parameter oceanContinentalness = span(DEEP_OCEAN_END, COAST_START);
    private final Climate.Parameter coastContinentalness = span(COAST_START, NEAR_INLAND_START);

    private final Climate.Parameter inlandContinentalness = span(NEAR_INLAND_START, 0.55F);
    private final Climate.Parameter nearInlandContinentalness = span(NEAR_INLAND_START, MID_INLAND_START);
    private final Climate.Parameter midInlandContinentalness = span(MID_INLAND_START, FAR_INLAND_START);
    private final Climate.Parameter farInlandContinentalness = span(FAR_INLAND_START, 1.0F);

    private final ResourceKey<Biome>[][] oceans = new ResourceKey[][]{
            {DEEP_FROZEN_OCEAN, DEEP_COLD_OCEAN, DEEP_OCEAN, DEEP_LUKEWARM_OCEAN, WARM_OCEAN},
            {FROZEN_OCEAN, COLD_OCEAN, OCEAN, LUKEWARM_OCEAN, WARM_OCEAN}
    };

    private final ResourceKey<Biome>[][] low = new ResourceKey[][]{
            {SNOWY_PLAINS, SNOWY_PLAINS, SNOWY_PLAINS, SNOWY_TAIGA, TAIGA, TAIGA},
            {PLAINS, PLAINS, FOREST, TAIGA, OLD_GROWTH_SPRUCE_TAIGA, REDWOOD_TAIGA},
            {FLOWER_FOREST, PLAINS, ASPEN_FOREST, BIRCH_FOREST, DARK_FOREST, DARK_FOREST},
            {SAVANNA, SAVANNA, FOREST, FOREST, JUNGLE, JUNGLE},
            {DESERT, DESERT, DESERT, DESERT, DESERT, DESERT}
    };

    private final ResourceKey<Biome>[][] lowVariant = new ResourceKey[][]{
            {ICE_SPIKES, null, SNOWY_TAIGA, null, null, null},
            {null, null, LAVENDER_FIELD, WOODED_PLAINS, REDWOOD_TAIGA, null},
            {LAVENDER_FIELD, WOODED_PLAINS, ASPEN_FOREST, OLD_GROWTH_BIRCH_FOREST, null, null},
            {null, BARLEY_FIELD, PLAINS, SPARSE_JUNGLE, BAMBOO_JUNGLE, null},
            {null, null, null, null, null, null}
    };

    private final ResourceKey<Biome>[][] middle = new ResourceKey[][]{
            {SNOWY_PLAINS, SNOWY_PLAINS, SNOWY_PLAINS, SNOWY_TAIGA, TAIGA, TAIGA},
            {PLAINS, PLAINS, FOREST, TAIGA, OLD_GROWTH_SPRUCE_TAIGA, REDWOOD_TAIGA},
            {FLOWER_FOREST, PLAINS, ASPEN_FOREST, BIRCH_FOREST, DARK_FOREST, DARK_FOREST},
            {SAVANNA, SAVANNA, FOREST, FOREST, JUNGLE, JUNGLE},
            {DESERT, DESERT, DESERT, DESERT, DESERT, DESERT}
    };

    private final ResourceKey<Biome>[][] middleVariant = new ResourceKey[][]{
            {ICE_SPIKES, null, SNOWY_TAIGA, null, null, null},
            {null, null, null, WOODED_PLAINS, OLD_GROWTH_PINE_TAIGA, null},
            {SUNFLOWER_PLAINS, WOODED_PLAINS, ASPEN_FOREST, OLD_GROWTH_BIRCH_FOREST, null, null},
            {null, BARLEY_FIELD, PLAINS, SPARSE_JUNGLE, BAMBOO_JUNGLE, null},
            {null, null, null, null, null, null}
    };

    private final ResourceKey<Biome>[][] plateau = new ResourceKey[][]{
            {SNOWY_PLAINS, SNOWY_PLAINS, SNOWY_PLAINS, SNOWY_TAIGA, SNOWY_TAIGA, SNOWY_TAIGA},
            {MEADOW, MEADOW, MAPLE_FOREST, TAIGA, OLD_GROWTH_SPRUCE_TAIGA, SPARSE_REDWOOD_TAIGA},
            {MEADOW, MEADOW, MEADOW, MEADOW, PALE_GARDEN, PALE_GARDEN},
            {SAVANNA_PLATEAU, SAVANNA_PLATEAU, FOREST, FOREST, FOREST, JUNGLE},
            {BADLANDS, BADLANDS, BADLANDS, WOODED_BADLANDS, WOODED_BADLANDS, WOODED_BADLANDS}
    };

    private final ResourceKey<Biome>[][] plateauVariant = new ResourceKey[][]{
            {ICE_SPIKES, null, null, null, null, null},
            {CHERRY_GROVE, FIREFLY_MEADOW, MAPLE_FOREST, MEADOW, OLD_GROWTH_PINE_TAIGA, null},
            {CHERRY_GROVE, CHERRY_GROVE, ASPEN_FOREST, BIRCH_FOREST, FIREFLY_MEADOW, null},
            {null, null, null, null, null, null},
            {ERODED_BADLANDS, ERODED_BADLANDS, null, null, null, null}
    };

    private final ResourceKey<Biome>[][] shattered = new ResourceKey[][]{
            {WINDSWEPT_GRAVELLY_HILLS, WINDSWEPT_GRAVELLY_HILLS, WINDSWEPT_HILLS, WINDSWEPT_FOREST, WINDSWEPT_FOREST, WINDSWEPT_FOREST},
            {WINDSWEPT_GRAVELLY_HILLS, WINDSWEPT_GRAVELLY_HILLS, WINDSWEPT_HILLS, WINDSWEPT_FOREST, WINDSWEPT_FOREST, WINDSWEPT_FOREST},
            {WINDSWEPT_HILLS, WINDSWEPT_HILLS, WINDSWEPT_HILLS, WINDSWEPT_FOREST, WINDSWEPT_FOREST, WINDSWEPT_FOREST},
            {null, null, null, null, null, null},
            {null, null, null, null, null, null}
    };

    public List<Climate.ParameterPoint> spawnTarget() {
        var depth = point(0);
        return List.of(
                new Climate.ParameterPoint(fullRange, fullRange, span(inlandContinentalness, fullRange), fullRange, depth, span(-1f, -0.16f), 0),
                new Climate.ParameterPoint(fullRange, fullRange, span(inlandContinentalness, fullRange), fullRange, depth, span(0.16f, 1f), 0)
        );
    }

    public void addBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> output) {
        addOffCoastBiomes(output);
        addInlandBiomes(output);
        addUndergroundBiomes(output);
    }

    private void addOffCoastBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> output) {
        addSurfaceBiome(output, fullRange, fullRange, mushroomFieldsContinentalness, fullRange, fullRange, 0, MUSHROOM_FIELDS);

        for (var ti = 0; ti < temperatures.length; ti++) {
            var temp = temperatures[ti];
            addSurfaceBiome(output, temp, fullRange, deepOceanContinentalness, fullRange, fullRange, 0, oceans[0][ti]);
            addSurfaceBiome(output, temp, fullRange, oceanContinentalness, fullRange, fullRange, 0, oceans[1][ti]);
        }
    }

    private void addInlandBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> output) {
        addMidSlice(output, peaksAndValleys[0]);
        addHighSlice(output, peaksAndValleys[1]);
        addPeaks(output, peaksAndValleys[2]);
        addHighSlice(output, peaksAndValleys[3]);
        addMidSlice(output, peaksAndValleys[4]);
        addLowSlice(output, peaksAndValleys[5]);
        addValleys(output, peaksAndValleys[6]);
        addLowSlice(output, peaksAndValleys[7]);
        addMidSlice(output, peaksAndValleys[8]);
        addHighSlice(output, peaksAndValleys[9]);
        addPeaks(output, peaksAndValleys[10]);
        addHighSlice(output, peaksAndValleys[11]);
        addMidSlice(output, peaksAndValleys[12]);
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
        addSurfaceBiome(output, fullRange, fullRange, coastContinentalness, span(erosions[0], erosions[2]), weirdness, 0, STONY_SHORE);

        addSurfaceBiome(output, span(temperatures[1], temperatures[2]), fullRange, span(nearInlandContinentalness, farInlandContinentalness), erosions[6], weirdness, 0, SWAMP);
        addSurfaceBiome(output, span(temperatures[3], temperatures[4]), fullRange, span(nearInlandContinentalness, farInlandContinentalness), erosions[6], weirdness, 0, MANGROVE_SWAMP);

        for (var tmpI = 0; tmpI < temperatures.length; tmpI++) {
            var tmp = temperatures[tmpI];

            for (int humI = 0; humI < humidities.length; humI++) {
                var hum = humidities[humI];

                var low = pickLowBiome(tmpI, humI, weirdness);
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

                addSurfaceBiome(output, tmp, hum, nearInlandContinentalness, erosions[2], weirdness, 0, low);
                addSurfaceBiome(output, tmp, hum, midInlandContinentalness, erosions[2], weirdness, 0, middleOrBadlands);
                addSurfaceBiome(output, tmp, hum, farInlandContinentalness, erosions[2], weirdness, 0, plateau);

                addSurfaceBiome(output, tmp, hum, span(coastContinentalness, nearInlandContinentalness), erosions[3], weirdness, 0, low);
                addSurfaceBiome(output, tmp, hum, span(midInlandContinentalness, farInlandContinentalness), erosions[3], weirdness, 0, middleOrBadlands);

                if (weirdness.max() < 0L) {
                    addSurfaceBiome(output, tmp, hum, coastContinentalness, erosions[4], weirdness, 0, beach);
                    addSurfaceBiome(output, tmp, hum, nearInlandContinentalness, erosions[4], weirdness, 0, low);
                    addSurfaceBiome(output, tmp, hum, span(midInlandContinentalness, farInlandContinentalness), erosions[4], weirdness, 0, middle);
                } else {
                    addSurfaceBiome(output, tmp, hum, span(coastContinentalness, nearInlandContinentalness), erosions[4], weirdness, 0, low);
                    addSurfaceBiome(output, tmp, hum, span(midInlandContinentalness, farInlandContinentalness), erosions[4], weirdness, 0, middle);
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
        addSurfaceBiome(output, fullRange, fullRange, coastContinentalness, span(erosions[0], erosions[2]), weirdness, 0, STONY_SHORE);

        addSurfaceBiome(output, span(temperatures[1], temperatures[2]), fullRange, span(nearInlandContinentalness, farInlandContinentalness), erosions[6], weirdness, 0, SWAMP);
        addSurfaceBiome(output, span(temperatures[3], temperatures[4]), fullRange, span(nearInlandContinentalness, farInlandContinentalness), erosions[6], weirdness, 0, MANGROVE_SWAMP);


        for (int tmpI = 0; tmpI < temperatures.length; tmpI++) {
            var tmp = temperatures[tmpI];

            for (int humI = 0; humI < humidities.length; humI++) {
                var hum = humidities[humI];

                var low = pickLowBiome(tmpI, humI, weirdness);
                var maybeHeath = maybePickHeathland(tmpI, humI, weirdness, low);
                var lowOrBadlands = pickLowBiomeOrBadlandsIfHot(tmpI, humI, weirdness);
                var middleOrBadlandsOrSlope = pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(tmpI, humI, weirdness);
                var beach = pickBeachBiome(tmpI, humI);
                var windsweptSavanna = maybePickWindsweptSavannaBiome(tmpI, humI, weirdness, low);
                var shatteredCoast = pickShatteredCoastBiome(tmpI, humI, weirdness);

                addSurfaceBiome(output, tmp, hum, nearInlandContinentalness, span(erosions[0], erosions[1]), weirdness, 0, lowOrBadlands);
                addSurfaceBiome(output, tmp, hum, span(midInlandContinentalness, farInlandContinentalness), span(erosions[0], erosions[1]), weirdness, 0, middleOrBadlandsOrSlope);

                addSurfaceBiome(output, tmp, hum, nearInlandContinentalness, span(erosions[2], erosions[3]), weirdness, 0, low);
                addSurfaceBiome(output, tmp, hum, span(midInlandContinentalness, farInlandContinentalness), span(erosions[2], erosions[3]), weirdness, 0, lowOrBadlands);

                addSurfaceBiome(output, tmp, hum, coastContinentalness, span(erosions[3], erosions[4]), weirdness, 0, beach);
                addSurfaceBiome(output, tmp, hum, span(nearInlandContinentalness, farInlandContinentalness), erosions[4], weirdness, 0, low);

                addSurfaceBiome(output, tmp, hum, coastContinentalness, erosions[5], weirdness, 0, shatteredCoast);
                addSurfaceBiome(output, tmp, hum, nearInlandContinentalness, erosions[5], weirdness, 0, windsweptSavanna);
                addSurfaceBiome(output, tmp, hum, span(midInlandContinentalness, farInlandContinentalness), erosions[5], weirdness, 0, maybeHeath);

                addSurfaceBiome(output, tmp, hum, coastContinentalness, erosions[6], weirdness, 0, beach);
                if (tmpI == 0) {
                    addSurfaceBiome(output, tmp, hum, span(nearInlandContinentalness, farInlandContinentalness), erosions[6], weirdness, 0, low);
                }
            }
        }
    }

    private void addValleys(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> output, Climate.Parameter weirdness) {
        addSurfaceBiome(output, frozen, fullRange, coastContinentalness, span(erosions[0], erosions[1]), weirdness, 0, weirdness.max() < 0L ? STONY_SHORE : FROZEN_RIVER);
        addSurfaceBiome(output, unfrozen, fullRange, coastContinentalness, span(erosions[0], erosions[1]), weirdness, 0, weirdness.max() < 0L ? STONY_SHORE : RIVER);

        addSurfaceBiome(output, frozen, fullRange, nearInlandContinentalness, span(erosions[0], erosions[1]), weirdness, 0, FROZEN_RIVER);
        addSurfaceBiome(output, unfrozen, fullRange, nearInlandContinentalness, span(erosions[0], erosions[1]), weirdness, 0, RIVER);

        addSurfaceBiome(output, frozen, fullRange, span(coastContinentalness, farInlandContinentalness), span(erosions[2], erosions[5]), weirdness, 0, FROZEN_RIVER);
        addSurfaceBiome(output, unfrozen, fullRange, span(coastContinentalness, farInlandContinentalness), span(erosions[2], erosions[5]), weirdness, 0, RIVER);

        addSurfaceBiome(output, frozen, fullRange, coastContinentalness, erosions[6], weirdness, 0, FROZEN_RIVER);
        addSurfaceBiome(output, unfrozen, fullRange, coastContinentalness, erosions[6], weirdness, 0, RIVER);

        addSurfaceBiome(output, span(temperatures[1], temperatures[2]), fullRange, span(inlandContinentalness, farInlandContinentalness), erosions[6], weirdness, 0, SWAMP);
        addSurfaceBiome(output, span(temperatures[3], temperatures[4]), fullRange, span(inlandContinentalness, farInlandContinentalness), erosions[6], weirdness, 0, MANGROVE_SWAMP);
        addSurfaceBiome(output, frozen, fullRange, span(inlandContinentalness, farInlandContinentalness), erosions[6], weirdness, 0, FROZEN_RIVER);

        // Low erosion inland
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
        addUndergroundBiome(output, fullRange, fullRange, span(.8f, 1f), fullRange, fullRange, 0, DRIPSTONE_CAVES);
        addUndergroundBiome(output, fullRange, fullRange, span(.4f, .8f), span(.5f, .74f), fullRange, 0, LIMESTONE_CAVES);
        addUndergroundBiome(output, fullRange, span(.7f, 1f), fullRange, fullRange, fullRange, 0, LUSH_CAVES);
        addBottomBiome(output, fullRange, fullRange, fullRange, span(erosions[0], erosions[1]), fullRange, 0, DEEP_DARK);
    }

    private ResourceKey<Biome> pickLowBiome(int tmpI, int humI, Climate.Parameter parameter) {
        var base = low[tmpI][humI];

        if (parameter.max() < 0L) {
            return base;
        }

        var variant = lowVariant[tmpI][humI];
        return variant == null ? base : variant;
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

    private ResourceKey<Biome> pickLowBiomeOrBadlandsIfHot(int tmpI, int humI, Climate.Parameter weirdness) {
        return tmpI == 4
                ? pickBadlandsBiome(humI, weirdness)
                : pickLowBiome(tmpI, humI, weirdness);
    }

    private ResourceKey<Biome> pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(int tmpI, int humI, Climate.Parameter weirdness) {
        return tmpI == 0
                ? pickSlopeBiome(tmpI, humI, weirdness)
                : pickMiddleBiomeOrBadlandsIfHot(tmpI, humI, weirdness);
    }

    private ResourceKey<Biome> maybePickWindsweptSavannaBiome(int tmpI, int humI, Climate.Parameter weirdness, ResourceKey<Biome> fallback) {
        return tmpI > 1 && humI < 5 && weirdness.max() >= 0L
                ? WINDSWEPT_SAVANNA
                : fallback;
    }

    private ResourceKey<Biome> maybePickHeathland(int tmpI, int humI, Climate.Parameter weirdness, ResourceKey<Biome> fallback) {
        return tmpI > 2 && humI < 4 && weirdness.max() < 0L
                ? HEATHLAND
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
            return SNOWY_BEACH;
        }

        if (tmpI == 4) {
            return DESERT;
        }

        return BEACH;
    }

    private ResourceKey<Biome> pickBadlandsBiome(int humI, Climate.Parameter weirdness) {
        if (humI < 2) {
            return weirdness.max() < 0L ? BADLANDS : ERODED_BADLANDS;
        }

        if (humI < 3) {
            return BADLANDS;
        }

        return WOODED_BADLANDS;
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
            return weirdness.max() < 0L ? JAGGED_PEAKS : FROZEN_PEAKS;
        }

        if (tmpI == 3) {
            return STONY_PEAKS;
        }

        return pickBadlandsBiome(humI, weirdness);
    }

    private ResourceKey<Biome> pickSlopeBiome(int tmpI, int humI, Climate.Parameter weirdness) {
        if (tmpI >= 3) {
            return pickPlateauBiome(tmpI, humI, weirdness);
        }

        if (humI <= 1) {
            return SNOWY_SLOPES;
        }

        return GROVE;
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
        return erosion.compute(ctx) < EROSION_DEEP_DARK_DRYNESS_THRESHOLD && depth.compute(ctx) > DEPTH_DEEP_DARK_DRYNESS_THRESHOLD;
    }

    public static String getDebugStringForPeaksAndValleys(double noise) {
        if (noise < NoiseRouterData.peaksAndValleys(VALLEY_SIZE)) {
            return "Valley";
        }

        if (noise < NoiseRouterData.peaksAndValleys(LOW_START)) {
            return "Low";
        }

        if (noise < NoiseRouterData.peaksAndValleys(HIGH_START)) {
            return "Mid";
        }

        if (noise < NoiseRouterData.peaksAndValleys(PEAK_START)) {
            return "High";
        }

        return "Peak";
    }

    public String getDebugStringForContinentalness(double noise) {
        var con = Climate.quantizeCoord((float) noise);

        if (con < mushroomFieldsContinentalness.max()) {
            return "Mushroom fields";
        }

        if (con < deepOceanContinentalness.max()) {
            return "Deep ocean";
        }

        if (con < oceanContinentalness.max()) {
            return "Ocean";
        }

        if (con < coastContinentalness.max()) {
            return "Coast";
        }

        if (con < nearInlandContinentalness.max()) {
            return "Near inland";
        }

        if (con < midInlandContinentalness.max()) {
            return "Mid inland";
        }

        return "Far inland";
    }

    public String getDebugStringForErosion(double noies) {
        return getDebugStringForNoiseValue(noies, erosions);
    }

    public String getDebugStringForTemperature(double noise) {
        return getDebugStringForNoiseValue(noise, temperatures);
    }

    public String getDebugStringForHumidity(double noise) {
        return getDebugStringForNoiseValue(noise, humidities);
    }

    private static String getDebugStringForNoiseValue(double noise, Climate.Parameter[] params) {
        var coord = Climate.quantizeCoord((float) noise);

        for (var i = 0; i < params.length; i++) {
            if (coord < params[i].max()) {
                return i + "";
            }
        }

        return "?";
    }

    private static Climate.Parameter[] segmentedRange(float first, float... segments) {
        var length = segments.length;
        var array = new Climate.Parameter[length];

        var start = first;
        for (var i = 0; i < length; i ++) {
            var end = segments[i];

            array[i] = span(start, end);
            start = end;
        }

        return array;
    }
}
