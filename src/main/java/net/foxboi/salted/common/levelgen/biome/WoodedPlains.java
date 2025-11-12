package net.foxboi.salted.common.levelgen.biome;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;

import static net.foxboi.salted.common.levelgen.biome.ModBiomeDefaultFeatures.*;
import static net.minecraft.data.worldgen.BiomeDefaultFeatures.*;

public class WoodedPlains extends OverworldBiome {
    @Override
    protected float temperature() {
        return 0.75f;
    }

    @Override
    protected float downfall() {
        return 0.5f;
    }

    @Override
    protected void effects(BiomeSpecialEffects.Builder builder) {
        super.effects(builder);
    }

    @Override
    protected void generation(BiomeGenerationSettings.Builder builder) {
        super.generation(builder);

        addPlainGrass(builder);
        addBushes(builder);

        addWoodedPlainsVegetation(builder);
        addExtraPlainsGrass(builder);
        addCattail(builder);

        addDefaultMushrooms(builder);
        addDefaultExtraVegetation(builder, true);
    }

    @Override
    protected void spawning(MobSpawnSettings.Builder builder) {
        super.spawning(builder);

        farmAnimals(builder);

        builder.addSpawn(MobCategory.CREATURE, 6, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 4, 4));
        builder.addSpawn(MobCategory.CREATURE, 5, new MobSpawnSettings.SpawnerData(EntityType.HORSE, 2, 6));
        builder.addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(EntityType.DONKEY, 1, 3));
    }

}
