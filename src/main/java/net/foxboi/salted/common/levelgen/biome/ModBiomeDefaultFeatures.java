package net.foxboi.salted.common.levelgen.biome;

import net.foxboi.salted.common.levelgen.ModVegetationPlacements;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.levelgen.GenerationStep;

import static net.minecraft.data.worldgen.BiomeDefaultFeatures.*;
import static net.minecraft.world.level.levelgen.GenerationStep.Decoration.*;

public record ModBiomeDefaultFeatures() {
    public static final int NORMAL_WATER_COLOR = 0x3F76E4;
    public static final int NORMAL_WATER_FOG_COLOR = 0x050533;
    public static final int OVERWORLD_FOG_COLOR = 0xC0D8FF;
    public static final int DARK_DRY_FOLIAGE_COLOR = 0x7B5334;
    public static final Music NORMAL_MUSIC = null;

    public static void defaultEffects(BiomeSpecialEffects.Builder builder, float temperature) {
        builder
                .waterColor(NORMAL_WATER_COLOR)
                .waterFogColor(NORMAL_WATER_FOG_COLOR)
                .fogColor(OVERWORLD_FOG_COLOR)
                .skyColor(OverworldBiomes.calculateSkyColor(temperature))
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .backgroundMusic(NORMAL_MUSIC);
    }

    public static void forestMusic(BiomeSpecialEffects.Builder builder) {
        builder.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FOREST));
    }



    public static void addGlobalGeneration(BiomeGenerationSettings.Builder builder) {
        addDefaultCarversAndLakes(builder);
        addDefaultCrystalFormations(builder);
        addDefaultMonsterRoom(builder);
        addDefaultUndergroundVariety(builder);
        addDefaultSprings(builder);
        addSurfaceFreezing(builder);
    }

    public static void addAspenTrees(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.TREES_ASPEN_FOREST);
    }

    public static void addMapleTrees(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.TREES_MAPLE_FOREST);
    }

    public static void addWoodedPlainsVegetation(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.TREES_WOODED_PLAINS);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.FLOWER_PLAINS);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
    }

    public static void addExtraForestGrass(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_GRASS_SPROUTS);
    }

    public static void addExtraPlainsGrass(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_GRASS_SPROUTS_EXTRA_COMMON);
    }

    public static void addBarleyFieldVegetation(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS);
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.VEGETATION_BARLEY_FIELD);
    }

    public static void addCattail(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_CATTAIL_NEAR_WATER);
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_CATTAIL_IN_WATER);
    }

    public static void addRareClovers(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_CLOVERS_RARE);
    }

    public static void addForestClovers(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_CLOVERS);
    }

    public static void addPlainsClovers(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_CLOVERS_DENSE);
    }

    public static void addMossCarpetsAroundMoss(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_MOSS_CARPET_AROUND_MOSS);
    }
}
