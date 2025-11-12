package net.foxboi.salted.common;

import net.fabricmc.api.DedicatedServerModInitializer;

public class SmptgServer extends Smptg implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        init();
    }
}
