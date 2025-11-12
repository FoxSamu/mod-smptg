package net.foxboi.salted.common.color;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.foxboi.salted.common.Smptg;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.HashMap;
import java.util.Map;

public class BiomeColors {
    public static final ResourceLocation GRASS = ResourceLocation.withDefaultNamespace("grass");
    public static final ResourceLocation FOLIAGE = ResourceLocation.withDefaultNamespace("foliage");
    public static final ResourceLocation DRY_FOLIAGE = ResourceLocation.withDefaultNamespace("dry_foliage");
    public static final ResourceLocation WATER = ResourceLocation.withDefaultNamespace("water");

    public static void init() {
        register("solid", SolidColor.TYPE);
        register("grass", GrassColor.TYPE);
        register("foliage", FoliageColor.TYPE);
        register("birch", BirchColor.TYPE);
        register("mangrove", MangroveColor.TYPE);
        register("spruce", SpruceColor.TYPE);
        register("dry_foliage", DryFoliageColor.TYPE);
        register("water", WaterColor.TYPE);
        register("colormap", ColormapColor.TYPE);
        register("interpolate", InterpolateColor.TYPE);
        register("dispatch", DispatchColor.TYPE);
        register("blur", BlurColor.TYPE);
    }

    private static final ResourceKey<Registry<BiomeColor.Type<?>>> REGISTRY_KEY = ResourceKey.createRegistryKey(
        ResourceLocation.fromNamespaceAndPath(Smptg.ID, "biome_color")
    );

    private static final Registry<BiomeColor.Type<?>> REGISTRY = FabricRegistryBuilder.createDefaulted(
        REGISTRY_KEY, ResourceLocation.withDefaultNamespace("solid")
    ).buildAndRegister();

    private static final Codec<BiomeColor> TYPE_CODEC =
        REGISTRY
            .byNameCodec()
            .<BiomeColor>dispatchMap(
                BiomeColor::type,
                BiomeColor.Type::codec
            )
            .codec();

    public static void register(ResourceLocation id, BiomeColor.Type<?> type) {
        Registry.register(REGISTRY, id, type);
    }

    private static void register(String id, BiomeColor.Type<?> type) {
        register(ResourceLocation.withDefaultNamespace(id), type);
    }

    private static final Codec<Integer> RAW_CODEC = Codec.intRange(0, 0xFFFFFF);
    private static final Codec<Integer> STR_CODEC = Codec.STRING.flatXmap(BiomeColors::parseArgb, BiomeColors::serialiseArgb);
    public static final Codec<Integer> HEX_CODEC = Codec.either(RAW_CODEC, STR_CODEC).xmap(
        either -> either.map(it -> it, it -> it),
        Either::right
    );

    public static final Codec<BiomeColor> CODEC = new Codec<>() {
        @Override
        public <T> DataResult<Pair<BiomeColor, T>> decode(DynamicOps<T> ops, T input) {
            var raw = HEX_CODEC.decode(ops, input).map(pair -> pair.mapFirst(color -> (BiomeColor) new SolidColor(color)));
            if (raw.isSuccess()) return raw;

            var error = raw.error().orElseThrow();
            return TYPE_CODEC.decode(ops, input).mapError(err -> error.message() + " | " + err);
        }

        @Override
        public <T> DataResult<T> encode(BiomeColor input, DynamicOps<T> ops, T prefix) {
            if (input instanceof SolidColor(int color)) {
                return HEX_CODEC.encode(color, ops, prefix);
            } else {
                return TYPE_CODEC.encode(input, ops, prefix);
            }
        }
    };

    private static int ch(char c) {
        if (c >= '0' && c <= '9')
            return c - '0';
        if (c >= 'a' && c <= 'f')
            return c - 'a' + 10;
        if (c >= 'A' && c <= 'F')
            return c - 'A' + 10;
        return -1;
    }

    private static int chch(char c) {
        var ch = ch(c);
        if (ch == -1) return -1;

        return ch << 4 | ch;
    }

    private static int chch(char c1, char c2) {
        var ch1 = ch(c1);
        var ch2 = ch(c2);
        if (ch1 == -1 || ch2 == -1) return -1;

        return ch1 << 4 | ch2;
    }

    private static DataResult<Integer> join(int r, int g, int b) {
        if (r == -1 || g == -1 || b == -1) return DataResult.error(() -> "Malformed color");
        return DataResult.success(r << 16 | g << 8 | b);
    }

    public static DataResult<Integer> parseArgb(String str) {
        if (str.startsWith("#"))
            str = str.substring(1);

        return switch (str.length()) {
            case 3 -> join(
                chch(str.charAt(0)),
                chch(str.charAt(1)),
                chch(str.charAt(2))
            );
            case 6 -> join(
                chch(str.charAt(0), str.charAt(1)),
                chch(str.charAt(2), str.charAt(3)),
                chch(str.charAt(4), str.charAt(5))
            );
            default -> DataResult.error(() -> "Malformed color");
        };
    }


    private static char uch(int n) {
        if (n >= 0 && n <= 9) {
            return (char) ('0' + n);
        } else {
            return (char) ('A' + n);
        }
    }

    public static DataResult<String> serialiseArgb(int n) {
        if (n > 0xFFFFFF) {
            return DataResult.error(() -> "Invalid color");
        }
        return DataResult.success(new String(new char[] {
            '#',
            uch(n >> 20 & 0xFF),
            uch(n >> 16 & 0xFF),
            uch(n >> 12 & 0xFF),
            uch(n >> 8 & 0xFF),
            uch(n >> 4 & 0xFF),
            uch(n & 0xFF)
        }));
    }

    public static int calculateBlockTintUncached(Level level, BlockPos pos, ColorResolver resolver) {
        if (level instanceof BiomeColorLevelInj inj) {
            return inj.smptg$computeBlockTintUncached(pos, resolver);
        } else {
            return resolver.getColor(level.getBiome(pos).value(), pos.getX(), pos.getZ());
        }
    }


    public static final Map<ResourceLocation, Colormap> COLORMAPS = new HashMap<>();

    public static double temperature(Biome biome) {
        return ((BiomeInj) (Object) biome).smptg$temperature();
    }

    public static double downfall(Biome biome) {
        return ((BiomeInj) (Object) biome).smptg$downfall();
    }

    public static int sample(BlockAndTintGetter level, BlockPos pos, ResourceLocation name) {
        if (level instanceof BiomeColorLevelInj inj) {
            return inj.smptg$getBlockTint(pos, name);
        } else {
            return 0xFF00FFFF;
        }
    }
}
