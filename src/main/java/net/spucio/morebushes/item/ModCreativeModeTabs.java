package net.spucio.morebushes.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.spucio.morebushes.MoreBushes;

public class ModCreativeModeTabs {
    // Zmiana: Używamy pakietów neoforged
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MoreBushes.MOD_ID);

    // Zmiana: RegistryObject w nowym NeoForge często zastępuje się DeferredHolder
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MORE_BUSHES_TAB =
            CREATIVE_MODE_TABS.register("more_bushes_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.BLUEBERRIES.get()))
                    .title(Component.translatable("More Bushes")) // Dobra praktyka: klucz tłumaczenia
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(Items.SWEET_BERRIES);
                        output.accept(ModItems.SWEETBERRYJUICE.get());
                        output.accept(ModItems.BLUEBERRIES.get());
                        output.accept(ModItems.BLUEBERRYJUICE.get());
                        output.accept(ModItems.MINERSBERRIES.get());
                        output.accept(ModItems.MINERSBERRYJUICE.get());
                        output.accept(ModItems.GOLDENBERRIES.get());
                        output.accept(ModItems.GOLDENBERRYJUICE.get());
                        output.accept(ModItems.LAVABERRIES.get());
                        output.accept(ModItems.LAVABERRYJUICE.get());
                        output.accept(ModItems.GLOWSTONEBERRIES.get());
                        output.accept(ModItems.GLOWSTONEBERRYJUICE.get());
                        output.accept(ModItems.SHULKERBERRIES.get());
                        output.accept(ModItems.SHULKERBERRYJUICE.get());
                        output.accept(ModItems.CHORUSBERRIES.get());
                        output.accept(ModItems.CHORUSBERRYJUICE.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}