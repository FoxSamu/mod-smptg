package net.foxboi.salted.common.levelgen.biome;

import net.foxboi.salted.common.misc.biome.BiomeEditor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;

import static net.foxboi.salted.common.levelgen.biome.ModBiomeFeatures.*;
import static net.foxboi.salted.common.levelgen.biome.VanillaBiomeFeatures.*;

public class FireflyMeadow extends OverworldBiome {
    @Override
    protected float temperature() {
        return 0.5f;
    }

    @Override
    protected float downfall() {
        return 0.8f;
    }

    @Override
    protected void attributes(BiomeEditor builder) {
        super.attributes(builder);

        meadowMusic(builder);
    }

    @Override
    protected void effects(BiomeEditor builder) {
        super.effects(builder);

        meadowColors(builder);
    }

    @Override
    protected void generation(BiomeEditor builder) {
        super.generation(builder);
        addPlainGrass(builder);
        addDefaultOres(builder);
        addDefaultSoftDisks(builder);

        addBushes(builder);

        addMeadowVegetation(builder);
        addCommonFireflyBushes(builder);

        addExtraEmeralds(builder);
        addInfestedStone(builder);

        addExtraPlainsGrass(builder);
        addCattail(builder);
    }

    @Override
    protected void spawning(BiomeEditor builder) {
        super.spawning(builder);

        builder.addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(EntityType.COW, 1, 2));
        builder.addSpawn(MobCategory.CREATURE, 2, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 6));
        builder.addSpawn(MobCategory.CREATURE, 2, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 2, 4));
    }

}
