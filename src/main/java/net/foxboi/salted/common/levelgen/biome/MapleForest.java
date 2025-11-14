package net.foxboi.salted.common.levelgen.biome;

import net.foxboi.salted.common.misc.biome.BiomeEditor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;

import static net.foxboi.salted.common.levelgen.biome.ModBiomeFeatures.*;
import static net.foxboi.salted.common.levelgen.biome.VanillaBiomeFeatures.*;

public class MapleForest extends OverworldBiome {
    @Override
    protected float temperature() {
        return 0.7f;
    }

    @Override
    protected float downfall() {
        return 0.8f;
    }

    @Override
    protected void effects(BiomeEditor builder) {
        super.effects(builder);

        forestMusic(builder);

        builder.grassColor(0xFF83A329);
        builder.waterColor(0x0E4ECF);
    }

    @Override
    protected void generation(BiomeEditor builder) {
        super.generation(builder);
        addDefaultOres(builder);
        addDefaultSoftDisks(builder);

        addForestFlowers(builder);
        addMapleTrees(builder);

        addMossCarpetsAroundMoss(builder);
        addBushes(builder);
        addDefaultFlowers(builder);
        addForestGrass(builder);
        addForestClovers(builder);

        addDefaultMushrooms(builder);
        addDefaultExtraVegetation(builder, true);

        addExtraForestGrass(builder);
        addCattail(builder);
        // TODO different tree canopy
    }

    @Override
    protected void spawning(BiomeEditor builder) {
        super.spawning(builder);

        farmAnimals(builder);

        builder.addSpawn(MobCategory.CREATURE, 4, new SpawnerData(EntityType.COW, 2, 3));
        builder.addSpawn(MobCategory.CREATURE, 5, new SpawnerData(EntityType.WOLF, 4, 4));
    }

}
