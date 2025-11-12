package net.foxboi.salted.data.shadercompat;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

import net.minecraft.world.level.block.Block;

@SuppressWarnings("deprecation")
public class ComplementaryUnboundShaderCompat extends AbstractShaderCompat {
    public ComplementaryUnboundShaderCompat(Properties block, Properties item) {
        super(block, item);
    }

    public ComplementaryUnboundShaderCompat(Path directory) throws IOException {
        super(directory);
    }

    private static String blockName(Block block) {
        return block.builtInRegistryHolder().key().location().toString();
    }

    @Override
    public void leaves(Block block) {
        addBlock(10009, blockName(block));
    }

    @Override
    public void grass(Block block) {
        addBlock(10005, blockName(block));
    }

    @Override
    public void plant(Block block) {
        addBlock(10005, blockName(block));
    }

    @Override
    public void tallPlant(Block block) {
        addBlock(10005, blockName(block) + ":half=lower");
        addBlock(10021, blockName(block) + ":half=upper");
    }

    @Override
    public void hangingPlant(Block block) {
        addBlock(10013, blockName(block));
    }
}
