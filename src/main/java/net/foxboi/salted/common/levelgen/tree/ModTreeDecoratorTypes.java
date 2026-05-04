package net.foxboi.salted.common.levelgen.tree;

import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.misc.reg.GameRegistry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

@SuppressWarnings("unused")
public record ModTreeDecoratorTypes() {
    private static final GameRegistry<TreeDecoratorType<?>> REGISTRY = Smptg.REGISTRAR.game(Registries.TREE_DECORATOR_TYPE);

    public static final TreeDecoratorType<AddShelfFungusDecorator> ADD_SHELF_FUNGUS = REGISTRY.register("add_shelf_fungus", AddShelfFungusDecorator.TYPE);
    public static final TreeDecoratorType<NoiseBasedMultifaceDecorator> NOISE_BASED_MULTIFACE = REGISTRY.register("noise_based_multiface", NoiseBasedMultifaceDecorator.TYPE);

    public static void init() {
    }
}
