package net.foxboi.salted.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.foxboi.salted.data.advancement.AdvancementProvider;
import net.foxboi.salted.data.lang.DefaultTranslationProvider;
import net.foxboi.salted.data.loot.BlockLootTableProvider;
import net.foxboi.salted.data.recipes.RecipesProvider;
import net.foxboi.salted.data.registry.RegistryProvider;
import net.foxboi.salted.data.shadercompat.ShaderCompatGenerator;
import net.foxboi.salted.data.tags.BiomeTagProvider;
import net.foxboi.salted.data.tags.BlockTagProvider;
import net.foxboi.salted.data.tags.EntityTypeTagProvider;
import net.foxboi.salted.data.tags.ItemTagProvider;

public class CommonDataGenerators {
    public void setup(FabricDataGenerator.Pack pack) {
        addAssets(pack);
        addData(pack);
        addTags(pack);
        addShaderCompat(pack);
    }

    protected void addAssets(FabricDataGenerator.Pack pack) {
        pack.addProvider(DefaultTranslationProvider::new);
    }

    protected void addData(FabricDataGenerator.Pack pack) {
        pack.addProvider(RegistryProvider::new);
        pack.addProvider(AdvancementProvider::new);
        pack.addProvider(BlockLootTableProvider::new);
        pack.addProvider(RecipesProvider.factory());
    }

    protected void addTags(FabricDataGenerator.Pack pack) {
        var blockTags = pack.addProvider(BlockTagProvider::new);
        pack.addProvider((output, regs) -> new ItemTagProvider(output, regs, blockTags));

        pack.addProvider(EntityTypeTagProvider::new);
        pack.addProvider(BiomeTagProvider::new);
    }

    protected void addShaderCompat(FabricDataGenerator.Pack pack) {
        pack.addProvider(ShaderCompatGenerator::new);
    }
}
