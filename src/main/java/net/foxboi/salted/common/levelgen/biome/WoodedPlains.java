package net.foxboi.salted.common.levelgen.biome;

import net.foxboi.salted.common.misc.biome.BiomeEditor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;

import static net.foxboi.salted.common.levelgen.biome.ModBiomeFeatures.*;
import static net.foxboi.salted.common.levelgen.biome.VanillaBiomeFeatures.*;

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
    protected void effects(BiomeEditor builder) {
        super.effects(builder);
    }

    @Override
    protected void generation(BiomeEditor builder) {
        super.generation(builder);
        addDefaultOres(builder);
        addDefaultSoftDisks(builder);

        addPlainGrass(builder);
        addBushes(builder);

        addWoodedPlainsVegetation(builder);

        addDefaultMushrooms(builder);
        addDefaultExtraVegetation(builder, true);

        addExtraPlainsGrass(builder);
        addCattail(builder);
    }

    @Override
    protected void spawning(BiomeEditor builder) {
        super.spawning(builder);

        farmAnimals(builder);

        builder.addSpawn(MobCategory.CREATURE, 6, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 4, 4));
        builder.addSpawn(MobCategory.CREATURE, 5, new MobSpawnSettings.SpawnerData(EntityType.HORSE, 2, 6));
        builder.addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(EntityType.DONKEY, 1, 3));
    }

}
