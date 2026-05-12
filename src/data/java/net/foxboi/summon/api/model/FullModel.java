package net.foxboi.summon.api.model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.UnaryOperator;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class FullModel {
    private Model parent;
    private boolean ao;
    private final Map<String, TextureReference> textures = new LinkedHashMap<>();
    private final Map<ItemDisplayContext, Display> display = new EnumMap<>(ItemDisplayContext.class);
    private final List<Element> elements = new ArrayList<>();

    private FullModel() {
    }

    private FullModel(FullModel copy) {
        parent = copy.parent;
        ao = copy.ao;
        textures.putAll(copy.textures);

        copy.display.forEach((c, d) -> display.put(c, d.copy()));
        copy.elements.forEach(e -> elements.add(e.copy()));
    }

    public FullModel copy() {
        return new FullModel(this);
    }

    public Model parent() {
        return parent;
    }

    public Identifier parentId() {
        return parent == null ? null : parent.id();
    }

    public boolean ao() {
        return ao;
    }

    public Map<String, TextureReference> textures() {
        textures.values().removeIf(it -> it == null);
        return Collections.unmodifiableMap(textures);
    }

    public Map<ItemDisplayContext, Display> display() {
        display.values().removeIf(it -> it == null);
        return Collections.unmodifiableMap(display);
    }

    public List<Element> elements() {
        return Collections.unmodifiableList(elements);
    }

    public int elementCount() {
        return elements.size();
    }

    public Element element(int index) {
        return elements.get(index);
    }

    public TextureReference texture(String name) {
        Objects.requireNonNull(name, "name cannot be null");

        return textures.get(name);
    }

    public Set<String> textureNames() {
        return Collections.unmodifiableSet(textures.keySet());
    }

    public Display display(ItemDisplayContext context) {
        Objects.requireNonNull(context, "context cannot be null");

        return display.get(context);
    }

    public Set<ItemDisplayContext> displayContexts() {
        return Collections.unmodifiableSet(display.keySet());
    }

    public FullModel parent(Model parent) {
        this.parent = parent;
        return this;
    }

    public FullModel parent(Identifier parent) {
        this.parent = Model.reference(parent);
        return this;
    }

    public FullModel ao(boolean ao) {
        this.ao = ao;
        return this;
    }

    public FullModel texture(String name, TextureReference reference) {
        Objects.requireNonNull(name, "name cannot be null");

        if (reference == null) {
            textures.remove(name);
        } else {
            textures.put(name, reference);
        }
        return this;
    }

    public FullModel texture(String name, String texture) {
        Objects.requireNonNull(name, "name cannot be null");

        if (texture == null) {
            textures.remove(name);
        } else {
            textures.put(name, TextureReference.of(texture));
        }
        return this;
    }

    public FullModel removeTexture(String name) {
        Objects.requireNonNull(name, "name cannot be null");

        textures.remove(name);
        return this;
    }

    public FullModel clearTextures() {
        textures.clear();
        return this;
    }

    public FullModel display(ItemDisplayContext context, Display display) {
        Objects.requireNonNull(context, "context cannot be null");

        if (display == null) {
            this.display.remove(context);
        } else {
            this.display.put(context, display);
        }
        return this;
    }

    public FullModel display(ItemDisplayContext context, UnaryOperator<Display> display) {
        Objects.requireNonNull(context, "context cannot be null");

        if (display == null) {
            this.display.remove(context);
        } else {
            this.display.compute(context, (_, d) -> d == null ? display.apply(Display.display()) : display.apply(d));
        }
        return this;
    }

    public FullModel removeDisplay(ItemDisplayContext context) {
        Objects.requireNonNull(context, "context cannot be null");

        display.remove(context);
        return this;
    }

    public FullModel clearDisplay() {
        display.clear();
        return this;
    }

    public FullModel addElement(Element element) {
        elements.add(element);
        return this;
    }

    public FullModel addElement(UnaryOperator<Element> element) {
        elements.add(element.apply(Element.element()));
        return this;
    }

    public FullModel addElement(int index, Element element) {
        elements.add(index, element);
        return this;
    }

    public FullModel addElement(int index, UnaryOperator<Element> element) {
        elements.add(index, element.apply(Element.element()));
        return this;
    }

    public FullModel modifyElement(int index, Element element) {
        elements.set(index, element);
        return this;
    }

    public FullModel modifyElement(int index, UnaryOperator<Element> element) {
        elements.set(index, element.apply(elements.get(index)));
        return this;
    }

    public FullModel removeElement(int index) {
        elements.remove(index);
        return this;
    }

    public FullModel clearElements() {
        elements.clear();
        return this;
    }

    public static FullModel fullModel() {
        return new FullModel();
    }

    public static DataResult<FullModel> parse(JsonElement element) {
        return CODEC.parse(JsonOps.INSTANCE, element);
    }

    public static FullModel parseOrThrow(JsonElement element) {
        return parse(element).getOrThrow();
    }

    public static DataResult<FullModel> loadFromFile(Path path) {
        try {
            var text = Files.readString(path, StandardCharsets.UTF_8);
            var json = JsonParser.parseString(text);
            return parse(json);
        } catch (Exception e) {
            return DataResult.error(() -> e.getLocalizedMessage());
        }
    }

    public JsonElement toJson() {
        return CODEC.encodeStart(JsonOps.INSTANCE, this).getOrThrow();
    }

    private Optional<Identifier> parentForCodec() {
        return Optional.ofNullable(parentId());
    }

    private Optional<Map<String, TextureReference>> texturesForCodec() {
        return textures.isEmpty() ? Optional.empty() : Optional.of(textures);
    }

    private Optional<Map<ItemDisplayContext, Display>> displayForCodec() {
        display.values().removeIf(it -> it == null);

        return display.isEmpty() ? Optional.empty() : Optional.of(display);
    }

    private Optional<List<Element>> elementsForCodec() {
        return elements.isEmpty() ? Optional.empty() : Optional.of(elements);
    }

    private static FullModel fromCodec(Optional<Identifier> parent, boolean ao, Optional<Map<String, TextureReference>> textures, Optional<Map<ItemDisplayContext, Display>> display, Optional<List<Element>> elements) {
        var model = new FullModel();
        model.parent = parent.map(Model::reference).orElse(null);
        model.ao = ao;
        textures.ifPresent(model.textures::putAll);
        display.ifPresent(model.display::putAll);
        elements.ifPresent(model.elements::addAll);
        return model;
    }

    public static final Codec<FullModel> CODEC = RecordCodecBuilder.create(i -> i.group(
            Identifier.CODEC.optionalFieldOf("parent").forGetter(FullModel::parentForCodec),
            Codec.BOOL.optionalFieldOf("ambientocclusion", true).forGetter(FullModel::ao),
            Codec.unboundedMap(Codec.STRING, TextureReference.CODEC).optionalFieldOf("textures").forGetter(FullModel::texturesForCodec),
            Codec.unboundedMap(ItemDisplayContext.CODEC, Display.CODEC).optionalFieldOf("display").forGetter(FullModel::displayForCodec),
            Element.CODEC.listOf().optionalFieldOf("elements").forGetter(FullModel::elementsForCodec)
    ).apply(i, FullModel::fromCodec));
}
