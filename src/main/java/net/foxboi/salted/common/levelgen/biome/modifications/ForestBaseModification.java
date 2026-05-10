package net.foxboi.salted.common.levelgen.biome.modifications;

import java.util.function.Consumer;

import net.foxboi.salted.common.levelgen.biome.ModBiomeFeatures;
import net.foxboi.salted.common.misc.biome.BiomeEditor;

public class ForestBaseModification implements Consumer<BiomeEditor> {
    @Override
    public void accept(BiomeEditor ctx) {
        ModBiomeFeatures.addMossCarpetsAroundMossInVanillaBiome(ctx);
        ModBiomeFeatures.addExtraForestGrass(ctx);
        ModBiomeFeatures.addCattail(ctx);

    }
}
