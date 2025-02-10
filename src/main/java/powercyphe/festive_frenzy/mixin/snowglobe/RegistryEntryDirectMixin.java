package powercyphe.festive_frenzy.mixin.snowglobe;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import powercyphe.festive_frenzy.util.BiomeWorldAccessor;

@Mixin(RegistryEntry.Direct.class)
public class RegistryEntryDirectMixin implements BiomeWorldAccessor {

    @Unique
    private World world;

    @ModifyReturnValue(method = "value", at = @At("RETURN"))
    private <T> T festive_frenzy$snowGlobe(T original) {
        RegistryEntry.Direct<?> direct = (RegistryEntry.Direct<?>) (Object) this;
        World world = ((BiomeWorldAccessor) (RegistryEntry<?>) direct).festive_frenzy$getWorld();
        if (world != null) {
            ((BiomeWorldAccessor) original).festive_frenzy$setWorld(world);
        }
        return original;
    }

    @Override
    public void festive_frenzy$setWorld(World world) {
        this.world = world;
    }

    @Override
    public World festive_frenzy$getWorld() {
        return this.world;
    }
}
