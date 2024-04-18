package com.idark.plusutils;

import com.idark.plusutils.config.ClientConfig;
import com.idark.plusutils.config.ServerConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.stream.Stream;

public class Events {


    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event) {
        if(ServerConfig.IFRAMES.get()) {
            Entity entity = event.getEntity();
            if (entity != null) {
                if (!event.getSource().is(TagsRegistry.BYPASS)) {
                    return;
                }

                entity.invulnerableTime = 0;
            }
        }
    }

    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent e) {
        if (ClientConfig.TAGS.get()) {
            ItemStack itemStack = e.getItemStack();
            Stream<ResourceLocation> itemTagStream = itemStack.getTags().map(TagKey::location);
            if (Minecraft.getInstance().options.advancedItemTooltips) {
                if (Screen.hasControlDown()) {
                    if (!itemStack.getTags().toList().isEmpty()) {
                        e.getToolTip().add(Component.empty());
                        e.getToolTip().add(Component.literal("ItemTags: " + itemTagStream.toList()).withStyle(ChatFormatting.DARK_GRAY));
                    }

                    if (itemStack.getItem() instanceof BlockItem blockItem) {
                        BlockState blockState = blockItem.getBlock().defaultBlockState();
                        Stream<ResourceLocation> blockTagStream = blockState.getTags().map(TagKey::location);
                        if (!blockState.getTags().map(TagKey::location).toList().isEmpty()) {
                            if (itemStack.getTags().toList().isEmpty())
                                e.getToolTip().add(Component.empty());

                            e.getToolTip().add(Component.literal("BlockTags: " + blockTagStream.toList()).withStyle(ChatFormatting.DARK_GRAY));
                        }
                    }
                } else if (!itemStack.getTags().toList().isEmpty() || itemStack.getItem() instanceof BlockItem blockItem && !blockItem.getBlock().defaultBlockState().getTags().toList().isEmpty()) {
                    e.getToolTip().add(Component.empty());
                    e.getToolTip().add(Component.literal("Press [Control] to get tags info").withStyle(ChatFormatting.GRAY));
                }
            }
        }
    }
}