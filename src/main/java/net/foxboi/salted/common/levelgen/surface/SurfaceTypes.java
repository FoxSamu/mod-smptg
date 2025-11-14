package net.foxboi.salted.common.levelgen.surface;

import net.foxboi.salted.common.block.ModBlocks;
import net.foxboi.salted.common.levelgen.noise.ModNoises;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import static net.minecraft.world.level.levelgen.SurfaceRules.*;

public class SurfaceTypes {
    public static final RuleSource DIRT = makeStateRule(Blocks.DIRT);
    public static final RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    public static final RuleSource PODZOL = makeStateRule(Blocks.PODZOL);
    public static final RuleSource MOSSY_DIRT = makeStateRule(ModBlocks.MOSSY_DIRT);
    public static final RuleSource COARSE_DIRT = makeStateRule(Blocks.COARSE_DIRT);

    private static RuleSource makeStateRule(Block block) {
        return state(block.defaultBlockState());
    }

    private static RuleSource defaultDirt(RuleSource top, RuleSource dirt) {
        return sequence(
                ifTrue(ON_FLOOR, top),
                ifTrue(UNDER_FLOOR, dirt)
        );
    }

    private static RuleSource defaultDirt(RuleSource top) {
        return defaultDirt(top, DIRT);
    }

    private static RuleSource aboveWaterOnly(RuleSource rule) {
        return ifTrue(waterBlockCheck(0, 0), rule);
    }

    public static RuleSource sometimesPodzolSurface() {
        return defaultDirt(
                aboveWaterOnly(sequence(
                        ifTrue(noiseCondition(ModNoises.PATHWAYS, 0.21), PODZOL),
                        GRASS_BLOCK
                ))
        );
    }

    public static RuleSource redwoodSurface() {
        return defaultDirt(
                aboveWaterOnly(sequence(
                        ifTrue(noiseCondition(ModNoises.PATHWAYS, 0.2), MOSSY_DIRT),
                        ifTrue(noiseCondition(ModNoises.LARGE_PATHWAYS, 0.17), PODZOL),
                        ifTrue(noiseCondition(ModNoises.LARGE_PATHWAYS, -0.17), GRASS_BLOCK),
                        PODZOL
                ))
        );
    }

    public static RuleSource sometimesPodzolSometimesMossSurface() {
        return defaultDirt(
                aboveWaterOnly(sequence(
                        ifTrue(noiseCondition(ModNoises.MOSS, 0.1), MOSSY_DIRT),
                        ifTrue(noiseCondition(ModNoises.PATHWAYS, 0.21), PODZOL),
                        GRASS_BLOCK
                ))
        );
    }
}
