package net.foxboi.salted.common.entity;

import java.util.function.Function;
import java.util.function.Supplier;

import com.mojang.datafixers.types.Func;
import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.item.ModItems;
import net.foxboi.salted.common.misc.Translator;
import net.foxboi.salted.common.misc.reg.GameRegistry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import net.minecraft.world.entity.vehicle.boat.ChestRaft;
import net.minecraft.world.entity.vehicle.boat.Raft;
import net.minecraft.world.item.Item;

public class ModEntityTypes {
    private static final GameRegistry<EntityType<?>> REGISTRY = Smptg.REGISTRAR.game(Registries.ENTITY_TYPE);

    public static final EntityType<Boat> ASPEN_BOAT = registerBoat("aspen_boat", boatFactory(() -> ModItems.ASPEN_BOAT));
    public static final EntityType<Boat> BEECH_BOAT = registerBoat("beech_boat", boatFactory(() -> ModItems.BEECH_BOAT));
    public static final EntityType<Boat> MAPLE_BOAT = registerBoat("maple_boat", boatFactory(() -> ModItems.MAPLE_BOAT));
    public static final EntityType<Boat> REDWOOD_BOAT = registerBoat("redwood_boat", boatFactory(() -> ModItems.REDWOOD_BOAT));
    public static final EntityType<Boat> DEAD_WOOD_BOAT = registerBoat("dead_wood_boat", boatFactory(() -> ModItems.DEAD_WOOD_BOAT));

    public static final EntityType<ChestBoat> ASPEN_CHEST_BOAT = registerBoat("aspen_chest_boat", chestBoatFactory(() -> ModItems.ASPEN_CHEST_BOAT));
    public static final EntityType<ChestBoat> BEECH_CHEST_BOAT = registerBoat("beech_chest_boat", chestBoatFactory(() -> ModItems.BEECH_CHEST_BOAT));
    public static final EntityType<ChestBoat> MAPLE_CHEST_BOAT = registerBoat("maple_chest_boat", chestBoatFactory(() -> ModItems.MAPLE_CHEST_BOAT));
    public static final EntityType<ChestBoat> REDWOOD_CHEST_BOAT = registerBoat("redwood_chest_boat", chestBoatFactory(() -> ModItems.REDWOOD_CHEST_BOAT));
    public static final EntityType<ChestBoat> DEAD_WOOD_CHEST_BOAT = registerBoat("dead_wood_chest_boat", chestBoatFactory(() -> ModItems.DEAD_WOOD_CHEST_BOAT));

    public static void init() {

    }

    public static void translate(Translator<EntityType<?>> translator) {
        translator.name(ASPEN_BOAT, "Aspen Boat");
        translator.name(BEECH_BOAT, "Beech Boat");
        translator.name(MAPLE_BOAT, "Maple Boat");
        translator.name(REDWOOD_BOAT, "Redwood Boat");
        translator.name(DEAD_WOOD_BOAT, "Dead Wood Boat");

        translator.name(ASPEN_CHEST_BOAT, "Aspen Chest Boat");
        translator.name(BEECH_CHEST_BOAT, "Beech Chest Boat");
        translator.name(MAPLE_CHEST_BOAT, "Maple Chest Boat");
        translator.name(REDWOOD_CHEST_BOAT, "Redwood Chest Boat");
        translator.name(DEAD_WOOD_CHEST_BOAT, "Dead Wood Chest Boat");
    }

    private static <E extends Entity> EntityType<E> register(String id, EntityType.Builder<E> type) {
        return REGISTRY.register(id, (Function<ResourceKey<EntityType<?>>, EntityType<E>>) type::build);
    }

    private static <E extends Entity> EntityType<E> registerBoat(String id, EntityType.EntityFactory<E> factory) {
        return register(
                id,
                EntityType.Builder.of(factory, MobCategory.MISC)
                        .noLootTable()
                        .sized(1.375F, 0.5625F)
                        .eyeHeight(0.5625F)
                        .clientTrackingRange(10)
        );
    }

    private static EntityType.EntityFactory<Boat> boatFactory(Supplier<Item> supplier) {
        return (entityType, level) -> new Boat(entityType, level, supplier);
    }

    private static EntityType.EntityFactory<ChestBoat> chestBoatFactory(Supplier<Item> supplier) {
        return (entityType, level) -> new ChestBoat(entityType, level, supplier);
    }

    private static EntityType.EntityFactory<Raft> raftFactory(Supplier<Item> supplier) {
        return (entityType, level) -> new Raft(entityType, level, supplier);
    }

    private static EntityType.EntityFactory<ChestRaft> chestRaftFactory(Supplier<Item> supplier) {
        return (entityType, level) -> new ChestRaft(entityType, level, supplier);
    }
}
