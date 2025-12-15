package powercyphe.festive_frenzy.mixin.accessor;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(ItemProperties.class)
public interface ItemPropertiesAccessor {

    @Accessor("GENERIC_PROPERTIES")
    static Map<ResourceLocation, ItemPropertyFunction> festive_frenzy$getGenericProperties() {
        throw new AssertionError();
    }
}
