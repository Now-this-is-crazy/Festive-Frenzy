package powercyphe.festive_frenzy.mixin.accessor;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;
import java.util.Set;

@Mixin(ModelLayers.class)
public interface ModelLayersAccessor {

    @Accessor("ALL_MODELS")
    static Set<ModelLayerLocation> festive_frenzy$getModels() {
        throw new AssertionError();
    }
}
