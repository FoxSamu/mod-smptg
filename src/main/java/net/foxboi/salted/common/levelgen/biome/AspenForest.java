package net.foxboi.salted.common.levelgen.biome;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;

import static net.foxboi.salted.common.levelgen.biome.ModBiomeDefaultFeatures.*;
import static net.minecraft.data.worldgen.BiomeDefaultFeatures.*;

public class AspenForest extends OverworldBiome {
    @Override
    protected float temperature() {
        return 0.6f;
    }

    @Override
    protected float downfall() {
        return 0.6f;
    }

    @Override
    protected void effects(BiomeSpecialEffects.Builder builder) {
        super.effects(builder);

        forestMusic(builder);
    }

    @Override
    protected void generation(BiomeGenerationSettings.Builder builder) {
        super.generation(builder);

        addForestFlowers(builder);
        addBirchForestFlowers(builder);
        addAspenTrees(builder);

        addBushes(builder);
        addDefaultFlowers(builder);
        addForestGrass(builder);
        addExtraForestGrass(builder);
        addCattail(builder);

        addDefaultMushrooms(builder);
        addDefaultExtraVegetation(builder, true);
    }

    @Override
    protected void spawning(MobSpawnSettings.Builder builder) {
        super.spawning(builder);

        farmAnimals(builder);

        builder.addSpawn(MobCategory.CREATURE, 4, new SpawnerData(EntityType.RABBIT, 2, 3));
        builder.addSpawn(MobCategory.CREATURE, 5, new SpawnerData(EntityType.WOLF, 4, 4));
    }

}
