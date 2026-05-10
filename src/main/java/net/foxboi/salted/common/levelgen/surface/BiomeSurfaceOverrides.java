package net.foxboi.salted.common.levelgen.surface;

import java.util.ArrayList;
import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.foxboi.salted.common.ModRegistries;
import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.levelgen.biome.ModBiomes;
import net.foxboi.salted.common.misc.reg.DataRegistry;
import net.foxboi.salted.common.misc.reg.DefinitionContext;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.SurfaceRules;

public record BiomeSurfaceOverrides(
        List<BiomeSurfaceOverride> overrides
) {

    // Codec
    // ================================================

    public static final Codec<BiomeSurfaceOverrides> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
            BiomeSurfaceOverride.CODEC.listOf().fieldOf("overrides").forGetter(it -> it.overrides)
    ).apply(inst, BiomeSurfaceOverrides::new));

    public static final Codec<Holder<BiomeSurfaceOverrides>> CODEC = RegistryFileCodec.create(ModRegistries.BIOME_SURFACE_OVERRIDES, DIRECT_CODEC, false);


    // Keys
    // ================================================

    private static final DataRegistry<BiomeSurfaceOverrides> REGISTRY = Smptg.REGISTRAR.data(ModRegistries.BIOME_SURFACE_OVERRIDES);

    public static final ResourceKey<BiomeSurfaceOverrides> OVERWORLD = REGISTRY.register("overworld", (_, context) -> overworld(context));
    public static final ResourceKey<BiomeSurfaceOverrides> NETHER = REGISTRY.register("nether", (_, context) -> nether(context));
    public static final ResourceKey<BiomeSurfaceOverrides> END = REGISTRY.register("end", (_, context) -> end(context));


    // Definitions
    // ================================================

    public static BiomeSurfaceOverrides overworld(DefinitionContext context) {
        // We modify vanilla biome surfaces here as well
        return new Builder(context.lookupOrThrow(Registries.BIOME))
                .add(
                        List.of(
                                ModBiomes.ASPEN_FOREST,
                                Biomes.BIRCH_FOREST,
                                Biomes.OLD_GROWTH_BIRCH_FOREST
                        ),
                        SurfaceTypes.sometimesPodzolSurface()
                )
                .add(
                        List.of(
                                ModBiomes.MAPLE_FOREST,
                                Biomes.FOREST,
                                Biomes.DARK_FOREST
                        ),
                        SurfaceTypes.sometimesPodzolSometimesMossSurface()
                )
                .add(
                        List.of(
                                ModBiomes.REDWOOD_TAIGA
                        ),
                        SurfaceTypes.redwoodSurface()
                )
                .add(
                        List.of(
                                ModBiomes.LIMESTONE_CAVES
                        ),
                        SurfaceTypes.allLimestone()
                )
                .build();
    }

    public static BiomeSurfaceOverrides nether(DefinitionContext context) {
        return new Builder(context.lookupOrThrow(Registries.BIOME))
                .add(
                        List.of(
                                ModBiomes.BURNING_FOREST
                        ),
                        SurfaceTypes.ash()
                )
                .build();
    }

    public static BiomeSurfaceOverrides end(DefinitionContext context) {
        return new BiomeSurfaceOverrides(List.of());
    }


    // Builder
    // ================================================

    public static class Builder {
        private final HolderGetter<Biome> biomes;
        private final List<BiomeSurfaceOverride> overrides = new ArrayList<>();

        public Builder(HolderGetter<Biome> biomes) {
            this.biomes = biomes;
        }

        public Builder add(ResourceKey<Biome> biome, SurfaceRules.RuleSource rule) {
            overrides.add(new BiomeSurfaceOverride(
                    HolderSet.direct(biomes.getOrThrow(biome)),
                    rule
            ));

            return this;
        }

        public Builder add(List<ResourceKey<Biome>> biome, SurfaceRules.RuleSource rule) {
            overrides.add(new BiomeSurfaceOverride(
                    HolderSet.direct(biome.stream().map(biomes::getOrThrow).toList()),
                    rule
            ));

            return this;
        }

        public Builder add(TagKey<Biome> biome, SurfaceRules.RuleSource rule) {
            overrides.add(new BiomeSurfaceOverride(
                    biomes.getOrThrow(biome),
                    rule
            ));

            return this;
        }

        public BiomeSurfaceOverrides build() {
            return new BiomeSurfaceOverrides(List.copyOf(overrides));
        }
    }
}
