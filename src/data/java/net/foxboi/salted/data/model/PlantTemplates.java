package net.foxboi.salted.data.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import net.minecraft.core.Direction;

import net.foxboi.salted.common.Smptg;
import net.foxboi.summon.api.model.*;

public class PlantTemplates {
    private static final String PATH = System.getProperty("smptg.template_model_path");

    public static final PlantModelSet SHRUB_1 = loadPlantModelSet("shrub_1", "_1", ModTextureKeys.BASE, ModTextureKeys.SIDE, ModTextureKeys.MIDDLE);
    public static final PlantModelSet SHRUB_2 = loadPlantModelSet("shrub_2", "_2", ModTextureKeys.BASE, ModTextureKeys.SIDE, ModTextureKeys.MIDDLE);
    public static final PlantModelSet SHRUB_3 = loadPlantModelSet("shrub_3", "_3", ModTextureKeys.BASE, ModTextureKeys.SIDE, ModTextureKeys.MIDDLE);
    public static final PlantModelSet SHRUB_4 = loadPlantModelSet("shrub_4", "_4", ModTextureKeys.BASE, ModTextureKeys.SIDE, ModTextureKeys.MIDDLE);

    private static Path path() {
        Objects.requireNonNull(PATH, "smptg.template_model_path not set, cannot build template models. Maybe fix your run configs.");
        return Path.of(PATH);
    }

    private static PlantModelSet loadPlantModelSet(String id, TextureKey... textures) {
        return loadPlantModelSet(id, "", textures);
    }

    private static PlantModelSet loadPlantModelSet(String id, String modelSuffix, TextureKey... textures) {
        class Deferral {
            FullModel base;

            void load() {
                if (base != null) {
                    return;
                }

                var filePath = path().resolve(id + ".json");

                base = FullModel.loadFromFile(filePath).getOrThrow();
            }

            ModelTemplate model(String suffix, Function<FullModel, FullModel> model) {
                var parentModel = Model.supply(() -> {
                    load();
                    return Model.full(Smptg.id(id + suffix), model.apply(base));
                });

                return (id, map) -> Model.inherit(id.withSuffix(modelSuffix), parentModel, map.copy().translate(List.of(textures)));
            }
        }

        var deferral = new Deferral();
        return new PlantModelSet(
                deferral.model("", it -> it),
                deferral.model("_tinted", PlantTemplates::createTinted),
                deferral.model("_emissive", PlantTemplates::createEmissive),
                deferral.model("_layered", PlantTemplates::createLayered),
                deferral.model("_glowing", PlantTemplates::createGlowing),
                deferral.model("_tinted_glowing", PlantTemplates::createTintedGlowing)
        );
    }


    public record PlantModelSet(
            ModelTemplate base,
            ModelTemplate tinted,
            ModelTemplate emissive,
            ModelTemplate layered,
            ModelTemplate glowing,
            ModelTemplate tintedGlowing
    ) {}

    private static FullModel createTinted(FullModel model) {
        var copy = model.copy();

        copy.elements().forEach(elem -> elem.existingFaces(face -> face.tintindex() < 0 ? face.tintindex(0) : face));
        return copy;
    }

    private static FullModel createGlowing(FullModel model) {
        var copy = model.copy();

        copy.elements().forEach(elem -> elem.lightEmission(15));
        return copy;
    }

    private static FullModel createTintedGlowing(FullModel model) {
        var copy = model.copy();

        copy.elements().forEach(elem -> elem.lightEmission(15).existingFaces(face -> face.tintindex() < 0 ? face.tintindex(0) : face));
        return copy;
    }

    private static FullModel createLayered(FullModel model) {
        var copy = model.copy();

        var extraLayer = new ArrayList<Element>();

        copy.elements().forEach(elem -> {
            var add = false;
            var cp = elem.copy().allFaces(_ -> null);

            for (var dir : Direction.values()) {
                var face = elem.face(dir);
                if (face == null) {
                    continue;
                }

                if (face.tintindex() >= 0) {
                    continue;
                }

                cp.face(dir, face.copy().tintindex(0).texture("#overlay"));
                add = true;
            }

            if (add) {
                extraLayer.add(cp);
            }
        });

        extraLayer.forEach(elem -> copy.addElement(elem));

        return copy;
    }

    private static FullModel createEmissive(FullModel model) {
        var copy = model.copy();

        var extraLayer = new ArrayList<Element>();

        copy.elements().forEach(elem -> extraLayer.add(elem.copy().lightEmission(15).existingFaces(face -> face.texture("#emissive"))));

        extraLayer.forEach(elem -> copy.addElement(elem));

        return copy;
    }
}
