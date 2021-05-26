package net.tetramc.amnesia.config;

import java.io.File;
import java.util.List;

import com.oroarmor.config.Config;

import net.fabricmc.loader.api.FabricLoader;

import com.oroarmor.config.*;
import static com.google.common.collect.ImmutableList.of;

public class AmnesiaConfig extends Config {
    public static final ConfigItemGroup mainGroup = new MainGroup();
    public static final List<ConfigItemGroup> configs = of(mainGroup);

    public AmnesiaConfig() {
        super(configs, new File(FabricLoader.getInstance().getConfigDir().toFile(), "amesia_config.json"), "amesia_config.json");
    }

    public static class MainGroup extends ConfigItemGroup {
        public static final BooleanConfigItem doesAlt = new BooleanConfigItem("Enable_Alternate_Mode", false, "Enable this to make Spawnpoints_Work take effect");
        public static final BooleanConfigItem modeBool = new BooleanConfigItem("Spawnpoints_Work", true, "This setting ONLY takes effect if Enable_Alternate_Mode is true");

        public MainGroup() {
            super(of(doesAlt, modeBool), "Amnesia Settings");
        }
    }
}