package net.foxboi.salted.common.levelgen.surface;

import java.util.List;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.foxboi.salted.common.ModRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.NoiseSettings;

public class ModifiedNoiseGeneratorSettings {
    public static void bootstrap(BootstrapContext<NoiseGeneratorSettings> ctx) {
        ctx.register(NoiseGeneratorSettings.OVERWORLD, overworld(ctx, false, false));
        ctx.register(NoiseGeneratorSettings.LARGE_BIOMES, overworld(ctx, false, true));
        ctx.register(NoiseGeneratorSettings.AMPLIFIED, overworld(ctx, true, false));
        ctx.register(NoiseGeneratorSettings.NETHER, nether(ctx));
        ctx.register(NoiseGeneratorSettings.END, end(ctx));
        ctx.register(NoiseGeneratorSettings.CAVES, caves(ctx));
        ctx.register(NoiseGeneratorSettings.FLOATING_ISLANDS, floatingIslands(ctx));
    }

    private static Holder<BiomeSurfaceOverrides> getOverrides(BootstrapContext<NoiseGeneratorSettings> ctx, ResourceKey<BiomeSurfaceOverrides> key) {
        return ctx.lookup(ModRegistries.BIOME_SURFACE_OVERRIDES).getOrThrow(key);
    }

    private static NoiseGeneratorSettings end(BootstrapContext<NoiseGeneratorSettings> ctx) {
        return new NoiseGeneratorSettings(
                NoiseSettings.END_NOISE_SETTINGS,
                Blocks.END_STONE.defaultBlockState(),
                Blocks.AIR.defaultBlockState(),
                NoiseRouterData.end(ctx.lookup(Registries.DENSITY_FUNCTION)),
                ModifiedSurfaceRuleData.end(getOverrides(ctx, BiomeSurfaceOverrides.END)),
                List.of(),
                0,
                true,
                false,
                false,
                true
        );
    }

    private static NoiseGeneratorSettings nether(BootstrapContext<NoiseGeneratorSettings> ctx) {
        return new NoiseGeneratorSettings(
                NoiseSettings.NETHER_NOISE_SETTINGS,
                Blocks.NETHERRACK.defaultBlockState(),
                Blocks.LAVA.defaultBlockState(),
                NoiseRouterData.nether(ctx.lookup(Registries.DENSITY_FUNCTION), ctx.lookup(Registries.NOISE)),
                ModifiedSurfaceRuleData.nether(getOverrides(ctx, BiomeSurfaceOverrides.NETHER)),
                List.of(),
                32,
                false,
                false,
                false,
                true
        );
    }

    private static NoiseGeneratorSettings overworld(BootstrapContext<NoiseGeneratorSettings> ctx, boolean amplified, boolean largeBiomes) {
        return new NoiseGeneratorSettings(
                NoiseSettings.OVERWORLD_NOISE_SETTINGS,
                Blocks.STONE.defaultBlockState(),
                Blocks.WATER.defaultBlockState(),
                NoiseRouterData.overworld(ctx.lookup(Registries.DENSITY_FUNCTION), ctx.lookup(Registries.NOISE), largeBiomes, amplified),
                ModifiedSurfaceRuleData.overworld(getOverrides(ctx, BiomeSurfaceOverrides.OVERWORLD)),
                new OverworldBiomeBuilder().spawnTarget(),
                63,
                false,
                true,
                true,
                false
        );
    }

    private static NoiseGeneratorSettings caves(BootstrapContext<NoiseGeneratorSettings> ctx) {
        return new NoiseGeneratorSettings(
                NoiseSettings.CAVES_NOISE_SETTINGS,
                Blocks.STONE.defaultBlockState(),
                Blocks.WATER.defaultBlockState(),
                NoiseRouterData.caves(ctx.lookup(Registries.DENSITY_FUNCTION)),
                ModifiedSurfaceRuleData.overworldLike(getOverrides(ctx, BiomeSurfaceOverrides.OVERWORLD), false, true, true),
                List.of(),
                32,
                false,
                false,
                false,
                true
        );
    }

    private static NoiseGeneratorSettings floatingIslands(BootstrapContext<NoiseGeneratorSettings> ctx) {
        return new NoiseGeneratorSettings(
                NoiseSettings.FLOATING_ISLANDS_NOISE_SETTINGS,
                Blocks.STONE.defaultBlockState(),
                Blocks.WATER.defaultBlockState(),
                NoiseRouterData.floatingIslands(ctx.lookup(Registries.DENSITY_FUNCTION), ctx.lookup(Registries.NOISE)),
                ModifiedSurfaceRuleData.overworldLike(getOverrides(ctx, BiomeSurfaceOverrides.OVERWORLD), false, false, false),
                List.of(),
                -64,
                false,
                false,
                false,
                true
        );
    }
}
