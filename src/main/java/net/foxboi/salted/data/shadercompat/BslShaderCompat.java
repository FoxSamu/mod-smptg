package net.foxboi.salted.data.shadercompat;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

import net.minecraft.world.level.block.Block;

@SuppressWarnings("deprecation")
public class BslShaderCompat extends AbstractShaderCompat {
    public BslShaderCompat(Properties block, Properties item) {
        super(block, item);
    }

    public BslShaderCompat(Path directory) throws IOException {
        super(directory);
    }

    private static String blockName(Block block) {
        return block.builtInRegistryHolder().key().identifier().toString();
    }

    @Override
    public void leaves(Block block) {
        addBlock(10009, blockName(block));
    }

    @Override
    public void grass(Block block) {
        addBlock(10000, blockName(block));
    }

    @Override
    public void plant(Block block) {
        addBlock(10100, blockName(block));
    }

    @Override
    public void tallPlant(Block block) {
        addBlock(10200, blockName(block) + ":half=lower");
        addBlock(10300, blockName(block) + ":half=upper");
    }

    @Override
    public void hangingPlant(Block block) {
        addBlock(10600, blockName(block));
    }
}
