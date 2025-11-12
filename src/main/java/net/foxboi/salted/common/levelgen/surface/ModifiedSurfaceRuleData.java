package net.foxboi.salted.common.levelgen.surface;

import java.util.ArrayList;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.VerticalAnchor;

import static net.foxboi.salted.common.levelgen.surface.ModSurfaceRules.*;
import static net.minecraft.world.level.levelgen.SurfaceRules.*;

public class ModifiedSurfaceRuleData {
    private static final RuleSource AIR = makeStateRule(Blocks.AIR);
    private static final RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
    private static final RuleSource WHITE_TERRACOTTA = makeStateRule(Blocks.WHITE_TERRACOTTA);
    private static final RuleSource ORANGE_TERRACOTTA = makeStateRule(Blocks.ORANGE_TERRACOTTA);
    private static final RuleSource TERRACOTTA = makeStateRule(Blocks.TERRACOTTA);
    private static final RuleSource RED_SAND = makeStateRule(Blocks.RED_SAND);
    private static final RuleSource RED_SANDSTONE = makeStateRule(Blocks.RED_SANDSTONE);
    private static final RuleSource STONE = makeStateRule(Blocks.STONE);
    private static final RuleSource DEEPSLATE = makeStateRule(Blocks.DEEPSLATE);
    private static final RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final RuleSource PODZOL = makeStateRule(Blocks.PODZOL);
    private static final RuleSource COARSE_DIRT = makeStateRule(Blocks.COARSE_DIRT);
    private static final RuleSource MYCELIUM = makeStateRule(Blocks.MYCELIUM);
    private static final RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final RuleSource CALCITE = makeStateRule(Blocks.CALCITE);
    private static final RuleSource GRAVEL = makeStateRule(Blocks.GRAVEL);
    private static final RuleSource SAND = makeStateRule(Blocks.SAND);
    private static final RuleSource SANDSTONE = makeStateRule(Blocks.SANDSTONE);
    private static final RuleSource PACKED_ICE = makeStateRule(Blocks.PACKED_ICE);
    private static final RuleSource SNOW_BLOCK = makeStateRule(Blocks.SNOW_BLOCK);
    private static final RuleSource MUD = makeStateRule(Blocks.MUD);
    private static final RuleSource POWDER_SNOW = makeStateRule(Blocks.POWDER_SNOW);
    private static final RuleSource ICE = makeStateRule(Blocks.ICE);
    private static final RuleSource WATER = makeStateRule(Blocks.WATER);
    private static final RuleSource LAVA = makeStateRule(Blocks.LAVA);
    private static final RuleSource NETHERRACK = makeStateRule(Blocks.NETHERRACK);
    private static final RuleSource SOUL_SAND = makeStateRule(Blocks.SOUL_SAND);
    private static final RuleSource SOUL_SOIL = makeStateRule(Blocks.SOUL_SOIL);
    private static final RuleSource BASALT = makeStateRule(Blocks.BASALT);
    private static final RuleSource BLACKSTONE = makeStateRule(Blocks.BLACKSTONE);
    private static final RuleSource WARPED_WART_BLOCK = makeStateRule(Blocks.WARPED_WART_BLOCK);
    private static final RuleSource WARPED_NYLIUM = makeStateRule(Blocks.WARPED_NYLIUM);
    private static final RuleSource NETHER_WART_BLOCK = makeStateRule(Blocks.NETHER_WART_BLOCK);
    private static final RuleSource CRIMSON_NYLIUM = makeStateRule(Blocks.CRIMSON_NYLIUM);
    private static final RuleSource ENDSTONE = makeStateRule(Blocks.END_STONE);

    private static RuleSource makeStateRule(Block block) {
        return state(block.defaultBlockState());
    }

    public static RuleSource overworld(Holder<BiomeSurfaceOverrides> overrides) {
        return overworldLike(overrides, true, false, true);
    }

    public static RuleSource overworldLike(Holder<BiomeSurfaceOverrides> overrides, boolean noUndergroundSurface, boolean bedrockRoof, boolean bedrockFloor) {
        var isY97Plus2Depth = yBlockCheck(VerticalAnchor.absolute(97), 2);
        var isY256 = yBlockCheck(VerticalAnchor.absolute(256), 0);
        var isY63MinusDepth = yStartCheck(VerticalAnchor.absolute(63), -1);
        var isY74PlusDepth = yStartCheck(VerticalAnchor.absolute(74), 1);
        var isY60 = yBlockCheck(VerticalAnchor.absolute(60), 0);
        var isY62 = yBlockCheck(VerticalAnchor.absolute(62), 0);
        var isY63 = yBlockCheck(VerticalAnchor.absolute(63), 0);

        var isWaterDepth1 = waterBlockCheck(-1, 0);
        var isWaterDepth0 = waterBlockCheck(0, 0);
        var isWaterDepth6 = waterStartCheck(-6, -1);

        var isHole = hole();
        var isSteep = steep();

        var isFrozenOcean = isBiome(Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN);

        var grassSurface = sequence(ifTrue(isWaterDepth0, GRASS_BLOCK), DIRT);
        var sandSurface = sequence(ifTrue(ON_CEILING, SANDSTONE), SAND);
        var gravelSurface = sequence(ifTrue(ON_CEILING, STONE), GRAVEL);

        var isAquaticSandBiome = isBiome(Biomes.WARM_OCEAN, Biomes.BEACH, Biomes.SNOWY_BEACH);
        var isDesert = isBiome(Biomes.DESERT);

        var stoneAndSand = sequence(
                ifTrue(
                        isBiome(Biomes.STONY_PEAKS),
                        sequence(ifTrue(noiseCondition(Noises.CALCITE, -0.0125, 0.0125), CALCITE), STONE)
                ),

                ifTrue(
                        isBiome(Biomes.STONY_SHORE),
                        sequence(ifTrue(noiseCondition(Noises.GRAVEL, -0.05, 0.05), gravelSurface), STONE)
                ),

                ifTrue(
                        isBiome(Biomes.WINDSWEPT_HILLS),
                        ifTrue(surfaceNoiseAbove(1.0), STONE)
                ),

                ifTrue(
                        isAquaticSandBiome,
                        sandSurface
                ),

                ifTrue(
                        isDesert,
                        sandSurface
                ),

                ifTrue(
                        isBiome(Biomes.DRIPSTONE_CAVES),
                        STONE
                )
        );

        var powderSnow = ifTrue(
                noiseCondition(Noises.POWDER_SNOW, 0.45, 0.58),
                ifTrue(isWaterDepth0, POWDER_SNOW)
        );

        var morePowderSnow = ifTrue(
                noiseCondition(Noises.POWDER_SNOW, 0.35, 0.6),
                ifTrue(isWaterDepth0, POWDER_SNOW)
        );

        var underFloorBase = sequence(
                ifTrue(
                        isBiome(Biomes.FROZEN_PEAKS),
                        sequence(
                                ifTrue(isSteep, PACKED_ICE),
                                ifTrue(noiseCondition(Noises.PACKED_ICE, -0.5, 0.2), PACKED_ICE),
                                ifTrue(noiseCondition(Noises.ICE, -0.0625, 0.025), ICE),
                                ifTrue(isWaterDepth0, SNOW_BLOCK)
                        )
                ),

                ifTrue(
                        isBiome(Biomes.SNOWY_SLOPES),
                        sequence(
                                ifTrue(isSteep, STONE),
                                powderSnow,
                                ifTrue(isWaterDepth0, SNOW_BLOCK)
                        )
                ),

                ifTrue(
                        isBiome(Biomes.JAGGED_PEAKS),
                        STONE
                ),

                ifTrue(
                        isBiome(Biomes.GROVE),
                        sequence(powderSnow, DIRT)
                ),

                stoneAndSand,

                ifTrue(
                        isBiome(Biomes.WINDSWEPT_SAVANNA),
                        ifTrue(surfaceNoiseAbove(1.75), STONE)
                ),

                ifTrue(
                        isBiome(Biomes.WINDSWEPT_GRAVELLY_HILLS),
                        sequence(
                                ifTrue(surfaceNoiseAbove(2.0), gravelSurface),
                                ifTrue(surfaceNoiseAbove(1.0), STONE),
                                ifTrue(surfaceNoiseAbove(-1.0), DIRT),
                                gravelSurface
                        )
                ),

                ifTrue(
                        isBiome(Biomes.MANGROVE_SWAMP),
                        MUD
                ),

                DIRT
        );

        var onFloorBase = sequence(
                ifTrue(
                        isBiome(Biomes.FROZEN_PEAKS),
                        sequence(
                                ifTrue(isSteep, PACKED_ICE),
                                ifTrue(noiseCondition(Noises.PACKED_ICE, 0.0, 0.2), PACKED_ICE),
                                ifTrue(noiseCondition(Noises.ICE, 0.0, 0.025), ICE),
                                ifTrue(isWaterDepth0, SNOW_BLOCK)
                        )
                ),

                ifTrue(
                        isBiome(Biomes.SNOWY_SLOPES),
                        sequence(
                                ifTrue(isSteep, STONE),
                                morePowderSnow,
                                ifTrue(isWaterDepth0, SNOW_BLOCK)
                        )
                ),

                ifTrue(
                        isBiome(Biomes.JAGGED_PEAKS),
                        sequence(
                                ifTrue(isSteep, STONE),
                                ifTrue(isWaterDepth0, SNOW_BLOCK)
                        )
                ),

                ifTrue(
                        isBiome(Biomes.GROVE),
                        sequence(
                                morePowderSnow,
                                ifTrue(isWaterDepth0, SNOW_BLOCK)
                        )
                ),

                stoneAndSand,

                ifTrue(
                        isBiome(Biomes.WINDSWEPT_SAVANNA),
                        sequence(
                                ifTrue(surfaceNoiseAbove(1.75), STONE),
                                ifTrue(surfaceNoiseAbove(-0.5), COARSE_DIRT)
                        )
                ),

                ifTrue(
                        isBiome(Biomes.WINDSWEPT_GRAVELLY_HILLS),
                        sequence(
                                ifTrue(surfaceNoiseAbove(2.0), gravelSurface),
                                ifTrue(surfaceNoiseAbove(1.0), STONE),
                                ifTrue(surfaceNoiseAbove(-1.0), grassSurface),
                                gravelSurface
                        )
                ),

                ifTrue(
                        isBiome(Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA),
                        sequence(
                                ifTrue(surfaceNoiseAbove(1.75), COARSE_DIRT),
                                ifTrue(surfaceNoiseAbove(-0.95), PODZOL)
                        )
                ),

                ifTrue(
                        isBiome(Biomes.ICE_SPIKES),
                        ifTrue(isWaterDepth0, SNOW_BLOCK)
                ),

                ifTrue(
                        isBiome(Biomes.MANGROVE_SWAMP),
                        MUD
                ),

                ifTrue(
                        isBiome(Biomes.MUSHROOM_FIELDS),
                        MYCELIUM
                ),

                grassSurface
        );

        var loSurface = noiseCondition(Noises.SURFACE, -0.909, -0.5454);
        var mdSurface = noiseCondition(Noises.SURFACE, -0.1818, 0.1818);
        var hiSurface = noiseCondition(Noises.SURFACE, 0.5454, 0.909);

        RuleSource surfaceAnywhere = sequence(
                ifTrue(
                        ON_FLOOR,
                        sequence(
                                ifTrue(
                                        isBiome(Biomes.WOODED_BADLANDS),
                                        ifTrue(
                                                isY97Plus2Depth,
                                                sequence(
                                                        ifTrue(loSurface, COARSE_DIRT),
                                                        ifTrue(mdSurface, COARSE_DIRT),
                                                        ifTrue(hiSurface, COARSE_DIRT),
                                                        grassSurface
                                                )
                                        )
                                ),

                                ifTrue(
                                        isBiome(Biomes.SWAMP),
                                        ifTrue(
                                                isY62, ifTrue(not(isY63), ifTrue(noiseCondition(Noises.SWAMP, 0.0), WATER))
                                        )
                                ),

                                ifTrue(
                                        isBiome(Biomes.MANGROVE_SWAMP),
                                        ifTrue(
                                                isY60, ifTrue(not(isY63), ifTrue(noiseCondition(Noises.SWAMP, 0.0), WATER))
                                        )
                                )
                        )
                ),

                ifTrue(
                        isBiome(Biomes.BADLANDS, Biomes.ERODED_BADLANDS, Biomes.WOODED_BADLANDS),
                        sequence(
                                ifTrue(
                                        ON_FLOOR,
                                        sequence(
                                                ifTrue(isY256, ORANGE_TERRACOTTA),
                                                ifTrue(
                                                        isY74PlusDepth,
                                                        sequence(
                                                                ifTrue(loSurface, TERRACOTTA),
                                                                ifTrue(mdSurface, TERRACOTTA),
                                                                ifTrue(hiSurface, TERRACOTTA),
                                                                bandlands()
                                                        )
                                                ),
                                                ifTrue(isWaterDepth1, sequence(ifTrue(ON_CEILING, RED_SANDSTONE), RED_SAND)),
                                                ifTrue(not(isHole), ORANGE_TERRACOTTA),
                                                ifTrue(isWaterDepth6, WHITE_TERRACOTTA),
                                                gravelSurface
                                        )
                                ),
                                ifTrue(
                                        isY63MinusDepth,
                                        sequence(
                                                ifTrue(isY63, ifTrue(not(isY74PlusDepth), ORANGE_TERRACOTTA)), bandlands()
                                        )
                                ),
                                ifTrue(UNDER_FLOOR, ifTrue(isWaterDepth6, WHITE_TERRACOTTA))
                        )
                ),
                ifTrue(
                        ON_FLOOR,
                        ifTrue(
                                isWaterDepth1,
                                sequence(
                                        ifTrue(
                                                isFrozenOcean,
                                                ifTrue(
                                                        isHole, sequence(ifTrue(isWaterDepth0, AIR), ifTrue(temperature(), ICE), WATER)
                                                )
                                        ),
                                        onFloorBase
                                )
                        )
                ),
                ifTrue(
                        isWaterDepth6,
                        sequence(
                                ifTrue(ON_FLOOR, ifTrue(isFrozenOcean, ifTrue(isHole, WATER))),
                                ifTrue(UNDER_FLOOR, underFloorBase),
                                ifTrue(isAquaticSandBiome, ifTrue(DEEP_UNDER_FLOOR, SANDSTONE)),
                                ifTrue(isDesert, ifTrue(VERY_DEEP_UNDER_FLOOR, SANDSTONE))
                        )
                ),
                ifTrue(
                        ON_FLOOR,
                        sequence(
                                ifTrue(isBiome(Biomes.FROZEN_PEAKS, Biomes.JAGGED_PEAKS), STONE),
                                ifTrue(isBiome(Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN), sandSurface),
                                gravelSurface
                        )
                )
        );

        var builder = new ArrayList<RuleSource>();
        if (bedrockRoof) {
            builder.add(ifTrue(not(verticalGradient("bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())), BEDROCK));
        }

        if (bedrockFloor) {
            builder.add(ifTrue(verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK));
        }

        builder.add(biomeOverrides(overrides));

        var surfaceOnTopOnly = ifTrue(abovePreliminarySurface(), surfaceAnywhere);

        builder.add(noUndergroundSurface ? surfaceOnTopOnly : surfaceAnywhere);
        builder.add(ifTrue(verticalGradient("deepslate", VerticalAnchor.absolute(0), VerticalAnchor.absolute(8)), DEEPSLATE));

        return sequence(builder.toArray(RuleSource[]::new));
    }

    public static RuleSource nether(Holder<BiomeSurfaceOverrides> overrides) {
        var isY31 = yBlockCheck(VerticalAnchor.absolute(31), 0);
        var isY32 = yBlockCheck(VerticalAnchor.absolute(32), 0);

        var isAboveOrAtY30 = yStartCheck(VerticalAnchor.absolute(30), 0);
        var isBelowY35 = not(yStartCheck(VerticalAnchor.absolute(35), 0));

        var isY5BelowTop = yBlockCheck(VerticalAnchor.belowTop(5), 0);

        var isHole = hole();

        var isSoulSand = noiseCondition(Noises.SOUL_SAND_LAYER, -0.012);
        var isGravel = noiseCondition(Noises.GRAVEL_LAYER, -0.012);
        var isPatch = noiseCondition(Noises.PATCH, -0.012);
        var isNetherrack = noiseCondition(Noises.NETHERRACK, 0.54);
        var isNetherWart = noiseCondition(Noises.NETHER_WART, 1.17);
        var isAlternate = noiseCondition(Noises.NETHER_STATE_SELECTOR, 0.0);

        var gravelPatch = ifTrue(isPatch, ifTrue(isAboveOrAtY30, ifTrue(isBelowY35, GRAVEL)));

        return sequence(
                ifTrue(verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK),
                ifTrue(not(verticalGradient("bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())), BEDROCK),

                ifTrue(isY5BelowTop, NETHERRACK),

                biomeOverrides(overrides),

                ifTrue(
                        isBiome(Biomes.BASALT_DELTAS),
                        sequence(
                                ifTrue(UNDER_CEILING, BASALT),
                                ifTrue(UNDER_FLOOR, sequence(gravelPatch, ifTrue(isAlternate, BASALT), BLACKSTONE))
                        )
                ),

                ifTrue(
                        isBiome(Biomes.SOUL_SAND_VALLEY),
                        sequence(
                                ifTrue(UNDER_CEILING, sequence(ifTrue(isAlternate, SOUL_SAND), SOUL_SOIL)),
                                ifTrue(UNDER_FLOOR, sequence(gravelPatch, ifTrue(isAlternate, SOUL_SAND), SOUL_SOIL))
                        )
                ),

                ifTrue(
                        ON_FLOOR,
                        sequence(
                                ifTrue(not(isY32), ifTrue(isHole, LAVA)),
                                ifTrue(
                                        isBiome(Biomes.WARPED_FOREST),
                                        ifTrue(
                                                not(isNetherrack),
                                                ifTrue(isY31, sequence(ifTrue(isNetherWart, WARPED_WART_BLOCK), WARPED_NYLIUM))
                                        )
                                ),
                                ifTrue(
                                        isBiome(Biomes.CRIMSON_FOREST),
                                        ifTrue(
                                                not(isNetherrack),
                                                ifTrue(isY31, sequence(ifTrue(isNetherWart, NETHER_WART_BLOCK), CRIMSON_NYLIUM))
                                        )
                                )
                        )
                ),

                ifTrue(
                        isBiome(Biomes.NETHER_WASTES),
                        sequence(
                                ifTrue(
                                        UNDER_FLOOR,
                                        ifTrue(
                                                isSoulSand,
                                                sequence(
                                                        ifTrue(not(isHole), ifTrue(isAboveOrAtY30, ifTrue(isBelowY35, SOUL_SAND))),
                                                        NETHERRACK
                                                )
                                        )
                                ),
                                ifTrue(
                                        ON_FLOOR,
                                        ifTrue(
                                                isY31,
                                                ifTrue(
                                                        isBelowY35,
                                                        ifTrue(
                                                                isGravel,
                                                                sequence(ifTrue(isY32, GRAVEL), ifTrue(not(isHole), GRAVEL))
                                                        )
                                                )
                                        )
                                )
                        )
                ),

                NETHERRACK
        );
    }

    public static RuleSource end(Holder<BiomeSurfaceOverrides> overrides) {
        return sequence(
                biomeOverrides(overrides),
                ENDSTONE
        );
    }

    public static RuleSource air() {
        return AIR;
    }

    private static ConditionSource surfaceNoiseAbove(double value) {
        return noiseCondition(Noises.SURFACE, value / 8.25, Double.MAX_VALUE);
    }
}
