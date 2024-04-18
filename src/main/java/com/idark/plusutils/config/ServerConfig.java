package com.idark.plusutils.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ServerConfig {

    public static ForgeConfigSpec.ConfigValue<Boolean>
            IFRAMES;

    public ServerConfig(ForgeConfigSpec.Builder builder) {
        IFRAMES = builder
                .comment("Will disable iframes (0,5 seconds cooldown when taking damage) (default: true)")
                .define("iframes", true);
    }

    public static final ServerConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }
}
