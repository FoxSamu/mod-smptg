package net.foxboi.salted.common.levelgen.feature;

import java.util.function.Function;
import java.util.function.Supplier;

import net.foxboi.salted.common.misc.reg.Definition;
import net.foxboi.salted.common.misc.reg.DefinitionContext;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class DefinedFeature<FC extends FeatureConfiguration> implements Definition<ConfiguredFeature<?, ?>> {
    private final Feature<FC> feature;
    private final Function<DefinitionContext, FC> config;

    public DefinedFeature(Feature<FC> feature, Function<DefinitionContext, FC> config) {
        this.feature = feature;
        this.config = config;
    }

    public static <F extends Feature<NoneFeatureConfiguration>> DefinedFeature<NoneFeatureConfiguration> of(F feature) {
        return new DefinedFeature<>(feature, context -> FeatureConfiguration.NONE);
    }

    public static <FC extends FeatureConfiguration> DefinedFeature<FC> of(Feature<FC> feature, Supplier<FC> config) {
        return new DefinedFeature<>(feature, context -> config.get());
    }

    public static <FC extends FeatureConfiguration, A> DefinedFeature<FC> of(Feature<FC> feature, ResourceKey<Registry<A>> registry, Function<HolderGetter<A>, FC> config) {
        return new DefinedFeature<>(feature, context -> config.apply(context.lookupOrThrow(registry)));
    }

    public static <FC extends FeatureConfiguration> DefinedFeature<FC> of(Feature<FC> feature, Function<DefinitionContext, FC> config) {
        return new DefinedFeature<>(feature, config);
    }

    @Override
    public ConfiguredFeature<?, ?> create(ResourceKey<ConfiguredFeature<?, ?>> key, DefinitionContext context) {
        return new ConfiguredFeature<>(feature, config.apply(context));
    }
}
