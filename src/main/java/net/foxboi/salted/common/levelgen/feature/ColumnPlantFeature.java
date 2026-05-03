package net.foxboi.salted.common.levelgen.feature;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import com.mojang.serialization.Codec;
import net.foxboi.salted.common.block.AbstractColumnPlantBlock;
import net.foxboi.salted.common.block.ColumnPlantShape;

public class ColumnPlantFeature extends Feature<ColumnPlantConfiguration> {
    public ColumnPlantFeature() {
        super(ColumnPlantConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<ColumnPlantConfiguration> context) {
        var level = context.level();
        var pos = context.origin();
        var config = context.config();
        var rng = context.random();

        var dir = config.direction();
        var block = config.block();
        var minLen = config.minLength();
        var maxLen = config.maxLength();
        var allowGrowth = config.allowGrowth();
        var canReplace = config.canReplace();
        var staticLen = config.staticLength().sample(rng);
        var dynamicLenProportion = config.dynamicLength().sample(rng);

        var mpos = pos.mutable();

        var dynamicLen = 0;
        var space = -1;
        if (dynamicLenProportion >= 0.0001f) {
            space = 0;

            mpos.set(pos);
            while (canReplace.test(level, mpos) && level.isInsideBuildHeight(mpos)) {

                mpos.move(dir);
                dynamicLen++;
                space++;
            }

            dynamicLen = (int) (dynamicLen * dynamicLenProportion);
        }

        var totalLen = dynamicLen + staticLen;

        if (space < 0) {
            space = 0;

            mpos.set(pos);
            for (var i = 0; i < totalLen; i ++) {
                if (!canReplace.test(level, mpos) || level.isOutsideBuildHeight(mpos)) {
                    break;
                }

                mpos.move(dir);
                space++;
            }
        }

        totalLen = Math.max(totalLen, minLen);
        totalLen = Math.min(totalLen, maxLen);
        totalLen = Math.min(totalLen, space);

        if (totalLen < minLen || totalLen <= 0) {
            return false;
        }


        mpos.set(pos);
        for (int i = 0; i < totalLen; i ++) {
            var base = i == 0;
            var end = i == totalLen - 1;

            var shape = ColumnPlantShape.BODY;
            if (end) {
                shape = allowGrowth ? ColumnPlantShape.GROWING : ColumnPlantShape.PERMANENT;
            }

            var state = block.getState(level, rng, mpos)
                    .trySetValue(AbstractColumnPlantBlock.BASE, base)
                    .trySetValue(AbstractColumnPlantBlock.SHAPE, shape);

            level.setBlock(mpos, state, Block.UPDATE_CLIENTS);
            mpos.move(dir);
        }

        return true;
    }
}
