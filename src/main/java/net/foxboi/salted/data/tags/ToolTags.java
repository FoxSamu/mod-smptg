package net.foxboi.salted.data.tags;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.function.BiConsumer;

public class ToolTags {
    private final BiConsumer<Block, TagKey<Block>> provider;

    public ToolTags(BiConsumer<Block, TagKey<Block>> provider) {
        this.provider = provider;
    }

    public void swordEfficiently(Block block) {
        provider.accept(block, BlockTags.SWORD_EFFICIENT);
    }

    public void swordInstantly(Block block) {
        provider.accept(block, BlockTags.SWORD_INSTANTLY_MINES);
    }

    public void hoe(Block block) {
        provider.accept(block, BlockTags.MINEABLE_WITH_HOE);
    }

    public void shovel(Block block) {
        provider.accept(block, BlockTags.MINEABLE_WITH_SHOVEL);
    }

    public void axe(Block block) {
        provider.accept(block, BlockTags.MINEABLE_WITH_AXE);
    }

    public void pickaxe(Block block) {
        provider.accept(block, BlockTags.MINEABLE_WITH_PICKAXE);
    }

    public void stoneHoe(Block block) {
        provider.accept(block, BlockTags.MINEABLE_WITH_HOE);
        provider.accept(block, BlockTags.NEEDS_STONE_TOOL);
    }

    public void stoneShovel(Block block) {
        provider.accept(block, BlockTags.MINEABLE_WITH_SHOVEL);
        provider.accept(block, BlockTags.NEEDS_STONE_TOOL);
    }

    public void stoneAxe(Block block) {
        provider.accept(block, BlockTags.MINEABLE_WITH_AXE);
        provider.accept(block, BlockTags.NEEDS_STONE_TOOL);
    }

    public void stonePickaxe(Block block) {
        provider.accept(block, BlockTags.MINEABLE_WITH_PICKAXE);
        provider.accept(block, BlockTags.NEEDS_STONE_TOOL);
    }

    public void ironHoe(Block block) {
        provider.accept(block, BlockTags.MINEABLE_WITH_HOE);
        provider.accept(block, BlockTags.NEEDS_IRON_TOOL);
    }

    public void ironShovel(Block block) {
        provider.accept(block, BlockTags.MINEABLE_WITH_SHOVEL);
        provider.accept(block, BlockTags.NEEDS_IRON_TOOL);
    }

    public void ironAxe(Block block) {
        provider.accept(block, BlockTags.MINEABLE_WITH_AXE);
        provider.accept(block, BlockTags.NEEDS_IRON_TOOL);
    }

    public void ironPickaxe(Block block) {
        provider.accept(block, BlockTags.MINEABLE_WITH_PICKAXE);
        provider.accept(block, BlockTags.NEEDS_IRON_TOOL);
    }

    public void diamondHoe(Block block) {
        provider.accept(block, BlockTags.MINEABLE_WITH_HOE);
        provider.accept(block, BlockTags.NEEDS_DIAMOND_TOOL);
    }

    public void diamondShovel(Block block) {
        provider.accept(block, BlockTags.MINEABLE_WITH_SHOVEL);
        provider.accept(block, BlockTags.NEEDS_DIAMOND_TOOL);
    }

    public void diamondAxe(Block block) {
        provider.accept(block, BlockTags.MINEABLE_WITH_AXE);
        provider.accept(block, BlockTags.NEEDS_DIAMOND_TOOL);
    }

    public void diamondPickaxe(Block block) {
        provider.accept(block, BlockTags.MINEABLE_WITH_PICKAXE);
        provider.accept(block, BlockTags.NEEDS_DIAMOND_TOOL);
    }
}
