package com.ChalkerCharles.morecolorful.common.menu;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModMenuTypes {
    private static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, MoreColorful.MODID);

    public static final Supplier<MenuType<PyrotechnicsMenu>> PYROTECHNICS = register("pyrotechnics", PyrotechnicsMenu::new);
    public static final Supplier<MenuType<PapercraftMenu>> PAPERCRAFT = register_("papercraft", PapercraftMenu::new);

    private static <T extends AbstractContainerMenu> Supplier<MenuType<T>> register(String name, MenuType.MenuSupplier<T> factory) {
        return MENU_TYPES.register(name, () -> new MenuType<>(factory, FeatureFlags.DEFAULT_FLAGS));
    }

    private static <T extends AbstractContainerMenu> Supplier<MenuType<T>> register_(String name, IContainerFactory<T> factory) {
        return MENU_TYPES.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
