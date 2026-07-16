package net.spucio.morebushes.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.spucio.morebushes.MoreBushes;
import net.spucio.morebushes.item.ModItems;

@EventBusSubscriber(modid = MoreBushes.MOD_ID)
public class ModTooltipHandler {

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() == Items.SWEET_BERRIES) {
            event.getToolTip().add(1, Component.literal("Speed (0:03)").withStyle(ChatFormatting.BLUE));
        }

        if (event.getItemStack().getItem() == ModItems.CHORUSBERRIES.get()) {
            event.getToolTip().add(1, Component.literal("Chorus Effect II").withStyle(ChatFormatting.BLUE));
        }

        if (event.getItemStack().getItem() == ModItems.LAVABERRIES.get()) {
            event.getToolTip().add(1, Component.literal("Fire Resistance (0:03)").withStyle(ChatFormatting.BLUE));
        }

        if (event.getItemStack().getItem() == ModItems.SHULKERBERRIES.get()) {
            event.getToolTip().add(1,Component.literal("Levitation (0:03)").withStyle(ChatFormatting.BLUE));
        }

        if (event.getItemStack().getItem() == ModItems.BLUEBERRIES.get()) {
            event.getToolTip().add(1,Component.literal("Water Breathing (0:03)").withStyle(ChatFormatting.BLUE));
        }

        if (event.getItemStack().getItem() == ModItems.GLOWSTONEBERRIES.get()) {
            event.getToolTip().add(1,Component.literal("Regeneration (0:03)").withStyle(ChatFormatting.BLUE));
            event.getToolTip().add(2,Component.literal("Glowing (0:03)").withStyle(ChatFormatting.BLUE));
        }

        if (event.getItemStack().getItem() == ModItems.MINERSBERRIES.get()) {
            event.getToolTip().add(1,Component.literal("Haste (0:03)").withStyle(ChatFormatting.BLUE));
            event.getToolTip().add(2,Component.literal("Night Vision (0:03)").withStyle(ChatFormatting.BLUE));
        }

        if (event.getItemStack().getItem() == ModItems.GOLDENBERRIES.get()) {
            event.getToolTip().add(1,Component.literal("Regeneration (0:10)").withStyle(ChatFormatting.BLUE));
            event.getToolTip().add(2,Component.literal("Resistance (2:30)").withStyle(ChatFormatting.BLUE));
            event.getToolTip().add(3,Component.literal("Fire Resistance (2:39)").withStyle(ChatFormatting.BLUE));
            event.getToolTip().add(4,Component.literal("Absorption (1:00)").withStyle(ChatFormatting.BLUE));
        }
    }
}