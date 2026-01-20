package net.foxboi.salted.common.levelgen.biome;

import net.foxboi.salted.common.misc.biome.BiomeEditor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;

import static net.foxboi.salted.common.levelgen.biome.ModBiomeFeatures.*;
import static net.foxboi.salted.common.levelgen.biome.VanillaBiomeFeatures.*;

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
    protected void attributes(BiomeEditor builder) {
        super.attributes(builder);

        forestMusic(builder);
    }

    @Override
    protected void generation(BiomeEditor builder) {
        super.generation(builder);
        addDefaultOres(builder);
        addDefaultSoftDisks(builder);

        addForestFlowers(builder);
        addBirchForestFlowers(builder);
        addAspenTrees(builder);

        addBushes(builder);
        addDefaultFlowers(builder);
        addForestGrass(builder);

        addDefaultMushrooms(builder);
        addDefaultExtraVegetation(builder, true);

        addExtraForestGrass(builder);
        addCattail(builder);
    }

    @Override
    protected void spawning(BiomeEditor builder) {
        super.spawning(builder);

        farmAnimals(builder);

        builder.addSpawn(MobCategory.CREATURE, 4, new SpawnerData(EntityType.RABBIT, 2, 3));
        builder.addSpawn(MobCategory.CREATURE, 5, new SpawnerData(EntityType.WOLF, 4, 4));
    }

}
