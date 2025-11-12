package net.foxboi.salted.mixin.main;

import net.foxboi.salted.common.color.BiomeColor;
import net.foxboi.salted.common.color.BiomeColorLevelInj;
import net.foxboi.salted.common.color.ErrorColor;
import net.minecraft.client.renderer.chunk.RenderSectionRegion;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.Level;

import java.util.Map;

@Mixin(RenderSectionRegion.class)
public class RenderSectionRegionMixin implements BiomeColorLevelInj {
    @Shadow
    @Final
    private Level level;

    @Unique
    private BiomeColorLevelInj levelAsInj() {
        if (level instanceof BiomeColorLevelInj inj) {
            return inj;
        } else {
            return null;
        }
    }

    @Override
    public int smptg$computeBlockTintUncached(BlockPos pos, ColorResolver resolver) {
        var inj = levelAsInj();
        if (inj != null)
            return inj.smptg$computeBlockTintUncached(pos, resolver);
        return ErrorColor.INSTANCE.sample(level, pos);
    }

    @Override
    public int smptg$getBlockTint(BlockPos pos, ResourceLocation color) {
        var inj = levelAsInj();
        if (inj != null)
            return inj.smptg$getBlockTint(pos, color);
        return ErrorColor.INSTANCE.sample(level, pos);
    }

    @Override
    public void smptg$reloadColors(Map<ResourceLocation, BiomeColor> map) {
        var inj = levelAsInj();
        if (inj != null)
            inj.smptg$reloadColors(map);
    }
}
