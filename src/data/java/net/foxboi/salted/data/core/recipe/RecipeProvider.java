package net.foxboi.salted.data.core.recipe;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.*;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public abstract class RecipeProvider implements DataProvider {
    private final PackOutput output;
    private final CompletableFuture<HolderLookup.Provider> registriesFuture;
    private final String namespace;

    private volatile HolderGetter<Item> items;

    public RecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture, String namespace) {
        this.output = output;
        this.registriesFuture = registriesFuture;
        this.namespace = namespace;
    }

    protected abstract void setup(HolderLookup.Provider registries, RecipeSink sink);

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return registriesFuture.thenCompose(lookups -> {
            items = lookups.lookupOrThrow(Registries.ITEM);

            var output = new Sink(cache, lookups);
            setup(lookups, output);
            return CompletableFuture.allOf(output.futures.toArray(CompletableFuture[]::new));
        });
    }


    public ShapedRecipeBuilder shaped(ItemLike result) {
        return shaped(result, 1);
    }

    public ShapedRecipeBuilder shaped(ItemLike result, int count) {
        return shaped(new ItemStackTemplate(result.asItem(), count));
    }

    public ShapedRecipeBuilder shaped(ItemStackTemplate result) {
        return new ShapedRecipeBuilder(items, result).namespace(namespace);
    }

    public ShapelessRecipeBuilder shapeless(ItemLike result) {
        return shapeless(result, 1);
    }

    public ShapelessRecipeBuilder shapeless(ItemLike result, int count) {
        return shapeless(new ItemStackTemplate(result.asItem(), count));
    }

    public ShapelessRecipeBuilder shapeless(ItemStackTemplate result) {
        return new ShapelessRecipeBuilder(items, result).namespace(namespace);
    }

    public CookingRecipeBuilder cooking(AbstractCookingRecipe.Factory<?> factory, ItemLike result) {
        return cooking(factory, result, 1);
    }

    public CookingRecipeBuilder cooking(AbstractCookingRecipe.Factory<?> factory, ItemLike result, int count) {
        return cooking(factory, new ItemStackTemplate(result.asItem(), count));
    }

    public CookingRecipeBuilder cooking(AbstractCookingRecipe.Factory<?> factory, ItemStackTemplate result) {
        return new CookingRecipeBuilder(factory, items, result).namespace(namespace);
    }

    public CookingRecipeBuilder smelting(ItemLike result) {
        return smelting(result, 1);
    }

    public CookingRecipeBuilder smelting(ItemLike result, int count) {
        return smelting(new ItemStackTemplate(result.asItem(), count));
    }

    public CookingRecipeBuilder smelting(ItemStackTemplate result) {
        return CookingRecipeBuilder.smelting(items, result).namespace(namespace);
    }

    public CookingRecipeBuilder blasting(ItemLike result) {
        return blasting(result, 1);
    }

    public CookingRecipeBuilder blasting(ItemLike result, int count) {
        return blasting(new ItemStackTemplate(result.asItem(), count));
    }

    public CookingRecipeBuilder blasting(ItemStackTemplate result) {
        return CookingRecipeBuilder.blasting(items, result).namespace(namespace);
    }

    public CookingRecipeBuilder smoking(ItemLike result) {
        return smoking(result, 1);
    }

    public CookingRecipeBuilder smoking(ItemLike result, int count) {
        return smoking(new ItemStackTemplate(result.asItem(), count));
    }

    public CookingRecipeBuilder smoking(ItemStackTemplate result) {
        return CookingRecipeBuilder.smoking(items, result).namespace(namespace);
    }

    public CookingRecipeBuilder campfireCooking(ItemLike result) {
        return campfireCooking(result, 1);
    }

    public CookingRecipeBuilder campfireCooking(ItemLike result, int count) {
        return campfireCooking(new ItemStackTemplate(result.asItem(), count));
    }

    public CookingRecipeBuilder campfireCooking(ItemStackTemplate result) {
        return CookingRecipeBuilder.campfireCooking(items, result).namespace(namespace);
    }

    public SingleItemRecipeBuilder stonecutting(ItemLike result) {
        return stonecutting(result, 1);
    }

    public SingleItemRecipeBuilder stonecutting(ItemLike result, int count) {
        return stonecutting(new ItemStackTemplate(result.asItem(), count));
    }

    public SingleItemRecipeBuilder stonecutting(ItemStackTemplate result) {
        return SingleItemRecipeBuilder.stonecutting(items, result).namespace(namespace);
    }

    @SuppressWarnings("deprecation")
    public static Criterion<EnterBlockTrigger.TriggerInstance> insideOf(Block block) {
        return CriteriaTriggers.ENTER_BLOCK.createCriterion(new EnterBlockTrigger.TriggerInstance(
                Optional.empty(),
                Optional.of(block.builtInRegistryHolder()),
                Optional.empty()
        ));
    }

    public Criterion<BredAnimalsTrigger.TriggerInstance> bredAnimal() {
        return CriteriaTriggers.BRED_ANIMALS.createCriterion(new BredAnimalsTrigger.TriggerInstance(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        ));
    }

    public Criterion<InventoryChangeTrigger.TriggerInstance> has(ItemLike item, MinMaxBounds.Ints count) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(items, item).withCount(count));
    }

    public Criterion<InventoryChangeTrigger.TriggerInstance> has(ItemLike item, int minAmount) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(items, item).withCount(MinMaxBounds.Ints.atLeast(minAmount)));
    }

    public Criterion<InventoryChangeTrigger.TriggerInstance> has(ItemLike item) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(items, item));
    }

    public Criterion<InventoryChangeTrigger.TriggerInstance> has(TagKey<Item> tag) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(items, tag));
    }

    public static Criterion<InventoryChangeTrigger.TriggerInstance> inventoryTrigger(ItemPredicate.Builder... predicates) {
        return inventoryTrigger(
                Arrays.stream(predicates)
                        .map(ItemPredicate.Builder::build)
                        .toArray(ItemPredicate[]::new)
        );
    }

    public static Criterion<InventoryChangeTrigger.TriggerInstance> inventoryTrigger(ItemPredicate... predicates) {
        return CriteriaTriggers.INVENTORY_CHANGED.createCriterion(new InventoryChangeTrigger.TriggerInstance(
                Optional.empty(),
                InventoryChangeTrigger.TriggerInstance.Slots.ANY,
                List.of(predicates)
        ));
    }

    public static String hasItemName(ItemLike item) {
        return "has_" + itemName(item);
    }

    public static String itemName(ItemLike item) {
        return BuiltInRegistries.ITEM.getKey(item.asItem()).getPath();
    }

    public static String hasTagName(TagKey<Item> tag) {
        return "has_" + tagName(tag);
    }

    public static String tagName(TagKey<Item> tag) {
        return tag.location().getPath();
    }

    public Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(namespace, path);
    }

    public Identifier simpleId(ItemLike product) {
        return id(itemName(product));
    }

    public Identifier conversionId(ItemLike product, ItemLike material) {
        return id(itemName(product) + "_from_" + itemName(material));
    }

    public Identifier smeltingId(ItemLike product) {
        return id(itemName(product) + "_from_smelting");
    }

    public Identifier blastingId(ItemLike product) {
        return id(itemName(product) + "_from_blasting");
    }

    private class Sink implements RecipeSink {
        private final HashSet<Identifier> generatedRecipes = new HashSet<>();
        private final ArrayList<CompletableFuture<?>> futures = new ArrayList<>();
        private final CachedOutput cache;
        private final HolderLookup.Provider lookups;

        public Sink(CachedOutput cache, HolderLookup.Provider lookups) {
            this.cache = cache;
            this.lookups = lookups;
        }

        @Override
        public void accept(ResourceKey<Recipe<?>> recipeKey, Recipe<?> recipe, AdvancementHolder advancement) {
            var identifier = recipeKey.identifier();

            if (!generatedRecipes.add(identifier)) {
                throw new IllegalStateException("Duplicate recipe with ID: " + identifier);
            }

            var recipePaths = output.createRegistryElementsPathProvider(Registries.RECIPE);
            var advancementPaths = output.createRegistryElementsPathProvider(Registries.ADVANCEMENT);

            futures.add(DataProvider.saveStable(cache, lookups, Recipe.CODEC, recipe, recipePaths.json(identifier)));
            if (advancement != null) {
                futures.add(DataProvider.saveStable(cache, lookups, Advancement.CODEC, advancement.value(), advancementPaths.json(advancement.id())));
            }
        }
    }
}
