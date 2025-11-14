package net.foxboi.salted.common.levelgen.biome;

import net.foxboi.salted.common.misc.biome.BiomeEditor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;

import static net.foxboi.salted.common.levelgen.biome.ModBiomeFeatures.*;
import static net.foxboi.salted.common.levelgen.biome.VanillaBiomeFeatures.*;

public class RedwoodTaiga extends OverworldBiome {
    private final boolean sparse;

    public RedwoodTaiga(boolean sparse) {
        this.sparse = sparse;
    }

    @Override
    protected float temperature() {
        return 0.3f;
    }

    @Override
    protected float downfall() {
        return 0.7f;
    }

    @Override
    protected void effects(BiomeEditor builder) {
        super.effects(builder);

        oldGrowthTaigaMusic(builder);
        taigaColors(builder);
    }

    @Override
    protected void generation(BiomeEditor builder) {
        super.generation(builder);

        addMossyStoneBlock(builder);

        if (sparse) {
            addSparseRedwoodTrees(builder);
        } else {
            addRedwoodTrees(builder);
        }

        addMossCarpetsAroundMoss(builder);
        addFerns(builder);
        addDefaultOres(builder);
        addDefaultSoftDisks(builder);
        addDefaultFlowers(builder);
        addForestGrass(builder);
        addGiantTaigaVegetation(builder);
        addDefaultMushrooms(builder);
        addDefaultExtraVegetation(builder, true);
        addCommonBerryBushes(builder);

        addExtraForestGrass(builder);
    }

    @Override
    protected void spawning(BiomeEditor builder) {
        super.spawning(builder);

        farmAnimals(builder);

        builder.addSpawn(MobCategory.CREATURE, 4, new SpawnerData(EntityType.RABBIT, 2, 3));
        builder.addSpawn(MobCategory.CREATURE, 10, new SpawnerData(EntityType.FOX, 2, 3));
    }

}
