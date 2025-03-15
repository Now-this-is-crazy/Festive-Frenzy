package powercyphe.festive_frenzy.mixin.snowglobe;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import powercyphe.festive_frenzy.common.util.BiomeWorldAccessor;

@Mixin(WorldView.class)
public interface WorldViewMixin {

    @ModifyReturnValue(method = "getBiome", at = @At("RETURN"))
    private RegistryEntry<Biome> festive_frenzy$snowGlobe(RegistryEntry<Biome> original) {
        WorldView worldView = (WorldView) (Object) this;
        if (worldView instanceof World world) {
            ((BiomeWorldAccessor) original).festive_frenzy$setWorld(world);
        }
        return original;
    }
}
