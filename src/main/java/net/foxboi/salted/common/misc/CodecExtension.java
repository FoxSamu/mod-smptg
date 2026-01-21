package net.foxboi.salted.common.misc;

import java.util.function.BiConsumer;
import java.util.function.Function;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;

/**
 * A codec that wraps another codec and extends it with an extension object. This can be used in mixins to add extra fields to a record codec.
 * Note that this ONLY works on codecs made from a {@code MapCodec}, which includes {@code RecordCodecBuilder}. The extension codec must also be made
 * from a {@code MapCodec}.
 */
public class CodecExtension<A, E> implements Codec<A> {
    private final Codec<A> baseCodec;
    private final Codec<E> extensionCodec;
    private final BiConsumer<A, E> applyExtension;
    private final Function<A, E> getExtension;

    public CodecExtension(Codec<A> baseCodec, Codec<E> extensionCodec, BiConsumer<A, E> applyExtension, Function<A, E> getExtension) {
        this.baseCodec = baseCodec;
        this.extensionCodec = extensionCodec;
        this.applyExtension = applyExtension;
        this.getExtension = getExtension;
    }

    @Override
    public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
        return baseCodec.decode(ops, input).map(baseResult -> {
            var base = baseResult.getFirst();
            var baseRemainder = baseResult.getSecond();

            // Try decode extension
            var finalResult = extensionCodec.decode(ops, baseRemainder).map(extensionResult -> {
                var extension = extensionResult.getFirst();
                var extensionRemainder = extensionResult.getSecond();

                applyExtension.accept(base, extension);

                return Pair.of(base, extensionRemainder);
            });

            // If extension decoding fails, we just ignore it, since it would cause compat issues with resources from other mods.
            if (finalResult.isSuccess()) {
                return finalResult.getOrThrow();
            } else {
                return baseResult;
            }
        });
    }

    @Override
    public <T> DataResult<T> encode(A base, DynamicOps<T> ops, T basePrefix) {
        var extension = getExtension.apply(base);
        return baseCodec.encode(base, ops, basePrefix)
                .flatMap(extensionPrefix -> extensionCodec.encode(extension, ops, extensionPrefix));
    }
}
