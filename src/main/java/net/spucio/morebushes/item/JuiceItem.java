package net.spucio.morebushes.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class JuiceItem extends Item {
    public JuiceItem(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        ItemStack result = super.finishUsingItem(stack, level, entityLiving);

        if (entityLiving instanceof Player player && player.isCreative()) {
            return result;
        }

        if (stack.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
        } else {
            if (entityLiving instanceof Player player) {
                ItemStack bottle = new ItemStack(Items.GLASS_BOTTLE);
                if (!player.getInventory().add(bottle)) {
                    player.drop(bottle, false);
                }
            }
            return result;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        // Pobieramy FoodProperties bezpośrednio z przedmiotu
        FoodProperties food = stack.getFoodProperties(null); // null, bo nie mamy tu dostępu do gracza/poziomu w łatwy sposób

        if (food != null) {
            for (FoodProperties.PossibleEffect possibleEffect : food.effects()) {
                // ... wewnątrz pętli for (FoodProperties.PossibleEffect possibleEffect : food.effects()) ...

                MobEffectInstance effect = possibleEffect.effect();
                String effectName = effect.getEffect().value().getDescriptionId();

// Obliczamy całkowitą liczbę sekund
                int totalSeconds = effect.getDuration() / 20;

// Obliczamy minuty i sekundy
                int minutes = totalSeconds / 60;
                int seconds = totalSeconds % 60;

// Formatujemy sekundy tak, aby zawsze miały dwie cyfry (np. 05 zamiast 5)
                String formattedTime = minutes + ":" + (seconds < 10 ? "0" + seconds : seconds);

                tooltip.add(Component.translatable(effectName)
                        .append(" (" + formattedTime + ")")
                        .withStyle(ChatFormatting.BLUE));
            }
        }

        // Poprawny sposób pobrania klucza przedmiotu:
        String itemName = BuiltInRegistries.ITEM.getKey(this).getPath();
        if (itemName.contains("chorus_berry_juice")) {
            tooltip.add(Component.literal("Chorus Effect III").withStyle(ChatFormatting.BLUE));
        }

        super.appendHoverText(stack, context, tooltip, flag);
    }
}