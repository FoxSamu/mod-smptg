package net.foxboi.salted.common.levelgen.biome;

import java.util.List;
import java.util.Optional;

import net.foxboi.salted.common.levelgen.ModVegetationPlacements;
import net.foxboi.salted.common.misc.biome.BiomeEditor;

import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.attribute.*;

import static net.foxboi.salted.common.levelgen.biome.VanillaBiomeFeatures.*;
import static net.minecraft.world.level.levelgen.GenerationStep.Decoration.*;

public record ModBiomeFeatures() {
    public static final int NORMAL_WATER_COLOR = 0x3F76E4;
    public static final int NORMAL_WATER_FOG_COLOR = 0x050533;
    public static final int OVERWORLD_FOG_COLOR = 0xC0D8FF;
    public static final int DARK_DRY_FOLIAGE_COLOR = 0x7B5334;
    public static final Music NORMAL_MUSIC = null;

    public static void defaultAttributes(BiomeEditor editor, float temperature) {
        editor.putAttribute(EnvironmentAttributes.SKY_COLOR, OverworldBiomes.calculateSkyColor(temperature));
    }

    public static void defaultEffects(BiomeEditor builder) {
        builder.waterColor(NORMAL_WATER_COLOR);
    }
    
    private static void overworldMusic(BiomeEditor builder, Music music) {
        builder.putAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(
                Optional.of(music),
                Optional.of(Musics.CREATIVE),
                Optional.empty()
        ));
    }

    private static void netherMusic(BiomeEditor builder, Music music) {
        builder.putAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(
                Optional.of(music),
                Optional.empty(),
                Optional.empty()
        ));
    }

    public static void forestMusic(BiomeEditor builder) {
        overworldMusic(builder, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FOREST));
    }

    public static void oldGrowthTaigaMusic(BiomeEditor builder) {
        overworldMusic(builder, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_OLD_GROWTH_TAIGA));
    }

    public static void cherryGroveMusic(BiomeEditor builder) {
        overworldMusic(builder, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_CHERRY_GROVE));
    }

    public static void flowerForestMusic(BiomeEditor builder) {
        overworldMusic(builder, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FLOWER_FOREST));
    }

    public static void desertMusic(BiomeEditor builder) {
        overworldMusic(builder, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DESERT));
    }

    public static void badlandsMusic(BiomeEditor builder) {
        overworldMusic(builder, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_BADLANDS));
    }

    public static void jungleMusic(BiomeEditor builder) {
        overworldMusic(builder, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JUNGLE));
    }

    public static void sparseJungleMusic(BiomeEditor builder) {
        overworldMusic(builder, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SPARSE_JUNGLE));
    }

    public static void bambooJungleMusic(BiomeEditor builder) {
        overworldMusic(builder, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_BAMBOO_JUNGLE));
    }

    public static void groveMusic(BiomeEditor builder) {
        overworldMusic(builder, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_GROVE));
    }

    public static void frozenPeaksMusic(BiomeEditor builder) {
        overworldMusic(builder, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FROZEN_PEAKS));
    }

    public static void jaggedPeaksMusic(BiomeEditor builder) {
        overworldMusic(builder, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JAGGED_PEAKS));
    }

    public static void snowySlopesMusic(BiomeEditor builder) {
        overworldMusic(builder, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SNOWY_SLOPES));
    }

    public static void stonyPeaksMusic(BiomeEditor builder) {
        overworldMusic(builder, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_STONY_PEAKS));
    }

    public static void meadowMusic(BiomeEditor builder) {
        overworldMusic(builder, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_MEADOW));
    }

    public static void swampMusic(BiomeEditor builder) {
        overworldMusic(builder, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SWAMP));
    }

    public static void lushCavesMusic(BiomeEditor builder) {
        overworldMusic(builder, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_LUSH_CAVES));
    }

    public static void dripstoneCavesMusic(BiomeEditor builder) {
        overworldMusic(builder, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DRIPSTONE_CAVES));
    }

    public static void deepDarkMusic(BiomeEditor builder) {
        overworldMusic(builder, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DEEP_DARK));
    }

    public static void soulSandValleyMusic(BiomeEditor builder) {
        netherMusic(builder, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SOUL_SAND_VALLEY));
    }

    public static void crimsonForestMusic(BiomeEditor builder) {
        netherMusic(builder, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST));
    }

    public static void warpedForestMusic(BiomeEditor builder) {
        netherMusic(builder, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_WARPED_FOREST));
    }

    public static void basaltDeltasMusic(BiomeEditor builder) {
        netherMusic(builder, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_BASALT_DELTAS));
    }

    public static void netherWastesMusic(BiomeEditor builder) {
        netherMusic(builder, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_NETHER_WASTES));
    }

    public static void meadowColors(BiomeEditor builder) {
        builder.waterColor(0x0E4ECF);
    }

    public static void taigaColors(BiomeEditor builder) {
        builder.waterColor(0x416DBF);
    }

    public static void netherWastesAmbience(BiomeEditor builder) {
        builder.putAttribute(
                EnvironmentAttributes.AMBIENT_SOUNDS,
                new AmbientSounds(
                        Optional.of(SoundEvents.AMBIENT_NETHER_WASTES_LOOP),
                        Optional.of(new AmbientMoodSettings(SoundEvents.AMBIENT_NETHER_WASTES_MOOD, 6000, 8, 2)),
                        List.of(new AmbientAdditionsSettings(SoundEvents.AMBIENT_NETHER_WASTES_ADDITIONS, 0.0111))
                )
        );
    }

    public static void soulSandValleyAmbience(BiomeEditor builder) {
        builder.putAttribute(
                EnvironmentAttributes.AMBIENT_SOUNDS,
                new AmbientSounds(
                        Optional.of(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_LOOP),
                        Optional.of(new AmbientMoodSettings(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD, 6000, 8, 2)),
                        List.of(new AmbientAdditionsSettings(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS, 0.0111))
                )
        );
    }

    public static void basaltDeltasAmbience(BiomeEditor builder) {
        builder.putAttribute(
                EnvironmentAttributes.AMBIENT_SOUNDS,
                new AmbientSounds(
                        Optional.of(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP),
                        Optional.of(new AmbientMoodSettings(SoundEvents.AMBIENT_BASALT_DELTAS_MOOD, 6000, 8, 2)),
                        List.of(new AmbientAdditionsSettings(SoundEvents.AMBIENT_BASALT_DELTAS_ADDITIONS, 0.0111))
                )
        );
    }

    public static void crimsonForestAmbience(BiomeEditor builder) {
        builder.putAttribute(
                EnvironmentAttributes.AMBIENT_SOUNDS,
                new AmbientSounds(
                        Optional.of(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP),
                        Optional.of(new AmbientMoodSettings(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD, 6000, 8, 2)),
                        List.of(new AmbientAdditionsSettings(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS, 0.0111))
                )
        );
    }

    public static void warpedForestAmbience(BiomeEditor builder) {
        builder.putAttribute(
                EnvironmentAttributes.AMBIENT_SOUNDS,
                new AmbientSounds(
                        Optional.of(SoundEvents.AMBIENT_WARPED_FOREST_LOOP),
                        Optional.of(new AmbientMoodSettings(SoundEvents.AMBIENT_WARPED_FOREST_MOOD, 6000, 8, 2)),
                        List.of(new AmbientAdditionsSettings(SoundEvents.AMBIENT_WARPED_FOREST_ADDITIONS, 0.0111))
                )
        );
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

    public static void addNetherGlobalGeneration(BiomeEditor builder) {
        builder.addCarver(Carvers.NETHER_CAVE);
        builder.addFeature(VEGETAL_DECORATION, MiscOverworldPlacements.SPRING_LAVA);

        addDefaultMushrooms(builder);
        addNetherDefaultOres(builder);
    }

    public static void addAshDecorations(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_ASHCREEP);
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_ASHVINE);
        builder.addFeature(UNDERGROUND_DECORATION, ModVegetationPlacements.PATCH_ASH_FIRE);
    }

    public static void addEmberPlants(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_EMBERGRASS);
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_EMBERWEED);
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_EMBERS);
    }

    public static void addBurnedStems(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.BURNED_STEM);
    }

    public static void addAshLayers(BiomeEditor builder) {
        builder.addFeature(TOP_LAYER_MODIFICATION, ModVegetationPlacements.PATCH_ASH_LAYER);
    }

    public static void addNetherFire(BiomeEditor builder) {
        builder.addFeature(UNDERGROUND_DECORATION, NetherPlacements.PATCH_FIRE);
        builder.addFeature(UNDERGROUND_DECORATION, NetherPlacements.PATCH_SOUL_FIRE);
    }
}
