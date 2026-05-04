package net.foxboi.salted.common.levelgen.modifications;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.foxboi.salted.common.levelgen.noise.ModNoises;
import net.foxboi.salted.common.misc.datamod.DataModifier;

@SuppressWarnings("deprecation")
public class NetherNoiseModifier implements DataModifier<NoiseGeneratorSettings> {
    @Override
    public NoiseGeneratorSettings modify(ResourceKey<NoiseGeneratorSettings> key, NoiseGeneratorSettings original, HolderGetter.Provider lookup) {
        var router = new NoiseRouter(
                original.noiseRouter().barrierNoise(),
                original.noiseRouter().fluidLevelFloodednessNoise(),
                original.noiseRouter().fluidLevelSpreadNoise(),
                original.noiseRouter().lavaNoise(),
                original.noiseRouter().temperature(),
                original.noiseRouter().vegetation(),
                original.noiseRouter().continents(),
                DensityFunctions.noise(lookup.getOrThrow(ModNoises.NETHER_EROSION)), // Nether default erosion and depth noise are just zero
                DensityFunctions.yClampedGradient(0, 128, -1.0, 1.0),
                original.noiseRouter().ridges(),
                original.noiseRouter().preliminarySurfaceLevel(),
                original.noiseRouter().finalDensity(),
                original.noiseRouter().veinToggle(),
                original.noiseRouter().veinRidged(),
                original.noiseRouter().veinGap()
        );

        return new NoiseGeneratorSettings(
                original.noiseSettings(),
                original.defaultBlock(),
                original.defaultFluid(),
                router,
                original.surfaceRule(),
                original.spawnTarget(),
                original.seaLevel(),
                original.disableMobGeneration(),
                original.aquifersEnabled(),
                original.oreVeinsEnabled(),
                original.useLegacyRandomSource()
        );
    }
}
