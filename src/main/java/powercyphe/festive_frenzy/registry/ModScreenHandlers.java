package powercyphe.festive_frenzy.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import powercyphe.festive_frenzy.FestiveFrenzy;
import powercyphe.festive_frenzy.screen.PresentScreenHandler;

public class ModScreenHandlers {
    public static ScreenHandlerType<PresentScreenHandler> PRESENT_SCREEN_HANDLER;

    public static void init() {
        PRESENT_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER,
                new Identifier(FestiveFrenzy.MOD_ID, "white_present"),
                new ScreenHandlerType<>(PresentScreenHandler::new, FeatureSet.empty()));
    }
}
