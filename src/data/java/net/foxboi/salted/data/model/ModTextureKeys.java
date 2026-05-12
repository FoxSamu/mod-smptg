package net.foxboi.salted.data.model;

import net.foxboi.summon.api.model.TextureKey;

public class ModTextureKeys {
    public static final TextureKey EMISSIVE = TextureKey.of("emissive");
    public static final TextureKey OVERLAY = TextureKey.of("overlay");
    public static final TextureKey FRAME = TextureKey.of("frame");

    public static final TextureKey MIDDLE = TextureKey.of("middle", TextureKeys.TEXTURE);
    public static final TextureKey SIDE = TextureKey.of("side", TextureKeys.TEXTURE);
    public static final TextureKey BASE = TextureKey.of("base");
}
