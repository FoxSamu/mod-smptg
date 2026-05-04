package net.foxboi.salted.data.advancement;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.item.ModItemTags;
import net.foxboi.salted.common.item.ModItems;
import net.foxboi.salted.common.misc.Translator;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.criterion.ConsumeItemTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends FabricAdvancementProvider {
    public ModAdvancementProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> regs) {
        super(output, regs);
    }

    private static String title(String category, String name) {
        return "advancements." + Smptg.ID + "." + category + "." + name + ".title";
    }

    private static String description(String category, String name) {
        return "advancements." + Smptg.ID + "." + category + "." + name + ".description";
    }

    @Override
    @SuppressWarnings("removal")
    public void generateAdvancement(HolderLookup.Provider regs, Consumer<AdvancementHolder> output) {
        var items = regs.lookupOrThrow(Registries.ITEM);


        var adventureRoot = Identifier.withDefaultNamespace("adventure/root");
//
//        var eatSaltedFood = Advancement.Builder.advancement()
//                .parent(adventureRoot)
//                .display(
//                        ModItems.PINCH_OF_SALT,
//                        Component.translatable(title("adventure", "eat_salted_food")),
//                        Component.translatable(description("adventure", "eat_salted_food")),
//                        null,
//                        AdvancementType.TASK,
//                        true,
//                        true,
//                        false
//                )
//                .addCriterion("eat_salted_food", ConsumeItemTrigger.TriggerInstance.usedItem(
//                        ItemPredicate.Builder.item()
//                                .of(items, ModItemTags.SALTED_FOODS)
//                ))
//                .save(output, Smptg.sid("adventure/eat_salted_food"));
    }

    public static void translate(Translator<String> translator) {
//        translator.name(title("adventure", "eat_salted_food"), "Take it with a Grain of Salt");
//        translator.name(description("adventure", "eat_salted_food"), "Add salt to your food, and then eat it");
    }
}
