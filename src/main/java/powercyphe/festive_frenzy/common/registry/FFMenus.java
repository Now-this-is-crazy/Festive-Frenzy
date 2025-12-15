package powercyphe.festive_frenzy.common.registry;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import powercyphe.festive_frenzy.common.FestiveFrenzy;
import powercyphe.festive_frenzy.common.menu.PresentMenu;

public class FFMenus {

    public static final MenuType<PresentMenu> PRESENT_MENU = register("present", new ExtendedScreenHandlerType<>(PresentMenu::new, ByteBufCodecs.STRING_UTF8));

    public static void init() {}

    public static <T extends AbstractContainerMenu> MenuType<T> register(String id, MenuType<T> menuType) {
        return Registry.register(BuiltInRegistries.MENU, FestiveFrenzy.id(id), menuType);
    }
}
