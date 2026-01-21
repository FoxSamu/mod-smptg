package net.foxboi.salted.client.misc;

import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.foxboi.salted.common.misc.LayerRegistry;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.world.level.block.Block;

public class FabricLayerRegistry implements LayerRegistry {
    @Override
    public void solid(Block block) {
        // N/A this is the default
    }

    @Override
    public void cutout(Block block) {
        BlockRenderLayerMap.putBlock(block, ChunkSectionLayer.CUTOUT);
    }

    @Override
    public void translucent(Block block) {
        BlockRenderLayerMap.putBlock(block, ChunkSectionLayer.TRANSLUCENT);
    }
}
