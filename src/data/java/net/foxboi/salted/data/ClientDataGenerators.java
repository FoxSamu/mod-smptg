package net.foxboi.salted.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.foxboi.salted.data.model.ModelProvider;
import net.foxboi.salted.data.model.ModModelProvider;

public class ClientDataGenerators extends CommonDataGenerators {
    @Override
    protected void addAssets(FabricDataGenerator.Pack pack) {
        super.addAssets(pack);

        pack.addProvider(ModModelProvider::new);
    }
}
