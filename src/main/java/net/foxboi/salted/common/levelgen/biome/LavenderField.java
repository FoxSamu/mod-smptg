package net.foxboi.salted.common.levelgen.biome;

import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;

import net.foxboi.salted.common.misc.biome.BiomeEditor;

import static net.foxboi.salted.common.levelgen.biome.ModBiomeFeatures.*;
import static net.foxboi.salted.common.levelgen.biome.VanillaBiomeFeatures.*;

public class LavenderField extends OverworldBiome {
    @Override
    protected float temperature() {
        return 0.78f;
    }

    @Override
    protected float downfall() {
        return 0.57f;
    }

    @Override
    protected void attributes(BiomeEditor builder) {
        super.attributes(builder);

        meadowMusic(builder);
        builder.putAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 0xFF172F87);
    }

    @Override
    protected void effects(BiomeEditor builder) {
        super.effects(builder);

        builder.waterColor(0xFF2B51D9);
    }

    @Override
    protected void generation(BiomeEditor builder) {
        super.generation(builder);
        addDefaultOres(builder);
        addDefaultSoftDisks(builder);

        addPlainGrass(builder);

        addAlwaysBeesBirch(builder);

        addLavenderFieldVegetation(builder);
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
