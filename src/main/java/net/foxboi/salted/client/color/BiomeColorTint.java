package net.foxboi.salted.client.color;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.foxboi.salted.common.color.BiomeColors;
import net.foxboi.salted.common.misc.Misc;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

@Environment(EnvType.CLIENT)
public record BiomeColorTint(ResourceLocation name) implements BlockColor, ItemTintSource {
    public static final MapCodec<BiomeColorTint> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ResourceLocation.CODEC.fieldOf("name").forGetter(it -> it.name)
    ).apply(inst, BiomeColorTint::new));

    @Override
    public int getColor(BlockState state, BlockAndTintGetter level, BlockPos pos, int index) {
        if (level != null && pos != null) {
            return BiomeColors.sample(level, Misc.getDoubleBlockPos(pos, state), name);
        }
        return 0xFFFF00FF;
    }

    @Override
    public int calculate(ItemStack item, ClientLevel level, LivingEntity entity) {
        return BiomeColorsClient.sampleItem(name);
    }

    @Override
    public MapCodec<? extends ItemTintSource> type() {
        return CODEC;
    }
}
