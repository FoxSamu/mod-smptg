# Notes
Some things in the codebase might be confusing. This is where we explain the confusing things, so that you don't have to figure it out yourself. You'll thank me later.

## Foliage colormaps
SMPTG uses custom colormaps for tree colors. Four colormaps are defined: dark red, red, golden and yellow. The textures for these reside in `smptg:textures/colormap` just like the vanilla ones.

The colormaps are defined in the code in `FoliageColorMap`. These each sample colors from a `ColorMap` object. `FoliageColorMap` is basically what `FoliageColor` is in vanilla, except it is reused for each foliage color in SMPTG, e.g. `FoliageColorMap.RED` is what vanilla's `FoliageColor` is for red foliage.

The `FoliageColorMap`s sample a fallback color, unless a `ColorMap` is loaded in. Both these classes are common classes, but the `ColorMap` is only loaded on the client. This means that the dedicated server will always sample the fallback color.

Some biomes have special colors for the foliage. The Pale Garden biome is a prime example, here the colors are all grayed out. These special colors are defined by color overrides. For vanilla colors, these are properties of the `Biome` class.

When sampling colors, all we get is a `Biome` instance, which does not associate itself with any `ResourceKey` that we can check against, so we cannot keep a map anywhere with color overrides for our custom colors.

For this reason, we extended the biome codec to optionally accept some color override extension properties for our custom colors. These are injected into `BiomeSpecialEffects` using mixin. We also had to mix into Fabric's biome mofification API since we need to update our extension with it.
