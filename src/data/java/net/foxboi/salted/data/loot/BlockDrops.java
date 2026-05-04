package net.foxboi.salted.data.loot;

import net.foxboi.salted.common.item.ModItems;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;
import java.util.Optional;

public class BlockDrops {
    private final ModBlockLootTableProvider generator;
    private final HolderLookup<Enchantment> enchantments;

    public BlockDrops(ModBlockLootTableProvider generator, HolderLookup.Provider lookup) {
        this.generator = generator;
        this.enchantments = lookup.lookupOrThrow(Registries.ENCHANTMENT);
    }

    /**
     * Block drops itself.
     */
    public void self(Block block) {
        generator.dropSelf(block);
    }

    /**
     * Block drops itself when using shears, otherwise nothing.
     */
    public void selfIfShears(Block block) {
        generator.add(block, generator::createShearsOnlyDrop);
    }

    /**
     * Block drops itself when using shears, otherwise the given item.
     */
    public void selfIfShears(Block block, ItemLike otherwise) {
        generator.add(block, it -> createShearsOrElseTable(it, otherwise));
    }

    /**
     * Block drops itself when using silk touch, otherwise nothing.
     */
    public void selfIfSilkTouch(Block block) {
        generator.add(block, generator::createSilkTouchOnlyTable);
    }

    /**
     * Block drops itself when using silk touch, otherwise the given item.
     */
    public void selfIfSilkTouch(Block block, ItemLike otherwise) {
        generator.add(block, it -> createSilkTouchOrElseTable(it, otherwise));
    }

    /**
     * Block drops like a slab: singular slabs drop once, double slabs drop twice.
     */
    public void slab(Block block) {
        generator.add(block, generator::createSlabItemTable);
    }

    /**
     * Block drops like a door: the lower piece drops itself.
     */
    public void door(Block block) {
        generator.add(block, generator::createDoorTable);
    }


    /**
     * Block drops like barley: it drops a bunch of seeds, or itself when broken with shears.
     */
    public void barley(Block block) {
        generator.add(block, this::createShortBarley);
    }

    /**
     * Block drops like tall barley: it drops a bunch of seeds, or twice its short variant when broken with shears.
     */
    public void tallBarley(Block block, Block shortBlock) {
        generator.add(block, it -> createTallBarley(it, shortBlock));
    }

    /**
     * Block drops twice its short variant.
     */
    public void splitTallPlant(Block block, Block shortBlock) {
        generator.add(block, it -> createTallPlantDropShortVariantTwice(it, shortBlock));
    }

    /**
     * Block drops itself if it's the lower half of a double block.
     */
    public void selfIfLower(Block block) {
        generator.add(block, this::createTallPlantDropSelf);
    }

    /**
     * Block drops up to four times itself based on segment count.
     */
    public void segmented(Block block) {
        generator.add(block, generator::createSegmentedBlockDrops);
    }

    /**
     * Block drops up to six times itself based on multiface count.
     */
    public void multiface(Block block) {
        generator.add(block, generator::createMultifaceBlockDrops);
    }

    /**
     * Block drops up to six times itself based on multiface count, but only when using shears.
     */
    public void multifaceIfShears(Block block) {
        generator.add(block, it -> generator.createMultifaceBlockDrops(it, generator.hasShears()));
    }


    // TABLES
    // =============================================

    private LootTable.Builder createShortBarley(Block block) {
        var loot = LootTable.lootTable();
        var pool = LootPool.lootPool();

        var seeds = LootItem.lootTableItem(Items.WHEAT_SEEDS)
                .apply(uniformCount(1f, 2f))
                .apply(ApplyBonusCount.addUniformBonusCount(enchantment(Enchantments.FORTUNE)))
                .when(generator.hasShears().invert());

        var item = LootItem.lootTableItem(block)
                .when(generator.hasShears());

        pool.add(generator.applyExplosionDecay(block, seeds));
        pool.add(item);

        loot.withPool(pool);

        return loot;
    }

    private LootTable.Builder createTallBarley(Block block, Block shortBlock) {
        var loot = LootTable.lootTable();
        var pool = LootPool.lootPool();

        var seeds = LootItem.lootTableItem(Items.WHEAT_SEEDS)
                .apply(uniformCount(2f, 4f))
                .apply(ApplyBonusCount.addUniformBonusCount(enchantment(Enchantments.FORTUNE), 2))
                .when(generator.hasShears().invert());

        var item = LootItem.lootTableItem(shortBlock)
                .apply(count(2f))
                .when(generator.hasShears());

        pool.add(generator.applyExplosionDecay(block, seeds));
        pool.add(item);

        pool.when(propertyIsExactly(block, BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER));

        loot.withPool(pool);

        return loot;
    }

    private LootTable.Builder createTallPlantDropShortVariantTwice(Block block, Block shortBlock) {
        var loot = LootTable.lootTable();
        var pool = LootPool.lootPool();

        var item = LootItem.lootTableItem(shortBlock)
                .apply(count(2f));

        pool.add(generator.applyExplosionDecay(shortBlock, item));

        pool.when(propertyIsExactly(block, BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER));

        loot.withPool(pool);

        return loot;
    }

    private LootTable.Builder createTallPlantDropSelf(Block block) {
        var loot = LootTable.lootTable();
        var pool = LootPool.lootPool();

        var item = LootItem.lootTableItem(block);

        pool.add(generator.applyExplosionDecay(block, item));

        pool.when(propertyIsExactly(block, BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER));

        loot.withPool(pool);

        return loot;
    }

    private LootTable.Builder createShearsOrElseTable(ItemLike silkTouch, ItemLike orElse) {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(silkTouch).when(generator.hasShears()))
                                .add(LootItem.lootTableItem(orElse).when(generator.hasShears().invert()))
                );
    }

    private LootTable.Builder createSilkTouchOrElseTable(ItemLike silkTouch, ItemLike orElse) {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(silkTouch).when(generator.hasSilkTouch()))
                                .add(LootItem.lootTableItem(orElse).when(generator.hasSilkTouch().invert()))
                );
    }


    // UTILITIES
    // =============================================

    private static LootItemConditionalFunction.Builder<?> count(float amount) {
        return SetItemCountFunction.setCount(ConstantValue.exactly(amount));
    }

    private static LootItemConditionalFunction.Builder<?> uniformCount(float min, float max) {
        return SetItemCountFunction.setCount(UniformGenerator.between(min, max));
    }

    @SuppressWarnings("deprecation")
    private static <T extends Comparable<T>> LootItemCondition.Builder propertyIsExactly(Block block, Property<T> property, T value) {
        return new FakeConditionBuilder(new LootItemBlockStatePropertyCondition(
                block.builtInRegistryHolder(),
                Optional.of(
                        new StatePropertiesPredicate(List.of(
                                new StatePropertiesPredicate.PropertyMatcher(
                                        property.getName(),
                                        new StatePropertiesPredicate.ExactMatcher(property.getName(value))
                                )
                        ))
                )
        ));
    }

    @SuppressWarnings("deprecation")
    private static <T extends Comparable<T>> LootItemCondition.Builder propertyIsBetween(Block block, Property<T> property, T min, T max) {
        return new FakeConditionBuilder(new LootItemBlockStatePropertyCondition(
                block.builtInRegistryHolder(),
                Optional.of(
                        new StatePropertiesPredicate(List.of(
                                new StatePropertiesPredicate.PropertyMatcher(
                                        property.getName(),
                                        new StatePropertiesPredicate.RangedMatcher(
                                                Optional.of(property.getName(min)),
                                                Optional.of(property.getName(max))
                                        )
                                )
                        ))
                )
        ));
    }

    @SuppressWarnings("deprecation")
    private static <T extends Comparable<T>> LootItemCondition.Builder propertyIsAtMost(Block block, Property<T> property, T max) {
        return new FakeConditionBuilder(new LootItemBlockStatePropertyCondition(
                block.builtInRegistryHolder(),
                Optional.of(
                        new StatePropertiesPredicate(List.of(
                                new StatePropertiesPredicate.PropertyMatcher(
                                        property.getName(),
                                        new StatePropertiesPredicate.RangedMatcher(
                                                Optional.empty(),
                                                Optional.of(property.getName(max))
                                        )
                                )
                        ))
                )
        ));
    }

    @SuppressWarnings("deprecation")
    private static <T extends Comparable<T>> LootItemCondition.Builder propertyIsAtLeast(Block block, Property<T> property, T min) {
        return new FakeConditionBuilder(new LootItemBlockStatePropertyCondition(
                block.builtInRegistryHolder(),
                Optional.of(
                        new StatePropertiesPredicate(List.of(
                                new StatePropertiesPredicate.PropertyMatcher(
                                        property.getName(),
                                        new StatePropertiesPredicate.RangedMatcher(
                                                Optional.of(property.getName(min)),
                                                Optional.empty()
                                        )
                                )
                        ))
                )
        ));
    }

    private Holder<Enchantment> enchantment(ResourceKey<Enchantment> key) {
        return enchantments.getOrThrow(key);
    }
}
