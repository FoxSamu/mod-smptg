package net.foxboi.salted.common.levelgen.noise;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import net.foxboi.salted.common.util.DataRegistry;
import net.foxboi.salted.common.util.Definition;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;

public class ModNoises {
    private static final DataRegistry<NoiseParameters> REGISTRY = DataRegistry.of(Registries.NOISE);

    public static final ResourceKey<NoiseParameters> PATHWAYS = REGISTRY.register("pathways", noise(-4, 1, 1, 1, 1.5));
    public static final ResourceKey<NoiseParameters> MOSS = REGISTRY.register("moss", noise(-3, 1, 1, 0.5));

    public static void init() {

    }

    private static Definition<NoiseParameters> noise(int firstOctave, double... amplitudes) {
        return (key, context) -> new NoiseParameters(
                firstOctave,
                new DoubleArrayList(amplitudes)
        );
    }
}
