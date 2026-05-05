package net.foxboi.salted.common.levelgen.biome;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.attribute.AmbientParticle;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;

import net.foxboi.salted.common.misc.biome.BiomeEditor;

import static net.foxboi.salted.common.levelgen.biome.ModBiomeFeatures.*;

public class BurnedForest extends NetherBiome {
    @Override
    protected void attributes(BiomeEditor builder) {
        super.attributes(builder);

        basaltDeltasMusic(builder);
        basaltDeltasAmbience(builder);

        builder.putAttribute(EnvironmentAttributes.AMBIENT_PARTICLES, AmbientParticle.of(ParticleTypes.ASH, 0.12f));
        builder.putAttribute(EnvironmentAttributes.FOG_COLOR, 0xFF1C1414);
    }

    @Override
    protected void generation(BiomeEditor builder) {
        super.generation(builder);

        addBurnedStems(builder);
        addEmberPlants(builder);
        addAshDecorations(builder);
        addAshLayers(builder);
    }

    @Override
    protected void spawning(BiomeEditor builder) {
        super.spawning(builder);

        builder.addSpawn(MobCategory.MONSTER, 8, new MobSpawnSettings.SpawnerData(EntityType.MAGMA_CUBE, 2, 2));
        builder.addSpawn(MobCategory.MONSTER, 1, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 4, 4));
        builder.addSpawn(MobCategory.MONSTER, 12, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIFIED_PIGLIN, 4, 4));
        builder.addSpawn(MobCategory.MONSTER, 1, new MobSpawnSettings.SpawnerData(EntityType.GHAST, 1, 1));
        builder.addSpawn(MobCategory.CREATURE, 60, new MobSpawnSettings.SpawnerData(EntityType.STRIDER, 1, 2));
        builder.addMobCharge(EntityType.GHAST, 0.3, 0.3);
    }

}
