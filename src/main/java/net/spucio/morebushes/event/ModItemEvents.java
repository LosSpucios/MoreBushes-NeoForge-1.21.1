package net.spucio.morebushes.event; // Dopasuj pakiet do swojej struktury

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.spucio.morebushes.MoreBushes;

@EventBusSubscriber(modid = MoreBushes.MOD_ID)
public class ModItemEvents {

    @SubscribeEvent
    public static void onFoodEaten(LivingEntityUseItemEvent.Finish event) {
        // Sprawdzamy, czy gracz zjadł waniliowe Sweet Berries
        if (event.getItem().is(Items.SWEET_BERRIES)) {
            event.getEntity().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60));
        }
    }
}