package net.foxboi.salted.common.levelgen.biome;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;

import static net.foxboi.salted.common.levelgen.biome.ModBiomeDefaultFeatures.*;
import static net.minecraft.data.worldgen.BiomeDefaultFeatures.*;

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
    protected void effects(BiomeSpecialEffects.Builder builder) {
        super.effects(builder);

        forestMusic(builder);

        builder.grassColorOverride(0xFF83A329);
        builder.waterColor(0x0E4ECF);
        builder.waterFogColor(0x050533);
    }

    @Override
    protected void generation(BiomeGenerationSettings.Builder builder) {
        super.generation(builder);

        addForestFlowers(builder);
        addMapleTrees(builder);

        addMossCarpetsAroundMoss(builder);
        addBushes(builder);
        addDefaultFlowers(builder);
        addForestGrass(builder);
        addExtraForestGrass(builder);
        addCattail(builder);
        addForestClovers(builder);

        addDefaultMushrooms(builder);
        addDefaultExtraVegetation(builder, true);

        // TODO different tree canopy
    }

    @Override
    protected void spawning(MobSpawnSettings.Builder builder) {
        super.spawning(builder);

        farmAnimals(builder);

        builder.addSpawn(MobCategory.CREATURE, 4, new SpawnerData(EntityType.COW, 2, 3));
        builder.addSpawn(MobCategory.CREATURE, 5, new SpawnerData(EntityType.WOLF, 4, 4));
    }

}
