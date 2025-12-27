package powercyphe.festive_frenzy.common.registry;

import org.ladysnake.cca.api.v3.component.Component;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import powercyphe.festive_frenzy.common.FestiveFrenzy;
import powercyphe.festive_frenzy.common.cca.ThrownBaubleDataComponent;
import powercyphe.festive_frenzy.common.cca.WreathChakramDataComponent;
import powercyphe.festive_frenzy.common.entity.ThrownBaubleProjectileEntity;
import powercyphe.festive_frenzy.common.entity.WreathChakramProjectileEntity;

public class FFComponents implements EntityComponentInitializer {
    public static final ComponentKey<WreathChakramDataComponent> WREATH_CHAKRAM_DATA = register("wreath_chakram_data", WreathChakramDataComponent.class);
    public static final ComponentKey<ThrownBaubleDataComponent> THROWN_BAUBLE_DATA = register("thrown_bauble_data", ThrownBaubleDataComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(WreathChakramProjectileEntity.class, WREATH_CHAKRAM_DATA, WreathChakramDataComponent::new);
        registry.registerFor(ThrownBaubleProjectileEntity.class, THROWN_BAUBLE_DATA, ThrownBaubleDataComponent::new);
    }

    public static <T extends Component> ComponentKey<T> register(String id, Class<T> component) {
        return ComponentRegistry.getOrCreate(FestiveFrenzy.id(id), component);
    }
}
