package net.foxboi.salted.common.levelgen.modifications;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import net.foxboi.salted.common.block.ModBlockTags;
import net.foxboi.salted.common.block.ModBlocks;
import net.foxboi.salted.common.misc.datamod.DataModifier;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class OreModifier implements DataModifier<ConfiguredFeature<?, ?>> {
    @Override
    public ConfiguredFeature<?, ?> modify(ResourceKey<ConfiguredFeature<?, ?>> key, ConfiguredFeature<?, ?> original, HolderGetter.Provider lookup) {
        var config = original.config();
        if (config instanceof OreConfiguration oreConfig) {
            return new ConfiguredFeature(original.feature(), addLimestoneVariants(oreConfig));
        }

        return original;
    }

    private static OreConfiguration.TargetBlockState createLimestoneVariant(Block limestoneVariant) {
        return OreConfiguration.target(
                new TagMatchTest(ModBlockTags.LIMESTONE_ORE_REPLACEABLES),
                limestoneVariant.defaultBlockState()
        );
    }

    private OreConfiguration addLimestoneVariants(OreConfiguration configuration) {
        var targetStates = new ArrayList<>(configuration.targetStates);
        var size = configuration.size;
        var discardChanceOnAirExposure = configuration.discardChanceOnAirExposure;

        for (var state : List.copyOf(targetStates)) {
            if (state.state.is(Blocks.COAL_ORE)) {
                targetStates.addFirst(createLimestoneVariant(ModBlocks.LIMESTONE_COAL_ORE));
            }
            if (state.state.is(Blocks.COPPER_ORE)) {
                targetStates.addFirst(createLimestoneVariant(ModBlocks.LIMESTONE_COPPER_ORE));
            }
            if (state.state.is(Blocks.IRON_ORE)) {
                targetStates.addFirst(createLimestoneVariant(ModBlocks.LIMESTONE_IRON_ORE));
            }
            if (state.state.is(Blocks.GOLD_ORE)) {
                targetStates.addFirst(createLimestoneVariant(ModBlocks.LIMESTONE_GOLD_ORE));
            }
            if (state.state.is(Blocks.DIAMOND_ORE)) {
                targetStates.addFirst(createLimestoneVariant(ModBlocks.LIMESTONE_DIAMOND_ORE));
            }
            if (state.state.is(Blocks.LAPIS_ORE)) {
                targetStates.addFirst(createLimestoneVariant(ModBlocks.LIMESTONE_LAPIS_ORE));
            }
            if (state.state.is(Blocks.REDSTONE_ORE)) {
                targetStates.addFirst(createLimestoneVariant(ModBlocks.LIMESTONE_REDSTONE_ORE));
            }
            if (state.state.is(Blocks.EMERALD_ORE)) {
                targetStates.addFirst(createLimestoneVariant(ModBlocks.LIMESTONE_EMERALD_ORE));
            }
        }

        return new OreConfiguration(targetStates, size, discardChanceOnAirExposure);
    }
}
