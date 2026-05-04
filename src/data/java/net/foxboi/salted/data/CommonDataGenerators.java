package net.foxboi.salted.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.foxboi.salted.data.advancement.ModAdvancementProvider;
import net.foxboi.salted.data.lang.ModDefaultTranslationProvider;
import net.foxboi.salted.data.loot.ModBlockLootTableProvider;
import net.foxboi.salted.data.recipes.ModRecipesProvider;
import net.foxboi.salted.data.registry.ModRegistryProvider;
import net.foxboi.salted.data.shadercompat.ShaderCompatGenerator;
import net.foxboi.salted.data.tags.*;

public class CommonDataGenerators {
    public void setup(FabricDataGenerator.Pack pack) {
        addAssets(pack);
        addData(pack);
        addShaderCompat(pack);
    }

    protected void addAssets(FabricDataGenerator.Pack pack) {
        pack.addProvider(ModDefaultTranslationProvider::new);
    }

    protected void addData(FabricDataGenerator.Pack pack) {
        pack.addProvider(ModTagProvider::new);
        pack.addProvider(ModRegistryProvider::new);
        pack.addProvider(ModAdvancementProvider::new);
        pack.addProvider(ModBlockLootTableProvider::new);
        pack.addProvider(ModRecipesProvider.factory());
    }

    protected void addShaderCompat(FabricDataGenerator.Pack pack) {
        pack.addProvider(ShaderCompatGenerator::new);
    }
}
