package net.foxboi.salted.mixin;

import java.util.List;
import java.util.Set;

import net.fabricmc.loader.api.FabricLoader;
import net.foxboi.salted.common.Smptg;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public abstract class CompatMixinPlugin implements IMixinConfigPlugin {
    private final String requiredModId;
    private boolean isModPresent;

    public CompatMixinPlugin(String requiredModId) {
        this.requiredModId = requiredModId;
    }

    @Override
    public void onLoad(String mixinPackage) {
        isModPresent = requiredModId != null && FabricLoader.getInstance().isModLoaded(requiredModId);

        if (isModPresent) {
            Smptg.LOGGER.info("Mod '{}' is present, applying compat mixins", requiredModId);
        }
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return isModPresent;
    }


    // We don't need all these
    // ==============================

    @Override
    public String getRefMapperConfig() {
        // N/A
        return null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
        // N/A
    }

    @Override
    public List<String> getMixins() {
        // N/A
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        // N/A
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        // N/A
    }
}
