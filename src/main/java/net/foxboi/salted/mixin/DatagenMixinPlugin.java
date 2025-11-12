package net.foxboi.salted.mixin;

import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.data.DataRunner;

public class DatagenMixinPlugin extends CompatMixinPlugin {
    public DatagenMixinPlugin() {
        super(null);
    }

    @Override
    public void onLoad(String mixinPackage) {
        if (DataRunner.enabled()) {
            Smptg.LOGGER.info("Datagen enabled, applying datagen mixins");
        }
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return DataRunner.enabled();
    }
}
