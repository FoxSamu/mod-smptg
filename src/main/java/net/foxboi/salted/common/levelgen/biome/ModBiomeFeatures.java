package net.foxboi.salted.common.levelgen.biome;

import net.foxboi.salted.common.levelgen.ModVegetationPlacements;
import net.foxboi.salted.common.misc.biome.BiomeEditor;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.biome.AmbientMoodSettings;

import static net.foxboi.salted.common.levelgen.biome.VanillaBiomeFeatures.*;
import static net.minecraft.world.level.levelgen.GenerationStep.Decoration.*;

public record ModBiomeFeatures() {
    public static final int NORMAL_WATER_COLOR = 0x3F76E4;
    public static final int NORMAL_WATER_FOG_COLOR = 0x050533;
    public static final int OVERWORLD_FOG_COLOR = 0xC0D8FF;
    public static final int DARK_DRY_FOLIAGE_COLOR = 0x7B5334;
    public static final Music NORMAL_MUSIC = null;

    public static void defaultEffects(BiomeEditor builder, float temperature) {
        builder
                .waterColor(NORMAL_WATER_COLOR)
                .waterFogColor(NORMAL_WATER_FOG_COLOR)
                .fogColor(OVERWORLD_FOG_COLOR)
                .skyColor(OverworldBiomes.calculateSkyColor(temperature))
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .backgroundMusic(NORMAL_MUSIC);
    }

    public static void forestMusic(BiomeEditor builder) {
        builder.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FOREST));
    }

    public static void oldGrowthTaigaMusic(BiomeEditor builder) {
        builder.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_OLD_GROWTH_TAIGA));
    }

    public static void cherryGroveMusic(BiomeEditor builder) {
        builder.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_CHERRY_GROVE));
    }

    public static void flowerForestMusic(BiomeEditor builder) {
        builder.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FLOWER_FOREST));
    }

    public static void desertMusic(BiomeEditor builder) {
        builder.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DESERT));
    }

    public static void badlandsMusic(BiomeEditor builder) {
        builder.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_BADLANDS));
    }

    public static void jungleMusic(BiomeEditor builder) {
        builder.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JUNGLE));
    }

    public static void sparseJungleMusic(BiomeEditor builder) {
        builder.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SPARSE_JUNGLE));
    }

    public static void bambooJungleMusic(BiomeEditor builder) {
        builder.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_BAMBOO_JUNGLE));
    }

    public static void groveMusic(BiomeEditor builder) {
        builder.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_GROVE));
    }

    public static void frozenPeaksMusic(BiomeEditor builder) {
        builder.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FROZEN_PEAKS));
    }

    public static void jaggedPeaksMusic(BiomeEditor builder) {
        builder.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JAGGED_PEAKS));
    }

    public static void snowySlopesMusic(BiomeEditor builder) {
        builder.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SNOWY_SLOPES));
    }

    public static void stonyPeaksMusic(BiomeEditor builder) {
        builder.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_STONY_PEAKS));
    }

    public static void meadowMusic(BiomeEditor builder) {
        builder.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_MEADOW));
    }

    public static void swampMusic(BiomeEditor builder) {
        builder.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SWAMP));
    }

    public static void lushCavesMusic(BiomeEditor builder) {
        builder.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_LUSH_CAVES));
    }

    public static void dripstoneCavesMusic(BiomeEditor builder) {
        builder.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DRIPSTONE_CAVES));
    }

    public static void deepDarkMusic(BiomeEditor builder) {
        builder.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DEEP_DARK));
    }

    public static void soulSandValleyMusic(BiomeEditor builder) {
        builder.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SOUL_SAND_VALLEY));
    }

    public static void crimsonForestMusic(BiomeEditor builder) {
        builder.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST));
    }

    public static void warpedForestMusic(BiomeEditor builder) {
        builder.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_WARPED_FOREST));
    }

    public static void basaltDeltasMusic(BiomeEditor builder) {
        builder.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_BASALT_DELTAS));
    }

    public static void netherWastesMusic(BiomeEditor builder) {
        builder.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_NETHER_WASTES));
    }

    public static void meadowColors(BiomeEditor builder) {
        builder.waterColor(0x0E4ECF);
    }

    public static void taigaColors(BiomeEditor builder) {
        builder.waterColor(0x416DBF);
    }



    public static void addGlobalGeneration(BiomeEditor builder) {
        addDefaultCarversAndLakes(builder);
        addDefaultCrystalFormations(builder);
        addDefaultMonsterRoom(builder);
        addDefaultUndergroundVariety(builder);
        addDefaultSprings(builder);
        addSurfaceFreezing(builder);
    }

    public static void addAspenTrees(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.TREES_ASPEN_FOREST);
    }

    public static void addSparseRedwoodTrees(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.TREES_REDWOOD_FOREST_SPARSE);
    }

    public static void addRedwoodTrees(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.TREES_REDWOOD_FOREST_GIANT);
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.TREES_REDWOOD_FOREST_SMALL);
    }

    public static void addMapleTrees(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.TREES_MAPLE_FOREST);
    }

    public static void addWoodedPlainsVegetation(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.TREES_WOODED_PLAINS);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.FLOWER_PLAINS);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
    }

    public static void addExtraForestGrass(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_GRASS_SPROUTS);
    }

    public static void addExtraPlainsGrass(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_GRASS_SPROUTS_EXTRA_COMMON);
    }

    public static void addCommonFireflyBushes(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_FIREFLY_BUSH_COMMON);
    }

    public static void addBarleyFieldVegetation(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS);
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.VEGETATION_BARLEY_FIELD);
    }

    public static void addCattail(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_CATTAIL_NEAR_WATER);
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_CATTAIL_IN_WATER);
    }

    public static void addRareClovers(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_CLOVERS_RARE);
    }

    public static void addForestClovers(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_CLOVERS);
    }

    public static void addPlainsClovers(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_CLOVERS_DENSE);
    }

    public static void addMossCarpetsAroundMoss(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_MOSS_CARPET_AROUND_MOSS);
    }
}
