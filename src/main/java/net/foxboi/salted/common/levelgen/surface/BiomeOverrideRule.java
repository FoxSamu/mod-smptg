package net.foxboi.salted.common.levelgen.surface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceRules;

public record BiomeOverrideRule(
        Holder<BiomeSurfaceOverrides> overrides
) implements SurfaceRules.RuleSource {
    public static final KeyDispatchDataCodec<BiomeOverrideRule> CODEC = KeyDispatchDataCodec.of(
        BiomeSurfaceOverrides.CODEC
                .xmap(BiomeOverrideRule::new, BiomeOverrideRule::overrides)
                .fieldOf("overrides")
    );

    @Override
    public KeyDispatchDataCodec<? extends SurfaceRules.RuleSource> codec() {
        return CODEC;
    }

    @Override
    public SurfaceRules.SurfaceRule apply(SurfaceRules.Context context) {
        var overrides = this.overrides.value();

        // Try collecting to map for constant-ish time lookup, otherwise fall back to linear search
        var entries = new ArrayList<SearchEntry>();
        var optimizedEntries = new HashMap<ResourceKey<Biome>, SurfaceRules.SurfaceRule>();
        var useOptimized = true;

        for (var override : overrides.overrides()) {
            var biomes = override.biomes();
            var rule = override.rule().apply(context);

            entries.add(new SearchEntry(biomes, rule));

            if (biomes.isBound()) {
                for (var biome : biomes) {
                    if (biome instanceof Holder.Reference<Biome> reference) {
                        optimizedEntries.put(reference.key(), rule);
                    } else {
                        useOptimized = false;
                    }
                }
            } else {
                useOptimized = false;
            }
        }

        if (useOptimized) {
            return new LookupRule(context, holder -> {
                if (holder instanceof Holder.Reference<Biome> reference) {
                    return optimizedEntries.get(reference.key());
                } else {
                    // Minecraft does not allow inlined biomes, this should
                    // never happen. If it happens anyway, we don't have any
                    // surface rule defined so null will do.
                    return null;
                }
            });
        } else {
            return new LinearSearchRule(context, entries);
        }
    }

    private record SearchEntry(
            HolderSet<Biome> biomes,
            SurfaceRules.SurfaceRule rule
    ) {
    }

    private static final class LinearSearchRule implements SurfaceRules.SurfaceRule {
        private final SurfaceRules.Context context;
        private final List<SearchEntry> entries;

        private long lastUpdate;
        private SearchEntry current;

        private LinearSearchRule(SurfaceRules.Context context, List<SearchEntry> entries) {
            this.context = context;
            this.entries = entries;

            this.lastUpdate = context.lastUpdateY - 1;
        }

        @Override
        public BlockState tryApply(int x, int y, int z) {
            if (context.lastUpdateY != lastUpdate) {
                current = null;

                for (var entry : entries) {
                    if (entry.biomes.contains(context.biome.get())) {
                        current = entry;
                        break;
                    }
                }

                lastUpdate = context.lastUpdateY;
            }

            if (current != null) {
                return current.rule().tryApply(x, y, z);
            }

            return null;
        }
    }

    private static final class LookupRule implements SurfaceRules.SurfaceRule {
        private final SurfaceRules.Context context;
        private final Function<Holder<Biome>, SurfaceRules.SurfaceRule> entries;

        private long lastUpdate;
        private SurfaceRules.SurfaceRule current;

        private LookupRule(SurfaceRules.Context context, Function<Holder<Biome>, SurfaceRules.SurfaceRule> entries) {
            this.context = context;
            this.entries = entries;

            this.lastUpdate = context.lastUpdateY - 1;
        }

        @Override
        public BlockState tryApply(int x, int y, int z) {
            if (context.lastUpdateY != lastUpdate) {
                current = entries.apply(context.biome.get());
                lastUpdate = context.lastUpdateY;
            }

            if (current != null) {
                return current.tryApply(x, y, z);
            }

            return null;
        }
    }
}
