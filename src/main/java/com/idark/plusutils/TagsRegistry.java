package com.idark.plusutils;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public class TagsRegistry {
    private final static String MODID = PlusUtils.MOD_ID;

    private static TagKey<DamageType> damage(final ResourceLocation name) {
        return TagKey.create(Registries.DAMAGE_TYPE, name);
    }
    public static final TagKey<DamageType> BYPASS = damage(new ResourceLocation(MODID, "bypass_iframe"));
}