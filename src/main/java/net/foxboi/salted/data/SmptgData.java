package net.foxboi.salted.data;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.loader.api.FabricLoader;
import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.util.DataRegistry;
import net.foxboi.salted.data.advancement.AdvancementProvider;
import net.foxboi.salted.data.lang.DefaultTranslationProvider;
import net.foxboi.salted.data.loot.BlockLootTableProvider;
import net.foxboi.salted.data.model.ModelProvider;
import net.foxboi.salted.data.recipes.RecipesProvider;
import net.foxboi.salted.data.registry.RegistryProvider;
import net.foxboi.salted.data.tags.BiomeTagProvider;
import net.foxboi.salted.data.tags.BlockTagProvider;
import net.foxboi.salted.data.tags.EntityTypeTagProvider;
import net.foxboi.salted.data.tags.ItemTagProvider;
import net.minecraft.core.RegistrySetBuilder;

public class SmptgData implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        var pack = generator.createPack();

        // Assets
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            pack.addProvider(DefaultTranslationProvider::new);
            pack.addProvider(ModelProvider::new);
        }

        // Data
        pack.addProvider(RegistryProvider::new);
        pack.addProvider(AdvancementProvider::new);
        pack.addProvider(BlockLootTableProvider::new);
        pack.addProvider(RecipesProvider.factory());

        // Tags
        var blockTags = pack.addProvider(BlockTagProvider::new);
        pack.addProvider((output, regs) -> new ItemTagProvider(output, regs, blockTags));

        pack.addProvider(EntityTypeTagProvider::new);
        pack.addProvider(BiomeTagProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder builder) {
        DataRegistry.depositAll(builder);
    }
}
