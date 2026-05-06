package net.foxboi.salted.data;

import net.foxboi.salted.data.model.ModModelProvider;
import net.foxboi.summon.api.DataGenerator;

public class ClientDataGenerators extends CommonDataGenerators {
    @Override
    protected void addAssets(DataGenerator.Pack pack) {
        super.addAssets(pack);

        pack.addProvider(ModModelProvider::new);
    }
}
