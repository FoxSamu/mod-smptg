package net.foxboi.salted.data;

import net.foxboi.salted.data.advancement.ModAdvancementProvider;
import net.foxboi.salted.data.lang.ModDefaultTranslationProvider;
import net.foxboi.salted.data.lang.ModLanguageProvider;
import net.foxboi.salted.data.loot.ModBlockLootTableProvider;
import net.foxboi.salted.data.recipes.ModRecipesProvider;
import net.foxboi.salted.data.registry.ModRegistryProvider;
import net.foxboi.salted.data.shadercompat.ShaderCompatGenerator;
import net.foxboi.salted.data.tags.*;
import net.foxboi.summon.api.DataGenerator;

public class CommonDataGenerators {
    public void setup(DataGenerator.Pack pack) {
        addAssets(pack);
        addData(pack);
        addShaderCompat(pack);
    }

    protected void addAssets(DataGenerator.Pack pack) {
        pack.addProvider(ModLanguageProvider::new);
    }

    protected void addData(DataGenerator.Pack pack) {
        pack.addProvider(ModTagProvider::new);
        pack.addProvider(ModRegistryProvider::new);
        pack.addProvider(ModAdvancementProvider::new);
        pack.addProvider(ModBlockLootTableProvider::new);
        pack.addProvider(ModRecipesProvider::new);
    }

    protected void addShaderCompat(DataGenerator.Pack pack) {
        pack.addProvider(ShaderCompatGenerator::new);
    }
}
