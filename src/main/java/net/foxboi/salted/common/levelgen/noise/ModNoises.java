package net.foxboi.salted.common.levelgen.noise;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.misc.reg.DataRegistry;
import net.foxboi.salted.common.misc.reg.Definition;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;

public class ModNoises {
    private static final DataRegistry<NoiseParameters> REGISTRY = Smptg.REGISTRAR.data(Registries.NOISE);

    public static final ResourceKey<NoiseParameters> LARGE_PATHWAYS = REGISTRY.register("large_pathways", noise(-6, 1, 1, 1, 1.5));
    public static final ResourceKey<NoiseParameters> PATHWAYS = REGISTRY.register("pathways", noise(-4, 1, 1, 1, 1.5));
    public static final ResourceKey<NoiseParameters> MOSS = REGISTRY.register("moss", noise(-3, 1, 1, 0.5));
    public static final ResourceKey<NoiseParameters> ASH = REGISTRY.register("ash", noise(-4, 1, 0.8, 0.6, 0.3));

    public static final ResourceKey<NoiseParameters> NETHER_EROSION = REGISTRY.register("nether_erosion", noise(-10, 1.5, 0.0, 1.0, 0.0, 0.0, 0.0));

    public static void init() {
    }

    private static Definition<NoiseParameters> noise(int firstOctave, double... amplitudes) {
        return (_, _) -> new NoiseParameters(
                firstOctave,
                new DoubleArrayList(amplitudes)
        );
    }
}
