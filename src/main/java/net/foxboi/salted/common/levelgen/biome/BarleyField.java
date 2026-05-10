package net.foxboi.salted.common.levelgen.biome;

import net.foxboi.salted.common.misc.biome.BiomeEditor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;

import static net.foxboi.salted.common.levelgen.biome.ModBiomeFeatures.*;
import static net.foxboi.salted.common.levelgen.biome.VanillaBiomeFeatures.*;

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
    protected void attributes(BiomeEditor builder) {
        super.attributes(builder);
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

        addBarleyFieldVegetation(builder);

        addDefaultMushrooms(builder);
        addDefaultExtraVegetation(builder, true);

        addCattail(builder);
    }

    @Override
    protected void spawning(BiomeEditor builder) {
        super.spawning(builder);

        farmAnimals(builder);

        builder.creatureGenerationProbability(0.3f);
        builder.addSpawn(MobCategory.CREATURE, 6, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 4, 4));
    }

}
