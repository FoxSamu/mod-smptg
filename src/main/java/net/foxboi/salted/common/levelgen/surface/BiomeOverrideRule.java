package net.foxboi.salted.common.levelgen.surface;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
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
        var entries = new ArrayList<Entry>();

        for (var override : overrides.overrides()) {
            entries.add(new Entry(
                    override.biomes(),
                    override.rule().apply(context)
            ));
        }

        return new Rule(context, entries);
    }

    private record Entry(
            HolderSet<Biome> biomes,
            SurfaceRules.SurfaceRule rule
    ) {
    }

    private static final class Rule implements SurfaceRules.SurfaceRule {
        private final SurfaceRules.Context context;
        private final List<Entry> entries;

        private long lastUpdate;
        private Entry current;

        private Rule(SurfaceRules.Context context, List<Entry> entries) {
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
}
