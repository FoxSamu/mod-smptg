package net.foxboi.salted.common.levelgen.tree;

import com.mojang.datafixers.Products.P3;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

/**
 * @deprecated A deobfuscated version of BlobFoliagePlacer. Do not use.
 */
@Deprecated
public class BlobFoliagePlacer extends FoliagePlacer {
	public static final MapCodec<BlobFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(instance -> blobParts(instance).apply(instance, BlobFoliagePlacer::new));
	protected final int height;

	protected static <P extends BlobFoliagePlacer> P3<Mu<P>, IntProvider, IntProvider, Integer> blobParts(Instance<P> instance) {
		return foliagePlacerParts(instance).and(Codec.intRange(0, 16).fieldOf("height").forGetter(blobFoliagePlacer -> blobFoliagePlacer.height));
	}

	public BlobFoliagePlacer(IntProvider radius, IntProvider offset, int height) {
		super(radius, offset);
		this.height = height;
	}

	@Override
	protected FoliagePlacerType<?> type() {
		return FoliagePlacerType.BLOB_FOLIAGE_PLACER;
	}

	@Override
	protected void createFoliage(
		LevelSimulatedReader level,
		FoliageSetter setter,
		RandomSource rng,
		TreeConfiguration config,
		int treeHeight,
		FoliageAttachment attachment,
		int height,
		int radius,
		int offset
	) {
        var offsetRadius = radius + attachment.radiusOffset();

		for (int localY = offset; localY >= offset - height; localY--) {
			var layerRadius = Math.max(offsetRadius - (1 + localY / 2), 0);

			placeLeavesRow(level, setter, rng, config, attachment.pos(), layerRadius, localY, attachment.doubleTrunk());
		}
	}

	@Override
	public int foliageHeight(
            RandomSource rng,
            int treeHeight,
            TreeConfiguration config
    ) {
		return height;
	}

	@Override
	protected boolean shouldSkipLocation(
            RandomSource rng,
            int localX,
            int localY,
            int localZ,
            int radius,
            boolean doubleTrunk
    ) {
		return localX == radius
                && localZ == radius
                && (rng.nextInt(2) == 0 || localY == 0);
	}
}