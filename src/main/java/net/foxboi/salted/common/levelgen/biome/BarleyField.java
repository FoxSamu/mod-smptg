package net.foxboi.salted.common.levelgen.biome;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;

import static net.foxboi.salted.common.levelgen.biome.ModBiomeDefaultFeatures.*;
import static net.minecraft.data.worldgen.BiomeDefaultFeatures.*;

public class BarleyField extends OverworldBiome {
    @Override
    protected float temperature() {
        return 0.75f;
    }

    @Override
    protected float downfall() {
        return 0.37f;
    }

    @Override
    protected void effects(BiomeSpecialEffects.Builder builder) {
        super.effects(builder);
    }

    @Override
    protected void generation(BiomeGenerationSettings.Builder builder) {
        super.generation(builder);

        addCattail(builder);
        addBarleyFieldVegetation(builder);

        addDefaultMushrooms(builder);
        addDefaultExtraVegetation(builder, true);
    }

    @Override
    protected void spawning(MobSpawnSettings.Builder builder) {
        super.spawning(builder);

        farmAnimals(builder);

        builder.creatureGenerationProbability(0.3f);
        builder.addSpawn(MobCategory.CREATURE, 6, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 4, 4));
    }

}
