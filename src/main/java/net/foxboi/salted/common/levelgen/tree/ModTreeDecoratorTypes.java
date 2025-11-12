package net.foxboi.salted.common.levelgen.tree;

import net.foxboi.salted.common.Smptg;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

@SuppressWarnings("unused")
public record ModTreeDecoratorTypes() {
    public static final TreeDecoratorType<AddShelfFungusDecorator> ADD_SHELF_FUNGUS = register("add_shelf_fungus", AddShelfFungusDecorator.TYPE);
    public static final TreeDecoratorType<NoiseBasedMultifaceDecorator> NOISE_BASED_MULTIFACE = register("noise_based_multiface", NoiseBasedMultifaceDecorator.TYPE);

    private static <TD extends TreeDecorator> TreeDecoratorType<TD> register(String id, TreeDecoratorType<TD> type) {
        return Registry.register(BuiltInRegistries.TREE_DECORATOR_TYPE, Smptg.id(id), type);
    }

    public static void init() {
    }
}
