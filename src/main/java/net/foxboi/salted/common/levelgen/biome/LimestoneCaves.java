package net.foxboi.salted.common.levelgen.biome;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.attribute.modifier.FloatModifier;
import net.minecraft.world.level.levelgen.GenerationStep;

import net.foxboi.salted.common.levelgen.ModCaveFeatures;
import net.foxboi.salted.common.levelgen.ModCavePlacements;
import net.foxboi.salted.common.levelgen.ModVegetationPlacements;
import net.foxboi.salted.common.misc.biome.BiomeEditor;

import static net.foxboi.salted.common.levelgen.biome.ModBiomeFeatures.*;
import static net.foxboi.salted.common.levelgen.biome.VanillaBiomeFeatures.*;

public class LimestoneCaves extends CaveBiome {
    @Override
    protected float temperature() {
        return .5f;
    }

    @Override
    protected float downfall() {
        return .5f;
    }

    @Override
    protected void attributes(BiomeEditor builder) {
        super.attributes(builder);

        dripstoneCavesMusic(builder);
        builder.putAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 0xFF39665E);
        builder.modifyAttribute(EnvironmentAttributes.WATER_FOG_START_DISTANCE, FloatModifier.MULTIPLY, 0.6f);
        builder.modifyAttribute(EnvironmentAttributes.WATER_FOG_END_DISTANCE, FloatModifier.MULTIPLY, 0.6f);
    }

    @Override
    protected void effects(BiomeEditor builder) {
        builder.waterColor(0xFF36C0C9);
    }

    @Override
    protected void generation(BiomeEditor builder) {
        super.generation(builder);

        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModVegetationPlacements.GLOW_LICHEN);

        addLimestoneSpeleothems(builder);
        addPlainGrass(builder);
        addDefaultOres(builder, true);
        addDefaultSoftDisks(builder);
        addPlainVegetation(builder);
        addDefaultMushrooms(builder);
        addDefaultExtraVegetation(builder, false);
        addCaveGrass(builder);
        addMoreDripmoss(builder);
    }
}
