package net.foxboi.salted.common.levelgen.biome;

import net.foxboi.salted.common.misc.biome.BiomeEditor;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;

import static net.minecraft.world.level.levelgen.GenerationStep.Decoration.*;

public record VanillaBiomeFeatures() {
    public static void addDefaultCarversAndLakes(BiomeEditor builder) {
        builder.addCarver(Carvers.CAVE);
        builder.addCarver(Carvers.CAVE_EXTRA_UNDERGROUND);
        builder.addCarver(Carvers.CANYON);
        builder.addFeature(LAKES, MiscOverworldPlacements.LAKE_LAVA_UNDERGROUND);
        builder.addFeature(LAKES, MiscOverworldPlacements.LAKE_LAVA_SURFACE);
    }

    public static void addDefaultMonsterRoom(BiomeEditor builder) {
        builder.addFeature(UNDERGROUND_STRUCTURES, CavePlacements.MONSTER_ROOM);
        builder.addFeature(UNDERGROUND_STRUCTURES, CavePlacements.MONSTER_ROOM_DEEP);
    }

    public static void addDefaultUndergroundVariety(BiomeEditor builder) {
        builder.addFeature(UNDERGROUND_ORES, OrePlacements.ORE_DIRT);
        builder.addFeature(UNDERGROUND_ORES, OrePlacements.ORE_GRAVEL);
        builder.addFeature(UNDERGROUND_ORES, OrePlacements.ORE_GRANITE_UPPER);
        builder.addFeature(UNDERGROUND_ORES, OrePlacements.ORE_GRANITE_LOWER);
        builder.addFeature(UNDERGROUND_ORES, OrePlacements.ORE_DIORITE_UPPER);
        builder.addFeature(UNDERGROUND_ORES, OrePlacements.ORE_DIORITE_LOWER);
        builder.addFeature(UNDERGROUND_ORES, OrePlacements.ORE_ANDESITE_UPPER);
        builder.addFeature(UNDERGROUND_ORES, OrePlacements.ORE_ANDESITE_LOWER);
        builder.addFeature(UNDERGROUND_ORES, OrePlacements.ORE_TUFF);
        builder.addFeature(VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
    }

    public static void addDripstone(BiomeEditor builder) {
        builder.addFeature(LOCAL_MODIFICATIONS, CavePlacements.LARGE_DRIPSTONE);
        builder.addFeature(UNDERGROUND_DECORATION, CavePlacements.DRIPSTONE_CLUSTER);
        builder.addFeature(UNDERGROUND_DECORATION, CavePlacements.POINTED_DRIPSTONE);
    }

    public static void addSculk(BiomeEditor builder) {
        builder.addFeature(UNDERGROUND_DECORATION, CavePlacements.SCULK_VEIN);
        builder.addFeature(UNDERGROUND_DECORATION, CavePlacements.SCULK_PATCH_DEEP_DARK);
    }

    public static void addDefaultOres(BiomeEditor builder) {
        addDefaultOres(builder, false);
    }

    public static void addDefaultOres(BiomeEditor builder, boolean largeCopper) {
        builder.addFeature(UNDERGROUND_ORES, OrePlacements.ORE_COAL_UPPER);
        builder.addFeature(UNDERGROUND_ORES, OrePlacements.ORE_COAL_LOWER);
        builder.addFeature(UNDERGROUND_ORES, OrePlacements.ORE_IRON_UPPER);
        builder.addFeature(UNDERGROUND_ORES, OrePlacements.ORE_IRON_MIDDLE);
        builder.addFeature(UNDERGROUND_ORES, OrePlacements.ORE_IRON_SMALL);
        builder.addFeature(UNDERGROUND_ORES, OrePlacements.ORE_GOLD);
        builder.addFeature(UNDERGROUND_ORES, OrePlacements.ORE_GOLD_LOWER);
        builder.addFeature(UNDERGROUND_ORES, OrePlacements.ORE_REDSTONE);
        builder.addFeature(UNDERGROUND_ORES, OrePlacements.ORE_REDSTONE_LOWER);
        builder.addFeature(UNDERGROUND_ORES, OrePlacements.ORE_DIAMOND);
        builder.addFeature(UNDERGROUND_ORES, OrePlacements.ORE_DIAMOND_MEDIUM);
        builder.addFeature(UNDERGROUND_ORES, OrePlacements.ORE_DIAMOND_LARGE);
        builder.addFeature(UNDERGROUND_ORES, OrePlacements.ORE_DIAMOND_BURIED);
        builder.addFeature(UNDERGROUND_ORES, OrePlacements.ORE_LAPIS);
        builder.addFeature(UNDERGROUND_ORES, OrePlacements.ORE_LAPIS_BURIED);
        builder.addFeature(UNDERGROUND_ORES, largeCopper ? OrePlacements.ORE_COPPER_LARGE : OrePlacements.ORE_COPPER);
        builder.addFeature(UNDERGROUND_ORES, CavePlacements.UNDERWATER_MAGMA);
    }

    public static void addExtraGold(BiomeEditor builder) {
        builder.addFeature(UNDERGROUND_ORES, OrePlacements.ORE_GOLD_EXTRA);
    }

    public static void addExtraEmeralds(BiomeEditor builder) {
        builder.addFeature(UNDERGROUND_ORES, OrePlacements.ORE_EMERALD);
    }

    public static void addInfestedStone(BiomeEditor builder) {
        builder.addFeature(UNDERGROUND_DECORATION, OrePlacements.ORE_INFESTED);
    }

    public static void addDefaultSoftDisks(BiomeEditor builder) {
        builder.addFeature(UNDERGROUND_ORES, MiscOverworldPlacements.DISK_SAND);
        builder.addFeature(UNDERGROUND_ORES, MiscOverworldPlacements.DISK_CLAY);
        builder.addFeature(UNDERGROUND_ORES, MiscOverworldPlacements.DISK_GRAVEL);
    }

    public static void addSwampClayDisk(BiomeEditor builder) {
        builder.addFeature(UNDERGROUND_ORES, MiscOverworldPlacements.DISK_CLAY);
    }

    public static void addMangroveSwampDisks(BiomeEditor builder) {
        builder.addFeature(UNDERGROUND_ORES, MiscOverworldPlacements.DISK_GRASS);
        builder.addFeature(UNDERGROUND_ORES, MiscOverworldPlacements.DISK_CLAY);
    }

    public static void addMossyStoneBlock(BiomeEditor builder) {
        builder.addFeature(LOCAL_MODIFICATIONS, MiscOverworldPlacements.FOREST_ROCK);
    }

    public static void addFerns(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_LARGE_FERN);
    }

    public static void addBushes(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_BUSH);
    }

    public static void addRareBerryBushes(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_BERRY_RARE);
    }

    public static void addCommonBerryBushes(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_BERRY_COMMON);
    }

    public static void addLightBambooVegetation(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.BAMBOO_LIGHT);
    }

    public static void addBambooVegetation(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.BAMBOO);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.BAMBOO_VEGETATION);
    }

    public static void addTaigaTrees(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.TREES_TAIGA);
    }

    public static void addGroveTrees(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.TREES_GROVE);
    }

    public static void addWaterTrees(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.TREES_WATER);
    }

    public static void addBirchTrees(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.TREES_BIRCH);
    }

    public static void addOtherBirchTrees(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.TREES_BIRCH_AND_OAK_LEAF_LITTER);
    }

    public static void addTallBirchTrees(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.BIRCH_TALL);
    }

    public static void addBirchForestFlowers(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.WILDFLOWERS_BIRCH_FOREST);
    }

    public static void addSavannaTrees(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.TREES_SAVANNA);
    }

    public static void addShatteredSavannaTrees(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.TREES_WINDSWEPT_SAVANNA);
    }

    public static void addLushCavesVegetationFeatures(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, CavePlacements.LUSH_CAVES_CEILING_VEGETATION);
        builder.addFeature(VEGETAL_DECORATION, CavePlacements.CAVE_VINES);
        builder.addFeature(VEGETAL_DECORATION, CavePlacements.LUSH_CAVES_CLAY);
        builder.addFeature(VEGETAL_DECORATION, CavePlacements.LUSH_CAVES_VEGETATION);
        builder.addFeature(VEGETAL_DECORATION, CavePlacements.ROOTED_AZALEA_TREE);
        builder.addFeature(VEGETAL_DECORATION, CavePlacements.SPORE_BLOSSOM);
        builder.addFeature(VEGETAL_DECORATION, CavePlacements.CLASSIC_VINES);
    }

    public static void addLushCavesSpecialOres(BiomeEditor builder) {
        builder.addFeature(UNDERGROUND_ORES, OrePlacements.ORE_CLAY);
    }

    public static void addMountainTrees(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.TREES_WINDSWEPT_HILLS);
    }

    public static void addMountainForestTrees(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.TREES_WINDSWEPT_FOREST);
    }

    public static void addJungleTrees(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.TREES_JUNGLE);
    }

    public static void addSparseJungleTrees(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.TREES_SPARSE_JUNGLE);
    }

    public static void addBadlandsTrees(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.TREES_BADLANDS);
    }

    public static void addSnowyTrees(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.TREES_SNOWY);
    }

    public static void addJungleGrass(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_JUNGLE);
    }

    public static void addSavannaGrass(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_TALL_GRASS);
    }

    public static void addShatteredSavannaGrass(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_NORMAL);
    }

    public static void addSavannaExtraGrass(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_SAVANNA);
    }

    public static void addBadlandGrass(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_BADLANDS);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_DRY_GRASS_BADLANDS);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH_BADLANDS);
    }

    public static void addForestFlowers(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.FOREST_FLOWERS);
    }

    public static void addForestGrass(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_FOREST);
    }

    public static void addSwampVegetation(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.TREES_SWAMP);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.FLOWER_SWAMP);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_NORMAL);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_WATERLILY);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_SWAMP);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.RED_MUSHROOM_SWAMP);
    }

    public static void addMangroveSwampVegetation(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.TREES_MANGROVE);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_NORMAL);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_WATERLILY);
    }

    public static void addMushroomFieldVegetation(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.MUSHROOM_ISLAND_VEGETATION);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_TAIGA);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.RED_MUSHROOM_TAIGA);
    }

    public static void addPlainVegetation(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.FLOWER_PLAINS);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
    }

    public static void addDesertVegetation(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_DRY_GRASS_DESERT);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH_2);
    }

    public static void addGiantTaigaVegetation(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_TAIGA);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_OLD_GROWTH);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.RED_MUSHROOM_OLD_GROWTH);
    }

    public static void addDefaultFlowers(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.FLOWER_DEFAULT);
    }

    public static void addCherryGroveVegetation(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.FLOWER_CHERRY);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.TREES_CHERRY);
    }

    public static void addMeadowVegetation(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_MEADOW);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.FLOWER_MEADOW);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.TREES_MEADOW);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.WILDFLOWERS_MEADOW);
    }

    public static void addWarmFlowers(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.FLOWER_WARM);
    }

    public static void addDefaultGrass(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_BADLANDS);
    }

    public static void addTaigaGrass(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_TAIGA_2);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_TAIGA);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.RED_MUSHROOM_TAIGA);
    }

    public static void addPlainGrass(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_TALL_GRASS_2);
    }

    public static void addDefaultMushrooms(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_NORMAL);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.RED_MUSHROOM_NORMAL);
    }

    public static void addDefaultExtraVegetation(BiomeEditor builder, boolean nearWaterVegetation) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN);
        if (nearWaterVegetation) {
            addNearWaterVegetation(builder);
        }
    }

    public static void addNearWaterVegetation(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_FIREFLY_BUSH_NEAR_WATER);
    }

    public static void addLeafLitterPatch(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_LEAF_LITTER);
    }

    public static void addBadlandExtraVegetation(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE_BADLANDS);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_CACTUS_DECORATED);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_FIREFLY_BUSH_NEAR_WATER);
    }

    public static void addJungleMelons(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_MELON);
    }

    public static void addSparseJungleMelons(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_MELON_SPARSE);
    }

    public static void addJungleVines(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.VINES);
    }

    public static void addDesertExtraVegetation(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE_DESERT);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_CACTUS_DESERT);
    }

    public static void addSwampExtraVegetation(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE_SWAMP);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_FIREFLY_BUSH_SWAMP);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_FIREFLY_BUSH_NEAR_WATER_SWAMP);
    }

    public static void addMangroveSwampExtraVegetation(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_SWAMP);
        builder.addFeature(VEGETAL_DECORATION, VegetationPlacements.PATCH_FIREFLY_BUSH_NEAR_WATER);
    }

    public static void addDesertExtraDecoration(BiomeEditor builder) {
        builder.addFeature(SURFACE_STRUCTURES, MiscOverworldPlacements.DESERT_WELL);
    }

    public static void addFossilDecoration(BiomeEditor builder) {
        builder.addFeature(UNDERGROUND_STRUCTURES, CavePlacements.FOSSIL_UPPER);
        builder.addFeature(UNDERGROUND_STRUCTURES, CavePlacements.FOSSIL_LOWER);
    }

    public static void addColdOceanExtraVegetation(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, AquaticPlacements.KELP_COLD);
    }

    public static void addLukeWarmKelp(BiomeEditor builder) {
        builder.addFeature(VEGETAL_DECORATION, AquaticPlacements.KELP_WARM);
    }

    public static void addDefaultSprings(BiomeEditor builder) {
        builder.addFeature(FLUID_SPRINGS, MiscOverworldPlacements.SPRING_WATER);
        builder.addFeature(FLUID_SPRINGS, MiscOverworldPlacements.SPRING_LAVA);
    }

    public static void addFrozenSprings(BiomeEditor builder) {
        builder.addFeature(FLUID_SPRINGS, MiscOverworldPlacements.SPRING_LAVA_FROZEN);
    }

    public static void addIcebergs(BiomeEditor builder) {
        builder.addFeature(LOCAL_MODIFICATIONS, MiscOverworldPlacements.ICEBERG_PACKED);
        builder.addFeature(LOCAL_MODIFICATIONS, MiscOverworldPlacements.ICEBERG_BLUE);
    }

    public static void addBlueIce(BiomeEditor builder) {
        builder.addFeature(SURFACE_STRUCTURES, MiscOverworldPlacements.BLUE_ICE);
    }

    public static void addSurfaceFreezing(BiomeEditor builder) {
        builder.addFeature(TOP_LAYER_MODIFICATION, MiscOverworldPlacements.FREEZE_TOP_LAYER);
    }

    public static void addNetherDefaultOres(BiomeEditor builder) {
        builder.addFeature(UNDERGROUND_DECORATION, OrePlacements.ORE_GRAVEL_NETHER);
        builder.addFeature(UNDERGROUND_DECORATION, OrePlacements.ORE_BLACKSTONE);
        builder.addFeature(UNDERGROUND_DECORATION, OrePlacements.ORE_GOLD_NETHER);
        builder.addFeature(UNDERGROUND_DECORATION, OrePlacements.ORE_QUARTZ_NETHER);
        addAncientDebris(builder);
    }

    public static void addAncientDebris(BiomeEditor builder) {
        builder.addFeature(UNDERGROUND_DECORATION, OrePlacements.ORE_ANCIENT_DEBRIS_LARGE);
        builder.addFeature(UNDERGROUND_DECORATION, OrePlacements.ORE_ANCIENT_DEBRIS_SMALL);
    }

    public static void addDefaultCrystalFormations(BiomeEditor builder) {
        builder.addFeature(LOCAL_MODIFICATIONS, CavePlacements.AMETHYST_GEODE);
    }

    public static void farmAnimals(BiomeEditor builder) {
        builder.addSpawn(MobCategory.CREATURE, 12, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 4, 4));
        builder.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityType.PIG, 4, 4));
        builder.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 4, 4));
        builder.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.COW, 4, 4));
    }

    public static void caveSpawns(BiomeEditor builder) {
        builder.addSpawn(MobCategory.AMBIENT, 10, new MobSpawnSettings.SpawnerData(EntityType.BAT, 8, 8));
        builder.addSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityType.GLOW_SQUID, 4, 6));
    }

    public static void commonSpawns(BiomeEditor builder) {
        commonSpawns(builder, 100);
    }

    public static void commonSpawns(BiomeEditor builder, int i) {
        caveSpawns(builder);
        monsters(builder, 95, 5, i, false);
    }

    public static void oceanSpawns(BiomeEditor builder, int squidWeight, int maxSquidCount, int fishWeight) {
        builder.addSpawn(MobCategory.WATER_CREATURE, squidWeight, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 1, maxSquidCount));
        builder.addSpawn(MobCategory.WATER_AMBIENT, fishWeight, new MobSpawnSettings.SpawnerData(EntityType.COD, 3, 6));
        commonSpawns(builder);
        builder.addSpawn(MobCategory.MONSTER, 5, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 1, 1));
    }

    public static void warmOceanSpawns(BiomeEditor builder, int squidWeight, int minSquidCount) {
        builder.addSpawn(MobCategory.WATER_CREATURE, squidWeight, new MobSpawnSettings.SpawnerData(EntityType.SQUID, minSquidCount, 4));
        builder.addSpawn(MobCategory.WATER_AMBIENT, 25, new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH, 8, 8));
        builder.addSpawn(MobCategory.WATER_CREATURE, 2, new MobSpawnSettings.SpawnerData(EntityType.DOLPHIN, 1, 2));
        builder.addSpawn(MobCategory.MONSTER, 5, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 1, 1));
        commonSpawns(builder);
    }

    public static void plainsSpawns(BiomeEditor builder) {
        farmAnimals(builder);
        builder.addSpawn(MobCategory.CREATURE, 5, new MobSpawnSettings.SpawnerData(EntityType.HORSE, 2, 6));
        builder.addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(EntityType.DONKEY, 1, 3));
        commonSpawns(builder);
    }

    public static void snowySpawns(BiomeEditor builder) {
        builder.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 3));
        builder.addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(EntityType.POLAR_BEAR, 1, 2));
        caveSpawns(builder);
        monsters(builder, 95, 5, 20, false);
        builder.addSpawn(MobCategory.MONSTER, 80, new MobSpawnSettings.SpawnerData(EntityType.STRAY, 4, 4));
    }

    public static void desertSpawns(BiomeEditor builder) {
        builder.addSpawn(MobCategory.CREATURE, 12, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 3));
        builder.addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(EntityType.CAMEL, 1, 1));
        caveSpawns(builder);
        monsters(builder, 19, 1, 100, false);
        builder.addSpawn(MobCategory.MONSTER, 80, new MobSpawnSettings.SpawnerData(EntityType.HUSK, 4, 4));
    }

    public static void dripstoneCavesSpawns(BiomeEditor builder) {
        caveSpawns(builder);
        monsters(builder, 95, 5, 100, false);
        builder.addSpawn(MobCategory.MONSTER, 95, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 4, 4));
    }

    public static void monsters(BiomeEditor builder, int zombieWeight, int villagerZombieWeight, int skeletonWeight, boolean drowned) {
        builder.addSpawn(MobCategory.MONSTER, 100, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 4, 4));
        builder.addSpawn(MobCategory.MONSTER, zombieWeight, new MobSpawnSettings.SpawnerData(drowned ? EntityType.DROWNED : EntityType.ZOMBIE, 4, 4));
        builder.addSpawn(MobCategory.MONSTER, villagerZombieWeight, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE_VILLAGER, 1, 1));
        builder.addSpawn(MobCategory.MONSTER, skeletonWeight, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 4, 4));
        builder.addSpawn(MobCategory.MONSTER, 100, new MobSpawnSettings.SpawnerData(EntityType.CREEPER, 4, 4));
        builder.addSpawn(MobCategory.MONSTER, 100, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 4, 4));
        builder.addSpawn(MobCategory.MONSTER, 10, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 1, 4));
        builder.addSpawn(MobCategory.MONSTER, 5, new MobSpawnSettings.SpawnerData(EntityType.WITCH, 1, 1));
    }

    public static void mooshroomSpawns(BiomeEditor builder) {
        builder.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.MOOSHROOM, 4, 8));
        caveSpawns(builder);
    }

    public static void baseJungleSpawns(BiomeEditor builder) {
        farmAnimals(builder);
        builder.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 4, 4));
        commonSpawns(builder);
    }

    public static void endSpawns(BiomeEditor builder) {
        builder.addSpawn(MobCategory.MONSTER, 10, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 4, 4));
    }
}
