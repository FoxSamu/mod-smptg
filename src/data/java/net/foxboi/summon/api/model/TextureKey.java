package net.foxboi.summon.api.model;

import java.util.ArrayList;
import java.util.List;

public record TextureKey(String name, TextureKey parent) {
    public static TextureKey of(String name) {
        return of(name, null);
    }

    public static TextureKey of(String name, TextureKey parent) {
        return new TextureKey(name, parent);
    }

    public List<String> possibleNames() {
        var names = new ArrayList<String>();
        var key = this;
        while (key != null) {
            names.add(key.name());
            key = key.parent();
        }
        return names;
    }
}
