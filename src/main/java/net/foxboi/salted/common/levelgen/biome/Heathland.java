package net.foxboi.salted.common.levelgen.biome;

import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;

import net.foxboi.salted.common.misc.biome.BiomeEditor;

import static net.foxboi.salted.common.levelgen.biome.ModBiomeFeatures.*;
import static net.foxboi.salted.common.levelgen.biome.VanillaBiomeFeatures.*;

public class Heathland extends OverworldBiome {
    @Override
    protected float temperature() {
        return 0.8f;
    }

    @Override
    protected float downfall() {
        return 0.2f;
    }

    @Override
    protected void attributes(BiomeEditor builder) {
        super.attributes(builder);

        meadowMusic(builder);
        builder.putAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 0xFF0B2040);
    }

    @Override
    protected void effects(BiomeEditor builder) {
        super.effects(builder);

        builder.waterColor(0xFF24455C);
        builder.grassColor(0xFFA69C67);
        builder.foliageColor(0xFF337D3E);
        builder.dryFoliageColor(0xFF736646);
    }

    @Override
    protected void generation(BiomeEditor builder) {
        super.generation(builder);
        addDefaultOres(builder);
        addDefaultSoftDisks(builder);
        addPuddles(builder);

        addAlwaysBeesTrees(builder);

        addHeathlandVegetation(builder);
        addCattail(builder);
    }

    @Override
    protected void spawning(BiomeEditor builder) {
        swampSpawns(builder, 100);

        farmAnimals(builder);

        builder.addSpawn(MobCategory.CREATURE, 24, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 6, 8));
        builder.addSpawn(MobCategory.CREATURE, 5, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 3));
    }

}
