package com.you.maceclicker;

import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;

public class MaceClickerAddon extends MeteorAddon {
    @Override
    public void onInitialize() {
        Modules.get().add(new MaceClickerModule());
    }
}
