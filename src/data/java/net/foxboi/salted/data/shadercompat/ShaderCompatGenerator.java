package net.foxboi.salted.data.shadercompat;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.block.ModBlocks;
import net.foxboi.salted.data.ModBlockData;
import net.minecraft.util.Util;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;

public class ShaderCompatGenerator implements DataProvider {
    private static final String INPUT_DIR = System.getProperty("smptg.shadercompat.input");

    private final FabricDataOutput output;
    private final Path outPath;

    public ShaderCompatGenerator(FabricDataOutput output) {
        this.output = output;
        this.outPath = output.getOutputFolder().resolve("shadercompat");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput out) {
        if (INPUT_DIR == null) {
            Smptg.LOGGER.warn("No shadercompat input directory set");
            return CompletableFuture.completedFuture(null);
        }

        var inputDir = Path.of(INPUT_DIR);

        return CompletableFuture.runAsync(() -> {
            try {
                run(inputDir, out);
            } catch (IOException exc) {
                throw new UncheckedIOException(exc);
            }
        }, Util.backgroundExecutor());
    }

    private void run(Path inputDir, CachedOutput out) throws IOException {
        run(new ComplementaryUnboundShaderCompat(inputDir.resolve("complementary_unbound")), "complementary_unbound", out);
        run(new BslShaderCompat(inputDir.resolve("bsl")), "bsl", out);
    }

    private void run(AbstractShaderCompat compat, String directory, CachedOutput out) throws IOException {
        ModBlockData.shadercompat(compat);
        compat.write(outPath.resolve(directory), out);
    }

    @Override
    public String getName() {
        return "ShaderCompat";
    }
}
